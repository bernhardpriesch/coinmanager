package at.priesch.coinmanager.pdfgenerator.datatypes;

public class Material
{
    private String materialName     = null;
    private String abbreviation     = null;
    private double rate     = 0.0;


     public Material ()
    {
        //nothing to do
    }

    public String getMaterialName ()
    {
        return materialName;
    }

    public void setMaterialName (final String materialName)
    {
        this.materialName = materialName;
    }

    public String getAbbreviation ()
    {
        return abbreviation;
    }

    public void setAbbreviation (final String abbreviation)
    {
        this.abbreviation = abbreviation;
    }

    public double getRate ()
    {
        return rate;
    }

    public void setRate (final double rate)
    {
        this.rate = rate;
    }
}
