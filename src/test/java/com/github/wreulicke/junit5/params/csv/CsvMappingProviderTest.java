package com.github.wreulicke.junit5.params.csv;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Condition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@RunWith(JUnitPlatform.class)
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

  @JsonPropertyOrder({
    "userName",
    "password"
  })
  public static class User {
    private String userName;
    private String password;

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    @Override
    public String toString() {
      return "User [userName=" + userName + ", password=" + password + "]";
    }
  }
}
