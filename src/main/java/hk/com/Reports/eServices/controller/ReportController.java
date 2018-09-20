package hk.com.Reports.eServices.controller;

import hk.com.Reports.eServices.model.Report;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Controller
public class ReportController {

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
    public ModelAndView addReportPost(@ModelAttribute("report") Report report) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("addOrEditReport");
        return mav;
    }

//    @RequestMapping(value = "/addReport", method = RequestMethod.POST)
//    public @ResponseBody String upload( MultipartHttpServletRequest request, HttpServletResponse response,@ModelAttribute("report")Report report) throws IOException {
//        File jasperFile = getFile(request);
//        String parameters = request.getParameter("parameters");
//        return  jasperFile.getName() + " upload successful!" + "with parameters: " + parameters;
//
//    }

    private File getFile(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = request.getFile(itr.next());

        InputStream in = mpf.getInputStream();
        File jasperFile = new File(mpf.getOriginalFilename());
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
