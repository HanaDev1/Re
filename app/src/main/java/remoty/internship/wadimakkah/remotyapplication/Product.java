package remoty.internship.wadimakkah.remotyapplication;

public class Product {
//product
    private String productName;
    private int productDeadline;
    private String productID;
    private String productStatus;
    private String productDetails;
    private String productSteps;


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

    public String getProductID() { return productID; }

    public void setProductID(String productID) { this.productID = productID; }

    public String getProductStatus() { return productStatus; }

    public void setProductStatus(String productStatus) { this.productStatus = productStatus; }

    public String getProductDetails() { return productDetails; }

    public void setProductDetails(String productDetails) { this.productDetails = productDetails; }

    public String getProductSteps() { return productSteps; }

    public void setProductSteps(String productSteps) { this.productSteps = productSteps; }

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
