package hk.com.Reports.eServices.controller;

import hk.com.Reports.eServices.model.Report;
import hk.com.Reports.eServices.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/addReport", method = RequestMethod.GET)
    public ModelAndView addReportGet() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("addOrEditReport");
        mav.addObject("report", new Report());
        mav.addObject("action", "add");
        return mav;
    }

    @RequestMapping(value = "/editReport", method = RequestMethod.GET)
    public ModelAndView editReportGet() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("addOrEditReport");
        mav.addObject("report", new Report());
        mav.addObject("action", "edit");
        return mav;
    }

    @RequestMapping(value = "/addReport", method = RequestMethod.POST)
    public ModelAndView addReportPost(@ModelAttribute("report") Report report) throws IOException {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("addOrEditReport");
        System.out.println(report.toString());
        report.setReportId("testReportID_12345");
        reportService.saveOrUpdate(report);
        return mav;
    }



    private File getFile(MultipartFile multipartFile) throws IOException {
        InputStream in = multipartFile.getInputStream();
        File jasperFile = new File(multipartFile.getOriginalFilename());
        String path = jasperFile.getAbsolutePath();
        FileOutputStream f = new FileOutputStream(path);
        int ch = 0;
        while ((ch = in.read()) != -1) {
            f.write(ch);
        }
        f.flush();
        f.close();
        return jasperFile;
    }


}
