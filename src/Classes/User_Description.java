package Classes;

public abstract class User_Description {
    private String egn;
    private String phone;
    private String address;
    private String description;

    public User_Description(String egn, String phone, String address, String description) {
        this.egn = egn;
        this.phone = phone;
        this.address = address;
        this.description = description;
    }

    public String getEgn() {
        return egn;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }
}
