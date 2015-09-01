package library.rent;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.defaults.TitlesDefaults;
import library.objects.BookData;
import library.objects.RentData;
import library.register.RegisterBooksToTable;
import library.util.AlertBox;
import library.util.ConfirmBox;

import static javafx.scene.layout.GridPane.setConstraints;

public class AddBooksRentWindow
{
    private Stage window = new Stage();

    private BorderPane border_pane = new BorderPane();
    private GridPane grid = new GridPane();
    private HBox bottom_box = new HBox();

    private Button action_button = new Button();
    private Button cancel_button = new Button("Cancelar");

    private Label partner_name = new Label(TitlesDefaults.PARTNER_NAME);
    private Label partner_id = new Label(TitlesDefaults.PARTNER_ID);
    private Label book_name = new Label(TitlesDefaults.BOOK_TITLE);
    private Label book_id = new Label(TitlesDefaults.BOOK_CODE);
    private Label rent_time = new Label(TitlesDefaults.DELIVERY_TIME);
    private Label delivery_time = new Label(TitlesDefaults.RECEIVED_TIME);

    private ComboBox<String> combo = new ComboBox<>();

    private TextField partner_name_field = new TextField();
    private TextField partner_id_field = new TextField();
    private TextField book_id_field = new TextField();
    private TextField rent_time_field = new TextField();
    private TextField delivery_time_field = new TextField();

    private ObservableList<RentData> list = null;
    private ObservableList<BookData> listBooks = null;
    private ObservableList<String> comboList = null;

    public void addBooks(String title, ObservableList<RentData> list)
    {
        this.list = list;
        addRenterBooks(title);
    }

    public void addRenterBooks(String title)
    {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        window.setOnCloseRequest(e ->
        {
            e.consume();
            closeProgram();
        });

        listBooks = FXCollections.observableArrayList(new RegisterBooksToTable().readXML());
        comboList = FXCollections.observableArrayList();
        for(int i = 0; i < listBooks.size(); i++)
        {
            comboList.add(listBooks.get(i).getBook_name());
        }
        combo.setItems(comboList);
        combo.getSelectionModel().selectFirst();
        combo.setPrefSize(150, 20);
        book_id_field.setText(listBooks.get(0).getBook_id());

        combo.setOnAction(e -> {
            String code = "";
            for(int i = 0; i < comboList.size(); i++)
            {
                if(combo.getSelectionModel().getSelectedItem().equals(comboList.get(i)))
                {
                    code = listBooks.get(i).getBook_id();
                }
            }
            book_id_field.setText(code);
        });

        border_pane.setCenter(grid);
        border_pane.setBottom(bottom_box);

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        bottom_box.setPadding(new Insets(15, 12, 30, 12));
        bottom_box.setSpacing(10);
        bottom_box.setAlignment(Pos.CENTER);

        if(this.list != null)
        {
            partner_name_field.setText(list.get(0).getPartner_name());
            partner_id_field.setText(list.get(0).getPartner_id());
            combo.getSelectionModel().select(list.get(0).getBook_name());
            book_id_field.setText(list.get(0).getBook_id());
            rent_time_field.setText(list.get(0).getDelivery_time());
            delivery_time_field.setText(list.get(0).getReceived_time());
        }

        action_button.setText(title);
        action_button.setPrefSize(150, 20);
        action_button.setOnAction(e ->
        {
            if(this.list != null) {
                editXML();
            } else {
                addToXML();
            }
        });

        cancel_button.setPrefSize(150, 20);
        cancel_button.setOnAction(e -> closeProgram());

        book_id_field.setEditable(false);
        rent_time_field.setPromptText("dd/mm/aaaa");
        delivery_time_field.setPromptText("dd/mm/aaaa");

        setConstraints(partner_name, 0, 0);
        setConstraints(partner_name_field, 1, 0);
        setConstraints(partner_id, 0, 1);
        setConstraints(partner_id_field, 1, 1);
        setConstraints(book_name, 0, 2);
        setConstraints(combo, 1, 2);
        setConstraints(book_id, 0, 3);
        setConstraints(book_id_field, 1, 3);
        setConstraints(rent_time, 0, 4);
        setConstraints(rent_time_field, 1, 4);
        setConstraints(delivery_time, 0, 5);
        setConstraints(delivery_time_field, 1, 5);

        grid.getChildren().addAll(partner_name, partner_name_field, partner_id, partner_id_field,
                book_name, combo, book_id, book_id_field, rent_time, rent_time_field,
                delivery_time, delivery_time_field);
        bottom_box.getChildren().addAll(action_button, cancel_button);

        Scene scene = new Scene(border_pane, 300, 400);
        scene.getStylesheets().add(getClass().getResource("AddBooksRentWindow.css").toExternalForm());

        window.setScene(scene);
        window.showAndWait();
    }

    private void addToXML()
    {
        AddBooksRentXML xml = new AddBooksRentXML();
        if(partner_name_field.getText().equals("") || partner_id_field.getText().equals("") ||
                combo.getSelectionModel().getSelectedItem().isEmpty() || book_id_field.getText().equals("")
                || rent_time_field.getText().equals(""))
        {
            new AlertBox().display("Alerta","Todos os dados devem ser preenchidos, excepto \n" +
                    "Data Recepcao se assim for necessario");
        } else
        {
            xml.register(new RentData(partner_name_field.getText(), partner_id_field.getText(),
                    combo.getSelectionModel().getSelectedItem(), book_id_field.getText(), rent_time_field.getText(),
                    delivery_time_field.getText()));
            new AlertBox().display("Sucesso", "O livro foi inserido/editado com sucesso");
            window.close();
        }
    }

    private void editXML()
    {
        EditBooksRentXML xml = new EditBooksRentXML();
        if(partner_name_field.getText().equals("") || partner_id_field.getText().equals("")
                || combo.getSelectionModel().getSelectedItem().isEmpty() || book_id_field.getText().equals("")
                || rent_time_field.getText().equals(""))
        {
            new AlertBox().display("Alerta","Todos os dados devem ser preenchidos, excepto \n" +
                    "Data Recepcao se assim for necessario");
        } else
        {
            xml.edition(list.get(0).getPartner_id(), new RentData(partner_name_field.getText(), partner_id_field.getText(),
                    combo.getSelectionModel().getSelectedItem(), book_id_field.getText(), rent_time_field.getText(),
                    delivery_time_field.getText()));
            new AlertBox().display("Sucesso", "O livro foi inserido/editado com sucesso");
            window.close();
        }
    }

    private void closeProgram()
    {
        boolean answer = new ConfirmBox().display("Saida", "Tem a certeza de que pretende sair?");
        if(answer)
        {
            window.close();
        }
    }
}
