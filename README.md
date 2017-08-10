# junit5-params

[![CircleCI](https://circleci.com/gh/Wreulicke/junit5-params.svg?style=svg)](https://circleci.com/gh/Wreulicke/junit5-params)

* provides CSV POJO Mapping with jackson-dataformat-csv

# usage

```java
@RunWith(JUnitPlatform.class)
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

