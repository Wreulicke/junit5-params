package com.github.wreulicke.junit5.params.csv;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.params.provider.ArgumentsSource;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.wreulicke.junit5.params.csv.CsvMappingProvider;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ArgumentsSource(CsvMappingProvider.class)
public @interface CsvMappingSource {
  /**
   * @return classpath for csv resouce
   */
  String value();

  /**
   * 
   * use instead of JsonPropertyOrder
   * 
   * @see JsonPropertyOrder
   * @return json columns schema
   */
  String[] columns() default "";

  /**
   * @return resource encoding
   */
  String encoding() default "UTF-8";

  /**
   * line separator for CSV
   * 
   * @return
   */
  String lineSeparator() default "\n";

  /**
   * delimiter for CSV
   * 
   * @return
   */
  char delimiter() default ',';

}
