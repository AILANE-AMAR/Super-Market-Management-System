package View;

import CLASSES.Fournisseur;
import services.GestionFournisseur; // Assure-toi que le nom du service est correct
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.sql.SQLException;

public class FournisseurView extends VBox {
    private GestionFournisseur service = new GestionFournisseur();
    private StackPane contenuDynamique = new StackPane();

    public FournisseurView() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);

        // Header
        Label titre = new Label("GESTION DES FOURNISSEURS");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Barre de navigation
        HBox barreNav = new HBox(15);
        barreNav.setAlignment(Pos.CENTER);
        Button btnListe = new Button("Voir les Fournisseurs");
        Button btnForm = new Button("Nouveau Fournisseur");
        barreNav.getChildren().addAll(btnListe, btnForm);

        // Actions
        btnListe.setOnAction(e -> afficherTableau());
        btnForm.setOnAction(e -> afficherFormulaire());

        // Affichage par défaut
        afficherTableau();

        this.getChildren().addAll(titre, barreNav, contenuDynamique);
    }

    // --- TABLEAU ---
    private void afficherTableau() {
        TableView<Fournisseur> table = new TableView<>();

        TableColumn<Fournisseur, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Fournisseur, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Fournisseur, String> colSiret = new TableColumn<>("SIRET");
        colSiret.setCellValueFactory(new PropertyValueFactory<>("numeroSiret"));

        TableColumn<Fournisseur, String> colAdresse = new TableColumn<>("Adresse");
        colAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        TableColumn<Fournisseur, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Fournisseur, String> colTel = new TableColumn<>("Téléphone");
        colTel.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        TableColumn<Fournisseur, Boolean> colActif = new TableColumn<>("Actif");
        colActif.setCellValueFactory(new PropertyValueFactory<>("actif"));

        table.getColumns().addAll(colId, colNom, colSiret, colAdresse, colEmail, colTel, colActif);

        try {
            table.getItems().setAll(service.getAllFournisseur());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        contenuDynamique.getChildren().setAll(table);
    }

    // --- FORMULAIRE ---
    private void afficherFormulaire() {
        GridPane form = new GridPane();
        form.setHgap(10); form.setVgap(10);
        form.setAlignment(Pos.CENTER);

        TextField txtNom = new TextField();
        TextField txtSiret = new TextField();
        TextField txtAdresse = new TextField();
        TextField txtEmail = new TextField();
        TextField txtTel = new TextField();
        CheckBox chkActif = new CheckBox("Fournisseur Actif");
        chkActif.setSelected(true);

        Button btnValider = new Button("Enregistrer Fournisseur");

        form.add(new Label("Nom:"), 0, 0);         form.add(txtNom, 1, 0);
        form.add(new Label("N° SIRET:"), 0, 1);     form.add(txtSiret, 1, 1);
        form.add(new Label("Adresse:"), 0, 2);     form.add(txtAdresse, 1, 2);
        form.add(new Label("Email:"), 0, 3);       form.add(txtEmail, 1, 3);
        form.add(new Label("Téléphone:"), 0, 4);   form.add(txtTel, 1, 4);
        form.add(chkActif, 1, 5);
        form.add(btnValider, 1, 6);

        btnValider.setOnAction(e -> {
            Fournisseur f = new Fournisseur(
                    0, // ID généré par le SERIAL de la base
                    txtNom.getText(),
                    txtSiret.getText(),
                    txtAdresse.getText(),
                    txtEmail.getText(),
                    txtTel.getText(),
                    chkActif.isSelected()
            );
            service.ajouterFournisseur(f);
            afficherTableau();
        });

        contenuDynamique.getChildren().setAll(form);
    }
}