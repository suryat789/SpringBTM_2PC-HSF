package com.technoficent.btm.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technoficent.btm.dao.EmployeeADAO;
import com.technoficent.btm.dao.EmployeeBDAO;
import com.technoficent.btm.entities.Employee;

public class EmployeeImpl implements EmployeeSEI {

	@Autowired
	private EmployeeADAO empADAO;
	
	@Autowired
	private EmployeeBDAO empBDAO;
	
	
	public EmployeeADAO getEmpADAO() {
		return empADAO;
	}

	public EmployeeBDAO getEmpBDAO() {
		return empBDAO;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addEmployee(Employee employee) throws Exception {
		getEmpADAO().addEmployee(employee);
		getEmpBDAO().addEmployee(employee);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Employee getEmpoyeeDetails(String id, String type) {
		if("A".equalsIgnoreCase(type))
			return getEmpADAO().getEmployeeDetails(id);
		if("B".equalsIgnoreCase(type))
			return getEmpBDAO().getEmployeeDetails(id);
		else
			return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateEmployee(Employee employee) {
		getEmpADAO().updateEmployee(employee);
		getEmpBDAO().updateEmployee(employee);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteEmployeeByEmpID(String empID) {
		getEmpADAO().deleteEmployeeByEmpID(empID);
		getEmpBDAO().deleteEmployeeByEmpID(empID);
	}
}
