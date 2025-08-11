package utcn.pt.dataAccess;

import utcn.pt.dataModel.Employee;
import utcn.pt.dataModel.Task;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Serialization implements Serializable {
    private static final long serialVersionUID = 1L;
    //private static final String fileName ="task_management.txt";

    public static void serializeData(Map<Employee, List<Task>> map,String filename){

        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(map);
            System.out.println("Data saved successfully");

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
