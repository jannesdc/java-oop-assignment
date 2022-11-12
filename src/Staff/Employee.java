package Staff;

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
public abstract class Employee {

    String firstName;
    String lastName;
    int yearsOfExperience;

    /**
     * Constructor for an {@code Employee} with a first and last name as strings and a number of years of experience
     * given as an integer.
     *
     * @param firstName First name of an employee
     * @param lastName Last name of an employee
     * @param yearsOfExperience Number of years of experience of an employee
     */
    public Employee(String firstName, String lastName, int yearsOfExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yearsOfExperience = yearsOfExperience;
    }

    /**
     * @return First name of an employee
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return Last name of an employee
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return First and second name of an employee divided by a whitespace
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * @return Number of years of experience of an employee
     */
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    /**
     * @param newFirstName new first name for an employee
     */
    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    /**
     * @param newLastName new last of an employee (no two word last names or special characters are allowed)
     */
    public void setLastName(String newLastName) {
        this.lastName = newLastName;
    }

    /**
     * @param yearsOfExperience set the number of years of experience of an employee
     */
    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    /**
     * Override of the {@code toString()} method.
     *
     * @return The full name of an employee
     */
    @Override
    public String toString() {
        return getFullName();
    }
}

