package hk.com.Reports.eServices.dao;

import hk.com.Reports.eServices.model.Report;

import java.util.List;

public interface ReportDao extends GenericDao <Report, Integer> {
    public Report saveOrUpdate(Report report);
    public List<Report> listAll();
    public Report getByID(int id);
    public List<Report> getDailyReport();
    public List<Report> getMonthlyReport();
    public List<Report> getYearlyReport();
    public List<Report> deleteByID(int id);
}
