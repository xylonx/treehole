package com.xr.treehole.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Encrypt {

    // for email hashing
    public static String Hashing(String origin){
        return DigestUtils.sha256Hex(origin);
    }

    // for password hashing
    public static String SecurityHash(String origin){
        return BCrypt.hashpw(origin, BCrypt.gensalt());
    }

    // for checking hashed password
    public static boolean CheckSecurityHash(String origin, String hashed){
        return BCrypt.checkpw(origin, hashed);
    }

}
