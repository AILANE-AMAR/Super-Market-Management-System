package View;


import javafx.scene.layout.HBox;
import CLASSES.Lot;
import services.GestionLot;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class LotsView extends VBox {
    private  GestionLot services =new GestionLot();
    private  StackPane pane = new StackPane();
    public LotsView()  {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);

        // Header
        Label titre = new Label("GESTION DES LOTS");
        titre.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Barre de navigation
        HBox barreNav = new HBox(15);
        barreNav.setAlignment(Pos.CENTER);
        Button btnListe = new Button("Voir les Lots");
        Button btnForm = new Button("Nouvelle Lot");
        barreNav.getChildren().addAll(btnListe, btnForm);
        // Actions des boutons
        btnListe.setOnAction(e -> {
            try {
                afficherTableau();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        btnForm.setOnAction(e -> afficherFormulaire());

        // Par défaut, on affiche le tableau
        try {
            afficherTableau();
        }
        catch(SQLException ex ){
            throw new RuntimeException(ex);
        }

        this.getChildren().addAll(titre, barreNav, pane);
    }
    public void afficherTableau() throws SQLException {
       TableView<Lot> table = new TableView<>();

       TableColumn<Lot, Integer> colLot = new TableColumn<>("ID Lot");
       colLot.setCellValueFactory(new PropertyValueFactory<>("idLot"));
       TableColumn<Lot, Integer> colProd = new TableColumn<>("ID Produit");
       colProd.setCellValueFactory(new PropertyValueFactory<>("idProduit"));
        TableColumn<Lot, Integer> colFour = new TableColumn<>("ID Fournisseur");
        colFour.setCellValueFactory(new PropertyValueFactory<>("idFournisseur"));
        TableColumn<Lot, Integer> colQuant = new TableColumn<>("Quantite");
        colQuant.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        TableColumn<Lot, Integer> colPrix = new TableColumn<>("Prix D'achat");
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prixAchat"));
        TableColumn<Lot, Integer> colDatep = new TableColumn<>("Date peremption");
        colDatep.setCellValueFactory(new PropertyValueFactory<>("datePeremption"));
        TableColumn<Lot, Integer> colDateA = new TableColumn<>(" Date Achat");
        colDateA.setCellValueFactory(new PropertyValueFactory<>("dateAchat"));
        table.getColumns().addAll(colLot, colProd,  colFour,colQuant ,  colPrix,colDatep,colDateA);
        table.getItems().setAll(services.getAllLots());

        pane.getChildren().setAll(table);
    }
    public void afficherFormulaire() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER);

        TextField txtprod = new TextField();
        TextField txtfour = new TextField();
        TextField txtquant = new TextField();
        TextField txtprix = new TextField();
        TextField txtdatep = new TextField(); // L'utilisateur tape AAAA-MM-JJ
        txtdatep.setPromptText("AAAA-MM-JJ");

        Button valider = new Button("Enregistrer le Lot");

        form.add(new Label("ID Produit"), 0, 0);   form.add(txtprod, 0, 1);
        form.add(new Label("ID Fournisseur"), 1, 0); form.add(txtfour, 1, 1);
        form.add(new Label("Quantité"), 2, 0);     form.add(txtquant, 2, 1);
        form.add(new Label("Prix"), 3, 0);         form.add(txtprix, 3, 1);
        form.add(new Label("Date Péremption"), 4, 0); form.add(txtdatep, 4, 1);
        form.add(valider, 5, 1);

        valider.setOnAction(e -> {
            try {
                // RÉCUPÉRATION DU TEXTE ICI (quand on clique)
                String datep = txtdatep.getText();

                // CRÉATION DU LOT (pas Vente !)
                Lot nouveauLot = new Lot(0,
                        Integer.parseInt(txtprod.getText()),
                        Integer.parseInt(txtfour.getText()),
                        Integer.parseInt(txtquant.getText()),
                        Double.parseDouble(txtprix.getText()),
                        java.time.LocalDate.now(), // Date achat (aujourd'hui)
                        datep                      // Ta date de péremption String
                );

                // APPEL AU SERVICE
                services.ajouterLot(nouveauLot);

                // RAFRAICHIR LE TABLEAU
                afficherTableau();

            } catch (Exception ex) {
                // Petite alerte en cas d'erreur de saisie
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur de saisie : vérifiez les nombres et le format date.");
                alert.show();
            }
        });

        pane.getChildren().setAll(form);
    }
}
