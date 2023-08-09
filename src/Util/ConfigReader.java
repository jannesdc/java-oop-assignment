package Util;

import Genetics.Genome;
import Staff.Bioinformatician;
import Staff.Employee;
import Staff.TeamLead;
import Staff.TechnicalSupport;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class ConfigReader {

    // method that reads the config.properties file and returns an Array that contains all relevant properties
    public static String[] readConfig() {

        // variable declaration
        Properties prop = new Properties();
        InputStream config = null;
        String[] configEntries = new String[2];

        try {
            config = new FileInputStream("src\\config.properties");
            // load properties file located in src folder
            prop.load(config);
            if (prop.getProperty("teamfilename").isEmpty()) {
                // throw exception if no team file was provided in the config
                throw new RuntimeException("No team file was provided in the config.properties file please " +
                        "make sure a team file is specified. ");
            } else {
                configEntries[0] = (prop.getProperty("teamfilename"));
            }
            if (prop.getProperty("fastafilename").isEmpty()) {
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

    public static ArrayList<Employee> readTeamFile(File teamFile) throws FileNotFoundException {

        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        String firstName;
        String lastName;
        int yearsOfExperience;
        Employee newEmployee;

        try (Scanner sc = new Scanner(teamFile)) {

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
                    throw new IllegalArgumentException("Invalid employee type was provided in the team file.");
                }
                employeeList.add(newEmployee);
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("The specified team file was not found: " + teamFile.getAbsolutePath() +
                    "\nPlease make sure the correct file is specified in the \"config.properties\" file.");
        }
        return employeeList;
    }

    public static ArrayList<Genome> readFastaFile(File fastaFile) throws FileNotFoundException {

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
            throw new FileNotFoundException("The specified team file was not found: " + fastaFile.getAbsolutePath() +
                    "\nPlease make sure the correct file is specified in the \"config.properties\" file.");
        }
    }
}
