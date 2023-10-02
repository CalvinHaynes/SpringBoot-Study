package top.calvinhaynes.demo04configurationproperties.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//注册组件的方法一：@Component注解
@Component
@ConfigurationProperties(prefix = "cat")   // 声明组件的属性和配置文件哪些前缀开始项进行绑定
public class Cat {
    private String name;
    private int age;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
