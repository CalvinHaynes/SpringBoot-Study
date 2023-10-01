## IOC容器的组件注册（Spring Boot 3）

### 组件注册

#### 常用注解：
> @Configuration、@SpringBootConfiguration
> @Bean、@Scope
> @Controller、 @Service、@Repository、@Component
> @Import
> @ComponentScan

#### 步骤：
1. @Configuration 编写一个配置类
2. 在配置类中，自定义方法给容器中注册组件，配合注解@Bean
3. 使用@Import注解导入第三方组件（包括自定义组件）

### Tips

在Spring中，Bean可以配置为单例（Singleton）或多例（Prototype），它们有以下主要区别：

1. **单例（Singleton）Bean**：
    - 单例Bean在Spring容器中只有一个实例。
    - 当你第一次请求单例Bean时，Spring容器会创建该Bean的实例，并在容器的生命周期内保持这个实例，以供后续请求使用。
    - 单例Bean适用于那些状态无关的共享组件，例如服务层、数据访问层、缓存管理等，因为它们可以在多个地方重用相同的实例，减少了内存开销。

2. **多例（Prototype）Bean**：
    - 多例Bean每次请求时都会创建一个新的实例。
    - 每次你请求一个多例Bean，Spring都会创建一个新的实例，并将其交给你使用，而不会重用已存在的实例。
    - 多例Bean适用于那些具有状态的组件，例如Web层中的控制器，因为每个请求需要一个独立的实例来处理请求，避免了状态混乱。

总结区别：
- 单例Bean通常用于那些在应用程序中共享的组件，Spring容器会维护一个实例，它们是默认的作用域。
- 多例Bean适用于那些需要独立实例的组件，每次请求都会创建一个新的实例，你需要显式指定它们的作用域为`prototype`。

你可以通过在Spring配置文件或使用注解时设置`@Scope("singleton")`或`@Scope("prototype")`来定义Bean的作用域。例如：

```java
@Component
@Scope("singleton") // 或 @Scope("prototype")，根据需求选择
public class MySingletonOrPrototypeBean {
    // ...
}
```

需要注意的是，单例Bean应该是线程安全的，因为它们可能在多个线程中共享。多例Bean通常不需要特别注意线程安全，因为每个实例都是独立的。