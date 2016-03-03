### ZLog
一个AndroidStudio专用的LogCat工具，主要功能为打印行号、函数调用、Json解析、XML解析、点击跳转、Log信息保存等功能。参考自Logger、KLog

### Integration

Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

```
dependencies {
    compile 'com.github.z-chu:ZLog:1.1'
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
