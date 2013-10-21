package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.ProjectDAO;
import no.ugland.utransprod.model.Project;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class ProjectDAOHibernate extends BaseDAOHibernate<Project> implements
        ProjectDAO {

    public ProjectDAOHibernate() {
        super(Project.class);
    }

    public final Project findByOrderNr(final String orderNr) {
        return (Project) getHibernateTemplate().execute(
                new HibernateCallback() {

                    @SuppressWarnings("unchecked")
                    public Object doInHibernate(final Session session) {
                        String sql = "select project from Project project,Sale sale "
                                + "        where project.projectId=sale.projectId and "
                                + "              sale.number1=:orderNr";
                        List<Project> list = session.createQuery(sql)
                                .setParameter("orderNr", orderNr).list();
                        if (list != null && list.size() == 1) {
                            return list.get(0);
                        }
                        return null;
                    }

                });
    }

}
