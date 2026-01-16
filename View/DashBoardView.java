package View;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;

public class DashBoardView extends HBox {
    StackPane center = new StackPane();
  public DashBoardView() {
      center.getChildren().add(new Label("Bienvenue dans le logiciel de gestion"));
  }
}
