package moving_smiley;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel implements ActionListener {

    final int PANEL_WIDTH = 500;
    final int PANEL_HEIGHT = 500;
    Image smiley;
    Image bg;
    Timer timer;
    int xVelocity = 2;
    int yVelocity = 3;
    int x = 0;
    int y = 0;

    public MyPanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        smiley = new ImageIcon("smiley.png").getImage();
        bg = new ImageIcon("bg.png").getImage();
        timer = new Timer(10, this);
        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g); // paint background

        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(bg, 0, 0, null);
        g2D.drawImage(smiley, x, y, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(x >= PANEL_WIDTH - smiley.getWidth(null) || x < 0) {
            xVelocity *= -1;
        }
        if(y >= PANEL_HEIGHT - smiley.getHeight(null) || y < 0) {
            yVelocity *= -1;
        }
        x += xVelocity;
        y += yVelocity;
        repaint();
    }
}
