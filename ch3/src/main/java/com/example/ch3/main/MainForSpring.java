package com.example.ch3.main;

import com.example.ch3.config.AppCtx;
import com.example.ch3.spring.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// MainForAssembler와 차이점은 Assembler 클래스 대신 스프링 컨테이너인 ApplicationContext를 사용했다는 것 뿐이다.
// -> 단순 객체 생성에서 getBean을 사용해서 클래스를 생성하는 방식으로 변경됨

// 여기서 2장에서 말했던 Bean 객체들이 싱글톤 범위를 가지는 이유에 대해 한 번 생각해보자
// 우리가 구현한 memberRegSvc() 메서드와 changePwdSvc() 메서드는 둘 다 memberDao() 메서드를 실행하고 있다.
// 그리고 memberDao() 메서드는 매번 새로운 MemberDao 객체를 생성해서 리턴한다.
// 그럼 memberRegSvc, changePwdSvc에서 각각 생성되는 MemberDao는 서로 다른 객체인걸까?
// 정말 다른 객체를 사용한다면 MainForSpring에서 new 명령어로 등록한 회원 정보를 저장할 때 사용하는 MemberDao와
// change 명령어로 수정할 회원 정보를 찾을 때 사용하는 MemberDao는 다른 객체인데, 이게 왜 문제없이 잘 돌아가는걸까

// 답은 스프링이 런타임에 생성한 설정 클래스의 memberDao() 메서드는 매번 새로운 객체를 생성하지 않기 때문이다.
// 대신 한 번 생성한 객체를 보관했다가 이후에는 동일한 객체를 리턴한다.
// 그렇기에 memberRegSvc, changePwdSvc에서 사용하는 MemberDao 객체는 같은 객체이다.
// 따라서 Bean 객체는 싱글톤 범위를 가지고 있다.
public class MainForSpring {

    private static ApplicationContext ctx = null;

    public static void main(String[] args) throws IOException {
        ctx = new AnnotationConfigApplicationContext(AppCtx.class);

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("명령어를 입력하세요:");
            String command = reader.readLine();
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("종료합니다.");
                break;
            }
            if (command.startsWith("new ")) {
                processNewCommand(command.split(" "));
                continue;
            } else if (command.startsWith("change ")) {
                processChangeCommand(command.split(" "));
                continue;
            } else if (command.equals("list")) {
                processListCommand();
                continue;
            } else if (command.startsWith("info ")) {
                processInfoCommand(command.split(" "));
                continue;
            } else if (command.equals("version")) {
                processVersionCommand();
                continue;
            }
            printHelp();
        }
    }

    private static void processNewCommand(String[] arg) {
        if (arg.length != 5) {
            printHelp();
            return;
        }
        MemberRegisterService regSvc =
                ctx.getBean("memberRegSvc", MemberRegisterService.class);
        RegisterRequest req = new RegisterRequest();
        req.setEmail(arg[1]);
        req.setName(arg[2]);
        req.setPassword(arg[3]);
        req.setConfirmPassword(arg[4]);

        if (!req.isPasswordEqualToConfirmPassword()) {
            System.out.println("암호와 확인이 일치하지 않습니다.\n");
            return;
        }
        try {
            regSvc.regist(req);
            System.out.println("등록했습니다.\n");
        } catch (DuplicateMemberException e) {
            System.out.println("이미 존재하는 이메일입니다.\n");
        }
    }

    private static void processChangeCommand(String[] arg) {
        if (arg.length != 4) {
            printHelp();
            return;
        }
        ChangePasswordService changePwdSvc =
                ctx.getBean("changePwdSvc", ChangePasswordService.class);
        try {
            changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
            System.out.println("암호를 변경했습니다.\n");
        } catch (MemberNotFoundException e) {
            System.out.println("존재하지 않는 이메일입니다.\n");
        } catch (WrongIdPasswordException e) {
            System.out.println("이메일과 암호가 일치하지 않습니다.\n");
        }
    }

    private static void printHelp() {
        System.out.println();
        System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
        System.out.println("명령어 사용법:");
        System.out.println("new 이메일 이름 암호 암호확인");
        System.out.println("change 이메일 현재비번 변경비번");
        System.out.println();
    }

    private static void processListCommand() {
        MemberListPrinter listPrinter =
                ctx.getBean("listPrinter", MemberListPrinter.class);
        listPrinter.printAll();
    }

    private static void processInfoCommand(String[] arg) {
        if (arg.length != 2) {
            printHelp();
            return;
        }
        MemberInfoPrinter infoPrinter =
                ctx.getBean("infoPrinter", MemberInfoPrinter.class);
        infoPrinter.printMemberInfo(arg[1]);
    }

    private static void processVersionCommand() {
        VersionPrinter versionPrinter =
                ctx.getBean("versionPrinter", VersionPrinter.class);
        versionPrinter.print();
    }
}
