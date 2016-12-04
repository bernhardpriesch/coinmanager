package at.priesch.coinmanager.pdfgenerator.datatypes;

import java.util.ArrayList;
import java.util.List;

public class Document
{
    private List<Page> pages = new ArrayList<Page> ();
    
    public Document ()
    {
        //nothing to do!
    }

    public List<Page> getPages ()
    {
        return pages;
    }

    public void setPages (final List<Page> pages)
    {
        this.pages = pages;
    }

}
