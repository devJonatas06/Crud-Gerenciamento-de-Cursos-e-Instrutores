package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.AppDao;
import com.luv2code.cruddemo.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CruddemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CruddemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AppDao appDAO) {
        return runner -> {
            Scanner scanner = new Scanner(System.in);
            int option = -1;

            while (option != 0) {
                System.out.println("//MENU//");
                System.out.println(" 1 - Create Instructor");
                System.out.println(" 2 - Create Instructor With Courses");
                System.out.println(" 3 - Delete Instructor");
                System.out.println(" 4 - Delete Course");
                System.out.println(" 5 - Delete Course and Reviews");
                System.out.println(" 6 - Find Instructor Detail");
                System.out.println(" 7 - Delete Instructor Detail");
                System.out.println(" 8 - Find Instructor");
                System.out.println(" 9 - Delete Student");
                System.out.println("10 - Find Instructor With Courses");
                System.out.println("11 - Find Courses By Instructor ID");
                System.out.println("12 - Update Instructor");
                System.out.println("13 - Update Course Title");
                System.out.println("14 - Create Course and Reviews");
                System.out.println("15 - Find Course And Reviews By Course ID");
                System.out.println("16 - Create Course and Students");
                System.out.println("17 - Find Courses And Student By Course Id");
                System.out.println("18 - Find Student And Courses By Student Id");
                System.out.println("19 - Add More Courses For Student");
                System.out.println(" 0 - Exit");


                option = scanner.nextInt();
                scanner.nextLine(); // clear buffer

                switch (option) {
                    case 1 -> createInstructor(appDAO, scanner);
                    case 2 -> createInstructorWithCourses(appDAO, scanner);
                    case 3 -> deleteInstructor(appDAO, scanner);
                    case 4 -> deleteCourse(appDAO, scanner);
                    case 5 -> deleteCourseAndReviews(appDAO, scanner);
                    case 6 -> findInstructorDetail(appDAO, scanner);
                    case 7 -> deleteInstructorDetail(appDAO, scanner);
                    case 8 -> findInstructor(appDAO, scanner);
                    case 9 -> deleteStudent(appDAO, scanner);
                    case 10 -> findInstructorWithCoursesJoinFetch(appDAO, scanner);
                    case 11 -> findCoursesByInstructorId(appDAO, scanner);
                    case 12 -> updateInstructor(appDAO, scanner);
                    case 13 -> updateCourse(appDAO, scanner);
                    case 14 -> createCourseAndReviews(appDAO, scanner);
                    case 15 -> findCourseAndReviewsByCourseId(appDAO, scanner);
                    case 16 -> createCourseAndStudents(appDAO, scanner);
                    case 17 -> findCourseAndStudent(appDAO, scanner);
                    case 18 -> findStudentAndCurses(appDAO, scanner); // "Curses" deve ser "Courses"
                    case 19 -> addmoreCoursesForStudent(appDAO, scanner);
                    case 0 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid option.");
                }
            }

            scanner.close();
        };
    }

    private void deleteStudent(AppDao appDAO, Scanner scanner) {
        int theId = readInt(scanner, "Enter the Student ID to delete: ");
        appDAO.deleteStudent(theId);
        System.out.println("Student deleted.");
    }


    private void addmoreCoursesForStudent(AppDao appDAO, Scanner scanner) {
        System.out.println("Type student ID to add more courses:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consome a nova linha pendente

        Student student = appDAO.findStudentAndCoursesByStudentId(id);

        System.out.print("Enter course title: ");
        String title = scanner.nextLine();  // Agora vai funcionar corretamente

        Course course = new Course(title);
        student.addCourse(course);

        System.out.println("Updating student: " + student);
        System.out.println("Associated courses: " + student.getCourses());

        appDAO.update(student);
        System.out.println("Done!!");
    }


    private void findStudentAndCurses(AppDao appDAO, Scanner scanner) {
        System.out.println("Type Student Id For View The Students and Courses ");
        int theId = scanner.nextInt();
        Student student = appDAO.findStudentAndCoursesByStudentId(theId);
        System.out.println("Loaded Student: " + student);
        System.out.println("courses: "+ student.getCourses());
        System.out.println("Done!!");
    }

    private void findCourseAndStudent(AppDao appDAO, Scanner scanner) {
        System.out.println("Type Course Id For View the courses and Student");
        int theId = scanner.nextInt();
        Course course = appDAO.findCourseAndStudentByCourseId(theId);
        System.out.println("Course: " + course.getTitle());
        System.out.println("Students enrolled:");
        course.getStudents().forEach(student ->
                System.out.println("- " + student.getFirstName() + " " + student.getLastName()));
    }

    private void createCourseAndStudents(AppDao appDAO, Scanner scanner) {
        System.out.print("Enter course title: ");
        String title = scanner.nextLine();
        Course course = new Course(title);
        System.out.print("How many Students do you want to add? ");
        int numberOfStudents = readInt(scanner, "");

        for (int i = 1; i <= numberOfStudents; i++) {
            System.out.print("Enter FirstName Student " + i + ": ");
            String firstName = scanner.nextLine();
            System.out.print("Enter LastName Student " + i + ": ");
            String lastName = scanner.nextLine();
            System.out.print("Enter email Student " + i + ": ");
            String email = scanner.nextLine();
            Student student = new Student(firstName,lastName,email);
            course.addStudent(student);
        }
        appDAO.save(course);
        System.out.println("Perfect!");
    }

    private void deleteCourseAndReviews(AppDao appDAO, Scanner scanner) {
        System.out.println("Enter id for delete");
        int theid = scanner.nextInt();
        System.out.println("Course deleted");
        appDAO.deleteCourseById(theid);
    }

    private void findCourseAndReviewsByCourseId(AppDao appDAO, Scanner scanner) {
        System.out.println("Type Course Id For View The Reviews");
        int theId = scanner.nextInt();
        Course course = appDAO.findCourseAndReviewsByCourseId(theId);
        System.out.println(course);
        System.out.println("Reviews: "+course.getReviews());
    }

    private void createCourseAndReviews(AppDao appDAO, Scanner scanner) {
        System.out.print("Enter course title: ");
        String title = scanner.nextLine();
        Course course = new Course(title);

        System.out.print("How many reviews do you want to add? ");
        int numberOfReviews = readInt(scanner, "");

        for (int i = 1; i <= numberOfReviews; i++) {
            System.out.print("Enter comment for review " + i + ": ");
            String comment = scanner.nextLine();
            Review review = new Review(comment);
            course.addReview(review);
        }

        appDAO.save(course); // método para salvar curso com reviews em cascata
        System.out.println("Course and reviews saved successfully!");
    }

    private void deleteCourse(AppDao appDAO, Scanner scanner) {
        int theId = readInt(scanner, "Enter the Course ID to delete: ");
        appDAO.deleteCourseById(theId);
        System.out.println("Course deleted.");
    }

    private void updateCourse(AppDao appDAO, Scanner scanner) {
        int theId = readInt(scanner, "Enter the Course ID to update: ");
        System.out.println("Finding Course with ID: " + theId);
        Course course = appDAO.findCoursesById(theId);
        System.out.print("Enter the new title: ");
        String newName = scanner.nextLine();
        course.setTitle(newName);
        appDAO.updateCourse(course);
        System.out.println("Course title updated successfully!");
    }

    private void updateInstructor(AppDao appDAO, Scanner scanner) {
        int theId = readInt(scanner, "Enter the Instructor ID to update: ");
        System.out.println("Finding Instructor with ID: " + theId);
        Instructor instructor = appDAO.findInstructorById(theId);

        int choice = -1;

        while (choice != 0) {
            System.out.println("1 - Update First Name");
            System.out.println("2 - Update Last Name");
            System.out.println("3 - Update First and Last Name");
            System.out.println("0 - Go back");

            choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            switch (choice) {
                case 1 -> updateFirstName(appDAO, scanner, instructor);
                case 2 -> updateLastName(appDAO, scanner, instructor);
                case 3 -> updateFirstAndLastName(appDAO, scanner, instructor);
                case 0 -> System.out.println("Returning...");
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void updateFirstName(AppDao appDAO, Scanner scanner, Instructor instructor) {
        System.out.print("Enter new first name: ");
        String newFirstName = scanner.nextLine();
        instructor.setFirstName(newFirstName);
        appDAO.updateInstructor(instructor);
        System.out.println("First name updated successfully!");
    }

    private void updateLastName(AppDao appDAO, Scanner scanner, Instructor instructor) {
        System.out.print("Enter new last name: ");
        String newLastName = scanner.nextLine();
        instructor.setLastName(newLastName);
        appDAO.updateInstructor(instructor);
        System.out.println("Last name updated successfully!");
    }

    private void updateFirstAndLastName(AppDao appDAO, Scanner scanner, Instructor instructor) {
        System.out.print("Enter new first name: ");
        String newFirstName = scanner.nextLine();
        System.out.print("Enter new last name: ");
        String newLastName = scanner.nextLine();
        instructor.setFirstName(newFirstName);
        instructor.setLastName(newLastName);
        appDAO.updateInstructor(instructor);
        System.out.println("First and last name updated successfully!");
    }

    private void findCoursesByInstructorId(AppDao appDAO, Scanner scanner) {
        int theId = readInt(scanner, "Enter the Instructor ID: ");
        Instructor instructor = appDAO.findInstructorById(theId);
        System.out.println("Instructor: " + instructor);
        System.out.println("Finding courses associated with Instructor ID: " + theId);
        List<Course> courses = appDAO.findCoursesByInstructorId(theId);
        instructor.setCourses(courses);
        System.out.println("Associated courses: " + instructor.getCourses());
        System.out.println("Done!");
    }

    private void findInstructorWithCoursesJoinFetch(AppDao appDAO, Scanner scanner) {
        int theId = readInt(scanner, "Enter the Instructor ID: ");
        System.out.println("Finding Instructor with ID: " + theId);
        Instructor instructor = appDAO.findInstructorByIdJoinFetch(theId);
        System.out.println("Instructor: " + instructor);
        System.out.println("Associated courses: " + instructor.getCourses());
        System.out.println("Done!");
    }

    private void createInstructorWithCourses(AppDao appDAO, Scanner scanner) {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("YouTube Channel: ");
        String youtubeChannel = scanner.nextLine();

        System.out.print("Hobby: ");
        String hobby = scanner.nextLine();

        Instructor instructor = new Instructor(firstName, lastName, email);
        InstructorDetail detail = new InstructorDetail(youtubeChannel, hobby);
        instructor.setInstructorDetail(detail);

        System.out.print("Course Title: ");
        Course course1 = new Course(scanner.nextLine());
        System.out.print("Course Title: ");
        Course course2 = new Course(scanner.nextLine());

        instructor.add(course1);
        instructor.add(course2);

        System.out.println("Instructor with courses successfully saved: " + instructor);
        System.out.println("Courses: " + instructor.getCourses());

        appDAO.save(instructor);
    }

    private void createInstructor(AppDao appDAO, Scanner scanner) {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("YouTube Channel: ");
        String youtubeChannel = scanner.nextLine();

        System.out.print("Hobby: ");
        String hobby = scanner.nextLine();

        Instructor instructor = new Instructor(firstName, lastName, email);
        InstructorDetail detail = new InstructorDetail(youtubeChannel, hobby);
        instructor.setInstructorDetail(detail);

        appDAO.save(instructor);
        System.out.println("Instructor successfully saved!");
    }

    private void findInstructor(AppDao appDAO, Scanner scanner) {
        int theId = readInt(scanner, "Enter the Instructor ID: ");
        Instructor instructor = appDAO.findInstructorById(theId);
        if (instructor != null) {
            System.out.println("Instructor found: " + instructor);
            System.out.println("Details: " + instructor.getInstructorDetail());
        } else {
            System.out.println("Instructor not found.");
        }
    }

    private void deleteInstructor(AppDao appDAO, Scanner scanner) {
        int theId = readInt(scanner, "Enter the Instructor ID to delete: ");
        appDAO.deleteInstructorById(theId);
        System.out.println("Instructor deleted.");
    }

    private void findInstructorDetail(AppDao appDAO, Scanner scanner) {
        int theId = readInt(scanner, "Enter the Instructor Detail ID: ");
        InstructorDetail detail = appDAO.findInstructorDetailById(theId);
        if (detail != null) {
            System.out.println("Detail found: " + detail);
            System.out.println("Associated instructor: " + detail.getInstructor());
        } else {
            System.out.println("Detail not found.");
        }
    }

    private void deleteInstructorDetail(AppDao appDAO, Scanner scanner) {
        int theId = readInt(scanner, "Enter the Instructor Detail ID to delete: ");
        appDAO.deleteInstructorDetailById(theId);
        System.out.println("Detail deleted.");
    }

    private int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer:");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // clear buffer
        return value;
    }
}
