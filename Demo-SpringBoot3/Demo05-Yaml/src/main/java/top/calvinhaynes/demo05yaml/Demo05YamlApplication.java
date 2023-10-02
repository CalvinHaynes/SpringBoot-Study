package top.calvinhaynes.demo05yaml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.calvinhaynes.demo05yaml.pojo.Person;

@SpringBootApplication
public class Demo05YamlApplication {

    public static void main(String[] args) {
        var ioc = SpringApplication.run(Demo05YamlApplication.class, args);

        Person person = ioc.getBean(Person.class);
        System.out.println(person);
    }

}
