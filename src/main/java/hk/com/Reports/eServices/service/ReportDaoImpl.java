package hk.com.Reports.eServices.service;

import hk.com.Reports.eServices.model.Report;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ReportDaoImpl implements ReportDao{

    @Autowired
    private SessionFactory sessionFactory;

    public ReportDaoImpl() {
    }

    @Override
    public List<Report> listAllReports() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Report.class);
        return c.list();
    }

    @Override
    public Report getReport(int id) {
        Session session = sessionFactory.getCurrentSession();
        Report report = (Report) session.get(Report.class, id);
        return report;
    }
}
