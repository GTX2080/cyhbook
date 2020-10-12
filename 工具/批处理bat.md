### ----------获取批处理所在路径----------
%~dp0 “d”为Drive的缩写，即为驱动器，磁盘、“p”为Path缩写，即为路径，目录（后面会带斜杠） 例如d://drive/


### ----------获取注册表----------

for /f "tokens=1,2,* " %%i in ('REG QUERY HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Lsa /v limitblankpassworduse ^| find /i "limitblankpassworduse"') do set "regvalue=%%k"
echo 注册表值为%regvalue%

### ----------添加注册表----------
reg add "HKEY_CURRENT_USER\Software\Microsoft\Internet Explorer\New Windows\Allow" /v "ccqepath" /t REG_RSZ /d %~dp0 /f
reg add KeyName [/v ValueName | /ve] [/t Type] [/s Separator] [/d Data] [/f]

/v 所选项之下要添加或修改的值名
/ve 为注册表项添加空白值名<无名称>
 /t RegKey 数据类型
[ REG_SZ | REG_MULTI_SZ | REG_DWORD_BIG_ENDIAN |
 REG_DWORD | REG_BINARY | REG_DWORD_LITTLE_ENDIAN |
 REG_NONE | REG_EXPAND_SZ ]
如果忽略，则采用 REG_SZ

 /s 指定一个在 REG_MULTI_SZ 数据字符串中
 用作分隔符的字符
 如果忽略，则将 "\0" 用作分隔符

/d 要分配给添加的注册表 ValueName 的数据

/f 不用提示就强行改写现有注册表项

 例如:
REG ADD \\ABC\HKLM\Software\MyCo
添加远程机器 ABC 上的一个注册表项 HKLM\Software\MyCo
 REG ADD HKLM\Software\MyCo /v Data /t REG_BINARY /d fe340ead
添加一个值(名称: Data，类型: REG_BINARY，数据: fe340ead)
 REG ADD HKLM\Software\MyCo /v MRU /t REG_MULTI_SZ /d fax\0mail
添加一个值(名称: MRU，类型: REG_MUTLI_SZ，数据: fax\0mail\0\0)
 REG ADD HKLM\Software\MyCo /v Path /t REG_EXPAND_SZ /d %%systemroot%%
添加一个值(名称: Path，类型: REG_EXPAND_SZ，数据: %systemroot%)
注意: 在扩充字符串中使用双百分比符号( %% )
比如现在
HKEY_LOCAL_MACHINE\SOFTWARE\03D0C547-EBAD-43d9-8B57-DE16E7A93B52
这个项下面有个值的名称叫做AutoStart,数据是十六进制的2,我要把它改为1,那就这样:
reg add HKLM\SOFTWARE\03D0C547-EBAD-43d9-8B57-DE16E7A93B52 /v AutoStart /t REG_DWORD /d 1 /f

注意：
1、如果路径中有空格或者data中，则需要用""括起来。
2、每个命令提示符中之间，比如/v /t /d，与前面的字符串间需要加空格。




### ----------删除注册表----------
reg delete HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Cryptography\RNG


### ----------设置环境变量----------
setx /m "JAVA_HOME" "jdk1.8"    /m为设置系统变量不加则设置用户变量

::添加环境变量JAVA_HOME
@echo off
echo 添加java环境变量
set regpath=HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Session Manager\Environment
set evname=JAVA_HOME
set javapath=c:\java\jdk
reg add "%regpath%" /v %evname% /d %javapath% /f
pause>nul
 
 
::删除环境变量JAVA_HOME
@echo off
echo 删除java环境变量
set regpath=HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Control\Session Manager\Environment
set evname=JAVA_HOME
reg delete "%regpath%" /v "%evname%"  /f
pause>nul