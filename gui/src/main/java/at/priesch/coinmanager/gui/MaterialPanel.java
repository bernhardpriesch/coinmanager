package at.priesch.coinmanager.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import at.priesch.coinmanager.servicecomponents.Materialmanager;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOMaterial;

/**
 * @author Bernhard PRIESCH
 */
public class MaterialPanel
    extends JPanel
{

    private static final long serialVersionUID  = 1L;

    private EntityManager     entityManager     = null;

    private JLabel            jLabelMaterial    = null;
    private JComboBox         jComboBoxMaterial = null;
    private JLabel            jLabelGewicht     = null;
    private JTextField        jTextFieldWeight  = null;
    private Materialmanager   materialManager   = null;
    private JComponent        parent            = null;
    private ResourceBundle    resources         = null;

    /**
     * This is the default constructor
     * 
     * @param entityManager
     * @param resources 
     */
    public MaterialPanel (final EntityManager entityManager, final JComponent parent, ResourceBundle resources)
    {
        super ();
        this.resources = resources;
        this.entityManager = entityManager;
        materialManager = new Materialmanager ();
        this.parent = parent;
        initialize ();
    }

    public MaterialPanel (final EntityManager entityManager, final JComponent parent, final VOMaterial material, final Double weight, final ResourceBundle resources)
    {
        super ();
        this.resources = resources;
        this.entityManager = entityManager;
        materialManager = new Materialmanager ();
        this.parent = parent;    
        initialize ();
        for (int i = 0; i < jComboBoxMaterial.getItemCount (); i++)
        {
            if (((VOMaterial)jComboBoxMaterial.getItemAt (i)).toString (resources).equals (material.toString (resources)))
            {
                jComboBoxMaterial.setSelectedIndex (i);
            }
        }
        jTextFieldWeight.setText (String.valueOf (weight));
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize ()
    {
        jLabelGewicht = new JLabel ();
        jLabelGewicht.setText (resources.getString ("weight"));
        jLabelGewicht.setBounds(new Rectangle(7, 27, 80, 17));
        jLabelGewicht.setHorizontalTextPosition (SwingConstants.RIGHT);
        jLabelGewicht.setHorizontalAlignment (SwingConstants.RIGHT);
        jLabelMaterial = new JLabel ();
        jLabelMaterial.setText (resources.getString ("material"));
        jLabelMaterial.setBounds(new Rectangle(7, 5, 80, 17));
        jLabelMaterial.setHorizontalTextPosition (SwingConstants.RIGHT);
        jLabelMaterial.setHorizontalAlignment (SwingConstants.RIGHT);
        this.setLayout (null);
        this.setBounds (new Rectangle (50, 50, 234, 48));
        this.add (jLabelMaterial, null);
        this.add (getJComboBoxMaterial (), null);
        this.add (jLabelGewicht, null);
        this.add (getJTextFieldWeight (), null);
        this.setBackground (new Color(205, 216, 249));
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
            jComboBoxMaterial.setBounds(new Rectangle(90, 2, 130, 20));
            jComboBoxMaterial.addItemListener (new java.awt.event.ItemListener ()
            {
                public void itemStateChanged (java.awt.event.ItemEvent e)
                {
                    updateParent ();
                }
            });

            for (VOMaterial material : materialManager.getMaterials (entityManager))
            {
                material.setResources (resources);
                jComboBoxMaterial.addItem (material);
            }
        }

        return jComboBoxMaterial;
    }

    /**
     * This method initializes jTextFieldWeight
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getJTextFieldWeight ()
    {
        if (jTextFieldWeight == null)
        {
            jTextFieldWeight = new JTextField ();
            jTextFieldWeight.setBounds(new Rectangle(90, 24, 130, 21));
        }
        return jTextFieldWeight;
    }

    public JComboBox getjComboBoxMaterial ()
    {
        return jComboBoxMaterial;
    }

    public JTextField getjTextFieldWeight ()
    {
        return jTextFieldWeight;
    }

    public void updateParent ()
    {
        if (parent.getGraphics () != null)
        {
            parent.update (parent.getGraphics ());
        }
    }
} // @jve:decl-index=0:visual-constraint="10,10"
