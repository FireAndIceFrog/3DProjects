package image;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//invert button by Mathew Lawrence 17354272
@SuppressWarnings("serial")
public class InvertButton extends myButton implements MouseListener{
	//this is a child of the parent class "myButton" - a custom button class (i had fun)
	public InvertButton(int x, int y, int width, int height, ImagePanel P) {
		super(x, y, width, height, P);
		// set up the text and mouse listener
		text.setText("Invert");
		
		text.addMouseListener(this);
		this.addMouseListener(this);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
			//System.out.println("Button Pressed!");
			if(p!=null)
				p.invertImage();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		this.changeColor(Color.white, Color.DARK_GRAY);
		this.setColor();
			
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		this.changeColor(Color.black, Color.LIGHT_GRAY);
		this.setColor();
	}
	

}
