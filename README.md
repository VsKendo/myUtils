# myUtils-项目介绍
The utils I used in project.

一些在项目中我使用到的使用工具。不断完善中...

因为我是在项目上把它们取出来并进行一定优化的，所以它们基本上都依赖于Spring，其中一些会依赖于 Mybatis Plus（简称MP）。

# 目录

```bash
├── README.md # 本文件
├── core # 方便直接复制使用，所有工具都放到这个文件里
├── examples # 一些可运行的 demos,方便快速了解不同工具如何使用
└── sql # 在运行 examples 中的代码时，可能有用的sql语句
```

# Core介绍

- crypto：加密和解密用到的工具类。可以加密或解密 md5\sha1\sha256 字符串。——代码参考：hutools 【无依赖】
- lambda：它允许你快速得到一个类的所有字段。只需要使用Lambda表达式（MP风格）即可获取某个类的某个字段的名字。工具类额外进行了缓存处理（FieldCacheMap.java），所以可以安心地使用它而不用担心性能。额外地，工具类PojoUtils重写了Spring的BeansUtils.copyProperties()函数，使用PojoUtils.copyProperties()来代替它！因为缓存的原因，可以肆无忌惮使用这个好用的函数！——代码参考：Mybatis Plus【依赖Spring、MP】

# examples介绍

- PojoUtils：使用了lambda中的文件。
