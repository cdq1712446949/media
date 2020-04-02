# 基于移动互联网技术多用户传媒系统的设计与实现

该系统采用微服务架构，使用到的技术有：[Spring Boot](https://spring.io/)，[Spring Cloud](https://spring.io/)，[Redis](https://redis.io/)，[Mybatis](https://blog.mybatis.org/)

### 项目启动前
- 1.启动Redis主从服务器
- 2.启动nginx服务器

该系统分为用户体验系统和后台管理系统，用户体验系统是用户登录注册后使用的系统，后台管理系统必须持有超级管理员账号创建的管理员账号才能登录，后台管理系统的作用是，维护用户体验系统内容健康以及内容修改等。

![GM8RgA.png](https://s1.ax1x.com/2020/03/31/GM8RgA.png)

## 1.用户体验系统
### 1.1登录/注册
登录：用户在持有账号密码的情况下，输入账号密码，由前端加密传输到后台，后台解密后验证账号密码正确性，如果验证失败会返回提示，如果验证成功，根据用户身份信息生成JWT作为value，然后生成唯一性id作为key保存到Redis缓存中，然后向前端返回数据，其中包括状态码，携带用户身份信息的token，提示信息msg。
![8csocV.png](https://s1.ax1x.com/2020/03/20/8csocV.png)



