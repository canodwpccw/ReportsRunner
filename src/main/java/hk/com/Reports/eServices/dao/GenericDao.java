package hk.com.Reports.eServices.dao;
import java.io.Serializable;
import java.util.List;

public interface GenericDao<E,K extends Serializable> {
    public void add(E entity) ;
    public E saveOrUpdate(E entity) ;
    public E getByID(int i);
    public List<E> listAll() ;
    public void update(E entity) ;
    public void remove(E entity);

}