#!/bin/bash

echo "=========================================="
echo "山海方舟企业级中后台管理系统"
echo "=========================================="
echo ""

# 检查Java版本
echo "检查Java环境..."
if command -v java &> /dev/null; then
    java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    echo "Java版本: $java_version"
    
    # 检查是否为Java 17+
    major_version=$(echo $java_version | cut -d'.' -f1)
    if [[ "$major_version" == "1" ]]; then
        major_version=$(echo $java_version | cut -d'.' -f2)
    fi
    
    if [[ $major_version -lt 17 ]]; then
        echo "❌ 错误: 需要Java 17或更高版本"
        echo "请安装Java 17+: https://adoptium.net/"
        exit 1
    fi
else
    echo "❌ 错误: 未找到Java环境"
    echo "请安装Java 17+: https://adoptium.net/"
    exit 1
fi

# 检查Maven
echo ""
echo "检查Maven环境..."
if command -v mvn &> /dev/null; then
    mvn_version=$(mvn -version | head -n 1 | cut -d' ' -f3)
    echo "Maven版本: $mvn_version"
else
    echo "❌ 错误: 未找到Maven环境"
    echo "请安装Maven: https://maven.apache.org/download.cgi"
    exit 1
fi

# 检查Node.js
echo ""
echo "检查Node.js环境..."
if command -v node &> /dev/null; then
    node_version=$(node --version)
    echo "Node.js版本: $node_version"
    
    # 检查是否为Node.js 18+
    major_version=$(echo $node_version | cut -d'v' -f2 | cut -d'.' -f1)
    if [[ $major_version -lt 18 ]]; then
        echo "❌ 错误: 需要Node.js 18或更高版本"
        echo "请安装Node.js 18+: https://nodejs.org/"
        exit 1
    fi
else
    echo "❌ 错误: 未找到Node.js环境"
    echo "请安装Node.js 18+: https://nodejs.org/"
    exit 1
fi

# 检查MySQL
echo ""
echo "检查MySQL服务..."
if command -v mysql &> /dev/null; then
    if systemctl is-active --quiet mysql || systemctl is-active --quiet mysqld; then
        echo "✅ MySQL服务正在运行"
    else
        echo "⚠️  MySQL服务未运行，请启动MySQL服务"
        echo "启动命令: sudo systemctl start mysql 或 sudo systemctl start mysqld"
    fi
else
    echo "⚠️  未找到MySQL客户端，请确保MySQL已安装并运行"
fi

# 检查Redis
echo ""
echo "检查Redis服务..."
if command -v redis-cli &> /dev/null; then
    if systemctl is-active --quiet redis; then
        echo "✅ Redis服务正在运行"
    else
        echo "⚠️  Redis服务未运行，请启动Redis服务"
        echo "启动命令: sudo systemctl start redis"
    fi
else
    echo "⚠️  未找到Redis客户端，请确保Redis已安装并运行"
fi

echo ""
echo "=========================================="
echo "环境检查完成！"
echo "=========================================="
echo ""

# 选择启动方式
echo "请选择启动方式:"
echo "1) 启动后端服务"
echo "2) 启动前端服务"
echo "3) 启动全部服务"
echo "4) 退出"
echo ""
read -p "请输入选择 (1-4): " choice

case $choice in
    1)
        echo "启动后端服务..."
        cd shanhaifangzhou-admin
        mvn spring-boot:run
        ;;
    2)
        echo "启动前端服务..."
        cd shanhaifangzhou-Admin-Vue-Ultra
        npm install
        npm run dev
        ;;
    3)
        echo "启动全部服务..."
        echo "请打开两个终端窗口分别运行:"
        echo "终端1: cd shanhaifangzhou-admin && mvn spring-boot:run"
        echo "终端2: cd shanhaifangzhou-Admin-Vue-Ultra && npm install && npm run dev"
        ;;
    4)
        echo "退出..."
        exit 0
        ;;
    *)
        echo "无效选择，退出..."
        exit 1
        ;;
esac
