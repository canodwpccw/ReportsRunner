package hk.com.Reports.eServices.model;

import com.sun.istack.internal.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="Reports")
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    @NotNull
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

    @Column(name = "parameters")
    private String parameters;

    @Column(name = "templateFilename")
    private String templateFilename;

    @Column(name = "isActive")
    @Setter(AccessLevel.NONE)
    private Integer isActive;

    @Column(name = "isDaily")
    @Setter(AccessLevel.NONE)
    private Integer isDaily;

    @Column(name = "isWeekly")
    @Setter(AccessLevel.NONE)
    private Integer isWeekly;

    @Column(name = "isMonthly")
    @Setter(AccessLevel.NONE)
    private Integer isMonthly;

    @Column(name = "isYearly")
    @Setter(AccessLevel.NONE)
    private Integer isYearly;

    @Transient
    private Boolean isActiveBool;
    @Transient
    private Boolean isDailyBool;
    @Transient
    private Boolean isWeeklyBool;
    @Transient
    private Boolean isMonthlyBool;
    @Transient
    private Boolean isYearlyBool;
    @Transient
    private MultipartFile[] multipartFiles;

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsDaily() {
        return 1;
    }

    public void setIsDaily(Integer isDaily) {
        this.isDaily = isDaily;
    }

    public Integer getIsWeekly() {
        return 1;
    }

    public void setIsWeekly(Integer isWeekly) {
        this.isWeekly = isWeekly;
    }

    public Integer getIsMonthly() {
        return 1;
    }

    public void setIsMonthly(Integer isMonthly) {
        this.isMonthly = isMonthly;
    }

    public Integer getIsYearly() {
        return 1;
    }

    public void setIsYearly(Integer isYearly) {
        this.isYearly = isYearly;
    }
}