import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;


    List<String> columnLabels = new ArrayList<>();
    // odwzorowanie: nazwa kolumny -> numer kolumny
    Map<String,Integer> columnLabelsToInt = new HashMap<>();

    /**
     *
     * @param filename - nazwa pliku
     * @param delimiter - separator pól
     * @param hasHeader - czy plik ma wiersz nagłówkowy
     */

    public CSVReader(String filename) throws IOException {
        reader =  new BufferedReader(new InputStreamReader( new FileInputStream(filename),
                Charset.forName("Cp1250")));
    }

    public CSVReader(String filename,String delimiter) throws IOException {
        this(filename);
        this.delimiter = delimiter;

    }

    public CSVReader(String filename,String delimiter,boolean hasHeader) throws IOException {
        this(filename, delimiter);

        this.hasHeader = hasHeader;
        if(hasHeader) parseHeader();
    }
    //...
    void parseHeader() throws IOException {
        // wczytaj wiersz
        String line  = reader.readLine();
        if(line==null){
            return;
        }
        // podziel na pola
        String[]header = line.split(delimiter);
        // przetwarzaj dane w wierszu
        for(int i=0;i<header.length;i++){
            // dodaj nazwy kolumn do columnLabels i numery do columnLabelsToInt
            columnLabels.add(header[i]);
            columnLabelsToInt.put(header[i],i);
        }
    }

    List<String> getColumnLabels()
    {
        return columnLabels;
    }

    int getRecordLength()
    {
        return current.length;
    }

    String[]current;
    boolean next() throws IOException {
        String line  = reader.readLine();
        if(line==null){
            return false;
        }
        current = line.split(delimiter);
        return true;
    }

    String get(int x)
    {
        return current[x];
    }
    String get(String s)
    {
        return current[columnLabelsToInt.get(s)];
    }

//"/home/eaiib/mielkema/Pobrane/with-header.csv"
    public static void main(String[] args) throws IOException {
        CSVReader reader = new CSVReader("C:/Users/Mojmira/Desktop/java/po2019-mielke-magdalena/lab6/CSV/src/with-header.csv",
                            ";",true);
        while(reader.next()){

            String name = reader.get("imię");


            System.out.println(name);
        }
    }
}