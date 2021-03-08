
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.Project;

public interface ProjectDAO extends DAO<Project> {
   Project findByOrderNr(String var1);
}
