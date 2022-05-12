package Classes;

public class User extends User_Description{
    private int id;
    private String name;

    public User(int id, String name, String egn, String phone, String address, String description) {
        super(egn, phone, address, description);
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
