# PojoUtils

欢迎使用PojoUtils。这里提供的类可以帮助你获取一个类的字段，并替换掉低性能的BeansUtils.copyProperties()方法。

当然，这里的一些其他工具也可以令你开发效率提高百倍。

# Depends On

- Spring core
- Mybatis plus
- Project Lombok

# 原理

通过查看 Mybatis plus 的原码，我写了一些工具类（比如mybatis plus中的通过lambda方法获取字段名，我也将其封装进了工具中），同时提供了BeanUtils中虽然好用但是低性能的 BeansUtils.copyProperties() 的替代方法。特别地，获取字段会使用缓存，从而做到了 就算一个类的多个实例进行多次 copyProperties() 操作 也不会令性能大幅下降的目标，故而可放心大胆地使用我提供的 copyProperties() 方法。

