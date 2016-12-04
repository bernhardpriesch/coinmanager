package at.priesch.coinmanager.servicecomponents;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import at.priesch.coinmanager.datamodel.Material;
import at.priesch.coinmanager.datamodel.datatypes.MaterialName;
import at.priesch.coinmanager.servicecomponents.valueobjects.VOMaterial;

/**
 * @author Bernhard PRIESCH
 */
public class Materialmanager
{
    public Materialmanager ()
    {
        //nothing to do!
    }

    @SuppressWarnings ("unchecked")
    public List<VOMaterial> getMaterials (final EntityManager entityManager)
    {
        List<VOMaterial>  retValue    = null;
        VOMaterial        voMaterial  = null;
        Query             query       = null;
        EntityTransaction transaction = null;

        initMaterials (entityManager);

        transaction = entityManager.getTransaction ();
        transaction.begin ();

        query = entityManager.createNamedQuery ("GetMaterials");

        retValue = new ArrayList<VOMaterial> ();

        for (Material material : (List<Material>)query.getResultList ())
        {
            voMaterial = new VOMaterial ();
            voMaterial.id = material.getId ();
            voMaterial.name = material.getName ();
            voMaterial.rate = material.getRate ();

            retValue.add (voMaterial);
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

    public void updateMaterials (final List<VOMaterial> materials, final EntityManager entityManager)
    {
        Material          material    = null;
        EntityTransaction transaction = null;

        transaction = entityManager.getTransaction ();
        transaction.begin ();

        for (VOMaterial voMaterial : materials)
        {
            material = entityManager.find (Material.class, voMaterial.id);
            material.setRate (voMaterial.rate);

            entityManager.flush ();
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
    }

    public void initMaterials (final EntityManager entityManager)
    {
        Query             query       = null;
        EntityTransaction transaction = null;
        Material          material    = null;

        try
        {
            transaction = entityManager.getTransaction ();
            transaction.begin ();

            query = entityManager.createNamedQuery ("GetMaterials");

            if (query.getResultList ().isEmpty ())
            {
                //Gold
                material = new Material ();
                material.setName (MaterialName.AU);
                material.setRate (1050.0);
                entityManager.persist (material);

                //Silver
                material = new Material ();
                material.setName (MaterialName.AG);
                material.setRate (16.0);
                entityManager.persist (material);

                //Niob
                material = new Material ();
                material.setName (MaterialName.NB);
                material.setRate (1.7);
                entityManager.persist (material);

                //Titan
                material = new Material ();
                material.setName (MaterialName.TI);
                material.setRate (10.0);
                entityManager.persist (material);

                //Platin
                material = new Material ();
                material.setName (MaterialName.PT);
                material.setRate (933.0);
                entityManager.persist (material);
            }

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
}
