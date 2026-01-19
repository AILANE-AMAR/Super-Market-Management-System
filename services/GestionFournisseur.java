package services;

import db.Connexion;
import CLASSES.Fournisseur;
import DATA.fieldType;

import java.sql.*;
import java.util.HashMap;
import java.util.*;
public class GestionFournisseur {
  

	    public static void creerTableFournisseurs() {
	        String sqlCreateTable = """
	            CREATE TABLE IF NOT EXISTS Fournisseurs (
	                id SERIAL PRIMARY KEY,
	                nom VARCHAR(255) NOT NULL,
	                numero_siret VARCHAR(14) NOT NULL,
	                adresse TEXT NOT NULL,
	                emailpro VARCHAR(255) NOT NULL,
	                telephone VARCHAR(15) NOT NULL,
	                actif BOOLEAN DEFAULT TRUE
	            )
	        """;

	        try (Connection connection = Connexion.connect(); 
	             Statement stmt = connection.createStatement()) {
	            stmt.executeUpdate(sqlCreateTable);
	            System.out.println("Table 'fournisseurs' créée avec succès !");
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de la création de la table 'fournisseurs' : " + e.getMessage());
	        }
	    }
	    public void ajouterFournisseur(Fournisseur fournisseur) {
	        String sql = "INSERT INTO fournisseurs (nom, numero_siret, adresse, emailpro, telephone, actif) " +
	                     "VALUES (?, ?, ?, ?, ?, ?)";

	        try (Connection connection = Connexion.connect();
	             PreparedStatement stmt = connection.prepareStatement(sql)) {

	            stmt.setString(1, fournisseur.getNom());
	            stmt.setString(2, fournisseur.getNumeroSiret());
	            stmt.setString(3, fournisseur.getAdresse());
	            stmt.setString(4, fournisseur.getEmail());
	            stmt.setString(5, fournisseur.getTelephone());
	            stmt.setBoolean(6, fournisseur.isActif());

	            stmt.executeUpdate();
	            System.out.println("Fournisseur ajouté avec succès !");
	        } catch (SQLException e) {
	            System.err.println("Erreur lors de l'ajout du fournisseur : " + e.getMessage());
	        }
	    }


    
//    // Afficher la structure d'une table
//    public HashMap<String, fieldType> structTable(String table, boolean display) throws SQLException {
//        HashMap<String, fieldType> structure = new HashMap<>();
//
//        try (Connection conn = Connexion.connect();
//             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + table + " LIMIT 0");
//             ResultSet rs = stmt.executeQuery()) {
//
//            ResultSetMetaData metaData = rs.getMetaData();
//
//            for (int i = 1; i <= metaData.getColumnCount(); i++) {
//                String columnName = metaData.getColumnName(i);
//                int columnType = metaData.getColumnType(i);
//                fieldType type = fieldType.VARCHAR; // Valeur par défaut
//
//                switch (columnType) {
//                    case Types.INTEGER:
//                        type = fieldType.INT4;
//                        break;
//                    case Types.VARCHAR:
//                        type = fieldType.VARCHAR;
//                        break;
//                    case Types.DOUBLE:
//                        type = fieldType.FLOAT8;
//                        break;
//                    case Types.NUMERIC:
//                        type = fieldType.NUMERIC;
//                        break;
//                    case Types.BOOLEAN:
//                        type = fieldType.BOOLEAN;
//                        break;
//                }
//
//                structure.put(columnName, type);
//            }
//        }
//
//        if (display) {
//            structure.forEach((key, value) -> System.out.println(key + ": " + value));
//        }
//
//        return structure;
//    }
    public List<Fournisseur> getAllFournisseur() throws SQLException {
        List<Fournisseur> fournisseurs = new ArrayList<>();
        String table = "fournisseurs";

        try (Connection conn = Connexion.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + table);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String siret = rs.getString("numero_siret");
                String adresse = rs.getString("adresse");
                String email = rs.getString("emailpro");
                String telephone = rs.getString("telephone");
                boolean actif = rs.getBoolean("actif");
                Fournisseur fournisseur = new Fournisseur(id, nom, siret, adresse, email, telephone, actif);

                fournisseurs.add(fournisseur);
            }
        }

        return fournisseurs;
    }
   
}

