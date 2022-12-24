import Genetics.*;
import Staff.*;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Note: All comments in the different java classes are created in javadoc for ease of use.
        //       Only in the main class is this type of comment used.

        // First we read the config file, which gets the paths to the specified team and employee file.
        String[] configEntries = readConfig();
        // Next we create two File objects for the team and the fasta file, so these can be scanned.
        File teamFile = new File("src\\" + configEntries[0]);
        File fastaFile = new File("src\\" + configEntries[1]);
        // We scan the team file to create employeeArrayList for this we use the readTeamFile method.
        ArrayList<Employee> employeeArrayList = readTeamFile(teamFile);
        System.out.println("Employees of the bioinformatics team:");
        for (Employee e : employeeArrayList) {
            System.out.println(
                    employeeArrayList.indexOf(e) + " - " + e.getFullName() + " - " + e.getClass().getSimpleName());
        }
        System.out.println("\n\n");
        // For ease later on we create a separate list of bioinformaticians, which we will later populate.
        ArrayList<Bioinformatician> bioinformaticianArrayList = new ArrayList<>();
        // We also scan the fasta file for all Genomes so that an alignment can be created.
        ArrayList<Genome> genomeArrayList = readFastaFile(fastaFile);

        // The alignment for hiv is created here once, and we create the repository based the alignment we just created.
        Alignment hiv = new Alignment(genomeArrayList);
        AlignmentRepository repo = new AlignmentRepository(hiv);
        // Now we populate bioinformatician ArrayList and their personal alignments.
        for (Employee e : employeeArrayList) {
            if (e.getClass() == Bioinformatician.class) {
                bioinformaticianArrayList.add((Bioinformatician) e);
                ((Bioinformatician) e).setPersonalAlignment(repo);
            }
        }


        /*
        for (Employee e : employeeArrayList) {
            if (e instanceof Bioinformatician) {
                ((Bioinformatician) e).setPersonalAlignment(hiv);
            }
        }

        Bioinformatician marc = (Bioinformatician) employeeArrayList.get(1);
        System.out.println(marc.getPersonalAlignment().printGenome(0));
        System.out.println(marc.getPersonalAlignment().printGenome(1));

        SNPAlignment hivSNP = hiv.snpAlign();

        marc.setPersonalAlignment(hivSNP);
        System.out.println(marc.getPersonalAlignment().printGenome(0));
        System.out.println(marc.getPersonalAlignment().printGenome(1));

        System.out.println(hiv.score());

        System.out.println(hivSNP.score());
        System.out.println(hivSNP.score());
        for (int i = 0; i < 2; i++) {
            System.out.println(hivSNP.printGenome(i));
        }

        hivSNP.replaceGenome(hiv.getGenome(10), 1);

        for (int i = 0; i < 2; i++) {
            System.out.println(hivSNP.printGenome(i));
        }
        */

    }

    // method that reads the config.properties file and returns an Arraylist that reads all relevant properties
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
                // the property teamfilename is empty in the config.properties file
                System.out.println("No team file was provided in the config.properties file, " +
                        "please make sure the team file is specified. ");
            } else {
                configEntries[0] = (prop.getProperty("teamfilename"));
            }
            if (prop.getProperty("fastafilename").equals("")) {
                // the property fastafilename is empty in the config.properties file
                System.out.println("No fasta file was provided in the config.properties file, " +
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