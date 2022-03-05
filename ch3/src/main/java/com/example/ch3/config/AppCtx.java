package com.example.ch3.config;

import com.example.ch3.spring.ChangePasswordService;
import com.example.ch3.spring.MemberDao;
import com.example.ch3.spring.MemberRegisterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppCtx {

    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }

    @Bean
    public MemberRegisterService memberRegisterService() {
        return new MemberRegisterService(memberDao());
    }

    @Bean
    public ChangePasswordService changePasswordService() {
        ChangePasswordService changePwdSvc = new ChangePasswordService();
        changePwdSvc.setMemberDao(memberDao());
        return changePwdSvc;
    }
}
