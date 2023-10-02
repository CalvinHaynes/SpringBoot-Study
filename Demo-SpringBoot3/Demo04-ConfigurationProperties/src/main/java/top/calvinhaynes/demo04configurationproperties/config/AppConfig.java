package top.calvinhaynes.demo04configurationproperties.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import top.calvinhaynes.demo04configurationproperties.pojo.Mouse;
import top.calvinhaynes.demo04configurationproperties.pojo.User;

@SpringBootConfiguration
@EnableConfigurationProperties(User.class)
public class AppConfig {

    //用@Bean注册组件，同时使用@ConfigurationProperties注解，将配置文件中的属性值注入到组件中
    @Bean
    @ConfigurationProperties(prefix = "mouse")
    public Mouse mouse(){
        return new Mouse();
    }
}
