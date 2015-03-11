import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        Reader reader = null;
        Writer writer = null;
        TreeMap<String, Integer> data = new TreeMap<String, Integer>();
        int fullNumber = 0;

        //Get data.
        try {
            StringBuilder builder = new StringBuilder();
            char tempChar;
            boolean inWord = false;
            reader = new InputStreamReader(new FileInputStream(args[0]));
            do {
                tempChar = (char) reader.read();
                if (Character.isLetterOrDigit(tempChar))  {
                    builder.append(tempChar);
                    inWord = true;

                } else {
                    inWord = false;
                }
                if (false == reader.ready())
                {
                    inWord = false;
                }
                if ((builder.length() != 0) && !inWord) {
                    fullNumber++;
                    Integer j = 1;
                    if (data.containsKey(builder.toString())) {
                        j = data.get(builder.toString()) + 1;
                    }
                    data.put(builder.toString(), j);
                    builder.delete(0, builder.length());
                }
            } while (reader.ready());

        } catch (FileNotFoundException e) {
            System.err.println("File not found:" + e.getLocalizedMessage());
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        //Sort result
        //Define Number Format
        NumberFormat formatter = NumberFormat.getInstance();
        formatter.setMinimumIntegerDigits(6);

        TreeMap<String, String> valMap = new TreeMap<String, String>();

        int size = data.size();
        String temp;
        Integer tempInt;
        float percent;

        for(int i = 0; i < size; i++) {
            temp = data.lastEntry().getKey();
            tempInt = data.lastEntry().getValue();
            percent = tempInt.floatValue() / fullNumber;
            data.remove(data.lastKey());
            valMap.put(formatter.format(tempInt) + "," + temp, temp + "," + tempInt.intValue() + "," + percent);
        }

        // Write data.
        try {
            writer = new OutputStreamWriter(new FileOutputStream("output.csv"));

            for (int i = 0; i < size; i++) {
                writer.write(valMap.lastEntry().getValue());
                writer.write('\n');
                valMap.remove(valMap.lastKey());
                writer.flush();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error while writing file: " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.err.println("Error while writing file: " + e.getLocalizedMessage());
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}

