#!/bin/bash

echo "开始智能修复Controller返回类型..."

# 修复分页查询方法的返回类型
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/public Result<Void> getPage(/public Result<PageResult<.*>> getPage(/g' {} \;

# 修复获取单个实体的返回类型
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/public Result<Void> getById(/public Result<.*> getById(/g' {} \;

# 修复获取列表的返回类型
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/public Result<Void> getList(/public Result<List<.*>> getList(/g' {} \;

# 修复获取统计信息的返回类型
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/public Result<Void> getStats(/public Result<Map<String,Object>> getStats(/g' {} \;

echo "Controller返回类型修复完成！"