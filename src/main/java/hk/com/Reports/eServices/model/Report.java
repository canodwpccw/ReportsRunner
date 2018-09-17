package hk.com.Reports.eServices.model;

import lombok.Data;

@Data
public class Report {
    private Integer id;
    private String reportId;
    private String parameters;
    private String fileLocation;

}