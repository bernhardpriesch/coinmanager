package at.priesch.coinmanager.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import at.priesch.coinmanager.gui.components.ImageFilter;
import at.priesch.coinmanager.gui.components.ImageUtils;
import at.priesch.coinmanager.servicecomponents.Coinmanager;
import at.priesch.coinmanager.servicecomponents.Currencymanager;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOCoin;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOCurrency;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOMaterial;

/**
 * @author Bernhard PRIESCH
 */
public class EditScreen
    extends JFrame
{

    private static final long   serialVersionUID   = 1L;

    private Logger              logger             = Logger.getLogger (getClass ()); // @jve:decl-index=0:

    private EntityManager       entityManager      = null;
    private Currencymanager     currencyManager    = null;
    private Coinmanager         coinManager        = null;

    private JPanel              jContentPane       = null;
    private JLabel              coinName           = null;
    private JTextField          coinNameTF         = null;
    private JLabel              coinYear           = null;
    private JTextField          coinYearTF         = null;
    private JLabel              coinDenomination   = null;
    private JTextField          coinDenominationTF = null;
    private List<MaterialPanel> materialPanels     = new ArrayList<MaterialPanel> (); // @jve:decl-index=0:
    private JTabbedPane         jTabbedPane        = null;

    private JButton             jButton            = null;

    private JLabel              currency           = null;

    private JComboBox           currencies         = null;

    private JLabel              front              = null;

    private JLabel              back               = null;
    
    private ImageIcon           frontImage         = null;
    private ImageIcon           backImage          = null;

    private JButton save = null;

    private JLabel coinDenomination1 = null;

    private JTextField estimatedValueTF = null;
    
    private VOCoin coin = null;
    
    private SearchScreen parent = null;
    private ResourceBundle resources = null;

    /**
     * This is the default constructor
     * 
     * @param entityManager
     */
    public EditScreen (final EntityManager entityManager, final VOCoin coin, final SearchScreen parent, final ResourceBundle resources)
    {
        super ();
        this.resources = resources;
        this.entityManager = entityManager;
        currencyManager = new Currencymanager ();
        coinManager = new Coinmanager ();
        this.coin = coin;
        this.parent = parent;
        initialize ();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize ()
    {
        this.setSize (549, 548);
        this.setContentPane (getJContentPane ());
        this.setTitle ("Edit Coin");
        this.setResizable (false);
        this.addWindowListener (new java.awt.event.WindowAdapter ()
        {
            public void windowClosing (java.awt.event.WindowEvent e)
            {
                System.out.println ("windowClosing()");
                e.getWindow ().setVisible (false);
                e.getWindow ().dispose ();
            }
        });
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane ()
    {
        if (jContentPane == null)
        {
            coinDenomination1 = new JLabel();
            coinDenomination1.setBounds(new Rectangle(10, 391, 140, 17));
            coinDenomination1.setHorizontalTextPosition(SwingConstants.RIGHT);
            coinDenomination1.setText(resources.getString ("estimatedValue"));
            coinDenomination1.setHorizontalAlignment(SwingConstants.RIGHT);
            back = new JLabel ();
            back.setBounds (new Rectangle (281, 179, 190, 190));
            back.setForeground (Color.white);
            back.setMinimumSize (new Dimension (190, 190));
            back.setOpaque (true);
            back.setPreferredSize (new Dimension (190, 190));
            backImage = new ImageIcon(coin.back);
            back.setIcon (new ImageIcon(backImage.getImage ().getScaledInstance (back.getWidth (), back.getHeight (), Image.SCALE_AREA_AVERAGING)));
            back.setText ("JLabel");
            back.setBackground (new Color(205, 216, 249));
            back.addMouseListener (new java.awt.event.MouseAdapter ()
            {
                public void mouseClicked (java.awt.event.MouseEvent e)
                {
                    // Create a file chooser
                    final JFileChooser fc = new JFileChooser ();
                    fc.addChoosableFileFilter(new ImageFilter());
                    // In response to a button click:
                    int returnVal = fc.showOpenDialog (back);

                    if (returnVal == JFileChooser.APPROVE_OPTION)
                    {
                        File file = fc.getSelectedFile ();
                        // This is where a real application would open the file.
                        logger.info ("Opening: " + file.getName () + ".");
                        backImage = new ImageIcon(file.getAbsolutePath ());
                        back.setIcon (new ImageIcon(backImage.getImage ().getScaledInstance (back.getWidth (), back.getHeight (), Image.SCALE_AREA_AVERAGING)));
                        back.update (back.getGraphics ());
                    }
                    else
                    {
                        logger.info ("Open command cancelled by user.");
                    }

                }
            });
            front = new JLabel ();
            front.setBackground (new Color(205, 216, 249));
            front.setForeground (Color.white);
            front.setLocation (new Point (60, 179));
            front.setSize (new Dimension (190, 190));
            front.setPreferredSize (new Dimension (190, 190));
            front.setMinimumSize (new Dimension (190, 190));
            frontImage = new ImageIcon(coin.front);
            front.setIcon (new ImageIcon(frontImage.getImage().getScaledInstance (front.getWidth (), front.getHeight (), Image.SCALE_AREA_AVERAGING)));
            front.setOpaque (true);
            front.addMouseListener (new java.awt.event.MouseAdapter ()
            {
                public void mouseClicked (java.awt.event.MouseEvent e)
                {
                    // Create a file chooser
                    final JFileChooser fc = new JFileChooser ();
                    fc.addChoosableFileFilter(new ImageFilter());
                    // In response to a button click:
                    int returnVal = fc.showOpenDialog (front);

                    if (returnVal == JFileChooser.APPROVE_OPTION)
                    {
                        File file = fc.getSelectedFile ();
                        // This is where a real application would open the file.
                        logger.info ("Opening: " + file.getName () + ".");
                        frontImage = new ImageIcon(file.getAbsolutePath ());
                        front.setIcon (new ImageIcon(frontImage.getImage().getScaledInstance (front.getWidth (), front.getHeight (), Image.SCALE_AREA_AVERAGING)));
                        front.update (front.getGraphics ());
                    }
                    else
                    {
                        logger.info ("Open command cancelled by user.");
                    }

                }
            });
            currency = new JLabel ();
            currency.setText (resources.getString ("currency"));
            currency.setSize (new Dimension (90, 16));
            currency.setLocation (new Point (230, 57));
            coinDenomination = new JLabel ();
            coinDenomination.setBounds (new Rectangle (5, 57, 110, 16));
            coinDenomination.setHorizontalTextPosition (SwingConstants.RIGHT);
            coinDenomination.setText (resources.getString ("denomination"));
            coinDenomination.setHorizontalAlignment (SwingConstants.RIGHT);
            coinYear = new JLabel ();
            coinYear.setText (resources.getString ("year"));
            coinYear.setHorizontalTextPosition (SwingConstants.RIGHT);
            coinYear.setHorizontalAlignment (SwingConstants.RIGHT);
            coinYear.setBounds (new Rectangle (5, 33, 110, 16));
            coinName = new JLabel ();
            coinName.setText (resources.getString ("name"));
            coinName.setBounds (new Rectangle (5, 7, 110, 16));
            coinName.setHorizontalAlignment (SwingConstants.RIGHT);
            coinName.setName ("coinName");
            jContentPane = new JPanel ();
            jContentPane.setLayout (null);
            jContentPane.add (coinName, null);
            jContentPane.add (getCoinNameTF (), null);
            jContentPane.add (coinYear, null);
            jContentPane.add (getCoinYearTF (), null);
            jContentPane.add (coinDenomination, null);
            jContentPane.add (getCoinDenominationTF (), null);
            jContentPane.add (getJTabbedPane (), null);
            jContentPane.add (getJButton (), null);
            jContentPane.add (currency, null);
            jContentPane.add (getCurrencies (), null);
            jContentPane.add (front, null);
            jContentPane.add (back, null);
            jContentPane.add(getSave(), null);
            jContentPane.add(coinDenomination1, null);
            jContentPane.add(getEstimatedValueTF(), null);
            jContentPane.setBackground (new Color(205, 216, 249));
        }
        return jContentPane;
    }

    /**
     * This method initializes coinNameTF
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getCoinNameTF ()
    {
        if (coinNameTF == null)
        {
            coinNameTF = new JTextField ();
            coinNameTF.setSize (200, coinNameTF.getHeight ());
            coinNameTF.setPreferredSize (new Dimension (200, coinNameTF.getHeight ()));
            coinNameTF.setName ("coinNameTF");
            coinNameTF.setBounds (new Rectangle (125, 5, 357, 22));
            coinNameTF.setMinimumSize (new Dimension (200, coinNameTF.getHeight ()));
            coinNameTF.setText (coin.name);
        }
        return coinNameTF;
    }

    /**
     * This method initializes coinYearTF
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getCoinYearTF ()
    {
        if (coinYearTF == null)
        {
            coinYearTF = new JTextField ();
            coinYearTF.setMinimumSize (new Dimension (200, getCoinYearTF ().getHeight ()));
            coinYearTF.setPreferredSize (new Dimension (200, getCoinYearTF ().getHeight ()));
            coinYearTF.setBounds (new Rectangle (125, 30, 100, 22));
            coinYearTF.setName ("coinNameTF");
            coinYearTF.setText (String.valueOf (coin.year));
        }
        return coinYearTF;
    }

    /**
     * This method initializes coinDenominationTF
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getCoinDenominationTF ()
    {
        if (coinDenominationTF == null)
        {
            coinDenominationTF = new JTextField ();
            coinDenominationTF.setBounds (new Rectangle (125, 55, 100, 22));
            coinDenominationTF.setMinimumSize (new Dimension (200, getCoinDenominationTF ().getHeight ()));
            coinDenominationTF.setPreferredSize (new Dimension (200, getCoinDenominationTF ().getHeight ()));
            coinDenominationTF.setName ("coinNameTF");
            coinDenominationTF.setText (String.valueOf (coin.denomination));
        }
        return coinDenominationTF;
    }

    /**
     * This method initializes jTabbedPane
     * 
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getJTabbedPane ()
    {
        int i = 1;
        if (jTabbedPane == null)
        {
            jTabbedPane = new JTabbedPane ()
            {
                /**
                 * 
                 */
                private static final long serialVersionUID = 1L;

                @Override
                public void update (final Graphics g)
                {
                    int i = 0;
                    int selected = 0;

                    if (materialPanels.size () == 0)
                    {
                        materialPanels.add (new MaterialPanel (entityManager, this, resources));
                    }

                    selected = jTabbedPane.getSelectedIndex ();

                    this.removeAll ();
                    for (MaterialPanel panel : materialPanels)
                    {
                        jTabbedPane.addTab (resources.getString ("materialtab") + " - " + panel.getjComboBoxMaterial ().getSelectedItem (), panel);
                        i++;
                    }
                    jTabbedPane.setSelectedIndex (selected);

                    super.update (g);
                }
            };
            jTabbedPane.setBounds (new Rectangle (70, 91, 417, 73));

            for(Entry<VOMaterial, Double> entry : coin.materials.entrySet ())
            {
                materialPanels.add (new MaterialPanel (entityManager, jTabbedPane, entry.getKey (), entry.getValue (), resources));
            }
            
            if (materialPanels.size () == 0)
            {
                materialPanels.add (new MaterialPanel (entityManager, jTabbedPane, resources));
            }

            for (MaterialPanel panel : materialPanels)
            {
                jTabbedPane.addTab (resources.getString ("materialtab") + " - " + panel.getjComboBoxMaterial ().getSelectedItem (), panel);
                i++;
            }
        }
        return jTabbedPane;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getJButton ()
    {
        ImageIcon icon = null;
        icon = new ImageIcon ("images/add-icon.png");
        icon.setImage (icon.getImage ().getScaledInstance (30, 30, Image.SCALE_AREA_AVERAGING));

        if (jButton == null)
        {
            jButton = new JButton ();
            jButton.setBorder (null);
            jButton.setBackground (new Color(205, 216, 249));
            jButton.setIcon (icon);
            jButton.setBounds (new Rectangle (500, 89, 39, 35));
            jButton.addActionListener (new java.awt.event.ActionListener ()
            {
                public void actionPerformed (java.awt.event.ActionEvent e)
                {
                    MaterialPanel panel = null;
                    panel = new MaterialPanel (entityManager, jTabbedPane, resources);
                    materialPanels.add (panel);
                    jTabbedPane.addTab (resources.getString ("materialtab") + " - " + panel.getjComboBoxMaterial ().getSelectedItem (), panel);
                    jTabbedPane.repaint ();
                    jTabbedPane.update (jTabbedPane.getGraphics ());
                }
            });
        }
        return jButton;
    }

    /**
     * This method initializes currencies
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getCurrencies ()
    {
        if (currencies == null)
        {
            currencies = new JComboBox ();
            currencies.setBounds (new Rectangle (305, 55, 147, 22));
            currencies.setPreferredSize (new Dimension (32, 22));

            for (VOCurrency currency : currencyManager.getCurrencies (entityManager))
            {
                currencies.addItem (currency);
            }
            
            for (int i = 0; i < currencies.getItemCount (); i++)
            {
                if (currencies.getItemAt (i).toString ().equals (coin.currency.toString ()))
                {
                    currencies.setSelectedIndex (i);
                }
            }
        }
        return currencies;
    }

    /**
     * This method initializes save	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getSave ()
    {
        if (save == null)
        {
            save = new JButton ();
            save.setBounds(new Rectangle(62, 457, 128, 34));
            save.setText(resources.getString ("saveButton"));
            save.addActionListener (new java.awt.event.ActionListener ()
            {
                public void actionPerformed (java.awt.event.ActionEvent e)
                {
                    Map<VOMaterial, Double> materials = new HashMap<VOMaterial, Double> ();
                    
                    coin.name = coinNameTF.getText ();
                    coin.year = Integer.parseInt (coinYearTF.getText ());
                    coin.denomination = Double.parseDouble (coinDenominationTF.getText ().replaceAll (",", "."));
                    coin.currency = (VOCurrency) currencies.getSelectedItem ();
                    
                    for(MaterialPanel panel : materialPanels)
                    {
                        materials.put ((VOMaterial) panel.getjComboBoxMaterial ().getSelectedItem(), Double.parseDouble ("".equals (panel.getjTextFieldWeight ().getText ()) ? "-1" : panel.getjTextFieldWeight ().getText ().replaceAll (",", "."))); 
                        
                    }
                    coin.materials = materials;
                    coin.front = ImageUtils.image2byteArray (frontImage);
                    coin.back = ImageUtils.image2byteArray (backImage);
                    coin.estimatedValue = Double.parseDouble ("".equals (estimatedValueTF.getText ()) ? "-1" : estimatedValueTF.getText ().replaceAll (",", "."));
                    
                    coinManager.updateCoin (coin, entityManager);
                    
                    parent.update ();
                    closeFrame ();
                }
            });
        }
        return save;
    }

    /**
     * This method initializes estimatedValueTF	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getEstimatedValueTF ()
    {
        if (estimatedValueTF == null)
        {
            estimatedValueTF = new JTextField ();
            estimatedValueTF.setMinimumSize(new Dimension(200, getEstimatedValueTF().getHeight()));
            estimatedValueTF.setPreferredSize(new Dimension(200, 22));
            estimatedValueTF.setLocation(new Point(160, 390));
            estimatedValueTF.setSize(new Dimension(107, 22));
            estimatedValueTF.setName("coinNameTF");
            estimatedValueTF.setText (String.valueOf (coin.estimatedValue));
        }
        return estimatedValueTF;
    }
    
    public void closeFrame ()
    {
        this.dispose ();
    }

} // @jve:decl-index=0:visual-constraint="10,10"
