
spring MVC接收请求体总是多一个等号
使用RequestBody时多出一个等号
因为请求的content type不对
应该是 application/json;charset=UTF-8
默认的是 application/x-www-form-urlencoded;charset=UTF-8
