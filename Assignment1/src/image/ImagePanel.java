package image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//Class to read and display an image. By Mathew Lawrence 17354272
@SuppressWarnings("serial")
public class ImagePanel extends JPanel { 

	 BufferedImage image = null;
	 private int x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT;
	 
	 public ImagePanel(int x, int y) {
		 this.x = x;
		 this.y = y;
		 this.setLocation(x, y);
		 x = 0;
		 y = 0;
		 DEFAULT_WIDTH = 300;
		 DEFAULT_HEIGHT = 300;
	 }
	
	 // A method to read an image from a file handle object. Note here
	 // that we MUST handle the IOException
	 public void getImage(File imageFile) {
		 image = null;
		 //if the image file is null, just set the bounds - dont load anything else
				if(imageFile != null) {
			     try {
			    	 //get the file and insert it into the buffered image
			    	 System.out.println("Path: "+imageFile);
			         image = ImageIO.read(imageFile);
			         repaint();
			         revalidate();
			     }
			     catch (IOException e) {
			         e.printStackTrace();
			     }
		 } else {
			 this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
			
		 }
	 }
	public boolean checkBounds(int x, int y) {
		/*boolean to check if the X and Y are within the co-ordinates of the box 
			this removes errors caused by the difference of width/height of the panel compared to the image */
			if(x>this.getHeight())
				return false;
			if(y>this.getWidth())
				return false;
			if(x<this.getX())
				return false;
			if(y<this.getY())
				return false;
			return true;
	}
	 public int[] getColor(int x, int y){
		 //returns the color of the pixel at co-ordinates X Y
		 if(image != null) {
			 int[] returnInt = new int[3];
			 //to save face
			 try {
				 //this returns the integer code of the pixel at x y
				 int clr=  image.getRGB(x,y); 
				 //decode the pixel colors
				returnInt[0]   = (clr & 0x00ff0000) >> 16;
				returnInt[1] = (clr & 0x0000ff00) >> 8;
				returnInt[2]  =  clr & 0x000000ff;

			 } catch (Exception e) {
				return null; 
			 }
		  return returnInt;
		 }
		 return null;
	 }
	
	 // This gets called by the JVM whenever it needs to do so. For
	 // example, when rendering a JFrame, this method will be called
	 // for all JPanels that have been added to the JFrame. Here we
	 // override the paintComponent() method in the parent class (which
	 // actually does not nothing
	 public void paintComponent(Graphics g) {
	     Graphics2D g2 = (Graphics2D)g;
	
	     // If there is an image to draw, then draw it!
	     if (image != null) {
	         g2.drawImage(image, 0, 0, null);
	         this.setBounds(x,y,image.getWidth(),image.getHeight());
	     }
	 }
	 public void invertImage() {
		 //function to invert image using the pixel method
		 if(image!=null) {
			 for(int i = 0; i<image.getWidth(); ++i) {			//row
				 for(int j = 0; j<image.getHeight(); ++j) {		//cols
					 //swap each pixel in column major style
					 Color myColor = new Color(image.getRGB(i, j));
					 //to invert, minus 255 by the original
					 myColor = new Color(255- myColor.getRed(),255-myColor.getGreen(), 255-myColor.getBlue());
					 image.setRGB(i, j, myColor.hashCode());
				 }
			 }
		 }
	 }

	 public void VFlipImage() {
		 //vertical flip using affix transform method
		 /*I tried to use a colum/row major method however the image.setRGB didnt work correctly and broke my image*/
		 if(image!=null) {
			 AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
			 tx.translate(0, -image.getHeight(null));
			// using the negative of the image height, this sets the stage for all pixels to be rotated
			 AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			 image = op.filter(image, null);
			 
		 }
	 }
	 public void HFlipImage() {
		 //horizontal flip method
		 if(image!=null) {
			 AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			 tx.translate(-image.getWidth(null), 0);
			 AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			 image = op.filter(image, null);
			 
		 }
	 }
 
 
 



}