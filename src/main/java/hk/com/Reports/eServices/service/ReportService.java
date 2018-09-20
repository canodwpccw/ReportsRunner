package hk.com.Reports.eServices.service;


import hk.com.Reports.eServices.model.Report;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ReportService {
    public void uploadJasperFiles(Report report) throws IOException;
    public Report saveOrUpdate(Report report);
}
