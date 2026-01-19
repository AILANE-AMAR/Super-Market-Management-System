package View;

import CLASSES.Produit;
import services.GestionProduit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.util.List;

public class ProduitView extends VBox {
    private GestionProduit service = new GestionProduit();
    private StackPane contenuDynamique = new StackPane();

    public ProduitView() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);

        // Header
        Label titre = new Label("GESTION DES PRODUITS");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Barre de navigation
        HBox barreNav = new HBox(15);
        barreNav.setAlignment(Pos.CENTER);
        Button btnListe = new Button("Voir le Stock");
        Button btnForm = new Button("Nouveau Produit");
        barreNav.getChildren().addAll(btnListe, btnForm);

        // Actions des boutons
        btnListe.setOnAction(e -> afficherTableau());
        btnForm.setOnAction(e -> afficherFormulaire());

        // Par défaut, on affiche le tableau
        afficherTableau();

        this.getChildren().addAll(titre, barreNav, contenuDynamique);
    }

    // --- MÉTHODE 1 : AFFICHAGE DU TABLEAU ---
    private void afficherTableau() {
        TableView<Produit> table = new TableView<>();

        TableColumn<Produit, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Produit, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Produit, String> colDesc = new TableColumn<>("Description");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Produit, String> colCat = new TableColumn<>("Catégorie");
        colCat.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        TableColumn<Produit, Double> colPrix = new TableColumn<>("Prix Vente");
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prixVente"));

        table.getColumns().addAll(colId, colNom, colDesc, colCat, colPrix);

        // On récupère les données via le service
        table.getItems().setAll(service.getAllProduit());

        contenuDynamique.getChildren().setAll(table);
    }

    // --- MÉTHODE 2 : AFFICHAGE DU FORMULAIRE ---
    private void afficherFormulaire() {
        GridPane form = new GridPane();
        form.setHgap(10); form.setVgap(10);
        form.setAlignment(Pos.CENTER);

        TextField txtId = new TextField(); // Optionnel si SERIAL, mais présent dans ton SQL
        TextField txtNom = new TextField();
        TextField txtDesc = new TextField();
        TextField txtCat = new TextField();
        TextField txtPrix = new TextField();
        Button btnValider = new Button("Ajouter au Stock");

        form.add(new Label("ID:"), 0, 0);          form.add(txtId, 1, 0);
        form.add(new Label("Nom:"), 0, 1);         form.add(txtNom, 1, 1);
        form.add(new Label("Description:"), 0, 2);  form.add(txtDesc, 1, 2);
        form.add(new Label("Catégorie:"), 0, 3);    form.add(txtCat, 1, 3);
        form.add(new Label("Prix Vente:"), 0, 4);   form.add(txtPrix, 1, 4);
        form.add(btnValider, 1, 5);

        btnValider.setOnAction(e -> {
            try {
                Produit p = new Produit(
                        Integer.parseInt(txtId.getText()),
                        txtNom.getText(),
                        txtDesc.getText(),
                        txtCat.getText(),
                        Double.parseDouble(txtPrix.getText())
                );
                service.ajouterProduit(p);
                afficherTableau(); // Rafraîchir la liste
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur de saisie. Vérifiez les champs numériques.");
                alert.show();
            }
        });

        contenuDynamique.getChildren().setAll(form);
    }
}