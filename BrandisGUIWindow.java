/**
 * COMI2510 - Advanced Java Programming
 * October 24th, 2016
 * 
 * Program designed to take an order from a customer
 * and give a real time total value to what is being
 * ordered.
 * 
 * @author Dylan Grandjean
 */
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class BrandisGUIWindow extends JFrame
{
	//Fields:
	//GUI elements:
	JPanel orderPanel;
	JLabel title;
	JLabel bagelLabel;
	JLabel toppingLabel;
	JLabel coffeeLabel;
	JLabel redeyeLabel;
	JLabel bagelAmount;
	JLabel toppingAmount;
	JLabel coffeeAmount;
	JLabel shotAmount;
	JLabel subtotal;
	JLabel tax;
	JLabel total;
	JSlider shotSlider;
	JComboBox bagelBox;
	JComboBox toppingBox;
	JComboBox coffeeBox;
	JButton calculateButton;
	JButton exitButton;
	
	//Other fields:
	String[] bagels = {"No Bagel", "White bagel", "Wheat bagel", "Sesame bagel"};
	String[] toppings = {"No Toppings", "Cream Cheese", "Butter", "Peach Jelly", "Blueberry Jam"};
	String[] coffees = {"No Coffee", "Regular", "Decaf", "Cappuccino", "Redeye"};
	
	final double TAX = .06;
	double bagelPrice;
	double toppingPrice;
	double coffeePrice;
	double purchase;
	int shotQty;
	boolean bagelSelected = false;
	boolean coffeeSelected = false;
	
	/**
	 * BrandisGUIWindow -- no arg contructor
	 */
	public BrandisGUIWindow()
	{
		//Set frame title
		setTitle("Order Calculator");
		
		//Set layout manager
		setLayout(new BorderLayout());
		
		//setResizable(false);
		
		//Specify action for the close button
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create label
		title = new JLabel("Welcome to Brandi's Bagel House", SwingConstants.CENTER);
		
		CenterPanel centerPanel = new CenterPanel();
		ButtonPanel buttonPanel = new ButtonPanel();
		
		//Adds element to layout
		add(title, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		//Pack, center, and display window
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Inner class which creates the center panel of the
	 * program in order to allow both panels to interact
	 * directly with one another.
	 */
	private class CenterPanel extends JPanel
	{
		/**
		 * CenterPanel -- no arg contructor.
		 */
		private CenterPanel()
		{
			//Set GridLayout manager
			setLayout(new BorderLayout());
			
			//Create panels
			OrderPanel orderPanel = new OrderPanel();
			ReceiptPanel receiptPanel = new ReceiptPanel();
			orderPanel.setReceipt(receiptPanel);
			
			//Add elements to panel
			this.add(orderPanel, BorderLayout.CENTER);
			this.add(receiptPanel, BorderLayout.EAST);
		}
		
		/**
		* Private inner class designed to take 
		* in the customer's order
		*/
		private class OrderPanel extends JPanel
		{
			ReceiptPanel receiptPanel;
			
			/**
			* OrderPanel -- no arg contructor
			*/
			private OrderPanel()
			{
				//Set GridLayout Manager
				setLayout(new GridLayout(8, 1));
			
				//Create elements for the grid
				bagelLabel = new JLabel("Bagel:");
				toppingLabel = new JLabel("Topping:");
				coffeeLabel = new JLabel("Coffee");
				redeyeLabel = new JLabel("Number of shots:");
			
				bagelBox = new JComboBox(bagels);
				bagelBox.addActionListener(new BagelListener());
				toppingBox = new JComboBox(toppings);
				toppingBox.addActionListener(new ToppingListener());
				coffeeBox = new JComboBox(coffees);
				coffeeBox.addActionListener(new CoffeeListener());
			
				shotSlider = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
				shotSlider.setMajorTickSpacing(1);
				shotSlider.setPaintTicks(true);
				shotSlider.setPaintLabels(true);
				shotSlider.addChangeListener(new ShotListener());
				
				//Reloads the window
				buildBox();
			}
			
			/**
			* buildBox method rebuilds the grid in order
			* to update its components.
			*/
			private void buildBox()
			{
				this.removeAll();
					
				//Set border for panel
				this.setBorder(BorderFactory.createTitledBorder("Order"));
				
				//Add elements to grid
				this.add(bagelLabel);
				this.add(bagelBox);
				this.add(toppingLabel);
				this.add(toppingBox);
				//Hids toppings when no bagel is selected and resets topping to "No toppings".
				if(bagelSelected)
				{
					toppingLabel.setVisible(true);
					toppingBox.setVisible(true);
				}
				else
				{
					toppingLabel.setVisible(false);
					toppingBox.setVisible(false);
				}
				this.add(coffeeLabel);
				this.add(coffeeBox);
				this.add(redeyeLabel);
				this.add(shotSlider);
				//Hides shots when redeye is not selected and resets shot quantity to 0.
				if(coffeeSelected)
				{
					redeyeLabel.setVisible(true);
					shotSlider.setVisible(true);
				}
				else
				{
					shotSlider.setValue(0);
					redeyeLabel.setVisible(false);
					shotSlider.setVisible(false);
				}
				
				//Reloads window
				pack();
			}
			
			/**
			 * setReceipt set receiptPanel to a panel object.
			 * @param panel -- ReceiptPanel, object to be assigned to the receiptPanel variable.
			 */
			private void setReceipt(ReceiptPanel panel)
			{
				receiptPanel = panel;
			}
			
			//---------- OrderPanel Class Action Listeners ----------
			/**
			* Private inner class that handles the event that is generated
			* when the user uses the bagel comboBox
			*/
			private class BagelListener implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					int selection = bagelBox.getSelectedIndex();
					if(selection == 0)
					{
						bagelSelected = false;
						bagelPrice = 0;
						toppingBox.setSelectedIndex(0);
					}
					else
					{
						bagelSelected = true;
						bagelPrice = 1.5;
					}
					receiptPanel.buildReceipt();
					buildBox();
				}
			}
			
			/**
			* Private inner class that handles the event that is generated
			* when the user uses the toppings comboBox
			*/
			private class ToppingListener implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{	
					int selection = toppingBox.getSelectedIndex();
					if(selection == 0)
						toppingPrice = 0;
					else
						toppingPrice = 0.5;
					receiptPanel.buildReceipt();
					buildBox();
				}
			}
			
			/**
			* Private inner class that handles the event that is generated
			* when the user uses the coffee comboBox
			*/
			private class CoffeeListener implements ActionListener
			{
				public void actionPerformed(ActionEvent e)
				{
					int selection = coffeeBox.getSelectedIndex();
					if(selection == 0)
					{
						coffeeSelected = false;
						coffeePrice = 0;
						shotQty = 0;
					}
					else if(selection <= 3)
					{
						coffeeSelected = false;
						coffeePrice = 1.5;
					}
					else
					{
						coffeeSelected = true;
						coffeePrice = 1.5;
					}
					receiptPanel.buildReceipt();
					buildBox();
				}
			}
			
			/**
			 * Private inner class that handles the event that is generated
			 * when the user selects slides through the bar on the window.
			 */
			private class ShotListener implements ChangeListener
			{
				public void stateChanged(ChangeEvent e)
				{
					shotQty = shotSlider.getValue();
					receiptPanel.buildReceipt();
					buildBox();
				}
			}
		}
		
		/**
		* Private inner class designed to display
		* the customer's order
		*/
		private class ReceiptPanel extends JPanel
		{
			/**
			 * ReceiptPanel -- no arg constructor.
			 */
			private ReceiptPanel()
			{
				//Set layout manager
				setLayout(new GridLayout(20, 1));
			
				buildReceipt();
			}
			
			/**
			 * buildReceipt method allows the ReceiptPanel labels
			 * to be reset and updted whenever an actionListener of
			 * OrderPanel is activated through the receiptPanel object
			 * passed to the ObjectPanel class.
			 */
			public void buildReceipt()
			{
				this.removeAll();
				
				//Create labels
				bagelAmount = new JLabel(String.format("   %s $%,.2f    ", (String)bagelBox.getSelectedItem(), bagelPrice));
				toppingAmount = new JLabel(String.format("   %s $%,.2f    ", (String)toppingBox.getSelectedItem(), toppingPrice));
				coffeeAmount = new JLabel(String.format("   %s $%,.2f    ", (String)coffeeBox.getSelectedItem(), coffeePrice + (shotQty * 0.5)));
				shotAmount = new JLabel(String.format("   (Includes %d shots) $%,.2f    ", shotQty, (0.5 * shotQty)));
				
				purchase = bagelPrice + toppingPrice + coffeePrice + (0.5 * shotQty);
				
				subtotal = new JLabel(String.format("   Subtotal: $%, .2f", (purchase)));
				tax = new JLabel(String.format("   Tax: $%,.2f", (purchase * TAX)));
				total = new JLabel(String.format("   Total: $%,.2f", purchase + (purchase * TAX)));
				
				//Add labels to panel
				this.add(bagelAmount);
				this.add(toppingAmount);
				this.add(coffeeAmount);
				this.add(shotAmount);
				
				if(bagelPrice != 0 || coffeePrice != 0)
				{
					this.add(new JLabel(""));
					this.add(subtotal);
					this.add(tax);
					this.add(total);
				}
			}
		}
	}
	
	/**
	 * Private inner class designed to display
	 * buttons to the customer.
	 */
	private class ButtonPanel extends JPanel
	{
		private ButtonPanel()
		{
			//Create buttons and add actionListeners
			calculateButton = new JButton("Calculate");
			calculateButton.addActionListener(new CalculateListener());
			exitButton = new JButton("Exit");
			exitButton.addActionListener(new ExitListener());
			
			this.add(calculateButton);
			this.add(exitButton);
		}
		
		//---------- ButtonPanel Class Action Listeners ----------
		/**
		* Private inner class that handles the event that is generated
		* when the user clicks the Exit button.
		*/
		private class ExitListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		}
		
		/**
		* Private inner class that handles the event that is generated
		* when the user clicks the Calculate button.
		*/
		private class CalculateListener implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if(bagelPrice == 0 && coffeePrice == 0)
					JOptionPane.showMessageDialog(null, "Your order is empty.", "Error", JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, String.format("Subtotal: %,.2f\nTax: %,.2f\nTotal: %,.2f", purchase, (purchase * TAX), (purchase + (purchase * TAX))));
			}
		}
	}
	
	/**
	 * The main method creates an instance of the MenuWindow class, which 
	 * causes it to display its window
	 */
	public static void main(String[] args)
	{
		new BrandisGUIWindow();
	}
}
