package com.compete.dao;

import com.compete.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserDao {

    SysUser getSysUserByUserid(@Param("userid") String userid);

}
