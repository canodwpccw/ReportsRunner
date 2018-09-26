package hk.com.Reports.eServices.service;

import hk.com.Reports.eServices.dao.ReportDao;
import hk.com.Reports.eServices.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    private Environment env;
    @Autowired
    private ReportDao reportDao;

    @Override
    public void testMethod() {
        System.out.println("test method only!");
    }

    @Override
    public Report saveOrUpdate(Report report) throws IOException {
        boolean isUploaded = uploadJasperFiles(report);
        return (isUploaded)? reportDao.saveOrUpdate(report):report;
    }

    public boolean uploadJasperFiles(Report report) {
        boolean isUploaded = false;
        for(MultipartFile mf :report.getMultipartFiles()){
            File reportDir = new File(env.getProperty("report.location") + report.getReportId());
            if(!reportDir.exists())reportDir.mkdirs();
            try {
                mf.transferTo(new File(reportDir.getAbsolutePath()+"//"+ mf.getOriginalFilename()));
                isUploaded =true;
            } catch (IOException e) {
                e.printStackTrace();
                isUploaded =false;
            }
        }
        return isUploaded;
    }

}
