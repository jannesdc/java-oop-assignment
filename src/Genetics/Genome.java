package Genetics;

import java.util.ArrayList;

public class Genome {

    // variable declarations
    // a genome is defined by an identifier/name and the "genome" which is renamed to nucleotideList,
    // so it is easily distinguishable from the class itself.
    String identifier;
    String nucleotides;

    public Genome(String identifier, String nucleotides) {
        this.identifier = identifier;
        this.nucleotides = nucleotides;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getNucleotides() {
        return nucleotides;
    }

    public char[] getNucleotideArray() {
        return nucleotides.toCharArray();
    }

    // override of the toString method which prints only the identifier/name of the associated genome.
    @Override
    public String toString() {
        return identifier;
    }
}
