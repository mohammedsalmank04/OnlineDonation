package com.practice.onlinedonation;

import com.practice.onlinedonation.test.authControllerTest.TestMailConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.mail.autoconfigure.MailSenderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestMailConfig.class)


@EnableAutoConfiguration(exclude = MailSenderAutoConfiguration.class)
class OnlineDonationApplicationTests {




    @Test
    void contextLoads() {
    }

}
