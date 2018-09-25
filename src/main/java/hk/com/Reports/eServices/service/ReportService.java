package hk.com.Reports.eServices.service;

import hk.com.Reports.eServices.model.Report;

import java.io.IOException;

public interface ReportService {
    public void testMethod();
    public Report saveOrUpdate(Report report) throws IOException;
}
