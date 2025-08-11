package utcn.pt.dataModel;

import java.io.Serializable;

public sealed abstract class Task implements Serializable permits SimpleTask,ComplexTask {

    private int idTask;
    private String statusTask;

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public Task(int idTask, String statusTask) {
        this.idTask = idTask;
        this.statusTask = statusTask;
    }

    public int getIdTask() {
        return idTask;
    }

    public String getStatusTask() {
        return statusTask;
    }

    public abstract int estimateDuration();

    public String toString(){
        return "Task " + this.idTask + ", are statusul " + this.statusTask;
    }
}
