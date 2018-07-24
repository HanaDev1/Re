package remoty.internship.wadimakkah.remotyapplication;

public class Product {

    private String productName;
    private int productDeadline;
    public String full_name;
    private String decription;
    private String email;
    private String phone_number;
    private String user;

    public String getFull_name() {
        return full_name;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getDecription() {
        return decription;
    }
// Default constructor required for calls to
    // DataSnapshot.getValue(Product.class)

    public Product() { }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }


    public String getProductName() {
        return productName;
    }



    public String getPhone_number() {
        return phone_number;
    }

    public String getUser() {
        return user;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public int getProductDeadline() {
        return productDeadline;
    }

    public void setProductDeadline(int deadline) {
        this.productDeadline = deadline;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
