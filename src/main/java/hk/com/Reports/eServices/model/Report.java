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

    @Column(name = "isActive")
    private Boolean isActive;

    @Column(name = "reportId")
    private String reportId;

    @Column(name = "reportTitle")
    private String reportTitle;

    @Column(name = "templateType")
    private String templateType;

    @Column(name = "module")
    private String module;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "parameters")
    private String parameters;

    @Column(name = "templateFilename")
    private String templateFilename;

    @Column(name = "dBSource")
    private String dBSource;

    @Column(name = "preStoredProc")
    private String preStoredProc;

    @Column(name = "postStoredProc")
    private String postStoredProc;

    @Transient
    private MultipartFile[] multipartFiles;

}