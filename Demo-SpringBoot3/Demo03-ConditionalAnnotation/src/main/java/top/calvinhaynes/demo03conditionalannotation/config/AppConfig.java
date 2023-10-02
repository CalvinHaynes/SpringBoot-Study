package top.calvinhaynes.demo03conditionalannotation.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import top.calvinhaynes.demo03conditionalannotation.pojo.Cat;
import top.calvinhaynes.demo03conditionalannotation.pojo.Mouse;
import top.calvinhaynes.demo03conditionalannotation.pojo.User;

@SpringBootConfiguration
public class AppConfig {
    @ConditionalOnClass(name = "com.alibaba.druid.pool.DruidDataSource")
    @Bean
    public Cat tom(){
        return new Cat();
    }
    @ConditionalOnMissingClass(value = "com.alibaba.druid.pool.DruidDataSource")
    @Bean
    public Mouse jerry(){
        return new Mouse();
    }
    @ConditionalOnBean(name = "tom")
    @Bean
    public User lucy(){
        return new User();
    }

    @ConditionalOnMissingBean(name = "tom")
    @Bean
    public User jack(){
        return new User();
    }
}
