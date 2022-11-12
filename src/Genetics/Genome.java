package Genetics;


public class Genome {

    // variable declarations
    String identifier;
    String nucleotides;

    /**
     * @param identifier identifier/name of the genome
     * @param nucleotides to avoid confusion the "genome" sequence is renamed to nucleotides which is a String of all
     *                    "A", "C", "G" or "T" characters
     */
    public Genome(String identifier, String nucleotides) {
        this.identifier = identifier;
        this.nucleotides = nucleotides;
    }

    /**
     * @return Identifier/Name of the genome in a String.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @return Nucleotide sequence of the genome in a String.
     */
    public String getNucleotides() {
        return nucleotides;
    }

    /**
     * Converts the nucleotides String into a char array.
     * @return Char array of all nucleotides of the genome.
     */
    public char[] getNucleotideArray() {
        return nucleotides.toCharArray();
    }

    /**
     * Examples: >1990.U.CD.90.90CD121E12 or >1999.A1.UG.99.99UGA07072
     *
     * @param identifier String type of the format >YYYY.##.##.##.#############
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @param nucleotides Assign new nucleotide String with a String as parameter.
     */
    public void setNucleotides(String nucleotides) {
        this.nucleotides = nucleotides;
    }

    /**
     * @param nucleotideArray Assign new nucleotide String with a char array as a parameter.
     */
    public void setNucleotides(char[] nucleotideArray) {
        String string = new String(nucleotideArray);
        this.nucleotides = string;
    }

    /**
     * Override of the {@code toString()} method
     * @return Identifier and the entire nucleotide sequence over two lines.
     */
    @Override
    public String toString() {
        return identifier + "\n" + nucleotides;
    }
}
