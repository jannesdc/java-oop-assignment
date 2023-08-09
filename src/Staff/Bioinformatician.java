package Staff;

import Genetics.Alignment;
import Genetics.AlignmentRepository;
import Genetics.Genome;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Bioinformatician extends Employee {

    /**
     * Personal alignment on which a bioinformatician can make changes.
     */
    private Alignment personalAlignment;

    /**
     * The constructor method for a bioinformatician does not populate their personal Alignment and is in
     * essence the same as the constructor for an employee.
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
     * Overloaded method that takes an alignment to set as personal alignment for this bioinformatician.
     * @param alignment Alignment to set as new personal alignment.
     */
    public void setPersonalAlignment(Alignment alignment) {
        this.personalAlignment = alignment;
    }

    /**
     * @return The personal alignment of a bioinformatician.
     */
    public Alignment getPersonalAlignment() {
        return personalAlignment;
    }

    /**
     * Changes the reference genome around which an SNP alignment is made and the alignment score is calculated
     * @param newReferenceGenomePosition Position of the new reference Genome in the genomeList.
     */
    public void changeReferenceGenome(int newReferenceGenomePosition) {
        personalAlignment.setReferenceGenomePosition(newReferenceGenomePosition);
    }

    /**
     * Prints the identifier and nucleotide sequence of a genome.
     * @param genomePosition position of the to be printed Genome in the genomeList.
     */
    public void printGenome(int genomePosition) {
        personalAlignment.printGenome(genomePosition);
    }

    /**
     * Returns the Genome at position genomePosition from the personal alignment.
     * @param genomePosition Position of the genome in alignment that needs to be returned.
     * @return The genome at genomePosition
     */
    public Genome getGenome(int genomePosition) {
        return personalAlignment.getGenome(genomePosition);
    }

    /**
     * Method that returns the "difference" score of an alignment. Which is the number of different characters
     * of each other genome compared to the reference genome. The sum of all these differences is the difference score.
     * @return Difference score of alignment, the lower, the better.
     */
    public int alignmentScore() {
        return personalAlignment.score();
    }

    /**
     * Method that creates a new text file where it writes all the genomes of this bioinformatician's personal alignment.
     */
    public void writeDataToFile() {
        try {
            File dataFile = new File(this.getFullName() + ".alignment.txt");
            if (dataFile.createNewFile()) {
                System.out.println("Data file created: " + dataFile.getName());
                FileWriter writer = new FileWriter(dataFile.getPath());
                for (Genome g : this.getPersonalAlignment().getGenomeList()) {
                    writer.write(g.getIdentifier());
                    writer.write("\n");
                    writer.write(g.getNucleotides());
                    writer.write("\n");
                }
                writer.close();
                System.out.println("Alignment data was successfully written to data file.");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Error occurred creating data file.");
            e.printStackTrace();
        }
    }

    /**
     * Method that creates a new text file where it writes the difference score of this bioinformatician's personal
     * alignment.
     */
    public void writeReportToFile() {
        try {
            File dataFile = new File(this.getFullName() + ".score.txt");
            if (dataFile.createNewFile()) {
                System.out.println("Report file created: " + dataFile.getName());
                PrintWriter writer = new PrintWriter(dataFile.getPath());
                writer.write(((Integer) this.getPersonalAlignment().score()).toString());
                writer.close();
                System.out.println("Data was successfully written to report file.");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Error occurred creating report file.");
            e.printStackTrace();
        }
    }

    /**
     * Searches the alignment for all genomes that include a given sequence of characters and return all these genomes
     * in a list.
     * @param sequence String to search for in each nucleotide sequence.
     * @return ArrayList of all genomes that include the given sequence.
     */
    public ArrayList<Genome> searchNucleotideSequence(String sequence) {
        return personalAlignment.searchNucleotideSequence(sequence);
    }

    /**
     * Replaces the Genome at a given position by another given Genome.
     * @param genome Genome that will replace the old genome
     * @param genomePosition The position at which the new genome will be placed.
     */
    public void replaceGenome(Genome genome, int genomePosition) {
        personalAlignment.replaceGenome(genome, genomePosition);
    }

    /**
     * Replaces the current genome at a given position by a new genome defined by a genome identifier and a nucleotide
     * sequence.
     * @param genomeIdentifier Identifier/Name of the new genome.
     * @param nucleotides Nucleotide sequence of the new genome.
     * @param genomePosition position in the genomeList where the old Genome will be replaced by a new Genome.
     */
    public void replaceGenome(String genomeIdentifier, String nucleotides, int genomePosition) {
        personalAlignment.replaceGenome(genomeIdentifier, nucleotides, genomePosition);
    }

    /**
     * Method that replaces all instances of the nucleotide sequence oldSequence by newSequence.
     * @param oldSequence Old String sequence that will be replaced by newSequence.
     * @param newSequence New String sequence that will replace oldSequence.
     */
    public void replaceNucleotidesInAlignment(String oldSequence, String newSequence) {
        personalAlignment.replaceNucleotidesInAlignment(oldSequence, newSequence);
    }

    /**
     * Method to replace a given sequence of nucleotides with another given sequence of nucleotides at a given
     * position in the genomeList.
     *
     * @param oldSequence    old String sequence that will be replaced by {@code newSequence}
     * @param newSequence    new String sequence that will replace {@code oldSequence}
     * @param genomePosition the position of the genome where the replacement will occur
     */
    public void replaceNucleotidesInGenome(String oldSequence, String newSequence, int genomePosition) {
        personalAlignment.replaceNucleotidesInGenome(oldSequence, newSequence, genomePosition);
    }

    /**
     * Method to add a new genome at the end of the genomeList of the alignment.
     *
     * @param genome genome that will be added to the genomeList
     */
    public void addGenome(Genome genome) {
        personalAlignment.addGenome(genome);
    }

    /**
     * Method to add a new genome at the end of the genomeList of the alignment, but with a given identifier and
     * sequence of nucleotides instead of a Genome object.
     *
     * @param identifier  name/identifier of the new genome
     * @param nucleotides nucleotide sequence of the new genome
     */
    public void addGenome(String identifier, String nucleotides) {
        personalAlignment.addGenome(identifier,nucleotides);
    }

    /**
     * Method to add a new genome at a given position in the genomeList of the alignment.
     *
     * @param genome   genome that will be added to the genomeList
     * @param position position at which the new genome will be added to genomeList
     */
    public void addGenome(Genome genome, int position) {
        personalAlignment.addGenome(genome, position);
    }

    /**
     * Method to add a new genome at a given position in the genomeList of the alignment,
     * but with a given identifier and sequence of nucleotides instead of a Genome object.
     *
     * @param identifier  name/identifier of the new genome
     * @param nucleotides nucleotide sequence of the new genome
     * @param position    position at which the new genome will be added to genomeList
     */
    public void addGenome(String identifier, String nucleotides, int position) {
        personalAlignment.addGenome(identifier, nucleotides, position);
    }

    /**
     * @param position Remove the genome at the given position.
     */
    public void removeGenome(int position) {
        personalAlignment.removeGenome(position);
    }

    /**
     * @param genome Remove the given genome from the genomeList.
     */
    public void removeGenome(Genome genome) {
        personalAlignment.removeGenome(genome);
    }

    /**
     * @param identifier Removes all genomes with the given identifier/name from the genomeList.
     */
    public void removeGenomes(String identifier) {
        personalAlignment.removeGenomes(identifier);
    }
}
