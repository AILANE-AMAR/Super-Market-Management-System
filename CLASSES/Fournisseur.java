package CLASSES;

import DATA.IData;
import DATA.fieldType;

import java.util.HashMap;

public class Fournisseur implements IData {

    private int id;
    private String nom;
    private String numeroSiret;
    private String adresse;
    private String emailpro;
    private String telephone;
    private boolean actif;

    private String values;
    private HashMap<String, fieldType> map;

    public Fournisseur(int id, String nom, String numeroSiret, String adresse, String emailpro, String telephone,
			boolean actif) {
		super();
		this.id = id;
		this.nom = nom;
		this.numeroSiret = numeroSiret;
		this.adresse = adresse;
		this.emailpro = emailpro;
		this.telephone = telephone;
		this.actif = actif;
	}

	@Override
    public HashMap getStruct() {
        map = new HashMap<>();
        map.put("id", fieldType.INT4);
        map.put("nom", fieldType.VARCHAR);
        map.put("numero_siret", fieldType.VARCHAR);
        map.put("adresse", fieldType.VARCHAR);
        map.put("email", fieldType.VARCHAR);
        map.put("telephone", fieldType.VARCHAR);
        map.put("actif", fieldType.BOOLEAN);

        // Correction pour le champ BOOLEAN
        values = String.format("'%s', '%s', '%s', '%s', '%s', %s",
                nom, numeroSiret, adresse, emailpro, telephone, actif ? "TRUE" : "FALSE");
		return map;
    }

    @Override
    public String getValues() {
        return values;
    }

    @Override
    public HashMap<String, fieldType> getMap() {
        return map;
    }

    @Override
    public boolean check(HashMap<String, fieldType> tableStruct) {
        getStruct();
        return map.equals(tableStruct);
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumeroSiret() {
        return numeroSiret;
    }

    public void setNumeroSiret(String numeroSiret) {
        this.numeroSiret = numeroSiret;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return emailpro;
    }

    public void setEmail(String email) {
        this.emailpro = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }
}
