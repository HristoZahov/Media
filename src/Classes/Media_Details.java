package Classes;

public abstract class Media_Details {
    private int year;
    private String genre;
    private String description;
    private int quantity;
    private String picture_path;

    public Media_Details(int year, String genre, String description, int quantity, String picture_path) {
        this.year = year;
        this.genre = genre;
        this.description = description;
        this.quantity = quantity;
        this.picture_path = picture_path;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPicture_path() {
        return picture_path;
    }

    public void setPicture_path(String picture_path) {
        this.picture_path = picture_path;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Year: " + year + "\n");
        builder.append("Genre: " + genre + "\n");
        builder.append("Quantity: " + quantity + "\n");
        builder.append("Description: " + description + "\n");

        return builder.toString();
    }
}
