package View;

import CLASSES.Vente;
import services.GestionVente;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.sql.Date;
import java.time.LocalDateTime;

public class VentesView extends VBox {
    private GestionVente service = new GestionVente();
    private StackPane contenuDynamique = new StackPane();

    public VentesView() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);

        // Header
        Label titre = new Label("GESTION DES VENTES");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Barre de navigation
        HBox barreNav = new HBox(15);
        barreNav.setAlignment(Pos.CENTER);
        Button btnListe = new Button("Voir les Ventes");
        Button btnForm = new Button("Nouvelle Vente");
        barreNav.getChildren().addAll(btnListe, btnForm);

        // Actions des boutons
        btnListe.setOnAction(e -> afficherTableau());
        btnForm.setOnAction(e -> afficherFormulaire());

        // Par défaut, on affiche le tableau
        afficherTableau();

        this.getChildren().addAll(titre, barreNav, contenuDynamique);
    }

    // --- MÈTHODE 1 : AFFICHAGE DU TABLEAU ---
    private void afficherTableau() {
        TableView<Vente> table = new TableView<>();
        TableColumn<Vente, Integer> colVent = new TableColumn<>("ID Ventes");
        colVent.setCellValueFactory(new PropertyValueFactory<>("id"));



        TableColumn<Vente, Integer> colLot = new TableColumn<>("ID Lot ");
        colLot.setCellValueFactory(new PropertyValueFactory<>("lotId"));

        TableColumn<Vente, Integer> colQté = new TableColumn<>("Quantité");
        colQté.setCellValueFactory(new PropertyValueFactory<>("quantite"));

        TableColumn<Vente, Double> colPrix = new TableColumn<>("Prix");
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        TableColumn<Vente, Date> colDate = new TableColumn<>("Date Vente ");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateVente"));
        table.getColumns().addAll(colVent,colLot,  colQté, colPrix, colDate);
        table.getItems().setAll(service.getAllVentes());

        contenuDynamique.getChildren().setAll(table);
    }

    // --- MÈTHODE 2 : AFFICHAGE DU FORMULAIRE ---
    private void afficherFormulaire() {
        GridPane form = new GridPane();
        form.setHgap(10); form.setVgap(10);
        form.setAlignment(Pos.CENTER);

        TextField txtProd = new TextField();
        TextField txtLot = new TextField();
        TextField txtQté = new TextField();
        TextField txtPrix = new TextField();
        Button btnValider = new Button("Enregistrer la vente");

        form.add(new Label("ID Lot:"), 0, 0);     form.add(txtLot, 1, 0);
        form.add(new Label("Quantité:"), 0, 1);   form.add(txtQté, 1, 1);
        form.add(new Label("Prix Vente:"), 0, 2); form.add(txtPrix, 1, 2);
        form.add(btnValider, 1, 3);

        btnValider.setOnAction(e -> {
            Vente v = new Vente(0,
                    Integer.parseInt(txtProd.getText()),
                    Integer.parseInt(txtLot.getText()),
                    Integer.parseInt(txtQté.getText()),
                    LocalDateTime.now()
            );
            service.ajouterVente(v);
            afficherTableau(); // Retour à la liste après ajout
        });

        contenuDynamique.getChildren().setAll(form);
    }
}