package at.priesch.coinmanager.gui;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import at.priesch.coinmanager.servicecomponents.valueobjects.VOMaterial;

/**
 * @author Bernhard PRIESCH
 */
public class MaterialEditorPanel
    extends JPanel
{

    private static final long serialVersionUID  = 1L;

    private JLabel            jLabelMaterial    = null;
    private JLabel            jLabelRate     = null;
    private JTextField        jTextFieldRate  = null;
    private JComponent        parent            = null;
    private VOMaterial        material          = null;

    private JLabel jLabelMaterialName = null;
    
    private ResourceBundle    resources = null;

    public MaterialEditorPanel (final JComponent parent, final VOMaterial material, final Double rate, final ResourceBundle resources)
    {
        super ();
        this.resources = resources;
        this.material = material;
        this.parent = parent;
        initialize ();
        material.setResources (resources);
        jLabelMaterialName.setText (material.toString ());
        jTextFieldRate.setText (String.valueOf (rate));
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize ()
    {
        jLabelMaterialName = new JLabel();
        jLabelMaterialName.setBounds(new Rectangle(80, 5, 81, 17));
        jLabelMaterialName.setText("Name");
        jLabelRate = new JLabel ();
        jLabelRate.setText (resources.getString ("rate"));
        jLabelRate.setBounds(new Rectangle(7, 27, 70, 17));
        jLabelMaterial = new JLabel ();
        jLabelMaterial.setText (resources.getString ("material"));
        jLabelMaterial.setBounds(new Rectangle(7, 5, 70, 17));
        this.setLayout (null);
        this.setBounds (new Rectangle (50, 50, 234, 48));
        this.add (jLabelMaterial, null);
        this.add (jLabelRate, null);
        this.add (getJTextFieldRate (), null);
        this.add(jLabelMaterialName, null);
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
            jTextFieldRate.setBounds(new Rectangle(80, 24, 122, 21));
        }
        return jTextFieldRate;
    }

    public VOMaterial getMaterial ()
    {
        material.rate = Double.parseDouble (jTextFieldRate.getText ());
        return material;
    }

    public void updateParent ()
    {
        if (parent.getGraphics () != null)
        {
            parent.update (parent.getGraphics ());
        }
    }
} // @jve:decl-index=0:visual-constraint="10,10"
