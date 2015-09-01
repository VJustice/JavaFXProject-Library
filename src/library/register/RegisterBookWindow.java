package library.register;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.defaults.TitlesDefaults;
import library.objects.BookData;
import library.objects.NumTextField;
import library.util.AlertBox;
import library.util.ConfirmBox;

import static javafx.scene.layout.GridPane.setConstraints;

public class RegisterBookWindow
{
    private Stage window = new Stage();
    private BorderPane border_pane = new BorderPane();
    private GridPane grid = new GridPane();
    private HBox bottom_box = new HBox();

    private Label book_name = new Label(TitlesDefaults.BOOK_TITLE);
    private Label book_id = new Label(TitlesDefaults.BOOK_CODE);
    private Label author_name = new Label(TitlesDefaults.BOOK_AUTHOR);
    private Label book_category = new Label(TitlesDefaults.BOOK_CATEGORY);

    private TextField book_name_field = new TextField();
    private NumTextField book_id_field = new NumTextField();
    private TextField author_name_field = new TextField();
    private TextField book_category_field = new TextField();

    private Button add_button = new Button();
    private Button cancel_button = new Button("Cancelar");

    private ObservableList<BookData> list = null;

    public void addBooks(String title, ObservableList<BookData> list)
    {
        this.list = list;
        addSomeBooks(title);
    }

    public void addSomeBooks(String title)
    {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        border_pane.setCenter(grid);
        border_pane.setBottom(bottom_box);

        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        bottom_box.setPadding(new Insets(15, 12, 30, 12));
        bottom_box.setSpacing(10);
        bottom_box.setAlignment(Pos.CENTER);

        if(this.list != null)
        {
            book_name_field.setText(list.get(0).getBook_name());
            book_id_field.setText(list.get(0).getBook_id());
            author_name_field.setText(list.get(0).getAuthor_name());
            book_category_field.setText(list.get(0).getBook_category());
        }
        add_button.setText(title);
        add_button.setPrefSize(150, 20);
        add_button.setOnAction(e ->
        {
            if(this.list != null) {
                editXML();
            } else {
                addToXML();
            }
        });

        cancel_button.setPrefSize(150, 20);
        cancel_button.setOnAction(e -> closeProgram());

        setConstraints(book_name, 0, 0);
        setConstraints(book_name_field, 1, 0);
        setConstraints(author_name, 0, 1);
        setConstraints(author_name_field, 1, 1);
        setConstraints(book_category, 0, 2);
        setConstraints(book_category_field, 1, 2);
        setConstraints(book_id, 0, 3);
        setConstraints(book_id_field, 1, 3);

        grid.getChildren().addAll(book_name, author_name, book_category,book_id, book_name_field, author_name_field, book_category_field, book_id_field);
        bottom_box.getChildren().addAll(add_button, cancel_button);

        Scene scene = new Scene(border_pane, 300, 300);
        scene.getStylesheets().add(getClass().getResource("RegisterBookWindow.css").toExternalForm());

        window.setScene(scene);
        window.showAndWait();
    }

    private void addToXML()
    {
        RegisterBooksXML xml = new RegisterBooksXML();
        if(book_name_field.getText().equals("") || book_id_field.getText().equals("")
                || author_name_field.getText().equals("") || book_category_field.getText().equals(""))
        {
            new AlertBox().display("Alerta","Todos os dados devem ser preenchidos");
        } else
        {
            xml.register(new BookData(book_name_field.getText(), book_id_field.getText(),
                    author_name_field.getText(), book_category_field.getText()));
            new AlertBox().display("Sucesso", "O livro foi registado com sucesso");
            window.close();
        }
    }

    private void editXML()
    {
        EditBooksXML xml = new EditBooksXML();
        if(book_name_field.getText().equals("") || book_id_field.getText().equals("")
                || author_name_field.getText().equals("") || book_category_field.getText().equals(""))
        {
            new AlertBox().display("Alerta","Todos os dados devem ser preenchidos");
        } else
        {
            xml.edition(list.get(0).getBook_id(), new BookData(book_name_field.getText(), book_id_field.getText(),
                    author_name_field.getText(), book_category_field.getText()));
            new AlertBox().display("Sucesso", "O livro foi inserido/editado com sucesso");
            window.close();
        }
    }

    private void closeProgram()
    {
        boolean answer = new ConfirmBox().display("Saida", "Tem a certeza de que quer cancelar registo?");
        if(answer)
        {
            window.close();
        }
    }
}
