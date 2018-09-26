package hk.com.Reports.eServices.service;

import hk.com.Reports.eServices.model.Report;

import java.io.IOException;
import java.util.List;

public interface ReportService {
    public void testMethod();
    public Report saveOrUpdate(Report report) throws IOException;
    public Report getReportByID(int id);
    public List<Report> getAllReport();
    public List<Report> getAllDailyReports();
    public List<Report> getAllMonthlyReports();
    public List<Report> getAllYearlyReports();
}
