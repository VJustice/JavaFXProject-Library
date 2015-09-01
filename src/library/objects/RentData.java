package library.objects;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RentData
{
    private StringProperty partner_name;
    private StringProperty partner_id;
    private StringProperty book_name;
    private StringProperty book_id;
    private StringProperty delivery_time;
    private StringProperty received_time;

    public RentData(String partner_name, String partner_id, String book_name,
                    String book_id, String delivery_time, String received_time)
    {
        this.partner_name = new SimpleStringProperty(partner_name);
        this.partner_id = new SimpleStringProperty(partner_id);
        this.book_name = new SimpleStringProperty(book_name);
        this.book_id = new SimpleStringProperty(book_id);
        this.delivery_time = new SimpleStringProperty(delivery_time);
        this.received_time = new SimpleStringProperty(received_time);
    }

    public String getPartner_name() {
        return partner_name.get();
    }

    public String getPartner_id() {
        return partner_id.get();
    }

    public String getBook_name() {
        return book_name.get();
    }

    public String getBook_id() {
        return book_id.get();
    }

    public String getDelivery_time() {
        return delivery_time.get();
    }

    public String getReceived_time() {
        return received_time.get();
    }
}
