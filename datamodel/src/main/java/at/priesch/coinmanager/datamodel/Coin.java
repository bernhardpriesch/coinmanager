/*
 * Players.java
 *
 * Created on 10. Januar 2007, 12:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package at.priesch.coinmanager.datamodel;

import org.hibernate.annotations.CollectionType;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;

//import org.hibernate.annotations.CollectionOfElements;

/**
 * @author Bernhard PRIESCH
 */

@Entity
@Table(name="Coin")
@SequenceGenerator(name="CoinSequence", sequenceName="seq_coin", allocationSize = 1)
@NamedQueries({ @NamedQuery (name = "FindCoinByName", query = "SELECT c FROM Coin c WHERE c.name=:name"),
				@NamedQuery (name = "FindCoinByYear", query = "SELECT c FROM Coin c WHERE c.year=:year"),
				@NamedQuery (name = "GetCoins", query = "SELECT c FROM Coin c"),
				@NamedQuery (name = "GetCoinsByName", query = "SELECT c FROM Coin c WHERE c.name LIKE :pattern ORDER BY c.name"),
				@NamedQuery (name = "GetCountCoins", query = "SELECT COUNT(*) FROM Coin c")
	})
public class Coin
	extends AbstractPersistentObject
{

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String 					name		 	= null;
    private int 					year		 	= -1;
    private Map<Material, Double> 	materials	 	= new HashMap<Material, Double> ();
    private double 					denomination 	= 0;
    private Currency				currency		= null;
    private double					estimatedValue 	= 0;
    private byte[] 					front		 	= null;
    private byte[] 					back		 	= null;
    
    public Coin()
    {

    }

    public Coin(final int id, final String name, final int year, final Map<Material, Double> materials, final double denomination, final Currency currency, final byte[] front, final byte[] back, final double estimatedValue) 
    {
        this.setId(id);
        this.setName(name);
        this.setYear(year);
        this.setMaterials(materials);
        this.setDenomination(denomination);
        this.setCurrency(currency);
        this.setFront(front);
        this.setBack(back);
        this.setEstimatedValue (estimatedValue);
    }

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CoinSequence")
    @Column		(name="Id", nullable = false, unique = true)
	public long getId() {
		return id;
	}

	@Version
    @Column		(name="Version", nullable=false)
	public long getVersion() {
		return version;
	}

	@Column		(name="name", nullable=false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column		(name="year", nullable=true)
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	//@Column		(name="materials", nullable=true)
	//@CollectionOfElements
	@Column		(name="materials", nullable=true)
	@ElementCollection
	public Map<Material, Double> getMaterials() {
		return materials;
	}

	public void setMaterials(Map<Material, Double> materials) {
		this.materials = materials;
	}

	@Column		(name="denomination", nullable=true)
	public double getDenomination() {
		return denomination;
	}

	public void setDenomination(double denomination) {
		this.denomination = denomination;
	}

	@Column		(name="front", nullable=true)
	@Lob
	public byte[] getFront() {
		return front;
	}

	public void setFront(byte[] front) {
		this.front = front;
	}

	@Column		(name="back", nullable=true)
	@Lob
	public byte[] getBack() {
		return back;
	}

	public void setBack(byte[] back) {
		this.back = back;
	}

	@Column		(name="estimatedValue", nullable=true)
	public double getEstimatedValue() {
		return estimatedValue;
	}

	public void setEstimatedValue(double estimatedValue) {
		this.estimatedValue = estimatedValue;
	}

	@ManyToOne(optional=false)
	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
