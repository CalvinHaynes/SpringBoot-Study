package top.calvinhaynes.demo02ioc.config;

import com.alibaba.druid.sql.SQLUtils;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import top.calvinhaynes.demo02ioc.pojo.Cat;
import top.calvinhaynes.demo02ioc.pojo.Mouse;

@Import(SQLUtils.class)
@SpringBootConfiguration    //标记这是一个配置类，相当于Spring的XML配置文件
public class AppConfig {

    @Scope("prototype") //设置为多实例
    @Bean("maomao")    //注册组件的注解，参数为组件的名字，默认为方法名，相当于Spring的bean标签
    public Cat tom(){
        Cat cat = new Cat();
        cat.setName("Tom");
        cat.setAge(3);
        return cat;
    }

    @Scope //默认为单实例
    @Bean("shushu")    //注册组件的注解，参数为组件的名字，默认为方法名，相当于Spring的bean标签
    public Mouse jerry(){
        Mouse mouse = new Mouse();
        mouse.setName("Jerry");
        mouse.setAge(3);
        return mouse;
    }

    //注册外部组件的一般方法如下
//    @Bean    //注册组件的注解，参数为组件的名字，默认为方法名，相当于Spring的bean标签
//    public SQLUtils sqlUtils(){
//        return new SQLUtils();
//    }
}
