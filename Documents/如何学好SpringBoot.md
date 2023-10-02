# 学习和使用SpringBoot的逻辑

框架的框架、底层基于Spring。能调整每一个场景的底层行为。100%项目一定会用到底层自定义

> 摄影：
> - 傻瓜：自动配置好。
> - 单反：焦距、光圈、快门、感光度....
> - 傻瓜+单反：

## 学习SpringBoot的逻辑
1. 理解自动配置原理
   a. 导入starter --> 生效xxxxAutoConfiguration --> 组件 --> xxxProperties --> 配置文件
2. 理解其他框架底层
   a. 拦截器
3. 可以随时定制化任何组件
   a. 配置文件
   b. 自定义组件

普通开发：导入starter，Controller、Service、Mapper、偶尔修改配置文件
高级开发：自定义组件、自定义配置、自定义starter
核心：
- 这个场景自动配置导入了哪些组件，我们能不能@Autowired进来使用
- 能不能通过修改配置改变组件的一些默认参数
- 需不需要自己完全定义这个组件
- 场景定制化

## 使用SpringBoot的逻辑（最佳实战）

-  选场景，导入到项目: 
  1. 官方：starter 
  2. 第三方：去maven仓库网站搜
- 写配置，改配置文件关键项
  - 例如数据库参数（连接地址、账号密码...）
- 分析这个场景给我们导入了哪些能用的组件
  - 自动装配这些组件进行后续使用
  - 如果不满意boot提供的自动配好的默认组件（已经有默认设置的组件）
    - 定制化
      - 在配置文件里面改默认配置的值
      - 自定义组件（一般组件都含有一个@ConditionalMissingBean注解，所以如果自己在自己的业务代码中重新写这个组件就相当于覆盖了默认的组件）
> 最佳实战例子（整合redis）：
> 1. 选场景，导入starter：`spring-boot-starter-data-redis`
>   - 场景名+AutoConfiguration：`RedisAutoConfiguration`就是这个场景的自动配置类
> 2. 写配置：
>    - 分析这个场景自动配置类中开启了哪些属性绑定关系（一般有一个属性类XXXProperties) --> @EnableConfigurationProperties(RedisProperties.class)
>    - 修改配置文件中的redis的关键属性
> 3. 分析这个场景给我们导入了哪些能用的组件
>    - 分析到 `RedisAutoConfiguration`  给容器中放了 `StringRedisTemplate`
>    - 给业务代码中自动装配 `StringRedisTemplate`
> 4. 定制化
>    - 修改配置文件
>    - 自定义组件，例如自己给容器中放一个 `StringRedisTemplate`