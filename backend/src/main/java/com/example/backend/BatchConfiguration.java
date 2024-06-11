package com.example.backend;


import com.example.backend.data.nosql.CustomerDocument;
import com.example.backend.data.sql.Customer;
import com.example.backend.transformation.JobCompletionListener;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Bean
    public JpaPagingItemReader<Customer> reader(EntityManagerFactory entityManagerFactory) {
        JpaPagingItemReader<Customer> reader = new JpaPagingItemReader<>();
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT s FROM Customer s");
        reader.setPageSize(100);
        return reader;
    }
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionListener listener) {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }
//
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean
    public Step step1(
            JobRepository jobRepository,
            DataSourceTransactionManager transactionManager,
            JpaPagingItemReader<Customer> reader,
            ItemProcessor<Customer, CustomerDocument> itemProcessor,
            ItemWriter<CustomerDocument> itemWriter
    ) {
        return new StepBuilder("step1", jobRepository)
                .<Customer, CustomerDocument> chunk(3, transactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }
    @Bean
    public ItemProcessor<Customer, CustomerDocument> processor() {
        return customer -> {
            CustomerDocument customerDocument = new CustomerDocument();
            BeanUtils.copyProperties(customer, customerDocument);
            return customerDocument;
        };
    }

    @Bean
    public ItemWriter<CustomerDocument> writer(MongoTemplate mongoTemplate) {
        var mongoItemWriter = new MongoItemWriter<CustomerDocument>();
        mongoItemWriter.setTemplate(mongoTemplate);
        mongoItemWriter.setCollection("Customer");
        return mongoItemWriter;
    }
}

