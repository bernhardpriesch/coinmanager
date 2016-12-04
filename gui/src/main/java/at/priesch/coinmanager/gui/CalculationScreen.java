package at.priesch.coinmanager.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import at.priesch.coinmanager.datamodel.datatypes.MaterialName;
import at.priesch.coinmanager.servicecomponents.Coinmanager;

/**
 * @author Bernhard PRIESCH
 */
public class CalculationScreen
    extends JFrame
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Coinmanager                       coinManager      = null;
    private NumberFormat                      numberFormat     = null;
    
    private JPanel jPanel = null;
    private JLabel jLabel = null;
    private JLabel jLabel1 = null;
    private JLabel jLabel11 = null;
    private JLabel jLabel12 = null;
    private JLabel countGold = null;
    private JLabel countSilver = null;
    private JLabel countPlatin = null;
    private JSeparator separator = null;
    private JLabel jLabel121 = null;
    private JLabel countSum = null;
    private JLabel jLabel2 = null;
    private JLabel jLabel13 = null;
    private JLabel jLabel131 = null;
    private JLabel jLabel1311 = null;
    private JLabel silverDenomination = null;
    private JLabel silverMaterial = null;
    private JLabel silverEstimatedValue = null;
    private JLabel jLabel21 = null;
    private JLabel jLabel132 = null;
    private JLabel jLabel1312 = null;
    private JLabel jLabel13111 = null;
    private JLabel goldDenomination = null;
    private JLabel goldMaterial = null;
    private JLabel goldEstimatedValue = null;
    private JLabel jLabel211 = null;
    private JLabel jLabel1321 = null;
    private JLabel jLabel13121 = null;
    private JLabel jLabel131111 = null;
    private JLabel platinDenomination = null;
    private JLabel platinMaterial = null;
    private JLabel platinEstimatedValue = null;
    private JSeparator separator1 = null;
    private JLabel jLabel2111 = null;
    private JLabel jLabel13211 = null;
    private JLabel jLabel131211 = null;
    private JLabel jLabel1311111 = null;
    private JLabel sumDenomination = null;
    private JLabel sumMaterial = null;
    private JLabel sumEstimatedValue = null;
    
    private ResourceBundle resources = null;
    /**
     * This method initializes 
     * 
     */
    public CalculationScreen(final EntityManager entityManager, final ResourceBundle resources) 
    {
    	super();
    	this.resources = resources;
    	initialize();
    	coinManager = new Coinmanager ();
    	numberFormat = NumberFormat.getCurrencyInstance ();
    	numberFormat.setGroupingUsed (true);
    	countSilver.setText (String.valueOf (coinManager.countCoins (entityManager, MaterialName.AG)));
    	countGold.setText (String.valueOf (coinManager.countCoins (entityManager, MaterialName.AU)));
    	countPlatin.setText (String.valueOf (coinManager.countCoins (entityManager, MaterialName.PT)));
    	countSum.setText (String.valueOf (coinManager.countCoins (entityManager, null)));
    	
    	silverDenomination.setText (String.valueOf (numberFormat.format (coinManager.calculateDenominatioin (entityManager, MaterialName.AG))));
    	goldDenomination.setText (String.valueOf (numberFormat.format (coinManager.calculateDenominatioin (entityManager, MaterialName.AU))));
    	platinDenomination.setText (String.valueOf (numberFormat.format (coinManager.calculateDenominatioin (entityManager, MaterialName.PT))));
    	sumDenomination.setText (String.valueOf (numberFormat.format (coinManager.calculateDenominatioin (entityManager, null))));
    	
    	silverMaterial.setText (String.valueOf (numberFormat.format (coinManager.calculateMaterial (entityManager, MaterialName.AG))));
    	goldMaterial.setText (String.valueOf (numberFormat.format (coinManager.calculateMaterial (entityManager, MaterialName.AU))));
    	platinMaterial.setText (String.valueOf (numberFormat.format (coinManager.calculateMaterial (entityManager, MaterialName.PT))));
    	sumMaterial.setText (String.valueOf (numberFormat.format (coinManager.calculateMaterial (entityManager, null))));
    	
    	silverEstimatedValue.setText (String.valueOf (numberFormat.format (coinManager.calculateEstimatedValue (entityManager, MaterialName.AG))));
        goldEstimatedValue.setText (String.valueOf (numberFormat.format (coinManager.calculateEstimatedValue (entityManager, MaterialName.AU))));
        platinEstimatedValue.setText (String.valueOf (numberFormat.format (coinManager.calculateEstimatedValue (entityManager, MaterialName.PT))));
        sumEstimatedValue.setText (String.valueOf (numberFormat.format (coinManager.calculateEstimatedValue (entityManager, null))));
    }

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        this.setSize(new Dimension(383, 632));
        this.setContentPane(getJPanel());
        this.setTitle(resources.getString ("overview"));
        this.setResizable (false);
        this.addWindowListener (new java.awt.event.WindowAdapter ()
        {
            public void windowClosing (java.awt.event.WindowEvent e)
            {
                System.out.println ("windowClosing()"); // TODO Auto-generated Event stub windowClosing()
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
            sumEstimatedValue = new JLabel();
            sumEstimatedValue.setBounds(new Rectangle(200, 545, 90, 17));
            sumEstimatedValue.setText("0");
            sumMaterial = new JLabel();
            sumMaterial.setBounds(new Rectangle(200, 524, 90, 17));
            sumMaterial.setText("0");
            sumDenomination = new JLabel();
            sumDenomination.setBounds(new Rectangle(200, 501, 90, 17));
            sumDenomination.setText("0");
            jLabel1311111 = new JLabel();
            jLabel1311111.setBounds(new Rectangle(62, 544, 140, 17));
            jLabel1311111.setText(resources.getString ("estimatedValue"));
            jLabel131211 = new JLabel();
            jLabel131211.setBounds(new Rectangle(62, 523, 140, 17));
            jLabel131211.setText(resources.getString ("valueOfMaterials"));
            jLabel13211 = new JLabel();
            jLabel13211.setBounds(new Rectangle(62, 501, 140, 17));
            jLabel13211.setText(resources.getString ("denomination"));
            jLabel2111 = new JLabel();
            jLabel2111.setBounds(new Rectangle(29, 479, 141, 17));
            jLabel2111.setText(resources.getString ("sum"));
            platinEstimatedValue = new JLabel();
            platinEstimatedValue.setBounds(new Rectangle(200, 426, 90, 17));
            platinEstimatedValue.setText("0");
            platinMaterial = new JLabel();
            platinMaterial.setBounds(new Rectangle(200, 406, 90, 17));
            platinMaterial.setText("0");
            platinDenomination = new JLabel();
            platinDenomination.setBounds(new Rectangle(200, 384, 90, 17));
            platinDenomination.setText("0");
            jLabel131111 = new JLabel();
            jLabel131111.setBounds(new Rectangle(62, 426, 140, 17));
            jLabel131111.setText(resources.getString ("estimatedValue"));
            jLabel13121 = new JLabel();
            jLabel13121.setBounds(new Rectangle(62, 405, 140, 17));
            jLabel13121.setText(resources.getString ("valueOfMaterials"));
            jLabel1321 = new JLabel();
            jLabel1321.setBounds(new Rectangle(61, 384, 140, 17));
            jLabel1321.setText(resources.getString ("denomination"));
            jLabel211 = new JLabel();
            jLabel211.setBounds(new Rectangle(28, 360, 180, 17));
            jLabel211.setText(resources.getString ("valueOfPlatinCoins"));
            goldEstimatedValue = new JLabel();
            goldEstimatedValue.setBounds(new Rectangle(200, 331, 90, 17));
            goldEstimatedValue.setText("0");
            goldMaterial = new JLabel();
            goldMaterial.setBounds(new Rectangle(200, 309, 90, 17));
            goldMaterial.setText("0");
            goldDenomination = new JLabel();
            goldDenomination.setBounds(new Rectangle(200, 286, 90, 17));
            goldDenomination.setText("0");
            jLabel13111 = new JLabel();
            jLabel13111.setBounds(new Rectangle(62, 330, 140, 17));
            jLabel13111.setText(resources.getString ("estimatedValue"));
            jLabel1312 = new JLabel();
            jLabel1312.setBounds(new Rectangle(62, 308, 140, 17));
            jLabel1312.setText(resources.getString ("valueOfMaterials"));
            jLabel132 = new JLabel();
            jLabel132.setBounds(new Rectangle(62, 285, 140, 17));
            jLabel132.setText(resources.getString ("denomination"));
            jLabel21 = new JLabel();
            jLabel21.setBounds(new Rectangle(28, 261, 180, 17));
            jLabel21.setText(resources.getString ("valueOfGoldCoins"));
            silverEstimatedValue = new JLabel();
            silverEstimatedValue.setBounds(new Rectangle(200, 234, 90, 17));
            silverEstimatedValue.setText("0");
            silverMaterial = new JLabel();
            silverMaterial.setBounds(new Rectangle(200, 209, 90, 17));
            silverMaterial.setText("0");
            silverDenomination = new JLabel();
            silverDenomination.setBounds(new Rectangle(200, 184, 90, 17));
            silverDenomination.setText("0");
            jLabel1311 = new JLabel();
            jLabel1311.setBounds(new Rectangle(61, 233, 140, 17));
            jLabel1311.setText(resources.getString ("estimatedValue"));
            jLabel131 = new JLabel();
            jLabel131.setBounds(new Rectangle(62, 209, 140, 17));
            jLabel131.setText(resources.getString ("valueOfMaterials"));
            jLabel13 = new JLabel();
            jLabel13.setBounds(new Rectangle(62, 184, 140, 17));
            jLabel13.setText(resources.getString ("denomination"));
            jLabel2 = new JLabel();
            jLabel2.setBounds(new Rectangle(29, 160, 180, 17));
            jLabel2.setText(resources.getString ("valueOfSilverCoins"));
            countSum = new JLabel();
            countSum.setBounds(new Rectangle(127, 121, 38, 17));
            countSum.setText("0");
            jLabel121 = new JLabel();
            jLabel121.setBounds(new Rectangle(63, 121, 60, 17));
            jLabel121.setText(resources.getString ("sum"));
            separator = new JSeparator ();
            separator.setBounds (60, 116, 105, 5);
            countPlatin = new JLabel();
            countPlatin.setBounds(new Rectangle(128, 93, 37, 17));
            countPlatin.setText("0");
            countSilver = new JLabel();
            countSilver.setBounds(new Rectangle(127, 48, 36, 17));
            countSilver.setText("0");
            countGold = new JLabel();
            countGold.setBounds(new Rectangle(127, 72, 38, 17));
            countGold.setText("0");
            jLabel12 = new JLabel();
            jLabel12.setBounds(new Rectangle(63, 93, 50, 17));
            jLabel12.setText(resources.getString ("Platin"));
            jLabel11 = new JLabel();
            jLabel11.setBounds(new Rectangle(63, 72, 49, 17));
            jLabel11.setText(resources.getString ("Gold"));
            jLabel1 = new JLabel();
            jLabel1.setBounds(new Rectangle(63, 48, 51, 17));
            jLabel1.setText(resources.getString ("Silver"));
            jLabel = new JLabel();
            jLabel.setBounds(new Rectangle(28, 16, 150, 22));
            jLabel.setText(resources.getString ("amountCoins"));
            jPanel = new JPanel ();
            jPanel.setLayout(null);
            jPanel.add(jLabel, null);
            jPanel.add(jLabel1, null);
            jPanel.add(jLabel11, null);
            jPanel.add(jLabel12, null);
            jPanel.add(countGold, null);
            jPanel.add(countSilver, null);
            jPanel.add(countPlatin, null);
            jPanel.add (separator, null);
            jPanel.add(jLabel121, null);
            jPanel.add(countSum, null);
            jPanel.add(jLabel2, null);
            jPanel.add(jLabel13, null);
            jPanel.add(jLabel131, null);
            jPanel.add(jLabel1311, null);
            jPanel.add(silverDenomination, null);
            jPanel.add(silverMaterial, null);
            jPanel.add(silverEstimatedValue, null);
            jPanel.add(jLabel21, null);
            jPanel.add(jLabel132, null);
            jPanel.add(jLabel1312, null);
            jPanel.add(jLabel13111, null);
            jPanel.add(goldDenomination, null);
            jPanel.add(goldMaterial, null);
            jPanel.add(goldEstimatedValue, null);
            jPanel.add(jLabel211, null);
            jPanel.add(jLabel1321, null);
            jPanel.add(jLabel13121, null);
            jPanel.add(jLabel131111, null);
            jPanel.add(platinDenomination, null);
            jPanel.add(platinMaterial, null);
            jPanel.add(platinEstimatedValue, null);
            jPanel.add(getSeparator1(), null);
            jPanel.add(jLabel2111, null);
            jPanel.add(jLabel13211, null);
            jPanel.add(jLabel131211, null);
            jPanel.add(jLabel1311111, null);
            jPanel.add(sumDenomination, null);
            jPanel.add(sumMaterial, null);
            jPanel.add(sumEstimatedValue, null);
            jPanel.setBackground (new Color(205, 216, 249));
        }
        return jPanel;
    }

    /**
     * This method initializes separator1	
     * 	
     * @return javax.swing.JSeparator	
     */
    private JSeparator getSeparator1 ()
    {
        if (separator1 == null)
        {
            separator1 = new JSeparator ();
            separator1.setBounds(new Rectangle(30, 465, 300, 5));
        }
        return separator1;
    }

  

}  //  @jve:decl-index=0:visual-constraint="10,10"
