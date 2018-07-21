package remoty.internship.wadimakkah.remotyapplication;

public class ProductActivity {

    private String productName;
    private int productDeadline;


    public ProductActivity() {
    }

    public ProductActivity(String name, int deadline) {
        this.productName = name;
        this.productDeadline = deadline;

    }

    public String getProductName() {
        return productName;
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

}
