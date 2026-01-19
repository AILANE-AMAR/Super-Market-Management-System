package services;

import DATA.fieldType;
import db.Connexion;
import CLASSES.Vente;
import Exeption.ErrorLogger;

import java.sql.*;
import java.util.*;

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
        String checkLot = "SELECT COUNT(*) FROM lots WHERE id_lot = ?";
        String insertVente = "INSERT INTO ventes ( lot_id, quantite, prix_vente, date_vente) VALUES ( ?, ?, ?, ?)";

        try (Connection connection = Connexion.connect();
             PreparedStatement checkLotStmt = connection.prepareStatement(checkLot);
             PreparedStatement insertStmt = connection.prepareStatement(insertVente)) {


            // Vérification de l'existence du lot
            checkLotStmt.setInt(1, vente.getLotId());
            try (ResultSet rsLot = checkLotStmt.executeQuery()) {
                if (rsLot.next() && rsLot.getInt(1) == 0) {
                    System.err.println("Erreur : Le lot avec l'ID " + vente.getLotId() + " n'existe pas.");
                    return;
                }
            }

            // Insertion de la vente
            insertStmt.setInt(1, vente.getLotId());
            insertStmt.setInt(2, vente.getQuantite());
            insertStmt.setDouble(3, vente.getPrixVente());
            insertStmt.setTimestamp(4, Timestamp.valueOf(vente.getDateVente()));

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

    // NOUVELLE MÉTHODE : Pour afficher dans le tableau
    public List<Vente> getAllVentes() {
        List<Vente> liste = new ArrayList<>();
        String sql = "SELECT * FROM ventes";
        try (Connection conn = Connexion.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Vente v = new Vente(rs.getInt("id"), rs.getInt("lot_id"),
                        rs.getInt("quantite"), rs.getDouble("prix_vente"),
                        rs.getTimestamp("date_vente").toLocalDateTime());
                liste.add(v);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }
}
