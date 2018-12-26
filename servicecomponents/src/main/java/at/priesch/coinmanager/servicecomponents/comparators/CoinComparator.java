package at.priesch.coinmanager.servicecomponents.comparators;

import at.priesch.coinmanager.servicecomponents.valueobjects.VOCoin;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOMaterial;

import java.util.Comparator;
import java.util.Iterator;

public class CoinComparator
    implements Comparator<VOCoin>
{

    @Override
    public int compare (final VOCoin o1, final VOCoin o2)
    {
        int                  i         = 0;
        Iterator<VOMaterial> materials = null;
        VOMaterial           material  = null;

        if (o1.year > o2.year)
        {
            return 1;
        }
        else if (o1.year < o2.year)
        {
            return -1;
        }

        if (!o1.materials.isEmpty () && o2.materials.isEmpty ())
        {
            return 1;
        }
        else if (o1.materials.isEmpty () && !o2.materials.isEmpty ())
        {
            return -1;
        }
        else if (!o1.materials.isEmpty () && !o2.materials.isEmpty ())
        {
            i = 0;
            materials = o1.materials.keySet ().iterator ();
            while (materials.hasNext ())
            {
                material = materials.next ();
                if (o2.materials.size () < i)
                {
                    return 1;
                }
                else if (material.name.getOrder () > ((VOMaterial)o2.materials.keySet ().toArray ()[i]).name.getOrder ())
                {
                    return 1;
                }
                else if (material.name.getOrder () < ((VOMaterial)o2.materials.keySet ().toArray ()[i]).name.getOrder ())
                {
                    return -1;
                }
            }
        }
        else if (o1.materials.size () < o2.materials.size ())
        {
            return -1;
        }

        if (o1.denomination > o2.denomination)
        {
            return -1;
        }
        else if (o1.denomination < o2.denomination)
        {
            return 1;
        }

        return o1.name.compareTo (o2.name);
    }
}
