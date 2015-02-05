package com.datacity.erp.model;

/**
 * Model des ERP
 * 
 * @author Lionel Hamsou
 */
public class Building {

	private String categorie;
	private String codePostal;
	private String description;
	private String email;
	private String latitude;
	private String longitude;
	private String nom;
	private String nomVoie;
	private String numVoie;
	private String quartier;
	private String siteWeb;
	private String telephone;
	private String typeVoie;
	private String ville;

	public Building(String categorie, String description, String email,
			String latitude, String longitude, String nom, String nomVoie,
			String numVoie, String quartier, String siteWeb, String telephone,
			String typeVoie) {
		super();
		this.categorie = categorie;
		this.description = description;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
		this.nom = nom;
		this.nomVoie = nomVoie;
		this.numVoie = numVoie;
		this.quartier = quartier;
		this.siteWeb = siteWeb;
		this.telephone = telephone;
		this.typeVoie = typeVoie;
	}

	public String getCategorie() {
		return categorie;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public String getDescription() {
		return description;
	}

	public String getEmail() {
		return email;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getNom() {
		return nom;
	}

	public String getNomVoie() {
		return nomVoie;
	}

	public String getNumVoie() {
		return numVoie;
	}

	public String getQuartier() {
		return quartier;
	}

	public String getSiteWeb() {
		return siteWeb;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getTypeVoie() {
		return typeVoie;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public void setTypeVoie(String typeVoie) {
		this.typeVoie = typeVoie;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public void setQuartier(String quartier) {
		this.quartier = quartier;
	}

	public void setNumVoie(String numVoie) {
		this.numVoie = numVoie;
	}

	public void setNomVoie(String nomVoie) {
		this.nomVoie = nomVoie;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

}
