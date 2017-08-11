# Junit5-Params

[![CircleCI](https://circleci.com/gh/Wreulicke/junit5-params.svg?style=svg)](https://circleci.com/gh/Wreulicke/junit5-params)

`JUnit5-Params` provides more easy parameterized test using junit5 parameterized test feature.

* provides CSV/JSON POJO Mapping with jackson-dataformat-csv for testing

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
  // @JsonMappingSource(value = "/test.json")
  @CsvMappingSource(value = "/test.csv", columns = {
    "password",
    "userName"
  })
  void case1(User user) {
    assertThat(user.getUserName()).isEqualTo("john doe");
    assertThat(user.getPassword()).isEqualTo("some-passowrd");
  }
}
```

