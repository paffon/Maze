package grid;

import javax.swing.*;

public class MyFrame extends JFrame {
    MyPanel panel;

    public MyFrame(int rows, int cols, int squareSize) {
        panel = new MyPanel(rows, cols, squareSize);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
