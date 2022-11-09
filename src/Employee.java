public abstract class Employee {

    // variable declaration

    // every employee has a first and last name, therefore they are variables of the superclass Employee
    // the integer
    String firstName;
    String lastName;
    int yearsOfExperience;

    public Employee(String firstName, String lastName, int yearsOfExperience) {
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }


}

