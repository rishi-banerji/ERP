# Assumptions:
1. A Student should register for all courses of a particular semester at once (at least 18 credits, at most 20).
2. ADMIN assigns grade to student, not professor. (There was a lot of confusion about this on Google Classroom, so I have assumed and implemented this).
3. In the `track academic records` method (inside the `Student` class), I have assumed that the Student can only view grades, SGPA, and CGPA till the previous semester since once all grades are assigned for a semester, the student is automatically promoted to the next semester.
4. No cap(upper limit) on the number of feedbacks by a particular student for a specific course.
5. One TA is assigned to one course.
6. Assignment of TA is done by Admin.
7. TA can ONLY assign a grade to the student if it hasn't been assigned before.

# Running the code
1. In my implementation, before registering for courses, professors with the required domains should be signed up. Then, admins should assign professors to corresponding courses. After that, the student can register for the course.
2. I have hardcoded 5 courses(IP, HCI, DC, LA, COM), without assigning profs to them. The application will run smoothly, with the admin assigning profs to these courses. Other courses can be added too
3. For the demo, 3 students, 2 profs and an admin can be registered too(manually, by running the application).
4. Other than these, the prompts(while taking user input) should probably be sufficient to guide a user throughout the process.

# Note:
1. Admin Password: iiitd. Use this while signing up/logging in admins.


# OOPs Concepts 

## 1. Inheritance
- **Where used**: The `Student`, `Professor`, and `Admin` classes inherit from the `User` class.
- **Functionality**: Common methods like signup and login are defined in the abstract User class, while being implemented in the respective child classes

## 2. Encapsulation
- **Where used**: Almost all attributes of each class are private(Eg: email, password, name, etc).
- **Functionality**: Only controlled access is provided, using getters and setters.

## 3. Polymorphism(Runtime)
This is also called **Method Overriding**
- **Where used**: The `login` method is implemented differently in each subclass (`Student`, `Prof`, `Admin`), though it shares the same name in the `User` class.
- **Functionality**: Allows different behaviors on calling the same method, depending on the class type of the object.

## 4. Abstraction
- **Where used**: The `User` class is an abstract class. It acts as an abstraction.
- **Functionality**: Allows users to perform operations without needing to know the specific implementation details.

## 5. Static Members
- **Where used**: The `all_complaints` list in the `Admin` class is declared static.(among others such as available_courses)
- **Functionality**: Ensures that all admin instances share and access the same list of complaints. If one admin updates the status of a complaint, then the new status would be visible to other admins too.

## 6. Association
- **Where used**: A `Complaint` object is associated with a `Student` object, and a `Course` object is associated with a `Profe` object.
- **Functionality**: This allows us to link students to the complaints submitted by them, improving the functionality of this project. Similarly for courses and profs.

## 7. Composition
- **Where used**: A `Student` object has a list of `Complaint` objects that cannot exist without the student.
- **Functionality**: When a student is deleted, the complaints related to that student are also deleted.

## 8. Interface
- **Where used**: The User class implements the Menu interface. 
- **Functionality**: All the 3 inheritors of the user class(Student, Prof and Admin) can implement the print_menu() method in their own way. This also implements abstraction, which interfaces are used for.

## 9. Usage of Object Superclass
- **Where used and Functionality**: Using the to_String() methods of the Object superclass, the println methods for each class have been modified.