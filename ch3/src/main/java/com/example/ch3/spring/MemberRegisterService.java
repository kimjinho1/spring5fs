package com.example.ch3.spring;

import java.time.LocalDateTime;

// 객체를 아래와 같이 생성해서 사용하지 말자
// private MemberDao memberDao = new MemberDao();
// 예를 들어 MemberDao 객체가 필요한 클래스가 세 개가 있다. 그런데 만약 MemberDao가 아니라 CachedMemberDao를
// 사용해야 되는 일이 생기게 되면 세 클래스 모두 동일하게 코드를 변경해야 하는데, 이건 너무 비효율적이다.

// DI(Dependency Injection, 의존 주입)를 통해 MemberDao 객체를 전달받는 방식을 통해 위 문제를 해결할 수 있다.
public class MemberRegisterService {

     private MemberDao memberDao;

     public MemberRegisterService(MemberDao memberDao) {
         this.memberDao = memberDao;
     }

    public Long regist(RegisterRequest req) {
        Member member = memberDao.selectByEmail(req.getEmail());

        if (member != null) {
            throw new DuplicateMemberException("dup email " + req.getEmail());
        }

        Member newMember = new Member(
                req.getEmail(), req.getPassword(), req.getName(), LocalDateTime.now()
        );

        memberDao.insert(newMember);
        return newMember.getId();
    }

}
