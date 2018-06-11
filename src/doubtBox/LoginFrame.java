package doubtBox;

import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class LoginFrame extends JFrame{
	
	JLabel lblID, lblPwd;
	JTextField txtUid;
	JPasswordField txtPwd;
	JRadioButton r1;    
	JRadioButton r2;          
	Container c = null;
	DBHandler objDH = new DBHandler();
	
	
	public LoginFrame()
	{
		setLayout(null);
		c = getContentPane();
		//c.setBackground(Color.lightGray);
		ImageIcon ikn = new ImageIcon("BG.jpg");
		JLabel lblBG = new JLabel(ikn);
		lblBG.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		c.add(lblBG);
        
		
		//--------------------- Labels ------------------------
		lblID = new JLabel("ID: ");
		lblID.setBounds(80, 80, 150, 40);
		Font myFont = new Font("Aerial",Font.PLAIN,30);
		lblID.setFont(myFont);
		lblBG.add(lblID);
		
		lblPwd = new JLabel("Password: ");
		lblPwd.setBounds(80, 140, 150, 40);
		lblPwd.setFont(myFont);
		lblBG.add(lblPwd);
		
		
		//----------------------- Text Fields -------------------
		txtUid = new JTextField();
		txtUid.setBounds(230, 80, 200, 40);
		myFont = new Font("Aerial",Font.PLAIN,15);
		txtUid.setFont(myFont);
		lblBG.add(txtUid);
		txtPwd = new JPasswordField();
		txtPwd.setBounds(230, 140, 200, 40);
		lblBG.add(txtPwd);
		
		//----------------------- Radio buttons -------------------		
		r1=new JRadioButton("Teacher"); 
		r2=new JRadioButton("Student");
		
		r1.setBounds(230,200,100,30);
		r2.setBounds(330,200,100,30); 
		r1.setFont(myFont);
		r2.setFont(myFont);
		ButtonGroup bg=new ButtonGroup();    
		bg.add(r1);bg.add(r2);    
		lblBG.add(r1);lblBG.add(r2);
		
		
		//----------------------- Buttons -------------------
		ImageIcon iknBtnLogin = new ImageIcon("button_login.png");
		JButton btnLogin = new JButton(iknBtnLogin);
		btnLogin.setBounds(330, 270, 102, 40);
		lblBG.add(btnLogin);
		btnLogin.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String strUid, strPwd;
				strUid = txtUid.getText();
				strPwd = String.valueOf(txtPwd.getPassword());
				boolean res1 = false, res2 = false;
				if(r1.isSelected())
				{
					res1 = objDH.isValidTeacher(strUid, strPwd);
					//System.out.println(res1);
				}
				else if(r2.isSelected())
				{
				  res2 = objDH.isValidStudent(strUid, strPwd);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please select one");
				}
				
					
				if(res1)
				{
					
						WelcomeFrame1 frm = new WelcomeFrame1();
						dispose();
				}		
				else if(res2)
				{
					
						WelcomeFrame2 frm = new WelcomeFrame2();
						dispose();
				}
				
				else if (!res1 && !res2)
				{
					//System.out.println("false");
					JOptionPane.showMessageDialog(null, "Please enter valid Username and Password");
				}
			}
			
			
		});
		
		setTitle("DoubtBox");
		setVisible(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(2);
	}
}
