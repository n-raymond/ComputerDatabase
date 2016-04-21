package com.excilys.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;

public class ComputerDAOTest {
	
	public static Computer freshComputer(){
		Computer c = new Computer(0);
		c.setName("LambdaY");
		c.setIntroduced(Timestamp.valueOf("2010-01-01 00:00:01.0"));
		c.setDiscontinued(Timestamp.valueOf("2016-01-01 00:00:01.0"));
		c.setCompanyId(new Long(42));
		return c;
	}
	
	public static void testFind(){
		ComputerDAO cdao = ComputerDAO.getInstance();
		try{
			Computer c = cdao.find(575);
			System.out.println(c);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void testInsert(){
		ComputerDAO cdao = ComputerDAO.getInstance();
		try{
			Computer c = freshComputer();
			Computer res = cdao.insert(c);
			System.out.println(res);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		testFind();
		testInsert();
	}
}