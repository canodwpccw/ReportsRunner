package hk.com.Reports.eServices.controller;

import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hk.com.Reports.eServices.model.Report;
import hk.com.Reports.eServices.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import hk.com.Reports.eServices.model.ESGEN008;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;


@Controller
public class TestController {


    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = {"/generateReport"}, method = {org.springframework.web.bind.annotation.RequestMethod.POST})
    public ModelAndView addReportPost(@ModelAttribute("report") Report report)
            throws IOException, SQLException, JRException, ParseException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("esgen");

        report.setReportId("ESGEN008");
        report.setTemplateFilename("ESGEN008.jasper");
        Map<String, Object> parameters = prepareParameters(report.getParameters());
        System.out.println(parameters.toString());
        generateReport(report);
        return mav;
    }

    private Connection getDBConnection() throws SQLException {
        return sessionFactory.
                getSessionFactoryOptions().getServiceRegistry().
                getService(ConnectionProvider.class).getConnection();
    }


    private Map<String, Object> prepareParameters(String parameters) throws ParseException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, Object> myMap = (Map) gson.fromJson(parameters, type);

        myMap.remove("TRANS_DATE");
        myMap.remove("TRANS_YEAR");
        myMap.remove("PRINT_DATE");
        myMap.remove("PRINT_TIME");

        String startdate = (String) myMap.get("startdate");
        String enddate = (String) myMap.get("enddate");

        myMap.put("startdate", new String(convertDateToString(convertToDate(startdate))));
        myMap.put("enddate", new String(convertDateToString(convertToDate(enddate))));

        System.out.println(myMap.toString());
        return myMap;
    }

    private void generateReport(Report report) throws SQLException, JRException, ParseException {
        Map<String, Object> parameters = prepareParameters(report.getParameters());
        System.out.println("parameters : " + parameters);
        String ROOT_FOLDER = "C:\\reports\\ESGEN008\\";
        JasperPrint jasperPrint = JasperFillManager.fillReport("C:\\reports\\ESGEN008\\" + report.getTemplateFilename(), parameters, getDBConnection());
        JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\reports\\ESGEN008\\\\ESGEN008.pdf");
    }

    @RequestMapping(value = {"/index"}, method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public ModelAndView indexGet() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("esgen");
        mav.addObject("report", new Report());
        return mav;
    }

    Date convertToDate(String receivedDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Date date = format.parse(receivedDate);
        return date;
    }

    private String convertDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    @RequestMapping(value = "/testCrystal", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String testCrystal(){
//        reportService.generatePDFCrystalReport();
        return "asdasdasdasd";
    }

}
