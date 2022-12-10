package Staff;

import Genetics.Alignment;

public class Bioinformatician extends Employee {

    private Alignment personalAlignment;

    public Bioinformatician(String firstName, String lastName, int yearsOfExperience) {
        super(firstName, lastName, yearsOfExperience);
    }

    public Bioinformatician(String firstName, String lastName, int yearsOfExperience, Alignment personalAlignment) {
        super(firstName, lastName, yearsOfExperience);
    }

    public void setPersonalAlignment(Alignment personalAlignment) {
        this.personalAlignment = personalAlignment;
    }

    public Alignment getPersonalAlignment() {
        return personalAlignment;
    }
}
