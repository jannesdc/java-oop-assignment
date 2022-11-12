package Staff;

public abstract class Employee {
    /**
     *     This class has the following variables:
     *     <ul>
     *         <li>The first name variable is a String which consists of a single word with no special characters.</li>
     *         <li>The last name variable is a String which consists of a single word
     *         (no last names that consist of two words) with no special characters.</li>
     *         <li>The years of experience variable is an integer which cannot be negative
     *         (because it is an integer, and logically it does not make sense to have negative years of experience)
     *         and is not a decimal number.</li>
     *     </ul>
     *     Every type of employee has a first, last name and their years of experience,
     *     therefore they are variables of the superclass Employees.Employee.
     *     <p>
     *     This class does not have any methods other than getters/setters for its variables.
     *     But provides abstraction of the variables that are shared between all types of employees.
     */
    String firstName;
    String lastName;
    int yearsOfExperience;

    public Employee(String firstName, String lastName, int yearsOfExperience) {
        // constructor class for an employee with a first name, last name and a number of years of experience
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getFirstName() {
        // returns the first name of an employee
        return firstName;
    }
    public String getLastName() {
        // returns the last name of an employee
        return lastName;
    }
    public String getFullName() {
        // returns the full name of an employee
        return firstName + " " + lastName;
    }

    public int getYearsOfExperience() {
        // returns the years of experience of an employee
        return yearsOfExperience;
    }
    public void setFirstName(String newFirstName) {
        // sets the first name of an employee to a new, provided first name
        this.firstName = newFirstName;
    }
    public void setLastName(String newLastName) {
        // sets the last name of an employee to a new, provided last name
        this.lastName = newLastName;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}

