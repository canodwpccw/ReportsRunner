package hk.com.Reports.eServices.controller;

import hk.com.Reports.eServices.model.Report;
import hk.com.Reports.eServices.dao.ReportDao;
import hk.com.Reports.eServices.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView index(Model m){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        List<Report> reports =  reportService.getAllReport();
        mav.addObject("reports", reports);
        return mav;
    }

    @RequestMapping(value = "/getReportById/{id}",method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public Report getReportById(@PathVariable("id") int id){
        return reportService.getReportByID(id);
    }



    @RequestMapping(value = "/getAllDailyReport",method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public List<Report> getAllDailyReport(){{ return reportService.getAllDailyReports(); } }

    @RequestMapping(value = "/getAllMonthlyReport",method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public List<Report> getAllMonthlyReport(){{ return reportService.getAllMonthlyReports(); } }

    @RequestMapping(value = "/getAllYearlyReport",method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public List<Report> getAllYearlyReport(){{ return reportService.getAllYearlyReports(); } }

}
