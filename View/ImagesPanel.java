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

        // --- 1. Le Haut (Top) : Un titre ---
        Label title = new Label("GESTION DE LA SUPÉRETTE");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        HBox topBox = new HBox(title);
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(15));
        root.setTop(topBox); // On place la box en haut

        // --- 2. La Gauche (Left) : Menu de navigation ---
        HBox menu = new HBox(10); // Espacement de 10 entre les boutons
        menu.setPadding(new Insets(10));
        Button btnAcceuil = new Button("Acceuil");
        Button btnStock = new Button("Voir Stock");
        Button btnVentes = new Button("Ventes");
        Button btnLots = new Button("Lots");
        Button btnFournisseurs = new Button("Fournisseur");
        Button btnQuitter = new Button("Quitter");
        // On donne une largeur identique aux boutons
        btnStock.setMaxWidth(Double.MAX_VALUE);

        menu.getChildren().addAll(btnAcceuil,btnStock, btnVentes, btnLots,btnFournisseurs,btnQuitter);
        root.setLeft(menu); // On place le menu à gauche

        // --- 3. Le Centre (Center) : Zone de travail ---
        StackPane centerArea = new StackPane();
        btnAcceuil.setOnAction(e ->{
            centerArea.getChildren().add(new Label("Bienvenue dans le logiciel de gestion"));

        });
        btnStock.setOnAction(e->{

        });
        btnFournisseurs.setOnAction(e->{

        });
        btnVentes.setOnAction(e->{

        });
        btnLots.setOnAction(e->{

        });
        btnQuitter.setOnAction(e->{
          Platform.exit();
        });


        root.setCenter(centerArea); // On place au centre

        // Création de la scène (on passe le "root" qui est un Parent)
        Scene scene = new Scene(root, 600, 400);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}