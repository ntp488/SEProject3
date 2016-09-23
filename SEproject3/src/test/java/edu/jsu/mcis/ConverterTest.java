package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;
import java.util.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConverterTest {
    private String csvString;
    private String jsonString;

    @Before
    public void setUp() {
        try {
            jsonString = Converter.loadFile("src/test/resources/grades.json");
            csvString = Converter.loadFile("src/test/resources/grades.csv");
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
    
    @Test
    public void testConvertCSVtoJSON() {
        String jsonStringTwo = Converter.csvToJson(csvString);
        assertTrue(Converter.jsonStringsAreEqual(jsonString, jsonStringTwo));
    }

    @Test
    public void testConvertJSONtoCSV() {
        assertEquals(Converter.jsonToCsv(jsonString), csvString);
    }
}







