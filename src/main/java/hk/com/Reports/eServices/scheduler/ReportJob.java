package hk.com.Reports.eServices.scheduler;

import hk.com.Reports.eServices.service.ReportService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
@EnableScheduling
@EnableAsync
public class ReportJob {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ReportService reportService;

    @Scheduled(cron = "*/2 * * * * *")
    public void runTestDaily(){
        System.out.println("1 test test");
        reportService.testMethod();
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void runTestWeekly(){
        System.out.println("2 test test");
        reportService.testMethod();
    }

}