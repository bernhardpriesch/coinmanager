/*
 * Players.java
 *
 * Created on 10. Januar 2007, 12:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.priesch.coinmanager.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import at.priesch.coinmanager.datamodel.datatypes.CurrencyName;

/**
 * @author Bernhard PRIESCH
 */

@Entity
@Table(name="Currency")
@SequenceGenerator(name="CurrencySequence", sequenceName="seq_currency", allocationSize = 1)
@NamedQueries({ @NamedQuery (name = "FindCurrencyByName", query = "SELECT c FROM Currency c WHERE c.name=:name"),
				@NamedQuery (name = "GetCurrencies", query = "SELECT c FROM Currency c")
	})
public class Currency
	extends AbstractPersistentObject
{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private CurrencyName 			name		 = null;
    private double 					rate		 = -1;
    
    public Currency()
    {

    }

    public Currency(final long id, final CurrencyName name, final double rate) 
    {
        this.setId(id);
        this.setName(name);
        this.setRate(rate);
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CurrencySequence")
    @Column		(name="Id", nullable = false, unique = true)
	public long getId() {
		return id;
	}

	@Version
    @Column		(name="Version", nullable=false)
	public long getVersion() {
		return version;
	}

	@Column		(name="name", nullable=false, unique=true)
    public CurrencyName getName() {
        return name;
    }

    public void setName(CurrencyName name) {
        this.name = name;
    }

    @Column		(name="rate", nullable=false)
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
}
