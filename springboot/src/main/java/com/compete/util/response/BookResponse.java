package com.compete.util.response;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class BookResponse implements Serializable {

    private String code;
    private String desc;
    private Map<String, Object> result = new HashMap<>();

    public BookResponse() {
        setCode(BookRespEnum.CODE_FAIL.getCode());
    }

    public BookResponse(String desc) {
        setCode(BookRespEnum.CODE_FAIL.getCode());
        setDesc(desc + BookRespEnum.CODE_FAIL.getDesc());
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BookResponse setTokenFail() {
        setCode(BookRespEnum.CODE_ILLEGALl.getCode());
        setDesc(BookRespEnum.CODE_ILLEGALl.getDesc());
        return this;
    }

    public void setEntity(String key, Object value) {
        result.put(key, value);
    }

    public BookResponse success() {
        setCode(BookRespEnum.CODE_SUCCESS.getCode());
        setDesc(BookRespEnum.CODE_SUCCESS.getDesc());
        return this;
    }

    public BookResponse fail() {
        setCode(BookRespEnum.CODE_FAIL.getCode());
        setDesc(BookRespEnum.CODE_FAIL.getDesc());
        return this;
    }

    public BookResponse exce(String desc) {
        setCode(BookRespEnum.CODE_EXCE.getCode());
        setDesc(desc);
        return this;
    }

    public String toString() {
        return "code:[" + code + "] desc:[" + desc + "] result:[" + result.toString() + "]";
    }

}





