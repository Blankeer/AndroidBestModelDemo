# Model层最佳实践demo
参考：http://blog.piasy.com/2016/05/06/Perfect-Android-Model-Layer/

http://blog.alexsimo.com/delightful-persistence-android/

SqlDelight、AutoValue、Gson、SqlBrite、Retrofit

实践demo使用的gank.io的api，仅做了model层编码，
最终效果通过查看log和database验证。

加入了Junit、robolectric、mockito、RESTMock测试框架，
实现了mock HTTP请求的效果。

PS：SqlDelight的小bug，"desc"等关键字不能做字段名，已提交issues，正在解决：https://github.com/square/sqldelight/pull/295/commits/36409700a32e897e0a251f6cf7eaf38c2e5b9523

后续可能继续完善该demo，加入dagger2、完善View层、MVP等。

