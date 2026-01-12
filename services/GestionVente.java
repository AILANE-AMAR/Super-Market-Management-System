package services;

import DATA.fieldType;
import db.Connexion;
import CLASSES.Vente;
import Exeption.ErrorLogger;

import java.sql.*;
import java.util.HashMap;

public class GestionVente {
    public static void creerTableVentes() {
 
        String sqlCreateTable = """
            CREATE TABLE IF NOT EXISTS ventes (
                id SERIAL PRIMARY KEY,
                produit_id INT NOT NULL,
                lot_id INT NOT NULL,
                quantite INT NOT NULL,
                prix_vente DOUBLE PRECISION NOT NULL,
                date_vente TIMESTAMP NOT NULL,
                FOREIGN KEY (produit_id) REFERENCES produits(id),
                FOREIGN KEY (lot_id) REFERENCES lots(id_lot)
            )
        """;

        try (Connection connection = Connexion.connect(); 
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Table 'ventes' créée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table 'ventes' : " + e.getMessage());
        }
    }
    public void ajouterVente(Vente vente) {
        String checkProduit = "SELECT COUNT(*) FROM produits WHERE id = ?";
        String checkLot = "SELECT COUNT(*) FROM lots WHERE id_lot = ?";
        String insertVente = "INSERT INTO ventes (produit_id, lot_id, quantite, prix_vente, date_vente) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = Connexion.connect();
             PreparedStatement checkProduitStmt = connection.prepareStatement(checkProduit);
             PreparedStatement checkLotStmt = connection.prepareStatement(checkLot);
             PreparedStatement insertStmt = connection.prepareStatement(insertVente)) {

            // Vérification de l'existence du produit
            checkProduitStmt.setInt(1, vente.getProduitId());
            try (ResultSet rsProduit = checkProduitStmt.executeQuery()) {
                if (rsProduit.next() && rsProduit.getInt(1) == 0) {
                    System.err.println("Erreur : Le produit avec l'ID " + vente.getProduitId() + " n'existe pas.");
                    return;
                }
            }

            // Vérification de l'existence du lot
            checkLotStmt.setInt(1, vente.getLotId());
            try (ResultSet rsLot = checkLotStmt.executeQuery()) {
                if (rsLot.next() && rsLot.getInt(1) == 0) {
                    System.err.println("Erreur : Le lot avec l'ID " + vente.getLotId() + " n'existe pas.");
                    return;
                }
            }

            // Insertion de la vente
            insertStmt.setInt(1, vente.getProduitId());
            insertStmt.setInt(2, vente.getLotId());
            insertStmt.setInt(3, vente.getQuantite());
            insertStmt.setDouble(4, vente.getPrixVente());
            insertStmt.setTimestamp(5, Timestamp.valueOf(vente.getDateVente()));

            insertStmt.executeUpdate();
            System.out.println("Vente ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'une vente : " + e.getMessage());
        }
    }


    public boolean verifierProduitEtLot(int produitId, int lotId) {
        String sql = "SELECT EXISTS (SELECT 1 FROM produits WHERE id = ?) AS produit_existe, " +
                     "EXISTS (SELECT 1 FROM lots WHERE id = ?) AS lot_existe";

        try (Connection connection = Connexion.connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, produitId);
            stmt.setInt(2, lotId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("produit_existe") && rs.getBoolean("lot_existe");
                }
            }
        } catch (SQLException e) {
            ErrorLogger.logError("Erreur lors de la vérification des IDs produit et lot", e);
        }
        return false;
    }

    private HashMap<String, fieldType> getTableStructure(String tableName) {
        HashMap<String, fieldType> tableStruct = new HashMap<>();
        String sql = "SELECT column_name, data_type FROM information_schema.columns WHERE table_name = ?";

        try (Connection connection = Connexion.connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tableName);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String columnName = rs.getString("column_name");
                    String dataType = rs.getString("data_type");

                    switch (dataType) {
                        case "character varying", "text" -> tableStruct.put(columnName, fieldType.VARCHAR);
                        case "integer" -> tableStruct.put(columnName, fieldType.INT4);
                        case "numeric", "double precision" -> tableStruct.put(columnName, fieldType.FLOAT8);
                        case "timestamp without time zone" -> tableStruct.put(columnName, fieldType.VARCHAR);
                        default -> System.err.println("Type non géré : " + dataType);
                    }
                }
            }
        } catch (SQLException e) {
            ErrorLogger.logError("Erreur lors de la récupération de la structure de la table " + tableName, e);
        }
        return tableStruct;
    }
}
