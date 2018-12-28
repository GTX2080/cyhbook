此次主要记录springboot中实现拦截器的两种方式，分别为：实现HandlerInterceptor接口和使用servlet的filter拦截器。 
##一、实现HandlerInterceptor接口 
###1.创建一个拦截器实现HandlerInterceptor接口
```java
package wmq.fly.mybatis.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 预处理回调方法，实现处理器的预处理
     * 返回值：true表示继续流程；false表示流程中断，不会继续调用其他的拦截器或处理器
   */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("开始拦截.........");
        //业务代码
        return false;
    }

     /**
     * 后处理回调方法，实现处理器（controller）的后处理，但在渲染视图之前
     * 此时我们可以通过modelAndView对模型数据进行处理或对视图进行处理
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }
    /**
     * 整个请求处理完毕回调方法，即在视图渲染完毕时回调，
     * 如性能监控中我们可以在此记录结束时间并输出消耗时间，
     * 还可以进行一些资源清理，类似于try-catch-finally中的finally，
     * 但仅调用处理器执行链中
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }

}
```
###2 新建配置类来管理拦截器，并将之前的拦截器注入其中
```java
package wmq.fly.mybatis.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MvcInterceptorConfig extends WebMvcConfigurationSupport{
    //可以放个config包中

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则，/**表示拦截所有请求
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
        .excludePathPatterns("/stuInfo/getAllStuInfoA","/account/register");    
        super.addInterceptors(registry);
    }
}
```
##二、使用servlet的filter拦截器
```java
package wmq.fly.mybatis.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
@WebFilter(urlPatterns="/**",filterName="loginFilter")
public class LoginFilter implements Filter{

    //排除不拦截的url
    private static final String[] excludePathPatterns = { "/stuInfo/getAllStuInfoA"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        // 获取请求url地址，不拦截excludePathPatterns中的url
        String url = req.getRequestURI();
        if (Arrays.asList(excludePathPatterns).contains(url)) {
            //放行，相当于第一种方法中LoginInterceptor返回值为true
            chain.doFilter(request, response);
        }

        System.out.println("开始拦截了................");
        //业务代码
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

}
```
--------------------- 
作者：wmq_fly 
来源：CSDN 
原文：https://blog.csdn.net/wmq_fly/article/details/82634287 
版权声明：本文为博主原创文章，转载请附上博文链接！