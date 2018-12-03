package hk.com.Reports.eServices.controller;

import com.google.gson.Gson;
import hk.com.Reports.eServices.model.JasperReportDTO;
import hk.com.Reports.eServices.model.Report;
import hk.com.Reports.eServices.model.utility.HiddenModelBean;
import hk.com.Reports.eServices.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;


    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

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
        reportService.saveOrUpdate(report);
        return mav;
    }

    @RequestMapping(value = "/generate/{id}",method = RequestMethod.GET, produces ="application/json")
    @ResponseBody
    public Report getReportById(@PathVariable("id") int id) throws ParseException, SQLException, JRException {
        Report report =  reportService.getReportByID(id);
        reportService.generatePDF(report,"DAILY");
        return report;
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

    @RequestMapping(value = "getPdf", method = RequestMethod.POST, produces = "application/pdf")
    public @ResponseBody void getPdf(@ModelAttribute("hiddenModelBean") HiddenModelBean hiddenModelBean, HttpServletRequest request, HttpServletResponse response) {
        try {
            String json = hiddenModelBean.getHiddenValue();
            Gson gson = new Gson();
            JasperReportDTO jasperReportDTO = gson.fromJson(json, JasperReportDTO.class);
            HashMap<String, Object> result = gson.fromJson(jasperReportDTO.getParameters(),HashMap.class);
            Report report = reportService.getReportByID(Integer.parseInt(jasperReportDTO.getId()));
            if(report.getTemplateType().equalsIgnoreCase("jasper")){
                JasperPrint jasperPrint = JasperFillManager.fillReport
                        (env.getProperty("report.location") + "\\" + report.getReportId() + "\\" + report.getReportId() + "." + report.getTemplateType(),
                                result, dataSource.getConnection());
                JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            }else if(report.getTemplateType().equalsIgnoreCase("rpt")){
                report.setParameters(jasperReportDTO.getParameters());
                byte[] barray= reportService.generatePDFCrystalReportStream(report);
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(barray);
            }
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/createPdf",method = RequestMethod.GET,produces = "application/pdf")
    public @ResponseBody void createPdf(HttpServletResponse response, HttpServletRequest request){
        String ReportID = request.getParameter("ReportID");
        Map<String,Object> param = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println(paramName);
            String[] paramValues = request.getParameterValues(paramName);
            String paramValue = null;
            for (int i = 0; i < paramValues.length; i++) {
                 paramValue=paramValues[i];
                System.out.println(paramValue);
            }
            param.put(paramName,paramValue);
        }
        param.remove("ReportID");
        byte[] barray= reportService.requestToGeneratePDF(ReportID,param);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(barray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public ModelAndView requestPDF() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("demo");
        return mav;
    }

}
