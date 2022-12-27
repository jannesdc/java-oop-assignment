import Genetics.*;
import Staff.*;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

public class Main {
    public static void main(String[] args) {

        // Note: All comments in the different java classes are created in javadoc for ease of use.
        //       Only in the main class is this type of comment used.

        // First we read the config file, which gets the paths to the specified team and employee file.
        System.out.println("----Initializing System----\n ");
        String[] configEntries = readConfig();
        System.out.println("1. Configuration file read successfully.");

        // Next we create two File objects for the team and the fasta file, so these can be scanned.
        File teamFile = new File("src\\" + configEntries[0]);
        System.out.println("2. Team file read successfully");
        File fastaFile = new File("src\\" + configEntries[1]);
        System.out.println("3. Fasta file read successfully.\n\n");

        // We scan the team file to create employeeArrayList for this we use the readTeamFile method.
        ArrayList<Employee> employeeArrayList = readTeamFile(teamFile);
        System.out.println("Employees of the bioinformatics team:");
        for (Employee e : employeeArrayList) {
            System.out.println(
                    employeeArrayList.indexOf(e) + " - " + e.getFullName() + " - " + e.getClass().getSimpleName());
        }
        System.out.println("\n");

        // For ease later on we create a separate list of each employee type, which we will later populate.
        ArrayList<Bioinformatician> bioinformaticianArrayList = new ArrayList<>();
        ArrayList<TeamLead> teamLeadArrayList = new ArrayList<>();
        ArrayList<TechnicalSupport> technicalSupportArrayList = new ArrayList<>();
        // We also scan the fasta file for all Genomes so that an alignment can be created.
        System.out.println("Converting raw data from fasta file into genomes...");
        ArrayList<Genome> genomeArrayList = readFastaFile(fastaFile);

        // The alignment for hiv is created here once, and we create the repository based the alignment we just created.
        System.out.println("Creating initial alignment...");
        Alignment hiv = new Alignment(genomeArrayList);
        System.out.println("Starting alignment successfully created.");
        System.out.println("Creating alignment repository...");
        AlignmentRepository repo = new AlignmentRepository(hiv);
        System.out.println("Alignment repository successfully created.\n");

        // Now we populate bioinformatician ArrayList and their personal alignments.
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

        // The personal alignments are added to the repository.
        System.out.println("All starting personal alignments successfully initialized.\n");
        System.out.println("Adding alignments to repository...");
        for (Bioinformatician b : bioinformaticianArrayList) {
            repo.addAlignmentToRepo(b.getPersonalAlignment());
        }
        System.out.println("All alignments have successfully been added to repository.\n");

        // We print a few genomes from the starting alignment.
        System.out.println("Printing first 5 genomes from the starting standard alignment:");
        for (int i = 0; i < 5; i++) {
            repo.getOptimalAlignment().printGenome(i);
        }
        // Then we get the difference score of the starting alignment.
        System.out.println("\nDifference score for current (starting) optimal standard alignment: "
                + repo.getOptimalAlignment().score());
        // We can also print the first genomes of the SNiP alignment.
        System.out.println("\nPrinting first 5 genomes from the starting SNiP alignment:");
        for (int i = 0; i < 5; i++) {
            repo.getOptimalSNPAlignment().printGenome(i);
        }
        System.out.println("\nDifference score for current (starting) optimal SNiP alignment: "
                + repo.getOptimalSNPAlignment().score());

        // We can now start making changes to each of the bioinformaticians personal alignments.
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

    }

    // method that reads the config.properties file and returns an Array that contains all relevant properties
    static String[] readConfig() {

        // variable declaration
        Properties prop = new Properties();
        InputStream config = null;
        String[] configEntries = new String[2];

        try {
            config = new FileInputStream("src\\config.properties");
            // load properties file located in src folder
            prop.load(config);
            if (prop.getProperty("teamfilename").equals("")) {
                // throw exception if no team file was provided in the config
                throw new RuntimeException("No team file was provided in the config.properties file please " +
                        "make sure a team file is specified. ");
            } else {
                configEntries[0] = (prop.getProperty("teamfilename"));
            }
            if (prop.getProperty("fastafilename").equals("")) {
                // throw exception if no team file was provided in the config
                throw new RuntimeException("No fasta file was provided in the config.properties file, " +
                        "please make sure the fasta file are specified.");
            } else {
                configEntries[1] = prop.getProperty("fastafilename");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configEntries;
    }

    static ArrayList<Employee> readTeamFile(File teamFile) {

        ArrayList<Employee> employeeArrayList = new ArrayList<Employee>();
        String firstName;
        String lastName;
        int yearsOfExperience;
        Employee newEmployee;

        try {
            Scanner sc = new Scanner(teamFile);

            while (sc.hasNextLine()) {

                switch (sc.next()) {
                    case "TeamLead" -> {
                        firstName = sc.next();
                        lastName = sc.next();
                        yearsOfExperience = Integer.parseInt(sc.next());
                        newEmployee = new TeamLead(firstName, lastName, yearsOfExperience);
                    }
                    case "Bioinformatician" -> {
                        firstName = sc.next();
                        lastName = sc.next();
                        yearsOfExperience = Integer.parseInt(sc.next());
                        newEmployee = new Bioinformatician(firstName, lastName, yearsOfExperience);
                    }
                    case "TechnicalSupport" -> {
                        firstName = sc.next();
                        lastName = sc.next();
                        yearsOfExperience = Integer.parseInt(sc.next());
                        newEmployee = new TechnicalSupport(firstName, lastName, yearsOfExperience);
                    }
                    default -> newEmployee = null;
                }
                if (newEmployee == null) {

                }
                employeeArrayList.add(newEmployee);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Specified file was not found.");
        }
        return employeeArrayList;
    }

    static ArrayList<Genome> readFastaFile(File fastaFile) {

        String identifier;
        String nucleotide;
        Genome newGenome;
        ArrayList<Genome> genomeArrayList = new ArrayList<Genome>();

        try {
            Scanner sc = new Scanner(fastaFile);
            while (sc.hasNext()) {
                identifier = sc.next();
                nucleotide = sc.next();
                newGenome = new Genome(identifier, nucleotide);
                genomeArrayList.add(newGenome);
            }
            sc.close();
            return genomeArrayList;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}