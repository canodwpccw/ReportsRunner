package hk.com.Reports.eServices.dao;

import hk.com.Reports.eServices.model.Report;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ReportDaoImpl extends GenericDaoImpl<Report, Integer> implements ReportDao{


    @Autowired
    private SessionFactory sessionFactory;

    public ReportDaoImpl() {
    }


    @Override
    public List<Report> getDailyReport() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Report.class);
        c.add(Restrictions.eq("isDaily",true));
        return c.list();

    }

    @Override
    public List<Report> getMonthlyReport() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Report.class);
        c.add(Restrictions.eq("isMonthly",true));
        return c.list();

    }

    @Override
    public List<Report> getYearlyReport() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Report.class);
        c.add(Restrictions.eq("isYearly",true));
        return c.list();
    }


}
