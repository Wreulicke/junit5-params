package com.github.wreulicke.junit5.params.csv;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Condition;
import org.junit.jupiter.params.ParameterizedTest;

import com.github.wreulicke.junit5.params.User;

public class CsvMappingProviderTest {

  @ParameterizedTest
  @CsvMappingSource("/test.csv")
  void case1(User user) {
    assertThat(user.getUserName()).is(new Condition<>((n) -> {
      return n.equals("aaa") || n.equals("ccc");
    }, "valid user name"));
    assertThat(user.getPassword()).is(new Condition<>((n) -> {
      return n.equals("bbb") || n.equals("ddd");
    }, "valid password"));
  }

  @ParameterizedTest
  @CsvMappingSource(value = "/test.csv", columns = {
    "password",
    "userName"
  })
  void case2(User user) {
    assertThat(user.getUserName()).is(new Condition<>((n) -> {
      return n.equals("bbb") || n.equals("ddd");
    }, "valid user name"));
    assertThat(user.getPassword()).is(new Condition<>((n) -> {
      return n.equals("aaa") || n.equals("ccc");
    }, "valid password"));
  }
}
