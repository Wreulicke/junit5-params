package com.github.wreulicke.junit5.params.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.platform.commons.JUnitException;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMappingProvider implements ArgumentsProvider, AnnotationConsumer<JsonMappingSource> {
  private String encoding;
  private String resourcePath;
  private static ObjectMapper mapper = new ObjectMapper();

  @Override
  public void accept(JsonMappingSource source) {
    encoding = source.encoding();
    resourcePath = source.value();
  }

  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
    Method method = context.getTestMethod()
      .orElseThrow(() -> new JUnitException("cannot find method"));
    Class<?> testClass = method.getDeclaringClass();
    Parameter[] parameters = method.getParameters();

    if (parameters.length != 1) {
      throw new JUnitException("cannot use here");
    }

    Class<?> type = parameters[0].getType();

    try (InputStream rs = testClass.getResourceAsStream(resourcePath);
      InputStreamReader stream = new InputStreamReader(rs, Charset.forName(encoding))) {
      MappingIterator<Object> it = mapper.readerFor(type)
        .readValues(stream);
      return it.readAll()
        .stream()
        .map(Arguments::of);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

}
