package com.github.wreulicke.junit5.params.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Condition;
import org.junit.jupiter.params.ParameterizedTest;

import com.github.wreulicke.junit5.params.User;

public class JsonMappingProviderTest {

  @ParameterizedTest
  @JsonMappingSource(value = "/test.json")
  void test(User user) {
    assertThat(user.getUserName()).is(new Condition<>((n) -> {
      return n.equals("aaa") || n.equals("ccc");
    }, "valid user name"));
    assertThat(user.getPassword()).is(new Condition<>((n) -> {
      return n.equals("bbb") || n.equals("ddd");
    }, "valid password"));
  }

}
