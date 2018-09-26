package hk.com.Reports.eServices.service;

import hk.com.Reports.eServices.dao.ReportDao;
import hk.com.Reports.eServices.model.Report;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    private Environment env;
    @Autowired
    private ReportDao reportDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional

    @Override
    public void testMethod() {
        System.out.println("test method only!");
    }

    @Override
    public Report saveOrUpdate(Report report) throws IOException {
        boolean isUploaded = uploadJasperFiles(report);
        if(isUploaded){
            report.setTimestamp(new Date());
            report.setDateCreated(new Date());
            report.setIsActive(true);
            report = reportDao.saveOrUpdate(report);
        }
        return report;
    }

    @Override
    public Report getReportByID(int id) {
        return reportDao.getByID(id);
    }

    @Override
    public List<Report> getAllReport() {
        return reportDao.listAll();
    }

    @Override
    public List<Report> getAllDailyReports() {
        return null;
    }

    @Override
    public List<Report> getAllMonthlyReports() {
        return null;
    }

    @Override
    public List<Report> getAllYearlyReports() {
        return null;
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
