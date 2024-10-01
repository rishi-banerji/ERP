import java.util.ArrayList;
import java.util.Scanner;

public class TA extends Student {
    private Course assigned_course;

    public TA() {
        super();
        this.assigned_course = null;
    }

    @Override
    public TA login(String email, String password) {                       // REVIEW THIS AND OTHER LOGINS
        // ONLY CHECK PASSWORD, OBJECT CALL this: TA

        // If user exists, and user has entered the right password
        if (this.password.equals(password)) {
            // Retrieving student data

            System.out.println("Login successful. TA name: " + this.name);
            return this;
        } else {
            System.out.println("Login failed. Incorrect email or password.");
            return null;
        }
    }

    @Override
    public String role() {
        return "TA";
    }

    @Override
    public void print_menu() {
        super.print_menu();
        System.out.println("8: View Grades of Students enrolled in assigned course");
        System.out.println("9: Update Grades of Students enrolled in assigned course");
    }

    // Utility Function
    public Student find_student(String roll_num) {
        for (User user: Main.users) {
            if (user instanceof Student && ((Student) user).getRoll_number().equals(roll_num)) {
                // student with given roll number found
                return (Student) user;
            }
        }
        return null;
    }

    // TA FUNCTIONALITY 1
    public void view_grades() {
        if (this.assigned_course == null) {
            System.out.println("You have not been assigned a course yet.");
            return;
        }

        Scanner in = new Scanner(System.in);
        System.out.println("Enter the Roll-Number of the Student whose grade you want to view: ");
        String roll_number = in.nextLine();

        Student required_student = find_student(roll_number);
        if (required_student != null) {
            // student exists
            for (ArrayList<Course> list_of_courses: required_student.course_list) {
                for (Course c: list_of_courses) {
                    if (c.getCourse_code().equals(this.assigned_course.getCourse_code())) {
                        // Student has taken the course assigned to the TA
                        if (c.getGrade() == -1) {
                            System.out.println("Grade of the Student for your course has not been assigned yet.");
                        }
                        else {
                            System.out.println("Grade of the Student: " + c.getGrade());
                        }
                        return;
                    }
                }
            }
            // Course not found
            System.out.println("The Student has not taken the course assigned to you.");
            return;
        }
        // student not found
        System.out.println("Student with the given roll number does not exist.");
    }

    // TA FUNCTIONALITY 2
    public void update_grades() {
        if (this.assigned_course == null) {
            System.out.println("You have not been assigned a course yet.");
            return;
        }
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the Roll-Number of the Student who's grade you want to update: ");
        String roll_number = in.nextLine();

        Student required_student = find_student(roll_number);
        if (required_student != null) {
            for (ArrayList<Course> list_of_courses: required_student.course_list) {
                for (Course c: list_of_courses) {
                    if (c.getCourse_code().equals(this.assigned_course.getCourse_code())) {
                        // Student has taken the course assigned to the TA
                        if (c.getGrade() != -1) {
                            System.out.println("Grade already assigned: " + c.getGrade());
                            return;
                        }
                        System.out.println("Enter the grade for the Student: ");
                        int grade = in.nextInt();
                        in.nextLine();
                        c.setGrade(grade);
                        System.out.println("Grade updated successfully.");
                        return;
                    }
                }
            }
            System.out.println("The Student has not taken the course assigned to you.");
            return;
        }
        System.out.println("Student with the given roll number does not exist.");
    }

    public void setAssigned_course(Course assigned_course) {
        this.assigned_course = assigned_course;
    }
}