package com.exptype.model;
import java.util.*;

public interface ExptypeDAO_interface {
	public void insert(ExptypeVO exptypeVO);
	public void update(ExptypeVO exptypeVO);
	public void delete(String exp_Id);
	public ExptypeVO findByPrimaryKey(String exp_Id);
	public List<ExptypeVO> getAll();
}
