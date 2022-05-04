package Classes;

public abstract class Media_Details {
    private short year;
    private String genre;
    private String description;
    private int quantity;
    private String picture_path;

    public Media_Details(short year, String genre, String description, int quantity, String picture_path) {
        this.year = year;
        this.genre = genre;
        this.description = description;
        this.quantity = quantity;
        this.picture_path = picture_path;
    }
}
