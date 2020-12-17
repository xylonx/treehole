package com.xr.treehole;

import com.xr.treehole.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TreeholeApplicationTests {

    @Autowired
    private MailService mailService;

    @Test
    void contextLoads() {
    }

    @Test
    void SendTemplateMail(){
        mailService.SendTemplateMail("u201917285@hust.edu.cn", "Bot", "123456");
    }

}
