package at.priesch.coinmanager.datamodel.datatypes;

/**
 * @author Bernhard PRIESCH
 */
public enum MaterialName 
{
	AU("Gold", 2),
	AG("Silver", 5),
	NB("Niob", 4),
	TI("Titan", 3),
	PT("Platin", 1);

	private final String name;
	private final int    order;

	MaterialName(String name, int order)
	{
		this.name = name;
		this.order = order;
	}

	public String getName()
	{
		return name;
	}

	public int getOrder ()
	{
		return order;
	}

	@Override
	public String toString ()
	{
	    return name;
	}
}
