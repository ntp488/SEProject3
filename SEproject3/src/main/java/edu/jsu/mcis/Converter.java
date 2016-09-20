package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import au.com.bytecode.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    public static String loadFile(String path) throws IOException {
        File inputFile = new File(path);
        Scanner scanner = new Scanner(inputFile);
        String output = "";
        
        while(scanner.hasNextLine()) {
            output += scanner.nextLine() + "\n";
        }
        scanner.close();
        return output;
    }
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        JSONObject jsonObj = new JSONObject();
        
        JSONArray colHeaders = new JSONArray();
        colHeaders.add("Total");
        colHeaders.add("Assignment 1");
        colHeaders.add("Assignment 2");
        colHeaders.add("Exam 1");
        
        JSONArray rowHeaders = new JSONArray();
        JSONArray data = new JSONArray();
        
        jsonObj.put("colHeaders", colHeaders);
        jsonObj.put("rowHeaders", rowHeaders);
        jsonObj.put("data", data);
        
        CSVParser csvParser = new CSVParser();
        BufferedReader bufferedReader = new BufferedReader(new StringReader(csvString));
        
        try {
            String line = bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null ) {
                String[] input = csvParser.parseLine(line);
                JSONArray dataRow = new JSONArray();
                
                rowHeaders.add(input[0]);
                dataRow.add(new Double(input[1]));
                dataRow.add(new Double(input[2]));
                dataRow.add(new Double(input[3]));
                dataRow.add(new Double(input[4]));
                data.add(dataRow);
            }
        } catch(IOException exception) {
        }
        return jsonObj.toString();
    }
    
    public static String jsonToCsv(String jsonString) {
        JSONObject jsonObj = null;

        try {
            JSONParser jsonParser = new JSONParser();
            jsonObj = (JSONObject) jsonParser.parse(jsonString);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        String csvContent = "\"ID\"," + Converter.<String>joinArray((JSONArray) jsonObj.get("colHeaders")) + "\n";

        JSONArray rowHeaders = (JSONArray) jsonObj.get("rowHeaders");
        JSONArray rowData = (JSONArray) jsonObj.get("data");
        int numOfHeaders = rowHeaders.size();
        
        for (int i = 0; i < numOfHeaders; i++) {
            csvContent += (
                "\""+ (String) rowHeaders.get(i) + "\"," +
                Converter.<Double>joinArray((JSONArray) rowData.get(i)) + "\n"
            );
            //if (i < numOfHeaders - 1) {
            //    csvContent += "\n";
            //}
        }
        return csvContent;
    }
    
    @SuppressWarnings("unchecked")
    private static <T> String joinArray(JSONArray inputJSONArray) {
        String ouput = "";
        for (int i = 0, length = inputJSONArray.size(); i < length; i++) {
            ouput += "\"" + ((T) inputJSONArray.get(i)) + "\"";
            if (i < length - 1) {
                ouput += ",";
            }
        }
        return ouput;
    }

    public static boolean jsonStringsAreEqual(String a, String b) {
        try {
            return jsonEqaul(new JSONParser().parse(a), new JSONParser().parse(b));
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    private static boolean jsonEqaul(Object a, Object b) {
        if (a instanceof JSONObject && b instanceof JSONObject) {
            return jsonObjectEqaul((JSONObject) a, (JSONObject) b);
        } else if (a instanceof JSONArray && b instanceof JSONArray) {
            return jsonArrayEqaul((JSONArray) a, (JSONArray) b);
        } else {
            return a.equals(b);
        }
    }
    
    private static boolean jsonObjectEqaul(JSONObject a, JSONObject b) {
        for (Object k : a.keySet()) {
            String key = (String) k;

            if (!b.containsKey(key) || !jsonEqaul(a.get(key), b.get(key))) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean jsonArrayEqaul(JSONArray a, JSONArray b) {
        int aSize = a.size();

        if (aSize != b.size()) {
            return false;
        } else {
            for (int i = 0, il = aSize; i < il; i++) {
                if (!jsonEqaul(a.get(i), b.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }
}













