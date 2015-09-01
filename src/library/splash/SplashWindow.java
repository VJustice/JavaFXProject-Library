package library.splash;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.admin.AdminWindow;
import library.register.BookWindow;
import library.register.RegisterBookWindow;
import library.rent.RentWindow;

public class SplashWindow extends Application
{
    private Stage window = null;
    private Scene scene = null;

    private BorderPane border_pane = new BorderPane();
    private HBox horizontal_box = new HBox();
    private VBox vertical_box = new VBox();

    private Button rent_button = new Button("Requisitar/Entregar Livros");
    private Button register_books_button = new Button("Registar Livros");
    private Button admin_button = new Button("Administrador");

    private Label head_label = new Label("Biblioteca Associacao de \n" +
            "     Festas de Sao Torcato");

    public void start(Stage primaryStage) throws Exception
    {
        window = primaryStage;
        window.setTitle("Biblioteca Associacao de Festas de Sao Torcato");
        window.setOnCloseRequest(e -> window.close());

        border_pane.setCenter(vertical_box);
        border_pane.setBottom(horizontal_box);
        horizontal_box.setAlignment(Pos.CENTER);
        vertical_box.setAlignment(Pos.CENTER);
        horizontal_box.setPadding(new Insets(15, 12, 30, 12));
        horizontal_box.setSpacing(10);

        rent_button.setPrefSize(250, 20);
        register_books_button.setPrefSize(250, 20);
        admin_button.setPrefSize(250, 20);

        vertical_box.getChildren().add(head_label);
        horizontal_box.getChildren().addAll(rent_button, register_books_button, admin_button);

        rent_button.setOnAction(e -> new RentWindow().rentSomeBooks());
        register_books_button.setOnAction(e -> new BookWindow().registerBooks());
        admin_button.setOnAction(e -> new AdminWindow().showAdminWindow());

        scene = new Scene(border_pane, 800, 400);
        scene.getStylesheets().add(getClass().getResource("SplashWindow.css").toExternalForm());

        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
