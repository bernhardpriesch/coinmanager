package at.priesch.coinmanager.pdfgenerator.datatypes;

import java.util.ArrayList;
import java.util.List;

import at.priesch.coinmanager.servicecomponents.valueobjects.VOCoin;

public class Page
{
    private String       year  = "Unknown";
    private List<VOCoin> coins = new ArrayList<VOCoin> ();
    
    public Page ()
    {
        //nothing to do
    }

    public String getYear ()
    {
        return year;
    }

    public void setYear (final String year)
    {
        this.year = year;
    }

    public List<VOCoin> getCoins ()
    {
        return coins;
    }

    public void setCoins (final List<VOCoin> coins)
    {
        this.coins = coins;
    }
    
    public void addCoin (final VOCoin coin)
    {
        coins.add (coin);
    }
    
    
}
