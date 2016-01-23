# asLockApp

安卓应用锁

### 开发配置
本应用是在Android Studio 14上面开发的。

使用的JDK版本是 jdk1.8.0_51。

安卓版本：
最低要求是：minSdkVersion 9
目标版本为：targetSdkVersion 23

关于各个分支的介绍：目前有三个分支，
1、master只实现了最核心的锁定功能。没有设置，只能选择程序锁定。
2、ActionBar大家不用看，是我想实现一个侧滑栏，不过完成度不高，后面没有继续跟进
3、framelayout这个版本，名字没取好，反而是这个版本实现了ActionBar的基本框架。设置界面的框架也搭出来了。
同时这个版本采用的Broadcast 唤醒LockService 服务。只需稍作改动就能完成开机自启动等其他重要功能。

### 后续工作

目前的完成度已经比较高，在安卓5.1以下所有的功能都能够完美实现。5.1的实现方式也在里面的资源文件夹里面。

下一步的目标，将一些Bug解决。


### 参考资料
关于应用锁的实现可以参考我的博客：

[安卓开发之应用锁](http://blog.csdn.net/include_u/article/details/49889791)

[应用锁之获取栈顶Activity](http://blog.csdn.net/include_u/article/details/50558130)