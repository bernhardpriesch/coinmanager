package at.priesch.coinmanager.servicecomponents.valueobjects;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bernhard PRIESCH
 */
public class VOCoin 
{
	public long						id			= -1;
	public String 					name		 	= null;
	public int 						year		 	= -1;
	public Map<VOMaterial, Double> 	materials	 	= new HashMap<VOMaterial, Double> ();
	public double 					denomination 	= 0;
	public VOCurrency				currency		= null;
	public double					estimatedValue 	= 0;
	public byte[] 					front		 	= null;
	public byte[] 					back		 	= null;
    
    public VOCoin ()
    {
    	//nothing to do!
    }
}
