package utcn.pt.dataModel;

public non-sealed class SimpleTask extends Task {

    private int startHour;
    private int endHour;

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public SimpleTask(int idTask, String statusTask,int startHour, int endHour) {
        super(idTask,statusTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public int estimateDuration() {
        return endHour - startHour;
    }
}
