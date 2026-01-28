public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }
    public String getDescription() {
        return description;
    }
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
    public void markAsDone() {
        this.isDone = true;
        System.out.println("hooray! you've completed this task. i've marked it as done: ");
        System.out.println(this);
    }
    public void unmark() {
        this.isDone = false;
        System.out.println("oh dear, it seems you have NOT completed this task. i've unmarked it: ");
        System.out.println(this);
    }
}
