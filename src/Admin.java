import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {
    private String email;
    private final String password = "iiitd";
    private String name;

    public static List<Complaint> all_complaints = new ArrayList<>();

    // Constructors
    public Admin(String name) {
        super(name);
    }

    public Admin() {
        super(null);
        this.email = null;
    }


    @Override
    public String toString() {
        return "Admin Name: " + name + ", Email id: " + email;
    }

    // PARENT(USER) CLASS METHODS
    @Override
    public String role() {
        return "Admin";
    }

    // ADMIN will enter both email and pass, ACCEPT ONLY IF PASS == this.password
    @Override
    public boolean sign_up(String email, String password) {
        Scanner scanner = new Scanner(System.in);
        if (!this.password.equals(password)) {
            System.out.println("Wrong password for Admin. Sign-up failed.");
            return false;
        }
        if (Main.does_user_exist(email)) {
            System.out.println("User already exists. Please login, or choose a different email id");
            return false;
        }
        else {
            this.email = email;

            System.out.println("Enter your name: ");
            this.name= scanner.nextLine();

            Main.register_user(this);
            System.out.println("Signup successful.");
        }
        return true;
    }

    @Override
    public Admin login(String email, String password) {
        if (this.password.equals(password)) {
            // Retrieving admin data
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
        System.out.println("1: Manage course catalog");
        System.out.println("2: Manage student records");
        System.out.println("3: Assign professors to courses");
        System.out.println("4: Handle Complaints");

    }



    // FUNCTIONALITIES

    // Functionality 1: Managing Course Catalog
    public void viewCourses() {
        if (Course.available_courses.isEmpty()) {
            System.out.println("No available courses.");
        } else {
            for (Course course : Course.available_courses) {
                System.out.println(course);
            }
        }
    }

    public void add_course() {
        // NOTE: Assigning prof SEPARATELY using the prof_to_course method
        Scanner s = new Scanner(System.in);

        System.out.println("Enter course name:");
        String course_name = s.nextLine();
        System.out.println("Enter course location:");
        String location = s.nextLine();
        System.out.println("Enter course code:");
        String course_code = s.nextLine();
        System.out.println("Enter domain of course:");
        String domain = s.nextLine();
        System.out.println("Enter number of credits:");
        int credits = s.nextInt();
        s.nextLine();
        System.out.println("Enter course timings:");
        String timings = s.nextLine();
        System.out.println("Enter course syllabus:");
        String syllabus = s.nextLine();
        System.out.println("Enter office hours:");
        String office_hours = s.nextLine();
        System.out.println("Enter enrollment limit for the course");
        int enrollment = s.nextInt();

        // setting pre-requisites
        ArrayList<Course> pre_req = new ArrayList<>();
        boolean flag = true;
        while (flag) {
            System.out.println("Do you want to add a pre-requisite? Enter 1 for yes, 2 for no");
            int choice = s.nextInt();

            if (choice == 1) {
                System.out.println("Enter the course code");
                String code = s.nextLine();

                for (Course c: Course.available_courses) {
                    if (c.getCourse_code().equals(code)) {      // Searching for course by COURSE CODE
                        pre_req.add(c);
                    }
                }
            }
            else {
                flag = false;
                break;
            }
        }

        Course newCourse = new Course(course_name, location, course_code, domain, credits, timings, pre_req, syllabus,
                office_hours, enrollment);
        Course.available_courses.add(newCourse);

        System.out.println("Course added successfully.");
    }

    public void delete_course() {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter the course code to delete:");
        String code = s.nextLine();

        Course c = null;
        for (Course course: Course.available_courses) {
            if (course.getCourse_code().equals(code)) {
                c = course;
                break;
            }
        }

        if (c != null) {
            Course.available_courses.remove(c);
            System.out.println("Course removed successfully.");
        } else {
            System.out.println("Course does not exist. Enter a valid coursee");
        }
    }

    private Student get_student(String rollNumber) {                        // Utility func
        for (User user : Main.users) {
            if (user.role().equals("Student")) {
                Student student = (Student) user;
                if (student.getRoll_number().equals(rollNumber)) {
                    return student;
                }
            }
        }
        return null;
    }

    // Functionality 2: Managing Student records
    public void print_student_details() {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter the student's roll number:");
        String roll_num = s.nextLine();

        Student student = get_student(roll_num);

        if (student == null) {
            System.out.println("Student not found");
        } else {
            System.out.println(student);  // Calls the student's toString() method to display personal details
            view_courses_by_sem(student);
        }
    }

    private void view_courses_by_sem(Student student) {                     // This is a utility func
        ArrayList<ArrayList<Course>> course_list = student.getCourse_list();
        for (int semester = 0; semester < course_list.size(); semester++) {
            System.out.println("Semester " + (semester + 1) + " Courses:");
            for (Course course : course_list.get(semester)) {
                System.out.println(course);  // Calls the Course's toString() method
            }
        }
    }


    public void update_student() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the student's roll number to update:");
        String roll_num = scanner.nextLine();

        Student student = get_student(roll_num);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        // Choose to update either grades or other info
        System.out.println("What would you like to update? Enter 1 for personal info(email/CGPA), 2 for grades: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            // Update personal info
            update_personal_info(student, scanner);
        } else if (choice == 2) {
            // Update grades
            update_grades(student, scanner);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Updates personal_info: Email and CGPA. Note: SGPA will automatically be updated if CGPA is being updated
    private void update_personal_info(Student student, Scanner scanner) {
        // ASSUMPTION: Name and Roll no. cannot change
        System.out.println("What do you want to update/calculate? Enter 1 for email, 2 for CGPA");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("Enter new email. current: " + student.getEmail());
            String newEmail = scanner.nextLine();
            student.setEmail(newEmail);

            System.out.println("Personal information updated successfully.");
        }
        else if (choice == 2) {
            // ONLY IF GRADES HAVE BEEN ASSIGNED FOR CURRENT SEM
            boolean flag = true;
            for (Course c: student.getCourse_list().get(student.getSem() - 1)) {        // for courses in current sem
                if (c.getGrade() == -1) {
                    // grade not assigned
                    System.out.println("Grade has not been assigned for 1 or more courses");
                    flag = false;
                    break;
                }
            }
            if (flag) {
                student.computeCGPA(student.getSem());
                student.setSem(student.getSem() + 1);

                System.out.println("Student CGPA updated. Student passes to the next sem.");
            }
        }
        else {
            System.out.println("invaild choice");
        }
    }


    private void update_grades(Student student, Scanner scanner) {
        /*
        ASSUMPTIONS:
        1. Grades for ALL courses for a semester are given at once
        2.
        */

        ArrayList<ArrayList<Course>> course_list = student.getCourse_list();

        // View courses by semester
        view_courses_by_sem(student);

        // Ask for the semester and course code
        System.out.println("Enter the semester number to update grades:");
        int sem_num = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (sem_num < 1 || sem_num > course_list.size()) {
            System.out.println("Invalid semester number.");
            return;
        }

        // Get the semester's courses
        ArrayList<Course> semesterCourses = course_list.get(sem_num - 1);

        System.out.println("Enter the course code of the course whose grade u want to update:");
        String cc = scanner.nextLine();

        Course course_to_update = null;
        for (Course course : semesterCourses) {
            if (course.getCourse_code().equals(cc)) {
                course_to_update = course;
                break;
            }
        }

        if (course_to_update == null) {
            System.out.println("Course not found.");
            return;
        }

        // Enter new grade
        System.out.println("Enter the grade for course: ");
        int grade = scanner.nextInt();
        course_to_update.setGrade(grade);


        System.out.println("Grade updated successfully for course.");
    }


    // Functionality 3: Assigning prof to course
    public void prof_to_course(Course course) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the EMAIL of the professor to assign to this course:");
        String email = scanner.nextLine();

        int flag = 1;
        for (User user : Main.users) {
            if (user.role().equals("Prof")) {
                Prof professor = (Prof) user;
                if (professor.getEmail().equals(email) && professor.getDomain().equals(course.getDomain())
                        && professor.isAvailable()) {
                    course.setProf(professor);
                    professor.setAvailable(false);

                    // Setting assigned course in professor object as well
                    professor.setAssignedCourse(course);

                    flag = 0;
                    System.out.println("Professor " + professor.getName() + " assigned to course " + course.getCourse_name());
                    break;
                }
            }
        }

        if (flag == 1) {
            System.out.println("Professor not found, not available, or domain not same as that of the course.");
            return;
        }
    }


    // NOTE: IMPLEMENT HANDLE COMPLAINTS HERE
    // Functionality 4: Handle Complaints
    // Viewing all complaints
    public void view_all_complaints() {
        if (all_complaints.isEmpty()) {
            System.out.println("No complaints submitted yet.");
        }
        else {
            for (Complaint complaint : all_complaints) {
                System.out.println(complaint);
            }
        }
    }

    // Updating the status of a complaint
    public void update_complaint_status(String student_rollnum, String new_status) {
        for (Complaint complaint : all_complaints) {
            if (complaint.getStudent().getRoll_number().equals(student_rollnum)) {
                complaint.setStatus(new_status);
                System.out.println("Status of given complaint updated");
                return;
            }
        }
        System.out.println("Complaint not found.");
    }

    // Filtering complaints by status
    public void filter_complaints(String status) {                          // by status
        boolean f = false;
        for (Complaint complaint: all_complaints) {
            if (complaint.getStatus().equals(status)) {
                System.out.println(complaint);
                f = true;
            }
        }
        if (!f) {
            System.out.println("No complaint with given status");
        }
    }



    // GETTERS AND SETTERS
    public String getEmail() {
        return email;
    }
}
