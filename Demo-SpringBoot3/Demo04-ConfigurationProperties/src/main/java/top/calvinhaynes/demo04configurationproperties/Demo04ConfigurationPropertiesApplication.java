package top.calvinhaynes.demo04configurationproperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.calvinhaynes.demo04configurationproperties.pojo.Cat;
import top.calvinhaynes.demo04configurationproperties.pojo.Mouse;
import top.calvinhaynes.demo04configurationproperties.pojo.User;

@SpringBootApplication
public class Demo04ConfigurationPropertiesApplication {

    public static void main(String[] args) {
        var ioc = SpringApplication.run(Demo04ConfigurationPropertiesApplication.class, args);

        Cat tom = ioc.getBean(Cat.class);
        System.out.println(tom);

        Mouse jerry = ioc.getBean(Mouse.class);
        System.out.println(jerry);

        User jack = ioc.getBean(User.class);
        System.out.println(jack);
    }

}
