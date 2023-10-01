package top.calvinhaynes.demo02ioc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo02IocApplication {

    public static void main(String[] args) {
        var ioc = SpringApplication.run(Demo02IocApplication.class, args);

        //打印所有注册的组件bean
        for (String beanDefinitionName : ioc.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        //测试是否为单实例
//        var cat01 = ioc.getBean("maomao");
//        var cat02 = ioc.getBean("maomao");
//        System.out.println(cat01 == cat02); //false
//
//        var mouse01 = ioc.getBean("shushu");
//        var mouse02 = ioc.getBean("shushu");
//        System.out.println(mouse01 == mouse02); //true

    }

}
