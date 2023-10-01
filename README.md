# FlapyPan Blog

**本仓库已经停止维护(v1.3.0)，请迁移至 [kotlin 版本](https://github.com/FlapyPan/flapypan-blog-spring)**


基于 SpringBoot 3 开发一款简单高效的博客系统(后端程序)

**本仓库为后端项目，要配合下列其中之一的前端项目使用。请对应版本号，例：前端版本`v1.2.x`则后端也使用`v1.2.x`**

| 名称                | Github 地址                                     |
|-------------------|-----------------------------------------------|
| flapypan-blog-vue | https://github.com/FlapyPan/flapypan-blog-vue |

## 功能列表

- 单用户登录
- 文章的在线查看、新建、编辑、删除
- 文章搜索
- 标签管理
- 图片上传
- 支持 Postgresql、MySQL、H2(默认)数据库
- 其他自定义设置功能

## 技术栈

- Spring Boot 3.x
- Spring Data JPA
- Spring Validation
- Spring Security
- Postgres、MySQL、H2

## 启动方法

**前置要求**

- `jdk` (version >= 17)
- `maven` (version >= 3.8)

克隆本仓库源码

```shell
git clone <仓库地址> # 替换成对应的仓库地址
cd flapypan-blog-java # 进入目录
mvn install # 初始化项目
```

### 编译 Jar

在`target`目录下可以找到编译好的 jar 包

```shell
mvn package
```

### 运行

**环境变量说明**

| 环境变量           | 默认值                               | 说明        |
|----------------|-----------------------------------|-----------|
| PORT           | 8080                              | 运行端口      |
| ADMIN_USERNAME | 无                                 | 管理员用户名    |
| ADMIN_PASSWORD | 无                                 | 管理员密码(加密) |
| DB_URL         | jdbc:h2:file:./data/flapypan-blog | 数据库连接地址   |
| DB_USERNAME    | sa                                | 数据库用户名    |
| DB_PASSWORD    | 无                                 | 数据库密码     |
| UPLOAD_DIR     | ./upload                          | 图片上传的路径   |

管理员密码需要使用 hash 加密过的密码，可以使用下列方式获得

```shell
# 使用编译好的 jar 包
java -jar flapypan-blog.jar hash <密码>
```

> 如果使用 IDEA 开发，通过启动时指定程序实参也可以获取

H2 示例

```shell
export ADMIN_USERNAME='admin'
# admin
export ADMIN_PASSWORD='$2a$10$6MRSKmrdg7kjYn1Dh4pFT.5jxDMQ7wPYV9M.Qhs9A2QOjIWX.DFDi'

java -jar flapypan-blog.jar
```

Postgres 示例

```shell
export ADMIN_USERNAME='admin'
# admin
export ADMIN_PASSWORD='$2a$10$6MRSKmrdg7kjYn1Dh4pFT.5jxDMQ7wPYV9M.Qhs9A2QOjIWX.DFDi'
export DB_URL='jdbc:postgresql://127.0.0.1:5432/db_blog'
export DB_USERNAME='postgres'
export DB_PASSWORD='postgres'

java -jar flapypan-blog.jar
```

MySQL 示例

```shell
export ADMIN_USERNAME='admin'
# admin
export ADMIN_PASSWORD='$2a$10$6MRSKmrdg7kjYn1Dh4pFT.5jxDMQ7wPYV9M.Qhs9A2QOjIWX.DFDi'
export DB_URL='jdbc:mysql://127.0.0.1:3306/db_blog?useSSL=false&serverTimezone=UTC'
export DB_USERNAME='root'
export DB_PASSWORD='root'

java -jar flapypan-blog.jar
```
