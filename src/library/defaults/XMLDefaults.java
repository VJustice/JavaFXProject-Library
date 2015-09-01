package library.defaults;

public class XMLDefaults
{
    /** XML Files Paths **/
    public static final String RENT_DATA_PATH_XML = System.getProperty("user.dir") + "/rentData.xml";
    public static final String BOOK_DATA_PATH_XML = System.getProperty("user.dir") + "/bookData.xml";
    public static final String BOOK_DATA_PATH_EXCEL = System.getProperty("user.dir") + "/bookData.xls";
    public static final String RENT_DATA_PATH_EXCEL = System.getProperty("user.dir") + "/rentData.xls";

    /** rentData.xml **/
    public static final String RENT = "rent";
    public static final String PARTNER_NAME = "partnerName";
    public static final String PARTNER_ID = "partnerID";
    public static final String DELIVERY_TIME = "deliveryTime";
    public static final String RECEIVED_TIME = "receivedTime";

    /** bookData.xml **/
    public static final String BOOK = "book";
    public static final String AUTHOR_NAME = "authorName";
    public static final String BOOK_CATEGORY = "bookCategory";

    /** Common XML files **/
    public static final String DATA = "data";
    public static final String BOOK_NAME = "bookName";
    public static final String BOOK_ID = "bookID";
}
