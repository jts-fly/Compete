package com.compete.dto;

import org.springframework.stereotype.Component;

@Component
public class CustInfoDTO {

    private String custid;
    private String custname;

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }
}
