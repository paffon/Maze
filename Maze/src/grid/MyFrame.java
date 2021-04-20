package grid;

import javax.swing.*;
import java.io.FileNotFoundException;

public class MyFrame extends JFrame {
    MyPanel panel;

    public MyFrame(String searchKind) throws FileNotFoundException {
        panel = new MyPanel(searchKind);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
