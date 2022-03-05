package com.example.ch2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// 스프링의 핵심 기능은 객체를 생성하고 초기화 하는 것이다.
// 스프링은 객체 컨테이너이다 -> 스프링 컨테이너는 내부적으로 Bean 객체와 Bean 이름을 연결하는 정보를 갖는다.
// 이름과 실제 객체의 관계뿐만 아니라 실제 객체의 생성, 초기화, 의존 주입 등등 객체 관리를 위한 다양한 기능을 제공한다.
public class Main {

    public static void main(String[] args) {
//      AnnotationConfigApplicationContext 클래스는 자바 설정에서 정보를 읽어와 Bean 객체를 생성하고 관리한다
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);
//      getBean() 메서드 -> Bean 객체를 검색할 때 사용됨
        Greeter g = ctx.getBean("greeter", Greeter.class);
        String msg = g.greet("스프링");
        System.out.println(msg);
        ctx.close();
    }
}
