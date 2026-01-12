package services;

import db.Connexion;
import Exeption.ErrorLogger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestionTableauxDeBord {

    // Résultats des ventes du jour
    public List<String> obtenirResultatsDuJour() {
        List<String> resultats = new ArrayList<>();
        String sql = "SELECT SUM(prix_vente * quantite) AS total_ventes, " +
                     "SUM((prix_vente - prix_achat) * quantite) AS total_benefices " +
                     "FROM ventes v JOIN lots l ON v.lot_id = l.id " +
                     "WHERE v.date_vente::date = CURRENT_DATE";

        try (Connection connection = Connexion.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                resultats.add("Total des Ventes : " + rs.getDouble("total_ventes"));
                resultats.add("Total des Bénéfices : " + rs.getDouble("total_benefices"));
            }
        } catch (SQLException e) {
            ErrorLogger.logError("Erreur lors de la récupération des résultats du jour", e);
        }
        return resultats;
    }

    // Classement des 10 meilleures ventes
    public List<String> obtenirTop10Ventes() {
        List<String> ventes = new ArrayList<>();
        String sql = "SELECT p.nom, SUM(v.quantite) AS total_quantite, " +
                     "SUM(v.prix_vente * v.quantite) AS total_ventes " +
                     "FROM ventes v JOIN produits p ON v.produit_id = p.id " +
                     "GROUP BY p.nom ORDER BY total_quantite DESC LIMIT 10";

        try (Connection connection = Connexion.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String vente = "Produit : " + rs.getString("nom") +
                               ", Quantité Vendue : " + rs.getInt("total_quantite") +
                               ", Total des Ventes : " + rs.getDouble("total_ventes");
                ventes.add(vente);
            }
        } catch (SQLException e) {
            ErrorLogger.logError("Erreur lors de la récupération des meilleures ventes", e);
        }
        return ventes;
    }

    // Liste des lots proches de la péremption
    public List<String> obtenirLotsProchesPeremption() {
        List<String> lots = new ArrayList<>();
        String sql = """
    SELECT id_lot, id_produit, date_peremption
    FROM lots
    WHERE date_peremption <= CURRENT_DATE + INTERVAL '7 days'
""";


        try (Connection connection = Connexion.connect();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String lot = "Lot ID : " + rs.getInt("id_lot")
                        + ", Produit ID : " + rs.getInt("id_produit")
                        + ", Date de Péremption : " + rs.getDate("date_peremption");
                lots.add(lot);
            }
        } catch (SQLException e) {
            ErrorLogger.logError("Erreur lors de la récupération des lots proches de la péremption", e);
        }
        return lots;
    }
}
