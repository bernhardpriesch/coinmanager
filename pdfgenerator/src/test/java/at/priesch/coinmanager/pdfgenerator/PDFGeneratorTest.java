package at.priesch.coinmanager.pdfgenerator;

import java.util.*;

import javax.persistence.*;

import at.priesch.coinmanager.datamodel.Coin;
import at.priesch.coinmanager.datamodel.Currency;
import at.priesch.coinmanager.datamodel.Material;
import at.priesch.coinmanager.datamodel.datatypes.MaterialName;
import at.priesch.coinmanager.servicecomponents.Currencymanager;
import at.priesch.coinmanager.servicecomponents.Materialmanager;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOCoin;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOMaterial;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.priesch.coinmanager.servicecomponents.Coinmanager;

import static org.junit.Assert.fail;

public class PDFGeneratorTest
{
    private Currencymanager currencymanager = null;
    private Coinmanager coinmanager =null;
    Materialmanager materialmanager = null;
    private EntityManagerFactory    entityManagerFactory    = null;
    private EntityManager           entityManager           = null;

    private static final    String  DB_USERNAME             = "coinmanager";
    private static final    String  DB_PASSWORD             = "coinmanager";

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
            persistenceProperties.put("hibernate.connection.url", "jdbc:h2:./h2/coinmanager");

            // Start EntityManagerFactory
            entityManagerFactory    = Persistence.createEntityManagerFactory ("COINMANAGER", persistenceProperties);
            entityManager           = entityManagerFactory.createEntityManager ();
            currencymanager = new Currencymanager ();

            materialmanager = new Materialmanager ();

            coinmanager = new Coinmanager ();

        }
        catch (Exception e)
        {
            logger.error (e);
            e.printStackTrace (System.err);
            throw   e;
        }

    }

    public void createCoin ()
    {
        VOCoin coin = null;
        Map<VOMaterial, Double> materials = new HashMap<> ();

        try
        {
            materials.put (materialmanager.getMaterials (entityManager).get (0), 18.0);
            coin = new VOCoin ();
            coin.name = "Test";
            coin.currency = currencymanager.getCurrencies (entityManager).get (0);
            coin.denomination = 50.0;
            coin.estimatedValue = 65.0;
            coin.materials = materials;
            coin.year = 2018;
            coinmanager.addCoin (coin, entityManager);
        }
        catch (Exception e)
        {
            fail (e.toString ());
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
    public void testCreatePDF()
    {
        System.setProperty("file.encoding", "UTF-8");

        PDFGenerator generator = new PDFGenerator ();

        generator.createPDF (new Coinmanager (), ResourceBundle.getBundle("coinmanager", new Locale("de")), entityManager, 2014, 2015);
    }

    @Test
    public void testFetchWithMultipleMaterials ()
    {
        logger.info ("Coins: " + coinmanager.countCoins (entityManager, Arrays.asList (MaterialName.NB)));
        logger.info ("Coins: " + coinmanager.countCoins (entityManager, Arrays.asList (MaterialName.AG, MaterialName.NB)));

        logger.info ("Coins: " + coinmanager.countCoins (entityManager, Arrays.asList (MaterialName.TI)));
        logger.info ("Coins: " + coinmanager.countCoins (entityManager, Arrays.asList (MaterialName.AG, MaterialName.TI)));
    }
}
