# 本文以三种springMVC请求处理方法的书写来详细说明SpringMVC交互。

## 一、使用原生态的Servlet来做参数处理数据交互
先看代码：

```java
@RequestMapping("add")
public void addStudent(HttpServletRequest req,HttpServletResponse resp){
	StudentEntity student0=new StudentEntity();
	student0.setS_id(UUID.randomUUID().toString().replace("-", ""));
	student0.setS_name("小百");
	student0.setS_age(19);
	student0.setS_loginName("sdjk");
	student0.setS_passWord("12");
	student0.setT_id("2");

	//将这个实体类学生对象的信息写入在数据库中
	studentservice.addStudent(student0);

	StudentEntity student1=new StudentEntity();
	student1.setS_name("小");
	List list=studentservice.selectLike(student1);
	System.out.println("*****"+list.size());
	for(int i=0;i<list.size();i++){
		StudentEntity student=(StudentEntity) list.get(i);
		TeacherEntity teacher=student.getTeacherEntity();
		System.out.println("student_name:"+student.getS_name()+"\tteacher_name:"+teacher.getT_name());
	}
	try {
		resp.sendRedirect(req.getContextPath()+"/student/getList.do");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
```
这样处理的好处是对于习惯了servlet处理交互的童鞋，可以同时使用springMVC配置请求，用servlet的数据交互方式来完成数据交互。

## 二、使用实体类对象或基本你数据类型，STring或map作为请求处理方法参数

### 1、使用实体类对象作为请求处理方法的参数来接收前台表单提交的数据。

```java
/*
* 使用@ModelAttribute注解来处理前台表单提交的时候，未在表单中输入的数据，
* 防止在进行数据更改或者插入时，在数据库中插入或者修改数据成空值
*/
@ModelAttribute
//带有modelAtrribute注解的方法会在类对象执行方法的时候先执行
public void getStudent(Map<String,Object> map){
	StudentEntity student0=new StudentEntity();
	student0.setS_id("12341323");
	student0.setS_name("小百");
	student0.setS_age(19);
	student0.setS_loginName("sdjk");
	student0.setS_passWord("12");
	student0.setT_id("2");

	map.put("student", student0);
	//此处key值最好设置成小写开头的同实体类类名形式，如使用其他key，在调用的时候需要作出指定
	//map.put("peter", student0);
}

@RequestMapping("update")
//public void updateStudent(@ModelAttribute("peter")StudentEntity student){
public void updateStudent(StudentEntity student){
	//do update
}
```

在接收前台表单提交数据（实体类对象的数据）时，可以直接使用实体类对象作为请求处理方法的参数。只需要使用@ModelAttribute注解先进行处理，
这样处理有个好处就是，在进去请求方法之前，完成执行@ModelAttribute修饰的方法后，就获得了一个完整的实体类对象，同时还可以给这个实体类对象设置默认值和创建新的id等一系列操作。

### 2、使用使用注解@RequestParam("keyName")来获取前台传递的参数
```java
@RequestMapping("update")
public void updateStudent(@ModelAttribute("peter")StudentEntity student,@RequestParam("lattice")String lattice){
	//do update
	System.out.println(student.toString());
}
```

### 3、使用Map类型来向前台传递数据
```java
//设置需要传递到sessionScope的数据，本注解只能修饰类
@SessionAttributes(value={"hello","studentList"},types={String.class,List.class})//修饰类

	@RequestMapping("testmap")
	/*
	 * 测试使用map来传递数据到前台
	 */
	public String testMap(Map<String,Object> map){
		map.put("hello","hello");
		//查询数据库获取学生信息列表
		List list=studentservice.getList();
		map.put("studentList", list);
		return "encoding";
	}
```
前台接收显示：
```jsp
${sessionScope.hello}<br>
<c:forEach items="${studentList }" var="student">
	<c:out value="${student.s_name }"></c:out><br/>
</c:forEach>
```

## 三、使用modelAndView向前台传递数据(推荐)
```java
@RequestMapping("getList")
public ModelAndView getList() {
	System.out.println("++++++++++++++");
	ModelAndView view=new ModelAndView();
	List list=studentservice.getList();
	System.out.println("+++++++++++"+list.size());
	view.addObject("studentList", list);
	//设置要跳转的页面，与返回值时String时返回success类似，以下跳转到/student/studentList.jsp
	view.setViewName("/student/studentList");
	return view;
}
```
说明：使用ModelAndView，可以直接使用AddObject来传递数据，并且数据㐓使用EL表达式在前台页面处理。使用setViewName("/student/studentList");来设置页面跳转路径。





---------------------
作者：格乄子
来源：CSDN
原文：https://blog.csdn.net/gezi2015129/article/details/61919894
版权声明：本文为博主原创文章，转载请附上博文链接！
