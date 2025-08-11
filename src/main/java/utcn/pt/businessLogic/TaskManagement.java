package utcn.pt.businessLogic;

import utcn.pt.dataAccess.Deserialization;
import utcn.pt.dataAccess.Serialization;
import utcn.pt.dataModel.Employee;
import utcn.pt.dataModel.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManagement {

    private Map<Employee, List<Task>> map;
    private List<Task> allTasks;

    public TaskManagement(Map<Employee, List<Task>> map) {
        this.map = map;
        allTasks = new ArrayList<>();
    }

    public void assignTaskToEmployee(int idEmployee, int taskId) {
        Employee selectedEmployee = null;
        Task selectedTask = null;

        for (Employee emp : map.keySet()) {
            if (emp.getIdEmployee() == idEmployee) {
                selectedEmployee = emp;
                break;
            }
        }
        if (selectedEmployee == null) {
            System.out.println("Employee with ID " + idEmployee + " not found.");
            return;
        }

        for (Task task : allTasks) {
            if (task.getIdTask() == taskId) {
                selectedTask = task;
                break;
            }
        }
        if (selectedTask == null) {
            System.out.println("Task with ID " + taskId + " not found.");
            return;
        }
        List<Task> taskList = map.get(selectedEmployee);
        if (taskList == null) {
            taskList = new ArrayList<>();
            map.put(selectedEmployee, taskList);
        }

        taskList.add(selectedTask);
        allTasks.remove(selectedTask);
        save("task_management.txt");
    }

    public int calculateEmployeeWorkDuration(int idEmployee){
        int totalDuration = 0;
        for (Employee emp : map.keySet()) {
            if (emp.getIdEmployee() == idEmployee) {
                for (Task task : map.get(emp)) {
                    if (task.getStatusTask().equals("Completed")) {
                        totalDuration += task.estimateDuration();
                    }
                }
                break;
            }
        }
        return totalDuration;
    }

    public void modifyTaskStatus(int idEmployee,int idTask){
        for (Employee emp : map.keySet()) {
            if (emp.getIdEmployee() == idEmployee) {
                for (Task task : map.get(emp)) {
                    if (task.getIdTask() == idTask) {
                        task.setStatusTask(task.getStatusTask().equals("Completed") ? "Uncompleted" : "Completed");
                        return;
                    }
                }
            }
        }
    }

    public Map<Employee, List<Task>> getMap() {
        return map;
    }

    public void setMap(Map<Employee, List<Task>> map) {
        this.map = map;
    }

    public void save(String filename)
    {
        Serialization.serializeData(map,filename);
    }

    public void upload(String filename)
    {
        map = Deserialization.deserializeData(filename);
        allTasks = new ArrayList<>();
        for (List<Task> tasks : map.values()) {
            allTasks.addAll(tasks);
        }
        System.out.println("Data loaded successfully.");
    }
    public List<Task> getAllTasks() {
        return allTasks;
    }
}
