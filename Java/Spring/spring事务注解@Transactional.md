# SpringBoot事务注解@Transactional
SpringBoot提供了非常方便的事务操作，通过注解就可以实现事务的回滚，非常方便快捷，下面我们就说一下如何进行事务操作。

## 1. 事务说明
在Spring中，事务有两种实现方式，分别是编程式事务管理和声明式事务管理两种方式。 
编程式事务管理： 编程式事务管理使用TransactionTemplate或者直接使用底层的PlatformTransactionManager。对于编程式事务管理，spring推荐使用TransactionTemplate。 
声明式事务管理： 建立在AOP之上的。其本质是对方法前后进行拦截，然后在目标方法开始之前创建或者加入一个事务，在执行完目标方法之后根据执行情况提交或者回滚事务。 
声明式事务管理不需要入侵代码，通过@Transactional就可以进行事务操作，更快捷而且简单。推荐使用

## 2. 如何使用
在Mybatis中使用事务，非常简单，只需要在函数增加注解@Transactional，无需任何配置。我们通过在controller层增加事务例子看下：

```java
@Autowired
OrderServiceorderService;   //order操作的Service层

@ApiOperation(value = "增加OrderCustomer")
@RequestMapping(value = "", method = RequestMethod.POST)
@ResponseBody
@Transactional
public JsonBean<Order> insertOrder(@RequestParam(value = "order") String order) {
    try {
        //创建订单
        Order order = orderService.insert(user.getId(),is_company,fk_want_company_id);
        return new JsonBean(SUCCESS, orderCustomer);
    } 
    catch (ErrorCodeException e)
    {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return new JsonBean(e.getErrorCode());
    }
}
```
@Transactional可以作用于接口、接口方法、类以及类方法上。当作用于类上时，该类的所有 public 方法将都具有该类型的事务属性，同时，我们也可以在方法级别使用该标注来覆盖类级别的定义。因此可以在Service层和Controller层使用，上述例子我们在Controller层实现，我们模拟了一个订单提交的接口，其中JsonBean是统一返回错误码基类，ErrorCodeException是自定义异常。

通过@Transactional，实现了事务操作。
Spring的AOP即声明式事务管理默认是针对unchecked exception回滚。也就是默认对RuntimeException()异常或是其子类进行事务回滚；checked异常,即Exception可try{}捕获的不会回滚，因此对于我们自定义异常，通过rollbackFor进行设定，后续会单独讲
如果我们需要捕获异常后，同时进行回滚，通过TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();进行手动回滚操作。
使用Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint(); 
设置回滚点，使用TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);回滚到savePoint。

## 3.常用配置
参 数名 称|功 能描 述
-|:-:
readOnly|该属性用于设置当前事务是否为只读事务，设置为true表示只读，false则表示可读写，默认值为false。例如：@Transactional(readOnly=true)
rollbackFor|该属性用于设置需要进行回滚的异常类数组，当方法中抛出指定异常数组中的异常时，则进行事务回滚。例如：指定单一异常类：@Transactional(rollbackFor=RuntimeException.class)|指定多个异常类：@Transactional(rollbackFor={RuntimeException.class, Exception.class})
rollbackForClassName|该属性用于设置需要进行回滚的异常类名称数组，当方法中抛出指定异常名称数组中的异常时，则进行事务回滚。例如：指定单一异常类名称@Transactional(rollbackForClassName=”RuntimeException”)指定多个异常类名称：@Transactional(rollbackForClassName={“RuntimeException”,”Exception”})
noRollbackFor|该属性用于设置不需要进行回滚的异常类数组，当方法中抛出指定异常数组中的异常时，不进行事务回滚。例如：指定单一异常类：@Transactional(noRollbackFor=RuntimeException.class)|指定多个异常类：@Transactional(noRollbackFor={RuntimeException.class, Exception.class})
noRollbackForClassName|该属性用于设置不需要进行回滚的异常类名称数组，当方法中抛出指定异常名称数组中的异常时，不进行事务回滚。例如：指定单一异常类名称：@Transactional(noRollbackForClassName=”RuntimeException”)|指定多个异常类名称：@Transactional(noRollbackForClassName={“RuntimeException”,”Exception”})
propagation|该属性用于设置事务的传播行为。例如：@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
isolation|该属性用于设置底层数据库的事务隔离级别，事务隔离级别用于处理多事务并发的情况，通常使用数据库的默认隔离级别即可，基本不需要进行设置
timeout|该属性用于设置事务的超时秒数，默认值为-1表示永不超时

## 4. 事务属性
#### 事务隔离级别

隔离级别是指若干个并发的事务之间的隔离程度。TransactionDefinition 接口中定义了五个表示隔离级别的常量：
```java
TransactionDefinition.ISOLATION_DEFAULT：这是默认值，表示使用底层数据库的默认隔离级别。对大部分数据库而言，通常这值就是TransactionDefinition.ISOLATION_READ_COMMITTED。 
TransactionDefinition.ISOLATION_READ_UNCOMMITTED：该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据。该级别不能防止脏读，不可重复读和幻读，因此很少使用该隔离级别。比如PostgreSQL实际上并没有此级别。 
TransactionDefinition.ISOLATION_READ_COMMITTED：该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。 
TransactionDefinition.ISOLATION_REPEATABLE_READ：该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。该级别可以防止脏读和不可重复读。 
TransactionDefinition.ISOLATION_SERIALIZABLE：所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。
```
#### 事务传播行为
所谓事务的传播行为是指，如果在开始当前事务之前，一个事务上下文已经存在，此时有若干选项可以指定一个事务性方法的执行行为。在TransactionDefinition定义中包括了如下几个表示传播行为的常量：
```java
TransactionDefinition.PROPAGATION_REQUIRED：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值。 
TransactionDefinition.PROPAGATION_REQUIRES_NEW：创建一个新的事务，如果当前存在事务，则把当前事务挂起。 
TransactionDefinition.PROPAGATION_SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。 
TransactionDefinition.PROPAGATION_NOT_SUPPORTED：以非事务方式运行，如果当前存在事务，则把当前事务挂起。 
TransactionDefinition.PROPAGATION_NEVER：以非事务方式运行，如果当前存在事务，则抛出异常。 
TransactionDefinition.PROPAGATION_MANDATORY：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。 
TransactionDefinition.PROPAGATION_NESTED：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于TransactionDefinition.PROPAGATION_REQUIRED。
```
#### 事务超时 
所谓事务超时，就是指一个事务所允许执行的最长时间，如果超过该时间限制但事务还没有完成，则自动回滚事务。在 TransactionDefinition 中以 int 的值来表示超时时间，其单位是秒。 
默认设置为底层事务系统的超时值，如果底层数据库事务系统没有设置超时值，那么就是none，没有超时限制。

#### 事务只读属性 
只读事务用于客户代码只读但不修改数据的情形，只读事务用于特定情景下的优化，比如使用Hibernate的时候。 
默认为读写事务。
--------------------- 
作者：小虎牙Kenny 
来源：CSDN 
原文：https://blog.csdn.net/wkl305268748/article/details/77619367 
版权声明：本文为博主原创文章，转载请附上博文链接！