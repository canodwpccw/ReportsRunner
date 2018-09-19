package hk.com.Reports.eServices.controller;

import hk.com.Reports.eServices.model.Report;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {

    @RequestMapping(value = "/addReport",method = RequestMethod.GET)
    public ModelAndView addReportGet(){
        ModelAndView mav =  new ModelAndView();
        mav.setViewName("addOrEditReport");
        mav.addObject("report",new Report());
        mav.addObject("action","add");
        return mav;
    }

    @RequestMapping(value = "/addReport",method = RequestMethod.POST)
    public ModelAndView addReportPost(@ModelAttribute("report")Report report){
        ModelAndView mav =  new ModelAndView();
        mav.setViewName("addOrEditReport");
        report.getId();
        mav.addObject("action","add");
        return mav;
    }
}
