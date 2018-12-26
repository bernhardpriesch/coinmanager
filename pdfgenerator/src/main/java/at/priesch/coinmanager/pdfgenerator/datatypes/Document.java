package at.priesch.coinmanager.pdfgenerator.datatypes;

import at.priesch.coinmanager.servicecomponents.valueobjects.VOMaterial;

import java.util.ArrayList;
import java.util.List;

public class Document
{
    private byte[]         logo      = null;
    private List<Material> materials = new ArrayList<> ();
    private List<Summary>  summaries = new ArrayList<> ();
    private List<Page>     pages     = new ArrayList<> ();
    
    public Document ()
    {
        //nothing to do!
    }

    public byte[] getLogo ()
    {
        return logo;
    }

    public void setLogo (final byte[] logo)
    {
        this.logo = logo;
    }

    public List<Material> getMaterials ()
    {
        return materials;
    }

    public void setMaterials (final List<Material> materials)
    {
        this.materials = materials;
    }

    public void addMaterial (final Material material)
    {
        this.materials.add (material);
    }

    public List<Summary> getSummaries ()
    {
        return summaries;
    }

    public void setSummaries (final List<Summary> summaries)
    {
        this.summaries = summaries;
    }

    public void addSummary (final Summary summary)
    {
        this.summaries.add (summary);
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
