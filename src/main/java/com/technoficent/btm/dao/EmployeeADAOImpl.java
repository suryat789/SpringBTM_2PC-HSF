package com.technoficent.btm.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.technoficent.btm.entities.Employee;
import com.technoficent.btm.to.Status;
import com.technoficent.btm.to.StatusConstants;

@SuppressWarnings(value = "unchecked")
@Repository("employeeADao")
public class EmployeeADAOImpl implements EmployeeADAO {

	@Autowired
	@Qualifier("sessionFactoryA")
	SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	protected Session getSession(){
		return getSessionFactory().getCurrentSession();
	}

	public void persist(Object entity) {
		getSession().persist(entity);
	}

	public void delete(Object entity) {
		getSession().delete(entity);
	}
	
	
	@Override
	public Employee getEmployeeDetails(String id) {

		org.hibernate.Query query = getSession().createQuery(" from Employee emp WHERE emp.employeeID = :employeeID");
		query.setParameter("employeeID", id);
		List<Employee> employees = query.list();
		if(employees != null && !employees.isEmpty()){
			return employees.get(0);
		}
		return null;
	}

	public Status updateEmployee(Employee employee){
		Status status = null;
		try {
			getSession().update(employee);
			status = new Status(StatusConstants.StatusCode.SUCCESS, StatusConstants.StatusMessages.SUCCESS);
		} catch (Exception e) {
			status = new Status(StatusConstants.StatusCode.FAILURE, StatusConstants.StatusMessages.FAILURE);
			e.printStackTrace();
		}
		return status;
	}

	public Status deleteEmployeeByEmpID(String empID) {
		Status status = null;
		try {
			Employee employee = getEmployeeDetails(empID);
			delete(employee);
			status = new Status(StatusConstants.StatusCode.SUCCESS, StatusConstants.StatusMessages.SUCCESS);
		} catch (Exception ex){
			status = new Status(StatusConstants.StatusCode.FAILURE, StatusConstants.StatusMessages.FAILURE);
			ex.printStackTrace();
		}
		return status;
	}

	@Override
	public Status addEmployee(Employee employee) {
		Status status = null;
		try {
			persist(employee);
			status = new Status(StatusConstants.StatusCode.SUCCESS, StatusConstants.StatusMessages.SUCCESS);
		} catch (Exception ex){
			status = new Status(StatusConstants.StatusCode.FAILURE, StatusConstants.StatusMessages.FAILURE);
			ex.printStackTrace();
		}
		return status;
	}
}
