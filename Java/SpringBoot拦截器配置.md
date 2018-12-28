##继承WebMvcConfigurationSupport后自动配置不生效的问题及如何配置拦截器

二、继承WebMvcConfigurationSupport类如何配置拦截器的
@Configuration
public class MyConfigurer extends WebMvcConfigurationSupport {
```java
@Override
    protected void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/emp/toLogin","/emp/login","/js/**","/css/**","/images/**");
        super.addInterceptors(registry);
    }
 
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
```

注意这段代码：
```java
registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
```

由于继承WebMvcConfigurationSupport后会导致自动配置失效，所以这里要指定默认的静态资源的位置。同时要注意不能写成

```java
registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");\;
```
