package csl;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ClipBordTest {

    public static void main(String... args) throws Exception {

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable transferable = clipboard.getContents(null);
        if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            String str = transferable.getTransferData(DataFlavor.stringFlavor).toString();
            System.out.println(str);
        }

        Transferable content=new StringSelection("this is test data in clipboard");
        clipboard.setContents(content,null);
    }
}
