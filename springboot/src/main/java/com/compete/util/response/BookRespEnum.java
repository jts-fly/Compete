package com.compete.util.response;

public enum BookRespEnum {

    CODE_SUCCESS("0000","执行成功!"),
    CODE_FAIL("9999","执行失败!"),
    CODE_EXCE("7777","执行异常!"),
    CODE_ILLEGALl("4444","非法请求！");

    private String code, desc;

    BookRespEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
