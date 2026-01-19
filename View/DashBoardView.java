package View;

import services.GestionTableauxDeBord;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.List;

public class DashBoardView extends VBox { // Utilise VBox pour empiler les sections
    private GestionTableauxDeBord service = new GestionTableauxDeBord();

    public DashBoardView() {
        this.setSpacing(20);
        this.setPadding(new Insets(30));
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle("-fx-background-color: #f4f7f6;"); // Fond gris très léger

        // --- TITRE ---
        Label titre = new Label("TABLEAU DE BORD");
        titre.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // --- SECTION 1 : RÉSULTATS DU JOUR (CARTES) ---
        HBox containerCartes = new HBox(20);
        containerCartes.setAlignment(Pos.CENTER);

        List<String> resultatsJour = service.obtenirResultatsDuJour();
        for (String res : resultatsJour) {
            containerCartes.getChildren().add(creerCarteStat("Aujourd'hui", res));
        }

        // --- SECTION 2 : TOP VENTES ET PÉREMPTION ---
        HBox colonnes = new HBox(30);
        colonnes.setAlignment(Pos.TOP_CENTER);
        colonnes.setPrefHeight(400);

        // Colonne Top 10
        VBox colTop = creerSectionListe("🏆 Top 10 des Ventes", service.obtenirTop10Ventes(), "#e8f6f3");
        // Colonne Péremption
        VBox colAlerte = creerSectionListe("⚠️ Alertes Péremption (7j)", service.obtenirLotsProchesPeremption(), "#fdedec");

        HBox.setHgrow(colTop, Priority.ALWAYS);
        HBox.setHgrow(colAlerte, Priority.ALWAYS);
        colonnes.getChildren().addAll(colTop, colAlerte);

        this.getChildren().addAll(titre, containerCartes, colonnes);
    }

    // Méthode pour créer une petite carte de stats (Ventes/Bénéfices)
    private VBox creerCarteStat(String titre, String valeur) {
        VBox carte = new VBox(10);
        carte.setPadding(new Insets(20));
        carte.setMinWidth(250);
        carte.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        Label lblTitre = new Label(titre);
        lblTitre.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
        Label lblVal = new Label(valeur);
        lblVal.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");

        carte.getChildren().addAll(lblTitre, lblVal);
        return carte;
    }

    // Méthode pour créer les listes (Top 10 et Péremption)
    private VBox creerSectionListe(String titre, List<String> donnees, String color) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(15));
        box.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10; -fx-border-color: #dcdde1; -fx-border-radius: 10;");

        Label lblTitre = new Label(titre);
        lblTitre.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(donnees);
        listView.setPrefHeight(300);

        // Message si liste vide
        if(donnees.isEmpty()) {
            listView.setPlaceholder(new Label("Aucune donnée disponible"));
        }

        box.getChildren().addAll(lblTitre, listView);
        return box;
    }
}