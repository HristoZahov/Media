package Classes;

public class Media extends Media_Details{
    private int id;
    private String name;
    private String author;

    public Media(short year, String genre, String description, int quantity, String picture_path, int id, String name, String author) {
        super(year, genre, description, quantity, picture_path);
        this.id = id;
        this.name = name;
        this.author = author;
    }
}
