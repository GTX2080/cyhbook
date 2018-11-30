第一类：请求路径参数
1、@PathVariable
获取路径参数。即url/{id}这种形式。

2、@RequestParam
获取查询参数。即url?name=这种形式

例子
GET
http://localhost:8080/demo/123?name=suki_rong
对应的java代码：

@GetMapping("/demo/{id}")
public void demo(@PathVariable(name = "id") String id, @RequestParam(name = "name") String name) {
    System.out.println("id="+id);
    System.out.println("name="+name);
}
输出结果：
id=123
name=suki_rong

第二类：Body参数
因为是POST请求，这里用Postman的截图结合代码说明

1、@RequestBody
例子

对应的java代码：

@PostMapping(path = "/demo1")
public void demo1(@RequestBody Person person) {
    System.out.println(person.toString());
}
输出结果：
name:suki_rong;age=18;hobby:programing

也可以是这样

@PostMapping(path = "/demo1")
public void demo1(@RequestBody Map<String, String> person) {
    System.out.println(person.get("name"));
}
输出结果：
suki_rong

2、无注解
例子

对应的java代码：

@PostMapping(path = "/demo2")
public void demo2(Person person) {
    System.out.println(person.toString());
}
输出结果：
name:suki_rong;age=18;hobby:programing

Person类
public class Person {

    private long id;
    private String name;
    private int age;
    private String hobby;

    @Override
    public String toString(){
        return "name:"+name+";age="+age+";hobby:"+hobby;
    }

    // getters and setters
}
第三类：请求头参数以及Cookie
1、@RequestHeader
2、@CookieValue
例子
java代码：

@GetMapping("/demo3")
public void demo3(@RequestHeader(name = "myHeader") String myHeader,
        @CookieValue(name = "myCookie") String myCookie) {
    System.out.println("myHeader=" + myHeader);
    System.out.println("myCookie=" + myCookie);
}
也可以这样

@GetMapping("/demo3")
public void demo3(HttpServletRequest request) {
    System.out.println(request.getHeader("myHeader"));
    for (Cookie cookie : request.getCookies()) {
        if ("myCookie".equals(cookie.getName())) {
            System.out.println(cookie.getValue());
        }
    }
}
---------------------
作者：suki_rong
来源：CSDN
原文：https://blog.csdn.net/suki_rong/article/details/80445880
版权声明：本文为博主原创文章，转载请附上博文链接！
