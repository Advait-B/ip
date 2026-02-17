package mango.task;

import mango.parser.MangoException;
import java.util.ArrayList;

public class TaskManager {
    private static final int MAX_TASKS = 100;
    protected ArrayList<Task> tasks;
    protected int completedTasks;

    private Task getValidatedTask(int index) throws MangoException {
        if (index < 0 || index >= tasks.size()) {
            throw new MangoException("invalid index");
        }
        return tasks.get(index);
    }

    public TaskManager() {
        tasks = new ArrayList<>();
        completedTasks = 0;
    }

    public int incompleteTasks() {
        return tasks.size() - completedTasks;
    }

    public void addTask(Task task) throws MangoException {
        if (tasks.size() >= MAX_TASKS) {
            throw new MangoException("task limit");
        }
        tasks.add(task);
        System.out.println("got it. i've added this task:");
        System.out.println("  " + task);
        System.out.println("now you have " + tasks.size() + " tasks in the list.");
    }

    public void deleteTask(int index) throws MangoException {
        Task removed = getValidatedTask(index);
        tasks.remove(index);
        if (removed.isDone()) {
            completedTasks--;
        }
        System.out.println("i've successfully removed this task: " + removed);
        int tasksDue = incompleteTasks();
        if (tasksDue != 0) {
            System.out.println("you now have " + tasksDue + " tasks left to complete - good luck!");
        } else {
            System.out.println("hooray! you've completed all your tasks :)");
        }
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("please add some tasks for us to list.");
            return;
        }
        if (completedTasks != 0) {
            System.out.println("hooray! you have completed " + completedTasks + " task(s).");
        }
        int tasksDue = incompleteTasks();
        if (tasksDue != 0) {
            System.out.println("oh dear, you have " + (tasksDue)
                    + " task(s) you must complete.");
        }
        System.out.println("here's your task list:");
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.println(i + ". " + tasks.get(i - 1));
        }
    }

    public void markTaskAsDone(int index) throws MangoException {
        Task task = getValidatedTask(index);
        if (task.isDone()) {
            throw new MangoException("already marked");
        }
        task.markAsDone();
        System.out.println("hooray! you've completed this task. i've marked it as done:");
        System.out.println(task);
        completedTasks++;
    }

    public void unmarkTask(int index) throws MangoException {
        Task task = getValidatedTask(index);
        if (!task.isDone()) {
            throw new MangoException("already unmarked");
        }
        task.unmark();
        System.out.println("oh dear, it seems you have not completed this task. i've unmarked it:");
        System.out.println(task);
        completedTasks--;
    }
}
