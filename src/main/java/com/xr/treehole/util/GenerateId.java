package com.xr.treehole.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.time.Instant;

public class GenerateId {

    public static String GenerateIdWithTime(String content, String authorId) {

        String str = content + authorId;
        long now = Instant.now().getEpochSecond();

        return DigestUtils.md2Hex(str + now);

    }

    public static String GenerateIdByItems(String... items) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String item : items) {
            stringBuilder.append(item);
        }

        return DigestUtils.md5Hex(stringBuilder.toString());
    }

}
