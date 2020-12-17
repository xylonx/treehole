package com.xr.treehole.util;

import com.xr.treehole.constant.ErrorType;
import org.springframework.ui.Model;

import java.util.HashMap;

public class ErrorInfo {

    private final static String attrName = "errorType";

    public void SetErrorInfo(Model model, ErrorType errorType) {
        model.addAttribute(attrName, errorType.getStr());
    }
}

