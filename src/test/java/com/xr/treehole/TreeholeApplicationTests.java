package com.xr.treehole;

import com.xr.treehole.middleware.jwt.JwtUtils;
import com.xr.treehole.service.MailService;
import com.xr.treehole.util.Encrypt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TreeholeApplicationTests {

    @Autowired
    private MailService mailService;

    @Autowired
//    private JwtUtils    jwtUtils;

    @Test
    void contextLoads() {
        assert Encrypt.CheckSecurityHash("15948", "$2a$10$7s1M4SU6pS1HzLYy3HIzV.XPbZIBqwJqmsk.vGAbPZ31j.bSulXWa");
        assert Encrypt.Hashing("3102131813@qq.com").equals("88346234510054dd998fdff07f5e389a0af11912720692bff87eac75c1115a23");
    }
//
//    @Test
//    void SendTemplateMail(){
//        mailService.SendTemplateMail("u201917285@hust.edu.cn", "Bot", "123456");
//    }

}
