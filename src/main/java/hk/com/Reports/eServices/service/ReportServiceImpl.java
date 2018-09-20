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
    public void uploadJasperFiles(Report report) throws IOException {
        for(MultipartFile mf :report.getMultipartFiles()){
            File reportDir = new File(env.getProperty("report.location") + report.getReportId());
            if(!reportDir.exists())reportDir.mkdirs();
            mf.transferTo(new File(reportDir.getAbsolutePath()+"//"+ mf.getOriginalFilename()));
        }
    }

    @Override
    public Report saveOrUpdate(Report report) {
        return reportDao.saveOrUpdate(report);
    }

}
