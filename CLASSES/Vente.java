package CLASSES;

import DATA.IData;
import DATA.fieldType;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Vente implements IData {

    private int id;
    private int produitId;
    private int lotId;
    private int quantite;
    private double prixVente;
    private LocalDateTime dateVente;

    private String values;
    private HashMap<String, fieldType> map;

    public Vente( int produitId, int lotId, int quantite, double prixVente, LocalDateTime dateVente) {
		super();
		this.produitId = produitId;
		this.lotId = lotId;
		this.quantite = quantite;
		this.prixVente = prixVente;
		this.dateVente = dateVente;
	}

	@Override
    public HashMap getStruct() {
        map = new HashMap<>();
        map.put("id", fieldType.INT4);
        map.put("produit_id", fieldType.INT4);
        map.put("lot_id", fieldType.INT4);
        map.put("quantite", fieldType.INT4);
        map.put("prix_vente", fieldType.FLOAT8);
        map.put("date_vente", fieldType.VARCHAR);

        values = String.format("%d, %d, %d, %f, '%s'",
                produitId, lotId, quantite, prixVente, dateVente);
        return map ;
    }

    @Override
    public String getValues() {
        if (values == null) getStruct();
        return values;
    }

    @Override
    public HashMap<String, fieldType> getMap() {
        if (map == null) getStruct();
        return map;
    }

    @Override
    public boolean check(HashMap<String, fieldType> tableStruct) {
        if (map == null) getStruct();
        return tableStruct.equals(map);
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public int getLotId() {
        return lotId;
    }

    public void setLotId(int lotId) {
        this.lotId = lotId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public LocalDateTime getDateVente() {
        return dateVente;
    }

    public void setDateVente(LocalDateTime dateVente) {
        this.dateVente = dateVente;
    }

}
