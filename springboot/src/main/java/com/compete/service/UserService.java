package com.compete.service;

import com.compete.config.SystemCache;
import com.compete.dao.SysUserDao;
import com.compete.entity.SysUser;
import com.compete.util.DESUtil;
import com.compete.util.DateUtil;
import com.compete.util.IdWorker;
import com.compete.util.response.BookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);


    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    IdWorker idWorker;
    @Autowired
    SystemCache systemCache;
    @Autowired
    DateUtil dateUtil;

    public BookResponse login(String userid, String password) throws Exception {
        BookResponse bookResponse = new BookResponse("用户登录");
        password = DESUtil.encrypt(password);
        SysUser sysUser = sysUserDao.getSysUserByUserid(userid);
        if (sysUser == null) {
            return bookResponse.exce("未查询到此用户！请重新输入！");
        }
        if (!sysUser.getPassword().equals(password)) {
            return bookResponse.exce("密码输入错误！请重新输入！");
        }
        String token = String.valueOf(idWorker.nextId());
        String oldToken = systemCache.getLoginUserMap().get("loginUser" + userid);
        if (oldToken != null) {
            systemCache.getLoginUserMap().remove("loginUser" + userid);
            systemCache.getLoginInfoMap().remove("loginInfo" + oldToken);
        }
        Map<String, String> loginInfoMap = new HashMap<>();
        loginInfoMap.put("userid", sysUser.getUserid());
        loginInfoMap.put("username", sysUser.getUsername());
        loginInfoMap.put("logintime", dateUtil.getCurrentTime());
        loginInfoMap.put("workdate", dateUtil.getCurrentDate());
        systemCache.getLoginUserMap().put("loginUser" + userid, token);
        systemCache.getLoginInfoMap().put("loginInfo" + token, loginInfoMap);
        token = DESUtil.encrypt(token);

        bookResponse.setEntity("token", token);
        return bookResponse.success();
    }

    public BookResponse logout(String token) throws Exception {
        BookResponse bookResponse = new BookResponse("登出系统");
        String userid = systemCache.getLoginInfoMap().get("loginInfo" + token).get("userid");
        systemCache.getLoginUserMap().remove("loginUser" + userid);
        systemCache.getLoginInfoMap().remove("loginInfo" + token);
        return bookResponse.success();
    }

}
