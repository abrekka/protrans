
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.Contact;

public interface ContactDAO extends DAO<Contact> {
   List<Contact> findAllTilAvrop(Integer var1);
}
