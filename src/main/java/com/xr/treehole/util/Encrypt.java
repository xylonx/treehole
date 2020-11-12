package com.xr.treehole.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Encrypt {

    public static String Hashing(String origin){
        return DigestUtils.sha256Hex(origin);
    }

    public static boolean checkPassword(String origin, String hashed){
        return DigestUtils.sha256Hex(origin).equals(hashed);
    }

}
