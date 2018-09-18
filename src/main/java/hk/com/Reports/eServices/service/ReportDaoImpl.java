package hk.com.Reports.eServices.service;

import hk.com.Reports.eServices.model.Report;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
        Session session = sessionFactory.openSession();
        CriteriaQuery<Report> criteriaQuery = session.getCriteriaBuilder().createQuery(Report.class);
        criteriaQuery.from(Report.class);
        List<Report> reports = session.createQuery(criteriaQuery).getResultList();
        session.close();
        return reports;
    }

    @Override
    public Report getReport(int id) {
        Session session = sessionFactory.openSession();
        CriteriaQuery<Report> criteriaQuery = session.getCriteriaBuilder().createQuery(Report.class);
        criteriaQuery.from(Report.class);
        Report report = session.createQuery(criteriaQuery).getSingleResult();
        session.close();



        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Report> criteria = builder.createQuery(Report.class);
        criteria.where(Restrictions.eq());
        return report;
    }
}
