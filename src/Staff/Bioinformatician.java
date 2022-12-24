package Staff;

import Genetics.Alignment;
import Genetics.AlignmentRepository;

public class Bioinformatician extends Employee {

    /**
     * Personal alignment on which a bioinformatician can make changes.
     */
    private Alignment personalAlignment;

    /**
     * The constructor method for a bioinformatician does not populate their personal Alignment and is in
     * essence the same as the constructor for a employee.
     * @param firstName First name of an employee
     * @param lastName Last name of an employee
     * @param yearsOfExperience Years of experience of an employee
     */
    public Bioinformatician(String firstName, String lastName, int yearsOfExperience) {
        super(firstName, lastName, yearsOfExperience);
    }


    /**
     * Sets the personal alignment of a bioinformatician as the optimal alignment of a given repository.
     * @param repository Repository from which the optimal alignment will be cloned.
     */
    public void setPersonalAlignment(AlignmentRepository repository) {
        this.personalAlignment = new Alignment(repository.getOptimalAlignment());
    }

    /**
     * @return the personal alignment of a bioinformatician.
     */
    public Alignment getPersonalAlignment() {
        return personalAlignment;
    }

}
