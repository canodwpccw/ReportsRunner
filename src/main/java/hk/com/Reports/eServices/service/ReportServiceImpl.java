package hk.com.Reports.eServices.service;

import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.data.IConnectionInfo;
import com.crystaldecisions.sdk.occa.report.data.ITable;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.PropertyBag;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hk.com.Reports.eServices.dao.ReportDao;
import hk.com.Reports.eServices.model.Report;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    private Environment env;
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private SessionFactory sessionFactory;
    private final DateTimeFormatter JASPER_STRING_DATE_FORMAT_PARAM = DateTimeFormatter.ofPattern("yyyyMMdd");
    private final DateTimeFormatter STRING_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd.HHmmss");
    private final LocalDateTime TODAY = LocalDateTime.now();


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

        return reportDao.getDailyReport();
    }

    @Override
    public List<Report> getAllMonthlyReports() {
        return reportDao.getMonthlyReport();
    }

    @Override
    public List<Report> getAllYearlyReports() {
        return reportDao.getYearlyReport();
    }

    @Override
    public void generatePDF(Report report,String frequency) throws ParseException, SQLException, JRException {
        if ((report.getTemplateType().equalsIgnoreCase("jasper"))) {
            generatePDFJasperReport(report, frequency);
        } else {
            generatePDFCrystalReport(report, frequency);
        }
        reportDao.updateLastRun(report.getId());
    }

    @Override
    public List<Report> deleteByID(int id) {
        return reportDao.deleteByID(id);
    }

    @Override
    public HashMap<String, String> getDatesInStrFmt() {
        HashMap<String,String> datesInStr = new HashMap<>();
        datesInStr.put("dailyStartdate", JASPER_STRING_DATE_FORMAT_PARAM.format(TODAY.minus(1, DAYS) ));
        datesInStr.put("dailyEnddate", JASPER_STRING_DATE_FORMAT_PARAM.format(TODAY.minus(1, DAYS) ));
        datesInStr.put("monthlyStartdate", JASPER_STRING_DATE_FORMAT_PARAM.format(TODAY.withDayOfMonth(1)));
        datesInStr.put("monthlyEnddate", JASPER_STRING_DATE_FORMAT_PARAM.format(TODAY.with(TemporalAdjusters.lastDayOfMonth())));
        datesInStr.put("yearlyStartdate", JASPER_STRING_DATE_FORMAT_PARAM.format(TODAY.with(TemporalAdjusters.firstDayOfYear())));
        datesInStr.put("yearlyEnddate", JASPER_STRING_DATE_FORMAT_PARAM.format(TODAY.with(TemporalAdjusters.lastDayOfYear())));
        return datesInStr;
    }

    private boolean uploadJasperFiles(Report report) {
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
    private Map<String, Object> prepareParameters(String parameters,String frequency) {
        Map<String, Object> jasperParam = prepareParameters(parameters);
        String startdate ="";
        String enddate ="";
        switch(frequency) {
            case "DAILY":
                startdate = enddate =  JASPER_STRING_DATE_FORMAT_PARAM.format(TODAY.minus(1, DAYS) );
                break;
            case "MONTHLY" :
                startdate = JASPER_STRING_DATE_FORMAT_PARAM.format(TODAY.withDayOfMonth(1));
                enddate = JASPER_STRING_DATE_FORMAT_PARAM.format(TODAY.with(TemporalAdjusters.lastDayOfMonth()));
                break;
            case "YEARLY" :
                startdate = JASPER_STRING_DATE_FORMAT_PARAM.format(TODAY.with(TemporalAdjusters.firstDayOfYear()));
                enddate = JASPER_STRING_DATE_FORMAT_PARAM.format(TODAY.with(TemporalAdjusters.lastDayOfYear()));
                break;
            default :
                startdate = enddate = JASPER_STRING_DATE_FORMAT_PARAM.format(TODAY.minus(1, DAYS));
        }
        jasperParam.put("startdate", startdate);
        jasperParam.put("enddate", enddate);

        jasperParam.put("TRANS_DATE",new java.sql.Date(new java.util.Date().getTime()));
        jasperParam.put("TRANS_YEAR",new java.sql.Date(new java.util.Date().getTime()));
        jasperParam.put("PRINT_DATE",new java.sql.Date(new java.util.Date().getTime()));
        jasperParam.put("PRINT_TIME",new java.sql.Date(new java.util.Date().getTime()));
        System.out.println("PARAMETERS:\n" + jasperParam.toString());
        return jasperParam;
    }
    private void generatePDFJasperReport(Report report, String frequency) throws SQLException, JRException, ParseException {
        Map<String, Object> parameters = prepareParameters(report.getParameters(),frequency);
        final String ROOT_FOLDER = env.getProperty("report.location") + report.getReportId() + "\\";
        final String TEMPLATE = report.getReportId() + ".jasper";
        final String PDF = STRING_DATETIME_FORMAT.format(TODAY.minus(1, DAYS)) + "_" + report.getReportId() + ".pdf";
        JasperPrint jasperPrint = JasperFillManager.fillReport(ROOT_FOLDER + TEMPLATE, parameters, getDBConnection());
        JasperExportManager.exportReportToPdfFile(jasperPrint, ROOT_FOLDER + PDF);
    }
    private Connection getDBConnection() throws SQLException {
        return sessionFactory.
                getSessionFactoryOptions().getServiceRegistry().
                getService(ConnectionProvider.class).getConnection();
    }

    public void generatePDFCrystalReport(Report report, String frequency){
        HashMap<String,String> datasInStr =  getDatesInStrFmt();
        final String ROOT_FOLDER = env.getProperty("report.location") + report.getReportId() + "\\";
        final String TEMPLATE = report.getReportId() + ".rpt";
        final String PDF = STRING_DATETIME_FORMAT.format(TODAY.minus(1, DAYS)) + "_" + report.getReportId() + ".pdf";
        String report_name = ROOT_FOLDER + TEMPLATE;
        String exportFileName = ROOT_FOLDER + PDF;
        try {
            ReportClientDocument clientDoc = new ReportClientDocument();
            clientDoc.open(report_name, ReportExportFormat._PDF);
            ITable table = clientDoc.getDatabaseController().getDatabase().getTables().getTable(0);
            IConnectionInfo connectionInfo = table.getConnectionInfo();
            PropertyBag propertyBag = connectionInfo.getAttributes();
            propertyBag.clear();

            propertyBag.put("URI", env.getProperty("db.eService.crystal.URI"));
            propertyBag.put("Use JDBC", "true");
            propertyBag.put("Database DLL", env.getProperty("db.eService.crystal.dll"));

            connectionInfo.setAttributes(propertyBag);

            connectionInfo.setUserName(env.getProperty("db.eService.crystal.username"));
            connectionInfo.setPassword(env.getProperty("db.eService.crystal.password"));
            table.setConnectionInfo(connectionInfo);
            clientDoc.getDatabaseController().setTableLocation(table, table);
/** START OF SETTING PARAMETERS **/
            Map<String, Object> parameters = prepareParameters(report.getParameters());
            for (String key : parameters.keySet()) {
                clientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", key, (String)parameters.get(key));
            }
/** START OF CREATING PDF FILE **/
            ByteArrayInputStream bais = (ByteArrayInputStream) clientDoc.getPrintOutputController().export(ReportExportFormat.PDF);
            int size = bais.available();
            byte[] barray = new byte[size];
            FileOutputStream fos = new FileOutputStream(new File(exportFileName));
            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
            int bytes = bais.read(barray, 0, size);
            baos.write(barray, 0, bytes);
            baos.writeTo(fos);
            clientDoc.close();
            bais.close();
            baos.close();
            fos.close();
        }
         catch (ReportSDKException ex) {
            System.out.println("ReportSDKException" + ex);
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }
    }
    private Map<String, Object> prepareParameters(String parameters){
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        return (Map)gson.fromJson(parameters, type);
    }

}
