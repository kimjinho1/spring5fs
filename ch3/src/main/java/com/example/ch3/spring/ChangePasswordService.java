package com.example.ch3.spring;

// MemberRegisterService 객체와 마찬가지로 MemberDao 객체를 의존 주입 받는다.
// 이 객체는 setter로 받았다.
public class ChangePasswordService {

    private MemberDao memberDao;

    public void changePassword(String email, String oldPwd, String newPwd) throws MemberNotFoundException, WrongIdPasswordException {
        Member member = memberDao.selectByEmail(email);

        if (member == null) {
            throw new MemberNotFoundException();
        }

        member.changePassword(oldPwd, newPwd);

        memberDao.update(member);
    }

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
}
