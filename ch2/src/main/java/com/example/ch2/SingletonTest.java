package com.example.ch2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// g1 == g2의 결과가 true라는 것은 g1과 g2가 같은 객체라는 것을 의미한다.
// 즉 getBean() 메서드는 같은 객체를 리턴한다는 것이다.
// 별도 설정을 하지 않을 경우 스프링은 한 개의 Bean 객체만을 생성하며,
// 이 때 Bean 객체는 싱글톤(singleton) 범위를 가진다.
// 이 이유는 다음 3장에서 쉽게 이해할 수 있다.
public class SingletonTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);
        Greeter g1 = ctx.getBean("greeter", Greeter.class);
        Greeter g2 = ctx.getBean("greeter", Greeter.class);
        System.out.println("(g1 == g2) = " + (g1 == g2));
        ctx.close();
    }
}
