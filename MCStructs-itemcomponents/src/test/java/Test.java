import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) throws Throwable {
        String clipboardContent = Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
        Matcher matcher = Pattern.compile("\"([^\"]+)\"").matcher(clipboardContent);
        System.out.print("this.sort(");
        while (matcher.find()) {
            System.out.print("\"" + matcher.group(1) + "\", ");
        }
        System.out.print(");");
    }

}
