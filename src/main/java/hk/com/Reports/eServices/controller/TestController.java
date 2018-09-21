package hk.com.Reports.eServices.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hk.com.Reports.eServices.model.Report;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import hk.com.Reports.eServices.model.ESGEN008;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {


    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/generateReport", method = RequestMethod.POST)
    public ModelAndView addReportPost(@ModelAttribute("report") Report report) throws IOException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("addOrEditReport");
        return mav;
    }

    private Connection getDBConnection() throws SQLException {
        return sessionFactory.
                getSessionFactoryOptions().getServiceRegistry().
                getService(ConnectionProvider.class).getConnection();
    }

    private  Map<String, Object> prepareParameters(String parameters){
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
        return gson.fromJson(parameters, stringStringMap);
    }

    private void generateReport(Report report){

      //  Map<String, Object> parameters = prepareParameters(report.getParameters());

        String reportParams = "[{\"REPORT_ID\":\"ESGEN008\"},{\"LOCATION\":\"asd\"},{\"TRANS_DATE\":\"09-21-2018\"},{\"USER_ID\":\"UID-1234\"},{\"TRANS_YEAR\":\"09-21-2018\"},{\"startdate\":\"09-21-2018\"},{\"enddate\":\"09-21-2018\"},{\"sub1_data0\":\"HKS\"},{\"sub1_data1\":\"TD-EKO\"},{\"sub2_data0\":\"EKS\"},{\"sub2_data1\":\"A\"}]";

        Map<String, Object> parameters = prepareParameters(reportParams);

        File reportDir = new File(env.getProperty("report.location") + report.getReportId());


        try {

          //  String reportName = "ESGEN001";

            JasperCompileManager.compileReport(reportDir + ".jrxml");

            JasperPrint print = JasperFillManager.fillReport(reportDir + ".jasper", parameters, getDBConnection());

            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(reportDir + ".pdf"));

            exporter.exportReport();

        } catch (Exception e) {
            throw new RuntimeException("Error Generating Report", e);
        }

    }

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView indexGet(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("esgen");
        mav.addObject("esgen",new ESGEN008());
        return mav;
    }

}
