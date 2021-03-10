package main;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

/*  CLass to simplify the texture mapping procedure. The image file is read and
    loaded into a buffered image which is then accessed via normalized coordinates u and v
    */
public class TextureMap {

    BufferedImage image = null;
    int npixx, npixy;

    public TextureMap(String filename) {
        try {
            image = ImageIO.read(new File(filename));
            npixx = image.getWidth();
            npixy = image.getHeight();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
    PART 4: Complete the implementation to return the nearest pixel colour given normalized u and v
     */
    public Color pickColour(double u, double v) {
    	Color c = Color.black;
    	try {
        int xpixel = (int) Math.round(u * (npixx - 1))-1;
        int ypixel = (int) Math.round(v * (npixy - 1))-1;
        int rgb = image.getRGB(xpixel, ypixel);
        c = new Color(rgb);
    	} catch(Exception e) {
//    		e.printStackTrace();
//    		System.out.println("U is: "+u);
//    		System.out.println("V is: "+v);
    	}
    	return c;   // Replace this line so that you return the appropriate colour
    }

}
