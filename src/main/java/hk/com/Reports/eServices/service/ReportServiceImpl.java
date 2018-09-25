package hk.com.Reports.eServices.service;

import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService{

    @Override
    public void testMethod() {
        System.out.println("test method only!");
    }
}
