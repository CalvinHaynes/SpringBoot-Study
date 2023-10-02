# 自动配置机制

## 初步理解

- 自动配置的 Tomcat. SpringMVC 等 
  - 导入场景，容器中就会自动配置好这个场景的核心组件。 
  - 以前：DispatcherServlet. ViewResolver. CharacterEncodingFilter.... 
  - 现在：自动配置好的这些组件
  - 验证：容器中有了什么组件，就具有什么功能
```java
  public static void main(String[] args) {

    //java10： 局部变量类型的自动推断
    var ioc = SpringApplication.run(MainApplication.class, args);

    //1. 获取容器中所有组件的名字
    String[] names = ioc.getBeanDefinitionNames();
    //2. 挨个遍历：
    // dispatcherServlet. beanNameViewResolver. characterEncodingFilter. multipartResolver
    // SpringBoot把以前配置的核心组件现在都给我们自动配置好了。
    for (String name : names) {
        System.out.println(name);
    }

}
```
- 默认的包扫描规则
  - @SpringBootApplication 标注的类就是主程序类
  - SpringBoot只会扫描主程序所在的包及其下面的子包，自动的component-scan功能
  - 自定义扫描路径
    - @SpringBootApplication(scanBasePackages = "com.atguigu")
    - @ComponentScan("com.atguigu") 直接指定扫描的路径
- 配置默认值
  - 配置文件的所有配置项是和某个类的对象值进行一一绑定的。
  - 绑定了配置文件中每一项值的类： 属性类。
  - 比如：
    - ServerProperties绑定了所有Tomcat服务器有关的配置
    - MultipartProperties绑定了所有文件上传相关的配置
    - ....参照官方文档：或者参照 绑定的  属性类。
- 按需加载自动配置
  - 导入场景spring-boot-starter-web
  - 场景启动器除了会导入相关功能依赖，导入一个spring-boot-starter，是所有starter的starter，基础核心starter
  - spring-boot-starter导入了一个包 spring-boot-autoconfigure。包里面都是各种场景的AutoConfiguration自动配置类
  - 虽然全场景的自动配置都在 spring-boot-autoconfigure这个包，但是不是全都开启的。
    - 导入哪个场景就开启哪个自动配置

总结： 导入场景启动器. 触发 spring-boot-autoconfigure这个包的自动配置生效. 容器中就会具有相关场景的功能

## 完整流程
>思考：
> 1. SpringBoot怎么实现导一个starter. 写一些简单配置，应用就能跑起来，我们无需关心整合
> 2. 为什么Tomcat的端口号可以配置在application.properties中，并且Tomcat能启动成功？
> 3. 导入场景后哪些自动配置能生效？

![自动配置完整流程图](https://xf233.oss-cn-hangzhou.aliyuncs.com/CalvinHaynesBlogImageSpringBoot%E8%87%AA%E5%8A%A8%E9%85%8D%E7%BD%AE%E5%8E%9F%E7%90%86.png)

自动配置流程细节梳理：
1. 导入starter-web：导入了web开发场景
   1. 场景启动器导入了相关场景的所有依赖：starter-json. starter-tomcat. springmvc
   2. 每个场景启动器都引入了一个spring-boot-starter，核心场景启动器。
   3. 核心场景启动器引入了spring-boot-autoconfigure包。
   4. spring-boot-autoconfigure里面囊括了所有场景的所有配置。
   5. 只要这个包下的所有类都能生效，那么相当于SpringBoot官方写好的整合功能就生效了。
   6. SpringBoot默认却扫描不到 spring-boot-autoconfigure下写好的所有配置类。（这些配置类给我们做了整合操作），默认只扫描主程序所在的包。
2. 主程序：@SpringBootApplication
   1. @SpringBootApplication由三个注解组成@SpringBootConfiguration. @EnableAutoConfiguratio. @ComponentScan
   2. SpringBoot默认只能扫描自己主程序所在的包及其下面的子包，扫描不到 spring-boot-autoconfigure包中官方写好的配置类
   3. @EnableAutoConfiguration：SpringBoot 开启自动配置的核心。
      1. 是由@Import(AutoConfigurationImportSelector.class)提供功能：批量给容器中导入组件。
      2. SpringBoot启动会默认加载 142个配置类。
      3. 这142个配置类来自于spring-boot-autoconfigure下 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports文件指定的
      项目启动的时候利用 @Import 批量导入组件机制把 autoconfigure 包下的142 xxxxAutoConfiguration类导入进来（自动配置类）
       虽然导入了142个自动配置类
      4. 按需生效：
         - 并不是这142个自动配置类都能生效
         - 每一个自动配置类，都有条件注解@ConditionalOnxxx，只有条件成立，才能生效 
3. xxxxAutoConfiguration自动配置类
   1. 给容器中使用@Bean 放一堆组件。
   2. 每个自动配置类都可能有这个注解@EnableConfigurationProperties(ServerProperties.class)，用来把配置文件中配的指定前缀的属性值封装到 xxxProperties属性类中
   3. 以Tomcat为例：把服务器的所有配置都是以server开头的。配置都封装到了属性类中。
   4. 给容器中放的所有组件的一些核心参数，都来自于xxxProperties。xxxProperties都是和配置文件绑定。
     只需要改配置文件的值，核心组件的底层参数都能修改
4. 写业务，全程无需关心各种整合（底层这些整合写好了，而且也生效了）

核心流程总结：
1. 导入starter，就会导入autoconfigure包。
2. autoconfigure 包里面 有一个文件 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports,里面指定的所有启动要加载的自动配置类
3. @EnableAutoConfiguration 会自动的把上面文件里面写的所有自动配置类都导入进来。xxxAutoConfiguration 是有条件注解进行按需加载
4. xxxAutoConfiguration给容器中导入一堆组件，组件都是从 xxxProperties中提取属性值
5. xxxProperties又是和配置文件进行了绑定

效果：导入starter. 修改配置文件，就能修改底层行为。