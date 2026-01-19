package services;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import CLASSES.Produit;
import CLASSES.Vente;
import DATA.fieldType;
import db.Connexion;
import Exeption.ErrorLogger;
public class GestionProduit {

    public static void creerTableProduits() {
        // Script SQL pour créer la table 'produits'
        String sqlCreateTable = """
        CREATE TABLE IF NOT EXISTS produits (
            id  INT PRIMARY KEY,
            nom VARCHAR(255),
            description TEXT,
            categorie VARCHAR(255),
            prix_vente DOUBLE PRECISION
        );
    """;

        try (Connection connection = Connexion.connect(); 
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlCreateTable);
            System.out.println("Table 'produits' créée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création de la table 'produits' : " + e.getMessage());
        }
    }

    public void ajouterProduit(Produit produit) {
        String sql = "INSERT INTO produits (id,nom, description, categorie, prix_vente) VALUES (?,?, ?, ?, ?)";
        try (Connection connection = Connexion.connect();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            if (connection == null) {
                System.err.println("Erreur : Connexion à la base de données échouée.");
                return;
            }
            pstmt.setInt(1,produit.getId());
            pstmt.setString(2, produit.getNom());
            pstmt.setString(3, produit.getDescription());
            pstmt.setString(4, produit.getCategorie());
            pstmt.setDouble(5, produit.getPrixVente());
            pstmt.executeUpdate();

            System.out.println("Produit ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du produit : " + e.getMessage());
            ErrorLogger.logError("Erreur lors de l'ajout d'un produit", e);
        }
    }


    
    public List<Produit> Display() {
        List<Produit> produits = new ArrayList<>();
        String sql = "SELECT * FROM produits";

        try (Connection connection = Connexion.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                String categorie = rs.getString("categorie");
                double prixVente = rs.getDouble("prix_vente");
                Produit produit = new Produit(id, nom, description, categorie, prixVente);
                produits.add(produit);
            }
        } catch (Exception e) {
            ErrorLogger.logError("Erreur lors de la récupération des produits", e);
            System.err.println("Impossible de récupérer les produits.");
        }

        return produits;
    }

    public void execute(String query) {
        try (Connection connection = Connexion.connect();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
            System.out.println("Requête exécutée avec succès : " + query);
        } catch (Exception e) {
            ErrorLogger.logError("Erreur lors de l'exécution de la requête : " + query, e);
            System.err.println("Impossible d'exécuter la requête.");
        }
    }


    public List<Produit> getAllProduit(){
        List<Produit>  list = new ArrayList<>();
        String sql = "select * from produits";
        try(Connection conn = Connexion.connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                Produit produit = new Produit(rs.getInt("id"),
                        rs.getString("nom"),rs.getString("description"),
                        rs.getString("categorie"), rs.getDouble("prix_vente") );
            list.add(produit);
            }
        }
        catch (SQLException e){ e.printStackTrace();    }
        return list ;
    }

    public void supprimerProduitParId(int idProduit) {
        String sql = "DELETE FROM produits WHERE id_produit = ?";

        try (Connection connection = Connexion.connect();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProduit);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Produit supprimé avec succès !");
            } else {
                System.err.println("Aucun produit trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            ErrorLogger.logError("Erreur lors de la suppression du produit", e);
            System.err.println("Impossible de supprimer le produit. Vérifiez l'ID.");
        }
    }
}
