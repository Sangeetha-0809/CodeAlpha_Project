Main.java

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GradeTracker tracker = new GradeTracker();

        while (true) {
            System.out.println("\n--- Student Grade Tracker ---");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade to Student");
            System.out.println("3. Display All Students");
            System.out.println("4. Show Class Statistics");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter student name: ");
                    String name = sc.nextLine();
                    tracker.addStudent(new Student(name));
                    System.out.println("Student added!");
                }
                case 2 -> {
                    System.out.print("Enter student name: ");
                    String name = sc.nextLine();
                    Student s = tracker.findStudent(name);
                    if (s == null) {
                        System.out.println("Student not found!");
                    } else {
                        System.out.print("Enter grade to add (0-100): ");
                        int grade = sc.nextInt();
                        sc.nextLine();
                        s.addGrade(grade);
                        System.out.println("Grade added!");
                    }
                }
                case 3 -> tracker.displayAllStudents();
                case 4 -> {
                    System.out.println("Class Average: " + tracker.getClassAverage());
                    System.out.println("Class Highest Score: " + tracker.getClassHighest());
                    System.out.println("Class Lowest Score: " + tracker.getClassLowest());
                }
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}


Student.java

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
    private String name;
    private ArrayList<Integer> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getGrades() {
        return grades;
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }

    public double getAverage() {
        if (grades.isEmpty()) return 0;
        double sum = 0;
        for (int g : grades) sum += g;
        return sum / grades.size();
    }

    public int getHighest() {
        if (grades.isEmpty()) return 0;
        int max = grades.get(0);
        for (int g : grades) if (g > max) max = g;
        return max;
    }

    public int getLowest() {
        if (grades.isEmpty()) return 0;
        int min = grades.get(0);
        for (int g : grades) if (g < min) min = g;
        return min;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", grades=" + grades +
                ", average=" + getAverage() +
                ", highest=" + getHighest() +
                ", lowest=" + getLowest() +
                '}';
    }
}


GradeTracker.java

import java.util.ArrayList;

public class GradeTracker {
    private ArrayList<Student> students;

    public GradeTracker() {
        students = new ArrayList<>();
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public Student findStudent(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("\n--- Student Summary Report ---");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    public double getClassAverage() {
        if (students.isEmpty()) return 0;
        double total = 0;
        int count = 0;
        for (Student s : students) {
            for (int g : s.getGrades()) {
                total += g;
                count++;
            }
        }
        return count == 0 ? 0 : total / count;
    }

    public int getClassHighest() {
        int max = Integer.MIN_VALUE;
        for (Student s : students) {
            int studentMax = s.getHighest();
            if (studentMax > max) max = studentMax;
        }
        return max == Integer.MIN_VALUE ? 0 : max;
    }

    public int getClassLowest() {
        int min = Integer.MAX_VALUE;
        for (Student s : students) {
            int studentMin = s.getLowest();
            if (studentMin < min) min = studentMin;
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }
}
