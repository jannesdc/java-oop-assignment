package Genetics;

import java.util.ArrayList;

public class AlignmentRepository {

    /**
     * ArrayList that stores all alignments of the department. At position 0 the optimal alignment is stored.
     * The alignments of the different bioinformaticians are stored in the rest of the ArrayList.
     */
    private ArrayList<Alignment> alignmentList = new ArrayList<>();
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
        alignmentList.add(0, startingAlignment);
        this.optimalSNPAlignment = this.getOptimalAlignment().snpAlign();
    }

    /**
     * Copy constructor method for an alignment repository, this method will make a deep clone of a repository.
     * @param repositoryToCopy Repository that will be copied
     */
    public AlignmentRepository(AlignmentRepository repositoryToCopy) {
        ArrayList<Alignment> copyAlignmentList = new ArrayList<>();
        Alignment copyAlignment;
        SNPAlignment copySNPAlignment = repositoryToCopy.getOptimalAlignment().snpAlign();
        for (Alignment a : repositoryToCopy.alignmentList) {
            copyAlignment = new Alignment(a);
            copyAlignmentList.add(copyAlignment);
        }
        this.alignmentList = copyAlignmentList;
        this.optimalSNPAlignment = copySNPAlignment;
    }

    /**
     * Method that returns the optimal alignment of the repo, which is stored as the first object of
     * the alignmentArrayList
     *
     * @return The current optimal Alignment.
     */
    public Alignment getOptimalAlignment() {
        return alignmentList.get(0);
    }

    /**
     * Method that returns the SNiP alignment for the current optimal alignment.
     * @return SNPAlignment for current optimal alignment.
     */
    public SNPAlignment getOptimalSNPAlignment() {
        this.setOptimalSNPAlignment();
        return optimalSNPAlignment;
    }

    /**
     * Returns all the alignments in the repository as an ArrayList.
     * @return ArrayList of all alignments in the repository.
     */
    public ArrayList<Alignment> getAlignmentList() {
        return alignmentList;
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
     * @param alignment Alignment that will be the  new optimal alignment.
     */
    public void setOptimalAlignment(Alignment alignment) {
        this.alignmentList.remove(0);
        this.alignmentList.add(0,alignment);
        if (alignment != null) {
            setOptimalSNPAlignment();
        } else {
            this.optimalSNPAlignment = null;
        }
    }

    /**
     * Method that adds a new Alignment object to the end of the alignmentArrayList.
     *
     * @param newAlignment Alignment that is to be added to the repository.
     */
    public void addAlignmentToRepo(Alignment newAlignment) {
        this.alignmentList.add(newAlignment);
    }
}


