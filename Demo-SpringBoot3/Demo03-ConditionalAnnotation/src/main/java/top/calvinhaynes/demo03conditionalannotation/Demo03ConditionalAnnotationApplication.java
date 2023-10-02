package top.calvinhaynes.demo03conditionalannotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.calvinhaynes.demo03conditionalannotation.pojo.Cat;
import top.calvinhaynes.demo03conditionalannotation.pojo.Mouse;
import top.calvinhaynes.demo03conditionalannotation.pojo.User;

@SpringBootApplication
public class Demo03ConditionalAnnotationApplication {

    public static void main(String[] args) {
        var ioc = SpringApplication.run(Demo03ConditionalAnnotationApplication.class, args);

        for (String s : ioc.getBeanNamesForType(Cat.class)) {
            System.out.println("cat:" + s);
        }

        for (String s : ioc.getBeanNamesForType(User.class)) {
            System.out.println("user:" + s);
        }

        for (String s : ioc.getBeanNamesForType(Mouse.class)) {
            System.out.println("mouse:" + s);
        }


    }

}
