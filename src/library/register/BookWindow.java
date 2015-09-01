package library.register;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import library.defaults.TitlesDefaults;
import library.objects.BookData;
import library.util.AlertBox;
import library.util.ConfirmBox;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
public class BookWindow
{
    private Stage window = new Stage();

    private BorderPane border_pane = new BorderPane();
    private HBox bottom_border = new HBox();

    private TableView table = new TableView();

    private TableColumn book_name_c = new TableColumn(TitlesDefaults.BOOK_TITLE);
    private TableColumn author_name_c = new TableColumn(TitlesDefaults.BOOK_AUTHOR);
    private TableColumn book_category_c = new TableColumn(TitlesDefaults.BOOK_CATEGORY);
    private TableColumn book_id_c = new TableColumn(TitlesDefaults.BOOK_CODE);
    private TableColumn action_c = new TableColumn("Edicao");

    private Button add_new = new Button("Registar");
    private Button close_rent = new Button("Cancelar");
    private Button remove_rent = new Button("Remover");

    private RegisterBooksToTable registration = new RegisterBooksToTable();

    public void registerBooks()
    {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Registar Livros");

        ObservableList<BookData> list = FXCollections.observableArrayList(registration.readXML());

        bottom_border.setPadding(new Insets(15, 12, 15, 12));
        bottom_border.setSpacing(10);
        bottom_border.setAlignment(Pos.CENTER);

        add_new.setPrefSize(150, 20);
        remove_rent.setPrefSize(150, 20);
        close_rent.setPrefSize(150, 20);

        book_name_c.setCellValueFactory(new PropertyValueFactory<BookData, String>("Book_name"));
        book_id_c.setCellValueFactory(new PropertyValueFactory<BookData, String>("Book_id"));
        author_name_c.setCellValueFactory(new PropertyValueFactory<BookData, String>("Author_name"));
        book_category_c.setCellValueFactory(new PropertyValueFactory<BookData, String>("Book_category"));

        action_c.setSortable(false);
        action_c.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BookData, Boolean>, ObservableValue<Boolean>>()
        {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<BookData, Boolean> features)
            {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        action_c.setCellFactory(e -> new EditBook(table));

        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().setAll(book_name_c, author_name_c, book_category_c, book_id_c, action_c);
        bottom_border.getChildren().addAll(add_new, remove_rent, close_rent);

        add_new.setOnAction(e ->
        {
            new RegisterBookWindow().addSomeBooks("Registar Livros");
            refreshTableView(table, Arrays.asList(book_name_c, author_name_c, book_category_c, book_id_c, action_c),
                    registration.readXML());
        });
        remove_rent.setOnAction(e -> {
            if(!table.getSelectionModel().getSelectedCells().isEmpty())
            {
                boolean answer = new ConfirmBox().display("Eliminar", "Tem a certeza de que pretende eliminar esta Requisicao?\n" +
                        "Apos eliminado nao pode ser recuperado");
                if (answer) {
                    ObservableList<BookData> data = table.getSelectionModel().getSelectedItems();
                    new DeleteBooksXML().deletion(data);
                    refreshTableView(table, Arrays.asList(book_name_c, author_name_c, book_category_c, book_id_c, action_c),
                            registration.readXML());
                }
            } else
            {
                new AlertBox().display("Alerta", "Para remover deve seleccionar uma Linha");
            }
        });
        close_rent.setOnAction(e -> window.close());

        border_pane.setCenter(table);
        border_pane.setBottom(bottom_border);

        table.setItems(list);

        Scene scene = new Scene(border_pane, 800, 600);
        scene.getStylesheets().add(getClass().getResource("BookWindow.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();
    }

    public static <T,U> void refreshTableView(TableView<T> tableView, List<TableColumn<T,U>> columns, List<T> rows) {
        tableView.getColumns().clear();
        tableView.getColumns().addAll(columns);

        ObservableList<T> list = FXCollections.observableArrayList(rows);
        tableView.setItems(list);
    }

    private class EditBook extends TableCell<BookData, Boolean>
    {
        final Button addButton = new Button("Editar");
        final StackPane paddedButton = new StackPane();
        final DoubleProperty buttonY = new SimpleDoubleProperty();

        EditBook(final TableView table)
        {
            paddedButton.setPadding(new Insets(3));
            paddedButton.getChildren().add(addButton);

            addButton.setOnMousePressed(new EventHandler<MouseEvent>()
            {
                @Override public void handle(MouseEvent mouseEvent)
                {
                    buttonY.set(mouseEvent.getScreenY());
                }
            });
            addButton.setOnAction(e ->
            {
                table.getSelectionModel().select(getTableRow().getIndex());
                new RegisterBookWindow().addBooks("Gravar", table.getSelectionModel().getSelectedItems());
                refreshTableView(table, Arrays.asList(book_name_c, author_name_c, book_category_c, book_id_c, action_c),
                        registration.readXML());
            });
        }

        @Override protected void updateItem(Boolean item, boolean empty)
        {
            super.updateItem(item, empty);
            if (!empty)
            {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(paddedButton);
            } else
            {
                setGraphic(null);
            }
        }
    }

}
