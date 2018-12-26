package at.priesch.coinmanager.gui;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import at.priesch.coinmanager.pdfgenerator.PDFGenerator;
import at.priesch.coinmanager.servicecomponents.Coinmanager;
import org.apache.log4j.Logger;

/**
 * @author Bernhard PRIESCH
 */
public class StartScreen 
	extends JFrame 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Logger  logger      = Logger.getLogger (getClass ());  //  @jve:decl-index=0:

    private        JPanel              content             = null;
    private        JButton             add                 = null;
    private static StartScreen         screen              = null;
    private        AddScreen           addScreen           = null;
    private        SearchScreen        searchScreen        = null;
    private        SettingsScreen      settingsScreen      = null;
    private        CalculationScreen   calculationScreen   = null;
    private        PdfGenerationScreen pdfGenerationScreen = null;
	
	private EntityManagerFactory    entityManagerFactory    = null;  //  @jve:decl-index=0:
	private EntityManager           entityManager           = null;  //  @jve:decl-index=0:
	private ResourceBundle          resources               = null;  //  @jve:decl-index=0:
	private Locale                  currentLocale           = null;  //  @jve:decl-index=0:

//	private static final    String  DB_HOSTNAME             = "192.168.0.40";  //  @jve:decl-index=0:
	private static final    String  DB_USERNAME             = "coinmanager";
	private static final    String  DB_PASSWORD             = "coinmanager";
	
	private Font font                                       = new Font ("SansSerif", Font.BOLD, 12);
	
	private String imageDirectory = null;

    private JButton search = null;

    private JButton calculate = null;

    private JButton settings = null;

    private JButton pdf = null;

    private JLabel logo = null;
	
	/**
	 * This method initializes 
	 * 
	 */
	public StartScreen() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() 
	{
	    Properties properties = null;
	    
	    properties = new Properties ();
        try
        {
            properties.load (new FileReader(new File("configuration/settings.properties")));
            currentLocale = new Locale((String) properties.get ("language"));
            logger.info ("Locale set to: " + currentLocale);
        }
        catch (FileNotFoundException e)
        {
            logger.error (e);
        }
        catch (IOException e)
        {
            logger.error (e);
        }
	    if(null == currentLocale)
        {
            currentLocale = Locale.getDefault ();
            logger.info ("Locale set to: " + currentLocale);
        }
        resources = ResourceBundle.getBundle("coinmanager", currentLocale);
        
        this.setSize(new Dimension(443, 594));
        this.setContentPane(getContent());
        this.setTitle("Coinmanager");
        this.setResizable (false);
        this.getContentPane ().setFont (font);
        this.setFont (font);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        	public void windowClosing(java.awt.event.WindowEvent e) {
        		System.out.println("windowClosing()"); // TODO Auto-generated Event stub windowClosing()
        		e.getWindow().setVisible(false);
        		e.getWindow().dispose();
                screen.dispose();
        		screen.stop ();
        	}
        });
			
	}

	/**
	 * This method initializes Content	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getContent() {
		if (content == null) {
			logo = new JLabel();
			logo.setBounds(new Rectangle(65, 12, 291, 169));
			logo.setText("");
			logo.setPreferredSize(new Dimension(291, 169));
			logo.setBounds(new Rectangle(33, 12, 372, 169));
			logo.setIcon (new ImageIcon(new ImageIcon("images/CoinmanagerLogo.png").getImage ().getScaledInstance (logo.getWidth (), logo.getHeight (), Image.SCALE_AREA_AVERAGING)));
			content = new JPanel();
			content.setLayout(null);
			content.setPreferredSize(new Dimension(538, 334));
			content.setBackground(new Color(205, 216, 249));
			content.add(getAdd(), null);
			content.add(getSearch(), null);
			content.add(getCalculate(), null);
			content.add(getSettings(), null);
            content.add(getPdf (), null);
			content.add(logo, null);
		}
		return content;
	}

	/**
	 * This method initializes Add	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAdd() 
	{
		ImageIcon icon = null;
		icon = new ImageIcon("images/add-icon.png");
		icon.setImage(icon.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
		if (add == null) {
			add = new JButton();
			add.setBorder (null);
			add.setBackground(new Color(205, 216, 249));
			add.setToolTipText(resources.getString ("addButton"));
			add.setBounds(new Rectangle(76, 200, 90, 90));
			add.setIcon(icon);
			add.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) 
				{
					addScreen = new AddScreen(screen, entityManager, resources, font);
	                addScreen.setFont (font);
					addScreen.setVisible(true);
				}
			});
		}
		return add;
	}

	/**
     * This method initializes search	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getSearch ()
    {
        if (search == null)
        {
            ImageIcon icon1 = new ImageIcon("images/search.png");
            icon1.setImage(icon1.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
            search = new JButton ();
            search.setBorder(null);
            search.setToolTipText(resources.getString ("searchButton"));
            search.setBounds(new Rectangle(271, 200, 90, 90));
            search.setIcon(icon1);
            search.setBackground(new Color(205, 216, 249));
            search.addActionListener (new java.awt.event.ActionListener ()
            {
                public void actionPerformed (java.awt.event.ActionEvent e)
                {
                    searchScreen = new SearchScreen(entityManager, resources);
                    searchScreen.setFont (font);
                    searchScreen.setVisible(true);
                }
            });
        }
        return search;
    }

    /**
     * This method initializes calculate	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getCalculate ()
    {
        if (calculate == null)
        {
            ImageIcon icon2 = new ImageIcon("images/calculate.png");
            icon2.setImage(icon2.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
            calculate = new JButton ();
            calculate.setBorder(null);
            calculate.setToolTipText(resources.getString ("calculateButton"));
            calculate.setBounds(new Rectangle(76, 335, 90, 90));
            calculate.setIcon(icon2);
            calculate.setBackground(new Color(205, 216, 249));
            calculate.addActionListener (new java.awt.event.ActionListener ()
            {
                public void actionPerformed (java.awt.event.ActionEvent e)
                {
                    calculationScreen = new CalculationScreen(entityManager, resources);
                    calculationScreen.setFont (font);
                    calculationScreen.setVisible(true);
                }
            });
        }
        return calculate;
    }

    /**
     * This method initializes settings	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getSettings ()
    {
        if (settings == null)
        {
            ImageIcon icon21 = new ImageIcon("images/settings.png");
            icon21.setImage(icon21.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
            settings = new JButton ();
            settings.setBorder(null);
            settings.setToolTipText(resources.getString ("settingsButton"));
            settings.setBounds(new Rectangle(271, 335, 90, 90));
            settings.setIcon(icon21);
            settings.setBackground(new Color(205, 216, 249));
            settings.addActionListener (new java.awt.event.ActionListener ()
            {
                public void actionPerformed (java.awt.event.ActionEvent e)
                {
                    settingsScreen = new SettingsScreen(screen, entityManager, resources);
                    settingsScreen.setFont (font);
                    settingsScreen.setVisible(true);
                }
            });
        }
        return settings;
    }

    /**
     * This method initializes settings
     *
     * @return javax.swing.JButton
     */
    private JButton getPdf ()
    {
        if (pdf == null)
        {
            ImageIcon icon21 = new ImageIcon("images/pdf.png");
            icon21.setImage(icon21.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
            pdf = new JButton ();
            pdf.setBorder(null);
            pdf.setToolTipText(resources.getString ("pdfButton"));
            pdf.setBounds(new Rectangle(170, 450, 90, 90));
            pdf.setIcon(icon21);
            pdf.setBackground(new Color(205, 216, 249));
            pdf.addActionListener (new java.awt.event.ActionListener ()
            {
                public void actionPerformed (java.awt.event.ActionEvent e)
                {
                    pdfGenerationScreen = new PdfGenerationScreen (entityManager, resources);
                    pdfGenerationScreen.setFont (font);
                    pdfGenerationScreen.setVisible(true);
                }
            });
        }
        return pdf;
    }

    public static void main(final String[] args)
	{
		screen = new StartScreen();
		try
        {
            screen.start();
            screen.setVisible(true);
        }
        catch (Exception e)
        {
            System.exit (0);
        }
		
		
	}
	
	public void start () 
	    throws Exception
	{
	    Map<String, String> persistenceProperties   = new HashMap<String, String> ();

        try
        {
            persistenceProperties.put("hibernate.archive.autodetection", "class, hbm");
            persistenceProperties.put("hibernate.show_sql", "true");
            persistenceProperties.put("hibernate.format_sql", "false");
            persistenceProperties.put("hibernate.hbm2ddl.auto", "update");           
            persistenceProperties.put("hibernate.connection.username", DB_USERNAME);
            persistenceProperties.put("hibernate.connection.password", DB_PASSWORD);
            persistenceProperties.put("hibernate.c3p0.min_size", "1");
            persistenceProperties.put("hibernate.c3p0.max_size", "5");
            persistenceProperties.put("hibernate.c3p0.timeout", "300");
            persistenceProperties.put("hibernate.c3p0.max_statements", "50");
            persistenceProperties.put("hibernate.c3p0.idle_test_period", "3000");
            
//            persistenceProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//            persistenceProperties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
//            persistenceProperties.put("hibernate.connection.url", "jdbc:postgresql://" + DB_HOSTNAME + "/coinmanager");
            
            persistenceProperties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            persistenceProperties.put("hibernate.connection.driver_class", "org.h2.Driver");
            persistenceProperties.put("hibernate.connection.url", "jdbc:h2:./h2/coinmanager");
            

            // Start EntityManagerFactory
            entityManagerFactory    = Persistence.createEntityManagerFactory ("COINMANAGER", persistenceProperties);
            entityManager           = entityManagerFactory.createEntityManager ();

        }
        catch (Exception e)
        {
            logger.error (e);
            e.printStackTrace (System.err);
            throw   e;
        }
	}
	
	
	public void stop ()
	{
        entityManager = null;
        for (Frame frame : Frame.getFrames ())
        {
            logger.info (frame.getClass ().getCanonicalName ());
            frame.dispose ();
        }
        System.exit (0);
    }

    public void setResources (final ResourceBundle resources)
    {
        this.resources = resources;
    }

    public String getImageDirectory ()
    {
        return imageDirectory;
    }

    public void setImageDirectory (final String imageDirectory)
    {
        this.imageDirectory = imageDirectory;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
