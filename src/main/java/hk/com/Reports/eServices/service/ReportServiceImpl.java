package hk.com.Reports.eServices.service;

import com.crystaldecisions.reports.sdk.DatabaseController;
import com.crystaldecisions.reports.sdk.ISubreportClientDocument;
import com.crystaldecisions.reports.sdk.ParameterFieldController;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfoKind;
import com.crystaldecisions.sdk.occa.report.data.IConnectionInfo;
import com.crystaldecisions.sdk.occa.report.data.ITable;
import com.crystaldecisions.sdk.occa.report.data.Tables;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.IStrings;
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
    private final DateTimeFormatter  JASPER_STRING_DATE_FORMAT_PARAM = DateTimeFormatter.ofPattern("yyyyMMdd");
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

    @Override
    public byte[] requestToGeneratePDF(String reportId, Map<String,Object> param) {
        final String REPORT_NAME = env.getProperty("report.location") + reportId + "\\" + reportId + ".rpt";
        final String EXPORT_FILE = env.getProperty("report.location") + reportId + "\\" + reportId + ".pdf";


        try {
            com.crystaldecisions.reports.sdk.ReportClientDocument reportClientDoc = new com.crystaldecisions.reports.sdk.ReportClientDocument();
            reportClientDoc.open(REPORT_NAME, 0);
            switch_tables(reportClientDoc.getDatabaseController());
            IStrings subreportNames = reportClientDoc.getSubreportController().getSubreportNames();
            for (int i = 0; i < subreportNames.size(); i++ ) {
                ISubreportClientDocument subreportClientDoc = reportClientDoc.getSubreportController().getSubreport(subreportNames.getString(i));

                switch_tables(subreportClientDoc.getDatabaseController());
            }
            reportClientDoc.getReportSource();
            System.out.println("Setting Params");
            ParameterFieldController paramFieldController = reportClientDoc.getDataDefController().getParameterFieldController();


            for (String key : param.keySet()) {
                paramFieldController.setCurrentValue("",key, (String)param.get(key));
            }
            System.out.println("Finished Setting Params");
            System.out.println("Generating PDF");
            ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream)reportClientDoc.getPrintOutputController().export(ReportExportFormat.PDF);
            System.out.println("Done");
            return  writeToFileSystem(byteArrayInputStream, EXPORT_FILE);
        }
        catch(ReportSDKException ex) {
            System.out.println(ex);
        }
        catch(Exception ex) {
            System.out.println(ex);
        }
        return new byte[0];
    }

    public void switch_tables(DatabaseController databaseController) throws ReportSDKException {
        final String SERVERNAME = "dedaps";
        final String CONNECTION_STRING =
                "Use JDBC=b(true);Connection URL=s("+env.getProperty("db.eService.crystal.host") +");"
                +"Database Class Name=s("+env.getProperty("db.eService.driver")+");"
                +"Server=s(dedaps);User ID=s("+env.getProperty("db.eService.crystal.username")+");"
                +"Password=;Trusted_Connection=b(false);"
                +"JDBC Connection String=s(!oracle.jdbc.driver.OracleDriver!jdbc:oracle:thin:"+env.getProperty("db.eService.crystal.username")
                +"/"+env.getProperty("db.eService.crystal.password")+ env.getProperty("db.ep2.host") +")";


        System.out.println("CONNECTION_STRING \n" + CONNECTION_STRING);


        final String JNDI_DATASOURCE_NAME = "testing";
        final String DATABASE_CLASS_NAME = env.getProperty("db.eService.driver");
        final String DATABASE_DLL = "crdb_jdbc.dll";
        final String DBURI = env.getProperty("db.eService.crystal.URI");
        final String DBUSERNAME = env.getProperty("db.eService.crystal.username");
        final String DBPASSWORD = env.getProperty("db.eService.crystal.password");


        Tables tables = databaseController.getDatabase().getTables();

        for (int i = 0; i < tables.size(); i++) {
            ITable table = tables.getTable(i);
            table.setName(table.getName());
            table.setAlias(table.getAlias());
            IConnectionInfo connectionInfo = table.getConnectionInfo();
            PropertyBag innerProp = connectionInfo.getAttributes();
            innerProp.clear();
            PropertyBag propertyBag = new PropertyBag();

            propertyBag.put("Trusted_Connection", "b(false)");
            propertyBag.put("Server Name", SERVERNAME);
            propertyBag.put("Connection String", CONNECTION_STRING);
            propertyBag.put("Server Type", "JDBC (JNDI)");
            propertyBag.put("JNDI Datasource Name", JNDI_DATASOURCE_NAME);
            propertyBag.put("Database Class Name", DATABASE_CLASS_NAME);
            propertyBag.put("Use JDBC", "true");
            propertyBag.put("URI", DBURI);
            propertyBag.put("Database DLL", DATABASE_DLL);

            connectionInfo.setAttributes(propertyBag);
            connectionInfo.setUserName(DBUSERNAME);
            connectionInfo.setPassword(DBPASSWORD);
            connectionInfo.setKind(ConnectionInfoKind.SQL);

            table.setConnectionInfo(connectionInfo);
            databaseController.setTableLocation(table, tables.getTable(i));
        }
    }

    public byte[] writeToFileSystem(ByteArrayInputStream byteArrayInputStream, String exportFile) throws Exception {
        byte byteArray[] = new byte[byteArrayInputStream.available()];
        File file = new File(exportFile);
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(byteArrayInputStream.available());
        int x = byteArrayInputStream.read(byteArray, 0, byteArrayInputStream.available());

        byteArrayOutputStream.write(byteArray, 0, x);
        byteArrayOutputStream.writeTo(fileOutputStream);

        //Close streams.
        byteArrayInputStream.close();
        byteArrayOutputStream.close();
        fileOutputStream.close();
        return byteArray;

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
        File file = new File(ROOT_FOLDER + PDF);
        try(FileOutputStream fop = new FileOutputStream(file)) {
            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            // get the content in bytes
            byte[] contentInBytes = JasperExportManager.exportReportToPdf(jasperPrint);;

            fop.write(contentInBytes);
            fop.flush();
            fop.close();
            System.out.println("Done");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Connection getDBConnection() throws SQLException {
        return sessionFactory.
                getSessionFactoryOptions().getServiceRegistry().
                getService(ConnectionProvider.class).getConnection();
    }

    public void generatePDFCrystalReport(Report report,String frequency){
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

    @Override
    public byte[] generatePDFCrystalReportStream(Report report){
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
            int bytes = bais.read(barray, 0, size);
            clientDoc.close();
            bais.close();
            fos.close();
            return barray;
        }
        catch (ReportSDKException ex) {
            System.out.println("ReportSDKException" + ex);
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }
        return null;
    }

    @Override
    public byte[] generatePDFCrystalReportStreamGet(String reportId,Map<String,Object>param){
        HashMap<String,String> datasInStr =  getDatesInStrFmt();
        final String ROOT_FOLDER = env.getProperty("report.location") + reportId + "\\";
        final String TEMPLATE = reportId + ".rpt";
        final String PDF = STRING_DATETIME_FORMAT.format(TODAY.minus(1, DAYS)) + "_" + reportId + ".pdf";
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
            for (String key : param.keySet()) {
                clientDoc.getDataDefController().getParameterFieldController().setCurrentValue("", key, (String)param.get(key));
            }
/** START OF CREATING PDF FILE **/
            ByteArrayInputStream bais = (ByteArrayInputStream) clientDoc.getPrintOutputController().export(ReportExportFormat.PDF);
            int size = bais.available();
            byte[] barray = new byte[size];
            FileOutputStream fos = new FileOutputStream(new File(exportFileName));
            int bytes = bais.read(barray, 0, size);
            clientDoc.close();
            bais.close();
            fos.close();
            return barray;
        }
        catch (ReportSDKException ex) {
            System.out.println("ReportSDKException" + ex);
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        }
        return null;
    }
    private Map<String, Object> prepareParameters(String parameters){
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        return (Map)gson.fromJson(parameters, type);
    }

}
