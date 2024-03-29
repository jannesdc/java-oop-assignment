package Main;

import Genetics.*;
import Staff.*;
import Util.*;

import java.io.*;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {

        // Note: All comments in the different java classes are created in javadoc for ease of use.
        //       Only in the main class is this type of comment used.

        // First I read the config file, which gets the paths to the specified team and employee file.
        System.out.println("----Initializing System----\n ");
        String[] configEntries = ConfigReader.readConfig();
        System.out.println("1. Configuration file read successfully.");

        // Next I create two File objects for the team and the fasta file, so these can be scanned.
        File teamFile = new File("src\\" + configEntries[0]);
        System.out.println("2. Team file read successfully");
        File fastaFile = new File("src\\" + configEntries[1]);
        System.out.println("3. Fasta file read successfully.\n\n");

        // I scan the team file to create employeeArrayList for this I use the readTeamFile method.
        ArrayList<Employee> employeeArrayList;
        try {
            employeeArrayList = ConfigReader.readTeamFile(teamFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Employees of the bioinformatics team:");
        for (Employee e : employeeArrayList) {
            System.out.println(
                    employeeArrayList.indexOf(e) + " - " + e.getFullName() + " - " + e.getClass().getSimpleName());
        }
        System.out.println("\n");

        // For ease later on I create a separate list of each employee type, which I will later populate.
        ArrayList<Bioinformatician> bioinformaticianArrayList = new ArrayList<>();
        ArrayList<TeamLead> teamLeadArrayList = new ArrayList<>();
        ArrayList<TechnicalSupport> technicalSupportArrayList = new ArrayList<>();
        // I also scan the fasta file for all Genomes so that an alignment can be created.
        System.out.println("Converting raw data from fasta file into genomes...");
        ArrayList<Genome> genomeArrayList = null;
        try {
            genomeArrayList = ConfigReader.readFastaFile(fastaFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // The alignment for hiv is created here once, and I create the repository based the alignment I just created.
        System.out.println("Creating initial alignment...");
        Alignment hiv = new Alignment(genomeArrayList);
        System.out.println("Starting alignment successfully created.");
        System.out.println("Creating alignment repository...");
        AlignmentRepository repo = new AlignmentRepository(hiv);
        System.out.println("Alignment repository successfully created.\n");

        // Now I populate bioinformatician ArrayList and their personal alignments.
        System.out.println("Setting the initial alignment as the personal alignment for each bioinformatician...");
        for (Employee e : employeeArrayList) {
            if (e.getClass() == Bioinformatician.class) {
                bioinformaticianArrayList.add((Bioinformatician) e);
                ((Bioinformatician) e).setPersonalAlignment(repo);
                ((Bioinformatician) e).getPersonalAlignment().setAssociatedEmployee((Bioinformatician) e);
                System.out.println("Starting personal alignment successfully intialized for " + e);
            }
            if (e.getClass() == TeamLead.class) {
                teamLeadArrayList.add((TeamLead) e);
            }
            if (e.getClass() == TechnicalSupport.class) {
                technicalSupportArrayList.add((TechnicalSupport) e);
            }
        }
        System.out.println("All starting personal alignments successfully initialized.\n");

        // The personal alignments are added to the repository.
        System.out.println("Adding alignments to repository...");
        for (Bioinformatician b : bioinformaticianArrayList) {
            repo.addAlignmentToRepo(b.getPersonalAlignment());
        }
        System.out.println("All alignments have successfully been added to repository.\n");

        // I print a few genomes from the starting alignment.
        System.out.println("Printing first 5 genomes from the starting standard alignment:");
        for (int i = 0; i < 5; i++) {
            repo.getOptimalAlignment().printGenome(i);
        }
        // Then I get the difference score of the starting alignment.
        System.out.println("\nDifference score for current (starting) optimal standard alignment: "
                + repo.getOptimalAlignment().score());
        // I can also print the first genomes of the SNiP alignment.
        System.out.println("\nPrinting first 5 genomes from the starting SNiP alignment:");
        for (int i = 0; i < 5; i++) {
            repo.getOptimalSNPAlignment().printGenome(i);
        }
        System.out.println("\nDifference score for current (starting) optimal SNiP alignment: "
                + repo.getOptimalSNPAlignment().score());

        // I can now start making changes to each of the bioinformaticians personal alignments.
        System.out.println("\n\nDifference score of " + bioinformaticianArrayList.get(1) + "'s current alignment: "
                + bioinformaticianArrayList.get(1).getPersonalAlignment().score());
        System.out.println("Changing all occurrences of nucleotide sequence \"TGTCC\" to \"TTTCC\" in "
                + bioinformaticianArrayList.get(1) + "'s alignment...");
        bioinformaticianArrayList.get(1).getPersonalAlignment().replaceNucleotidesInAlignment(
                "TGTCC","TTTCC");
        System.out.println("Difference score after change: "
                + bioinformaticianArrayList.get(1).getPersonalAlignment().score());

        // Making a second change to another bioinformatician's alignment
        System.out.println("\nDifference score of " + bioinformaticianArrayList.get(2) + "'s current alignment: "
                + bioinformaticianArrayList.get(2).getPersonalAlignment().score());
        System.out.println("Changing all occurrences of nucleotide sequence \"AAGTTACCG\" to \"AAATTAAGG\" " +
                "in genome >2001.F1.BR.01.01BR087 in " + bioinformaticianArrayList.get(2) + "'s alignment...");
        bioinformaticianArrayList.get(2).getPersonalAlignment().replaceNucleotidesInGenome(
                "AAGTTACCG", "AAATTAAGG",1);
        System.out.println("Difference score after change: "
                + bioinformaticianArrayList.get(2).getPersonalAlignment().score());

        // We found a better difference score, we will promote Yves Colpaert's
        // personal alignment to the new shared/optimal alignment.
        System.out.println("\n\nDifference score of each bioinformatician's personal alignment:");
        for (Bioinformatician b : bioinformaticianArrayList) {
            System.out.println(b + "'s alignment: " + b.getPersonalAlignment().score());
        }
        System.out.println("\nPromoting " + bioinformaticianArrayList.get(2) + "'s alignment to shared alignment.");
        teamLeadArrayList.get(0).promoteAlignmentToShared(bioinformaticianArrayList.get(2),repo);

        // Let us now copy the shared alignment to Marc Janssens personal alignment
        System.out.println("\nCopying shared alignment to " + bioinformaticianArrayList.get(0) + ".");
        teamLeadArrayList.get(0).copySharedToBioinformatician(bioinformaticianArrayList.get(0),repo);
        // Let us see what this changed to the difference scores:
        System.out.println("\nDifference score of each bioinformatician's personal alignment:");
        for (Bioinformatician b : bioinformaticianArrayList) {
            System.out.println(b + "'s alignment: " + b.getPersonalAlignment().score());
        }

        // Making a backup of the current repository.
        System.out.println("\nMaking backup of repository...");
        technicalSupportArrayList.get(0).makeRepositoryBackup(repo);
        System.out.println("Backup complete.");

        // Trying out other functions that bioinformaticians have.

        // Let us try making another change.
        System.out.println("\nChanging all occurrences of nucleotide sequence \"ACGC\" to \"TATC\" in "
                + bioinformaticianArrayList.get(0) + "'s alignment...");
        bioinformaticianArrayList.get(0).getPersonalAlignment().replaceNucleotidesInAlignment(
                "ACGC","TATC");
        // What are the changes?
        System.out.println("\nDifference score of each bioinformatician's personal alignment:");
        for (Bioinformatician b : bioinformaticianArrayList) {
            System.out.println(b + "'s alignment: " + b.getPersonalAlignment().score());
        }

        // Let's try restoring the previous backup
        System.out.println("\nRestoring backup from before change...");
        repo = technicalSupportArrayList.get(0).getRepositoryBackup(0);

        // The scores should be returned to the ones before the change
        System.out.println("\nDifference score of each bioinformatician's personal alignment:");
        for (Bioinformatician b : bioinformaticianArrayList) {
            System.out.println(b.getPersonalAlignment().getAssociatedEmployee() + "'s alignment: " + b.getPersonalAlignment().score());
        }

        // Testing search function
        System.out.println("\nSearching for all genomes containing \"ACTGACAA\" nucleotide sequence in " + bioinformaticianArrayList.get(1) + "'s alignment...");
        ArrayList<Genome> search1 = bioinformaticianArrayList.get(1).searchNucleotideSequence("ACTGACAA");
        System.out.println(search1.size() + " genomes found, printing:");
        for (Genome g : search1) {
            System.out.println(g);
        }

        // Changing the reference genome to the 3rd genome instead of the 1st in all personal alignments.
        System.out.println("\nChanging reference genome to 3rd genome in all personal alignments...");
        System.out.println("Difference score of each bioinformatician's personal alignment:");
        for (Bioinformatician b : bioinformaticianArrayList) {
            b.getPersonalAlignment().setReferenceGenomePosition(2);
            System.out.println(b.getPersonalAlignment().getAssociatedEmployee() + "'s alignment: " + b.getPersonalAlignment().score());
        }
        System.out.println("\nChanging reference genome back to 1st genome...");
        for (Bioinformatician b : bioinformaticianArrayList) {
            b.getPersonalAlignment().setReferenceGenomePosition(0);
        }

        // Writing data file for Marc Janssens.
        System.out.println("\nCreating data file for " + bioinformaticianArrayList.get(0) + "'s alignment...");
        bioinformaticianArrayList.get(0).writeDataToFile();
        // Writing report file for Marc Janssens.
        System.out.println("\nCreating report file for " + bioinformaticianArrayList.get(0) + "'s alignment...");
        bioinformaticianArrayList.get(0).writeReportToFile();

        // Writing data file for team lead Jozef Groenewegen.
        System.out.println("\nCreating data file for " + teamLeadArrayList.get(0) + ".");
        teamLeadArrayList.get(0).writeDataToFile(repo);
        // Writing report file for team lead Jozef Groenewegen.
        System.out.println("\nCreating report file for " + teamLeadArrayList.get(0) + ".");
        teamLeadArrayList.get(0).writeReportToFile(repo);
    }
}