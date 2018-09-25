package hk.com.Reports.eServices.scheduler;

import hk.com.Reports.eServices.service.ReportService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

public class ReportJob implements Job {

    @Autowired
    private ReportService reportService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        reportService.testMethod();
    }

}
