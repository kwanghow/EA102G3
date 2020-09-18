package com.employee.model;

import java.util.List;

public class EmployeeService {
    private EmployeeDAO_interface dao;
    public EmployeeService() {
    	dao=new EmployeeDAO();
    }


    public EmployeeVO addEmp(String emp_name,String emp_account,String emp_psw,String emp_email,String emp_phone) {
    	
    	EmployeeVO employeeVO = new EmployeeVO();
    	employeeVO.setEmp_name(emp_name);
    	employeeVO.setEmp_account(emp_account);
    	employeeVO.setEmp_email(emp_email);
    	employeeVO.setEmp_psw(emp_psw);
    	employeeVO.setEmp_phone(emp_phone);
    	dao.insert(employeeVO);
    	
    	
		return employeeVO;
	}
    public EmployeeVO updateEmp(String emp_id,String emp_name,String emp_account,String emp_psw,String emp_email,String emp_phone) {
    	
    EmployeeVO employeeVO = new EmployeeVO();
    employeeVO.setEmp_id(emp_id);
    employeeVO.setEmp_name(emp_name);
    employeeVO.setEmp_account(emp_account);
    employeeVO.setEmp_psw(emp_psw);
    employeeVO.setEmp_email(emp_email);
    employeeVO.setEmp_phone(emp_phone);
    dao.update(employeeVO);
    
    
    
    return employeeVO;
}
    public void deleteEmp(String emp_id) {
    	dao.delete(emp_id);
    }
    
    public EmployeeVO getOneEmp(String emp_id) {
    	return dao.findByPrimaryKey(emp_id);
    }
    public List<EmployeeVO> getAll(){
    	return dao.getAll();
    }
    
    public EmployeeVO getLogin(String emp_account) {
    	return dao.findByAccount(emp_account);
    }

}