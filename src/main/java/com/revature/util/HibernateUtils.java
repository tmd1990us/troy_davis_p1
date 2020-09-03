package com.revature.util;


import com.revature.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {
	
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactoryProgrammaticConfig() {
		if (sessionFactory != null)
			return sessionFactory;
		
		try {
			Configuration config = new Configuration()
				.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
				.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
				.setProperty("hibernate.connection.url", "jdbc:postgresql://java-ng-usf-200727.cyd04jk1n1lv.us-east-1.rds.amazonaws.com:5432/postgres")
				.setProperty("hibernate.connection.username", "postgres")
				.setProperty("hibernate.connection.password", "morrell01")
				.addAnnotatedClass(User.class);
			
				config.setImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE);
			
			sessionFactory = config.buildSessionFactory();
			return sessionFactory;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	/* Bootstrapping the SessionFactory is a 3 step process:
	 *   1. Get the ServiceRegistry
	 *   2. Get the Metadata from the Service Registry, which is used for configuring the class mappings
	 *   3. Get the SessionFactoryBuilder from the Metadata, and the SessionFactory from the Builder
	 */
	public static SessionFactory getSessionFactoryFileConfig() {
		if (sessionFactory != null)
			return sessionFactory;
		
		try {
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
			
			Metadata metadata = new MetadataSources(serviceRegistry)
				.getMetadataBuilder()
				.applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
				.build();
			
			sessionFactory = metadata.getSessionFactoryBuilder().build();
			return sessionFactory;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
