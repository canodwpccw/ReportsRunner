package hk.com.Reports.eServices.service;

import hk.com.Reports.eServices.model.Report;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ReportService {
    public void testMethod();
    public Report saveOrUpdate(Report report) throws IOException;
    public Report getReportByID(int id);
    public List<Report> getAllReport();
    public List<Report> getAllDailyReports();
    public List<Report> getAllMonthlyReports();
    public List<Report> getAllYearlyReports();
    public void generatePDF(Report report,String frequency) throws ParseException, SQLException, JRException;
    public byte[] generatePDFCrystalReportStream(Report report);
    public byte[] generatePDFCrystalReportStreamGet(String reportId, Map<String,Object> param);
    public List<Report> deleteByID(int id);
    public HashMap<String, String> getDatesInStrFmt();
    public byte[] requestToGeneratePDF(String reportId, Map<String,Object> param);
}
