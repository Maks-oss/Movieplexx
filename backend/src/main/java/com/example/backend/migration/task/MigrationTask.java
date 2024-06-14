package com.example.backend.migration.task;

import com.example.backend.migration.transform.reader.ItemReader;
import com.example.backend.migration.transform.translator.ItemTranslator;
import com.example.backend.migration.transform.writer.ItemWriter;

public class MigrationTask<I, O> implements Runnable {
    private final ItemReader<I> itemReader;
    private final ItemTranslator<I, O> itemTranslator;

    private final ItemWriter<O> itemWriter;

    public MigrationTask(ItemReader<I> itemReader, ItemTranslator<I,O> itemTranslator, ItemWriter<O> itemWriter) {
        this.itemReader = itemReader;
        this.itemTranslator = itemTranslator;
        this.itemWriter = itemWriter;
    }


    @Override
    public void run() {
        var inputData = itemReader.readItems();
        var transformedData = itemTranslator.transformData(inputData);
        itemWriter.writeData(transformedData);
    }


}
