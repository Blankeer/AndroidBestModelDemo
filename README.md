# Model层最佳实践demo
参考：http://blog.piasy.com/2016/05/06/Perfect-Android-Model-Layer/

SqlDelight、AutoValue、Gson、SqlBrite、Retrofit

实践demo使用的gank.io的api，仅做了model层编码，
最终效果通过查看log和database验证。

PS：SqlDelight的小bug，"desc"等关键字不能做字段名，已提交issues

后续可能继续完善该demo，加入dagger2、完善View层、MVP等。