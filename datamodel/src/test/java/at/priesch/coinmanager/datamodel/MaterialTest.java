package at.priesch.coinmanager.datamodel;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.priesch.coinmanager.datamodel.datatypes.MaterialName;

public class MaterialTest 
{
	private EntityManagerFactory    entityManagerFactory    = null;
    private EntityManager   		entityManager			= null;

    private static final    String  DB_HOSTNAME             = "192.168.0.40";
    private static final    String  DB_USERNAME  			= "coinmanager";
    private static final    String  DB_PASSWORD  			= "coinmanager";

    private Logger  logger      = Logger.getLogger (getClass ());


    @Before
    public void setUp ()
        throws Exception
    {
        Map<String, String> persistenceProperties   = new HashMap<String, String> ();

        try
        {
            persistenceProperties.put("hibernate.archive.autodetection", "class, hbm");
            persistenceProperties.put("hibernate.show_sql", "true");
            persistenceProperties.put("hibernate.format_sql", "false");
            persistenceProperties.put("hibernate.hbm2ddl.auto", "update");
            persistenceProperties.put("hibernate.connection.username", DB_USERNAME);
            persistenceProperties.put("hibernate.connection.password", DB_PASSWORD);
            persistenceProperties.put("hibernate.c3p0.min_size", "1");
            persistenceProperties.put("hibernate.c3p0.max_size", "5");
            persistenceProperties.put("hibernate.c3p0.timeout", "300");
            persistenceProperties.put("hibernate.c3p0.max_statements", "50");
            persistenceProperties.put("hibernate.c3p0.idle_test_period", "3000");
            
//          persistenceProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//          persistenceProperties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
//          persistenceProperties.put("hibernate.connection.url", "jdbc:postgresql://" + DB_HOSTNAME + "/coinmanager");
          
          persistenceProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
          persistenceProperties.put("hibernate.connection.driver_class", "org.h2.Driver");
          persistenceProperties.put("hibernate.connection.url", "jdbc:h2:h2/coinmanager");

            // Start EntityManagerFactory
            entityManagerFactory    = Persistence.createEntityManagerFactory ("COINMANAGER_TEST", persistenceProperties);
            entityManager           = entityManagerFactory.createEntityManager ();
        }
        catch (Exception e)
        {
            logger.error (e);
            e.printStackTrace (System.err);
            throw   e;
        }

    }

    @After
    public void tearDown ()
        throws Exception
    {
        if (null != entityManager)
        {
            entityManager.close ();
        }

        if (null != entityManagerFactory)
        {
            entityManagerFactory.close ();
        }
    }



	@Test
	public void testTest()
	{
		System.out.println("Test");
	}

	@Test
	public void createMaterials()
	{
		EntityTransaction  	transaction 	= null;
		Material 			material		= null;
		try 
		{
			transaction = entityManager.getTransaction();
			transaction.begin();
//			material = new Material(-1L, MaterialName.AU, 0.0);
//			entityManager.persist(material);
//			entityManager.flush();
//			material = new Material(-1L, MaterialName.AG, 0.0);
//			entityManager.persist(material);
//			entityManager.flush();
//			material = new Material(-1L, MaterialName.NB, 0.0);
//			entityManager.persist(material);
//			entityManager.flush();
//			material = new Material(-1L, MaterialName.TI, 0.0);
//			entityManager.persist(material);
//			entityManager.flush();
			material = new Material(-1L, MaterialName.PT, 0.0);
            entityManager.persist(material);
			entityManager.flush();
			transaction.commit();
		} catch (Exception e) 
		{
			fail(e.toString());
		}
	}

}
