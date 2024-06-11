/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package modul4_modul5;
/**
 *
 * @author TIO
 */
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class DataPassword {
    public static final ArrayList<PasswordStore> passData = new ArrayList<>();
    private static final String csvPath = "./datapassword.csv";
    private static final String[] headers = { "name", "username", "password", "hashkey", "category", "score" };

    public static void saveCSVData() {
        if (passData.isEmpty()) {
            System.out.println("Data masih kosong");
        } else {
            try (FileWriter writer = new FileWriter(csvPath);
                    CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers))) {
                for (PasswordStore pass : passData) {
                    printer.printRecord(pass.name, pass.username, pass.getEncPassword(), pass.getHashkey(),
                            pass.getCategoryCode(), pass.getScore());
                }
                printer.flush();
            } catch (IOException ex) {
                Logger.getLogger(DataPassword.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static ArrayList<PasswordStore> loadCSVData() {
        passData.clear();
        try (FileReader reader = new FileReader(csvPath)) {
            Iterable<CSVRecord> data = CSVFormat.DEFAULT.withHeader(headers).withSkipHeaderRecord().parse(reader);
            for (CSVRecord record : data) {
                PasswordStore newPass;
                if (record.isSet("hashkey")) {
                    newPass = new PasswordStore(record.get("name"), record.get("username"), record.get("password"),
                            Integer.parseInt(record.get("category")), record.get("hashkey"),
                            Double.parseDouble(record.get("score")));
                } else {
                    newPass = new PasswordStore(record.get("name"), record.get("username"), record.get("password"),
                            Integer.parseInt(record.get("category")));
                }
                passData.add(newPass);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Data password kosong ...");
        } catch (IOException ex) {
            Logger.getLogger(DataPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passData;
    }
}
