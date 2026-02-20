package mango.storage;

import mango.task.Deadline;
import mango.task.Event;
import mango.task.Task;
import mango.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String FILE_PATH = "data" + File.separator + "mango.txt";

    /**
     * Loads tasks from the save file, returning an empty list if the file does not exist.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return tasks;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                Task task = parseLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("warning: could not read save file. starting fresh.");
        }
        return tasks;
    }

    /**
     * Saves the given tasks to the save file, overwriting any existing contents.
     */
    public void save(ArrayList<Task> tasks) {
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) {
                writer.write(serialise(task) + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("warning: could not save tasks to disk.");
        }
    }

    /**
     * Returns a string representation of the given task suitable for saving to disk.
     */
    private String serialise(Task task) {
        int done = task.isDone() ? 1 : 0;
        if (task instanceof Deadline) {
            return "D | " + done + " | " + task.getDescription() + " | " + ((Deadline) task).getBy();
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return "E | " + done + " | " + task.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
        } else {
            return "T | " + done + " | " + task.getDescription();
        }
    }

    /**
     * Parses a saved line into a Task, or returns null if the line is invalid.
     */
    private Task parseLine(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }
        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();

        Task task = null;
        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            if (parts.length < 4) {
                return null;
            }
            task = new Deadline(description, parts[3].trim());
            break;
        case "E":
            if (parts.length < 5) {
                return null;
            }
            task = new Event(description, parts[3].trim(), parts[4].trim());
            break;
        default:
            return null;
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}