import Genetics.*;
import Staff.*;

import javax.sound.midi.SysexMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        String[] configEntries = readConfig();
        File teamFile = new File("src\\" + configEntries[0]);
        File fastaFile = new File("src\\" + configEntries[1]);

        ArrayList<Employee> employeeArrayList = readTeamFile(teamFile);
        ArrayList<Genome> genomeArrayList = readFastaFile(fastaFile);

        Alignment hiv = new Alignment(genomeArrayList);
        System.out.println(hiv.score());
        System.out.println(hiv.printGenome(0));
        hiv.replaceNucleotidesInAlignment("TTT", "CCC");
        System.out.println(hiv.printGenome(0));

        Alignment SNPhiv = new Alignment(hiv.snpAlign());
        System.out.println(SNPhiv.score());

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