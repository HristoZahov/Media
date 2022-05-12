package Classes;

public class Media extends Media_Details{
    private int id;
    private String name;
    private String author;

    public Media(int id, String name, String author, int year, String genre, String description, int quantity, String picture_path) {
        super(year, genre, description, quantity, picture_path);
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Id: " + id + "\n");
        builder.append("Name: " + name + "\n");
        builder.append("Author: " + author + "\n");
        builder.append(super.toString());

        return builder.toString();
    }
}
