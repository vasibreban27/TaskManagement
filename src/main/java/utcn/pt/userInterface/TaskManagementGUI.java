package utcn.pt.userInterface;

import utcn.pt.businessLogic.TaskManagement;
import utcn.pt.businessLogic.Utility;
import utcn.pt.dataAccess.Deserialization;
import utcn.pt.dataModel.ComplexTask;
import utcn.pt.dataModel.Employee;
import utcn.pt.dataModel.SimpleTask;
import utcn.pt.dataModel.Task;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TaskManagementGUI {

    public static void main(String[] args) {
        String filename = "task_management.txt";
        TaskManagement manager = new TaskManagement(new HashMap<>());
        Map<Employee, List<Task>> map = manager.getMap();
        Utility utility = new Utility(manager);
        manager.upload(filename);

        JFrame frame = new JFrame("Task Management GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2,10,10));
        JButton addEmployee = new JButton("Add an Employee");
        JButton addTask = new JButton("Add a Task");
        JButton assignTask = new JButton("Assign a Task to Employee");
        JButton viewEmployeesAndTasks = new JButton("View all Employees");
        JButton modifyStatusTask = new JButton("Modify Status to a Task");
        JButton viewStatistics = new JButton("View Statistics from Utility");

        panel.add(addEmployee);
        panel.add(addTask);
        panel.add(assignTask);
        panel.add(viewEmployeesAndTasks);
        panel.add(modifyStatusTask);
        panel.add(viewStatistics);

        addEmployee.addActionListener(e -> {
            JFrame frameEmployee = new JFrame("Add Employee");
            //frameEmployee.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameEmployee.setSize(500,400);

            JPanel panelEmployee = new JPanel();
            panelEmployee.setLayout(new GridLayout(3,2,10,10));
            JLabel name = new JLabel("Employee name");
            JTextField nameField = new JTextField(30);

            JLabel id = new JLabel("Employee id");
            JTextField idField = new JTextField(30);

            JButton addEmployeeButton = new JButton("Add Employee");
            addEmployeeButton.addActionListener(e1->{
                String nameEmp = nameField.getText();
                String idText = idField.getText();

                if (!nameEmp.isEmpty() && !idText.isEmpty()) {
                    try {
                        int idEmp = Integer.parseInt(idText);
                        Employee employee = new Employee(idEmp, nameEmp);
                        map.put(employee, new ArrayList<>());
                        manager.save(filename);
                        JOptionPane.showMessageDialog(frameEmployee, "Employee added succesfully!");
                        frameEmployee.dispose();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frameEmployee, "Invalid ID. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frameEmployee, "Fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }


            });
            panelEmployee.add(name);
            panelEmployee.add(nameField);
            panelEmployee.add(id);
            panelEmployee.add(idField);
            panelEmployee.add(addEmployeeButton);
            frameEmployee.add(panelEmployee);
            frameEmployee.setVisible(true);
        });
        addTask.addActionListener(e -> {
            JFrame frameTask = new JFrame("Add a Task");
            JComboBox<String> box = new JComboBox<>(new String[]{"Select a task type", "Simple Task", "Complex Task"});
            frameTask.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameTask.setSize(500, 500);

            JPanel panelTask = new JPanel();
            panelTask.setLayout(new GridLayout(10, 1, 8, 8));

            JLabel taskId = new JLabel("Task Id");
            JTextField taskIdField = new JTextField(20);

            JLabel taskStatus = new JLabel("Task Status");
            JTextField taskStatusField = new JTextField(20);

            JLabel startHour = new JLabel("Start Hour");
            JTextField startHourField = new JTextField(20);

            JLabel endHour = new JLabel("End Hour");
            JTextField endHourField = new JTextField(20);

            panelTask.add(box);
            panelTask.add(taskId);
            panelTask.add(taskIdField);
            panelTask.add(taskStatus);
            panelTask.add(taskStatusField);
            panelTask.add(startHour);
            panelTask.add(startHourField);
            panelTask.add(endHour);
            panelTask.add(endHourField);

            taskId.setVisible(false);
            taskStatus.setVisible(false);
            taskIdField.setVisible(false);
            taskStatusField.setVisible(false);
            startHour.setVisible(false);
            startHourField.setVisible(false);
            endHour.setVisible(false);
            endHourField.setVisible(false);

            box.addActionListener(e2 -> {
                String selected = (String) box.getSelectedItem();
                boolean isSimpleTask = selected.equals("Simple Task");
                boolean isComplexTask = selected.equals("Complex Task");

                taskId.setVisible(isSimpleTask || isComplexTask);
                taskStatus.setVisible(isSimpleTask || isComplexTask);
                taskIdField.setVisible(isSimpleTask || isComplexTask);
                taskStatusField.setVisible(isSimpleTask || isComplexTask);
                startHour.setVisible(isSimpleTask);
                startHourField.setVisible(isSimpleTask);
                endHour.setVisible(isSimpleTask);
                endHourField.setVisible(isSimpleTask);
            });

            JButton addTaskButton = new JButton("Add Task");
            panelTask.add(addTaskButton);

            addTaskButton.addActionListener(e1 -> {
                String selected = (String) box.getSelectedItem();
                String taskIdText = taskIdField.getText();
                String taskStatusText = taskStatusField.getText();

                if ("Select a task type".equals(selected) || taskIdText.isEmpty() || taskStatusText.isEmpty()) {
                    JOptionPane.showMessageDialog(frameTask, "Please complete all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int taskIdValue = Integer.parseInt(taskIdText);
                    Task task;

                    if ("Simple Task".equals(selected)) {
                        String startHourText = startHourField.getText();
                        String endHourText = endHourField.getText();

                        if (startHourText.isEmpty() || endHourText.isEmpty()) {
                            JOptionPane.showMessageDialog(frameTask, "Please enter valid start and end hours.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        int startHourValue = Integer.parseInt(startHourText);
                        int endHourValue = Integer.parseInt(endHourText);
                        task = new SimpleTask(taskIdValue, taskStatusText, startHourValue, endHourValue);
                    } else {
                        List<Task> simpleTasks = new ArrayList<>();
                        for (Task t : manager.getAllTasks()) {
                            if (t instanceof SimpleTask) {
                                simpleTasks.add(t);
                            }
                        }

                        if (simpleTasks.isEmpty()) {
                            JOptionPane.showMessageDialog(frameTask, "No available SimpleTasks to add!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        String[] taskOptions = simpleTasks.stream()
                                .map(t -> "Task ID: " + t.getIdTask() + " | Status: " + t.getStatusTask())
                                .toArray(String[]::new);

                        JList<String> taskList = new JList<>(taskOptions);
                        taskList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

                        int option = JOptionPane.showConfirmDialog(frameTask, new JScrollPane(taskList),
                                "Select SimpleTasks for ComplexTask", JOptionPane.OK_CANCEL_OPTION);

                        if (option != JOptionPane.OK_OPTION || taskList.getSelectedIndices().length == 0) {
                            JOptionPane.showMessageDialog(frameTask, "You must select at least one SimpleTask!", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        ComplexTask complexTask = new ComplexTask(taskIdValue, taskStatusText);
                        for (int index : taskList.getSelectedIndices()) {
                            complexTask.addTask(simpleTasks.get(index));
                        }

                        task = complexTask;
                    }
                    manager.getAllTasks().add(task);
                    manager.save(filename);

                    JOptionPane.showMessageDialog(frameTask, "Task added successfully and is now available for assignment!");
                    frameTask.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frameTask, "Invalid number format. Please enter valid numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            frameTask.add(panelTask);
            frameTask.setVisible(true);
        });
        assignTask.addActionListener(e -> {
            JFrame assignTaskEmployee = new JFrame("Assign a Task to an Employee");
            assignTaskEmployee.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            assignTaskEmployee.setSize(500, 400);

            JPanel panelAssignTask = new JPanel();
            panelAssignTask.setLayout(new GridLayout(4, 2, 10, 10));

            JLabel employeeID = new JLabel("Enter Employee ID:");
            JTextField employeeIDField = new JTextField(20);

            JLabel taskID = new JLabel("Enter Task ID:");
            JTextField taskIDField = new JTextField(20);

            JButton assignTaskButton = new JButton("Assign Task");
            panelAssignTask.add(employeeID);
            panelAssignTask.add(employeeIDField);
            panelAssignTask.add(taskID);
            panelAssignTask.add(taskIDField);
            panelAssignTask.add(assignTaskButton);
            assignTaskEmployee.add(panelAssignTask);
            assignTaskEmployee.setVisible(true);

            assignTaskButton.addActionListener(e2 -> {
                String empIdText = employeeIDField.getText();
                String taskIdText = taskIDField.getText();
                if (empIdText.isEmpty() || taskIdText.isEmpty()) {
                    JOptionPane.showMessageDialog(assignTaskEmployee, "Fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    int empId = Integer.parseInt(empIdText);
                    int taskId = Integer.parseInt(taskIdText);

                    manager.assignTaskToEmployee(empId, taskId);

                    JOptionPane.showMessageDialog(assignTaskEmployee, "Task assigned successfully!");
                    assignTaskEmployee.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(assignTaskEmployee, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
        viewEmployeesAndTasks.addActionListener(e -> {
            StringBuilder employeesTasks = new StringBuilder();
            for (Map.Entry<Employee, List<Task>> entry : manager.getMap().entrySet()) {
                Employee employee = entry.getKey();
                List<Task> tasks = entry.getValue();

                employeesTasks.append("Employee: ").append(employee.getName()).append(" (ID: ").append(employee.getIdEmployee()).append(")\n");

                if (tasks.isEmpty()) {
                    employeesTasks.append("  - No tasks assigned\n");
                } else {
                    for (Task task : tasks) {
                        employeesTasks.append("  - Task ID: ").append(task.getIdTask())
                                .append(", Status: ").append(task.getStatusTask())
                                .append(", Estimated Duration: ").append(task.estimateDuration())
                                .append(" hours\n");
                    }
                }
                employeesTasks.append("\n");
            }

            if (employeesTasks.length() == 0) {
                JOptionPane.showMessageDialog(null, "No employees found.", "Employees & Tasks", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JTextArea textArea = new JTextArea(employeesTasks.toString());
            textArea.setEditable(false); //read only
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea.setRows(10);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            JOptionPane.showMessageDialog(null, scrollPane, "Employees & Tasks", JOptionPane.INFORMATION_MESSAGE);
        });
        modifyStatusTask.addActionListener(e -> {
            JFrame modifyFrame = new JFrame("Modify Task Status");
            modifyFrame.setSize(400, 300);
            JPanel panelModify = new JPanel();
            panelModify.setLayout(new GridLayout(4, 2, 10, 10));

            JLabel empIdLabel = new JLabel("Enter Employee ID:");
            JTextField empIdField = new JTextField(20);

            JLabel taskIdLabel = new JLabel("Enter Task ID:");
            JTextField taskIdField = new JTextField(20);

            JButton modifyButton = new JButton("Modify Status");
            panelModify.add(empIdLabel);
            panelModify.add(empIdField);
            panelModify.add(taskIdLabel);
            panelModify.add(taskIdField);
            panelModify.add(modifyButton);

            modifyFrame.add(panelModify);
            modifyFrame.setVisible(true);

            modifyButton.addActionListener(e1 -> {
                String empIdText = empIdField.getText();
                String taskIdText = taskIdField.getText();
                if (empIdText.isEmpty() || taskIdText.isEmpty()) {
                    JOptionPane.showMessageDialog(modifyFrame, "Fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int empId = Integer.parseInt(empIdText);
                    int taskId = Integer.parseInt(taskIdText);

                    manager.modifyTaskStatus(empId, taskId);
                    manager.save("task_management.txt");

                    JOptionPane.showMessageDialog(modifyFrame, "Task status modified successfully!");
                    modifyFrame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(modifyFrame, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        });
        viewStatistics.addActionListener(e -> {
            StringBuilder statistics = new StringBuilder();

            Map<String, Map<String, Integer>> taskStats = utility.countTaskStatus(manager.getMap());
            statistics.append(" Task Status Count:\n");

            if (taskStats.isEmpty()) {
                statistics.append("No tasks found.\n");
            } else {
                for (Map.Entry<String, Map<String, Integer>> entry : taskStats.entrySet()) {
                    statistics.append("Employee: ").append(entry.getKey()).append("\n");
                    statistics.append("  - Completed: ").append(entry.getValue().get("Completed")).append("\n");
                    statistics.append("  - Uncompleted: ").append(entry.getValue().get("Uncompleted")).append("\n\n");
                }
            }

            statistics.append("\n Employees with >40 hours of work (Sorted by Work Duration):\n");
            List<Employee> filteredEmployees = utility.filterEmployees(manager.getMap());

            if (filteredEmployees.isEmpty()) {
                statistics.append("No employees exceed 40 hours.\n");
            } else {
                for (Employee emp : filteredEmployees) {
                    int workDuration = manager.calculateEmployeeWorkDuration(emp.getIdEmployee());
                    statistics.append("  - ").append(emp.getName())
                            .append(" (ID: ").append(emp.getIdEmployee()).append(") → ")
                            .append(workDuration).append(" hours\n");
                }
            }

            JTextArea textArea = new JTextArea(statistics.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));

            JOptionPane.showMessageDialog(null, scrollPane, "Task Statistics", JOptionPane.INFORMATION_MESSAGE);
        });

        frame.add(panel);
        frame.setVisible(true);
    }

}
