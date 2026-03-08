package com.airtribe.meditrack.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class CSVUtil {
    private CSVUtil() {}

    public static List<String[]> read(String path) throws IOException {
        List<String[]> rows = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) return rows;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) rows.add(line.split(",", -1));
        }
        return rows;
    }

    public static void write(String path, List<String[]> rows) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
            for (String[] r : rows) {
                bw.write(String.join(",", r));
                bw.newLine();
            }
        }
    }
}
