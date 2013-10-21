package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.EmployeeDAO;
import no.ugland.utransprod.model.Employee;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.service.EmployeeManager;

/**
 * Implementsjon av serviceklasse for tabell EMPLOYEE.
 * @author atle.brekka
 */
public class EmployeeManagerImpl extends ManagerImpl<Employee>implements EmployeeManager {
    /**
     * @see no.ugland.utransprod.service.EmployeeManager#findAll()
     */
    public final List<Employee> findAll() {
        return dao.getObjects("firstName");
    }

    /**
     * @param employee
     * @return ansatte
     */
    public final List<Employee> findByEmployee(final Employee employee) {
        return dao.findByExampleLike(employee);
    }

    /**
     * @param object
     * @return ansatte
     * @see no.ugland.utransprod.service.OverviewManager#findByObject(java.lang.Object)
     */
    public final List<Employee> findByObject(final Employee object) {
        return findByEmployee(object);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#refreshObject(java.lang.Object)
     */
    public final void refreshObject(final Employee object) {
        ((EmployeeDAO)dao).refreshObject(object);

    }

    /**
     * @param employee
     */
    public final void removeEmployee(final Employee employee) {
        dao.removeObject(employee.getEmployeeId());

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#removeObject(java.lang.Object)
     */
    public final void removeObject(final Employee object) {
        removeEmployee(object);

    }

    /**
     * @param employee
     */
    public final void saveEmployee(final Employee employee) {
        dao.saveObject(employee);

    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.OverviewManager#saveObject(java.lang.Object)
     */
    public final void saveObject(final Employee object) {
        saveEmployee(object);

    }

    /**
     * @see no.ugland.utransprod.service.EmployeeManager#findBySupplier(no.ugland.utransprod.model.Supplier)
     */
    public final List<Employee> findBySupplier(final Supplier supplier) {
        return ((EmployeeDAO)dao).findBySupplier(supplier);
    }


    @Override
    protected Serializable getObjectId(Employee object) {
        return object.getEmployeeId();
    }

}
