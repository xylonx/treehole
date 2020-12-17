package com.xr.treehole.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class RegisterCodeRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final static String keyPostfix = "RegisterCode_";

    private final static long ONE_MINUTE_IN_MILLIS = 60000;


    public void Save(String registerCode, Long expDuration) {
        String key = keyPostfix + registerCode;

        long now = Calendar.getInstance().getTimeInMillis();
        Date expTime = new Date(now + expDuration * ONE_MINUTE_IN_MILLIS);

        redisTemplate.opsForValue().
                set(key, registerCode);
    }

    public boolean FindRegisterCode(String registerCode) {
        String key = keyPostfix + registerCode;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            redisTemplate.delete(key);
            return true;
        }

        return false;
    }
}
