package hk.com.Reports.eServices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView index(Model m){
        ModelAndView mav = new ModelAndView();
        mav.addObject("test","hello world");
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ModelAndView test(Model m){
        ModelAndView mav = new ModelAndView();
        mav.addObject("test","hello world");
        mav.setViewName("index");
        return mav;
    }

}
