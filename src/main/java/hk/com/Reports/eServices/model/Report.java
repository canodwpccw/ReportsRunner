package hk.com.Reports.eServices.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="Reports")
@Data
public class Report {

    @Id
    @SequenceGenerator(name = "REPORTS_SEQ", sequenceName = "REPORTS_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "REPORTS_SEQ", strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "modifier")
    private String modifier;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateCreated")
    private Date dateCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "reportId")
    private String reportId;

    @Column(name = "reportTitle")
    private String reportTitle;

    @Column(name = "templateType")
    private String templateType;

    @Column(name = "module")
    private String module;

    @Column(name = "parameters")
    private String parameters;

    @Column(name = "templateFilename")
    private String templateFilename;

    @Column(name = "isActive")
    private Boolean isActive;

    @Column(name = "isDaily")
    private Boolean isDaily;

    @Column(name = "isWeekly")
    private Boolean isWeekly;

    @Column(name = "isMonthly")
    private Boolean isMonthly;

    @Column(name = "isYearly")
    private Boolean isYearly;

    @Transient
    private MultipartFile[] multipartFiles;

}