#!/bin/bash

echo "开始全面修复Controller类型问题..."

# 修复分页查询方法的返回类型 - 移除错误的泛型类型
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/Result\.<Void>success(/Result.success(/g' {} \;
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/Result\.<Void>error(/Result.error(/g' {} \;

# 修复方法签名中的返回类型
# 分页查询方法应该返回 Result<PageResult<T>>
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/public Result<Void> get.*Page(/public Result<PageResult<.*>> get.*Page(/g' {} \;

# 获取单个实体的方法应该返回 Result<T>
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/public Result<Void> get.*ById(/public Result<.*> get.*ById(/g' {} \;

# 获取列表的方法应该返回 Result<List<T>>
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/public Result<Void> get.*List(/public Result<List<.*>> get.*List(/g' {} \;

# 获取统计信息的方法应该返回 Result<Map<String,Object>>
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/public Result<Void> get.*Stats(/public Result<Map<String,Object>> get.*Stats(/g' {} \;

# 获取菜单的方法应该返回 Result<List<T>>
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/public Result<Void> get.*Menus(/public Result<List<.*>> get.*Menus(/g' {} \;

# 获取权限的方法应该返回 Result<List<String>>
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/public Result<Void> get.*Permissions(/public Result<List<String>> get.*Permissions(/g' {} \;

# 登录相关方法应该返回 Result<LoginResponse>
find shanhaifangzhou-admin/src/main/java -name "*Controller.java" -exec sed -i 's/public Result<Void> login(/public Result<LoginResponse> login(/g' {} \;

echo "Controller类型问题修复完成！"