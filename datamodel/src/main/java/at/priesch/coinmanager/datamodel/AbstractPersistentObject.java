package at.priesch.coinmanager.datamodel;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

/**
 * @author Bernhard PRIESCH
 */
@MappedSuperclass
public abstract class AbstractPersistentObject
    implements  Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected   long    id          = -1L;

    protected   long    version     = -1L;

    protected   int     hashCode    = -1;

    protected AbstractPersistentObject ()
    {
        // Nothing to do!
    }

    public void setId (final long id)
    {
        this.id         = id;
    }

    public void setVersion (final long version)
    {
        this.version    = version;

        calculateHashCode ();
    }

    @Override
    public boolean equals (final Object object)
    {
    	AbstractPersistentObject other   = null;

        if (null == object)
        {
            return  false;
        }

        if (this == object)
        {
            return  true;
        }

        if (!getClass ().equals (object.getClass ()))
        {
            return  false;
        }

        other   = (AbstractPersistentObject)object;

        if (this.id != other.id)
        {
            return  false;
        }

        if (-1 == this.id || -1 == other.id)
        {
            return  false;
        }

        return  true;
    }

    @Override
    public int hashCode ()
    {
        return  hashCode;
    }

    protected void calculateHashCode ()
    {
        hashCode    = (getClass ().getName () + ":" + String.valueOf (this.id)).hashCode ();
    }
}