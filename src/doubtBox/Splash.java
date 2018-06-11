package doubtBox;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;


public class Splash extends JFrame {
	
	private JLabel imglbl;
	private ImageIcon img;
	private static JProgressBar pbar;
	Thread t = null;
	
	
	public Splash()
	{
		setSize(550,350);
		setDefaultCloseOperation(2);
		setLocationRelativeTo(null); // for center of screen
		setUndecorated(true); // hide the TitleBar
		
		img = new ImageIcon("Specter.jpg");
		imglbl = new JLabel(img);
		add(imglbl);
		
		setLayout(null);
		
		pbar = new JProgressBar();
		pbar.setMinimum(0);
		pbar.setMaximum(100);
		pbar.setStringPainted(true);
		pbar.setForeground(Color.black);
		imglbl.setBounds(0,0,550,350);
		add(pbar);
		pbar.setBounds(0, 320, 550, 30);
		
		Thread t = new Thread()
		{
			public void run()
			{
				int i = 0;
				while(i<=100)
				{
					pbar.setValue(i);
					
					try {
						sleep(45);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					i++;
				}
			}
		};
		t.start();
	}
}
