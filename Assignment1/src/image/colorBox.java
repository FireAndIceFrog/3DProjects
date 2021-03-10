package image;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JTextArea;

//Color Display box by Mathew Lawrence 17354272
@SuppressWarnings("serial")
public class colorBox extends JPanel{
	private JTextArea text = null;
	private JTextArea box = null;
	private RGBbox rgbBox = null;
	public colorBox(int width, int height) {
		//set the panel size
		setSize(width,height);
		setLocation(0,0);
		

		//text will display the X and Y location of the mouse in regards to the image panel
		text = new JTextArea(2, 7);
		text.setLocation(0,0);
		text.setEnabled(true);
		text.setEditable(false);
		text.setVisible(true);
		//this is a color box - a simple JPanel child which changes its background when called
		box = new JTextArea(2, 4);
		box.setLocation(text.getWidth(),text.getHeight());
		box.setEnabled(true);
		box.setEditable(false);
		box.setVisible(true);

		//RGB Box is the box which will display "RED: x GREEN: x BLUE X"
		this.rgbBox = new RGBbox(300,300);
		rgbBox.setLocation(box.getWidth(), box.getHeight());
		
		add(text);
		add(box);
		add(rgbBox);
		
	}
	void setMouseXY(int x, int y){
		//The basic idea is that every mouse event, this will change.
		text.setText("X: "+x+"\nY: "+y);
		

	}
	void setBackground(int[] colors){
		//The frame will call this class - change the XY box, the COLOR box and the RGB box
		rgbBox.setText(colors[0], colors[1], colors[2]); 
		this.setBackground(new Color(colors[0], colors[1], colors[2]));
		box.setBackground(new Color(colors[0], colors[1], colors[2]));
		
		repaint();
		
		

	}
	
	
	public void paintComponent(Graphics g) {
		
	}

}
