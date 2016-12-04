package at.priesch.coinmanager.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import at.priesch.coinmanager.servicecomponents.Currencymanager;
import at.priesch.coinmanager.servicecomponents.Materialmanager;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOCurrency;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOMaterial;

/**
 * @author Bernhard PRIESCH
 */
public class SettingsScreen
    extends JFrame
{

    /**
     * 
     */
    private static final long           serialVersionUID        = 1L;

    private Logger                      logger                  = Logger.getLogger (getClass ());       
    
    private EntityManager               entityManager           = null;
    private Currencymanager             currencyManager         = null;
    private Materialmanager             materialManager         = null;

    private JPanel                      jPanel                  = null;
    private JTabbedPane                 jTabbedPaneMaterials    = null;
    private JTabbedPane                 jTabbedPaneCurrencies   = null;
    private List<MaterialEditorPanel>   materialPanels          = new ArrayList<MaterialEditorPanel> ();  //  @jve:decl-index=0:
    private List<CurrencyEditorPanel>   currencyPanels          = new ArrayList<CurrencyEditorPanel> ();  //  @jve:decl-index=0:

    private JButton         save                = null;    
    private JButton         update              = null;    
    private ResourceBundle  resources           = null;
    private JLabel          jLabelLanguage      = null;
    private JComboBox       jComboBoxLanguage   = null;    
    private StartScreen     parent              = null;
    
    /**
     * This method initializes
     * 
     */
    public SettingsScreen (final StartScreen parent, final EntityManager entityManager, final ResourceBundle resources)
    {
        super ();
        this.parent = parent;
        this.resources = resources;
        this.entityManager = entityManager;
        currencyManager = new Currencymanager ();
        materialManager = new Materialmanager ();
        
        for(VOMaterial material : materialManager.getMaterials (entityManager))
        {
            material.setResources (resources);
            materialPanels.add (new MaterialEditorPanel(jTabbedPaneMaterials, material, material.rate, resources));
        }
        
        for(VOCurrency currency : currencyManager.getCurrencies (entityManager))
        {
            currencyPanels.add (new CurrencyEditorPanel(jTabbedPaneMaterials, currency, currency.rate, resources));
        }
        
        initialize ();
    }

    /**
     * This method initializes this
     * 
     */
    private void initialize ()
    {
        this.setSize (new Dimension(1003, 409));
        this.setContentPane (getJPanel ());
        this.setTitle ("Settings");
        this.setResizable (false);
        this.addWindowListener (new java.awt.event.WindowAdapter ()
        {
            public void windowClosing (java.awt.event.WindowEvent e)
            {
                e.getWindow().setVisible(false);
                e.getWindow().dispose();
            }
        });
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel ()
    {
        if (jPanel == null)
        {
            jLabelLanguage = new JLabel();
            jLabelLanguage.setBounds(new Rectangle(123, 276, 87, 18));
            jLabelLanguage.setText(resources.getString ("language"));
            jPanel = new JPanel ();
            jPanel.setLayout (null);
            jPanel.add (getJTabbedPaneMaterials (), null);
            jPanel.add (getJTabbedPaneCurrencies (), null);
            jPanel.add (getSave (), null);
            jPanel.add (getUpdate (), null);
            jPanel.setBackground (new Color(205, 216, 249));
            jPanel.add(jLabelLanguage, null);
            jPanel.add(getJComboBoxLanguage(), null);
        }
        return jPanel;
    }

    /**
     * This method initializes jTabbedPane
     * 
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getJTabbedPaneMaterials ()
    {
        int i = 1;
        
        if (jTabbedPaneMaterials == null)
        {           
            jTabbedPaneMaterials = new JTabbedPane ()
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

                    selected = jTabbedPaneMaterials.getSelectedIndex ();

                    this.removeAll ();
                    for (MaterialEditorPanel panel : materialPanels)
                    {
                        jTabbedPaneMaterials.addTab (resources.getString ("materialtab") + " - " + panel.getMaterial ().name, panel);
                        i++;
                    }
                    jTabbedPaneMaterials.setSelectedIndex (selected);

                    super.update (g);
                }
            };
            jTabbedPaneMaterials.setBounds (new Rectangle (25, 25, 650, 120));

            for (MaterialEditorPanel panel : materialPanels)
            {
                jTabbedPaneMaterials.addTab (resources.getString ("materialtab") + " - " + panel.getMaterial ().name, panel);
                i++;
            }
        }
        return jTabbedPaneMaterials;
    }
    
    /**
     * This method initializes jTabbedPane
     * 
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getJTabbedPaneCurrencies ()
    {
        int i = 1;
        
        if (jTabbedPaneCurrencies == null)
        {           
            jTabbedPaneCurrencies = new JTabbedPane ()
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

                    selected = jTabbedPaneCurrencies.getSelectedIndex ();

                    this.removeAll ();
                    for (CurrencyEditorPanel panel : currencyPanels)
                    {
                        jTabbedPaneCurrencies.addTab (resources.getString ("currencytab") + " - " + panel.getCurrency ().name, panel);
                        i++;
                    }
                    jTabbedPaneCurrencies.setSelectedIndex (selected);

                    super.update (g);
                }
            };
            jTabbedPaneCurrencies.setBounds (new Rectangle (25, 145, 650, 120));

            for (CurrencyEditorPanel panel : currencyPanels)
            {
                jTabbedPaneCurrencies.addTab (resources.getString ("currencytab") + " - " + panel.getCurrency ().name, panel);
                i++;
            }
        }
        return jTabbedPaneCurrencies;
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
            save.setBounds(new Rectangle(120, 327, 112, 30));
            save.setText(resources.getString ("saveButton"));
            save.setBackground (new Color(205, 216, 249));
            save.addActionListener (new java.awt.event.ActionListener ()
            {
                public void actionPerformed (java.awt.event.ActionEvent e)
                {
                    Properties       properties = null;
                    List<VOMaterial> materials  = new ArrayList<VOMaterial> ();
                    List<VOCurrency> currencies = new ArrayList<VOCurrency> ();
                    
                    for(MaterialEditorPanel panel : materialPanels)
                    {
                        materials.add (panel.getMaterial ());
                    }
                    
                    for(CurrencyEditorPanel panel : currencyPanels)
                    {
                        currencies.add (panel.getCurrency ());
                    }
                    
                    materialManager.updateMaterials (materials, entityManager);
                    currencyManager.updateCurrencies (currencies, entityManager);
                    
                    properties = new Properties ();

                    try
                    {
                        properties.load (new FileReader(new File("configuration/settings.properties")));
                        properties.put ("language", jComboBoxLanguage.getSelectedItem ());
                        properties.store (new FileWriter(new File("configuration/settings.properties")), null);
                        
                        parent.setResources (ResourceBundle.getBundle("coinmanager", new Locale((String) properties.get ("language"))));
                    }
                    catch (FileNotFoundException e1)
                    {
                        logger.error (e1);
                    }
                    catch (IOException e1)
                    {
                        logger.error (e1);
                    }                    
                    closeFrame ();     
                }
            });
        }
        return save;
    }
    
    /**
     * This method initializes save 
     *  
     * @return javax.swing.JButton  
     */
    private JButton getUpdate ()
    {
        if (update == null)
        {
            update = new JButton ();
            update.setBounds(new Rectangle(320, 327, 250, 30));
            update.setText(resources.getString ("updateButton"));
            update.setBackground (new Color(205, 216, 249));
            update.addActionListener (new java.awt.event.ActionListener ()
            {
                public void actionPerformed (java.awt.event.ActionEvent e)
                {                    
                    for(CurrencyEditorPanel panel : currencyPanels)
                    {
                        try
                        {
                            panel.setJTextFieldRate (currencyManager.getExchangeRate (panel.getCurrency ().name));
                            panel.update (panel.getGraphics ());
                        }
                        catch (Exception e1)
                        {
                            logger.error ("Error updating currencies: ", e1);
                        }
                    }                   
                }
            });
        }
        return update;
    }
    
    public void closeFrame ()
    {
        this.dispose ();
    }

    /**
     * This method initializes jComboBoxLanguage	
     * 	
     * @return javax.swing.JComboBox	
     */
    private JComboBox getJComboBoxLanguage ()
    {
        File        fileFolder  = null;
        File[]      files       = null;
        String      fileName    = null;
        int         index       = -1;
        Properties  properties  = null;
        
        fileFolder = new File("configuration/");
        
        if (jComboBoxLanguage == null)
        {
            jComboBoxLanguage = new JComboBox ();
            jComboBoxLanguage.setBounds(new Rectangle(219, 275, 118, 19));
        }
        
        files = fileFolder.listFiles ();
        
        for(File file : files)
        {
            fileName = file.getName ();
            
            index = fileName.indexOf ('_');
            
            if(index != -1)
            {
                fileName = fileName.substring (index + 1, fileName.indexOf ('.'));
                jComboBoxLanguage.addItem (fileName);
            }
        }
 
        properties = new Properties ();
        try
        {
            properties.load (new FileReader(new File("configuration/settings.properties")));
            jComboBoxLanguage.setSelectedItem (properties.get ("language"));
        }
        catch (FileNotFoundException e)
        {
            logger.error (e);
        }
        catch (IOException e)
        {
            logger.error (e);
        }
        return jComboBoxLanguage;
    }

} // @jve:decl-index=0:visual-constraint="10,10"
