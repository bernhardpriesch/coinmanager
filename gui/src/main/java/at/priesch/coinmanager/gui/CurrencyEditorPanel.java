package at.priesch.coinmanager.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import at.priesch.coinmanager.servicecomponents.valueobjects.VOCurrency;

/**
 * @author Bernhard PRIESCH
 */
public class CurrencyEditorPanel
    extends JPanel
{

    private static final long serialVersionUID   = 1L;

    private JLabel            jLabelCurrency     = null;
    private JLabel            jLabelRate         = null;
    private JTextField        jTextFieldRate     = null;
    private JComponent        parent             = null;
    private VOCurrency        currency           = null;

    private JLabel            jLabelCurrencyName = null;
    
    private ResourceBundle    resources          = null;

    public CurrencyEditorPanel (final JComponent parent, final VOCurrency currency, final Double rate, final ResourceBundle resources)
    {
        super ();
        this.resources = resources;
        this.currency = currency;
        this.parent = parent;
        initialize ();
        jLabelCurrencyName.setText (currency.toString ());
        jTextFieldRate.setText (String.valueOf (rate));
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize ()
    {
        jLabelCurrencyName = new JLabel ();
        jLabelCurrencyName.setBounds (new Rectangle(90, 4, 149, 17));
        jLabelCurrencyName.setText ("Name");
        jLabelRate = new JLabel ();
        jLabelRate.setText (resources.getString ("rate"));
        jLabelRate.setBounds (new Rectangle (7, 27, 80, 17));
        jLabelCurrency = new JLabel ();
        jLabelCurrency.setText (resources.getString ("currency"));
        jLabelCurrency.setBounds (new Rectangle (7, 5, 80, 17));
        this.setLayout (null);
        this.setBounds (new Rectangle (50, 50, 234, 48));
        this.add (jLabelCurrency, null);
        this.add (jLabelRate, null);
        this.add (getJTextFieldRate (), null);
        this.add (jLabelCurrencyName, null);
        this.setBackground (new Color(205, 216, 249));
    }

    /**
     * This method initializes jTextFieldRate
     * 
     * @return javax.swing.JTextField
     */
    public JTextField getJTextFieldRate ()
    {
        if (jTextFieldRate == null)
        {
            jTextFieldRate = new JTextField ();
            jTextFieldRate.setBounds (new Rectangle (90, 24, 122, 21));
        }
        return jTextFieldRate;
    }
    
    public void setJTextFieldRate (final double rate)
    {
        if (jTextFieldRate != null)
        {
            jTextFieldRate.setText (String.valueOf (rate));
        }
    }

    public VOCurrency getCurrency ()
    {
        currency.rate = Double.parseDouble (jTextFieldRate.getText ());
        return currency;
    }

    public void updateParent ()
    {
        if (parent.getGraphics () != null)
        {
            parent.update (parent.getGraphics ());
        }
    }
} // @jve:decl-index=0:visual-constraint="10,10"
