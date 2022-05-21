package Classes;

import java.util.Date;

public class Order {
    private String user;
    private String media;
    private Date start_date;
    private Date end_date;

    public Order(String user, String media, Date start_date, Date end_date) {
        this.user = user;
        this.media = media;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("User: " + getUser() + "\n");
        builder.append("Media: " + getMedia() + "\n");
        builder.append("Start Date: " + getStart_date() + "\n");
        builder.append("End Date: " + getEnd_date() + "\n");

        return builder.toString();
    }
}
