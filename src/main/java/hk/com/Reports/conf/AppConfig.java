package hk.com.Reports.conf;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "hk.com.Reports")
public class AppConfig {
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/view/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@172.168.0.48:1521:ORCL");
        dataSource.setUsername("DBATIDBSFCLONE");
        dataSource.setPassword("admin");
        return dataSource;
    }

//    @Autowired
//    @Bean(name = "sessionFactory")
//    public SessionFactory getSessionFactory() {
//        DataSource dataSource = getDataSource();
//        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
//        sessionBuilder.addAnnotatedClasses(User.class, UserRole.class);
//        sessionBuilder.scanPackages("au.com.nmcgroup.model");
//        sessionBuilder.setProperty("hibernate.show_sql", "true");
//        sessionBuilder.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
//
//        return sessionBuilder.buildSessionFactory();
//    }

}
