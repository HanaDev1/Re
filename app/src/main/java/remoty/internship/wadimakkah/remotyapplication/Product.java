package remoty.internship.wadimakkah.remotyapplication;

public class Product {
    //product
    private String product_name;
    private String id;
    private String product_details;
    private String Designer_email;
    private String type;
    private String Status;
    private String step;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Product(String product_name) { this.product_name = product_name;

    }

    public Product() {
    }

    public Product(String product_name, String id) {
        this.product_name = product_name;
        this.id = id;
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