###数据库里字符串字段里存数字的mapper问题
当数据库里字段是字符串类型时mapper里使用test进行测试时不能将其加引号当作字符型，  test里会自动转换，不加引号直接写数字会自动转换字符串判断
例如
<if test=" appState == 1 "></if>
以下错误
<if test=" appState == ’1‘ "></if>
