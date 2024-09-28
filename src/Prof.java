import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Prof extends User {
    private String email, password, name;
    private String domain;                                          // cse, ece, mth, etc
    private Course assignedCourse;

    // ASSUMPTION: A prof can only have 1 DOMAIN, and can ONLY TEACH COURSES OF THAT DOMAIN
    // ASSUMPTION: A prof can only teach 1 COURSE

    private boolean available;


    // CONSTRUCTORS
    public Prof() {
        super(null);
        this.email = null;
        this.password = null;
        this.domain = null;
        this.available = true;
    }

    public Prof(String name, String email, String password, String domain, boolean available) {
        super(name);
        this.email = email;
        this.password = password;
        this.domain = domain;
        this.available = available;
    }



    @Override
    public String toString() {
        return "Professor Email id: " + email + " Domain: " + domain + ", Available: " + available;
    }


        @Override
    public String role() {
        return "Prof";
    }

    @Override
    public boolean sign_up(String email, String password) {
        Scanner scanner = new Scanner(System.in);
        if (Main.does_user_exist(email)) {
            System.out.println("User already exists. Please login, or choose a different email id");
            return false;
        }
        else {
            this.email = email;
            this.password = password;

            System.out.println("Enter your name");
            this.name = scanner.nextLine();
            System.out.println("enter the Domain of your work: ");
            this.domain = scanner.nextLine();

            Main.register_user(this);
            System.out.println("Signup successful");
        }
        return true;
    }

    @Override
    public Prof login(String email, String password) {
        if (this.password.equals(password)) {
            // Retrieving prof data
            System.out.println("Login successful. Prof name: " + this.name);
            return this;
        } else {
            System.out.println("Login failed. Incorrect email or password.");
            return null;
        }
    }

    @Override
    public void print_menu() {
        System.out.println("0: Exit");
        System.out.println("1: Manage Course assigned to you");
        System.out.println("2: View Enrolled Students");
        System.out.println("3: View Feedback");
    }


    // FUNCTIONALITIES

    // Functionality 1: Manage Courses
    public void manage_course() {
        if (this.assignedCourse == null) {
            System.out.println("No course assigned to you yet.");
            return;
        }
        Scanner scanner = new Scanner(System.in);

        System.out.println(this.assignedCourse);

        System.out.println("Do you update the details of the course assigned to you? Enter 1 if you do");
        int choice = scanner.nextInt();

        if (choice == 1) {
            update_course(this.assignedCourse);
        }
    }

    private void update_course(Course course) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select the attribute to update:");
        System.out.println("1: Syllabus, 2: Timings, 3: Credits, 4: Prerequisites, 5: Enrollment Limit, 6: Office Hours");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter new syllabus:");
                String syllabus = scanner.nextLine();
                course.setSyllabus(syllabus);
                break;
            case 2:
                System.out.println("Enter new timings:");
                String timings = scanner.nextLine();
                course.setTimings(timings);
                break;
            case 3:
                System.out.println("Enter new credits:");
                int credits = scanner.nextInt();
                scanner.nextLine();
                course.setCredits(credits);
                break;
            case 4:
                System.out.println("Enter the course code of the new prerequisites(comma-separated, NO SPACES):");
                String input = scanner.nextLine();
                String[] pre_requisites = input.split(",");
                ArrayList<Course> new_pr_list = new ArrayList<>();                      // new pre-requisites
                for (String code : pre_requisites) {
                    for (Course available_course : Course.available_courses) {
                        if (available_course.getCourse_code().trim().equals(code.trim())) {
                            new_pr_list.add(available_course);
                        }
                    }
                }
                course.setPre_req(new_pr_list);
                break;
            case 5:
                System.out.println("Enter new enrollment limit:");
                int limit = scanner.nextInt();
                scanner.nextLine();
                course.setEnrollment_limit(limit);
                break;
            case 6:
                System.out.println("Enter new office hours:");
                String office_hours = scanner.nextLine();
                course.setOffice_hours(office_hours);
                break;
            default:
                System.out.println("Invalid choice.");
        }

        System.out.println("Course updated successfully.");
    }

    // Functionality 2: View Enrolled Students
    public void view_enrolled_students() {
        if (this.assignedCourse == null) {
            System.out.println("No course has been assigned to you yet.");
            return;
        }

        List<Student> enrolled_students = assignedCourse.getEnrolledStudents();
        if (enrolled_students.isEmpty()) {
            System.out.println("No students are enrolled in this course.");
        } else {
            System.out.println("Enrolled Students for Course: " + assignedCourse.getCourse_name());
            for (Student student : enrolled_students) {
                System.out.println("Name: " + student.getName() + ", Roll Number: " + student.getRoll_number() + ", CGPA: " + student.getCGPA());
            }
        }
    }

    // Functionality 3: View Feedback
    public void view_feedback() {
        if (this.assignedCourse == null) {
            System.out.println("No course has been assigned to you yet, so no feedback has been submitted.");
            return;
        }
        for (var f: this.assignedCourse.feedback_list) {
            System.out.println(f);
        }
    }


    // GETTERS AND SETTERS
    public String getEmail() {
        return this.email;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Course getAssignedCourse() {
        return assignedCourse;
    }

    public void setAssignedCourse(Course assignedCourse) {
        this.assignedCourse = assignedCourse;
    }
}
