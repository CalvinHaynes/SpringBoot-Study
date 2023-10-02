# 属性绑定

> 所谓属性绑定，就是可以通过外部的配置文件设置Bean的属性值

1. @ConfigurationProperties： 声明组件的属性和配置文件哪些前缀开始项进行绑定
2. @EnableConfigurationProperties：快速注册注解
   - 场景：SpringBoot默认只扫描自己主程序所在的包。如果导入第三方包，即使组件上标注了 @Component、@ConfigurationProperties 注解，也没用。因为组件都扫描不进来，此时使用这个注解就可以快速进行属性绑定并把组件注册进容器
   - 上述的意思就是：@EnableConfigurationProperties 相当于把使用 @ConfigurationProperties 的类自动注册进了容器中，
   否则通常我们还需要使用@Component或者@Bean来注册进容器中。其中@Component标注的额类需要在主程序所在的包下，否则也不会被扫描进来，
   而@Bean在配置类中使用，放在方法前面，这样就可以把这个方法的返回值注册进容器中了，对于第三方包也可以使用这种方式进行注册。
   所以通常选择使用配置类加@Bean的方式注册组件是更灵活的选择（过去在Spring中是使用XML配置文件实现的）

> 将容器中任意组件（Bean）的属性值和配置文件的配置项的值进行绑定 
> 1. 给容器中注册组件（@Component、@Bean） 
> 2. 使用@ConfigurationProperties 声明组件和配置文件的哪些配置项进行绑定