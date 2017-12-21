package com.iot.monitoring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class De{
	
	@Id
	@GeneratedValue
	private int id;
	@Column(nullable = false)
	private int number1;
	@Column(nullable = false)
	private int number2;
	@Column(nullable = false)
	private boolean aJouer;
	@Column(nullable = false)
	private boolean resultatRecup;

	

	public De() {

	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getNumber1() {
		return number1;
	}
	public void setNumber1(int number1) {
		this.number1 = number1;
	}
	public int getNumber2() {
		return number2;
	}
	public void setNumber2(int number2) {
		this.number2 = number2;
	}
	public boolean isaJouer() {
		return aJouer;
	}
	public void setaJouer(boolean aJouer) {
		this.aJouer = aJouer;
	}
	
	
	public boolean isResultatRecup() {
		return resultatRecup;
	}
	public void setResultatRecup(boolean resultatRecup) {
		this.resultatRecup = resultatRecup;
	}
	public De(Integer id, int number1, int number2, boolean aJouer, boolean resultatRecup) {
		super();
		this.id = id;
		this.number1 = number1;
		this.number2 = number2;
		this.aJouer = aJouer;
		this.resultatRecup = resultatRecup;
	}
	
	
	
	
	

}
