package services;

import db.Connexion;

import CLASSES.Lot;
import DATA.fieldType;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

import java.util.*
;public class GestionLot {
    // Méthode pour créer la table 'lots'
    public static void creerTableLots() {
        String sqlCreateTable = """
            CREATE TABLE IF NOT EXISTS lots (
                id_lot SERIAL PRIMARY KEY,
                id_produit INT NOT NULL,
                id_fournisseur INT NOT NULL,
                quantite INT NOT NULL,
                prix_achat DOUBLE PRECISION NOT NULL,
                date_peremption DATE NOT NULL,
                date_achat DATE NOT NULL,
                FOREIGN KEY (id_produit) REFERENCES produits(id),
                FOREIGN KEY (id_fournisseur) REFERENCES fournisseurs(id)
            )
        """;

        try (Connection connection = Connexion.connect(); 
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Table 'lots' créée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table 'lots' : " + e.getMessage());
        }
    }
        public void ajouterLot(Lot lot) throws SQLException {
            // Vérifications avant l'insertion
            String sqlCheckProduit = "SELECT COUNT(*) FROM produits WHERE id = ?";
            String sqlCheckFournisseur = "SELECT COUNT(*) FROM fournisseurs WHERE id = ?";
            String sqlCheckContrat = "SELECT COUNT(*) FROM contrats WHERE fournisseurId = ? AND produitId = ?";
            String sqlInsertLot = "INSERT INTO lots ( id_produit,id_fournisseur, quantite, prix_achat, date_achat, date_peremption) " +
                                  "VALUES ( ?, ?, ?, ?, ?, ?)";

            try (Connection connection = Connexion.connect()) {
                if (connection == null) {
                    System.err.println("Erreur : Connexion à la base de données échouée.");
                    return;
                }

                // Vérification de l'existence du produit
                try (PreparedStatement checkProduitStmt = connection.prepareStatement(sqlCheckProduit)) {
                    checkProduitStmt.setInt(1, lot.getIdProduit());
                    try (ResultSet rs = checkProduitStmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) == 0) {
                            System.err.println("Erreur : Le produit avec l'ID " + lot.getIdProduit() + " n'existe pas.");
                            return;
                        }
                    }
                }

                // Vérification de l'existence du fournisseur
                try (PreparedStatement checkFournisseurStmt = connection.prepareStatement(sqlCheckFournisseur)) {
                    checkFournisseurStmt.setInt(1, lot.getIdFournisseur());
                    try (ResultSet rs = checkFournisseurStmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) == 0) {
                            System.err.println("Erreur : Le fournisseur avec l'ID " + lot.getIdFournisseur() + " n'existe pas.");
                            return;
                        }
                    }
                }
                try (PreparedStatement checkContratStmt = connection.prepareStatement(sqlCheckContrat)) {
                    checkContratStmt.setInt(1, lot.getIdFournisseur());
                    checkContratStmt.setInt(2, lot.getIdProduit());
                    try (ResultSet rsContrat = checkContratStmt.executeQuery()) {
                        if (rsContrat.next() && rsContrat.getInt(1) > 0) {
                            System.err.println("Erreur : Le produit avec l'ID " + lot.getIdProduit() + " est déjà lié au fournisseur avec l'ID " + lot.getIdFournisseur() + " dans un contrat.");
                            return;
                        }
                    }
                }

                // Insertion du lot
                try (PreparedStatement stmt = connection.prepareStatement(sqlInsertLot)) {

                    stmt.setInt(1, lot.getIdProduit());
                    stmt.setInt(2, lot.getIdFournisseur());
                    stmt.setInt(3, lot.getQuantite());
                    stmt.setDouble(4, lot.getPrixAchat());
                    stmt.setDate(5, Date.valueOf(lot.getDateAchat()));
                    stmt.setDate(6, Date.valueOf(lot.getDatePeremption()));

                    stmt.executeUpdate();
                    System.out.println("Lot ajouté avec succès !");
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de l'ajout du lot : " + e.getMessage());
            }
        }

  
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
//                fieldType type = fieldType.VARCHAR; // Par défaut
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

  
    public List<Lot> displayTable() throws SQLException {
        List<Lot> lots = new ArrayList<>();
        String table = "Lots";

        try (Connection conn = Connexion.connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + table);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idLot = rs.getInt("id_lot");
                int idProduit = rs.getInt("id_produit");
                int idFournisseur = rs.getInt("id_fournisseur");
                int quantite = rs.getInt("quantite");
                double prixAchat = rs.getDouble("prix_achat");
                String dateAchatString = rs.getString("date_achat");
                LocalDate dateAchat = (dateAchatString != null) ? LocalDate.parse(dateAchatString) : null;
                String datePeremption = rs.getString("date_peremption");
                Lot lot = new Lot( idProduit, idFournisseur, quantite, prixAchat, dateAchat, datePeremption);

                lots.add(lot);
            }
        }

        return lots;
    }

}
