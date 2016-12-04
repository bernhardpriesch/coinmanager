package at.priesch.coinmanager.datamodel.datatypes;

/**
 * @author Bernhard PRIESCH
 */
public enum CurrencyName 
{
	ATS("Schilling"),
	EUR("Euro"),
	CAD("Canadian Dollar"),
	USD("US Dollar"),
	DM("Deutsche Mark");

	private final String name;

	CurrencyName(String name)
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
