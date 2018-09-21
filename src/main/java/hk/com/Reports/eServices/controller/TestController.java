package hk.com.Reports.eServices.controller;

import hk.com.Reports.eServices.model.ESGEN008;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView indexGet(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("esgen");
        mav.addObject("esgen",new ESGEN008());
        return mav;
    }

}
