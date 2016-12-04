package at.priesch.coinmanager.servicecomponents.valueobjects;

import at.priesch.coinmanager.datamodel.datatypes.CurrencyName;

/**
 * @author Bernhard PRIESCH
 */
public class VOCurrency 
{
	public long						id			= -1;
	public CurrencyName 			name		= null;
    public double 					rate		= -1;
    
    public VOCurrency ()
    {
    	//nothing to do!
    }
    
    @Override
    public String toString ()
    {
        return name.getName ();
    }
}
