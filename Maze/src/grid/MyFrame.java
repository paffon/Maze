package grid;

import javax.swing.*;

public class MyFrame extends JFrame {
    MyPanel panel;

    public MyFrame(int rows, int cols, int tileSize) {
        panel = new MyPanel(rows, cols, tileSize);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
