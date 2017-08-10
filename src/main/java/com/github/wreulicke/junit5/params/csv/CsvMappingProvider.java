package com.github.wreulicke.junit5.params.csv;

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
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder;

public class CsvMappingProvider implements ArgumentsProvider, AnnotationConsumer<CsvMappingSource> {
  private char delimiter;
  private String encoding;
  private String separator;
  private String[] columns;
  private String resourcePath;
  private static CsvMapper mapper = new CsvMapper();

  @Override
  public void accept(CsvMappingSource source) {
    delimiter = source.delimiter();
    String[] columns = source.columns();
    if (columns.length != 1 || !columns[0].equals("")) {
      this.columns = columns;
    }
    encoding = source.encoding();
    separator = source.lineSeparator();
    resourcePath = source.value();

  }

  private CsvSchema buildSchema(Class<?> target) {
    if (columns == null) {
      return mapper.schemaFor(target)
        .withColumnSeparator(delimiter)
        .withLineSeparator(separator);
    }
    Builder builder = CsvSchema.builder();
    for (String column : columns) {
      builder.addColumn(column);
    }
    return builder.build()
      .withColumnSeparator(delimiter)
      .withLineSeparator(separator);
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
    CsvSchema schema = buildSchema(type);

    try (InputStream rs = testClass.getResourceAsStream(resourcePath);
      InputStreamReader stream = new InputStreamReader(rs, Charset.forName(encoding))) {
      MappingIterator<?> it = mapper.readerFor(type)
        .with(schema)
        .readValues(stream);
      return it.readAll()
        .stream()
        .map(Arguments::of);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

}
