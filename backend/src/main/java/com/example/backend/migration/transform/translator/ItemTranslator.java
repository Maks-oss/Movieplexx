package com.example.backend.migration.transform.translator;

import java.util.List;

public interface ItemTranslator<I,O> {
    List<O> transformData(List<I> input);
}
