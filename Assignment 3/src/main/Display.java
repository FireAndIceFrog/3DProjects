/*

    Class to make it easy to display an image on a JPanel. Also includes a static
    convenience method to write an image to a PNG file.

    There is no need to modify this class.

 */
package main;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Display extends JPanel {

    private BufferedImage image = null;

    // Just call this static method to bring up a JPanel with the image displayed on it
    static void show(BufferedImage image) {
        new Display(image);
    }

    // Call this to write the image to a file in PNG format
    static void write(BufferedImage image, String filename) {
        try {
            ImageIO.write(image, "png", new File(filename));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Display(BufferedImage image) {
        this.image = image;
        JFrame mainFrame = new JFrame("My Toy Box");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainFrame.setContentPane(this);
        if (image == null) {
            mainFrame.setSize(101, 101);
        } else {
            mainFrame.setSize(image.getWidth(), image.getHeight());
        }
        mainFrame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        if (image != null) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(image, null, 0, 0);
        }
    }


}
