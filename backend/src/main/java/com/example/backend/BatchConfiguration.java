package com.example.backend;


import com.example.backend.data.sql.Customer;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
//    @Bean
//    public JpaPagingItemReader<Customer> reader(EntityManagerFactory entityManagerFactory) {
//        JpaPagingItemReader<Customer> reader = new JpaPagingItemReader<>();
//        reader.setEntityManagerFactory(entityManagerFactory);
//        reader.setQueryString("SELECT s FROM Customer s");
//        reader.setPageSize(100);
//        return reader;
//    }
//    @Bean
//    public Job importUserJob(JobRepository jobRepository, Step step1) {
//        return new JobBuilder("importUserJob", jobRepository)
////                .listener(listener)
//                .start(step1)
//                .build();
//    }
//
//    @Bean
//    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
//                      JpaPagingItemReader<Customer> reader) {
//        return new StepBuilder("step1", jobRepository)
//                .<Customer, Customer> chunk(3, transactionManager)
//                .reader(reader)
////                .processor(processor)
////                .writer(writer)
//                .build();
//    }
//    @Bean
//    public PersonItemProcessor processor() {
//        return new PersonItemProcessor();
//    }

//    @Bean
//    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
//        return new JdbcBatchItemWriterBuilder<Person>()
//                .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
//                .dataSource(dataSource)
//                .beanMapped()
//                .build();
//    }
}

