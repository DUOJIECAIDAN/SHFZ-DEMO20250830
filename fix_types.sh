#!/bin/bash

# 批量修复Java文件中的类型推断问题

echo "开始修复类型推断问题..."

# 修复Result.success()的类型推断问题
find shanhaifangzhou-admin/src/main/java -name "*.java" -exec sed -i 's/Result\.success(/Result.<Void>success(/g' {} \;
find shanhaifangzhou-admin/src/main/java -name "*.java" -exec sed -i 's/Result\.error(/Result.<Void>error(/g' {} \;

echo "类型推断问题修复完成！"