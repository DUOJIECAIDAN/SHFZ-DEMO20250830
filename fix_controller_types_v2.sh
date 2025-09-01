#!/bin/bash

echo "开始修复Controller类型问题 v2.0..."

# 修复分页查询方法的错误返回类型
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/return Result\.error("查询.*失败：/return Result.<PageResult<.*>>error("查询失败：/g' {} \;

# 修复获取单个实体的错误返回类型
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/return Result\.error(".*不存在");/return Result.<.*>error("不存在");/g' {} \;

# 修复其他错误返回类型
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/return Result\.error(".*失败：/return Result.<.*>error("失败：/g' {} \;

echo "Controller类型问题修复完成！"