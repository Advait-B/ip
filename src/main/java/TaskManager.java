public class TaskManager {
    private static final int MAX_TASKS = 100;
    private Task[] tasks;
    private int taskCount;
    private int completedTasks;

    public TaskManager() {
        this.tasks = new Task[MAX_TASKS];
        taskCount = 0;
        completedTasks = 0;
    }

    public void addTask(Task task) throws MangoException {
        if (taskCount >= MAX_TASKS) {
            throw new MangoException("task limit");
        }
        tasks[taskCount] = task;
        taskCount++;
        System.out.println("got it. i've added this task:");
        System.out.println("  " + tasks[taskCount - 1]);
        System.out.println("now you have " + taskCount + " tasks in the list.");
    }

    public void listTasks() {
        if (taskCount == 0) {
            System.out.println("please add some tasks for us to list.");
            Mango.printHelpMessage();
            return;
        }
        if (completedTasks != 0) {
            System.out.println("hooray! you have completed " + completedTasks + " task(s).");
        }
        if (taskCount - completedTasks != 0) {
            System.out.println("oh dear, you have " + (taskCount - completedTasks)
                    + " task(s) you must complete.");
        }
        System.out.println("here's your task list:");
        for (int i = 1; i <= taskCount; i++) {
            System.out.println(i + ". " + tasks[i - 1]);
        }
    }

    public void markTaskAsDone(int index) throws MangoException {
        if (index < 0 || index >= taskCount) {
            throw new MangoException("invalid index");
        }
        if (tasks[index].isDone()) {
            throw new MangoException("already marked");
        }
        tasks[index].markAsDone();
        System.out.println("hooray! you've completed this task. i've marked it as done:");
        System.out.println(tasks[index]);
        completedTasks++;
    }

    public void unmarkTask(int index) throws MangoException {
        if (index < 0 || index >= taskCount) {
            throw new MangoException("invalid index");
        }
        if (!tasks[index].isDone()) {
            throw new MangoException("already unmarked");
        }
        tasks[index].unmark();
        System.out.println("oh dear, it seems you have not completed this task. i've unmarked it:");
        System.out.println(tasks[index]);
        completedTasks--;
    }
}