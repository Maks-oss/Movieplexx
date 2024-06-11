package com.example.backend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MigrationController {
//    private JobLauncher jobLauncher;
//
//    private Job job;

//    public MigrationController(JobLauncher jobLauncher, Job job) {
//        this.jobLauncher = jobLauncher;
//        this.job = job;
//    }

    @GetMapping
    public String migrateCustomer() {
//        try {
//            jobLauncher.run(job, new JobParameters());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Migration failed!";
//        }
        return "Migration completed!";
    }
}
