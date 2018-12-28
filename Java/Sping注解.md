1、@controller 控制器（注入服务）

用于标注控制层，相当于struts中的action层
2、@service 服务（注入dao）

用于标注服务层，主要用来进行业务的逻辑处理
3、@repository（实现dao访问）

用于标注数据访问层，也可以说用于标注数据访问组件，即DAO组件.
4、@component （把普通pojo实例化到spring容器中，相当于配置文件中的 

`<bean id="" class=""/>`
泛指各种组件，就是说当我们的类不属于各种归类的时候（不属于@Controller、@Services等的时候），我们就可以使用@Component来标注这个类。