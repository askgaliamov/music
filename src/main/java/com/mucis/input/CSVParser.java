package com.mucis.input;

import java.io.InputStream;
import java.util.stream.Stream;

public interface CSVParser<T> {

    Stream<T> parse(InputStream inputStream);

}
