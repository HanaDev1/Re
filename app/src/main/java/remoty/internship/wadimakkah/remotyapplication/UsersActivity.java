package remoty.internship.wadimakkah.remotyapplication;

public class UsersActivity {
    private String designer_name;
private String img;

    public UsersActivity() {

    }

    public UsersActivity(String designer_name, String img) {
        this.designer_name = designer_name;
        this.img = img;
    }

    public  String getDesigner_name() {
        return designer_name;
    }

    public void setDesigner_name(String designer_name) {
        this.designer_name = designer_name;
    }


    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
