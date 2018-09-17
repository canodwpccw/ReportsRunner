package hk.com.Reports.eServices.model;

import lombok.Data;

import javax.persistence.*;


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

    @Column(name = "reportId")
    private String reportId;

    @Column(name = "parameters")
    private String parameters;

    @Column(name = "templateLocation")
    private String templateLocation;

    @Column(name = "isDaily")
    private Boolean isDaily;

    @Column(name = "isWeekly")
    private Boolean isWeekly;

    @Column(name = "isMonthly")
    private Boolean isMonthly;

    @Column(name = "isYearly")
    private Boolean isYearly;
}