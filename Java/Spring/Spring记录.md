## 启动时运行

创建类并实现ApplicationRunner接口，并且类上要加@Component注解，执行顺序使用@Order(2)
```java
@Component      //被 spring 容器管理
@Order(2)     //如果多个自定义的 ApplicationRunner  ，用来标明执行的顺序
public class MyApplicationRunner implements ApplicationRunner{
    @Override    
    public void run(ApplicationArguments applicationArguments) throws Exception{
        System.out.println("-------------->" + "项目启动，now=" + new Date());
        System.out.println("获取到的参数： "+applicationArguments.getOptionNames());
        System.out.println("获取到的参数： "+applicationArguments.getNonOptionArgs());
        System.out.println("获取到的参数： "+applicationArguments.getSourceArgs());
        //myTimer();    }
}
```