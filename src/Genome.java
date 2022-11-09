import java.util.ArrayList;

public class Genome {

    // variable declarations
    // a genome is defined by an identifier/name and the "genome" which is renamed to nucleotideList,
    // so it is easily distinguishable from the class itself.
    String identifier;
    ArrayList <Character> nucleotideList = new ArrayList<Character>();

    public Genome(String identifier, ArrayList<Character> nucleotideList) {
        this.identifier = identifier;
        this.nucleotideList = nucleotideList;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ArrayList<Character> getNucleotideList() {
        return nucleotideList;
    }

    // override of the toString method which prints only the identifier/name of the associated genome.
    @Override
    public String toString() {
        return identifier;
    }
}
