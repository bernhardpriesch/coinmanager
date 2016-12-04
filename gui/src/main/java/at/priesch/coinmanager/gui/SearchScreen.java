package at.priesch.coinmanager.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.TableRowSorter;

import at.priesch.coinmanager.datamodel.datatypes.MaterialName;
import at.priesch.coinmanager.gui.components.CoinOverviewTableModel;
import at.priesch.coinmanager.servicecomponents.Coinmanager;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOCoin;

/**
 * @author Bernhard PRIESCH
 */
public class SearchScreen
    extends JFrame
{

    /**
     * 
     */
    private static final long                 serialVersionUID = 1L;

    private EntityManager                     entityManager    = null;
    private Coinmanager                       coinManager      = null;
    private List<VOCoin>                      coins            = null; // @jve:decl-index=0:
    private JPanel                            jPanel           = null;
    private JScrollPane                       jScrollPane      = null;
    private JTable                            jTable           = null;
    private EditScreen                        editScreen       = null;
    private SearchScreen                      screen           = null;
    private RowSorter<CoinOverviewTableModel> sorter           = null;
    private CoinOverviewTableModel            model            = null;

    private JLabel jLabelMaterial = null;

    private JComboBox jComboBoxMaterial = null;

    private JLabel jLabelName = null;

    private JTextField jTextField = null;
    
    private ResourceBundle resources = null;

    /**
     * This method initializes
     * 
     * @param entityManager
     * 
     */
    public SearchScreen (final EntityManager entityManager, final ResourceBundle resources)
    {
        super ();
        this.resources = resources;
        this.entityManager = entityManager;
        screen = this;
        coinManager = new Coinmanager ();
        initialize ();
    }

    /**
     * This method initializes this
     * 
     */
    private void initialize ()
    {
        this.setSize (new Dimension (846, 328));
        this.setContentPane (getJPanel ());
        this.setTitle ("Search");
        this.setResizable (false);
        this.addWindowListener (new java.awt.event.WindowAdapter ()
        {
            public void windowClosing (java.awt.event.WindowEvent e)
            {
                System.out.println ("windowClosing()"); // TODO Auto-generated Event stub
                // windowClosing()
                e.getWindow ().setVisible (false);
                e.getWindow ().dispose ();
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
            jLabelName = new JLabel();
            jLabelName.setBounds(new Rectangle(243, 9, 45, 20));
            jLabelName.setText(resources.getString ("name"));
            jLabelMaterial = new JLabel();
            jLabelMaterial.setBounds(new Rectangle(38, 10, 66, 18));
            jLabelMaterial.setText(resources.getString ("material"));
            jPanel = new JPanel ();
            jPanel.setLayout (null);
            jPanel.add (getJScrollPane (), null);
            jPanel.setBackground (new Color(205, 216, 249));
            jPanel.add(jLabelMaterial, null);
            jPanel.add(getJComboBoxMaterial(), null);
            jPanel.add(jLabelName, null);
            jPanel.add(getJTextField(), null);
        }
        return jPanel;
    }

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane ()
    {
        if (jScrollPane == null)
        {
            jScrollPane = new JScrollPane ();
            jScrollPane.setBounds (new Rectangle (38, 36, 774, 227));
            jScrollPane.getViewport ().setBackground (Color.WHITE);
            jScrollPane.setViewportView (getJTable ());
            jScrollPane.setBackground (Color.WHITE);
        }
        return jScrollPane;
    }

    /**
     * This method initializes jTable
     * 
     * @return javax.swing.JTable
     */
    private JTable getJTable ()
    {
        if (jTable == null)
        {
            coins = coinManager.getCoins (entityManager);
            model = new CoinOverviewTableModel (resources);
            model.setData (coins);

            jTable = new JTable (model);
            jTable.addMouseListener (new java.awt.event.MouseAdapter ()
            {
                public void mouseClicked (java.awt.event.MouseEvent e)
                {
                    if (e.getClickCount () == 2)
                    {
                        editScreen = new EditScreen (entityManager, coins.get (sorter.convertRowIndexToModel (jTable.getSelectedRow ())), screen, resources);
                        editScreen.setVisible (true);
                    }
                }
            });

            jTable.addKeyListener (new java.awt.event.KeyAdapter ()
            {
                public void keyPressed (java.awt.event.KeyEvent e)
                {
                    if (e.getKeyCode () == KeyEvent.VK_DELETE)
                    {
                        for (int i : jTable.getSelectedRows ())
                        {
                            coinManager.deleteCoin (coins.get (sorter.convertRowIndexToModel (i)), entityManager);                           
                        }
                        coins = coinManager.getCoins (entityManager);
                        update ();                        
                    }
                }
            });
            sorter = new TableRowSorter<CoinOverviewTableModel> (model);
            jTable.setRowSorter (sorter);
            jTable.setBackground (Color.WHITE);
        }
        return jTable;
    }

    public void update ()
    {       
        model.setData (coins);
        jTable.setModel (model);
        sorter = new TableRowSorter<CoinOverviewTableModel> (model);
        jTable.setRowSorter (sorter);
        jScrollPane.update (jScrollPane.getGraphics ());
    }

    /**
     * This method initializes jComboBoxMaterial	
     * 	
     * @return javax.swing.JComboBox	
     */
    private JComboBox getJComboBoxMaterial ()
    {
        if (jComboBoxMaterial == null)
        {
            jComboBoxMaterial = new JComboBox ();
            jComboBoxMaterial.setBounds(new Rectangle(107, 10, 122, 18));
            
            jComboBoxMaterial.addItem ("-");
            
            for(MaterialName material : MaterialName.values ())
            {
                jComboBoxMaterial.addItem (new MaterialNameItem(material, resources.getString (material.toString ())));
            }
            
            jComboBoxMaterial.setSelectedItem ("-");
            jComboBoxMaterial.addItemListener (new java.awt.event.ItemListener ()
            {
                public void itemStateChanged (java.awt.event.ItemEvent e)
                {
                    if(jComboBoxMaterial.getSelectedItem ().toString ().equals ("-"))
                    {
                        coins = coinManager.getCoins (entityManager);
                    }
                    else
                    {
                        coins = coinManager.getCoinsByMaterial (entityManager, ((MaterialNameItem)jComboBoxMaterial.getSelectedItem ()).getMaterial ().toString (), jTextField.getText ());
                    }
                    update ();
                }
            });
        }
        return jComboBoxMaterial;
    }

    /**
     * This method initializes jTextField	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getJTextField ()
    {
        if (jTextField == null)
        {
            jTextField = new JTextField ();
            jTextField.setLocation(new Point(297, 10));
            jTextField.setSize(new Dimension(200, 18));
            jTextField.addKeyListener (new java.awt.event.KeyAdapter ()
            {
                public void keyTyped (java.awt.event.KeyEvent e)
                {
                    if(jComboBoxMaterial.getSelectedItem ().toString ().equals ("-"))
                    {
                        coins = coinManager.getCoinsByName (entityManager, jTextField.getText ());
                    }
                    else
                    {
                        coins = coinManager.getCoinsByMaterial (entityManager, ((MaterialNameItem)jComboBoxMaterial.getSelectedItem ()).getMaterial ().toString (), jTextField.getText ());
                    }
                    update ();
                }
            });
        }
        return jTextField;
    }
    
    protected class MaterialNameItem
    {
        private MaterialName    material    = null;
        private String          name        = null;
        
        public MaterialNameItem (final MaterialName material, final String name)
        {
            this.material   = material;
            this.name       = name;
        }

        public MaterialName getMaterial ()
        {
            return material;
        }

        public String getName ()
        {
            return name;
        }
        
        @Override
        public String toString ()
        {
            return name;
        }
        
    }
} // @jve:decl-index=0:visual-constraint="10,10"
