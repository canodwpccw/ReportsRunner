package hk.com.Reports.eServices.dao;

import hk.com.Reports.eServices.model.Report;

import java.util.List;

public interface ReportDao extends GenericDao <Report, String > {
    public List<Report> listAllReports();
    public Report getReport(int id);

    public List<Report> getAllReport();
    public Report addNewReport();
    public Report editReport();
    public Report getReportByID(int id);

}
