package com.github.wreulicke.junit5.params.json;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.params.provider.ArgumentsSource;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ArgumentsSource(JsonMappingProvider.class)
public @interface JsonMappingSource {
  /**
   * @return classpath for csv resouce
   */
  String value();

  /**
   * @return resource encoding
   */
  String encoding() default "UTF-8";

}
