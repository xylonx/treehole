package com.xr.treehole.constant;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum  ErrorType {

    NOT_LOGIN("not login"),
    RE_REGISTER("re register");

    private String str;

    public String getStr(){
        return str;
    }

    public void setStr(String str){
        this.str = str;
    }

}
