import java.io.*;
import java.util.*;

public class TodoApp {
    private static final String FILE_NAME = "tasks.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nTodo App");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Complete");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task: ");
                    String task = scanner.nextLine();
                    addTask(task);
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    System.out.print("Enter task number to mark complete: ");
                    int completeIndex = scanner.nextInt();
                    markComplete(completeIndex);
                    break;
                case 4:
                    System.out.print("Enter task number to delete: ");
                    int deleteIndex = scanner.nextInt();
                    deleteTask(deleteIndex);
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addTask(String task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(task + ",Pending\n");
            System.out.println("Task added successfully.");
        } catch (IOException e) {
            System.out.println("Error adding task: " + e.getMessage());
        }
    }

    private static void viewTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int index = 1;
            System.out.println("\nYour Tasks:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.printf("%d. %s [%s]\n", index++, parts[0], parts[1]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No tasks found. Start by adding a task.");
        } catch (IOException e) {
            System.out.println("Error reading tasks: " + e.getMessage());
        }
    }

    private static void markComplete(int taskNumber) {
        try {
            List<String> tasks = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    tasks.add(line);
                }
            }

            if (taskNumber <= 0 || taskNumber > tasks.size()) {
                System.out.println("Invalid task number.");
                return;
            }

            String task = tasks.get(taskNumber - 1);
            tasks.set(taskNumber - 1, task.replace("Pending", "Completed"));

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (String t : tasks) {
                    writer.write(t + "\n");
                }
            }

            System.out.println("Task marked as completed.");
        } catch (IOException e) {
            System.out.println("Error updating task: " + e.getMessage());
        }
    }

    private static void deleteTask(int taskNumber) {
        try {
            List<String> tasks = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    tasks.add(line);
                }
            }

            if (taskNumber <= 0 || taskNumber > tasks.size()) {
                System.out.println("Invalid task number.");
                return;
            }

            tasks.remove(taskNumber - 1);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (String t : tasks) {
                    writer.write(t + "\n");
                }
            }

            System.out.println("Task deleted successfully.");
        } catch (IOException e) {
            System.out.println("Error deleting task: " + e.getMessage());
        }
    }
}
