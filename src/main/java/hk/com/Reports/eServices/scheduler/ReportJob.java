package hk.com.Reports.eServices.scheduler;

import hk.com.Reports.eServices.service.ReportService;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.logging.Logger;

@Configuration
@EnableScheduling
@EnableAsync
public class ReportJob {

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