package at.priesch.coinmanager.pdfgenerator;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.*;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import at.priesch.coinmanager.datamodel.datatypes.MaterialName;
import at.priesch.coinmanager.pdfgenerator.datatypes.Material;
import at.priesch.coinmanager.pdfgenerator.datatypes.Summary;
import at.priesch.coinmanager.servicecomponents.comparators.CoinComparator;
import at.priesch.coinmanager.servicecomponents.i18n.Translator;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOCurrency;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;

import at.priesch.coinmanager.pdfgenerator.datatypes.Document;
import at.priesch.coinmanager.pdfgenerator.datatypes.Page;
import at.priesch.coinmanager.servicecomponents.Coinmanager;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOCoin;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOMaterial;

import com.thoughtworks.xstream.XStream;

public class PDFGenerator
{
    private Logger  logger   = Logger.getLogger (getClass ());
    private XStream xstream  = null;
    private File    xsltfile = null;
    private String  baseDir  = "configuration/xslt";
    private ResourceBundle resourceBundle = null;

    public PDFGenerator ()
    {
        xstream = new XStream ();
        xstream.setMode (XStream.NO_REFERENCES);
        xstream.alias ("document", Document.class);
        xstream.alias ("internalMaterial", Material.class);
        xstream.alias ("summary", Summary.class);
        xstream.alias ("page", Page.class);
        xstream.alias ("coin", VOCoin.class);
        xstream.alias ("material", VOMaterial.class);
        xstream.alias ("currency", VOCurrency.class);
        xstream.addImplicitCollection (Document.class, "materials");
        xstream.addImplicitCollection (Document.class, "summaries");
        xstream.addImplicitCollection (Document.class, "pages");
        xstream.addImplicitCollection (Page.class, "coins");

        xsltfile = new File (baseDir, "coinmanager.xsl");
    }

    public void createPDF (final Coinmanager coinmanager, final ResourceBundle resourceBundle, final EntityManager entityManager, final int start, final int end)
    {
        Document document = null;

        this.resourceBundle = resourceBundle;

        document = getDocument (coinmanager, entityManager, start, end);

        try
        {
            generatePDF (xstream.toXML (document));
        }
        catch (Exception e)
        {
            logger.error ("Error generating PDF!", e);
        }
    }

    private Document getDocument (final Coinmanager coinmanager, final EntityManager entityManager, final int start, final int end)
    {
        List<VOCoin>      coins       = null;
        List<Page>        pages       = null;
        Iterator<VOCoin>  iterator    = null;
        Page              page        = null;
        Document          retValue    = null;
        Summary           summary     = null;
        Material          material    = null;
        GregorianCalendar calendar    = new GregorianCalendar ();

        retValue = new Document ();
        pages = new ArrayList<> ();

        for (VOMaterial voMaterial : coinmanager.getMaterials (entityManager, resourceBundle))
        {
            material = new Material ();
            material.setMaterialName (voMaterial.toString ());
            material.setAbbreviation (voMaterial.name.name ());
            material.setRate (voMaterial.rate);
            retValue.addMaterial (material);
        }

        summary = new Summary ();

        try
        {
            retValue.setLogo (Files.readAllBytes(new File ("images/CoinmanagerLogo.png").toPath ()));
        }
        catch (IOException e)
        {
            logger.info ("Couldn't load logo...");
        }
        summary.setMaterialName ("Gesamt");
        summary.setNumberCoins (coinmanager.countCoins (entityManager, null));
        summary.setDenomination (coinmanager.calculateDenominatioin (entityManager, null));
        summary.setValueOfMaterials (coinmanager.calculateMaterial (entityManager, null));
        summary.setEstimatedValue (coinmanager.calculateEstimatedValue (entityManager, null));
        retValue.addSummary (summary);

        for (MaterialName materialName : MaterialName.values ())
        {
            if (coinmanager.countCoins (entityManager, Arrays.asList (materialName)) != 0)
            {
                summary = new Summary ();
                summary.setMaterialName (VOMaterial.getI18nName (materialName.getName (), resourceBundle));
                summary.setNumberCoins (coinmanager.countCoins (entityManager, Arrays.asList (materialName)));
                summary.setDenomination (coinmanager.calculateDenominatioin (entityManager, Arrays.asList (materialName)));
                summary.setValueOfMaterials (coinmanager.calculateMaterial (entityManager, Arrays.asList (materialName)));
                summary.setEstimatedValue (coinmanager.calculateEstimatedValue (entityManager, Arrays.asList (materialName)));
                retValue.addSummary (summary);
            }
        }

        List<String> materialNameList = new ArrayList<> ();

        for (MaterialName materialName1 : MaterialName.values ())
        {
            for (MaterialName materialName2 : MaterialName.values ())
            {
                if (!materialNameList.contains (materialName2.getName () + materialName1.getName ()))
                {
                    if (coinmanager.countCoins (entityManager, Arrays.asList (materialName1, materialName2)) != 0)
                    {
                        summary = new Summary ();
                        summary.setMaterialName (VOMaterial.getI18nName (materialName1.getName (), resourceBundle) + " / " + VOMaterial.getI18nName (materialName2.getName (), resourceBundle));
                        summary.setNumberCoins (coinmanager.countCoins (entityManager, Arrays.asList (materialName1, materialName2)));
                        summary.setDenomination (coinmanager.calculateDenominatioin (entityManager, Arrays.asList (materialName1, materialName2)));
                        summary.setValueOfMaterials (coinmanager.calculateMaterial (entityManager, Arrays.asList (materialName1, materialName2)));
                        summary.setEstimatedValue (coinmanager.calculateEstimatedValue (entityManager, Arrays.asList (materialName1, materialName2)));
                        retValue.addSummary (summary);
                    }
                    materialNameList.add (materialName1.getName () + materialName2.getName ());
                }
            }
        }

        calendar.setTime (new Date (System.currentTimeMillis ()));

        logger.info ("Start: " + start);
        logger.info ("End: " + end);

        for (int i = start; i <= end; i++)
        {
            coins = coinmanager.getCoinsForYear (entityManager, i);
            Collections.sort (coins, new CoinComparator ());
            if (coins.size () > 0)
            {
                page = new Page ();
                if (i==0)
                {
                    page.setYear (resourceBundle.getString ("Unknown"));
                }
                else
                {
                    page.setYear (String.valueOf (i));
                }
                pages.add (page);
                iterator = coins.iterator ();
                while (iterator.hasNext ())
                {
                    page.addCoin (iterator.next ());
                }
            }
            logger.debug ("Year: " + i);
        }

        retValue.setPages (pages);

        return retValue;
    }

    private void generatePDF (final String xml)
        throws
        Exception
    {
        FopFactory         fopFactory  = null;
        FOUserAgent        foUserAgent = null;
        TransformerFactory factory     = null;
        Source             src         = null;
        Result             res         = null;
        OutputStream       out         = null;
        Fop                fop         = null;
        Transformer        transformer = null;
        File               file        = null;
        Calendar           calendar    = null;

        calendar = new GregorianCalendar ();
        calendar.setTime (new Date (System.currentTimeMillis ()));

        file = new File ("CoinCollection.pdf");
        file.createNewFile ();

        fopFactory = FopFactory.newInstance (new File ("configuration/fop.xconf"));
        foUserAgent = fopFactory.newFOUserAgent ();

        out = new BufferedOutputStream (new FileOutputStream (file));
        fop = fopFactory.newFop (MimeConstants.MIME_PDF, foUserAgent, out);

        factory = TransformerFactory.newInstance ();
        transformer = factory.newTransformer (new StreamSource (xsltfile));
        transformer.setParameter("translator", new Translator (resourceBundle));
        transformer.setParameter ("currentDate", String.valueOf (calendar.get (Calendar.DAY_OF_MONTH)) + "." + String.valueOf (calendar.get (Calendar.MONTH)+1) + "." + String.valueOf (calendar.get (Calendar.YEAR)));
        src = new StreamSource (new StringReader (xml));
        res = new SAXResult (fop.getDefaultHandler ());
        transformer.transform (src, res);
        out.close ();
    }

}
