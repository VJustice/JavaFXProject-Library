package library.admin;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.defaults.XMLDefaults;
import library.util.ConfirmBox;

import java.io.File;

public class AdminWindow
{
    private Stage window = new Stage();

    private BorderPane border_pane = new BorderPane();
    private VBox vertical_box = new VBox();

    private Button import_excel_book = new Button("Importar ficheiro Excel para XML -> Livros");
    private Button export_excel_book = new Button("Exportar de XML para ficheiro Excel -> Livros");
    private Button import_excel_rent = new Button("Importar ficheiro Excel para XML -> Aluguer");
    private Button export_excel_rent = new Button("Exportar de XML para ficheiro Excel -> Aluguer");

    public void showAdminWindow()
    {
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Administracao");

        border_pane.setCenter(vertical_box);
        vertical_box.setPadding(new Insets(15, 12, 30, 12));
        vertical_box.setSpacing(10);
        vertical_box.setAlignment(Pos.CENTER);

        import_excel_book.setPrefSize(250, 20);
        export_excel_book.setPrefSize(250, 20);
        import_excel_rent.setPrefSize(250, 20);
        export_excel_rent.setPrefSize(250, 20);

        vertical_box.getChildren().addAll(import_excel_book, export_excel_book, import_excel_rent, export_excel_rent);

        /** Application **/
        import_excel_book.setOnAction(e -> new ExcelToXMLBook().generateXML(new File(XMLDefaults.BOOK_DATA_PATH_EXCEL)));
        export_excel_book.setOnAction(e -> new XMLtoExcelBook().generateExcel(new File(XMLDefaults.BOOK_DATA_PATH_XML)));
        import_excel_rent.setOnAction(e -> new ExcelToXMLRent().generateXML(new File(XMLDefaults.RENT_DATA_PATH_EXCEL)));
        export_excel_rent.setOnAction(e -> new XMLtoExcelRent().generateExcel(new File(XMLDefaults.RENT_DATA_PATH_XML)));

        /** Testing **/
        /*import_excel_book.setOnAction(e -> new ExcelToXMLBook().generateXML(new File("C:/Users/Filipe/Desktop/Programa Biblioteca/bookData.xls")));
        export_excel_book.setOnAction(e -> new XMLtoExcelBook().generateExcel(new File("C:/Users/Filipe/Desktop/Programa Biblioteca/bookData.xml")));
        import_excel_rent.setOnAction(e -> new ExcelToXMLRent().generateXML(new File("C:/Users/Filipe/Desktop/Programa Biblioteca/rentData.xls")));
        export_excel_rent.setOnAction(e -> new XMLtoExcelRent().generateExcel(new File("C:/Users/Filipe/Desktop/Programa Biblioteca/rentData.xml")));
        */

        Scene scene = new Scene(border_pane, 300, 300);
        scene.getStylesheets().add(getClass().getResource("AdminWindow.css").toExternalForm());

        window.setScene(scene);
        window.showAndWait();
    }
}
