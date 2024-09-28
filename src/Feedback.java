public class Feedback <T> {
    private T feedback;
    private Course course;
    private Student student;

    // Assuming we will get Course object and Student object
    Feedback(T feedback, Course co, Student stud) {
        this.feedback = feedback;
        this.course = co;
        this.student = stud;
        co.feedback_list.add(this);                 // adding student's feedback to the feedback list of the course
    }

    @Override
    public String toString() {
        return "Student: " + this.student.getName() + ", Feedback: " + this.feedback;
    }
}
