package at.priesch.coinmanager.datamodel.datatypes;

/**
 * @author Bernhard PRIESCH
 */
public enum MaterialName 
{
	AU("Gold"),
	AG("Silver"),
	NB("Niob"),
	TI("Titan"),
	PT("Platin");

	private final String name;

	MaterialName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString ()
	{
	    return name;
	}
}
