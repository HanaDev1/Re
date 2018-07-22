package remoty.internship.wadimakkah.remotyapplication;

public class UsersActivity {
    private String designer_name;
private int img;



    public UsersActivity(String designer_name, int img) {
        this.designer_name = designer_name;
        this.img = img;
    }

    public  String getDesigner_name() {
        return designer_name;
    }

    public void setDesigner_name(String designer_name) {
        this.designer_name = designer_name;
    }


    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
