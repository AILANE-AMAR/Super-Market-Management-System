package CLASSES;

import java.util.HashMap;
import DATA.IData;
import DATA.fieldType;
import java.time.*;
public class Lot implements IData {

    private int idLot;
    private int idProduit;
    private int quantite;
    private double prixAchat;
    private String datePeremption;
    LocalDate dateAchat;
    private int idFournisseur; 
    private HashMap<String, fieldType> map;
    private String values;

    // Constructeur avec idFournisseur ajouté
    public Lot( int idProduit, int idFournisseur, int quantite, double prixAchat, LocalDate dateAchat, String datePeremption) {

        this.idProduit = idProduit;
        this.quantite = quantite;
        this.prixAchat = prixAchat;
        this.dateAchat = dateAchat;
        this.datePeremption = datePeremption;
        this.idFournisseur = idFournisseur; 
    }

    // Getters et Setters
    public int getIdLot() {
        return idLot;
    }

    public void setIdLot(int idLot) {
        this.idLot = idLot;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public String getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(String datePeremption) {
        this.datePeremption = datePeremption;
    }


    public int getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    @Override
    public HashMap getStruct() {
        map = new HashMap<>();
        map.put("idLot", fieldType.INT4);
        map.put("idProduit", fieldType.INT4);
        map.put("quantite", fieldType.INT4);
        map.put("prixAchat", fieldType.FLOAT8);
        map.put("dateAchat", fieldType.VARCHAR);
        map.put("datePeremption", fieldType.VARCHAR);
        map.put("idFournisseur", fieldType.INT4);
 
        values = String.format("%d, %d, %d, %f, '%s', '%s', %d", 
                idLot, idProduit, quantite, prixAchat, dateAchat, datePeremption, idFournisseur);
        return map;
    }

    @Override
    public String getValues() {
        if (values == null) {
            getStruct(); 
        }
        return values;
    }

    @Override
    public HashMap<String, fieldType> getMap() {
        if (map == null) {
            getStruct(); 
        }
        return map;
    }

    @Override
    public boolean check(HashMap<String, fieldType> tableStruct) {
        if (map == null) {
            getStruct();  
        }
        return tableStruct.equals(map);
    }

    @Override
    public String toString() {
        return "Lot{idLot=" + idLot + ", idProduit=" + idProduit + ", quantite=" + quantite + 
               ", prixAchat=" + prixAchat + ", dateAchat='" + dateAchat + "', datePeremption='" + 
               datePeremption + "', idFournisseur=" + idFournisseur + "}";
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }
}
