package com.revature.util;


import com.revature.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class App {
	public static void main(String[] args) {
		Session session = 
				//HibernateUtils.getSessionFactoryFileConfig().openSession();
				HibernateUtils.getSessionFactoryProgrammaticConfig().openSession();
		
		Transaction tx = null;

		try {
		   tx = session.beginTransaction();
		   
		   List<User> users = session.createQuery("FROM User", User.class).list();
		   for (User u : users) {
			   System.out.println("Entry: " + u.getFirstname() + " " +
					   u.getLastname() + ", " + u.getUsername() + ", " + u.getEmail());
		   }
		   
		   tx.commit();
		}

		catch (Exception e) {
		   if (tx!=null) tx.rollback();
		   e.printStackTrace(); 
		} finally {
		   session.close();
		}
	}
}
