package image;



import javax.swing.JPanel;
import javax.swing.JTextArea;
//RGB Box (for assignment R G B display)
@SuppressWarnings("serial")
public class RGBbox extends JPanel{
	private JTextArea text = null;

	public RGBbox(int width, int height) {
		//this is a basic text box within a jpanel - this is for flexibility so it can be placed anywhere required.
		setSize(width,height);
		text = new JTextArea(3, 10);
		text.setLocation(0,0);
		text.setEnabled(true);
		text.setEditable(false);
		text.setVisible(true);
		add(text);
		
	}
	public void setText(int r, int g, int b) {
		text.setText("Red: "+r+"\nGreen: "+g+"\nBlue: "+b);
		
	}
	

}
