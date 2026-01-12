package CLASSES;

import DATA.IData;
import DATA.fieldType;

import java.util.HashMap;

public class Produit implements IData {

    private int id;                       
    private String nom;           
    private String description;   
    private String categorie;     
    private double prixVente;   
    private String values;        
    private HashMap<String, fieldType> map;

    // Constructeur
    public Produit(int id, String nom, String description, String categorie, double prixVente) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.prixVente = prixVente;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }


    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", categorie='" + categorie + '\'' +
                ", prixVente=" + prixVente +
                '}';
    }


    @Override
    public HashMap getStruct() {
        map = new HashMap<>();
        map.put("id", fieldType.INT4);
        map.put("nom", fieldType.VARCHAR);
        map.put("description", fieldType.VARCHAR);
        map.put("categorie", fieldType.VARCHAR);
        map.put("prixVente", fieldType.FLOAT8);

        values = String.format("'%d', '%d', '%s', '%s', '%f'", id, nom, description, categorie, prixVente);
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
        if (map.size() != tableStruct.size()) {
            return false;
        }

        for (String key : map.keySet()) {
            if (!tableStruct.containsKey(key) || tableStruct.get(key) != map.get(key)) {
                return false;
            }
        }
        return true;
    }
}
