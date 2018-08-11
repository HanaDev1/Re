package remoty.internship.wadimakkah.remotyapplication;

public class Product {
    //product
    private String product_name;
    private String id;
    private String product_details;
    private String Designer_email;
    private String type;
    private String productStatus;
    private String steps;

    public Product(String product_name) {
        this.product_name = product_name;

    }

    public Product() {
    }

    public String getProductStatus() {
        return productStatus;
    }



    public String getType() { return type; }

    public void setType(String type) { this.type = type; }


    public String getDesigner_email() {
        return Designer_email;
    }

    public void setDesigner_email(String designer_email) {
        Designer_email = designer_email;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_details() {
        return product_details;
    }


}