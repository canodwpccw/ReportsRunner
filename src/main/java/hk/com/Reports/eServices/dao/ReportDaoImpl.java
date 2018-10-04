package hk.com.Reports.eServices.dao;

import hk.com.Reports.eServices.model.Report;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.Date;
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
        c.add(Restrictions.eq("frequency","daily"));
        c.add(Restrictions.eq("isActive",true));
        return c.list();

    }

    @Override
    public List<Report> getMonthlyReport() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Report.class);
        c.add(Restrictions.eq("frequency","monthly"));
        c.add(Restrictions.eq("isActive",true));
        return c.list();

    }

    @Override
    public List<Report> getYearlyReport() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Report.class);
        c.add(Restrictions.eq("frequency","yearly"));
        c.add(Restrictions.eq("isActive",true));
        return c.list();
    }

    @Override
    public List<Report> deleteByID(int id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Report.class);
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<Report> criteria = builder.createCriteriaUpdate(Report.class);
        Root<Report> root = criteria.from(Report.class);
        criteria.set(root.get("isActive"), 0);
        criteria.where(builder.equal(root.get("id"), id));
        session.createQuery(criteria).executeUpdate();
        return c.list();
    }

    @Override
    public void updateLastRun(int id){
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Report.class);
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<Report> criteria = builder.createCriteriaUpdate(Report.class);
        Root<Report> root = criteria.from(Report.class);
        criteria.set(root.get("lastRun"), new Date());
        criteria.where(builder.equal(root.get("id"), id));
        session.createQuery(criteria).executeUpdate();
    }
}
