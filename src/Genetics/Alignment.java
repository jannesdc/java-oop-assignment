package Genetics;

import Staff.Bioinformatician;
import Staff.Employee;

import java.util.ArrayList;

public class Alignment{

    // variable declaration

    /**
     * ArrayList that contains all Genomes of an Alignment object
     */
    ArrayList<Genome> genomeList;
    /**
     * Integer for the position in the genomeList that is used as the referenceGenome
     */
    int referenceGenomePosition;
    /**
     * Genome object that is used as a reference genome to calculate the alignment score and SNP alignments.
     */
    Genome referenceGenome;

    /**
     * This variable is used to track which employee is associated with the certain Alignment.
     */
    Staff.Bioinformatician associatedEmployee;

    /**
     * First constructor of an {@code Alignment} in which the reference genome is the first genome in the
     * genomeList.
     *
     * @param genomeList ArrayList of all the genomes in an alignment
     */
    public Alignment(ArrayList<Genome> genomeList) {
        this.genomeList = genomeList;
        this.referenceGenomePosition = 0;
        this.referenceGenome = genomeList.get(referenceGenomePosition);
    }

    /**
     * Second, overloaded constructor of an {@code Alignment} in which the {@code referenceGenomePosition} is not
     * the default first genome, but a genome specified by the user.
     *
     * @param genomeList              ArrayList of all the genomes in an alignment
     * @param referenceGenomePosition Position of the reference genome in the genomeList
     */
    public Alignment(ArrayList<Genome> genomeList, int referenceGenomePosition) {
        this.genomeList = genomeList;
        this.referenceGenomePosition = referenceGenomePosition;
        this.referenceGenome = genomeList.get(referenceGenomePosition);
    }

    /**
     * Clone constructor method, to be used when an alignment needs to be deep cloned.
     * @param alignmentToCopy - Alignment that will be cloned.
     */
    public Alignment(Alignment alignmentToCopy) {
        ArrayList<Genome> copyGenomeList = new ArrayList<>();
        this.referenceGenomePosition = alignmentToCopy.getReferenceGenomePosition();
        this.referenceGenome = (Genome) alignmentToCopy.getReferenceGenome().clone();
        for (Genome g : alignmentToCopy.genomeList) {
            copyGenomeList.add((Genome) g.clone());
        }
        this.genomeList = copyGenomeList;
        this.associatedEmployee = (Bioinformatician) alignmentToCopy.getAssociatedEmployee();
    }

    /**
     * Special constructor method that makes the constructor of SNPAlignment possible. It is made protected, so it can only be
     * accessed from its subtype SNPAlignment.
     */
    protected Alignment() {
    }

    /**
     * @return The genomeList of the alignment.
     */
    public ArrayList<Genome> getGenomeList() {
        return genomeList;
    }

    /**
     * @return The position of the reference genome in the genomeList of the alignment
     */
    public int getReferenceGenomePosition() {
        return referenceGenomePosition;
    }

    /**
     * @return The reference genome of the alignment
     */
    public Genome getReferenceGenome() {
        return referenceGenome;
    }

    /**
     * @param genomePosition position in the genomeList of the genome that is to be returned.
     * @return Genome at the given genomePosition
     */
    public Genome getGenome(int genomePosition) {
        return genomeList.get(genomePosition);
    }

    /**
     * @return Employee associated with the alignment
     */
    public Employee getAssociatedEmployee() {
        return associatedEmployee;
    }

    /**
     * @param genomeList ArrayList of all the genomes in the alignment
     */
    void setGenomeList(ArrayList<Genome> genomeList) {
        this.genomeList = genomeList;
    }

    /**
     * Sets a new reference genome position around which the SNP alignment is made and the score is calculated.
     * This method also changes the referenceGenome itself.
     *
     * @param newReferenceGenomePosition Position of the reference genome in the genome list.
     */
    public void setReferenceGenomePosition(int newReferenceGenomePosition) {
        this.referenceGenomePosition = newReferenceGenomePosition;
        this.setReferenceGenome();
    }

    /**
     * Called in other methods that could make changes to the reference genome.
     * It replaces the current reference genome of the alignment to account for any changes made.
     */
    protected void setReferenceGenome() {
        this.referenceGenome = genomeList.get(referenceGenomePosition);
    }

    /**
     * Sets the employee that is associated with an Alignment. This employee has to be of a bioinformatician.
     * @param associatedEmployee Employee type object that is associated with an alignment
     */
    public void setAssociatedEmployee(Bioinformatician associatedEmployee) {
        this.associatedEmployee = associatedEmployee;
    }

    /**
     * Prints the identifier and nucleotide sequence as Strings of a genome which is defined by the method parameter
     * genomePosition.
     *
     * @param genomePosition The position (Integer) of which genome out of the {@code genomeList} that needs to be
     *                       returned.
     */
    public void printGenome(int genomePosition) {
       System.out.println(genomeList.get(genomePosition).toString());
    }

    /**
     * This method calculates the "difference score" of an alignment. Which is the number of different characters
     * of each other genome compared to the reference genome. The sum of all these differences is the difference score.
     *
     * @return Difference score of an alignment
     */
    public int score() {

        char[] referenceArray = referenceGenome.getNucleotideArray();
        char[] comparisonArray;
        int differenceScore = 0;

        for (int i = 0; i < genomeList.size(); i++) {
            if (i != referenceGenomePosition) {
                comparisonArray = genomeList.get(i).getNucleotideArray();
            } else {
                continue;
            }
            for (int j = 0; j < comparisonArray.length; j++) {
                if (referenceArray[j] != comparisonArray[j]) {
                    differenceScore = differenceScore + 1;
                }
            }
        }
        return differenceScore;
    }

    /**
     * Converts the current standard alignment to a SNiP or SNP alignment. This changes the nucleotides sequence
     * of all genomes in the alignment so that all nucleotides that are the same as the reference genome are
     * replaced by dots, and all differences compared to the reference genome are kept as a character.
     *
     * @return {@code ArrayList<Genome>} containing a SNiP alignment of the standard alignment.
     */
    public SNPAlignment snpAlign() {

        char[] referenceArray = referenceGenome.getNucleotideArray();
        char[] comparisonArray;
        ArrayList<Genome> SNPGenomeList = new ArrayList<Genome>();

        for (Genome g : genomeList) {
            SNPGenomeList.add((Genome) g.clone());
        }

        for (int i = 0; i < genomeList.size(); i++) {
            if (i != referenceGenomePosition) {
                comparisonArray = genomeList.get(i).getNucleotideArray();
            } else {
                continue;
            }
            for (int j = 0; j < comparisonArray.length; j++) {
                if (referenceArray[j] == comparisonArray[j]) {
                    comparisonArray[j] = '.';
                }
            }
            SNPGenomeList.get(i).setNucleotides(comparisonArray);
        }
        return new SNPAlignment(this,SNPGenomeList);
    }

    /**
     * Method that takes a String sequence and searches all genomes in the alignment for that sequence
     * and returns all genomes that have a match for the given String.
     *
     * @param sequence String to search for in the nucleotide sequence
     * @return ArrayList of all genomes that include the given sequence
     */
    public ArrayList<Genome> searchNucleotideSequence(String sequence) {

        ArrayList<Genome> resultArraylist = new ArrayList<Genome>();

        for (Genome g : genomeList) {
            if (g.getNucleotides().contains(sequence)) {
                resultArraylist.add(g);
            }
        }
        return resultArraylist;
    }

    /**
     * First replaceGenome method (overloaded) that takes a genome as a parameter to replace the current genome at genomePosition.
     *
     * @param genome         {@code Genome} that will replace the current {@code Genome} at {@code genomePosition}
     * @param genomePosition the position in {@code genomeList} where the old {@code Genome} will be replaced
     */
    public void replaceGenome(Genome genome, int genomePosition) {
        genomeList.set(genomePosition, genome);
        if (genomePosition == referenceGenomePosition) {
            this.setReferenceGenome();
        }
    }

    /**
     * Second replaceGenome method (overloaded) that takes a {@code genomeIdentifier} and a {@code nucleotides}
     * sequence to replace the current {@code Genome} at {@code genomePosition}
     *
     * @param genomeIdentifier identifier of the new {@code Genome}
     * @param nucleotides      nucleotide sequence of the new {@code Genome}
     * @param genomePosition   the position in {@code genomeList} where the old {@code Genome} will be replaced.
     */
    public void replaceGenome(String genomeIdentifier, String nucleotides, int genomePosition) {
        Genome replacementGenome = new Genome(genomeIdentifier, nucleotides);
        genomeList.set(genomePosition, replacementGenome);
        if (genomePosition == referenceGenomePosition) {
            this.setReferenceGenome();
        }
    }

    /**
     * Method that replaces all instances of the string {@code oldSequence} in the alignment by a new
     * sequence of nucleotides.
     *
     * @param oldSequence old String sequence that will be replaced by {@code newSequence}
     * @param newSequence new String sequence that will replace {@code oldSequence}
     */
    public void replaceNucleotidesInAlignment(String oldSequence, String newSequence) {
        if (oldSequence.length() != newSequence.length()) {
            throw new RuntimeException("The nucleotide sequences must be the same length, so that the " +
                    "total length of the nucleotide sequence is not changed.");
        }

        String identifier;
        String toReplace;
        String replacedString;

        for (int i = 0; i < genomeList.size(); i++) {
            identifier = genomeList.get(i).getIdentifier();
            toReplace = genomeList.get(i).getNucleotides();
            replacedString = toReplace.replaceAll(oldSequence, newSequence);

            Genome newGenome = new Genome(identifier, replacedString);
            genomeList.set(i, newGenome);
        }
        this.setReferenceGenome();
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

        if (oldSequence.length() != newSequence.length()) {
            throw new RuntimeException("The nucleotide sequences must be the same length, so that the " +
                    "total length of the nucleotide sequence is not changed.");
        }

        String identifier = genomeList.get(genomePosition).getIdentifier();
        String toReplace = genomeList.get(genomePosition).getNucleotides();
        String replacedString = toReplace.replaceAll(oldSequence, newSequence);

        Genome newGenome = new Genome(identifier, replacedString);
        genomeList.set(genomePosition, newGenome);

        if (genomePosition == referenceGenomePosition) {
            this.setReferenceGenome();
        }
    }

    /**
     * Method to add a new genome at the end of the genomeList of the alignment.
     *
     * @param genome genome that will be added to the genomeList
     */
    public void addGenome(Genome genome) {
        genomeList.add(genome);
    }

    /**
     * Method to add a new genome at the end of the genomeList of the alignment, but with a given identifier and
     * sequence of nucleotides instead of a Genome object.
     *
     * @param identifier  name/identifier of the new genome
     * @param nucleotides nucleotide sequence of the new genome
     */
    public void addGenome(String identifier, String nucleotides) {
        Genome newGenome = new Genome(identifier, nucleotides);
        genomeList.add(newGenome);
    }

    /**
     * Method to add a new genome at a given position in the genomeList of the alignment.
     *
     * @param genome   genome that will be added to the genomeList
     * @param position position at which the new genome will be added to genomeList
     */
    public void addGenome(Genome genome, int position) {
        genomeList.add(position, genome);
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
        Genome newGenome = new Genome(identifier, nucleotides);
        genomeList.add(position, newGenome);
    }

    /**
     * @param position remove the genome at this position
     */
    public void removeGenome(int position) {
        genomeList.remove(position);
    }

    /**
     * @param genome remove the given genome from the genomeList
     */
    public void removeGenome(Genome genome) {
        genomeList.remove(genome);
    }

    /**
     * @param identifier remove all the genomes with the given genome identifier/name
     */
    public void removeGenomes(String identifier) {
        ArrayList<Genome> genomesToDelete = new ArrayList<Genome>();
        for (Genome g : genomeList) {
            if (identifier.equals(g.getIdentifier())) {
                genomesToDelete.add(g);
            }
        }
        for (Genome g : genomesToDelete) {
            genomeList.remove(g);
        }
    }
}
