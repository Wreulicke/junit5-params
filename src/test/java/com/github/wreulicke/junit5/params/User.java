package com.github.wreulicke.junit5.params;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;

@JsonPropertyOrder({
  "userName",
  "password"
})
@Data
public class User {
  private String userName;
  private String password;
}
