package at.priesch.coinmanager.servicecomponents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import at.priesch.coinmanager.datamodel.Currency;
import at.priesch.coinmanager.datamodel.datatypes.CurrencyName;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOCurrency;

/**
 * @author Bernhard PRIESCH
 */
public class Currencymanager 
{
    
    private static final String currenciesUrl   = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private static final double ATS             = 13.7603d;
    private static final double DM              = 1.95583d;
    
	public Currencymanager ()
	{
		//nothing to do!
	}
	
	@SuppressWarnings("unchecked")
	public List<VOCurrency> getCurrencies(final EntityManager entityManager)
	{
		List<VOCurrency> 	retValue 	= null;
		VOCurrency			voCurrency	= null;
		Query				query		= null;
		EntityTransaction   transaction = null;
        
		try
        {
        	initCurrencies (entityManager);

            transaction = entityManager.getTransaction ();
            transaction.begin ();
    		
    		query = entityManager.createNamedQuery("GetCurrencies");
    		
    		retValue = new ArrayList<VOCurrency> ();
    		
    		for(Currency currency : (List<Currency>) query.getResultList())
    		{
    			voCurrency 		= new VOCurrency ();
    			voCurrency.id 	= currency.getId();
    			voCurrency.name = currency.getName();
    			voCurrency.rate = currency.getRate();
    			
    			retValue.add(voCurrency);
    		}
    		
    		transaction.commit ();
        }
        catch (Exception e)
        {
            if(null != transaction && transaction.isActive ())
            {
                transaction.rollback ();
            }
        }
		
		return retValue;
	}
	
	public void updateCurrencies(final List<VOCurrency> currencies, final EntityManager entityManager)
	{
		Currency currency = null;
		EntityTransaction   transaction = null;
        
		try
        {
            transaction = entityManager.getTransaction ();
            transaction.begin ();
    		
    		for(VOCurrency voCurrency : currencies)
    		{
    			currency = entityManager.find(Currency.class, voCurrency.id);
    			currency.setRate(voCurrency.rate);
    			
    			entityManager.flush();
    		}
    		    		
    		transaction.commit ();
        }
        catch (Exception e)
        {
            if(null != transaction && transaction.isActive ())
            {
                transaction.rollback ();
            }
        }
	}

	public void initCurrencies (final EntityManager entityManager)
	{
		Query				query		= null;
		EntityTransaction   transaction = null;
		Currency			currency    = null;

		try
		{
			transaction = entityManager.getTransaction ();
			transaction.begin ();

			query = entityManager.createNamedQuery("GetCurrencies");

			if (query.getResultList().isEmpty ())
			{
				currency = new Currency ();
				currency.setName (CurrencyName.ATS);
				currency.setRate (13.760);
				entityManager.persist (currency);

				currency = new Currency ();
				currency.setName (CurrencyName.DM);
				currency.setRate (1.95583);
				entityManager.persist (currency);

				currency = new Currency ();
				currency.setName (CurrencyName.EUR);
				currency.setRate (1.0);
				entityManager.persist (currency);

				currency = new Currency ();
				currency.setName (CurrencyName.USD);
				currency.setRate (1.07);
				entityManager.persist (currency);

				currency = new Currency ();
				currency.setName (CurrencyName.CAD);
				currency.setRate (1.42);
				entityManager.persist (currency);
			}

			transaction.commit ();
		}
		catch (Exception e)
		{
			if(null != transaction && transaction.isActive ())
			{
				transaction.rollback ();
			}
		}
	}
	
	public double getExchangeRate (final CurrencyName currency) 
	    throws  ClientProtocolException, 
	            IOException
	{
	    double retValue = 0.0;
	    
	    switch (currency)
	    {
	        case ATS:
	        {
	            retValue = ATS;
	            break;
	        }
	        
	        case DM:
            {
                retValue = DM;
                break;
            }
            
	        case EUR:
            {
                retValue = 1.0;
                break;
            }
	        
	        default:
	        {
	            retValue = extractRate (getCurrenciesXml (), currency.name ());
	            break;
	        }
	    }
	    
	    return retValue;
	}
	
	private String getCurrenciesXml () 
	    throws  ClientProtocolException, 
	            IOException
	{
	    String                     retValue        = null;        
	    HttpClient                 httpclient      = null;
	    HttpGet                    httpget         = null;
	    ResponseHandler<String>    responseHandler = null;
	    
	    httpclient = new DefaultHttpClient();
	    httpget = new HttpGet(currenciesUrl);

        // Create a response handler
        responseHandler = new BasicResponseHandler();
        retValue = httpclient.execute(httpget, responseHandler);

        return retValue;
	}
	
	private double extractRate (final String xml, final String currency)
	{
	    double retValue    = 0.0;
	    String subString   = null;
        
	    subString = xml.substring (xml.indexOf (currency) + 11);
        retValue = Double.valueOf (subString.substring (0, subString.indexOf ("'/>")));
        
        return retValue;
	}
}
