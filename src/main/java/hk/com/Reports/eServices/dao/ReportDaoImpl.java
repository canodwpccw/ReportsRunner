package hk.com.Reports.eServices.dao;

import hk.com.Reports.eServices.model.Report;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
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
        return null;
    }


}
