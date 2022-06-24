package infrastructure.dataprovider.services;

import java.io.IOException;
import java.util.stream.Stream;

public interface DataProvider<T> {
    Stream<T> provide() throws IOException;

    void populateData(Stream<T> data);
}
