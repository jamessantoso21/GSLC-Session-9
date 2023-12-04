package com.gslc.connection;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CsvConnection {
    private static CsvConnection instance = null;

    private CsvConnection() {
    }

    public static synchronized CsvConnection getInstance() {
        if (instance == null) {
            instance = new CsvConnection();
        }
        return instance;
    }

    public List<String[]> readCsvFile(String filePath) {
        List<String[]> data = new ArrayList<>();
        Path pathToFile = Paths.get(filePath);

        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] attributes = line.split(",");
                data.add(attributes);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return data;
    }

    public void writeCsvFile(String filePath, List<String[]> dataLines) {
        Path pathToFile = Paths.get(filePath);

        try (BufferedWriter bw = Files.newBufferedWriter(pathToFile)) {
            for (String[] data : dataLines) {
                bw.write(String.join(",", data));
                bw.newLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
