import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ClientLog {
    private static final StringBuilder log = new StringBuilder();

    public static void log(int productNum, int amount) {
        if (log.length() == 0) {
            log.append("productNum, amount\n");
        }
        log.append(productNum + 1).append(',').append(amount).append("\n");
    }

    public static void exportAsCSV(File txtFile) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(txtFile))) {
            bw.write(log.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}