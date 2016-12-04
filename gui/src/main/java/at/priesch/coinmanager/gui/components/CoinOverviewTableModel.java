package at.priesch.coinmanager.gui.components;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

import at.priesch.coinmanager.servicecomponents.valueobjects.VOCoin;

/**
 * @author Bernhard PRIESCH
 */
public class CoinOverviewTableModel
    extends AbstractTableModel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public String[]           m_colNames        = null;

    public Class<?>[]         m_colTypes        = {String.class, Integer.class, String.class, Double.class, Double.class };

    private List<VOCoin>      data             = new ArrayList<VOCoin> ();

    private ResourceBundle    resources        = null;
    
    public CoinOverviewTableModel(final ResourceBundle resources)
    {
        super ();
        this.resources = resources;
        m_colNames       = new String[]{resources.getString ("name"), resources.getString ("year"), resources.getString ("material"), resources.getString ("denomination"), resources.getString ("estimatedValue") };
    }
    
    @Override
    public int getColumnCount ()
    {
        return m_colNames.length;
    }

    @Override
    public String getColumnName(int col) 
    {
        return m_colNames[col];
    }
    
    @Override
    public int getRowCount ()
    {
        return data.size ();
    }

    @Override
    public Object getValueAt (int row, int column)
    {
        Object  retValue    = null;
        VOCoin  coin        = null;
        
        if(data.size () > row)
        {
            coin = data.get (row);
            
            switch(column)
            {
                case 0:
                {
                    retValue = coin.name;
                    break;
                }
                case 1:
                {
                    retValue = coin.year;
                    break;
                }
                case 2:
                {
                    coin.materials.entrySet ().iterator ().next ().getKey ().setResources (resources);
                    retValue = coin.materials.entrySet ().iterator ().next ().getKey ().toString ();
                    break;
                }
                case 3:
                {
                    retValue = coin.denomination;
                    break;
                }
                case 4:
                {
                    retValue = coin.estimatedValue;
                    break;
                }
            }
        }
        
        return retValue;
    }

    public List<VOCoin> getData ()
    {
        return data;
    }

    public void setData (final List<VOCoin> data)
    {
        this.data = data;
    }
}
