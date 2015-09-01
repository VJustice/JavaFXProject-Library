package library.objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BookData
{
    private StringProperty book_name;
    private StringProperty book_id;
    private StringProperty author_name;
    private StringProperty book_category;

    public BookData(String book_name, String book_id, String author_name, String book_category)
    {
        this.book_name = new SimpleStringProperty(book_name);
        this.book_id = new SimpleStringProperty(book_id);
        this.author_name = new SimpleStringProperty(author_name);
        this.book_category = new SimpleStringProperty(book_category);
    }

    public String getBook_name() {
        return book_name.get();
    }

    public String getBook_id() {
        return book_id.get();
    }

    public String getAuthor_name() {
        return author_name.get();
    }

    public String getBook_category() {
        return book_category.get();
    }
}

