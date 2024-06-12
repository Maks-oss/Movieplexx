package com.example.backend.migration.transform.reader;

import java.util.List;
public interface ItemReader<T> {
    List<T> readItems();
}
