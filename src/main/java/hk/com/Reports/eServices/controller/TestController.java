package hk.com.Reports.eServices.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hk.com.Reports.eServices.model.Report;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private SessionFactory sessionFactory;

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
        Map<String, Object> parameters = prepareParameters(report.getParameters());
    }


}
