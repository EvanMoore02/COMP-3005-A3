import java.sql.*;
import java.util.Scanner;

public class main{
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/UniversityDB";
        String user = "postgres";
        String password = "admin";
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            Scanner scanner = new Scanner(System.in);

            // Print All students
            getAllStudents(statement);

            // Add a student
            System.out.println("Enter The Student's first name:");
            String firstName = scanner.nextLine();
            System.out.println("Enter The Student's last name:");
            String lastName = scanner.nextLine();
            System.out.println("Enter The Student's email address:");
            String email = scanner.nextLine();
            System.out.println("Enter The Student's enrollment date (YYYY-MM-DD):");
            String enrollmentDate = scanner.nextLine();
            addStudent(statement, firstName, lastName, email, enrollmentDate);

            // Update student email
            System.out.println("Enter the student id of the student you would like to update:");
            String studentId = scanner.nextLine();
            System.out.println("Enter the new email address for the student:");
            String newEmail = scanner.nextLine();
            updateStudentEmail(statement, studentId, newEmail);

            // Delete a student
            System.out.println("Enter the student id of the student you would like to delete:");
            studentId = scanner.nextLine();
            deleteStudent(statement, studentId);

            statement.close();
            connection.close();
            scanner.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void getAllStudents(Statement statement) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM students");
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + " \t");
            }
            System.out.println();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i) + " \t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStudent(Statement statement, String firstName, String lastName, String email, String enrollmentDate) {
        try {
            String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES " +
                    "('" + firstName + "', '" + lastName + "', '" + email + "', '" + enrollmentDate + "')";
            int rowsInserted = statement.executeUpdate(insertSQL);
            if (rowsInserted > 0) {
                System.out.println("A new student was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudentEmail(Statement statement, String studentId, String newEmail) {
        try {
            String updateSQL = "UPDATE students SET email = '" + newEmail + "' WHERE student_id = '" + studentId + "'";
            int rowsUpdated = statement.executeUpdate(updateSQL);
            if (rowsUpdated > 0) {
                System.out.println("Student email was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(Statement statement, String studentId) {
        try {
            String deleteSQL = "DELETE FROM students WHERE student_id = '" + studentId + "'";
            int rowsDeleted = statement.executeUpdate(deleteSQL);
            if (rowsDeleted > 0) {
                System.out.println("Student was deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
