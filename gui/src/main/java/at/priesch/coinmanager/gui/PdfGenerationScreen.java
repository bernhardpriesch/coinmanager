package at.priesch.coinmanager.gui;

import at.priesch.coinmanager.pdfgenerator.PDFGenerator;
import at.priesch.coinmanager.servicecomponents.Coinmanager;

import javax.persistence.EntityManager;
import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

/**
 * @author Bernhard PRIESCH
 */
public class PdfGenerationScreen
    extends JFrame
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JPanel     jPanel      = null;
    private JLabel     jLabelStart = null;
    private JTextField startTF     = null;
    private JLabel     jLabelEnd   = null;
    private JTextField endTF       = null;
    private JSeparator separator   = null;
    private JButton    generatePdf = null;

    private Coinmanager    coinManager   = null;
    private EntityManager  entityManager = null;
    private ResourceBundle resources     = null;
    /**
     * This method initializes
     *
     */
    public PdfGenerationScreen (final EntityManager entityManager, final ResourceBundle resources)
    {
    	super();
    	this.entityManager = entityManager;
    	this.resources = resources;
    	initialize();
    	coinManager = new Coinmanager ();

    }

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        this.setSize(new Dimension(383, 632));
        this.setContentPane(getJPanel());
        this.setTitle(resources.getString ("pdfButton"));
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
        Calendar calendar = new GregorianCalendar ();
        calendar.setTime (new Date (System.currentTimeMillis ()));
        if (jPanel == null)
        {
            jLabelStart = new JLabel();
            jLabelStart.setBounds(new Rectangle(40, 145, 90, 17));
            jLabelStart.setText(resources.getString ("startYear"));
            startTF = new JTextField ();
            startTF.setSize (200, startTF.getHeight ());
            startTF.setPreferredSize (new Dimension (100, startTF.getHeight ()));
            startTF.setName ("startTF");
            startTF.setBounds (new Rectangle (100, 145, 150, 22));
            startTF.setMinimumSize (new Dimension (100, startTF.getHeight ()));
            startTF.setText ("0");
            jLabelEnd = new JLabel();
            jLabelEnd.setBounds(new Rectangle(40, 245, 90, 17));
            jLabelEnd.setText(resources.getString ("endYear"));
            endTF = new JTextField ();
            endTF.setSize (200, startTF.getHeight ());
            endTF.setPreferredSize (new Dimension (100, endTF.getHeight ()));
            endTF.setName ("startTF");
            endTF.setBounds (new Rectangle (100, 245, 150, 22));
            endTF.setMinimumSize (new Dimension (100, endTF.getHeight ()));
            endTF.setText (String.valueOf (calendar.get (Calendar.YEAR)));
            separator = new JSeparator ();
            separator.setBounds (20, 320, 350, 5);
            generatePdf = new JButton ();
            generatePdf.setBounds(new Rectangle(75, 400, 228, 34));
            generatePdf.setText(resources.getString ("pdfButton"));
            generatePdf.addActionListener (new java.awt.event.ActionListener ()
            {
                public void actionPerformed (java.awt.event.ActionEvent e)
                {
                    PDFGenerator generator = new PDFGenerator ();

                    generator.createPDF (coinManager, resources, entityManager, Integer.parseInt (startTF.getText ()), Integer.parseInt (endTF.getText ()));

                    closeFrame ();
                }
            });
            jPanel = new JPanel ();
            jPanel.setLayout(null);
            jPanel.add(jLabelStart, null);
            jPanel.add(startTF, null);
            jPanel.add(jLabelEnd, null);
            jPanel.add(endTF, null);
            jPanel.add (separator, null);
            jPanel.add(generatePdf, null);
            jPanel.setBackground (new Color(205, 216, 249));
        }
        return jPanel;
    }

    public void closeFrame ()
    {
        this.dispose ();
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
