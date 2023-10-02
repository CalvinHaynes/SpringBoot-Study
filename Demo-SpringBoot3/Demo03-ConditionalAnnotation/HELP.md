# 条件注解学习

> 如果注解指定的条件成立，则触发指定行为，结构为`@ConditionalOnXxx`
1. @ConditionalOnClass：如果类路径中存在这个类，则触发指定行为
2. @ConditionalOnMissingClass：如果类路径中不存在这个类，则触发指定行为
3. @ConditionalOnBean：如果容器中存在这个Bean（组件），则触发指定行为
4. @ConditionalOnMissingBean：如果容器中不存在这个Bean（组件），则触发指定行为
5. @ConditionalOnBean（value=组件类型，name=组件名字）：判断容器中是否有这个类型的组件，并且名字是指定的值

> 使用场景测试：
> - 如果存在Druid库中的`DruidDataSource`类（即导入了Druid的jar包），则给IOC容器中注册一个`cat`组件，名字为`tom`
> - 否则，就给容器中放一个`Mouse`组件，名为`jerry`
> - 如果系统中有一个`tom`组件，就给容器放一个`User`组件，名为`lucy`
> - 否则，就给容器放一个`User`组件，名为`jack`

> 结果：
> 1. 导入了Druid的jar包，注册的组件有：`tom`、`lucy`
> 2. 没有导入Druid的jar包，注册的组件有：`jerry`、`jack`