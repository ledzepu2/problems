import au.com.bytecode.opencsv.CSVReader;
import config.Constants;
import domain.Employee;
import domain.Person;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

/**
 * Created by menona on 12/11/15.
 */
public class EqualsHashCodeTestsTest {

    List<Employee> employees = new ArrayList<Employee>();
    List<Person>   people = new ArrayList<Person>();



    @org.junit.Before
    public void setUp() throws Exception {
        populateEmployees();
        populatePeople();


    }


    @Test
    public void testEmployeeCount()
    {
        assertEquals(employees.size(),5);
    }

    @Test
    public void testPersonCount()
    {
        assertEquals(people.size(),4);
    }

    @Test
    public void testEmployeeEquality()
    {
        Employee employeeA = employees.get(0);
        Employee employeeB = employees.get(1);
        assertEquals(employeeA,employeeB);

    }

    @Test
    public void testPersonEquality()
    {
        Person personA = people.get(0);
        Person personB = people.get(1);

        assertEquals(personA,personB);

    }
    private void populateEmployees() throws IOException {
        CSVReader employeeCSVList = new CSVReader(new FileReader(String.valueOf(Paths.get(Constants.EMPLOYEE_CSV_PATH))));

        employeeCSVList.readAll().forEach(new Consumer<String[]>() {
            public void accept(String[] strings) {
                employees.add(new Employee(strings[0],Integer.parseInt(strings[1]),strings[2]));
            }
        });
    }

    private void populatePeople() throws IOException {
        CSVReader personCSVList = new CSVReader(new FileReader(String.valueOf(Paths.get(Constants.PERSON_CSV_PATH))));

        personCSVList.readAll().forEach(new Consumer<String[]>() {
            public void accept(String[] strings) {
                people.add(new Person(strings[0],Integer.parseInt(strings[1])));
            }
        });
    }


}