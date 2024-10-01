import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Student extends User {
    protected String email, password;
    protected String name, roll_number;
    protected int sem;
    protected double CGPA;

    // stores all courses taken. Courses in CURRENT SEM: grade not assigned
    // inner arraylist: courses in 1 sem
    protected ArrayList<ArrayList<Course>> course_list;

    protected ArrayList<Double> SGPA_list = new ArrayList<>(8);

    protected List<Complaint> complaints;


    // Constructors
    public Student() {
        super(null);

        this.email = null;
        this.password = null;
        this.roll_number = null;
        this.sem = 1;  // Initially semester 1
        this.CGPA = 0.0;  // Initially CGPA is 0
        this.course_list = new ArrayList<>();  // Initialize an empty ArrayList for courses
        this.SGPA_list = new ArrayList<>(8);  // Initialize an empty ArrayList for SGPA
        this.complaints = new ArrayList<>();
    }

    public Student(String email, String password, String name, String roll_number) {
        super(name);

        this.email = email;
        this.password = password;
        this.roll_number = roll_number;
        this.sem = 1;  // Initially semester 1
        this.CGPA = 0.0;  // Initially CGPA is 0
        this.course_list = new ArrayList<>();  // Initialize an empty ArrayList for courses
        this.SGPA_list = new ArrayList<>(8);  // Initialize an empty ArrayList for SGPA
        this.complaints = new ArrayList<>();
    }



    public String toString() {
        return "Student Name: " + name + ", Email: " + email + ", Roll Number: " + roll_number;
    }

    // PARENT METHODS
    @Override
    public String role() {
        return "Student";
    }

    // Implementing Login and signup of User interface
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

            System.out.println("Enter your name: ");
            this.name = scanner.nextLine();
            System.out.println("enter your roll number: ");
            this.roll_number = scanner.nextLine();

            Main.register_user(this);
            System.out.println("Signup successful");

            this.sem = 1;                               // Students start from 1st Sem.
        }

        return true;
    }

    @Override
    public Student login(String email, String password) {                       // REVIEW THIS AND OTHER LOGINS
        // ONLY CHECK PASSWORD, OBJECT CALL this: STUDENT

        // If user exists, and user has entered the right password
        if (this.password.equals(password)) {
            // Retrieving student data

            System.out.println("Login successful. Student name: " + this.name);
            return this;
        } else {
            System.out.println("Login failed. Incorrect email or password.");
            return null;
        }
    }


    // IMPLEMENT THIS
    public void print_menu() {
        System.out.println("0: Exit");
        System.out.println("1: View all Available Courses");
        System.out.println("2: Register for Courses");
        System.out.println("3: View Schedule");
        System.out.println("4: Track Academic Progress");
        System.out.println("5: Drop Courses");
        System.out.println("6: Submit Complaints");
        System.out.println("7: Submit Feedback");
    }



    // UTILITY FUNCTIONS
    public int computeSGPA(int sem_number) {
        double sum = 0, total_credits = 0;
        for (Course c: course_list.get(sem_number - 1)) {
            if (c.getGrade() == -1) {
                // Grade NOT ASSIGNED YET
                System.out.println("Grade of " + c.getCourse_name() + "Not assigned yet.\n");
                return -1;
            }
            sum += (c.getGrade())*(c.getCredits());
            total_credits += c.getCredits();
        }
        if (SGPA_list.isEmpty()) {
            SGPA_list.add(0.0);
        }
        this.SGPA_list.set(sem_number - 1, sum/total_credits);

        System.out.println("SGPA: " + getSGPA(sem_number));

        return 0;
    }

    public int computeCGPA(int sem_num) {
        // To set all SGPA
        double sum = 0;
        for (int s = 1; s <= sem_num; s++) {
            int test = computeSGPA(s);
            if (test == -1) {                       // Grade not assigned for atleast 1 course
                return -1;
            }
            sum += SGPA_list.get(s - 1);
        }
        this.CGPA = sum/sem_num;

        System.out.println("CGPA: " + this.CGPA);

        return 0;
    }




    // FUNCTIONALITIES

    // Functionality 1: View All Available Courses
    public void view_available_courses() {
        // Prints details of ALL available courses
        for (Course course: Course.available_courses) {
            System.out.println(course);                  // uses Course's toString() method to print details
        }
    }

    // Functionality 2: Register for courses
    public void course_registration() {
        Scanner scanner = new Scanner(System.in);
        int total_credits = 0;
        if (course_list.size() >= sem && !course_list.get(sem - 1).isEmpty()) {   // if courses already exist, then calculate total credits
            for (Course c: course_list.get(sem - 1)) {
                total_credits += c.getCredits();
            }
        }

        System.out.println("current sem: " + this.sem);
        System.out.println("courses available for this sem: ");
        view_available_courses();

        // Register courses until the credit limit of 20 is reached
        while (total_credits < 20) {
            System.out.println("Enter the course code of the course you want to register for: ");
            String cc = scanner.nextLine();

            // If chosen course is already registered, then prompting student to choose a different course
            boolean course_valid = true;
            if (this.course_list.size() >= sem) {
                for (Course course : this.course_list.get(sem - 1)) {
                    if (course.getCourse_code().equals(cc)) {
                        System.out.println("Course already registered in this sem. Choose a different course.");
                        course_valid = false;
                        break;
                    }
                }
                if (!course_valid) {
                    continue;
                }
            }

            // Search for the course
            Course course_to_register = null;
            for (Course course : Course.available_courses) {
                if (course.getCourse_code().equals(cc)) {
                    course_to_register = course;
                    break;
                }
            }

            if (course_to_register == null) {
                System.out.println("Invalid course code. Try again.");
                continue;
            }

            // check if pre_requisites are met
            if (!check_prerequisites(course_to_register)) {
                System.out.println("Pre-requisites for the course not met.");
                continue;
            }

            // Check if credit limit is exceeded
            if (total_credits + course_to_register.getCredits() > 20) {
                System.out.println("You cannot register for this course as credit limit(20) will be exceeded.");
                continue;
            }

            if (!course_to_register.enrollStudent(this)) {
                //Enrollment limit for course reached.
                System.out.println("Enrollment limit for this course has been reached. Register for a different course.");
                continue;
            }

            // Adding the course to course_list of student
            if (this.course_list.size() < sem) {
                this.course_list.add(new ArrayList<>());     // Initialize the semester if not already present
            }
            this.course_list.get(sem - 1).add(course_to_register);                   // adding to current_sem
            total_credits += course_to_register.getCredits();

            System.out.println("Course added successfully. Total credits: " + total_credits);

            if (total_credits < 18) {
                System.out.println("You have to register for atleast 1 more course(total credits < 18)");
            }
        }
        System.out.println("Course registration complete for this sem.");
    }

    // To check if the pre_requisite courses of a course are completed
    private boolean check_prerequisites(Course course) {
        ArrayList<Course> prerequisites = course.getPre_req();

        if (prerequisites == null || prerequisites.isEmpty()) {             // no pre-reqs
            return true;
        }

        for (Course pr : prerequisites) {
            boolean flag = false;
            for (int i = 0; i < sem - 1; i++) {                         // Checking prev sems
                if (course_list.size() > i) {
                    for (Course completedCourse : course_list.get(i)) {
                        if (completedCourse.getCourse_code().equals(pr.getCourse_code()) && completedCourse.getGrade() != -1) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (flag) {
                    break;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    // Checking if a student has passed the current sem
    public boolean sem_complete() {
        if (course_list.size() < sem) {                     // no courses registered for this sem
            return false;
        }

        for (Course course : course_list.get(sem - 1)) {
            if (course.getGrade() == -1) {                      // course not graded
                return false;
            }
        }
        return true;
    }


    // Functionality 3: View Schedule
    public void viewSchedule() {
        // Check if courses are registered
        if (course_list.size() < sem || course_list.get(sem - 1).isEmpty()) {
            System.out.println("No courses have been registered for the current sem. Register courses first");
            return;
        }

        System.out.println("Schedule for Semester " + sem + ":");
        for (Course course : course_list.get(sem - 1)) {
            System.out.println("Timings: " + course.getTimings() + "; Course: " + course.getCourse_name() +
                    "Professor: " + course.getProf() + "; Location: " + course.getLocation());
        }
    }


    // Functionality 4: Track Academic Progress
    public void view_academic_records() {
        // ASSUMPTION: Student is able to view grades, sgpa and cgpa till the PREVIOUS SEM.


        // Print grades
        boolean flag = true;
        if (sem - 1 >= 0 && course_list.size() > sem - 1) {
            for (Course course : this.course_list.get(sem - 2)) {
                if (course.getGrade() == -1) {
                    System.out.println("Grade not assigned for Course: " + course.getCourse_name());
                    flag = false;
                } else {
                    System.out.println("Course: " + course.getCourse_name() + "; Grade: " + course.getGrade());
                }
            }
        }

        // Printing SGPA and CGPA
        if (!flag) {
            System.out.println("SGPA and CGPA not calculated because atleast 1 course has not been graded.");
        }
        else {
            // MODIFY THISSSSSS
            if (sem_complete()) {                       // semester complete: all courses graded
                // Calculating CGPA
                int chala_ya_nahi = this.computeCGPA(sem - 1);
                if (chala_ya_nahi == -1) {
                    return;
                }
            }
            // System.out.println("SGPA for last sem: " + getSGPA(sem - 1));
            System.out.println("CGPA till last sem(last sem passed): " + this.CGPA);
        }
    }


    // Functionality 5: Drop Courses
    public void drop_course() {
        Scanner scanner = new Scanner(System.in);

        // If no course is registered, then can't drop a course
        if (course_list.size() < sem || course_list.get(sem - 1).isEmpty()) {
            System.out.println("No courses registered this sem. Register for courses first.");
            return;
        }

        System.out.println("Enter the course code of the course you want to drop:");
        String cc = scanner.nextLine();

        // Search for course using cc
        Course course_to_drop = null;
        for (Course course : course_list.get(sem - 1)) {
            if (course.getCourse_code().equals(cc)) {
                course_to_drop = course;
                course_list.get(sem - 1).remove(course_to_drop);
                break;
            }
        }

        if (course_to_drop == null) {
            System.out.println("Course not found in your current semester.");
            return;
        }

        System.out.println("Course dropped successfully.");

        // Calculating sem credits. If credits < 18, then student has to register for courses
        int sem_credits = 0;
        for (Course course : course_list.get(sem - 1)) {
            sem_credits += course.getCredits();
        }

        // If total credits are less than 18, prompt the student to register for more courses
        if (sem_credits < 18) {
            System.out.println("Total credits for this semester are below 18. Register for more courses.");
            course_registration();  // Redirect to registerForCourses
        }
    }


    // Functionality 6: Submit Complaints
    public void submit_complaint(String description) {
        if (this.complaints.isEmpty()) {
            this.complaints = new ArrayList<>();
        }
        Complaint complaint = new Complaint(description, this);
        complaints.add(complaint);
        Admin.all_complaints.add(complaint);                // Add complaint to the static list in Admin
        System.out.println("Complaint submitted successfully. Status: Pending");
    }

    // Method for student to view their complaint statuses
    public void view_status_of_complaint() {
        if (complaints.isEmpty()) {
            System.out.println("No complaints submitted.");
        } else {
            for (Complaint complaint : complaints) {
                System.out.println(complaint);
            }
        }
    }


    // Functionality 7: Give feedback
    public void give_feedback() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Course code of the course for which you want to give feedback: ");
        String cc = scanner.nextLine();

        Course wanted_course = null;
        for (int i = 0; i < sem - 1; i++) {           // Allowing courses completed only(not the ones taken in this sem)
            for (Course course : course_list.get(i)) {
                if (course.getCourse_code().equals(cc)) {
                    // course found
                    wanted_course = course;

                    System.out.println("Do you want to enter a rating or some textual feedback for the course?");
                    System.out.println("1: Rating, 2: Textual Feedback.");
                    int choice = scanner.nextInt();
                    if (choice == 1) {
                        System.out.println("Enter the rating for the course: ");
                        Integer feedback = scanner.nextInt();
                        scanner.nextLine();
                        Feedback<Integer> f = new Feedback<>(feedback, wanted_course, this);
                        System.out.println("Rating submitted successfully");
                    }
                    else if (choice == 2) {
                        System.out.println("Enter your feedback(in text form) for the course: ");
                        String feedback = scanner.nextLine();
                        Feedback<String> f = new Feedback<>(feedback, wanted_course, this);
                        System.out.println("Feedback submitted successfully");
                    }
                    else {
                        System.out.println("Invalid feedback choice. Please try again.");
                    }

                    break;
                }
            }
        }

        if (wanted_course == null) {
            System.out.println("Course does not exist, or hasn't been taken by you.");
            return;
        }
    }



    // GETTERS AND SETTERS
    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }

    public double getSGPA(int sem_num) {
        return SGPA_list.get(sem_num - 1);
    }

    public double getCGPA() {
        return CGPA;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<ArrayList<Course>> getCourse_list() {
        return course_list;
    }

    public String getPassword() {
        return password;
    }
}