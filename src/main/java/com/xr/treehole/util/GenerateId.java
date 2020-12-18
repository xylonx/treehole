package com.xr.treehole.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.util.ArrayList;

public class GenerateId {

    private static String[] readableNames = {
        "小红","小明","小强","小壮","小白","小黑","小蓝","小紫","小圆","小方","小芳","小丽","小李","小莉",
        "玉芬","马大帅","范德彪","马小翠","小云","小唐","钢子","吴总","王老板","桂英","伍子",
        "克莱恩","奥黛丽","阿尔杰","戴里克","埃姆林","嘉德丽雅","伦纳德","洛薇雅","贝尔纳黛","查尼斯","弗雷格拉","阿蒙","亚当","奥赛库斯","赫拉伯根","列奥德罗","阿曼妮西斯",
        "阿尔萨斯","萨尔","瓦里安","伯瓦尔","弗丁","奥妮克希亚","奈法里奥","伊利丹","加尔鲁什","吉安娜"
    };

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

    public static String GenerateRandomId(int length, boolean useLetters, boolean useNumbers) {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
    
    public static String GenerateNickname() {
        return readableNames[(int)Math.floor(Math.random()*(readableNames.length))] + GenerateRandomId(4, true, true);
    }
}
