package edu.jsu.mcis;

import java.io.*;

public class Main {
    
    public static void main(String[] args) {
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        StringBuffer csvContents = new StringBuffer();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("grades.csv")))) {
            String line;
            while((line = reader.readLine()) != null) {
                csvContents.append(line + '\n');
            }
        }
        catch(IOException e) { e.printStackTrace(); }
        String testCsv = csvContents.toString();
        
        String json = Converter.csvToJson(testCsv);
        
        StringBuffer jsonContents = new StringBuffer();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("grades.json")))) {
            String line;
            while((line = reader.readLine()) != null) {
                jsonContents.append(line + '\n');
            }
        }
        catch(IOException e) { e.printStackTrace(); }
        String testJson = jsonContents.toString();

        System.out.println("\n--------input csv--------\n");
        System.out.println(testCsv);
        System.out.println("\n--------output json--------\n");
        System.out.println(json);
        System.out.println("\n--------template--------\n");
        System.out.println(testJson);
        String csv = Converter.jsonToCsv(testJson);
        System.out.println(csv);
    }
}