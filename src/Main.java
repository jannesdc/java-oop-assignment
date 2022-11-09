import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        String[] configEntries = readConfig();
        File teamFile = new File("src\\" + configEntries[0]);
        File fastaFile = new File("src\\" + configEntries[1]);
        Scanner scan = new Scanner(teamFile);
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            System.out.println(data);
        }

        try {
            Pattern pattern = Pattern.compile("^>.*");
            Matcher matcher = pattern.matcher("test");
            while (matcher.find()) {
                for (int i = 1 ; i < matcher.groupCount() ; i++) {
                    System.out.println(matcher.group(i));
                }
            }
        } catch (PatternSyntaxException e) {
            System.out.println("Syntax error in the regular expression.");
        }

    }

    // method that reads the config.properties file and returns an Arraylist that reads all relevant properties
    static String[] readConfig() {

        // variable declaration
        Properties prop = new Properties();
        InputStream config = null;
        String configEntries[] = new String[2];

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
}