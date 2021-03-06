package image;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class VertFlipButton extends myButton implements MouseListener{

	public VertFlipButton(int x, int y, int width, int height, ImagePanel P) {
		super(x, y, width, height, P);
		// TODO Auto-generated constructor stub
		text.setText("Vertical Flip");
		text.addMouseListener(this);
		this.addMouseListener(this);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
			if(p!=null)
				p.VFlipImage();
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
