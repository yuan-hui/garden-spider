echo 开始导出lib...
mvn dependency:copy-dependencies -DoutputDirectory=libs   -DincludeScope=compile
echo 完毕.
pause