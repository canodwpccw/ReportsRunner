package hk.com.Reports.eServices.dao;

import hk.com.Reports.eServices.model.Report;

import java.util.List;

public interface ReportDao {
    public List<Report> listAllReports();
    public Report getReport(int id);
}
