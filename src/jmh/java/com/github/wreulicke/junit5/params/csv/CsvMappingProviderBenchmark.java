package com.github.wreulicke.junit5.params.csv;

import java.lang.reflect.Method;

import org.openjdk.jmh.annotations.Benchmark;

public class CsvMappingProviderBenchmark {
  static {
    try {
      Method method = CsvMappingProviderBenchmark.class.getMethod("first");
      CsvMappingSource annotation = method.getAnnotation(CsvMappingSource.class);
      source = annotation;
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e);
    }
  }

  static final CsvMappingSource source;

  @Benchmark
  @CsvMappingSource("/test.csv")
  public void first() {
    new CsvMappingProvider().accept(source);
  }

}
