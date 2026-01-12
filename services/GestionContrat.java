package services ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import CLASSES.Contrat;
import Exeption.ErrorLogger;
import db.Connexion;
public class GestionContrat {
     
    public static void creerTableContrats() {
        String sql = """
            CREATE TABLE IF NOT EXISTS contrats (
                id SERIAL PRIMARY KEY,                       -- ID du contrat
                fournisseurId INT NOT NULL,                  -- ID du fournisseur
                produitId INT NOT NULL,                      -- ID du produit
                prixFixe DOUBLE PRECISION NOT NULL,          -- Prix fixe du contrat
                quantiteMinimale INT NOT NULL,               -- Quantité minimale
                dateDebut VARCHAR(255) NOT NULL,              -- Date de début
                dateFin VARCHAR(255) NOT NULL,                -- Date de fin
                estActif BOOLEAN DEFAULT TRUE ,              -- Etat actif du contrat
                FOREIGN KEY (produitId) REFERENCES produits(id),
                FOREIGN KEY (fournisseurId) REFERENCES fournisseurs(id)
            );
        """;

 
        try (Connection connection = Connexion.connect();  
             Statement stmt = connection.createStatement()) { 
            stmt.executeUpdate(sql);
            System.out.println("Table 'contrats' créée avec succès.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table 'contrats'.");
            e.printStackTrace();
        }
    }
    public void ajouterContrat(Contrat contrat) {
        String sqlCheckFournisseur = "SELECT COUNT(*) FROM fournisseurs WHERE id = ?";
        String sqlCheckProduit = "SELECT COUNT(*) FROM produits WHERE id = ?";
        String sqlInsertContrat = "INSERT INTO contrats (id, fournisseurId, produitId, prixFixe, quantiteMinimale, dateDebut, dateFin, estActif) " +
                                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Connexion.connect()) {
            if (connection == null) {
                System.err.println("Erreur : Connexion à la base de données échouée.");
                return;
            }

            // Vérification de l'existence du fournisseur
            try (PreparedStatement checkFournisseurStmt = connection.prepareStatement(sqlCheckFournisseur)) {
                checkFournisseurStmt.setInt(1, contrat.getFournisseurId());
                try (ResultSet rs = checkFournisseurStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        System.err.println("Erreur : Le fournisseur avec l'ID " + contrat.getFournisseurId() + " n'existe pas.");
                        return;
                    }
                }
            }

            // Vérification de l'existence du produit
            try (PreparedStatement checkProduitStmt = connection.prepareStatement(sqlCheckProduit)) {
                checkProduitStmt.setInt(1, contrat.getProduitId());
                try (ResultSet rs = checkProduitStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        System.err.println("Erreur : Le produit avec l'ID " + contrat.getProduitId() + " n'existe pas.");
                        return;
                    }
                }
            }
            try (PreparedStatement stmt = connection.prepareStatement(sqlInsertContrat)) {
                stmt.setInt(1, contrat.getId());
                stmt.setInt(2, contrat.getFournisseurId());
                stmt.setInt(3, contrat.getProduitId());
                stmt.setDouble(4, contrat.getPrixFixe());
                stmt.setInt(5, contrat.getQuantiteMinimale());
                stmt.setString(6, contrat.getDateDebut());
                stmt.setString(7, contrat.getDateFin());
                stmt.setBoolean(8, contrat.isEstActif());
                stmt.executeUpdate();
                System.out.println("Contrat ajouté avec succès !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'un contrat : " + e.getMessage());
            ErrorLogger.logError("Erreur lors de l'ajout d'un contrat", e);
        }
    }



    public void afficherContrats() {
        String sql = "SELECT * FROM contrats";

        try (Connection connection = Connexion.connect();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Affichage des contrats
            while (rs.next()) {
                System.out.println("id:"+rs.getInt("id") + " , fournisseur " +
                                   rs.getInt("fournisseurId") + "produit  " +
                                   rs.getInt("produitId") + "prix  " +
                                   rs.getDouble("prixFixe") + "  quantite " +
                                   rs.getInt("quantiteMinimale") + "  date debut" +
                                   rs.getString("dateDebut") + " date fin  " +
                                   rs.getString("dateFin"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des contrats : " + e.getMessage());
        }
    }


public void afficherContratsActifs() {
    String sql = "SELECT * FROM contrats WHERE estActif = TRUE";

    try (Connection connection = Connexion.connect();
         PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            System.out.println("Contrat ID: " + rs.getInt("id") +
                               ", Fournisseur ID: " + rs.getInt("fournisseurId") +
                               ", Produit ID: " + rs.getInt("produitId") +
                               ", Prix Fixe: " + rs.getDouble("prixFixe") +
                               ", Quantité Minimale: " + rs.getInt("quantiteMinimale") +
                               ", Date Début: " + rs.getString("dateDebut") +
                               ", Date Fin: " + rs.getString("dateFin")+                          
                              ", Est Actif: " + (rs.getBoolean("estActif") ? "Actif" : "Inactif"));
            
            
                
        }
    } catch (SQLException e) {
        ErrorLogger.logError("Erreur lors de l'affichage des contrats actifs", e);
    }
}
// si on veux que on arret d'acheter chez un  fournisseur c'est mieux que on le gele parec que on veux que on le garde dans notre base de donnnes , si on as un produut qui est expiret on vas oir de quelle fournisseur que l est savais 

public void gelerContrat(int id) {
    String sql = "UPDATE contrats SET estActif = FALSE WHERE id = ?";

    try (Connection connection = Connexion.connect();
         PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, id); 
        stmt.executeUpdate(); 
        System.out.println("Contrat marqué comme gelé !");
    } catch (SQLException e) {
        ErrorLogger.logError("Erreur lors du gel d'un contrat", e); 
    }
}
// si on veux que on le recative 
public void activerContrat(int id) {
    String sql = "UPDATE contrats SET estActif = TRUE WHERE id = ?";

    try (Connection connection = Connexion.connect();
         PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setInt(1, id);
        stmt.executeUpdate();
        System.out.println("Contrat activé !");
    } catch (SQLException e) {
        ErrorLogger.logError("Erreur lors de l'activation d'un contrat", e);
    }
}

}