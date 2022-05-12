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

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
