package utcn.pt.dataModel;

import java.util.ArrayList;
import java.util.List;

public non-sealed class ComplexTask extends Task {
    private List<Task> tasks;

    public ComplexTask(int idTask,String statusTask) {
        super(idTask,statusTask);
        this.tasks = new ArrayList<Task>();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        if (task != null) {
            this.tasks.add(task);
        }
    }

    @Override
    public int estimateDuration() {
        int duration = 0;
        for(Task t : tasks)
        {
            duration += t.estimateDuration();
        }
        return duration;
    }

    public String toString() {
        return "ComplexTask ID: " + getIdTask() + " | Status: " + getStatusTask() + " | Subtasks: " + tasks.size();
    }

}
