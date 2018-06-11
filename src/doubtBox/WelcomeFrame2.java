package doubtBox;

import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class WelcomeFrame2 extends JFrame{

	JLabel lblTdetails,lblIP,lblTime;
	JComboBox cboTdetails;
	JTextField txtIP,txtTime;
	Container c =null;
	DBHandler objDH = new DBHandler();
	
	public WelcomeFrame2()
	{
		c = getContentPane();
		c.setLayout(null);
		ImageIcon ikn = new ImageIcon("BG.jpg");
		JLabel lblBG = new JLabel(ikn);
		lblBG.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		c.add(lblBG);
		Font myFont = new Font("Aerial",Font.PLAIN,25);
		
		lblTdetails = new JLabel("Teacher name: ");
		lblTdetails.setBounds(200, 100, 180, 30);
		lblTdetails.setFont(myFont);
		lblBG.add(lblTdetails);
		
		lblTdetails = new JLabel("IP: ");
		lblTdetails.setBounds(200, 200, 130, 30);
		lblTdetails.setFont(myFont);
		lblBG.add(lblTdetails);
		
		lblTdetails = new JLabel("Time slot: ");
		lblTdetails.setBounds(200, 300, 130, 30);
		lblTdetails.setFont(myFont);
		lblBG.add(lblTdetails);
		

		txtIP=new JTextField();
		txtIP.setEditable(false);
		txtIP.setBounds(330, 200, 200, 30);
		txtIP.setFont(myFont);
		lblBG.add(txtIP);
		
		txtTime=new JTextField();
		txtTime.setEditable(false);
		txtTime.setBounds(330, 300, 200, 30);
		txtTime.setFont(myFont);
		lblBG.add(txtTime);
		
		
		Vector<String>vctrAllTdetails = objDH.getTnameTblteacher();
		cboTdetails = new JComboBox(vctrAllTdetails);
		cboTdetails.setBounds(390, 100, 400, 40);
		cboTdetails.setFont(myFont);
		lblBG.add(cboTdetails);
		cboTdetails.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				String strTname = String.valueOf(cboTdetails.getSelectedItem());
				Teacher t1 = objDH.getTdetais(strTname);
				setTitle(strTname);
				txtTime.setText(t1.getStrTime());
				txtIP.setText(t1.getStrIP());
			}
			
		});
		
		
		JButton btnConnect=new JButton("Connect");
		btnConnect.setBounds(200, 380, 150, 30);
		btnConnect.setFont(myFont);
		lblBG.add(btnConnect);
		
		btnConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new clientChat.ClientSide((String) cboTdetails.getSelectedItem());
			}
		});
		

		JButton signOut = new JButton("Sign out");
		signOut.setBounds(850, 40, 150, 30);
		signOut.setFont(myFont);
		lblBG.add(signOut);
		signOut.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
		
				
				dispose();
				new LoginFrame();
			}
		});
		
		
		setVisible(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setTitle("DoubtBox");

		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we)
			{
				System.exit(0);
			}
		});
	}
}
