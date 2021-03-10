package image;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JTextPane;
//My Button created by Mathew Lawrence 17354272
//Parent button created by me - i thought it would be fun and it totally was
@SuppressWarnings("serial")
public class myButton extends JPanel{
	//this is the parent class for all the following buttons, it is the base class simmilar to JButton containing the basic setup and controls
	//this is required for all children - the will use the imagePanels invert() and flip() functions
	protected ImagePanel p;
	protected int x,y,width,height;
	protected Color back;
	protected Color front;
	protected JTextPane text;
	
	protected void changeColor(Color front, Color back) {
		//when called, changes the colors
		this.front = front;
		this.back = back;
	}
	protected void setColor() {
		//when called, changes the foreground and background 
		if(text!=null) {
			text.setForeground(front);
			text.setBackground(back);
		}
		this.setBackground(back);
	}

	public myButton(int x,int  y, int width,int  height, ImagePanel P) {
		changeColor(Color.black, Color.LIGHT_GRAY);
		//I thought about using rectangle bounds but I prefer to use the classic X, Y, WIdth, Height
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setBounds(x, y, width, height);
		//p is for panel
		this.p = P;

		//Create a text pane (simmilar to textField except it automatically assigns the columns and rows - meaning the children classes do not need to use them
		text = new JTextPane();
		text.setDragEnabled(false);
		text.setText("Press me!");
		text.setEditable(false);

		
		this.add(text);
		setColor();
	}

	
	
	
	
	
}
