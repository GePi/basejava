package storage.serialization;

import java.io.IOException;

@FunctionalInterface
public interface IterableWriter<E> {
    void writeElement(E e) throws IOException;
}
