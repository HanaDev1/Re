package remoty.internship.wadimakkah.remotyapplication;

public class Product {
    //product
    private String product_name;
    private String id;
    private String product_details;
    private String Designer_email;


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

    public void setProduct_details(String product_details) {
        this.product_details = product_details;
    }
}