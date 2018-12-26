package at.priesch.coinmanager.pdfgenerator.datatypes;

public class Summary
{
    private String materialName     = null;
    private long   numberCoins      = -1;
    private double denomination     = 0.0;
    private double valueOfMaterials = 0.0;
    private double estimatedValue   = 0.0;

     public Summary ()
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

    public long getNumberCoins ()
    {
        return numberCoins;
    }

    public void setNumberCoins (final long numberCoins)
    {
        this.numberCoins = numberCoins;
    }

    public double getDenomination ()
    {
        return denomination;
    }

    public void setDenomination (final double denomination)
    {
        this.denomination = denomination;
    }

    public double getValueOfMaterials ()
    {
        return valueOfMaterials;
    }

    public void setValueOfMaterials (final double valueOfMaterials)
    {
        this.valueOfMaterials = valueOfMaterials;
    }

    public double getEstimatedValue ()
    {
        return estimatedValue;
    }

    public void setEstimatedValue (final double estimatedValue)
    {
        this.estimatedValue = estimatedValue;
    }
}
