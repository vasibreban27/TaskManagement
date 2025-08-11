package utcn.pt.dataAccess;

import utcn.pt.dataModel.Employee;
import utcn.pt.dataModel.Task;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deserialization implements Serializable {
    private static final long serialVersionUID = 1L;
    //private static final String fileName = "task_management.txt";

    public static Map<Employee,List<Task>> deserializeData(String filename)  {
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
            return (Map<Employee, List<Task>>)in.readObject();
        }catch(IOException |ClassNotFoundException e)
        {
            System.out.println("No previous data found.");
            return new HashMap<>(); //empty map if no file exists
        }
    }


}
