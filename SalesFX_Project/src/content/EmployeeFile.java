package content;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

/*
Katarzyna Karakow
Student Number: 991627844
FINAL PROJECT
April 17, 2021
*/

public class EmployeeFile {

    /*
    Method for writing employeeList records to Employee.dat file
     */
    public static void setEmployee(ArrayList<Employee> employeeList)
            throws IOException {

        FileWriter fw = new FileWriter("Employee.dat");
        BufferedWriter bw = new BufferedWriter(fw);

        Iterator<Employee> isEmployee = employeeList.iterator();
        while (isEmployee.hasNext()) {
            Employee one = isEmployee.next();
            bw.write(one.getID() + ", " + one.getName() + ", "
                    + one.getCity() + ", " + one.getPosition());
            bw.newLine();
        }
        bw.close();
        fw.close();
    }

    /*
    Method for reading from Employee.dat file to the employeeList
     */
    public static void getEmployee(ArrayList<Employee> employeeList)
            throws IOException, FileNotFoundException {

        String data = new String();

        FileReader fr = new FileReader("Employee.dat");
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();
        while (line != null) {
            Employee one = new Employee();
            StringTokenizer dataToken = new StringTokenizer(line, ", ");
            one.setID(Integer.parseInt(dataToken.nextToken()));
            String name = dataToken.nextToken() + " " + dataToken.nextToken();
            one.setName(name);
            one.setCity(dataToken.nextToken());
            one.setPosition(dataToken.nextToken());
            employeeList.add(one);
            line = br.readLine();
        }
        fr.close();
        br.close();
    }
}
