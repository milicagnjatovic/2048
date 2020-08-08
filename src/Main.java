import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    /** Object that represents current game, it's 4x4 by default*/
    GameGui gameGui = new GameGui(4);
    Window instructions = new Window("You can use left, right, up and down arrow for the game, by pressing it" +
            " you move all the fields with the numbers to one side. While moving two fields with same numbers collide in one " +
            "number, eg | 2 | 2 | will become | 4 | 0 | while moving left. If you have | 2 | 2 | 2 | 2 | you wont get 8, you'll get two 4s. " +
            "For getting 8 from that combination you have to make next move. \n" +
            "Goal of the game is to get highest possible number, game is called 2048 cause it's pretty good gaol, but you can make even higher scores." +
            "\nYou can chose what size of board you want for the game. Smaller the board is it's harder to play." +
            "\nGood luck :) ");
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        Label lbl = new Label("2048");

        //combo box for choosing what size board will be
        ComboBox<Integer> cbNumFields = new ComboBox<>();
        cbNumFields.getItems().addAll(4, 5, 8 ,10);
        cbNumFields.getSelectionModel().selectFirst();
        cbNumFields.setFocusTraversable(false);

        Button btnStart = new Button("START");
        btnStart.setFocusTraversable(false);

        Button btnInstructions = new Button("?");
        btnInstructions.setFocusTraversable(false);
        btnInstructions.setPrefWidth(btnInstructions.getHeight());

        VBox vbField = new VBox(10);
        vbField.getChildren().addAll(gameGui.getCanvas());
        vbField.setAlignment(Pos.CENTER);

        HBox hbControls = new HBox(10);
        hbControls.getChildren().addAll(cbNumFields, btnStart, btnInstructions);
        hbControls.setAlignment(Pos.CENTER);

        root.getChildren().addAll(lbl, hbControls, vbField);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        //we need to register event on the scene, we'll forward that event to gameGUI method which will handle it
        scene.setOnKeyPressed(e->{
            gameGui.action(e);
        });

        btnStart.setOnAction(e->{
            //if we just make new gameGUI it wont't change board on the screen (one in the vbField), old object will stay on show, that's why we need vbFieldf
            gameGui = new GameGui(cbNumFields.getValue());
            vbField.getChildren().clear();
            vbField.getChildren().addAll(gameGui.getCanvas());
            gameGui.restart();
        });

        btnInstructions.setOnAction(e -> {
            instructions.show();
        });
    }
}
