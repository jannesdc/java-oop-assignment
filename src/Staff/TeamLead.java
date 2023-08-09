package Staff;

import Genetics.Alignment;
import Genetics.AlignmentRepository;
import Genetics.Genome;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        for (int i = 1; i < repository.getAlignmentList().size(); i++) {
            Alignment a = repository.getAlignmentList().get(i);
            if (a.getAssociatedEmployee() == bioinformatician) {
                repository.getAlignmentList().set(i, alignmentToShare);
                break;
            }
        }
        bioinformatician.setPersonalAlignment(alignmentToShare);
    }

    /**
     * Writes all genomes of the personal alignment of each bioinformatician to a text file.
     * @param repository Repository that contains the personal alignments of each bioinformatician.
     */
    public void writeDataToFile(AlignmentRepository repository) {
        try {
            File dataFile = new File(this.getFullName() + ".alignment.txt");
            if (dataFile.createNewFile()) {
                System.out.println("Data file created: " + dataFile.getName());
                FileWriter writer = new FileWriter(dataFile.getPath());
                int i = 0;
                for (Alignment a : repository.getAlignmentList()) {
                    if (i == 0) {
                        i++;
                        continue;
                    }
                    writer.write(a.getAssociatedEmployee() + "'s alignment:\n");
                    for (Genome g : a.getGenomeList()) {
                        writer.write(g.getIdentifier());
                        writer.write("\n");
                        writer.write(g.getNucleotides());
                        writer.write("\n");
                    }
                    writer.write("\n\n");
                }
                writer.close();
                System.out.println("Alignment data for each bioinformatician was successfully written to data file.");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Error occurred creating data file.");
            e.printStackTrace();
        }
    }

    /**
     * Writes the difference score for each bioinformaticians personal alignment to a text file.
     * @param repository Repository that contains the personal alignments of each bioinformatician.
     */
    public void writeReportToFile(AlignmentRepository repository) {
        try {
            File dataFile = new File(this.getFullName() + ".score.txt");
            if (dataFile.createNewFile()) {
                System.out.println("Report file created: " + dataFile.getName());
                FileWriter writer = new FileWriter(dataFile.getPath());
                int i = 0;
                for (Alignment a : repository.getAlignmentList()) {
                    if (i == 0) {
                        i++;
                        continue;
                    }
                    writer.write(a.getAssociatedEmployee() + "'s difference score: " + a.score() + "\n");
                }
                writer.close();
                System.out.println("Difference score for each bioinformatician alignment" +
                        " was successfully written to report file.");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Error occurred creating data file.");
            e.printStackTrace();
        }
    }
}
