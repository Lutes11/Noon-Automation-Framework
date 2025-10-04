package utilities;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVFileManager {
    CSVParser csvParser;
    Iterable<CSVRecord> csvRecords;
    public CSVFileManager(String csvFilePath) {

        try {
            FileReader fileReader = new FileReader(csvFilePath);
            csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(fileReader);
            FileReader reader = new FileReader(csvFilePath);
            csvRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public List<String> getColomnName(){
        try{
            return new ArrayList<>(csvParser.getHeaderNames());
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
    public List<String[]> getRows(){
        List<String[]> rows = new ArrayList<>();
        try{
        for (CSVRecord csvRecord : csvRecords) {
            String[] data = new String[csvRecord.size()];
            for (int i = 0; i < data.length; i++) {
                data[i] = csvRecord.get(i);
            }
            rows.add(data);
        }
    }catch (Exception e){
            throw new RuntimeException();
        }
        return rows;

    }
}
