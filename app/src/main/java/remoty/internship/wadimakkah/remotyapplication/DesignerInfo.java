package remoty.internship.wadimakkah.remotyapplication;

public class DesignerInfo {
    private String fullName;
    private String description;

    public DesignerInfo(String fullName, String description) {
        this.fullName = fullName;
        this.description = description;
    }

    public DesignerInfo() {

    }

    public String getFullName() {
        return fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
