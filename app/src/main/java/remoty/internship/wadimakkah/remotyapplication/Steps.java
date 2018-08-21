package remoty.internship.wadimakkah.remotyapplication;

public class Steps {
    private String step;
    private String status;

    public Steps() {
    }

    public Steps(String step, String status) {
        this.step = step;
        this.status = status;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
