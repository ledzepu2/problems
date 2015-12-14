package domain;

/**
 * Created by menona on 12/11/15.
 */
public class Employee extends Person {

    private final String role;



    public Employee(String name, int age, String role) {
        super(name, age);
        this.role = role;

    }

    public String getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) {
            return false;
        }
        Employee employee = (Employee) o;

        return !(role != null ? !role.equals(employee.role) : employee.role != null);

    }

    @Override
    public int hashCode() {
        return role != null ? role.hashCode()+super.hashCode() : 0+super.hashCode();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "role='" + role + '\'' +
                "name='" + super.getName() + '\'' +
                "age='" + super.getAge() + '\'' +
                '}';
    }
}

