package Staff;

import Genetics.AlignmentRepository;

import java.util.ArrayList;

public class TechnicalSupport extends Employee {


    /**
     * Variable of all repository backups that this technical support employee has made.
     */
    private ArrayList<AlignmentRepository> repositoryBackups = new ArrayList<>();

    /**
     * Constructor for a technical support employee, it has no extra variables in the constructor compared to its
     * supertype Employee. But does have one extra variable outside its constructor.
     * @param firstName First name of an employee
     * @param lastName Last name of an employee
     * @param yearsOfExperience Number of years of experience of an employee
     */
    public TechnicalSupport(String firstName, String lastName, int yearsOfExperience) {
        super(firstName, lastName, yearsOfExperience);
    }

    /**
     * Makes a new backup of the given repository and adds this to the repositoryBackups variable of the Technical
     * Support employee that made the backup.
     * @param repoToBackup Repository for which a new backup will be made
     */
    public void makeRepositoryBackup(AlignmentRepository repoToBackup) {
        AlignmentRepository newRepoBackup = new AlignmentRepository(repoToBackup);
        repositoryBackups.add(newRepoBackup);
    }

    /**
     * Method that returns a alignment repository backup at a given position in the repository backup list.
     * @param positionBackupList position in the backup list of the backup that should be returned
     * @return Backup of repository at the given position in the backup list of this employee
     */
    public AlignmentRepository getRepositoryBackup(int positionBackupList) {
        if (repositoryBackups.size() != 0) {
            for (Genetics.Alignment a : repositoryBackups.get(0).getAlignmentArrayList()) {
                if (a.getAssociatedEmployee() != null) {
                    ((Bioinformatician) a.getAssociatedEmployee()).setPersonalAlignment(a);
                }
            }
            return repositoryBackups.get(positionBackupList);
        }
        throw new RuntimeException("This technical support employee has no stored backups.");
    }

    /**
     * Removes all data from a repository.
     * @param repository Repository that will be cleared.
     */
    public void clearRepository(AlignmentRepository repository) {
        repository.setOptimalAlignment(null);
        repository.getAlignmentArrayList().clear();
    }
}
