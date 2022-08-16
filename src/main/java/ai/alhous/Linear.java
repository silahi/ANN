package ai.alhous;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Linear {
    public static void main(String[] args) throws IOException {
        save("x","y");
       
    }

    static void save(String x, String y) throws IOException {
        BufferedWriter bw = new BufferedWriter(
                new FileWriter(new File("C:/Users/Ali/Documents/dl4j/pronto/data", "test.csv"), true));
        bw.write(x + "," + y);
        bw.write("\n");
        bw.close();
    }
}
