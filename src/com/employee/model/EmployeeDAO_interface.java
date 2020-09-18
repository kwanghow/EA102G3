package com.employee.model;

import java.util.List;

public interface EmployeeDAO_interface {
	public void insert(EmployeeVO employeeVO);
	public void update(EmployeeVO employeeVO);
	public void delete(String emp_id);
	public EmployeeVO findByPrimaryKey(String emp_id);
    public List<EmployeeVO> getAll();
    public EmployeeVO findByAccount(String emp_account);
}
