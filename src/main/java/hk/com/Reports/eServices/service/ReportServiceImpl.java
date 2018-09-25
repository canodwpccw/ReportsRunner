package hk.com.Reports.eServices.service;

import hk.com.Reports.eServices.dao.ReportDao;
import hk.com.Reports.eServices.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    ReportDao reportDao;

    @Override
    public void testMethod() {
        System.out.println("test method only!");
    }

    @Override
    public void generateDailyReports() {
        List<Report> reportList = reportDao.getAll();
        //generate report using data

    }
}
