# 浅谈Spring中的事务回滚

使用Spring管理事务过程中，碰到过一些坑，因此也稍微总结一下，方便后续查阅。

## 1.代码中事务控制的3种方式
编程式事务：就是直接在代码里手动开启事务，手动提交，手动回滚。优点就是可以灵活控制，缺点就是太麻烦了，太多重复的代码了。
声明式事务：就是使用SpringAop配置事务，这种方式大大的简化了编码。需要注意的是切入点表达式一定要写正确。
注解事务：直接在Service层的方法上面加上@Transactional注解，个人比较喜欢用这种方式。

## 2.事务不回滚的原因
 在工作中，看过别人写的代码出现了事务不回滚的现象。当然，事务不回滚的都是采用的声明式事务或者是注解事务；编程式事务都是自己写代码手动回滚的，因此是不会出现不回滚的现象。

再说下声明式事务和注解事务回滚的原理：当被切面切中或者是加了注解的方法中抛出了RuntimeException异常时，Spring会进行事务回滚。默认情况下是捕获到方法的RuntimeException异常，也就是说抛出只要属于运行时的异常（即RuntimeException及其子类）都能回滚；但当抛出一个不属于运行时异常时，事务是不会回滚的。

下面说说我经常见到的3种事务不回滚的产生原因：

    （1）声明式事务配置切入点表达式写错了，没切中Service中的方法
    （2）Service方法中，把异常给try catch了，但catch里面只是打印了异常信息，没有手动抛出RuntimeException异常
    （3）Service方法中，抛出的异常不属于运行时异常（如IO异常），因为Spring默认情况下是捕获到运行时异常就回滚

---
## 3.如何保证事务回滚
正常情况下，按照正确的编码是不会出现事务回滚失败的。下面说几点保证事务能回滚的方法
- （1）如果采用编程式事务，一定要确保切入点表达式书写正确
- （2）如果Service层会抛出不属于运行时异常也要能回滚，那么可以将Spring默认的回滚时的异常修改为Exception，这样就可以保证碰到什么异常都可以回滚。具体的设置方式也说下：

① 声明式事务，在配置里面添加一个rollback-for，代码如下

 
```xml
 <tx:method name="update*" propagation="REQUIRED" rollback-for="java.lang.Exception"/> 
```
 

@Transactional(rollbackFor=Exception.class)

 - （3）只有非只读事务才能回滚的，只读事务是不会回滚的
 - （4）如果在Service层用了try catch，在catch里面再抛出一个 RuntimeException异常，这样出了异常才会回滚
 - （5）如果你不喜欢（4）的方式，你还可以直接在catch后面写一句回滚代码（TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); ）来实现回滚，这样的话，就可以在抛异常后也能return 返回值；比较适合需要拿到Service层的返回值的场景。具体的用法可以参见考下面的伪代码

② 注解事务，直接在注解上面指定，代码如下
 ```java
@Transactional(rollbackFor = Exception.class)
public void test() {  
     try {  
        updata();  
        updata2();  
     } catch (Exception e) {  
        e.printStackTrace();
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//如果updata2()抛了异常,updata()会回滚,不影响事物正常执行                                  
     } 
 ```