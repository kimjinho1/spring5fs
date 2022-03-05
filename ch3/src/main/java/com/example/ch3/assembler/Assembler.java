package com.example.ch3.assembler;

import com.example.ch3.spring.ChangePasswordService;
import com.example.ch3.spring.MemberDao;
import com.example.ch3.spring.MemberRegisterService;

// 객체 조립기(Aseembler) : 객체를 생성하고 의존 객체를 주입하는 기능을 제공한다
// 의존 객체를 주입한다는 것은 서로 다른 두 객체를 조립한다고 볼 수 있기에 참 적절한 이름이다.

// 코드를 보면 의존 주입의 편리함이 느껴지는 게, memberDao가 아닌 다른 객체를 사용해야 되는 경우
// 생성자 안에 있는 memberDao = new ???Dao(); 코드 부분만 수정하면 된다.
public class Assembler {

    private MemberDao memberDao;
    private MemberRegisterService regSvc;
    private ChangePasswordService pwdSvc;

    public Assembler() {
        memberDao = new MemberDao();
        regSvc = new MemberRegisterService(memberDao);
        pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao);
    }

    public MemberDao getMemberDao() {
        return memberDao;
    }

    public MemberRegisterService getMemberRegisterService() {
        return regSvc;
    }

    public ChangePasswordService getChangePasswordService() {
        return pwdSvc;
    }
}
