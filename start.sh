#!/bin/bash

echo "山海方舟管理系统启动脚本"
echo "=========================="

# 检查Java版本
echo "检查Java版本..."
java -version
if [ $? -ne 0 ]; then
    echo "错误: 请先安装Java 17"
    exit 1
fi

# 检查Node.js版本
echo "检查Node.js版本..."
node -v
if [ $? -ne 0 ]; then
    echo "错误: 请先安装Node.js"
    exit 1
fi

# 检查MySQL
echo "检查MySQL连接..."
mysql -u root -p123456 -e "SELECT 1;" > /dev/null 2>&1
if [ $? -ne 0 ]; then
    echo "警告: MySQL连接失败，请检查数据库配置"
fi

# 启动后端
echo "启动后端服务..."
cd shanhaifangzhou-admin
mvn spring-boot:run &
BACKEND_PID=$!

# 等待后端启动
echo "等待后端服务启动..."
sleep 10

# 启动前端
echo "启动前端服务..."
cd ../shanhaifangzhou-Admin-Vue-Ultra
npm install
npm run dev &
FRONTEND_PID=$!

echo "=========================="
echo "服务启动完成！"
echo "后端地址: http://localhost:8080"
echo "前端地址: http://localhost:3000"
echo "Swagger文档: http://localhost:8080/swagger-ui.html"
echo "=========================="
echo "按 Ctrl+C 停止服务"

# 等待用户中断
trap "echo '正在停止服务...'; kill $BACKEND_PID $FRONTEND_PID; exit" INT
wait
