package CLASSES;

import java.util.List;
import services.GestionTableauxDeBord;
public class TableauDeBord {
    private final GestionTableauxDeBord gestionTableaux;

    public TableauDeBord() {
        this.gestionTableaux = new GestionTableauxDeBord();
    }

    public void afficherResultatsDuJour() {
        List<String> resultats = gestionTableaux.obtenirResultatsDuJour();
        System.out.println("Résultats du jour :");
        resultats.forEach(System.out::println);
    }

    public void afficherTop10Ventes() {
        List<String> ventes = gestionTableaux.obtenirTop10Ventes();
        System.out.println("Top 10 des meilleures ventes :");
        ventes.forEach(System.out::println);
    }

    public void afficherLotsProchesPeremption() {
        List<String> lots = gestionTableaux.obtenirLotsProchesPeremption();
        System.out.println("Lots proches de la péremption :");
        lots.forEach(System.out::println);
    }

    // Gérer une commande
    public void gererCommande(String produit, int nouvelleQuantite, boolean valider) {
        if (valider) {
            System.out.println("Commande validée pour le produit : " + produit + " avec quantité : " + nouvelleQuantite);
            // Appel à DAO pour mettre à jour les données dans la base (non implémenté ici)
        } else {
            System.out.println("Commande reportée pour le produit : " + produit);
        }
    }
}
