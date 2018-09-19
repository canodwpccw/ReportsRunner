package hk.com.Reports.eServices.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;
import java.util.Date;


@Entity
@Table(name="Reports")
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "modifier")
    private String modifier;

    @Column(name = "dateCreated")
    private Date dateCreated;

    @Column(name = "timestamp")
    private Date timestamp;

    @Column(name = "reportId")
    private String reportId;

    @Column(name = "isActive")
    private Boolean isActive;

    @Column(name = "parameters")
    private String parameters;

    @Column(name = "templateFilename")
    private String templateFilename;

    @Column(name = "isDaily")
    private Boolean isDaily;

    @Column(name = "isWeekly")
    private Boolean isWeekly;

    @Column(name = "isMonthly")
    private Boolean isMonthly;

    @Column(name = "isYearly")
    private Boolean isYearly;

    @Transient
    private MultipartFile file;

}