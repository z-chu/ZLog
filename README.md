### ZLog
**一个AndroidStudio专用的LogCat工具，主要功能为打印行号、函数调用、Json解析、XML解析、点击跳转、Log信息保存等功能。**

该项目修改自：
* https://github.com/orhanobut/logger

喜欢原作的可以去使用。同时欢迎大家下载体验本项目，如果使用过程中遇到什么问题，欢迎反馈。

为什么有这个项目：

1. 因为Logger的json、xml打印只有debug级别的，但有的手机压根就不打印debug日志，所以为了解决这个问题：
新增`Logger.fJson(String json)`和`Logger.fXml(String xml)`方法，方法只做字符的格式化，去掉了`Logger.json()`和`Logger.xml()`方法。

2. 使用起来更加简单，只需要调用`Logger.d(Object message)`即可打印任何对象的信息，比如：ArrayList、String、值类型、有get、set的普通类对象。

3. 去掉一些几乎不会用功能和函数（如：可变长度参数、没有用的Array操作函数）

### Integration
**Step 1**.Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
**Step 2**. Add the dependency

```
dependencies {
	compile 'com.github.z-chu:ZLog:2.0'
}
```

##License

```
Copyright 2015, 2016 ZhaoChengZhu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
