package utcn.pt.businessLogic;

import utcn.pt.dataModel.Employee;
import utcn.pt.dataModel.Task;

import java.util.*;

public class Utility {
    TaskManagement manager;

    public Utility(TaskManagement manager) {
        this.manager = manager;
    }

    public List<Employee> filterEmployees(Map<Employee, List<Task>> map) {
        List<Employee> emp = new ArrayList<>();
        TreeMap<Integer, List<Employee>> tree = new TreeMap<>();
        for (Employee e : map.keySet()) {
            int empId = e.getIdEmployee();
            int workDuration = manager.calculateEmployeeWorkDuration(empId);
            if (workDuration > 40) {
                tree.computeIfAbsent(workDuration, k -> new ArrayList<>()).add(e);
            }
        }
        for (List<Employee> employees : tree.values()) {
            emp.addAll(employees);
        }
        return emp;
    }

    public Map<String,Map<String,Integer>> countTaskStatus(Map<Employee,List<Task>> map){
        Map<String,Map<String,Integer>> employeeTaskCount = new HashMap<>();

        for(Map.Entry<Employee,List<Task>> entry: map.entrySet())
        {
            Employee emp = entry.getKey();
            List<Task> tasks = entry.getValue();

            Map<String,Integer> taskCount = new HashMap<>();
            taskCount.put("Completed",0);
            taskCount.put("Uncompleted",0);

            for(Task task: tasks){
                if(task.getStatusTask().equals("Completed")){
                    taskCount.put("Completed",taskCount.get("Completed")+1);
                }else{
                    taskCount.put("Uncompleted",taskCount.get("Uncompleted")+1);
                }
            }
            employeeTaskCount.put(emp.getName(),taskCount);
        }
        return employeeTaskCount;
    }

}
