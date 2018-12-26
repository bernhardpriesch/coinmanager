package at.priesch.coinmanager.servicecomponents.valueobjects;

import java.util.ResourceBundle;

import at.priesch.coinmanager.datamodel.datatypes.MaterialName;

/**
 * @author Bernhard PRIESCH
 */
public class VOMaterial 
{
	public long						id			= -1;
	public MaterialName 			name		= null;
    public double 					rate		= -1;
    private ResourceBundle          resources   = null;
    
    public VOMaterial ()
    {
    	//nothing to do!
    }
    
    @Override
    public String toString ()
    {
        return resources.getString (name.getName ());
    }
    
    public String toString (final ResourceBundle resources)
    {
        this.resources = resources;
        return resources.getString (name.getName ());
    }

    public static String getI18nName (final String name, final ResourceBundle resources)
    {
        return resources.getString (name);
    }

    public void setResources (final ResourceBundle resources)
    {
        this.resources = resources;
    }
}
