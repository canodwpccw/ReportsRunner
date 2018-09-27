package hk.com.Reports.eServices.scheduler;

import hk.com.Reports.eServices.model.Report;
import hk.com.Reports.eServices.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

@Configuration
@EnableScheduling
@EnableAsync
public class ReportJob {

    @Autowired
    ReportService reportService;

    @Scheduled(cron = "*/10 * * * * *")
    public void runTestDaily(){
        List<Report> dailyReports = reportService.getAllDailyReports();
        for(Report report:dailyReports){
            System.out.println("Generating " + report.getReportId() + "...");
            try {
                reportService.generatePDF(report,"DAILY");
                System.out.println( "SUCESS!" );
            } catch (ParseException e) {
                System.out.println( "FAILED!" );
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println( "FAILED!" );
                e.printStackTrace();
            } catch (JRException e) {
                System.out.println( "FAILED!" );
                e.printStackTrace();
            }finally {
                continue;
            }
        }
    }

//    @Scheduled(cron = "*/5 * * * * *")
//    public void runTestWeekly(){
//        System.out.println("2 test test");
//        reportService.testMethod();
//    }

}