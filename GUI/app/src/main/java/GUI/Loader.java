/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package GUI;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Loader {
    public static ArrayList<Mahasiswa> csvMhsLoad() {
        String path = "./data-mahasiswa.csv";
        ArrayList<Mahasiswa> dataMhs = new ArrayList<>();
        dataMhs.clear();
        try {
            FileReader reader = new FileReader(path);
            Iterable<CSVRecord> recordData = CSVFormat.DEFAULT
                .withHeader("nim", "nama", "sksTotal", "ipk")
                .withSkipHeaderRecord()
                .parse(reader);
            for (CSVRecord record : recordData) {
                dataMhs.add(new Mahasiswa(
                    record.get("nim"),
                    record.get("nama"),
                    Integer.parseInt(record.get("sksTotal")),
                    Double.parseDouble(record.get("ipk"))
                ));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMhs;
    }
}
