package com.example.backend.migration.transform.writer;

import com.example.backend.data.nosql.CustomerDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ItemWriter<T> {
    void writeData(List<T> data);
}
