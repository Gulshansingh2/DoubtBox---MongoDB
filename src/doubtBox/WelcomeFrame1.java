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

public class WelcomeFrame1 extends JFrame
{
	JLabel  lblTname,lblIP,lblTime;
	JTextField txtTname,txtIP,txtIP1,txtTime;
	Container c =null;
	DBHandler objDH = new DBHandler();
	
	public WelcomeFrame1()
	{
		c = getContentPane();
		c.setLayout(null);
		ImageIcon ikn = new ImageIcon("BG.jpg");
		JLabel lblBG = new JLabel(ikn);
		lblBG.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		c.add(lblBG);
		Font myFont = new Font("Aerial",Font.PLAIN,25);
	
		//=================
		
		lblTname=new JLabel("Name: ");
		lblTname.setBounds(200,130, 130, 30);
		lblTname.setFont(myFont);
		lblBG.add(lblTname);
		txtTname=new JTextField();
		txtTname.setBounds(320, 130, 290, 30);
		txtTname.setFont(myFont);
		lblBG.add(txtTname);		
		//=================
			
		lblIP=new JLabel("IP: ");
		lblIP.setBounds(200,190, 130, 30);
		lblIP.setFont(myFont);
		lblBG.add(lblIP);
		txtIP=new JTextField();
		txtIP.setBounds(320, 190, 290, 30);
		txtIP.setFont(myFont);
		lblBG.add(txtIP);		
		//=================
				
		lblTime=new JLabel("Time");
		lblTime.setBounds(200,240, 130, 30);
		lblTime.setFont(myFont);
		lblBG.add(lblTime);
		txtTime=new JTextField();
		txtTime.setBounds(320, 240, 290, 30);
		txtTime.setFont(myFont);
		lblBG.add(txtTime);		
		
		JButton btnInsert=new JButton("insert");
		btnInsert.setBounds(200, 300, 130, 40);
		btnInsert.setFont(myFont);
		lblBG.add(btnInsert);
		
		btnInsert.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String strtName, strIP, strTime;
				strtName=txtTname.getText();
				strIP=txtIP.getText();
				strTime=txtTime.getText();
				objDH.insertIntoTblteacher(strtName, strIP, strTime);
				JOptionPane.showMessageDialog(null, "Data inserted");
				
			}
		});
		

		JButton btnDel=new JButton("delete");
		btnDel.setBounds(200, 380, 130, 40);
		btnDel.setFont(myFont);
		lblBG.add(btnDel);
		
		btnDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String strIP=txtIP1.getText();
				objDH.deleteFromTblteacher(strIP);
				txtTname.setText("");
				txtIP.setText("");
				txtIP1.setText("");
				txtTime.setText("");
				JOptionPane.showMessageDialog(null, "Data deleted");
			}
		});
		
		txtIP1=new JTextField();
		txtIP1.setBounds(360, 380, 200, 30);
		txtIP1.setFont(myFont);
		lblBG.add(txtIP1);
		
		JButton btnUpdate=new JButton("update");
		btnUpdate.setBounds(480, 300, 130, 40);
		btnUpdate.setFont(myFont);
		lblBG.add(btnUpdate);
		
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String strtName, strIP, strTime;
				strtName=txtTname.getText();
				strIP=txtIP.getText();
				strTime=txtTime.getText();
				objDH.updateIntoTblteacher(strtName, strIP, strTime);
				JOptionPane.showMessageDialog(null, "Data updated");
			}
		});
		
		
		JButton signOut = new JButton("Sign out");
		signOut.setBounds(650, 40, 150, 30);
		signOut.setFont(myFont);
		lblBG.add(signOut);
		signOut.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
		
				
				dispose();
				new LoginFrame();
			}
		});
		
		JButton db =new JButton("Open DoubtBox");
		db.setBounds(200, 450, 250, 30);
		db.setFont(myFont);
		lblBG.add(db);
		
		db.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new serverChat.ServerSide();
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

