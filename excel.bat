::excel��ṹ ��������sql���
@echo off

::echo ������Ҫ������excel�ļ���, �ļ�Ŀ¼D:\360MoveData\Users\linkage\Desktop\MyBat\excel ......
::set /p QQ=

::pause
CD.>D:\360MoveData\Users\linkage\Desktop\MyBat\excel_2_creatSql.txt
::java -Dparam=%QQ% -jar D:\360MoveData\Users\linkage\Desktop\MyBat\jar\excel_2_creatSql.jar
java -Dparam=excel��������sql���.xlsx -jar D:\360MoveData\Users\linkage\Desktop\MyBat\jar\excel_2_creatSql.jar
echo.