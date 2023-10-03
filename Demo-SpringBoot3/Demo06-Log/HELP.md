## SpringBoot中的日志配置

> 有了日志之后，项目开发就不要用System.out.println()了。

![日志框架关系](https://xf233.oss-cn-hangzhou.aliyuncs.com/CalvinHaynesBlogImage%E6%97%A5%E5%BF%97%E6%A1%86%E6%9E%B6%E5%85%B3%E7%B3%BB.png)

## Spring使用日志的简介

1. Spring使用`commons-logging`作为内部日志，但底层日志实现是开放的。可对接其他日志框架。
   a. spring5及以后 `commons-logging`被spring直接自己写了。
2. 支持 `jul，log4j2,logback`。SpringBoot 提供了默认的控制台输出配置，也可以配置输出为文件。
3. `logback`是默认使用的。
4. 虽然日志框架很多，但是我们不用担心，使用 SpringBoot 的默认配置就能工作的很好。

## SpringBoot怎么把日志默认配置好的

1、每个starter场景，都会导入一个核心场景`spring-boot-starter`
2、核心场景引入了日志的所用功能`spring-boot-starter-logging`
3、默认使用了`logback + slf4j` 组合作为默认底层日志
4、日志是系统一启动就要用，`xxxAutoConfiguration`是系统启动好了以后放好的组件，后来用的。
5、日志是利用监听器机制配置好的。`ApplicationListener`。
6、日志所有的配置都可以通过修改配置文件实现。以`logging`前缀开始的所有配置。


## 日志格式

默认日志格式如下：
```shell
2023-10-03T21:34:37.275+08:00  INFO 22612 --- [           main] t.c.demo06log.Demo06LogApplication       : Starting Demo06LogApplication using Java 17.0.2 with PID 22612 (D:\OpenSource\MyRepository\MyRepositoryInGithub\Spring_Boot_Application\Demo-SpringBoot3\Demo06-Log\target\classes started by Administrator in D:\OpenSource\MyRepository\MyRepositoryInGithub\Spring_Boot_Application)
2023-10-03T21:34:37.278+08:00  INFO 22612 --- [           main] t.c.demo06log.Demo06LogApplication       : No active profile set, falling back to 1 default profile: "default"
2023-10-03T21:34:38.265+08:00  INFO 22612 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
```
默认输出格式：
- 时间和日期：毫秒级精度
- 日志级别：`ERROR, WARN, INFO, DEBUG, or TRACE`.
- 进程 ID
- ---： 消息分割符
- 线程名： 使用[]包含
- `Logger` 名： 通常是产生日志的类名
- 消息： 日志记录的内容
> 注意： `logback` 没有`FATAL`级别，对应的是ERROR
> 
> - 默认值：参照：`spring-boot`包`additional-spring-configuration-metadata.json`文件 
> - 默认输出格式值：`%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd'T'HH:mm:ss.SSSXXX}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx} `
> - 可修改为：`%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{15} ===> %msg%n`

## 记录日志
```java
Logger logger = LoggerFactory.getLogger(getClass());

//或者使用Lombok的@Slf4j注解
```

## 日志级别
- 由低到高：ALL,TRACE, DEBUG, INFO, WARN, ERROR,FATAL,OFF；
  - **只会打印指定级别及以上级别的日志**
  - ALL：打印所有日志
  - TRACE：追踪框架详细流程日志，一般不使用
  - DEBUG：开发调试细节日志
  - INFO：关键、感兴趣信息日志
  - WARN：警告但不是错误的信息日志，比如：版本过时
  - ERROR：业务错误日志，比如出现各种异常
  - FATAL：致命错误日志，比如jvm系统崩溃
  - OFF：关闭所有日志记录
- 不指定级别的所有类，都使用root指定的级别作为默认级别
- SpringBoot日志默认级别是 INFO

1. 在application.properties/yaml中配置logging.level.<logger-name>=<level>指定日志级别
2. level可取值范围：TRACE, DEBUG, INFO, WARN, ERROR, FATAL, or OFF，定义在 LogLevel类中
3. root 的logger-name叫root，可以配置logging.level.root=warn，代表所有未指定日志级别都使用 root 的 warn 级别

## 日志分组
比较有用的技巧是：
将相关的logger分组在一起，统一配置。SpringBoot 也支持。比如：Tomcat 相关的日志统一设置:
```java
logging.group.tomcat=org.apache.catalina,org.apache.coyote,org.apache.tomcat
logging.level.tomcat=trace
```
SpringBoot 预定义两个组:
- web：`org.springframework.core.codec, org.springframework.http, org.springframework.web, org.springframework.boot.actuate.endpoint.web, org.springframework.boot.web.servlet.ServletContextInitializerBeans`
- sql：`org.springframework.jdbc, org.hibernate.SQL`

## 日志输出到文件
SpringBoot 默认只把日志写在控制台，如果想额外记录到文件，可以在application.properties中添加logging.file.name or logging.file.path配置项。
- logging.file.name：指定日志文件名，可以加路径, 且既可以是绝对路径，也可以是相对路径。如：`logging.file.name=my.log`(根目录), `logging.file.name=D:\\my.log`
- logging.file.path：指定日志文件所在目录，可以是绝对路径，也可以是相对路径。如：`logging.file.path=/var/log`
- 如果没有指定logging.file.name或者logging.file.path，SpringBoot会自动在当前目录下创建spring.log文件作为日志文件。

## 文件归档与滚动切割
> - 归档：每天的日志单独存到一个文档中。
> - 切割：每个文件10MB，超过大小切割成另外一个文件。
1. 每天的日志应该独立分割出来存档。如果使用logback（SpringBoot 默认整合），可以通过application.properties/yaml文件指定日志滚动规则。
2. 如果是其他日志系统，需要自行配置（添加log4j2.xml或log4j2-spring.xml）
3. 支持的滚动规则设置如下:
- logging.logback.rollingpolicy.file-name-pattern: 日志存档的文件名格式（默认值：${LOG_FILE}.%d{yyyy-MM-dd}.%i.gz）
- logging.logback.rollingpolicy.clean-history-on-start: 是否在启动时清除历史归档文件（默认值：false）
- logging.logback.rollingpolicy.max-file-size: 每个日志文件的最大大小（默认值：10MB）
- logging.logback.rollingpolicy.max-history: 保留的历史文件的最大数量（默认值：7）
- logging.logback.rollingpolicy.total-size-cap: 日志文件总大小的上限（默认值：0，表示无上限,如果设置1GB则磁盘存储超过1GB日志后就会删除旧日志文件）

## 自定义配置
通常我们配置 application.properties 就够了。当然也可以自定义。比如：
- 对于Logback日志系统, 可以在类路径下添加`logback-spring.xml`文件。
- 对于Log4j2日志系统, 可以在类路径下添加`log4j2-spring.xml`文件。
- 对于JDK Logging日志系统, 可以在类路径下添加`logging.properties`文件。
  
> 如果可能，我们建议您在日志配置中使用-spring 变量（例如，logback-spring.xml 而不是 logback.xml）。如果您使用标准配置文件，spring 无法完全控制日志初始化。
> - 最佳实战：自己要写配置，配置文件名加上 xx-spring.xml，这样就可以保证使用SpringBoot的默认配置，保证不会被覆盖。

## 切换日志组合
SpringBoot默认使用logback作为日志框架，如果想切换其他日志框架，可以排除logback依赖，再引入其他日志框架依赖即可。
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!--排除默认日志依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <!--排除默认日志依赖-->
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!--引入其他日志框架依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```


- Log4j 2 支持使用 YAML 和 JSON 格式的配置文件来配置日志记录器。这些格式提供了更灵活和可读性更好的配置选项，使得配置日志变得更加容易。
以下是使用 YAML 和 JSON 配置文件的示例：

### 使用 YAML 格式的配置文件：

```yaml
Configuration:
  status: warn
  appenders:
    Console:
      name: Console
      target: SYSTEM_OUT
      PatternLayout:
        pattern: "%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"
  loggers:
    - logger:
        name: com.example.myapp
        level: debug
      AppenderRef:
        - ref: Console
```

### 使用 JSON 格式的配置文件：

```json
{
  "Configuration": {
    "status": "warn",
    "appenders": {
      "Console": {
        "name": "Console",
        "target": "SYSTEM_OUT",
        "PatternLayout": {
          "pattern": "%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"
        }
      }
    },
    "loggers": [
      {
        "logger": "com.example.myapp",
        "level": "debug",
        "AppenderRef": [
          {
            "ref": "Console"
          }
        ]
      }
    ]
  }
}
```

这些示例配置文件中包括了常见的日志配置选项，你可以根据自己的需求进行修改和扩展。在使用 Log4j 2 时，你可以选择使用 YAML 或 JSON 配置文件来配置你的日志系统，以满足项目的需要。
## 最佳实战

1. 导入任何第三方框架，先排除它的日志包，因为Boot底层控制好了日志
2. 修改 application.properties 配置文件，就可以调整日志的所有行为。如果不够，可以编写日志框架自己的配置文件放在类路径下就行，比如logback-spring.xml，log4j2-spring.xml
3. 如需对接专业日志系统，也只需要把 logback 记录的日志灌倒 kafka之类的中间件，这和SpringBoot没关系，都是日志框架自己的配置，修改配置文件即可
4. 业务中使用slf4j-api记录日志。不要再 sout 了
