import java.util.ArrayList;

public class Course {
    private String course_name, location, course_code, domain; // location: room in which class will be held
    private Prof prof;
    private int credits, enrollment_limit, grade = -1;                       // Taking grade not assigned yet to be -1
    private String syllabus, office_hours;
    private String timings;                             // stores timings of a course
    private ArrayList<Course> pre_req;               // pre-requisites for the course

    private ArrayList<Student> enrolled_students = new ArrayList<>();

    public static ArrayList<Course> available_courses = new ArrayList<>();

    // ASSUMPTION: ANY COURSE whose PRE-REQUISITES have been COMPLETED by a student in a previous sem, can be taken
    // by the student in the current sem.


    // Constructors
    public Course() {
        this.course_name = null;
        this.prof = null;
        this.location = null;
        this.course_code = null;
        this.domain = null;
        this.credits = 4;                                           // let credits be 4 by default
        this.timings = null;
        this.pre_req = new ArrayList<>();
        this.syllabus = null;
        this.office_hours = null;
        this.enrollment_limit = 50;
        this.enrolled_students = new ArrayList<>();
    }

    public Course(String course_name, Prof prof, String location, String course_code, String domain,
                  int credits, String timings, ArrayList<Course> pre_req, String syllabus, String office_hours,
                  int enrollment_limit) {
        // Before inputing PRE-REQUISITES, create arraylist(by using new Arraylist<>()), insert courses, etc

        this.course_name = course_name;
        this.prof = prof;
        this.location = location;
        this.course_code = course_code;
        this.domain = domain;
        this.credits = credits;
        this.timings = timings;
        this.pre_req = pre_req;
        this.enrollment_limit = enrollment_limit;
        this.syllabus = syllabus;
        this.office_hours = office_hours;
        this.enrolled_students = new ArrayList<>();
    }

    public Course(String course_name, String location, String course_code, String domain,
                  int credits, String timings, ArrayList<Course> pre_req, String syllabus, String office_hours,
                  int enrollment_limit) {
        // Before inputing PRE-REQUISITES, create arraylist(by using new Arraylist<>()), insert courses, etc

        this.course_name = course_name;
        this.location = location;
        this.course_code = course_code;
        this.domain = domain;
        this.credits = credits;
        this.timings = timings;
        this.pre_req = pre_req;
        this.prof = null;
        this.enrollment_limit = enrollment_limit;
        this.syllabus = syllabus;
        this.office_hours = office_hours;
        this.enrolled_students = new ArrayList<>();
    }


    @Override
    public String toString() {                          // Method of OBJECT SUPERCLASS
        this.print_preReq();
        if (this.prof != null) {
            return "Course Name: " + course_name + ", Professor: " + prof.getName() + ", Location: " + location
                    + ", Course Code: " + course_code + ", Credits: " + credits + ", Timings: " + timings;
        }
        else {
            return "Course Name: " + course_name + ", Location: " + location + ", Course Code: " + course_code +
                    ", Credits: " + credits + ", Timings: " + timings;
        }
    }

    public boolean enrollStudent(Student student) {
        if (enrolled_students.size() < enrollment_limit) {
            enrolled_students.add(student);
            return true;
        } else {
            System.out.println("Enrollment limit reached. Cannot enroll more students.");
            return false;
        }
    }



    // Getters and Setters
    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Prof getProf() {
        return prof;
    }

    public void setProf(Prof prof) {
        this.prof = prof;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public int getGrade() {
        return grade;
    }

    // ASSUMPTION: Grades for ALL COURSES in a SEMESTER are set at once.
    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void print_preReq() {
        System.out.print("Pre-requisites: ");
        for (Course c: pre_req) {
            System.out.print(c.getCourse_name() + ",");
        }
    }

    public ArrayList<Course> getPre_req() {
        return pre_req;
    }

    public void setPre_req(ArrayList<Course> pre_req) {
        this.pre_req = pre_req;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getOffice_hours() {
        return office_hours;
    }

    public void setOffice_hours(String office_hours) {
        this.office_hours = office_hours;
    }

    public int getEnrollment_limit() {
        return enrollment_limit;
    }

    public void setEnrollment_limit(int enrollment_limit) {
        this.enrollment_limit = enrollment_limit;
    }

    public ArrayList<Student> getEnrolledStudents() {
        return enrolled_students;
    }
}