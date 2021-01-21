::excel表结构 解析表创建sql语句
@echo off

::echo 输入需要解析的excel文件名, 文件目录D:\360MoveData\Users\linkage\Desktop\MyBat\excel ......
::set /p QQ=

::pause
CD.>D:\360MoveData\Users\linkage\Desktop\MyBat\excel_2_creatSql.txt
::java -Dparam=%QQ% -jar D:\360MoveData\Users\linkage\Desktop\MyBat\jar\excel_2_creatSql.jar
java -Dparam=excel解析创建sql语句.xlsx -jar D:\360MoveData\Users\linkage\Desktop\MyBat\jar\excel_2_creatSql.jar
echo.