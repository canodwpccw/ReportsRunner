package hk.com.Reports.eServices.controller;

import hk.com.Reports.eServices.model.Report;
import hk.com.Reports.eServices.dao.ReportDao;
import hk.com.Reports.eServices.model.utility.HiddenModelBean;
import hk.com.Reports.eServices.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.QueryParam;
import java.sql.SQLException;
import java.text.ParseException;
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
        mav.setViewName("index2");
        mav.addObject("hiddenModelBean",new HiddenModelBean());
        List<Report> reports =  reportService.getAllReport();
        mav.addObject("reports", reports);
        mav.addObject("datesInStr", reportService.getDatesInStrFmt());
        return mav;
    }

    @RequestMapping(value = "/getReportById/{id}",method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public Report getReportById(@PathVariable("id") int id){
        return reportService.getReportByID(id);
    }



    @RequestMapping(value = "/getAllDailyReport",method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public List<Report> getAllDailyReport(){ return reportService.getAllDailyReports(); }

    @RequestMapping(value = "/getAllMonthlyReport",method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public List<Report> getAllMonthlyReport(){ return reportService.getAllMonthlyReports(); }

    @RequestMapping(value = "/getAllYearlyReport",method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public List<Report> getAllYearlyReport(){ return reportService.getAllYearlyReports(); }

    @RequestMapping(value = "/deleteByID", method = RequestMethod.POST, produces ="application/json")
    @ResponseBody
    public List<Report> deleteByID(@QueryParam("id") int id) {
        return reportService.deleteByID(id);
    }

    @RequestMapping(value = "/generateDailyReports",method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public List<Report> generateDailyReports() {
        List<Report> dailyReports = reportService.getAllDailyReports();
        for(Report report:dailyReports){
            System.out.println("Generating " + report.getReportId() + "...");
            try {
                reportService.generatePDF(report,report.getFrequency());
                System.out.println( "SUCCESS!" );
            } catch (ParseException e) {
                System.out.println( "FAILED!" );
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println( "FAILED!" );
                e.printStackTrace();
            } catch (JRException e) {
                System.out.println( "FAILED!" );
                e.printStackTrace();
            }finally {
                continue;
            }
        }
        return dailyReports;
    }

}
