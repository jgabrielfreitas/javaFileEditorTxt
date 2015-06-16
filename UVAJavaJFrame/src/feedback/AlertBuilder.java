package feedback;

import javax.swing.JOptionPane;


public class AlertBuilder {
	
	String message;

	public AlertBuilder(String message) {
		this.message = message;
	}
	
	public void build(){
		JOptionPane jOptionPane = new JOptionPane();
		jOptionPane.showMessageDialog(null, message);
	}
	

}
