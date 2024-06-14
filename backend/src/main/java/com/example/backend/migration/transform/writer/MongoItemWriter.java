package com.example.backend.migration.transform.writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("mongo_writer")
public class MongoItemWriter<T> implements ItemWriter<T>{
    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public void writeData(List<T> data) {
        data.forEach(collection -> {
            mongoTemplate.save(collection);
        });
    }
}
