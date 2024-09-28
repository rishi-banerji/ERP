public class Complaint {
    private String description;
    private String status;              // Pending or resolved
    private Student student;             // student who complained

    // CONSTRUCTOR
    public Complaint(String description, Student student) {
        this.description = description;
        this.status = "Pending";
        this.student = student;
    }

    // GETTERS AND SETTERS
    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return "Complaint by Student: " + student.getName() + ", Description: " + description + ", Status: " + status;
    }
}