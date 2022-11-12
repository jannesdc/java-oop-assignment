package Genetics;

import java.util.ArrayList;

public class Alignment {

    // variable declaration

    ArrayList<Genome> genomeList;
    int referenceGenomePosition;
    Genome referenceGenome;

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
     * @param genomeList ArrayList of all the genomes in an alignment
     * @param referenceGenomePosition Position of the reference genome in the genomeList
     */
    public Alignment(ArrayList<Genome> genomeList, int referenceGenomePosition) {
        this.genomeList = genomeList;
        this.referenceGenomePosition = referenceGenomePosition;
        this.referenceGenome = genomeList.get(referenceGenomePosition);
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
     * Returns the identifier and nucleotide sequence as Strings of a genome which is defined by the method parameter
     * genomePosition.
     *
     * @param genomePosition The position (Integer) of which genome out of the {@code genomeList} that needs to be
     *                       returned.
     * @return The identifier and nucleotide sequence over two lines
     */
    public String printGenome(int genomePosition) {
        return genomeList.get(genomePosition).toString();
    }

    /**
     * @param genomeList ArrayList of all the genomes in the alignment
     */
    public void setGenomeList(ArrayList<Genome> genomeList) {
        this.genomeList = genomeList;
    }

    /**
     * Sets a new reference genome position around which the SNP alignment is made and the score is calculated.
     *
     * @param referenceGenomePosition Position of the reference genome in the genome list.
     */
    public void setReferenceGenomePosition(int referenceGenomePosition) {
        this.referenceGenomePosition = referenceGenomePosition;
    }

    /**
     * This method calculates the "difference score" of an alignment. Which is the number of different characters
     * of each other genome compared to the reference genome. The sum of all these differences is the difference score.
     *
     * @return Difference score of an alignment
     */
    public int score() {

        ArrayList<Genome> SNPGenomeList = snpAlign();
        char[] comparisonArray;
        int differenceScore = 0;

        for (int i = 0; i < genomeList.size(); i++) {
            if (i != referenceGenomePosition) {
                comparisonArray = SNPGenomeList.get(i).getNucleotideArray();
            } else {
                continue;
            }
            for (char c : comparisonArray) {
                if (c != '.') {
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
    public ArrayList<Genome> snpAlign() {

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
        return SNPGenomeList;
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
     * @param genome {@code Genome} that will replace the current {@code Genome} at {@code genomePosition}
     * @param genomePosition the position in {@code genomeList} where the old {@code Genome} will be replaced
     */
    public void replaceGenome(Genome genome, int genomePosition) {
        genomeList.set(genomePosition,genome);
    }

    /**
     * Second replaceGenome method (overloaded) that takes a {@code genomeIdentifier} and a {@code nucleotides}
     * sequence to replace the current {@code Genome} at {@code genomePosition}
     *
     * @param genomeIdentifier identifier of the new {@code Genome}
     * @param nucleotides nucleotide sequence of the new {@code Genome}
     * @param genomePosition the position in {@code genomeList} where the old {@code Genome} will be replaced.
     */
    public void replaceGenome(String genomeIdentifier, String nucleotides, int genomePosition) {
        Genome replacementGenome = new Genome(genomeIdentifier, nucleotides);
        genomeList.set(genomePosition, replacementGenome);
    }

}
