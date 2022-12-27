package Staff;

import Genetics.Alignment;
import Genetics.AlignmentRepository;

public class TeamLead extends Employee{
    /**
     * Constructor for a TeamLead object, as a team leader does not have any extra variables compared to it's supertype Employee
     * it has the same exact parameters.
     * @param firstName First name of an employee
     * @param lastName Last name of an employee
     * @param yearsOfExperience Number of years of experience of an employee
     */
    public TeamLead(String firstName, String lastName, int yearsOfExperience) {
        super(firstName, lastName, yearsOfExperience);
    }

    /**
     * Promotes the personal alignment of the given bioinformatician to the shared alignment of the given repository.
     * Makes a copy of the alignment.
     * @param bioinformaticianToPromote bioinformatician whose personal alignment will be promoted.
     * @param repository repository where the alignment will be promoted.
     */
    public void promoteAlignmentToShared(Bioinformatician bioinformaticianToPromote, AlignmentRepository repository) {
        Alignment alignmentToPromote = new Alignment(bioinformaticianToPromote.getPersonalAlignment());
        repository.setOptimalAlignment(alignmentToPromote);
    }

    /**
     * Method to copy the shared alignment to the personal alignment of the given bioinformatician.
     * @param bioinformatician Bioinformatician whose personal alignment will be replaced by the shared alignment.
     * @param repository Repository where the shared alignment will be taken.
     */
    public void copySharedToBioinformatician(Bioinformatician bioinformatician, AlignmentRepository repository) {
        Alignment alignmentToShare = new Alignment(repository.getOptimalAlignment());
        alignmentToShare.setAssociatedEmployee(bioinformatician);
        for (int i = 1; i < repository.getAlignmentArrayList().size(); i++) {
            Alignment a = repository.getAlignmentArrayList().get(i);
            if (a.getAssociatedEmployee() == bioinformatician) {
                repository.getAlignmentArrayList().set(i, alignmentToShare);
                break;
            }
        }
        bioinformatician.setPersonalAlignment(alignmentToShare);
    }
}
