# Junit5-Params

[![CircleCI](https://circleci.com/gh/Wreulicke/junit5-params.svg?style=svg)](https://circleci.com/gh/Wreulicke/junit5-params)

* provides CSV POJO Mapping with jackson-dataformat-csv

# Getting Started

## Add repository

This library provides from jitpack.
So, add followings into your configuration.

```groovy
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
```

## Add dependency

```groovy
dependencies {
  compile 'com.github.Wreulicke:junit5-params:0.0.1-RC2'
}
```

## example 

```java
public class ExampleTest{ 
  @ParameterizedTest
  @CsvMappingSource(value = "/test.csv", columns = {
    "password",
    "userName"
  })
  void case1(User user) {
    assertThat(user.getUserName()).is(new Condition<>((n) -> {
      return n.equals("bbb") || n.equals("ddd");
    }, "valid user name"));
    assertThat(user.getPassword()).is(new Condition<>((n) -> {
      return n.equals("aaa") || n.equals("ccc");
    }, "valid password"));
  }
}
```

