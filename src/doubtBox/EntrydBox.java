package doubtBox;


public class EntrydBox 
{
	
	public static void main(String[] args) 
	{
		Splash s = new Splash();
		s.setVisible(true);
		
		Thread t = Thread.currentThread();
		
		try {
			t.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.dispose();
		
		LoginFrame frm = new LoginFrame();
	}
}