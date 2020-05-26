# 基于移动互联网技术多用户传媒系统的设计与实现

* [1.用户体验系统](#1用户体验系统)
  * [1.1登录/注册](#11登录注册)
* [功能实现截图](#功能实现)
  * [首页](#首页)

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

## 功能实现
### 首页
当前界面首先会根据点击量获取文章记录，根据关注用户过去文章类表，当前界面可执行的操作有：
- 1.选择文章类型，根据联动菜单选择文章类型，确定后更新文章列表
- 2.创建文章（登录后）,点击右上角加号，跳转到文章创建页面
- 3.关注作者，不可重复关注
- 4.点赞文章
- 5.点击评论，跳转到文章详情页面

视频文章和图片文章的查询都是分页查询，前端界面中添加了触底事件监听，实现了无限滚动功能。

[![ti6TK0.th.png](https://s1.ax1x.com/2020/05/26/ti6TK0.th.png)](https://imgchr.com/i/ti6TK0)
[![ti7Xm4.th.png](https://s1.ax1x.com/2020/05/26/ti7Xm4.th.png)](https://imgchr.com/i/ti7Xm4) 

### 文章详情页面
当前页面根据window.location.href中的参数判断articleId的值，向后台发送请求并在页面中渲染数据。如果当前文章是当前登录用户的发表的文章，那么对评论的操作将会增加删除选项

[![ti7j0J.th.png](https://s1.ax1x.com/2020/05/26/ti7j0J.th.png)](https://imgchr.com/i/ti7j0J) 
[![ti7q6U.th.png](https://s1.ax1x.com/2020/05/26/ti7q6U.th.png)](https://imgchr.com/i/ti7q6U) 
[![ti7blT.th.png](https://s1.ax1x.com/2020/05/26/ti7blT.th.png)](https://imgchr.com/i/ti7blT) 
