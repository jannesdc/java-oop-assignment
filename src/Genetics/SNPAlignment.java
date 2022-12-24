package Genetics;

import Staff.Bioinformatician;

import java.util.ArrayList;

public class SNPAlignment extends Alignment{

    /**
     * ArrayList of all Genome objects in the alignment. All genomes are in the SNiP format except the reference genome.
     */
    private ArrayList<Genome> genomeListSNP;

    /**
     * Constructor for an SNPAlignment object, it is protected so that it is not possible to create an SNP alignment directly,
     * Instead the method {@code snpAlign} of an Alignment class object is to be used to create an SNPAlignment.
     * @param alignment Alignment object for which the corresponding SNP Alignment is created.
     * @param genomeList an ArrayList of all genomes in SNP format as compared to the referenceGenome.
     */
    protected SNPAlignment(Alignment alignment,ArrayList<Genome> genomeList) {
        this.genomeList = alignment.getGenomeList();
        this.referenceGenomePosition = alignment.getReferenceGenomePosition();
        this.referenceGenome = alignment.genomeList.get(referenceGenomePosition);
        this.genomeListSNP = genomeList;
        this.associatedEmployee = (Bioinformatician) alignment.getAssociatedEmployee();
    }

    /**
     * @return The genomeList (SNP) of the alignment.
     */
    @Override
    public ArrayList<Genome> getGenomeList() {
        return genomeListSNP;
    }

    @Override
    public Genome getGenome(int genomePosition) {
        return genomeListSNP.get(genomePosition);
    }

    /**
     * Sets a new reference genome position around which the SNP alignment is made and the score is calculated.
     * This method also changes the referenceGenome itself.
     *
     * @param newReferenceGenomePosition Position of the reference genome in the genome list.
     */
    @Override
    public void setReferenceGenomePosition(int newReferenceGenomePosition) {
        super.setReferenceGenomePosition(newReferenceGenomePosition);
        this.adjustSNPAlignment();
    }

    /**
     * @param genomeList ArrayList of all the genomes in the alignment
     */
    @Override
    public void setGenomeList(ArrayList<Genome> genomeList) {
        super.setGenomeList(genomeList);
        this.adjustSNPAlignment();
    }

    /**
     * This method calculates the "difference score" of an alignment. Which is the number of different characters
     * of each other genome compared to the reference genome. The sum of all these differences is the difference score.
     *
     * @return Difference score of an alignment
     */
    @Override
    public int score() {
        char[] comparisonArray;
        int differenceScore = 0;

        for (int i = 0; i < genomeListSNP.size(); i++) {
            if (i != referenceGenomePosition) {
                comparisonArray = genomeListSNP.get(i).getNucleotideArray();
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
     * Prints the identifier and nucleotide sequence as Strings of a genome which is defined by the method parameter
     * genomePosition.
     *
     * @param genomePosition The position (Integer) of which genome out of the {@code genomeList} that needs to be
     *                       printed.
     */
    @Override
    public void printGenome(int genomePosition) {
        System.out.println(genomeListSNP.get(genomePosition).toString());
    }

    /**
     * Recalculates the {@code ArrayList<Genome>} containing the SNP alignment. This method is used
     * when a change is made to the alignment so that the change is accurately reflected in the SNP alignment.
     * For example when the reference Genome is changed or a nucleotide sequence is replaced.
     *
     * @return {@code ArrayList<Genome>} containing a SNiP alignment of the standard alignment.
     */
    private void adjustSNPAlignment() {

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
        this.genomeListSNP = SNPGenomeList;
    }

    /**
     * Allows alignments to be changed back and forth between standard and SNiP by using this method and {@code snpAlign()}
     *
     * @return Standard alignment of SNiP alignment
     */
    public Alignment STDAlign() {
       Alignment myAlignment = new Alignment(this.genomeList, referenceGenomePosition);
       return myAlignment;
    }

    /**
     * First replaceGenome method (overloaded) that takes a genome as a parameter to replace the current genome at genomePosition.
     *
     * @param genome         {@code Genome} that will replace the current {@code Genome} at {@code genomePosition}
     * @param genomePosition the position in {@code genomeList} where the old {@code Genome} will be replaced
     */
    @Override
    public void replaceGenome(Genome genome, int genomePosition) {
        super.replaceGenome(genome, genomePosition);
        this.adjustSNPAlignment();
    }

    /**
     * Second replaceGenome method (overloaded) that takes a {@code genomeIdentifier} and a {@code nucleotides}
     * sequence to replace the current {@code Genome} at {@code genomePosition}
     *
     * @param genomeIdentifier identifier of the new {@code Genome}
     * @param nucleotides      nucleotide sequence of the new {@code Genome}
     * @param genomePosition   the position in {@code genomeList} where the old {@code Genome} will be replaced.
     */
    @Override
    public void replaceGenome(String genomeIdentifier, String nucleotides, int genomePosition) {
        super.replaceGenome(genomeIdentifier, nucleotides, genomePosition);
        this.adjustSNPAlignment();
    }

    /**
     * Method that replaces all instances of the string {@code oldSequence} in the alignment by a new
     * sequence of nucleotides.
     *
     * @param oldSequence old String sequence that will be replaced by {@code newSequence}
     * @param newSequence new String sequence that will replace {@code oldSequence}
     */
    @Override
    public void replaceNucleotidesInAlignment(String oldSequence, String newSequence) {
        super.replaceNucleotidesInAlignment(oldSequence, newSequence);
        this.adjustSNPAlignment();
    }

    /**
     * Method to replace a given sequence of nucleotides with another given sequence of nucleotides at a given
     * position in the genomeList.
     *
     * @param oldSequence    old String sequence that will be replaced by {@code newSequence}
     * @param newSequence    new String sequence that will replace {@code oldSequence}
     * @param genomePosition the position of the genome where the replacement will occur
     */
    @Override
    public void replaceNucleotidesInGenome(String oldSequence, String newSequence, int genomePosition) {
        super.replaceNucleotidesInGenome(oldSequence, newSequence, genomePosition);
        this.adjustSNPAlignment();
    }

    /**
     * Method to add a new genome at the end of the genomeList of the alignment.
     *
     * @param genome genome that will be added to the genomeList
     */
    @Override
    public void addGenome(Genome genome) {
        super.addGenome(genome);
        this.adjustSNPAlignment();
    }

    /**
     * Method to add a new genome at the end of the genomeList of the alignment, but with a given identifier and
     * sequence of nucleotides instead of a Genome object.
     *
     * @param identifier  name/identifier of the new genome
     * @param nucleotides nucleotide sequence of the new genome
     */
    @Override
    public void addGenome(String identifier, String nucleotides) {
        super.addGenome(identifier, nucleotides);
        this.adjustSNPAlignment();
    }

    /**
     * Method to add a new genome at a given position in the genomeList of the alignment.
     *
     * @param genome   genome that will be added to the genomeList
     * @param position position at which the new genome will be added to genomeList
     */
    @Override
    public void addGenome(Genome genome, int position) {
        super.addGenome(genome, position);
        this.adjustSNPAlignment();
    }

    /**
     * Method to add a new genome at a given position in the genomeList of the alignment,
     * but with a given identifier and sequence of nucleotides instead of a Genome object.
     *
     * @param identifier  name/identifier of the new genome
     * @param nucleotides nucleotide sequence of the new genome
     * @param position    position at which the new genome will be added to genomeList
     */
    @Override
    public void addGenome(String identifier, String nucleotides, int position) {
        super.addGenome(identifier, nucleotides, position);
        this.adjustSNPAlignment();
    }

    /**
     * @param position remove the genome at this position
     */
    @Override
    public void removeGenome(int position) {
        super.removeGenome(position);
        this.adjustSNPAlignment();
    }

    /**
     * @param genome remove the given genome from the genomeList
     */
    @Override
    public void removeGenome(Genome genome) {
        super.removeGenome(genome);
        this.adjustSNPAlignment();
    }

    /**
     * @param identifier remove all the genomes with the given genome identifier/name
     */
    @Override
    public void removeGenomes(String identifier) {
        super.removeGenomes(identifier);
        this.adjustSNPAlignment();
    }
}
