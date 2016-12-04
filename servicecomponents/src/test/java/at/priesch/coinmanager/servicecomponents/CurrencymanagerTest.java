package at.priesch.coinmanager.servicecomponents;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.priesch.coinmanager.datamodel.datatypes.CurrencyName;

public class CurrencymanagerTest
{
    private Currencymanager currencyManager = null;
    
    @Before
    public void setUp ()
        throws Exception
    {
        currencyManager = new Currencymanager ();
    }

    @After
    public void tearDown ()
        throws Exception
    {
    }

    @Test
    public void testGetExchangeRate () 
        throws  ClientProtocolException, 
                IOException
    {
        System.out.println (currencyManager.getExchangeRate (CurrencyName.USD));
    }

}
