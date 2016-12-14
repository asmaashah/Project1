package calculator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class interestStuff extends JPanel {
	public static final long serialVersionUID = 1L;
	

    final JPanel panel = new JPanel();
	
	public JLabel principalPrint;
	
	public JTextField rateFile;
	public JTextField principalenter;
	public JTextField years;
	
	public JLabel endResult = new JLabel("");
	
	
	public interestStuff() {
		principalPrint = new JLabel(" Principal: ");
		add(principalPrint);
		
		int fileNameTextFieldLength = 5;
		 principalenter = new JTextField(fileNameTextFieldLength);
		add(principalenter);
		
		add (new JLabel ("Rate(Percentage): "));
		int rateFil = 5;
		 rateFile = new JTextField(rateFil);
		add(rateFile);
		
		add (new JLabel ("Years "));
		int other = 5;
		 years = new JTextField(other);
		add(years);
		
		

		/* Adds the simple button */
		JButton SimpleInterest = new JButton("Compute Simple Interest");
		add(SimpleInterest);
		/* Associates action with the button using inner classs */
		SimpleInterest.addActionListener(new MyButtonListener());
		
		/* Adds the compound button */
		JButton compoundInterest = new JButton("Compute Compound Interest");
		/* Adding button action using an anonymous inner class */
		add(compoundInterest);
		
		
		
		remove(endResult);
		

		
		compoundInterest.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				remove(endResult);
				revalidate();
				repaint();
				
				
				double principal =  Double.parseDouble(principalenter.getText());
				double rateFilee = Double.parseDouble(rateFile.getText());
				double yearsNum = Double.parseDouble(years.getText());
		     	
				
				double compInt = (1 + rateFilee/100);
				double returnThis = java.lang.Math.pow(compInt, yearsNum);
				String formattedValue = NumberFormat.getCurrencyInstance().format(principal * returnThis);
				
		     	principalPrint.setText("Principal: ");
		     	endResult = new JLabel(("Computed Compound Interest: "+ formattedValue));
		     	add(endResult);
				
		     	
			}
			
			
			
		});
	}

	/* Listener for the simpleInterest */
	public class MyButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			
			remove(endResult);
			revalidate();
			repaint();
			
			double principal =  Double.parseDouble(principalenter.getText());
			double rateFilee = Double.parseDouble(rateFile.getText());
			double yearsNum = Double.parseDouble(years.getText());

		
			
			double compInt = principal + (principal * (rateFilee/100) * yearsNum);
			String formattedValue = NumberFormat.getCurrencyInstance().format(compInt);
			
	     	principalPrint.setText("Principal: ");
	     	endResult = (new JLabel("Computed Simple Interest: "+ formattedValue));
	     	add(endResult);
	     	
	     	
		}
	}
}
