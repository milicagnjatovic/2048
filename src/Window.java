import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Window {
    private Stage stage;
    public Window(String txt){
        this.stage = new Stage();

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label lbl = new Label(txt);
        lbl.setWrapText(true);
        lbl.setAlignment(Pos.CENTER);
        root.getChildren().addAll(lbl);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 400, 300);

        stage.setScene(scene);
    }

    public void show(){
        stage.show();
    }
}
