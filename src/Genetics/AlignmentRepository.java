package Genetics;

import java.util.ArrayList;

public class AlignmentRepository {

    /**
     * ArrayList that stores all alignments of the department. At position 0 the optimal alignment is stored.
     * The alignments of the different bioinformaticians are stored in the rest of the ArrayList.
     */
    private ArrayList<Alignment> alignmentArrayList = new ArrayList<>();
    /**
     * SNP version of the optimal alignment.
     */
    private SNPAlignment optimalSNPAlignment;

    /**
     * First constructor method for a new alignment repository, the startingAlignment is the optimal alignment.
     * Which is stored at position 0 in the repository.
     * @param startingAlignment First alignment
     */
    public AlignmentRepository(Alignment startingAlignment) {
        alignmentArrayList.add(0, startingAlignment);
        this.optimalSNPAlignment = this.getOptimalAlignment().snpAlign();
    }

    /**
     * Second constructor method, which creates an empty repository so that it can be filled later using the
     * {@code setOptimalAlignment()} method later on.
     */
    public AlignmentRepository() {
    }

    /**
     * Method that returns the optimal alignment of the repo, which is stored as the first object of
     * the alignmentArrayList
     *
     * @return The current optimal Alignment.
     */
    public Alignment getOptimalAlignment() {
        return alignmentArrayList.get(0);
    }

    /**
     * Method to recalculate the SNPAlignment of the current optimal alignment. To be used when the
     * optimal alignment is changed.
     */
    protected void setOptimalSNPAlignment() {
        this.optimalSNPAlignment = this.getOptimalAlignment().snpAlign();
    }

    /**
     * Method that sets the optimal alignment in the repository to the given alignment, also recalculates the
     * SNP alignment for the optimal alignment.
     * @param alignment
     */
    public void setOptimalAlignment(Alignment alignment) {
        this.alignmentArrayList.add(0, alignment);
        setOptimalSNPAlignment();
    }

    /**
     * Method that adds a new Alignment object to the end of the alignmentArrayList.
     *
     * @param newAlignment Alignment that is to be added to the repository.
     */
    public void addAlignmentToRepo(Alignment newAlignment) {
        this.alignmentArrayList.add(newAlignment);
    }
}


