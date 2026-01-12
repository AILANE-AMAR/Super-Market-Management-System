package CLASSES;

import DATA.IData;
import DATA.fieldType;

import java.util.HashMap;

public class Contrat implements IData {

    private int id;  
    private int fournisseurId;   
    private int produitId;  
    private double prixFixe;
    private int quantiteMinimale; 
    private String dateDebut;    
    private String dateFin;    
    private boolean estActif;
    private String values;        
    private HashMap<String, fieldType> map;  

    // Constructeur
    public Contrat(int id, int fournisseurId, int produitId, double prixFixe, boolean estActif, 
                   int quantiteMinimale, String dateDebut, String dateFin) {
        this.id = id;
        this.fournisseurId = fournisseurId;
        this.produitId = produitId;
        this.prixFixe = prixFixe;
        this.quantiteMinimale = quantiteMinimale;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.estActif = estActif;

        getStruct(); 
    }

	// Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(int fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public double getPrixFixe() {
        return prixFixe;
    }

    public void setPrixFixe(double prixFixe) {
        this.prixFixe = prixFixe;
    }

    public int getQuantiteMinimale() {
        return quantiteMinimale;
    }

    public void setQuantiteMinimale(int quantiteMinimale) {
        this.quantiteMinimale = quantiteMinimale;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isEstActif() {
        return estActif;
    }

    public void setEstActif(boolean estActif) {
        this.estActif = estActif;
    }

    // Méthode toString pour l'affichage
    @Override
    public String toString() {
        return "Contrat{" +
                "id=" + id +
                ", fournisseurId=" + fournisseurId +
                ", produitId=" + produitId +
                ", prixFixe=" + prixFixe +
                ", quantiteMinimale=" + quantiteMinimale +
                ", dateDebut='" + dateDebut + '\'' +
                ", dateFin='" + dateFin + '\'' +
                ", estActif=" + estActif +
                '}';
    }

    // Méthode pour obtenir la structure de la table
    @Override
    public HashMap getStruct() {
        map = new HashMap<>();
        map.put("id", fieldType.INT4);               // Identifiant du contrat
        map.put("fournisseurId", fieldType.INT4);    // Identifiant du fournisseur
        map.put("produitId", fieldType.INT4);        // Identifiant du produit
        map.put("prixFixe", fieldType.FLOAT8);       // Prix fixé
        map.put("quantiteMinimale", fieldType.INT4); // Quantité minimale
        map.put("dateDebut", fieldType.VARCHAR);     // Date de début
        map.put("dateFin", fieldType.VARCHAR);       // Date de fin
        map.put("estActif", fieldType.BOOLEAN);      // Statut actif ou non

        // Composition de la chaîne "values" pour la requête SQL
        values = String.format("%d, %d, %d, %f, %d, '%s', '%s', %b", 
                               id, fournisseurId, produitId, prixFixe, quantiteMinimale, 
                               dateDebut, dateFin, estActif);
        return map;
    }

    public String getValues() {
        if (values == null) getStruct(); 
        return values;
    }

    // Retourne la structure de la table sous forme de Map
    @Override
    public HashMap<String, fieldType> getMap() {
        return map;
    }

    // Vérifie si la structure de la table correspond à celle de l'objet Contrat
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
