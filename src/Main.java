import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // HARDCODE 5 courses(and maybe 3 students, 2 profs, 1 admin)
        ArrayList<Course> pr = new ArrayList<>();
        Course IP = new Course("Intro to Programming", "C101", "CSE101", "CSE", 4, "10-11am", pr, "c", "6-7pm", 300);
        Course LA = new Course("Linear Algebra", "C201", "MTH101", "MTH", 4, "3-4pm", pr, "m", "7-8pm", 300);
        Course DC = new Course("Digital Circuits", "C101", "ECE101", "ECE", 4, "9-10am", pr, "e", "5-6pm", 300);
        Course HCI = new Course("Human Computer Interaction", "C201", "HCD101", "HCD", 4, "4-5pm", pr, "d", "2-3pm", 200);
        Course COM = new Course("Communication Skills", "C102", "SSH101", "SSH", 4, "1-2pm", pr, "s", "8-9am", 300);

        // ASSIGN PROF TO COURSES SEPARATELY(ADMIN)
        // DO NOT HARDCODE IT

        Course.available_courses.add(IP);
        Course.available_courses.add(DC);
        Course.available_courses.add(HCI);
        Course.available_courses.add(LA);
        Course.available_courses.add(COM);





        boolean flag = true;
        while (flag) {
            System.out.println("do you want to login/signup from an account, or exit the application?");
            System.out.println("Enter 1 for login/signup, and 2 for exiting");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1:
                    // registration(login/sign-up) of user
                    User current_user = Main.registration(scanner);

                    if (current_user == null) {
                        System.out.println("Login or sign-up failed. Try again");
                        continue;
                    }

                    System.out.println("------------------------------------------MENU----------------------------------------------");


                    // When User is STUDENT
                    if (current_user.role().equals("Student")) {
                        Student current_student = (Student) current_user;
                        while (true) {
                            current_student.print_menu();

                            System.out.println("Which operation do you want to perform? Enter the number corresponding to the operation: ");
                            int option1 = scanner.nextInt();

                            boolean flag1 = true;
                            switch (option1) {
                                // NOTE: ADD BREAK STATEMENT AT THE END OF EACH SWITCH CASE

                                case 0:                                     // Exit
                                    flag1 = false;
                                    System.out.println();
                                    break;

                                case 1:                                     // view courses
                                    current_student.view_available_courses();
                                    System.out.println();
                                    break;

                                case 2:                                     // register for courses
                                    current_student.course_registration();
                                    System.out.println();
                                    break;

                                case 3:
                                    current_student.viewSchedule();
                                    System.out.println();
                                    break;

                                case 4:
                                    current_student.view_academic_records();
                                    System.out.println();
                                    break;

                                case 5:
                                    current_student.dropCourse();
                                    System.out.println();
                                    break;

                                case 6:
                                    // implement SUBMIT COMPLAINT functionality
                                    System.out.println("Do you want to submit a complaint, or view the statuses of your submitted complaints?");
                                    System.out.println("1: Submit complaint, 2: View status");
                                    int input = scanner.nextInt();
                                    scanner.nextLine();

                                    if (input == 1) {
                                        System.out.println("Describe your complaint: ");
                                        String description = scanner.nextLine();

                                        current_student.submit_complaint(description);
                                    }
                                    else if (input == 2) {
                                        current_student.view_status_ofComplaint();
                                    }
                                    else {
                                        System.out.println("Enter a valid choice.");
                                    }

                                    System.out.println();
                                    break;

                                default:
                                    System.out.println("Enter valid choice");
                            }

                            if (!flag1) {                           // User chose to exit from logged in account
                                break;
                            }
                        }
                    }
                    // When User is PROF
                    else if (current_user.role().equals("Prof")) {
                        Prof current_prof = (Prof) current_user;
                        while (true) {
                            current_prof.print_menu();

                            System.out.println("Which operation do you want to perform? Enter a number according to the operation: ");
                            int option2 = scanner.nextInt();

                            boolean flag2 = true;
                            switch (option2) {
                                // implement PROF functionalities flow
                                case 0:
                                    flag2 = false;
                                    System.out.println();
                                    break;

                                case 1:
                                    current_prof.manage_course();
                                    System.out.println();
                                    break;

                                case 2:
                                    current_prof.view_enrolled_students();
                                    System.out.println();
                                    break;

                                default:
                                    System.out.println("Enter a valid choice.");
                            }
                            if (!flag2) {                               // Prof chose to exit
                                break;
                            }
                        }
                    }
                    // When User is ADMIN
                    else if (current_user.role().equals("Admin")) {
                        Admin current_admin = (Admin) current_user;
                        while (true) {
                            current_admin.print_menu();

                            System.out.println("Which operation do you want to perform? Enter a number according to the operation: ");
                            int option3 = scanner.nextInt();
                            scanner.nextLine();

                            boolean flag3 = true;
                            switch (option3) {
                                case 0:
                                    flag3 = false;
                                    System.out.println();
                                    break;

                                case 1:
                                    System.out.println("Which operation do you want to perform? Enter a number according to the operation: ");
                                    System.out.println("1: View Courses, 2: Add course, 3: Delete Course");
                                    int o1 = scanner.nextInt();

                                    if (o1 == 1) {
                                        current_admin.viewCourses();
                                    }
                                    else if (o1 == 2) {
                                        current_admin.add_course();
                                    }
                                    else if (o1 == 3) {
                                        current_admin.delete_course();
                                    }
                                    System.out.println();
                                    break;

                                case 2:
                                    System.out.println("Which operation do you want to perform? Enter a number according to the operation: ");
                                    System.out.println("1: View Student Details, 2: Update Student Details");
                                    int o2 = scanner.nextInt();

                                    if (o2 == 1) {
                                        current_admin.print_student_details();
                                    }
                                    else if (o2 == 2) {
                                        current_admin.update_student();
                                    }
                                    else {
                                        System.out.println("Enter a valid operation.");
                                    }
                                    System.out.println();
                                    break;

                                case 3:
                                    System.out.println("Enter the course code of the course to which you want to assign a Professor: ");
                                    String cc = scanner.nextLine();

                                    Course course_for_prof = null;
                                    for (Course course: Course.available_courses) {
                                        if (course.getCourse_code().equals(cc)) {
                                            course_for_prof = course;
                                        }
                                    }

                                    if (course_for_prof == null) {
                                        // course not found
                                        System.out.println("Enter a valid course code");
                                    }
                                    else {
                                        current_admin.prof_to_course(course_for_prof);
                                    }
                                    System.out.println();
                                    break;

                                case 4:
                                    System.out.println("Which operation do you want to perform? Enter a number according to the operation: ");
                                    System.out.println("1: View All submitted complaints, 2: Update the status of a complaint" +
                                            "3: Filter submitted complaints according to status");
                                    int o3 = scanner.nextInt();
                                    scanner.nextLine();

                                    switch (o3) {
                                        case 1:
                                            current_admin.view_all_complaints();
                                            System.out.println();
                                            break;

                                        case 2:
                                            System.out.println("Enter the roll number of the student: ");
                                            String rn = scanner.nextLine();

                                            System.out.println("Enter the new status of the complaint: Resolved or Pending");
                                            String new_status = scanner.nextLine();

                                            current_admin.update_complaint_status(rn, new_status);

                                            System.out.println();
                                            break;

                                        case 3:
                                            System.out.println("Complaints should be filtered by which status(Resolved or Pending)? ");
                                            String chosen_status = scanner.nextLine();

                                            current_admin.filter_complaints(chosen_status);

                                            System.out.println();
                                            break;

                                        default:
                                            System.out.println("Enter a valid operation");
                                    }

                                    System.out.println();
                                    break;

                                default:
                                    System.out.println("Enter a valid choice");
                            }

                            if (!flag3) {                               // admin chose to exit
                                break;
                            }

                        }
                    }
                    break;

                case 2:
                    System.out.println("application exited successfully");
                    flag = false;
                    break;
            }
        }
    }


    // To check whether an email is already registered
    public static boolean does_user_exist(String email) {
        for (User user: users) {
            if (user instanceof Student && ((Student)user).getEmail().equals(email) ||
                user instanceof Prof && ((Prof)user).getEmail().equals(email) ||
                user instanceof Admin && ((Admin)user).getEmail().equals(email))
            {
                return true;
            }
        }
        return false;
    }

    // To register a user
    public static void register_user(User user) {
        users.add(user);
    }

    public static User loginUser(String email, String password) {
        for (User user : users) {
            // Checking if user exists; didn't want to write this 3 times, so wrote it as a common func
            if ((user instanceof Student && ((Student) user).getEmail().equals(email)) ) {
                // Check password
                Student existing_student = (Student) user;
                Student r = existing_student.login(email, password);
                if (r != null) {
                    System.out.println("Login successful");
                    return r;
                }
            }
            else if ((user instanceof Prof && ((Prof) user).getEmail().equals(email)) ) {
                    // Check password
                Prof existing_prof = (Prof) user;
                Prof t = existing_prof.login(email, password);
                if (t != null) {
                    System.out.println("Login successful");
                    return t;
                }
            }
            else if ((user instanceof Admin && ((Admin) user).getEmail().equals(email)) ) {
                    // Check password
                Admin existing_admin = (Admin) user;
                Admin u = existing_admin.login(email, password);
                if (u != null) {
                    System.out.println("Login successful");
                    return u;
                }
            }
        }
        System.out.println("No user found with that email.");
        return null;
    }


    // Takes user input to login/signup users
    public static User registration(Scanner scanner) {
        User user = new Prof();    // initializing to prof just because user needs initialization
        boolean flag = true;
        while (flag) {

            // Determining role of user
            System.out.println("Enter the role of the user. (1 for Student, 2 for Prof, 3 for Admin)");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    user = new Student();               // calling constructor of Student class
                    flag = false;
                    break;
                case 2:
                    user = new Prof();                  // calling constructor of Prof class
                    flag = false;
                    break;
                case 3:
                    user = new Admin();                 // calling constructor of Admin class
                    flag = false;
                    break;
                default:
                    System.out.println("Wrong value entered. Choose between 1, 2 and 3");
                    continue;
            }
        }

        // Taking email and password input
        String email, password;
        System.out.println("Enter email: ");
        email = scanner.nextLine();
        System.out.println("Enter password: ");
        password = scanner.nextLine();

        // Determining whether user wants to Login or Signup
        System.out.println("Do you want to login or sign-up? Enter l for login, s for signup, any other key to not continue: ");
        String command = scanner.nextLine();

        boolean val = true;
        if (command.equalsIgnoreCase("s")) {            // login
            val = user.sign_up(email, password);
        } else if (command.equalsIgnoreCase("l")) {
            user = loginUser(email, password);
        } else {
            System.out.println("User not logged in or signed up.");
        }


        if (!val) {
            return null;
        }


        if (user instanceof Student) {
            return (Student) user;
        } else if (user instanceof Prof) {
            return (Prof) user;
        } else if (user instanceof Admin) {
            return (Admin) user;
        }
        return user;

    }
}