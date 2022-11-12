package Genetics;

import java.util.ArrayList;

public class Alignment {

    // variable declaration

    ArrayList<Genome> genomeList;
    int referenceGenomePosition;
    Genome referenceGenome;

    public Alignment(ArrayList<Genome> genomeList) {
        this.genomeList = genomeList;
        this.referenceGenomePosition = 0;
        this.referenceGenome = genomeList.get(referenceGenomePosition);
    }

    public Alignment(ArrayList<Genome> genomeList, int referenceGenomePosition) {
        this.genomeList = genomeList;
        this.referenceGenomePosition = referenceGenomePosition;
        this.referenceGenome = genomeList.get(referenceGenomePosition);
    }

    public ArrayList<Genome> getGenomeList() {
        return genomeList;
    }

    public int getReferenceGenomePosition() {
        return referenceGenomePosition;
    }

    public void setGenomeList(ArrayList<Genome> genomeList) {
        this.genomeList = genomeList;
    }

    public void setReferenceGenomePosition(int referenceGenomePosition) {
        this.referenceGenomePosition = referenceGenomePosition;
    }

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
}
