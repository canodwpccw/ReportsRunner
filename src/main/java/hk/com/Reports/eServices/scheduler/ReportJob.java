package hk.com.Reports.eServices.scheduler;

import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;
import hk.com.Reports.eServices.model.Report;
import hk.com.Reports.eServices.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

    @Scheduled(cron = "* * 1 * * *")
    public void runTestDaily() {
        List<Report> dailyReports = reportService.getAllDailyReports();
        for (Report report : dailyReports) {
            System.out.println("Generating " + report.getReportId() + "...");
            try {
                reportService.generatePDF(report, "DAILY");
                System.out.println("SUCESS!");
            } catch (ParseException e) {
                System.out.println("FAILED!");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("FAILED!");
                e.printStackTrace();
            } catch (JRException e) {
                System.out.println("FAILED!");
                e.printStackTrace();
            } finally {
                continue;
            }
        }
    }

//    @Scheduled(cron = "*/5 * * * * *")
//    public void runCrystal() {
//        System.out.println("2 test test");
//        try {
//            String report_name = "C:\\Users\\81101651\\Downloads\\testcrystal\\demo_1.rpt";
//            String exportFileName = "C:\\Users\\81101651\\Downloads\\testcrystal\\demo_1.pdf";
//            ReportClientDocument clientDoc = new ReportClientDocument();
//            clientDoc.open(report_name, ReportExportFormat._PDF);
//            //Passing Parameter(p_name) to Crystal Report
//            clientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", "p_name", "Welcome to Crystal Reports");
//            //Writing into PDF file
//            ByteArrayInputStream bais = (ByteArrayInputStream) clientDoc.getPrintOutputController().export(ReportExportFormat.PDF);
//            int size = bais.available();
//            byte[] barray = new byte[size];
//            FileOutputStream fos = new FileOutputStream(new File(exportFileName));
//            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
//            int bytes = bais.read(barray, 0, size);
//            baos.write(barray, 0, bytes);
//            baos.writeTo(fos);
//            clientDoc.close();
//            bais.close();
//            baos.close();
//            fos.close();
//        } catch (ReportSDKException ex) {
//            System.out.println(ex);
//        } catch (Exception ex) {
//            System.out.println(ex);
//        }
//    }

}