package View;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class ImagesPanel extends Application {

    // Le BorderPane est notre conteneur principal (Parent)
    private BorderPane root = new BorderPane();

    @Override
    public void start(Stage stage) throws Exception {
        // 1. Titre
        Label title = new Label("GESTION DE LA SUPÉRETTE");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);

        // 2. Menu (HBox pour boutons horizontaux)
        HBox menu = new HBox(10);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);
        Button btnAcceuil = new Button("Accueil");
        Button btnStock = new Button("Voir Stock");
        Button btnVentes = new Button("Ventes");
        Button btnLots = new Button("Lots");
        Button btnFournisseurs = new Button("Fournisseur");
        Button btnQuitter = new Button("Quitter");
        menu.getChildren().addAll(btnAcceuil, btnStock, btnVentes, btnLots, btnFournisseurs, btnQuitter);

        // 3. Regrouper Titre + Menu en Haut
        VBox topContainer = new VBox(5);
        topContainer.getChildren().addAll(titleBox, menu);
        root.setTop(topContainer);

        btnStock.setOnAction(e -> root.setCenter(new ProduitView()));
        btnVentes.setOnAction(e -> root.setCenter(new VentesView()));
        btnFournisseurs.setOnAction(e -> root.setCenter(new FournisseurView()));
        btnLots.setOnAction(e -> root.setCenter(new LotsView()));
        btnQuitter.setOnAction(e -> Platform.exit());


        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Super-Market System");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}