package Classes;

public class User extends User_Description{
    private int id;
    private String name;

    public User(String egn, String phone, String address, String description, int id, String name) {
        super(egn, phone, address, description);
        this.id = id;
        this.name = name;
    }
}
