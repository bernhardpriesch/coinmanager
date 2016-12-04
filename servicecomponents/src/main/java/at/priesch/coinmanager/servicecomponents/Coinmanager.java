package at.priesch.coinmanager.servicecomponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import at.priesch.coinmanager.datamodel.Coin;
import at.priesch.coinmanager.datamodel.Currency;
import at.priesch.coinmanager.datamodel.Material;
import at.priesch.coinmanager.datamodel.datatypes.MaterialName;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOCoin;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOCurrency;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOMaterial;

/**
 * @author Bernhard PRIESCH
 */
public class Coinmanager
{
    @SuppressWarnings ("unchecked")
    public List<VOCoin> getCoins (final EntityManager entityManager)
    {
        List<VOCoin> retValue = null;
        VOCoin voCoin = null;
        VOCurrency voCurrency = null;
        VOMaterial voMaterial = null;
        Query query = null;
        EntityTransaction transaction = null;

        transaction = entityManager.getTransaction ();
        transaction.begin ();

        query = entityManager.createNamedQuery ("GetCoins");

        retValue = new ArrayList<VOCoin> ();

        for (Coin coin : (List<Coin>)query.getResultList ())
        {
            voCoin = new VOCoin ();
            voCoin.id = coin.getId ();
            voCoin.name = coin.getName ();
            voCoin.denomination = coin.getDenomination ();
            voCoin.year = coin.getYear ();
            voCoin.estimatedValue = coin.getEstimatedValue ();
            voCoin.front = coin.getFront ();
            voCoin.back = coin.getBack ();

            voCurrency = new VOCurrency ();
            voCurrency.id = coin.getCurrency ().getId ();
            voCurrency.name = coin.getCurrency ().getName ();
            voCurrency.rate = coin.getCurrency ().getRate ();

            voCoin.currency = voCurrency;

            for (Entry<Material, Double> entry : coin.getMaterials ().entrySet ())
            {
                voMaterial = new VOMaterial ();
                voMaterial.id = entry.getKey ().getId ();
                voMaterial.name = entry.getKey ().getName ();
                voMaterial.rate = entry.getKey ().getRate ();

                voCoin.materials.put (voMaterial, entry.getValue ());
            }

            retValue.add (voCoin);
        }
        try
        {
            transaction.commit ();
        }
        catch (Exception e)
        {
            if (null != transaction && transaction.isActive ())
            {
                transaction.rollback ();
            }
        }

        return retValue;
    }

    public VOCoin addCoin (final VOCoin voCoin, final EntityManager entityManager)
    {
        Coin coin = null;
        Currency currency = null;
        Map<Material, Double> materials = null;
        EntityTransaction transaction = null;

        transaction = entityManager.getTransaction ();
        transaction.begin ();

        currency = entityManager.find (Currency.class, voCoin.currency.id);

        materials = new HashMap<Material, Double> ();

        for (Entry<VOMaterial, Double> entry : voCoin.materials.entrySet ())
        {
            materials.put (entityManager.find (Material.class, entry.getKey ().id), entry.getValue ());
        }

        coin = new Coin (-1, voCoin.name, voCoin.year, materials, voCoin.denomination, currency, voCoin.front, voCoin.back, voCoin.estimatedValue);

        entityManager.persist (coin);
        entityManager.flush ();

        voCoin.id = coin.getId ();

        try
        {
            transaction.commit ();
        }
        catch (Exception e)
        {
            if (null != transaction && transaction.isActive ())
            {
                transaction.rollback ();
            }
        }

        return voCoin;
    }

    public VOCoin updateCoin (final VOCoin voCoin, final EntityManager entityManager)
    {
        Coin coin = null;
        Currency currency = null;
        Map<Material, Double> materials = null;
        EntityTransaction transaction = null;

        transaction = entityManager.getTransaction ();
        transaction.begin ();

        currency = entityManager.find (Currency.class, voCoin.currency.id);

        materials = new HashMap<Material, Double> ();

        for (Entry<VOMaterial, Double> entry : voCoin.materials.entrySet ())
        {
            materials.put (entityManager.find (Material.class, entry.getKey ().id), entry.getValue ());
        }

        coin = entityManager.find (Coin.class, voCoin.id);
        coin.setName (voCoin.name);
        coin.setYear (voCoin.year);
        coin.setDenomination (voCoin.denomination);
        coin.setCurrency (currency);
        coin.setMaterials (materials);
        coin.setFront (voCoin.front);
        coin.setBack (voCoin.back);
        coin.setEstimatedValue (voCoin.estimatedValue);

        entityManager.flush ();

        voCoin.id = coin.getId ();

        try
        {
            transaction.commit ();
        }
        catch (Exception e)
        {
            if (null != transaction && transaction.isActive ())
            {
                transaction.rollback ();
            }
        }

        return voCoin;
    }

    public void deleteCoin (final VOCoin voCoin, final EntityManager entityManager)
    {
        Coin coin = null;
        EntityTransaction transaction = null;

        transaction = entityManager.getTransaction ();
        transaction.begin ();

        coin = entityManager.find (Coin.class, voCoin.id);

        entityManager.remove (coin);
        entityManager.flush ();

        try
        {
            transaction.commit ();
        }
        catch (Exception e)
        {
            if (null != transaction && transaction.isActive ())
            {
                transaction.rollback ();
            }
        }
    }

    @SuppressWarnings ("unchecked")
    public long countCoins (final EntityManager entityManager, final MaterialName material)
    {
        long retValue = 0;
        Query query = null;
        boolean counted = false;
        List<Coin> coins = null;

        if (material == null)
        {
            query = entityManager.createNamedQuery ("GetCountCoins");
            retValue = (Long)query.getSingleResult ();
        }
        else
        {
            query = entityManager.createNamedQuery ("GetCoins");
            coins = (List<Coin>)query.getResultList ();

            for (Coin coin : coins)
            {
                for (Entry<Material, Double> mEntry : coin.getMaterials ().entrySet ())
                {
                    if ((material == null || mEntry.getKey ().getName () == material) && !counted)
                    {
                        counted = true;
                        retValue++;
                    }
                }
                counted = false;
            }
        }

        return retValue;
    }

    @SuppressWarnings ("unchecked")
    public double calculateDenominatioin (final EntityManager entityManager, final MaterialName material)
    {
        double retValue = 0;
        List<Coin> coins = null;
        Query query = null;
        boolean counted = false;

        query = entityManager.createNamedQuery ("GetCoins");
        coins = (List<Coin>)query.getResultList ();

        for (Coin coin : coins)
        {
            for (Entry<Material, Double> mEntry : coin.getMaterials ().entrySet ())
            {
                if ((material == null || mEntry.getKey ().getName () == material) && !counted)
                {
                    counted = true;
                    retValue += coin.getDenomination () / coin.getCurrency ().getRate ();
                }
            }
            counted = false;
        }

        return retValue;
    }

    @SuppressWarnings ("unchecked")
    public double calculateMaterial (final EntityManager entityManager, final MaterialName material)
    {
        double retValue = 0;
        List<Coin> coins = null;
        Query query = null;
        boolean materialFound = false;
        boolean counted = false;

        query = entityManager.createNamedQuery ("GetCoins");
        coins = (List<Coin>)query.getResultList ();

        for (Coin coin : coins)
        {
            for (Entry<Material, Double> mEntry : coin.getMaterials ().entrySet ())
            {
                if ((material == null || mEntry.getKey ().getName () == material) && !counted)
                {
                    counted = true;
                    for (Entry<Material, Double> entry : coin.getMaterials ().entrySet ())
                    {
                        if (entry.getValue () != -1)
                        {
                            retValue += ((entry.getKey ().getRate () / 31.1034768) * entry.getValue ());
                            materialFound = true;
                        }
                    }

                    if (!materialFound)
                    {
                        retValue += coin.getDenomination () / coin.getCurrency ().getRate ();

                    }
                    materialFound = false;
                }
            }
            counted = false;
        }

        return retValue;
    }

    @SuppressWarnings ("unchecked")
    public double calculateEstimatedValue (final EntityManager entityManager, final MaterialName material)
    {
        double retValue = 0;
        List<Coin> coins = null;
        Query query = null;
        boolean materialFound = false;
        boolean counted = false;

        query = entityManager.createNamedQuery ("GetCoins");
        coins = (List<Coin>)query.getResultList ();

        for (Coin coin : coins)
        {
            for (Entry<Material, Double> mEntry : coin.getMaterials ().entrySet ())
            {
                if ((material == null || mEntry.getKey ().getName () == material) && !counted)
                {
                    counted = true;
                    if (coin.getEstimatedValue () != -1)
                    {
                        retValue += coin.getEstimatedValue ();
                    }
                    else
                    {
                        for (Entry<Material, Double> entry : coin.getMaterials ().entrySet ())
                        {
                            if (entry.getValue () != -1)
                            {
                                retValue += ((entry.getKey ().getRate () / 31.1034768) * entry.getValue ());
                                materialFound = true;
                            }
                        }

                        if (!materialFound)
                        {
                            retValue += coin.getDenomination () / coin.getCurrency ().getRate ();
                        }
                    }
                }
            }
            counted = false;
        }

        return retValue;
    }

    @SuppressWarnings ("unchecked")
    public List<VOCoin> getCoinsByMaterial (EntityManager entityManager, String material, String pattern)
    {
        List<VOCoin> retValue = null;
        List<Coin> result = null;
        VOCoin voCoin = null;
        VOCurrency voCurrency = null;
        VOMaterial voMaterial = null;
        Query query = null;
        EntityTransaction transaction = null;
        boolean add = false;

        transaction = entityManager.getTransaction ();
        transaction.begin ();

        query = entityManager.createNamedQuery ("GetCoinsByName");
        query.setParameter ("pattern", "%" + pattern + "%");

        retValue = new ArrayList<VOCoin> ();
        
        result = (List<Coin>)query.getResultList ();
        
        for (Coin coin : result)
        {
            for(Material entry : coin.getMaterials ().keySet ())
            {
                if(entry.getName ().getName ().equals (material))
                {
                    add = true;
                    break;
                }
            }
            if(add)
            {
                voCoin = new VOCoin ();
                voCoin.id = coin.getId ();
                voCoin.name = coin.getName ();
                voCoin.denomination = coin.getDenomination ();
                voCoin.year = coin.getYear ();
                voCoin.estimatedValue = coin.getEstimatedValue ();
                voCoin.front = coin.getFront ();
                voCoin.back = coin.getBack ();

                voCurrency = new VOCurrency ();
                voCurrency.id = coin.getCurrency ().getId ();
                voCurrency.name = coin.getCurrency ().getName ();
                voCurrency.rate = coin.getCurrency ().getRate ();

                voCoin.currency = voCurrency;

                for (Entry<Material, Double> entry : coin.getMaterials ().entrySet ())
                {
                    voMaterial = new VOMaterial ();
                    voMaterial.id = entry.getKey ().getId ();
                    voMaterial.name = entry.getKey ().getName ();
                    voMaterial.rate = entry.getKey ().getRate ();

                    voCoin.materials.put (voMaterial, entry.getValue ());
                }

                retValue.add (voCoin);
            }            
            add = false;
        }
        try
        {
            transaction.commit ();
        }
        catch (Exception e)
        {
            if (null != transaction && transaction.isActive ())
            {
                transaction.rollback ();
            }
        }

        return retValue;
    }
    
    @SuppressWarnings ("unchecked")
    public List<VOCoin> getCoinsByName (final EntityManager entityManager, final String pattern)
    {
        List<VOCoin> retValue = null;
        VOCoin voCoin = null;
        VOCurrency voCurrency = null;
        VOMaterial voMaterial = null;
        Query query = null;
        EntityTransaction transaction = null;

        transaction = entityManager.getTransaction ();
        transaction.begin ();

        query = entityManager.createNamedQuery ("GetCoinsByName");
        query.setParameter ("pattern", "%" + pattern + "%");

        retValue = new ArrayList<VOCoin> ();

        for (Coin coin : (List<Coin>)query.getResultList ())
        {
            voCoin = new VOCoin ();
            voCoin.id = coin.getId ();
            voCoin.name = coin.getName ();
            voCoin.denomination = coin.getDenomination ();
            voCoin.year = coin.getYear ();
            voCoin.estimatedValue = coin.getEstimatedValue ();
            voCoin.front = coin.getFront ();
            voCoin.back = coin.getBack ();

            voCurrency = new VOCurrency ();
            voCurrency.id = coin.getCurrency ().getId ();
            voCurrency.name = coin.getCurrency ().getName ();
            voCurrency.rate = coin.getCurrency ().getRate ();

            voCoin.currency = voCurrency;

            for (Entry<Material, Double> entry : coin.getMaterials ().entrySet ())
            {
                voMaterial = new VOMaterial ();
                voMaterial.id = entry.getKey ().getId ();
                voMaterial.name = entry.getKey ().getName ();
                voMaterial.rate = entry.getKey ().getRate ();

                voCoin.materials.put (voMaterial, entry.getValue ());
            }

            retValue.add (voCoin);
        }
        try
        {
            transaction.commit ();
        }
        catch (Exception e)
        {
            if (null != transaction && transaction.isActive ())
            {
                transaction.rollback ();
            }
        }

        return retValue;
    }
}
