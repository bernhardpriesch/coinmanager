package at.priesch.coinmanager.pdfgenerator;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.log4j.Logger;

import at.priesch.coinmanager.datamodel.datatypes.MaterialName;
import at.priesch.coinmanager.pdfgenerator.datatypes.Document;
import at.priesch.coinmanager.pdfgenerator.datatypes.Page;
import at.priesch.coinmanager.servicecomponents.Coinmanager;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOCoin;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOMaterial;

import com.thoughtworks.xstream.XStream;

public class PDFGenerator
{
    private Logger          logger      = Logger.getLogger (getClass());
    private XStream         xstream     = null;
    private File            xsltfile    = null;
    private String          baseDir     = "configuration/xslt";
    
    public PDFGenerator ()
    {
        xstream = new XStream ();
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("document", Document.class);
        xstream.alias("page", Page.class);
        xstream.alias("coin", VOCoin.class);
        xstream.alias("material", VOMaterial.class);
        xstream.addImplicitCollection (Document.class, "pages");
        xstream.addImplicitCollection (Page.class, "coins");
//        xstream.omitField (VOCoin.class, "front");
//        xstream.omitField (VOCoin.class, "back");
        
        xsltfile = new File(baseDir, "coinmanager.xsl");
    }
    
    public void createPDF (final Coinmanager coinmanager, final EntityManager entityManager)
    {
        Document document = null;
        
        document = getDocument (coinmanager, entityManager);
        
        try
        {
            generatePDF (xstream.toXML (document));
        }
        catch (Exception e)
        {
            logger.error ("Error generating PDF!", e);
        }
    }
    
    private Document getDocument (final Coinmanager coinmanager, final EntityManager entityManager)
    {
        List<VOCoin>        coins       = null;
        List<Page>          pages       = null;
        Iterator<VOCoin>    iterator    = null;
        Page                page        = null;
        int                 i           = 0;
        Document            retValue    = null;
        
        pages = new ArrayList<Page> ();
                
//        coins = coinmanager.getCoinsByMaterial (entityManager, MaterialName.AG.getName (), "");
//        
//        iterator = coins.iterator ();
//        while (iterator.hasNext ())
//        {
//            if (i % 5 == 0)
//            {
//                page = new Page ();
//                pages.add (page);
//            }
//            page.addCoin (iterator.next ());
//            i ++;
//        }
        
        coins = coinmanager.getCoinsByMaterial (entityManager, MaterialName.AU.getName (), "");
        
        iterator = coins.iterator ();
        while (iterator.hasNext ())
        {
            if (i % 5 == 0)
            {
                page = new Page ();
                pages.add (page);
            }
            page.addCoin (iterator.next ());
            i ++;
        }
        
        coins = coinmanager.getCoinsByMaterial (entityManager, MaterialName.PT.getName (), "");
        
        iterator = coins.iterator ();
        while (iterator.hasNext ())
        {
            if (i % 5 == 0)
            {
                page = new Page ();
                pages.add (page);
            }
            page.addCoin (iterator.next ());
            i ++;
        }
        
        retValue = new Document ();
        retValue.setPages (pages);
        
        return retValue;
    }
    
    private void generatePDF (final String xml) 
        throws Exception
    {
        FopFactory fopFactory = null;
        FOUserAgent foUserAgent = null;
        TransformerFactory factory = null;
        Source src = null;
        Result res = null;
        OutputStream out = null;
        Fop fop = null;
        Transformer transformer = null;
        
        fopFactory = FopFactory.newInstance(new File ("CoinCollection.pdf"));
        foUserAgent = fopFactory.newFOUserAgent();        
        
        out = new BufferedOutputStream (new FileOutputStream(new File ("CoinCollection.pdf")));
        fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out); 
        
        factory = TransformerFactory.newInstance();
        transformer = factory.newTransformer(new StreamSource(xsltfile)); 
        transformer.setParameter("versionParam", "2.0"); 
        src = new StreamSource(new StringReader (xml)); 
        res = new SAXResult(fop.getDefaultHandler()); 
        transformer.transform(src, res);    
        out.close ();
    }
    
}
