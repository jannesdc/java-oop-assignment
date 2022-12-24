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
        // For ease later on we create a separate list of bioinformaticians, which we will later populate.
        ArrayList<Bioinformatician> bioinformaticianArrayList = new ArrayList<>();
        // We also scan the fasta file for all Genomes so that an alignment can be created.
        System.out.println("Converting raw data from fasta file into genomes....");
        ArrayList<Genome> genomeArrayList = readFastaFile(fastaFile);

        // The alignment for hiv is created here once, and we create the repository based the alignment we just created.
        System.out.println("Creating initial alignment....");
        Alignment hiv = new Alignment(genomeArrayList);
        System.out.println("Starting alignment successfully created.");
        System.out.println("Creating alignment repository ....");
        AlignmentRepository repo = new AlignmentRepository(hiv);
        System.out.println("Alignment repository successfully created.\n");
        // Now we populate bioinformatician ArrayList and their personal alignments.
        System.out.println("Setting the initial alignment as the personal alignment for each bioinformatician....");
        for (Employee e : employeeArrayList) {
            if (e.getClass() == Bioinformatician.class) {
                bioinformaticianArrayList.add((Bioinformatician) e);
                ((Bioinformatician) e).setPersonalAlignment(repo);
                ((Bioinformatician) e).getPersonalAlignment().setAssociatedEmployee((Bioinformatician) e);
                System.out.println("Starting personal alignment successfully intialized for " + e);
            }
        }
        System.out.println("All starting personal alignments successfully initialized.\n");

        //We can now start making changes to each of the bioinformaticians personal alignments.
        System.out.println("Difference score for current optimal alignment: " + repo.getOptimalAlignment().score());
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