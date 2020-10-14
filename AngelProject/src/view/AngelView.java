package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import model.User;
import model.UserFactory;

public class AngelView implements View {
	//frames in use
	private JFrame loginFrame;
	private JFrame createUserFrame;
	private JFrame createCustomerFrame;
	private JFrame createBusinessOwnerFrame;
	private JFrame customerMainFrame;
	private JFrame businessOwnerMainFrame;
	private JFrame businessPageFrame;
	private JFrame editInfoFrame;
	private JFrame editServicesFrame;
	private JFrame showAppointsFrame;
	
	//some private used variables/fields.
	private JTextField loginUsernameField;
	private JPasswordField loginPasswordField;
	private DefaultListModel<String> showListMatched;//users matched sort	
	 private PropertyChangeSupport property;
	private DefaultListModel<String> showListInterest;//users don't matched sort,but can interest user.
	private ArrayList<User> sortedUsersList;//list of users showed on display

    public static SimpleDateFormat angelFormatDate=new SimpleDateFormat("dd.MM.yyyy");
    public static SimpleDateFormat angelFormatDateTime=new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public static SimpleDateFormat angelFormatTime=new SimpleDateFormat("HH:mm");
    
    public static String currentUser;//username of user logged in
    private  User currentUserData;//some public info of user logged in
    private boolean existFlag;//flag show if username exist or not,for login use.
    
    /*
     * Constructor	
     */
    public AngelView() {
		property=new PropertyChangeSupport(this);
		currentUser=null;
		currentUserData=null;	
	}
    /*
     * add listener
     */
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		property.addPropertyChangeListener(propertyChangeListener);
	}
	


    /*
     * Start view,login frame
     */
	public void start() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
					login();
			}
		});
		
	}
	
	
	/*
	 * 
	 * Login frame:
	 * 
	 */
	public void login() {
		
		    loginFrame=new JFrame();		    
		    loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    loginFrame.setBounds(300, 100, 1280, 800);
		    JPanel contentPane = new JPanel();
		    contentPane.setBackground(new Color(250, 235, 215));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			loginFrame.setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JPanel panel = new JPanel();
			panel.setBackground(new Color(0,0,0));
			panel.setBounds(0, 0, 608, 772);
			contentPane.add(panel);
			panel.setLayout(null);
			
			JLabel iconAngel = new JLabel("");
			iconAngel.setIcon(new ImageIcon(AngelView.class.getResource("/logo.png")));
			iconAngel.setBounds(0, 87, 608, 563);
			panel.add(iconAngel);
			
			JLabel lblNewLabel = new JLabel("Login");
			lblNewLabel.setForeground(Color.BLACK);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 99));
			lblNewLabel.setBounds(763, 83, 359, 153);
			contentPane.add(lblNewLabel);
			
			JLabel lblUser = new JLabel("Username:");
			lblUser.setForeground(Color.DARK_GRAY);
			lblUser.setFont(new Font("Tahoma", Font.BOLD, 35));
			lblUser.setBounds(623, 272, 191, 49);
			contentPane.add(lblUser);
			
			loginUsernameField = new JTextField();
			loginUsernameField.setFont(new Font("Tahoma", Font.PLAIN, 22));
			loginUsernameField.setBounds(823, 282, 299, 39);
			contentPane.add(loginUsernameField);
			loginUsernameField.setColumns(10);
			
			JLabel lblPassword = new JLabel("Password:");
			lblPassword.setForeground(Color.DARK_GRAY);
			lblPassword.setFont(new Font("Tahoma", Font.BOLD, 35));
			lblPassword.setBounds(623, 337, 191, 49);
			contentPane.add(lblPassword);
			
			loginPasswordField = new JPasswordField();
			loginPasswordField.setToolTipText("  ");
			loginPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 25));
			loginPasswordField.setBounds(823, 347, 299, 39);
			contentPane.add(loginPasswordField);
			
			JButton signInBtn = new JButton("Sign in");
			signInBtn.setForeground(UIManager.getColor("Button.disabledShadow"));
			signInBtn.setFont(new Font("Arial", Font.BOLD, 20));
			signInBtn.setBounds(823, 436, 130, 39);
			contentPane.add(signInBtn);
			
			JButton newUserBtn = new JButton("New user");
			newUserBtn.setFont(new Font("Arial", Font.BOLD, 20));
			newUserBtn.setForeground(UIManager.getColor("Button.disabledShadow"));
			newUserBtn.setBounds(992, 436, 130, 39);
			contentPane.add(newUserBtn);
			
			/*
			 * 
			 * Login frame actions:
			 * 
			 */

			//Sign in button clicked
			signInBtn.addActionListener(new ActionListener() {				
				
				public void actionPerformed(ActionEvent e) {
					
					if(!(loginPasswordField.getText().equals("") || loginUsernameField.getText().equals(""))) {//if fields not empty	
						
					    String[] loginData=new String[2];
					    loginData[0]=loginUsernameField.getText();
					    loginData[1]=loginPasswordField.getText();
					    property.firePropertyChange(new PropertyChangeEvent(this, "Confirm sign in",null,loginData));//call observer to check login correctness
					    loginUsernameField.setText("");
					    loginPasswordField.setText("");
					}
					else {
						if(loginUsernameField.getText().equals("")){
							JOptionPane.showMessageDialog(contentPane, "Please enter username","warning",JOptionPane.WARNING_MESSAGE);
							loginUsernameField.setText("");
						}
						else {
							JOptionPane.showMessageDialog(contentPane, "Please enter password","warning",JOptionPane.WARNING_MESSAGE);
							 loginPasswordField.setText("");
						}
					}			  				
				}
			});
			
			//new user button clicked...
			newUserBtn.addActionListener(new ActionListener() {			
				public void actionPerformed(ActionEvent e) {
					createUser();
					loginFrame.dispose();
				}
			});	
			
			loginFrame.setVisible(true);
		
	}
	/*
	 * 
	 * New user frame
	 * 
	 */
	public void createUser() {
		
		createUserFrame=new JFrame();
		createUserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		createUserFrame.setBounds(400, 150, 1000, 800);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 240, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		createUserFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCreateNewUser = new JLabel("Create new user:");
		lblCreateNewUser.setFont(new Font("Tahoma", Font.BOLD, 42));
		lblCreateNewUser.setBounds(94, 27, 477, 103);
		contentPane.add(lblCreateNewUser);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Arial", Font.PLAIN, 20));
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Business owner", "Customer"}));
		comboBox.setBounds(291, 154, 239, 47);
		contentPane.add(comboBox);
		
		JLabel lblTypeOfUser = new JLabel("Type of user:");
		lblTypeOfUser.setBackground(new Color(189, 183, 107));
		lblTypeOfUser.setFont(new Font("Arial Black", Font.BOLD, 20));
		lblTypeOfUser.setBounds(46, 147, 169, 58);
		contentPane.add(lblTypeOfUser);
		
		JLabel lblUsername = new JLabel("Enter username:");
		lblUsername.setBackground(new Color(152, 251, 152));
		lblUsername.setFont(new Font("Arial Black", Font.BOLD, 20));
		lblUsername.setBounds(46, 212, 193, 47);
		contentPane.add(lblUsername);
		
		JLabel lblEnterPassword = new JLabel("Enter password:");
		lblEnterPassword.setBackground(new Color(255, 255, 255));
		lblEnterPassword.setFont(new Font("Arial Black", Font.BOLD, 20));
		lblEnterPassword.setBounds(46, 268, 193, 47);
		contentPane.add(lblEnterPassword);
		
		JPasswordField createPasswordField = new JPasswordField();
		createPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 22));
		createPasswordField.setBounds(291, 273, 254, 35);
		contentPane.add(createPasswordField);
		
		JTextField createUsernameField = new JTextField();
		createUsernameField.setFont(new Font("Tahoma", Font.PLAIN, 22));
		createUsernameField.setBounds(291, 217, 254, 35);
		contentPane.add(createUsernameField);
		createUsernameField.setColumns(10);
		
		JLabel lblConfirmPassword = new JLabel("Confirm password:");
		lblConfirmPassword .setBackground(new Color(255, 255, 255));
		lblConfirmPassword .setFont(new Font("Arial Black", Font.BOLD, 20));
		lblConfirmPassword .setBounds(46, 326, 230, 47);
		contentPane.add(lblConfirmPassword );
		
		JPasswordField confirmPasswordField = new JPasswordField();
		confirmPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 22));
		confirmPasswordField.setBounds(291, 331, 254, 35);
		contentPane.add(confirmPasswordField);
		
		JButton btnNext = new JButton("Next");
		btnNext.setBackground(new Color(211, 211, 211));
		btnNext.setFont(new Font("Arial", Font.PLAIN, 20));
		btnNext.setBounds(291, 594, 115, 29);
		contentPane.add(btnNext);
		contentPane.add(btnNext );
		
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(new Color(211, 211, 211));
		btnExit.setFont(new Font("Arial", Font.PLAIN, 20));
		btnExit.setBounds(430, 594, 115, 29);
		contentPane.add(btnExit);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(AngelView.class.getResource("/nail.jpg")));
		lblNewLabel_1.setBounds(82, 357, 380, 234);
		contentPane.add(lblNewLabel_1);
		
		/*
		 * 
		 * New user frame actions:
		 * 
		 */
		
		//Exit clicked
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createUserFrame.dispose();
				login();
			}
		});
		
		//Next clicked
		btnNext.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) { 
				//if username entered username and both password fields equal
				if(createUsernameField.getText().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Please enter username","",JOptionPane.WARNING_MESSAGE);
					createUsernameField.setText("");
					return;			
				}
				
				if(createUsernameField.getText().matches(".*\\s+.*")) {
					JOptionPane.showMessageDialog(contentPane, "Please enter username without spaces","",JOptionPane.WARNING_MESSAGE);
					createUsernameField.setText("");
					return;			
				}	
				if(createPasswordField .getText().equals(confirmPasswordField.getText()) && !(confirmPasswordField.getText().equals("")))	{			
				    String[] tempString;             //[0]=type of user,[1]=user name,[2]=password.
				   
					if(comboBox.getSelectedItem().equals("Business owner")) {
							tempString=new String[3];					
							tempString[0]="Business owner";				
							tempString[1]=createUsernameField.getText();	
							tempString[2]=createPasswordField .getText();
							property.firePropertyChange(new PropertyChangeEvent(this,"New user",null,tempString));
							if(!existFlag) {
								currentUser=createUsernameField.getText();
								createUserFrame.dispose();	
						     	editBusinessOwner();
							}
							else 
								createUsernameField.setText("");
					}	
						else				
							if(comboBox.getSelectedItem().equals("Customer")) {	
								tempString=new String[3];						
								tempString[0]="Customer";	
								tempString[1]=createUsernameField.getText();	
								tempString[2]=createPasswordField .getText();
								property.firePropertyChange(new PropertyChangeEvent(this,"New user",null,tempString));
								if(!existFlag) {
									currentUser=createUsernameField.getText();
									createUserFrame.dispose();	
								    editCustomer();	
								   // currentUser=createUsernameField.getText();
								}
								else 
									createUsernameField.setText("");						
							}
//					currentUser=createUsernameField.getText();

				}
				else //if something goes wrong ..
				{
					if(createUsernameField.getText().equals("")) 
						JOptionPane.showMessageDialog(contentPane, "Please enter username","",JOptionPane.WARNING_MESSAGE);
					else
						if(createPasswordField .getText().equals(""))
							JOptionPane.showMessageDialog(contentPane, "Please enter password","",JOptionPane.WARNING_MESSAGE);
					else {					    
					JOptionPane.showMessageDialog(contentPane, "Your password and confirmation password do not match","",JOptionPane.ERROR_MESSAGE);
					createPasswordField .setText("");
					confirmPasswordField.setText("");
					}
				}
				
			}
		});
		
		createUserFrame.setVisible(true);
	}
	
	/*
	 * 
	 * Edit customer start fields frame:
	 * 
	 */
	
	public void editCustomer() {
		
	createCustomerFrame=new JFrame();
	createCustomerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	createCustomerFrame.setBounds(450, 120, 1280, 800);
	JPanel contentPane = new JPanel();
	contentPane.setBackground(new Color(253, 245, 230));
	contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	createCustomerFrame.setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JLabel lblNewLabel = new JLabel("Edit Customer profile:");
	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
	lblNewLabel.setBounds(0, 0, 398, 68);
	contentPane.add(lblNewLabel);
	
	JLabel lblFirstName = new JLabel("First name:");
	lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 20));
	lblFirstName.setBounds(19, 90, 120, 29);
	contentPane.add(lblFirstName);
	
	JTextField first_name = new JTextField();
	first_name.setFont(new Font("Tahoma", Font.PLAIN, 22));
	first_name.setBounds(140, 93, 244, 29);
	contentPane.add(first_name);
	first_name.setColumns(10);
	
	JLabel lblLastName = new JLabel("Last name:");
	lblLastName.setFont(new Font("Tahoma", Font.BOLD, 20));
	lblLastName.setBounds(19, 134, 120, 29);
	contentPane.add(lblLastName);
	
	JLabel lblCity = new JLabel("Region:");
	lblCity.setFont(new Font("Tahoma", Font.BOLD, 20));
	lblCity.setBounds(19, 224, 106, 29);
	contentPane.add(lblCity);
	
	JLabel lblCity_1 = new JLabel("City:");
	lblCity_1.setFont(new Font("Tahoma", Font.BOLD, 20));
	lblCity_1.setBounds(19, 269, 106, 29);
	contentPane.add(lblCity_1);
	
	JTextField last_name = new JTextField();
	last_name.setFont(new Font("Tahoma", Font.PLAIN, 22));
	last_name.setBounds(140, 138, 244, 29);
	contentPane.add(last_name);
	last_name.setColumns(10);
	
	JTextField city = new JTextField();
	city.setFont(new Font("Tahoma", Font.PLAIN, 20));
	city.setColumns(10);
	city.setBounds(140, 272, 244, 29);
	contentPane.add(city);
	
	JComboBox region = new JComboBox();
	region.setModel(new DefaultComboBoxModel(new String[] {"Mercaz", "Darom", "Cafon"}));
	region.setFont(new Font("Arial", Font.PLAIN, 20));
	region.setBounds(140, 228, 244, 26);
	contentPane.add(region);
	
	JLabel lblAge = new JLabel("Age:");
	lblAge.setFont(new Font("Tahoma", Font.BOLD, 20));
	lblAge.setBounds(19, 179, 106, 29);
	contentPane.add(lblAge);
	
	JTextField age = new JTextField();
	age.setFont(new Font("Tahoma", Font.PLAIN, 20));
	age.setColumns(3);
	age.setBounds(140, 183, 244, 29);
	contentPane.add(age);
	
	JButton btnSave = new JButton("Save");
	btnSave.setBackground(new Color(211, 211, 211));
	btnSave.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnSave.setBounds(140, 445, 115, 29);
	contentPane.add(btnSave);
	
	JButton btnExit = new JButton("Exit");
	btnExit.setBackground(new Color(211, 211, 211));
	btnExit.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnExit.setBounds(269, 445, 115, 29);
	contentPane.add(btnExit);
	
	JLabel lblMail = new JLabel("Mail:");
	lblMail.setFont(new Font("Tahoma", Font.BOLD, 20));
	lblMail.setBounds(19, 314, 106, 29);
	contentPane.add(lblMail);
	
	JTextField mailTextField = new JTextField();
	mailTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
	mailTextField.setColumns(10);
	mailTextField.setBounds(140, 317, 244, 29);
	contentPane.add(mailTextField);
	
	JLabel lblNewLabel_1 = new JLabel("");
	lblNewLabel_1.setBounds(399, 0, 847, 744);
	contentPane.add(lblNewLabel_1);
	lblNewLabel_1.setIcon(new ImageIcon(AngelView.class.getResource("/time.jpeg")));
	
	/*
	 * 
	 * Edit customer actions
	 * 
	 */
	
	//Save clicked
	btnSave.addActionListener(new ActionListener() {		
		
		public void actionPerformed(ActionEvent e) {  
			if(first_name.getText().equals("")) {
				JOptionPane.showMessageDialog(contentPane, "Please enter first name","",JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(last_name.getText().equals("")) {
				JOptionPane.showMessageDialog(contentPane, "Please enter last name","",JOptionPane.WARNING_MESSAGE);
				return;
			}
			System.out.println("currrentUser="+currentUser);
			User tempUser=new UserFactory(currentUser).getUser("Customer");	
			tempUser.setFirst_name(first_name.getText());
			tempUser.setLast_name(last_name.getText());
			if(!age.getText().equals("") && age.getText().matches("[0-9]+") && age.getText().length() < 3) 
				tempUser.setAge(Integer.parseInt(age.getText()));
			else {
				JOptionPane.showMessageDialog(contentPane, "Age is illegal","",JOptionPane.WARNING_MESSAGE);
				age.setText("");
				return;
			}
			tempUser.setRegion((String)region.getSelectedItem());
			tempUser.setCity(city.getText());
			tempUser.setMail(mailTextField.getText());
			property.firePropertyChange(new PropertyChangeEvent(this,"User edit", null, tempUser));
            createCustomerFrame.dispose();
		}
	});
    
	//Exit clicked
	btnExit.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			createCustomerFrame.dispose();
			login();
		}
	});
	
	createCustomerFrame.setVisible(true);
	}
	
	/*
	 * 
	 * Edit business owner before log in frame:
	 * 
	 */
	
	public void editBusinessOwner() {
		
		createBusinessOwnerFrame=new JFrame("Edit Business owner info ");
		createBusinessOwnerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		createBusinessOwnerFrame.setBounds(450, 120, 1280, 800);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 240, 230));
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		createBusinessOwnerFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("Edit Business profile:");
		label.setFont(new Font("Tahoma", Font.BOLD, 36));
		label.setBounds(0, 0, 387, 68);
		contentPane.add(label);
		
		JLabel lblBusinessName = new JLabel("Business name:");
		lblBusinessName.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblBusinessName.setBounds(15, 72, 202, 29);
		contentPane.add(lblBusinessName);
		
		JTextField businessNameField = new JTextField();
		businessNameField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		businessNameField.setColumns(20);
		businessNameField.setBounds(182, 75, 244, 29);
		contentPane.add(businessNameField);
		
		JTextField adressField = new JTextField();
		adressField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		adressField.setColumns(30);
		adressField.setBounds(182, 165, 244, 29);
		contentPane.add(adressField);
		
		JTextField phoneNumberField = new JTextField();
		phoneNumberField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		phoneNumberField.setColumns(10);
		phoneNumberField.setBounds(182, 210, 244, 29);
		contentPane.add(phoneNumberField);
		
		JLabel lblAdress = new JLabel("Adress:");
		lblAdress.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAdress.setBounds(15, 162, 152, 29);
		contentPane.add(lblAdress);
		
		JLabel lblPhoneNumber = new JLabel("Phone number:");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPhoneNumber.setBounds(15, 207, 164, 29);
		contentPane.add(lblPhoneNumber);
		
		JLabel lblOpenningHours = new JLabel("Openning hours:");
		lblOpenningHours.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblOpenningHours.setBounds(15, 344, 164, 29);
		contentPane.add(lblOpenningHours);
		
		JLabel lblServices = new JLabel("Services:");
		lblServices.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblServices.setBounds(15, 389, 152, 29);
		contentPane.add(lblServices);
		
		JLabel lblPriceList = new JLabel("Price:");
		lblPriceList.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPriceList.setBounds(15, 434, 152, 29);
		contentPane.add(lblPriceList);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBackground(new Color(211, 211, 211));
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSave.setBounds(183, 543, 100, 29);
		contentPane.add(btnSave);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBackground(new Color(211, 211, 211));
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnExit.setBounds(326, 543, 100, 29);
		contentPane.add(btnExit);
		
		JLabel lblSocialPage = new JLabel("Social:");
		lblSocialPage.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSocialPage.setBounds(15, 252, 152, 29);
		contentPane.add(lblSocialPage);
		
		JTextField socialTextField = new JTextField();
		socialTextField.setFont(new Font("Arial", Font.PLAIN, 18));
		socialTextField.setColumns(20);
		socialTextField.setBounds(182, 255, 244, 29);
		contentPane.add(socialTextField);
		
		JComboBox fromComboBox = new JComboBox();
		fromComboBox.setBackground(Color.WHITE);
		fromComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		fromComboBox.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		fromComboBox.setMaximumRowCount(24);
		fromComboBox.setBounds(192, 347, 49, 26);
		contentPane.add(fromComboBox);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTo.setBounds(271, 343, 49, 30);
		contentPane.add(lblTo);
		
		JComboBox toComboBox = new JComboBox();
		toComboBox.setBackground(Color.WHITE);
		toComboBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		toComboBox.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		toComboBox.setMaximumRowCount(24);
		toComboBox.setBounds(338, 347, 49, 26);
		contentPane.add(toComboBox);
		
		JLabel lblRegion = new JLabel("Region:");
		lblRegion.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRegion.setBounds(15, 117, 152, 29);
		contentPane.add(lblRegion);
		
		JComboBox regionComboBox = new JComboBox();
		regionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Mercaz", "Darom", "Cafon"}));
		regionComboBox.setFont(new Font("Arial", Font.PLAIN, 21));
		regionComboBox.setBounds(182, 124, 244, 25);
		contentPane.add(regionComboBox);
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMail.setBounds(15, 299, 152, 29);
		contentPane.add(lblMail);
		
		JTextField mailTextField = new JTextField();
		mailTextField.setFont(new Font("Arial", Font.PLAIN, 18));
		mailTextField.setColumns(20);
		mailTextField.setBounds(182, 300, 244, 29);
		contentPane.add(mailTextField);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(250, 240, 230));
		panel.setBounds(675, 0, 583, 744);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBackground(new Color(250, 250, 210));
		panel_1.setBounds(182, 389, 468, 86);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JCheckBox serviceCheckBox1 = new JCheckBox("Manicure");
		serviceCheckBox1.setBounds(11, 6, 112, 29);
		panel_1.add(serviceCheckBox1);
		serviceCheckBox1.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JCheckBox serviceCheckBox2 = new JCheckBox("Pedicure");
		serviceCheckBox2.setBounds(130, 6, 109, 29);
		panel_1.add(serviceCheckBox2);
		serviceCheckBox2.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JCheckBox serviceCheckBox4 = new JCheckBox("Lack");
		serviceCheckBox4.setBounds(354, 6, 103, 29);
		panel_1.add(serviceCheckBox4);
		serviceCheckBox4.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JCheckBox serviceCheckBox3 = new JCheckBox("Build");
		serviceCheckBox3.setBounds(246, 6, 101, 29);
		panel_1.add(serviceCheckBox3);
		serviceCheckBox3.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		JSpinner spinner1 = new JSpinner();
		spinner1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinner1.setModel(new SpinnerNumberModel(50, 1, 300, 1));
		spinner1.setBounds(42, 47, 57, 26);
		panel_1.add(spinner1);
		
		JSpinner spinner2 = new JSpinner();
		spinner2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinner2.setModel(new SpinnerNumberModel(70, 1, 300, 1));
		spinner2.setBounds(161, 47, 57, 26);
		panel_1.add(spinner2);
		
		JSpinner spinner3 = new JSpinner();
		spinner3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinner3.setModel(new SpinnerNumberModel(50, 1, 300, 1));
		spinner3.setBounds(275, 47, 57, 26);
		panel_1.add(spinner3);
		
		JSpinner spinner4 = new JSpinner();
		spinner4.setFont(new Font("Tahoma", Font.PLAIN, 18));
		spinner4.setModel(new SpinnerNumberModel(80, 1, 300, 1));
		spinner4.setBounds(384, 47, 57, 26);
		panel_1.add(spinner4);
		
		/*
		 * 
		 * Edit business owner actions
		 * 
		 */
		
		//Save clicked
		btnSave.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(businessNameField.getText().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Please enter business name","",JOptionPane.WARNING_MESSAGE);
				    return;
				}				
				
				if(Integer.parseInt((String) fromComboBox.getSelectedItem())>= Integer.parseInt((String) toComboBox.getSelectedItem())) {
					JOptionPane.showMessageDialog(contentPane, "Opening time less or equal than closing time! ","",JOptionPane.WARNING_MESSAGE);
				    return;
				}
				
				if(!(serviceCheckBox1.isSelected()||serviceCheckBox2.isSelected()||serviceCheckBox3.isSelected()||serviceCheckBox4.isSelected())) {
					JOptionPane.showMessageDialog(contentPane, "Please select at least one service!","",JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(adressField.getText().matches("[0-9]+") && !adressField.getText().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Adress is illegal","",JOptionPane.WARNING_MESSAGE);
					adressField.setText("");
					return;
				}
				if(!phoneNumberField.getText().matches("[0-9]+") && !phoneNumberField.getText().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Phone number illegal","",JOptionPane.WARNING_MESSAGE);
					phoneNumberField.setText("");
					return;
				}
					
				User tempUser=new UserFactory(currentUser).getUser("Business owner");		
				tempUser.setBusiness_name(businessNameField.getText());
				tempUser.setRegion((String)regionComboBox.getSelectedItem());
				tempUser.setAdress(adressField.getText());
				tempUser.setPhone_number(phoneNumberField.getText());
				tempUser.setSocial(socialTextField.getText());
				tempUser.setOpenning_hours(""+(String)fromComboBox.getSelectedItem()+" - "+(String)toComboBox.getSelectedItem());
				HashMap<String,Integer> serviceMap=new HashMap<String,Integer>();
				if(serviceCheckBox1.isSelected()) { 
					serviceMap.put(serviceCheckBox1.getText(),(Integer) spinner1.getValue());
				}
				if(serviceCheckBox2.isSelected()) {
					serviceMap.put(serviceCheckBox2.getText(),(Integer) spinner2.getValue());
				}
				if(serviceCheckBox3.isSelected()) {
					serviceMap.put(serviceCheckBox3.getText(),(Integer) spinner3.getValue());
				}
				if(serviceCheckBox4.isSelected()) { 
					serviceMap.put(serviceCheckBox4.getText(),(Integer) spinner4.getValue());
				}
				tempUser.setServices(serviceMap);		
				tempUser.setMail(mailTextField.getText());
				property.firePropertyChange(new PropertyChangeEvent(this,"User edit", null, tempUser));				
				createBusinessOwnerFrame.dispose();			
			}
		});
		
		//Exit clicked
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createBusinessOwnerFrame.dispose();			
			}
		});
	
		createBusinessOwnerFrame.setVisible(true);		
	}
	
	/*
	 * 
	 * Main menu frame:
	 * 
	 */
	
	public void customerMainMenu(User user) {
		
		customerMainFrame=new JFrame("Main");
		customerMainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		customerMainFrame.setBounds(100, 0, 1600, 1024);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 215, 0));
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		contentPane.setLayout(new BorderLayout(0, 0));
		customerMainFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBackground(new Color(255, 228, 225));
		panel.setBounds(0, 0, 504, 968);
		contentPane.add(panel);
		panel.setLayout(null);
		
		showListMatched = new DefaultListModel<String>();
		JList listMatched = new JList(showListMatched);
		listMatched.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listMatched.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMatched.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 19));
		listMatched.setBackground(SystemColor.control);
		listMatched.setBounds(56, 183, 374, 304);
		panel.add(listMatched);
		
		JLabel lblListOfBuisnesses = new JLabel("Matched businesses:");
		lblListOfBuisnesses.setLabelFor(listMatched);
		lblListOfBuisnesses.setBackground(new Color(240, 230, 140));
		lblListOfBuisnesses.setFont(new Font("Arial", Font.BOLD, 25));
		lblListOfBuisnesses.setBounds(56, 128, 374, 51);
		panel.add(lblListOfBuisnesses);
		
		JButton chooseBtn = new JButton("Choose");
		chooseBtn.setBackground(UIManager.getColor("Button.shadow"));
		chooseBtn.setFont(new Font("Arial", Font.PLAIN, 20));
		chooseBtn.setBounds(280, 876, 150, 35);
		panel.add(chooseBtn);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(404, 183, 26, 256);
		panel.add(scrollBar);
		
		JLabel lblMayInterest = new JLabel("May interest you:");
		lblMayInterest.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblMayInterest.setBackground(new Color(240, 230, 140));
		lblMayInterest.setBounds(56, 517, 374, 51);
		panel.add(lblMayInterest);
		
		showListInterest = new DefaultListModel<String>();	
		JList listInterest = new JList(showListInterest);
		listInterest.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listInterest.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listInterest.setFont(new Font("Sitka Text", Font.BOLD | Font.ITALIC, 19));
		listInterest.setBackground(SystemColor.control);
		listInterest.setBounds(56, 571, 374, 289);
		panel.add(listInterest);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_1.setBackground(new Color(255, 255, 224));
		panel_1.setBounds(503, 0, 1075, 183);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblRegion = new JLabel("Region");
		lblRegion.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblRegion.setBounds(882, 150, 116, 33);
		panel_1.add(lblRegion);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblPrice.setBounds(347, 150, 188, 33);
		panel_1.add(lblPrice);
		
		JLabel lblServices = new JLabel("Services");
		lblServices.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblServices.setBounds(633, 150, 104, 33);
		panel_1.add(lblServices);
		
		JLabel lblLoggedIn = new JLabel(""+user.getUsername()+" logged in");
		lblLoggedIn.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLoggedIn.setFont(new Font("Arial", Font.PLAIN, 18));
		lblLoggedIn.setBounds(777, 61, 273, 33);
		panel_1.add(lblLoggedIn);
		
		JLabel lblDate = new JLabel(""+angelFormatDate.format(new Date()));
		lblDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDate.setBounds(787, 16, 263, 35);
		lblDate.setFont(new Font("Arial", Font.PLAIN, 20));
		panel_1.add(lblDate);
		
		JComboBox regionComboBox = new JComboBox();
		regionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Mercaz","Darom","Cafon"}));
		regionComboBox.setFont(new Font("Arial", Font.PLAIN, 21));
		regionComboBox.setBackground(new Color(240, 255, 255));
		regionComboBox.setBounds(1327, 185, 236, 31);
		contentPane.add(regionComboBox);
		
		JComboBox priceComboBox = new JComboBox();
		priceComboBox.setModel(new DefaultComboBoxModel(new String[] {"0-50", "51-100", "101-150", "151-200", "201-300"}));
		priceComboBox.setFont(new Font("Arial", Font.PLAIN, 21));
		priceComboBox.setBackground(new Color(240, 255, 255));
		priceComboBox.setSelectedIndex(0);
		priceComboBox.setBounds(824, 185, 236, 31);
		contentPane.add(priceComboBox);
		
		JComboBox serviseComboBox = new JComboBox();
		serviseComboBox.setModel(new DefaultComboBoxModel(new String[] {"Manicure", "Pedicure", "lack", "Build"}));
		serviseComboBox.setFont(new Font("Arial", Font.PLAIN, 21));
		serviseComboBox.setSelectedIndex(0);
		serviseComboBox.setBackground(new Color(240, 255, 255));
		serviseComboBox.setBounds(1076, 185, 236, 31);
		contentPane.add(serviseComboBox);
		
		JButton refreshBtn = new JButton("Refresh");
		refreshBtn.setFont(new Font("Arial", Font.BOLD, 22));
		refreshBtn.setBackground(UIManager.getColor("Button.focus"));
		refreshBtn.setBounds(563, 185, 198, 31);
		contentPane.add(refreshBtn);
		
		JButton exitBtn = new JButton("Log out");
		exitBtn.setBackground(UIManager.getColor("Button.shadow"));
		exitBtn.setFont(new Font("Arial", Font.PLAIN, 22));
		exitBtn.setBounds(1327, 867, 157, 42);
		contentPane.add(exitBtn);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(706, 250, 961, 584);
		contentPane.add(lblNewLabel);
		lblNewLabel.setIcon(new ImageIcon(AngelView.class.getResource("/logo.png")));
		
		JButton btnMyAppointments = new JButton("My appointments");
		btnMyAppointments.setFont(new Font("Arial", Font.PLAIN, 17));
		btnMyAppointments.setBackground(UIManager.getColor("Button.shadow"));
		btnMyAppointments.setBounds(1109, 867, 169, 42);
		contentPane.add(btnMyAppointments);
		
		/*
		 * 
		 * Main menu actions:
		 * 
		 */
		
		
		//refresh button clicked ,get all sort fields and fire property (call observer(Controller)).
		refreshBtn.addActionListener( new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				showListMatched.clear();
				showListInterest.clear();
				String[] filter=new String[3]; //[0]=region,[1]=price,[2]=service
			  	filter[0]=(String)regionComboBox.getSelectedItem();
			  	filter[1]=(String)priceComboBox.getSelectedItem();
			  	filter[2]=(String)serviseComboBox.getSelectedItem();
			  	property.firePropertyChange(new PropertyChangeEvent(this,"Refresh list",null,filter)); //send to controller that refresh clicked,end send him region,price,,service customer choose.
			  	if(showListMatched.isEmpty()) {
			  		showListMatched.addElement("There is no matches");
			  		filter[1]="all";
			  		filter[2]="all";	  		
			  		property.firePropertyChange(new PropertyChangeEvent(this,"Refresh list",null,filter));
			  		if(showListInterest.isEmpty())
			  			showListInterest.addElement("There is no businesses in your region");
			  	}			  	
			}
		});
		
		//Choose clicked
		chooseBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String businessNameSelected;//name of selected business
				if(!listMatched.isSelectionEmpty() && !listMatched.getSelectedValue().equals("There is no matches"))
					businessNameSelected=(String) listMatched.getSelectedValue();				
				else
				if(!listInterest.isSelectionEmpty() && !listInterest.getSelectedValue().equals("There is no businesses in your region")) 
					businessNameSelected=(String) listInterest.getSelectedValue();
				else {//if no business selected
					JOptionPane.showMessageDialog(contentPane, "There is no business selected!","",JOptionPane.WARNING_MESSAGE);
					return;
				}
			    for (User user : sortedUsersList) //find user instance for selected business from list.And open Business page for him.
				   if(user.getBusiness_name().equals(businessNameSelected))
					   businessPage(user);	
				
			}
		});
		
		btnMyAppointments.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				property.firePropertyChange(new PropertyChangeEvent(this,"Show appointments",null,currentUser));
				
			}
		});
		
		//Exit clicked
		exitBtn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				currentUser=null;
				currentUserData=null;
				customerMainFrame.dispose();	
				login();
			}
		});
		
		customerMainFrame.setVisible(true);		
	}
	/*
	 * 
	 * Business page frame
	 * 
	 */
	public void businessPage(User businessChoosed) {
		
	businessPageFrame=new JFrame("Bussines page");
	businessPageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	businessPageFrame.setBounds(100, 0, 1600, 1024);
	JPanel contentPane = new JPanel();
	contentPane.setBackground(new Color(253, 245, 230));
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	businessPageFrame.setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JPanel panel = new JPanel();
	panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
	panel.setBackground(new Color(218, 165, 32));
	panel.setBounds(0, 0, 409, 984);
	contentPane.add(panel);
	panel.setLayout(null);
	
	JLabel imageLbl = new JLabel("");
	imageLbl.setHorizontalAlignment(SwingConstants.LEFT);
	imageLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
	imageLbl.setIcon(new ImageIcon(AngelView.class.getResource("/scheduler.png")));
	imageLbl.setBounds(78, 0, 302, 231);
	panel.add(imageLbl);
	
	JLabel lblBusinName = new JLabel("Business name:");
	lblBusinName.setFont(new Font("Bodoni MT", Font.BOLD, 23));
	lblBusinName.setBounds(10, 270, 162, 31);
	panel.add(lblBusinName);
	
	JSeparator separator = new JSeparator();
	separator.setForeground(new Color(0, 0, 255));
	separator.setBackground(new Color(0, 0, 139));
	separator.setBounds(0, 247, 409, 26);
	panel.add(separator);
	
	JLabel lblAdress = new JLabel("Adress:");
	lblAdress.setFont(new Font("Bodoni MT", Font.BOLD, 23));
	lblAdress.setBounds(10, 317, 147, 31);
	panel.add(lblAdress);
	
	JLabel lblPhoneNumber = new JLabel("Phone number:");
	lblPhoneNumber.setFont(new Font("Bodoni MT", Font.BOLD, 23));
	lblPhoneNumber.setBounds(10, 364, 162, 31);
	panel.add(lblPhoneNumber);
	
	JLabel lblOpenningHours = new JLabel("Openning hours:");
	lblOpenningHours.setFont(new Font("Bodoni MT", Font.BOLD, 23));
	lblOpenningHours.setBounds(10, 411, 170, 31);
	panel.add(lblOpenningHours);
	
	JLabel lblSocial = new JLabel("Social:");
	lblSocial.setFont(new Font("Bodoni MT", Font.BOLD, 23));
	lblSocial.setBounds(10, 458, 147, 31);
	panel.add(lblSocial);
	
	JLabel lblShowName = new JLabel(businessChoosed.getBusiness_name());
	lblShowName.setForeground(Color.DARK_GRAY);
	lblShowName.setFont(new Font("Tahoma", Font.ITALIC, 20));
	lblShowName.setBounds(188, 274, 221, 27);
	panel.add(lblShowName);
	
	JLabel lblShowAdress = new JLabel(businessChoosed.getAdress());
	lblShowAdress.setForeground(Color.DARK_GRAY);
	lblShowAdress.setFont(new Font("Tahoma", Font.ITALIC, 20));
	lblShowAdress.setBounds(188, 321, 221, 27);
	panel.add(lblShowAdress);
	
	JLabel lblShowNumber = new JLabel(businessChoosed.getPhone_number());
	lblShowNumber.setForeground(Color.DARK_GRAY);
	lblShowNumber.setFont(new Font("Tahoma", Font.ITALIC, 20));
	lblShowNumber.setBounds(188, 369, 221, 27);
	panel.add(lblShowNumber);
	
	JLabel lblShowHours = new JLabel(businessChoosed.getOpenning_hours());
	lblShowHours.setForeground(Color.DARK_GRAY);
	lblShowHours.setFont(new Font("Tahoma", Font.ITALIC, 20));
	lblShowHours.setBounds(188, 416, 221, 27);
	panel.add(lblShowHours);
	
	JLabel lblShowSocial = new JLabel(businessChoosed.getSocial());
	lblShowSocial.setHorizontalAlignment(SwingConstants.LEFT);
	lblShowSocial.setForeground(Color.DARK_GRAY);
	lblShowSocial.setFont(new Font("Tahoma", Font.ITALIC, 20));
	lblShowSocial.setBounds(188, 458, 221, 27);
	panel.add(lblShowSocial);
	
	JLabel lblMail = new JLabel("Mail:");
	lblMail.setFont(new Font("Bodoni MT", Font.BOLD, 22));
	lblMail.setBounds(10, 497, 147, 31);
	panel.add(lblMail);
	
	JLabel lblShowMail = new JLabel(businessChoosed.getMail());
	lblShowMail.setForeground(Color.DARK_GRAY);
	lblShowMail.setFont(new Font("Tahoma", Font.ITALIC, 20));
	lblShowMail.setBounds(188, 501, 221, 27);
	panel.add(lblShowMail);
	
	JPanel panel_1 = new JPanel();
	panel_1.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(0, 0, 0)));
	panel_1.setBackground(new Color(255, 248, 220));
	panel_1.setBounds(378, 0, 1200, 425);
	contentPane.add(panel_1);
	panel_1.setLayout(null);
	
	JLabel lblScheduleApoinment = new JLabel("Schedule appointment date:");
	lblScheduleApoinment.setFont(new Font("Tahoma", Font.BOLD, 20));
	lblScheduleApoinment.setBounds(45, 16, 308, 44);
	panel_1.add(lblScheduleApoinment);
	
	JCalendar businessCalendar = new JCalendar();
	businessCalendar.setWeekdayForeground(new Color(0, 0, 255));
	businessCalendar.getDayChooser().setWeekOfYearVisible(false);
	businessCalendar.getDayChooser().setSundayForeground(new Color(0, 0, 255));
	businessCalendar.getDayChooser().setBackground(new Color(0, 0, 255));
	businessCalendar.getYearChooser().setStartYear(2019);
	businessCalendar.getYearChooser().setMaximum(2022);
	businessCalendar.getYearChooser().setMinimum(2019);
	businessCalendar.getMonthChooser().setBackground(new Color(152, 251, 152));
	businessCalendar.setTodayButtonText("Today");
	businessCalendar.setSundayForeground(new Color(0, 0, 255));
	businessCalendar.setDecorationBackgroundColor(new Color(240, 255, 255));
	businessCalendar.setBackground(new Color(255, 248, 220));
	businessCalendar.setBounds(45, 59, 613, 352);
	businessCalendar.setMinSelectableDate(new Date());
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());
	cal.add(Calendar.YEAR,1);	
	businessCalendar.setMaxSelectableDate(cal.getTime());
	panel_1.add(businessCalendar);
	
	JComboBox timeComboBox = new JComboBox();
	timeComboBox.setBackground(new Color(255, 255, 255));
	
	DefaultComboBoxModel<String> time = new DefaultComboBoxModel<String>();//time sequances from openning hour to close
	time.addElement("");
	String temp=new String();
	int from=Integer.parseInt((String)businessChoosed.getOpenning_hours().subSequence(0, 2));
	int to=to=Integer.parseInt((String)businessChoosed.getOpenning_hours().subSequence(5, 7));
    //find all sequences of time,from open to close
	for(;from<=to;from++) {
		if(from<10) {
		temp="0"+from+":"+"00";
		time.addElement(temp);
		   if(from!=to) {
		     temp="0"+from+":"+"30";
		     time.addElement(temp);
		    }
		}
		else {
			temp=""+from+":"+"00";
			time.addElement(temp);
			   if(from!=to) {
			     temp=""+from+":"+"30";
			     time.addElement(temp);
			   }
		}
	}

	timeComboBox.setModel(time);
	timeComboBox.setFont(new Font("Tahoma", Font.BOLD, 19));
	timeComboBox.setMaximumRowCount(48);
	timeComboBox.setBounds(749, 59, 206, 31);
	panel_1.add(timeComboBox);
	
	JLabel lblChooseTime = new JLabel("Choose time:");
	lblChooseTime.setFont(new Font("Tahoma", Font.BOLD, 20));
	lblChooseTime.setBounds(749, 23, 206, 31);
	panel_1.add(lblChooseTime);
	
	JTextField appointmentTextField = new JTextField();
	appointmentTextField.setBackground(new Color(211, 211, 211));
	appointmentTextField.setEditable(false);
	appointmentTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
	appointmentTextField.setBounds(749, 288, 397, 37);
	panel_1.add(appointmentTextField);
	appointmentTextField.setColumns(10);
	
	JLabel lblYourAppointment = new JLabel("Your appointment:");
	lblYourAppointment.setForeground(new Color(0, 0, 0));
	lblYourAppointment.setFont(new Font("Arial Black", Font.BOLD, 18));
	lblYourAppointment.setBounds(749, 254, 227, 31);
	panel_1.add(lblYourAppointment);
	
	JButton btnSchedule = new JButton("Schedule");
	btnSchedule.setBackground(UIManager.getColor("Button.focus"));
	btnSchedule.setFont(new Font("Arial", Font.PLAIN, 19));
	btnSchedule.setBounds(752, 129, 137, 37);
	panel_1.add(btnSchedule);
	
	JLabel lblChooseService = new JLabel("Choose service:");
	lblChooseService.setFont(new Font("Tahoma", Font.BOLD, 20));
	lblChooseService.setBounds(979, 23, 206, 31);
	panel_1.add(lblChooseService);
	
	JComboBox serviceComboBox = new JComboBox();
	serviceComboBox.setBackground(new Color(255, 255, 255));
	
	DefaultComboBoxModel<String> services = new DefaultComboBoxModel<String>();//services for current business choosed
	services.addElement("");
	for (Map.Entry<String,Integer> entry : businessChoosed.getServices().entrySet()) {
		services.addElement(entry.getKey());
	}
	serviceComboBox.setModel(services);
	serviceComboBox.setFont(new Font("Tahoma", Font.BOLD, 19));
	serviceComboBox.setMaximumRowCount(10);
	serviceComboBox.setBounds(979, 59, 206, 31);
	panel_1.add(serviceComboBox);
	
	JPanel panel_2 = new JPanel();
	panel_2.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(0, 0, 0)));
	panel_2.setBackground(new Color(255, 248, 220));
	panel_2.setBounds(378, 423, 1200, 545);
	contentPane.add(panel_2);
	panel_2.setLayout(null);
	
	JTextArea reviewtextArea = new JTextArea();
	reviewtextArea.setBackground(new Color(255, 250, 250));
	reviewtextArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
	reviewtextArea.setBounds(38, 156, 597, 183);
	panel_2.add(reviewtextArea);
	
	JLabel lblRateAs = new JLabel("Write a review:");
	lblRateAs.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblRateAs.setBounds(40, 121, 276, 30);
	panel_2.add(lblRateAs);
	
	JButton btnSave = new JButton("Save");
	btnSave.setBackground(UIManager.getColor("Button.focus"));
	btnSave.setBounds(520, 343, 115, 29);
	panel_2.add(btnSave);
	
	JButton btnExit = new JButton("Exit");
	btnExit.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnExit.setBounds(995, 469, 115, 29);
	panel_2.add(btnExit);
	
	JLabel lblRateUs = new JLabel("Rate us:");
	lblRateUs.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblRateUs.setBounds(38, 55, 115, 20);
	panel_2.add(lblRateUs);
	
	JRadioButton rateRadioBtn0 = new JRadioButton("0");
	rateRadioBtn0.setBackground(new Color(253, 245, 230));
	rateRadioBtn0.setBounds(141, 53, 41, 29);
	panel_2.add(rateRadioBtn0);
	
	JRadioButton rateRadioBtn1 = new JRadioButton("1");
	rateRadioBtn1.setBackground(new Color(253, 245, 230));
	rateRadioBtn1.setBounds(202, 53, 41, 29);
	panel_2.add(rateRadioBtn1);
	
	JRadioButton rateRadioBtn2 = new JRadioButton("2");
	rateRadioBtn2.setBackground(new Color(253, 245, 230));
	rateRadioBtn2.setBounds(263, 53, 41, 29);
	panel_2.add(rateRadioBtn2);
	
	JRadioButton rateRadioBtn3 = new JRadioButton("3");
	rateRadioBtn3.setBackground(new Color(253, 245, 230));
	rateRadioBtn3.setBounds(323, 53, 41, 29);
	panel_2.add(rateRadioBtn3);
	
	JRadioButton rateRadioBtn4 = new JRadioButton("4");
	rateRadioBtn4.setBackground(new Color(253, 245, 230));
	rateRadioBtn4.setBounds(383, 53, 41, 29);
	panel_2.add(rateRadioBtn4);
	
	JRadioButton rateRadioBtn5 = new JRadioButton("5");
	rateRadioBtn5.setBackground(new Color(253, 245, 230));
	rateRadioBtn5.setBounds(443, 53, 41, 29);
	panel_2.add(rateRadioBtn5);
	
	ButtonGroup radioBtnGroupRateUs=new ButtonGroup();
	radioBtnGroupRateUs.add(rateRadioBtn0);
	radioBtnGroupRateUs.add(rateRadioBtn1);
	radioBtnGroupRateUs.add(rateRadioBtn2);
	radioBtnGroupRateUs.add(rateRadioBtn3);
	radioBtnGroupRateUs.add(rateRadioBtn4);
	radioBtnGroupRateUs.add(rateRadioBtn5);
	
	/*
	 * 
	 * Action for Business page
	 * 
	 */
	
	//Exit clicked
	btnExit.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			businessPageFrame.dispose();
			
		}
	});
	
	
	
	//Save clicked,send rating and review to database
	btnSave.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String rate="-1";
			String review;
			String[] str=new String[2];
		    
		    str[0]=businessChoosed.getUsername();
            if(rateRadioBtn0.isSelected())
            	rate=rateRadioBtn0.getText();
            if(rateRadioBtn1.isSelected())
            	rate=rateRadioBtn1.getText();
            if(rateRadioBtn2.isSelected())
            	rate=rateRadioBtn2.getText();
            if(rateRadioBtn3.isSelected())
            	rate=rateRadioBtn3.getText();
            if(rateRadioBtn4.isSelected())
            	rate=rateRadioBtn4.getText();
            if(rateRadioBtn5.isSelected())
            	rate=rateRadioBtn5.getText();
            review=reviewtextArea.getText();
            
            if(!rate.equals("-1")) {
            	str[1]=rate;
            	rateRadioBtn0.setEnabled(false);
            	rateRadioBtn1.setEnabled(false);
            	rateRadioBtn2.setEnabled(false);
            	rateRadioBtn3.setEnabled(false);
            	rateRadioBtn4.setEnabled(false);
            	rateRadioBtn5.setEnabled(false);
            	property.firePropertyChange(new PropertyChangeEvent(this,"Change rating",null,str));
            }
            
            if(!review.equals("")) {
            	str[1]=review;
            	reviewtextArea.setEnabled(false);
                property.firePropertyChange(new PropertyChangeEvent(this,"Add review",null,str));
            }

		
		
		}
	});
	
	//Schedule clicked,get date and time selected..
	btnSchedule.addActionListener(new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			
			Date date=businessCalendar.getDate();
			String time;
			if(timeComboBox.getSelectedItem().equals("")) {
				JOptionPane.showMessageDialog(contentPane, "Please choose time","",JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(serviceComboBox.getSelectedItem().equals("")) {
				JOptionPane.showMessageDialog(contentPane, "Please choose service","",JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			
			
			time=(String)timeComboBox.getSelectedItem();
			String appointmentDateTime=""+angelFormatDate.format(date)+" "+time;
			try {
				if(businessChoosed.checkAppointmentFree(appointmentDateTime) ) {
					if(angelFormatDateTime.parse(appointmentDateTime).before(new Date())){
		                JOptionPane.showMessageDialog(contentPane, "Sorry date is not availble","",JOptionPane.WARNING_MESSAGE);
		                return;
	                }
				String[] str=new String[4];
				str[0]=businessChoosed.getUsername();//username
				str[1]=appointmentDateTime;//date
				str[2]=(String)serviceComboBox.getSelectedItem();               //service
				str[3]=currentUser;
				property.firePropertyChange(new PropertyChangeEvent(this,"Schedule appointment",null,str));//save appointment to business data
				appointmentTextField.setText(appointmentDateTime);
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "Sorry date is not free","",JOptionPane.WARNING_MESSAGE);
				}
			} catch (Exception e1) {
				e1.printStackTrace();		
			}
				
		}
	});
	
	businessPageFrame.setVisible(true);
	}
	/*
	 * 
	 * Business owner main frame
	 * 
	 */
	public void businessOwnerMain(User user) {
		
		businessOwnerMainFrame=new JFrame();
		businessOwnerMainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		businessOwnerMainFrame.setBounds(100, 5, 1600, 1024);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 235, 215));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		businessOwnerMainFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(253, 245, 230));
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBounds(0, 287, 430, 681);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnEditInfo = new JButton("Edit information");
		btnEditInfo.setBackground(UIManager.getColor("Label.foreground"));
		btnEditInfo.setFont(new Font("Arial Black", Font.PLAIN, 23));
		btnEditInfo.setHorizontalAlignment(SwingConstants.LEFT);
		btnEditInfo.setBounds(10, 63, 298, 40);
		panel.add(btnEditInfo);
		
		JButton btnEditServices = new JButton("Edit services");
		btnEditServices.setHorizontalAlignment(SwingConstants.LEFT);
		btnEditServices.setFont(new Font("Arial Black", Font.PLAIN, 23));
		btnEditServices.setBackground(UIManager.getColor("Label.foreground"));
		btnEditServices.setBounds(10, 135, 298, 40);
		panel.add(btnEditServices);
		
		JButton btnGetReports = new JButton("Get reports");
		btnGetReports.setHorizontalAlignment(SwingConstants.LEFT);
		btnGetReports.setFont(new Font("Arial Black", Font.PLAIN, 23));
		btnGetReports.setBackground(UIManager.getColor("Label.foreground"));
		btnGetReports.setBounds(10, 210, 298, 40);
		panel.add(btnGetReports);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.setHorizontalAlignment(SwingConstants.LEFT);
		btnLogOut.setFont(new Font("Arial Black", Font.PLAIN, 23));
		btnLogOut.setBackground(UIManager.getColor("Label.foreground"));
		btnLogOut.setBounds(10, 356, 298, 40);
		panel.add(btnLogOut);
		
		JButton btnShowAppointments = new JButton("Show appointments");
		btnShowAppointments.setHorizontalAlignment(SwingConstants.LEFT);
		btnShowAppointments.setFont(new Font("Arial Black", Font.PLAIN, 23));
		btnShowAppointments.setBackground(UIManager.getColor("Label.foreground"));
		btnShowAppointments.setBounds(10, 280, 298, 40);
		panel.add(btnShowAppointments);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBackground(new Color(253, 245, 230));
		panel_1.setBounds(0, 0, 430, 286);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblHelloUser = new JLabel("Hello "+user.getBusiness_name());
		lblHelloUser.setForeground(Color.BLACK);
		lblHelloUser.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblHelloUser.setBounds(15, 154, 384, 50);
		panel_1.add(lblHelloUser);
		
		JLabel lblDate = new JLabel(angelFormatDate.format(new Date()));
		lblDate.setForeground(Color.BLACK);
		lblDate.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblDate.setBounds(15, 220, 384, 50);
		panel_1.add(lblDate);
		
		JLabel lblUserIcon = new JLabel("");
		lblUserIcon.setIcon(new ImageIcon(AngelView.class.getResource("/user.png")));
		lblUserIcon.setBounds(15, 0, 190, 131);
		panel_1.add(lblUserIcon);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new MatteBorder(1, 1, 2, 1, (Color) new Color(0, 0, 0)));
		panel_2.setBackground(new Color(250, 235, 215));
		panel_2.setBounds(429, 0, 1149, 286);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Business name:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setBounds(83, 16, 158, 40);
		panel_2.add(lblNewLabel_1);
		
		JLabel label = new JLabel("Region:");
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		label.setBounds(83, 96, 158, 40);
		panel_2.add(label);
		
		JLabel lblAdress = new JLabel("Address:");
		lblAdress.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAdress.setBounds(83, 187, 158, 40);
		panel_2.add(lblAdress);
		
		JLabel lblPhoneNumber = new JLabel("Phone number:");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPhoneNumber.setBounds(566, 16, 158, 40);
		panel_2.add(lblPhoneNumber);
		
		JLabel lblSocial = new JLabel("Social:");
		lblSocial.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSocial.setBounds(566, 96, 158, 40);
		panel_2.add(lblSocial);
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMail.setBounds(566, 187, 158, 40);
		panel_2.add(lblMail);
		
		JTextField showNameField = new JTextField(user.getBusiness_name());
		showNameField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		showNameField.setEditable(false);
		showNameField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		showNameField.setBounds(241, 16, 280, 40);
		panel_2.add(showNameField);
		showNameField.setColumns(10);
		
		JTextField showRegioField = new JTextField(user.getRegion());
		showRegioField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		showRegioField.setEditable(false);
		showRegioField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		showRegioField.setColumns(10);
		showRegioField.setBounds(241, 96, 280, 40);
		panel_2.add(showRegioField);
		
		JTextField showAdressField = new JTextField(user.getAdress());
		showAdressField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		showAdressField.setEditable(false);
		showAdressField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		showAdressField.setColumns(10);
		showAdressField.setBounds(241, 187, 280, 40);
		panel_2.add(showAdressField);
		
		JTextField showPhoneField = new JTextField(user.getPhone_number());
		showPhoneField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		showPhoneField.setEditable(false);
		showPhoneField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		showPhoneField.setColumns(10);
		showPhoneField.setBounds(731, 16, 280, 40);
		panel_2.add(showPhoneField);
		
		JTextField showSocialField = new JTextField(user.getSocial());
		showSocialField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		showSocialField.setEditable(false);
		showSocialField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		showSocialField.setColumns(10);
		showSocialField.setBounds(731, 96, 280, 40);
		panel_2.add(showSocialField);
		
		JTextField showMailField = new JTextField(user.getMail());
		showSocialField.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		showMailField.setEditable(false);
		showMailField.setFont(new Font("Tahoma", Font.PLAIN, 24));
		showMailField.setColumns(10);
		showMailField.setBounds(731, 187, 280, 40);
		panel_2.add(showMailField);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_3.setBackground(new Color(135, 206, 250));
		panel_3.setBounds(429, 287, 1149, 681);
		contentPane.add(panel_3);
		panel_3.setLayout(null);
		
		JList<String> listReviews = new JList<String>();
		listReviews.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		listReviews.setFont(new Font("Tahoma",Font.BOLD, 22));
		ArrayList<String> reviewList=user.getReviews();
		String[] reviewsArray=reviewList.toArray(new String[0]);
		listReviews.setListData(reviewsArray);
		listReviews.setBounds(93, 93, 363, 456);
		panel_3.add(listReviews);
		
		JLabel lblReviews = new JLabel("Reviews:");
		lblReviews.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblReviews.setBounds(93, 57, 168, 37);
		panel_3.add(lblReviews);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(430, 93, 26, 363);
		panel_3.add(scrollBar);
		
		JLabel lblRating = new JLabel("Rating:");
		lblRating.setFont(new Font("Sylfaen", Font.BOLD, 45));
		lblRating.setBounds(544, 106, 168, 64);
		panel_3.add(lblRating);
		
		JLabel showRatingLbl = new JLabel(Integer.toString((int)user.getRating()));
		showRatingLbl.setFont(new Font("Trebuchet MS", Font.BOLD, 50));
		showRatingLbl.setHorizontalAlignment(SwingConstants.CENTER);
		showRatingLbl.setBounds(720, 89, 97, 94);
		panel_3.add(showRatingLbl);
		
		JLabel starIcon = new JLabel();
		starIcon.setIcon(new ImageIcon(AngelView.class.getResource("/star3.png")));
		starIcon.setHorizontalAlignment(SwingConstants.CENTER);
		starIcon.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
		starIcon.setBounds(830, 83, 97, 100);
		panel_3.add(starIcon);
		
		JButton btnShowReview = new JButton("show review");
		btnShowReview.setBackground(new Color(211, 211, 211));
		btnShowReview.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnShowReview.setBounds(302, 573, 147, 37);
		panel_3.add(btnShowReview);
		
		/*
		 * 
		 * business Owner Main Actions:
		 * 
		 */
		
		//Get reports clicked.Choose dates,save file report.
		btnGetReports.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				    JFrame chooseD=new JFrame();
					chooseD.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					chooseD.setBounds(200, 100, 732, 449);
					JPanel contentPane = new JPanel();
					contentPane.setBackground(new Color(253, 245, 230));
					contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
					chooseD.setContentPane(contentPane);
					contentPane.setLayout(null);
					
					JLabel label = new JLabel("Choose dates for financial report");
					label.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 25));
					label.setBounds(194, 16, 330, 33);
					contentPane.add(label);
					
					JDateChooser dateChooserFrom = new JDateChooser();
					dateChooserFrom.setBounds(123, 123, 181, 33);
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					cal.add(Calendar.HOUR,24);	
					dateChooserFrom.setMaxSelectableDate(cal.getTime());
					dateChooserFrom.setMaxSelectableDate((new Date()));
					contentPane.add(dateChooserFrom);
					
					JDateChooser dateChooserUntil= new JDateChooser();
					dateChooserUntil.setBounds(446, 123, 170, 33);
					dateChooserUntil.setMaxSelectableDate(new Date());
					contentPane.add(dateChooserUntil);
					
					JLabel label_1 = new JLabel("from:");
					label_1.setFont(new Font("Arial", Font.PLAIN, 28));
					label_1.setBounds(39, 123, 69, 33);
					contentPane.add(label_1);
					
					JLabel label_2 = new JLabel("until:");
					label_2.setFont(new Font("Arial", Font.PLAIN, 28));
					label_2.setBounds(366, 123, 69, 33);
					contentPane.add(label_2);
					
					JButton btnCreateReport = new JButton("Create report");
					btnCreateReport.setBackground(new Color(211, 211, 211));
					btnCreateReport.setFont(new Font("Tahoma", Font.PLAIN, 20));
					btnCreateReport.setBounds(265, 240, 170, 43);
					contentPane.add(btnCreateReport);
					
					btnCreateReport.addActionListener(new ActionListener() {
						
						public void actionPerformed(ActionEvent e) {
							
							Date dateFrom=dateChooserFrom.getDate();
							Date dateUntil=dateChooserUntil.getDate();
							if(dateFrom==null ||dateUntil==null) {
								JOptionPane.showMessageDialog(contentPane, "Please choose dates!","",JOptionPane.WARNING_MESSAGE);
								return;
							}
							if (dateUntil.before(dateFrom)) {
								JOptionPane.showMessageDialog(contentPane, "Can't create report whit ending date is before starting date","",JOptionPane.WARNING_MESSAGE);
								return;
							}
							
							Date[] dates=new Date[2];
							dates[0]=dateFrom;
							dates[1]=dateUntil;							
							property.firePropertyChange(new PropertyChangeEvent(this,"Create report",null,dates));
							chooseD.dispose();
						}
					});			
					chooseD.setVisible(true);						
			}
		});
		
		btnEditInfo.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {			
				editInfo();			
			}
		});
		
		btnEditServices.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editServices();
				
			}
		});
		
		btnLogOut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				businessOwnerMainFrame.dispose();
				System.out.println("User "+currentUser+"logged out");
				currentUser=null;	
				currentUserData=null;
				login();			
			}
		});
		
		btnShowReview.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!listReviews.isSelectionEmpty()) {
					String review=listReviews.getSelectedValue();
					
					JFrame showReview=new JFrame();
					showReview.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					showReview.setBounds(200, 100, 723, 409);
					JPanel contentPane = new JPanel();
					contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
					contentPane.setLayout(new BorderLayout(0, 0));
					showReview.setContentPane(contentPane);
					
					JTextArea txtArea = new JTextArea();
					txtArea.setRows(10);
					txtArea.setEditable(false);
					txtArea.setFont(new Font("Monospaced", Font.ITALIC, 18));
					contentPane.add(txtArea, BorderLayout.CENTER);
					
					showReview.setVisible(true);
					
					txtArea.setText(review);
				}
				
				
			}
		});
		
		btnShowAppointments.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	             property.firePropertyChange(new PropertyChangeEvent(this,"Show appointments",null,currentUser));
				
			}
		});	
		
		businessOwnerMainFrame.setVisible(true);
	}
	
	/*
	 * 
	 * Edit info frame
	 * 
	 */
	
	public void editInfo() {
		
		editInfoFrame=new JFrame();
		editInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		editInfoFrame.setBounds(200, 100, 928, 556);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(253, 245, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		editInfoFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Change information about business:");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel.setBounds(15, 16, 549, 43);
		contentPane.add(lblNewLabel);
		
		JLabel lblName = new JLabel("Busines name:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblName.setBounds(15, 75, 151, 32);
		contentPane.add(lblName);
		
		JTextField textFieldName = new JTextField();
		textFieldName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldName.setBounds(168, 75, 273, 32);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
		JTextField textFieldAdress = new JTextField();
		textFieldAdress.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldAdress.setColumns(10);
		textFieldAdress.setBounds(168, 126, 273, 33);
		contentPane.add(textFieldAdress);
		
		JTextField textFieldMail = new JTextField();
		textFieldMail.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldMail.setColumns(10);
		textFieldMail.setBounds(168, 175, 273, 33);
		contentPane.add(textFieldMail);
		
		JTextField textFieldPhoneNum = new JTextField();
		textFieldPhoneNum.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldPhoneNum.setColumns(10);
		textFieldPhoneNum.setBounds(168, 224, 273, 33);
		contentPane.add(textFieldPhoneNum);
		
		JTextField textFieldSocial = new JTextField();
		textFieldSocial.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textFieldSocial.setColumns(10);
		textFieldSocial.setBounds(168, 274, 273, 33);
		contentPane.add(textFieldSocial);
		
		JLabel lblAdress = new JLabel("Adress:");
		lblAdress.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAdress.setBounds(15, 126, 151, 32);
		contentPane.add(lblAdress);
		
		JLabel lblMail = new JLabel("Mail:");
		lblMail.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblMail.setBounds(15, 175, 151, 32);
		contentPane.add(lblMail);
		
		JLabel lblPhoneNumber = new JLabel("Phone number:");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblPhoneNumber.setBounds(15, 224, 151, 32);
		contentPane.add(lblPhoneNumber);
		
		JLabel lblSocial = new JLabel("Social page:");
		lblSocial.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSocial.setBounds(15, 273, 151, 32);
		contentPane.add(lblSocial);
		
		JLabel lblOppeningHours = new JLabel("Oppening hours:");
		lblOppeningHours.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblOppeningHours.setBounds(15, 340, 174, 32);
		contentPane.add(lblOppeningHours);
		
		JComboBox<String>comboBoxFrom = new JComboBox();
		comboBoxFrom.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxFrom.setMaximumRowCount(24);
		comboBoxFrom.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
		comboBoxFrom.setBounds(262, 344, 49, 26);
		contentPane.add(comboBoxFrom);
		
		JLabel lblFrom = new JLabel("from");
		lblFrom.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFrom.setBounds(204, 346, 54, 20);
		contentPane.add(lblFrom);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTo.setBounds(335, 346, 38, 20);
		contentPane.add(lblTo);
		
		JComboBox<String>comboBoxTo = new JComboBox();
		comboBoxTo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxTo.setModel(new DefaultComboBoxModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", ""}));
		comboBoxTo.setMaximumRowCount(24);
		comboBoxTo.setBounds(372, 344, 49, 26);
		contentPane.add(comboBoxTo);
		
		JButton buttonEditName = new JButton("edit");
		buttonEditName.setBackground(new Color(211, 211, 211));
		buttonEditName.setFont(new Font("Arial Black", Font.PLAIN, 16));
		buttonEditName.setBounds(488, 78, 92, 26);
		contentPane.add(buttonEditName);
		
		JButton buttonEditAdress = new JButton("edit");
		buttonEditAdress.setFont(new Font("Arial Black", Font.PLAIN, 16));
		buttonEditAdress.setBackground(new Color(211, 211, 211));
		buttonEditAdress.setBounds(488, 128, 92, 26);
		contentPane.add(buttonEditAdress);
		
		JButton buttonEditMail = new JButton("edit");
		buttonEditMail.setFont(new Font("Arial Black", Font.PLAIN, 16));
		buttonEditMail.setBackground(new Color(211, 211, 211));
		buttonEditMail.setBounds(488, 177, 92, 26);
		contentPane.add(buttonEditMail);
		
		JButton buttonEditNumber = new JButton("edit");
		buttonEditNumber.setFont(new Font("Arial Black", Font.PLAIN, 16));
		buttonEditNumber.setBackground(new Color(211, 211, 211));
		buttonEditNumber.setBounds(488, 226, 92, 26);
		contentPane.add(buttonEditNumber);
		
		JButton buttonEditSocial = new JButton("edit");
		buttonEditSocial.setFont(new Font("Arial Black", Font.PLAIN, 16));
		buttonEditSocial.setBackground(new Color(211, 211, 211));
		buttonEditSocial.setBounds(488, 276, 92, 26);
		contentPane.add(buttonEditSocial);
		
		JButton buttonEditOppeningHours = new JButton("edit");
		buttonEditOppeningHours.setFont(new Font("Arial Black", Font.PLAIN, 16));
		buttonEditOppeningHours.setBackground(new Color(211, 211, 211));
		buttonEditOppeningHours.setBounds(488, 343, 92, 26);
		contentPane.add(buttonEditOppeningHours);
		
		JButton btnDone = new JButton("Done");
		btnDone.setBackground(new Color(211, 211, 211));
		btnDone.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnDone.setBounds(715, 455, 115, 29);
		contentPane.add(btnDone);
		
		editInfoFrame.setVisible(true);
		
		/*
		 * 
		 * Edit info actions:
		 * 
		 */
		buttonEditName.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(textFieldName.getText().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Please enter business name","",JOptionPane.WARNING_MESSAGE);
					return;
				}					
				buttonEditName.setEnabled(false);
				textFieldName.setEditable(false);
				
			}
		});
		
		buttonEditAdress.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(textFieldAdress.getText().matches("[0-9]+") && !textFieldAdress.getText().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Adress is illegal","",JOptionPane.WARNING_MESSAGE);
					textFieldAdress.setText("");
					return;
				}
				buttonEditAdress.setEnabled(false);
				textFieldAdress.setEditable(false);
			
			}
		});

		buttonEditMail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonEditMail.setEnabled(false);
				textFieldMail.setEditable(false);
				
			}
		});
		
		buttonEditNumber.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textFieldPhoneNum.getText().matches("[0-9]+") && !textFieldPhoneNum.getText().equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Phone number illegal","",JOptionPane.WARNING_MESSAGE);
					textFieldPhoneNum.setText("");
					return;
				}
				buttonEditNumber.setEnabled(false);
				textFieldPhoneNum.setEditable(false);
				
			}
		});
		
		buttonEditSocial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonEditSocial.setEnabled(false);
				textFieldSocial.setEditable(false);			
			}
		});
		
		buttonEditOppeningHours.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Integer.parseInt((String) comboBoxFrom.getSelectedItem())>= Integer.parseInt((String) comboBoxTo.getSelectedItem())) {
					JOptionPane.showMessageDialog(contentPane, "Opening time less or equal than closing time!","",JOptionPane.WARNING_MESSAGE);
				    return;
				}
				buttonEditOppeningHours.setEnabled(false);
				comboBoxFrom.setEditable(false);
				comboBoxTo.setEditable(false);			
			}
		});
		
		btnDone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<String,String> newInfo=new HashMap<String, String>();
				if(!buttonEditAdress.isEnabled())
					newInfo.put("adress",textFieldAdress.getText());
				if(!buttonEditName.isEnabled())
					newInfo.put("business name",textFieldName.getText());
				if(!buttonEditMail.isEnabled())
					newInfo.put("mail",textFieldMail.getText());
				if(!buttonEditNumber.isEnabled())
					newInfo.put("phone number",textFieldPhoneNum.getText());
				if(!buttonEditOppeningHours.isEnabled())
					newInfo.put("oppening hours",new String((String)comboBoxFrom.getSelectedItem())+" - "+(String)comboBoxTo.getSelectedItem());
				if(!buttonEditSocial.isEnabled())
					newInfo.put("social",textFieldSocial.getText());
				property.firePropertyChange(new PropertyChangeEvent(this,"Edit info",null,newInfo));  
				editInfoFrame.dispose();	
				businessOwnerMainFrame.dispose();
				businessOwnerMain(currentUserData);
			}
		});
		
	}
	/*
	 * 
	 * Edit services frame
	 * 
	 */
	public void editServices() {
		editServicesFrame=new JFrame();
		editServicesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editServicesFrame.setBounds(200, 100, 804, 558);
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 240, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		editServicesFrame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Edit services:");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 28));
		lblNewLabel.setBounds(15, 16, 202, 42);
		contentPane.add(lblNewLabel);
		
		JCheckBox CheckBox1 = new JCheckBox("Manicure");
		CheckBox1.setBackground(new Color(250, 240, 230));
		CheckBox1.setBounds(15, 129, 131, 58);
		contentPane.add(CheckBox1);
		CheckBox1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JCheckBox CheckBox2 = new JCheckBox("Pedicure");
		CheckBox2.setBackground(new Color(250, 240, 230));
		CheckBox2.setBounds(15, 195, 135, 59);
		contentPane.add(CheckBox2);
		CheckBox2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JCheckBox CheckBox3 = new JCheckBox("Build");
		CheckBox3.setBackground(new Color(250, 240, 230));
		CheckBox3.setBounds(15, 262, 131, 57);
		contentPane.add(CheckBox3);
		CheckBox3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JCheckBox CheckBox4 = new JCheckBox("Lack");
		CheckBox4.setBackground(new Color(250, 240, 230));
		CheckBox4.setBounds(15, 327, 131, 58);
		contentPane.add(CheckBox4);
		CheckBox4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JLabel lblChooseServices = new JLabel("Choose services:");
		lblChooseServices.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblChooseServices.setBounds(15, 90, 173, 34);
		contentPane.add(lblChooseServices);
		
		JSpinner spinner1 = new JSpinner();
		spinner1.setBounds(233, 137, 78, 45);
		contentPane.add(spinner1);
		spinner1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		spinner1.setModel(new SpinnerNumberModel(50, 1, 300, 1));
		
		JSpinner spinner2 = new JSpinner();
		spinner2.setBounds(233, 204, 78, 44);
		contentPane.add(spinner2);
		spinner2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		spinner2.setModel(new SpinnerNumberModel(70, 1, 300, 1));
		
		JSpinner spinner3 = new JSpinner();
		spinner3.setBounds(233, 270, 78, 44);
		contentPane.add(spinner3);
		spinner3.setFont(new Font("Tahoma", Font.PLAIN, 17));
		spinner3.setModel(new SpinnerNumberModel(50, 1, 300, 1));
		
		JSpinner spinner4 = new JSpinner();
		spinner4.setBounds(233, 336, 78, 44);
		contentPane.add(spinner4);
		spinner4.setFont(new Font("Tahoma", Font.PLAIN, 17));
		spinner4.setModel(new SpinnerNumberModel(80, 1, 300, 1));
		
		JLabel lblChoosePrice = new JLabel("Choose price:");
		lblChoosePrice.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblChoosePrice.setBounds(211, 92, 158, 34);
		contentPane.add(lblChoosePrice);
		
		JButton btnSave = new JButton("save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnSave.setBounds(102, 430, 115, 29);
		contentPane.add(btnSave);
		 
		editServicesFrame.setVisible(true);
		
		/*
		 * 
		 * edit services actions
		 * 
		 */
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!(CheckBox1.isSelected()||CheckBox2.isSelected()||CheckBox3.isSelected()||CheckBox4.isSelected())) {
					JOptionPane.showMessageDialog(contentPane, "Please select at least one service!","",JOptionPane.WARNING_MESSAGE);
					return;
				}		
				HashMap<String,Integer> newServicesList=new HashMap<String, Integer>();
				if(CheckBox1.isSelected()) { 
					newServicesList.put(CheckBox1.getText(),(Integer) spinner1.getValue());
				}
				if(CheckBox2.isSelected()) {
					newServicesList.put(CheckBox2.getText(),(Integer) spinner2.getValue());
				}
				if(CheckBox3.isSelected()) {
					newServicesList.put(CheckBox3.getText(),(Integer) spinner3.getValue());
				}
				if(CheckBox4.isSelected()) { 
					newServicesList.put(CheckBox4.getText(),(Integer) spinner4.getValue());
				}
				property.firePropertyChange(new PropertyChangeEvent(this,"Edit services",null,newServicesList));
				editServicesFrame.dispose();			
			}
		});
		
	}
	
	/*
	 * Show appointments frame .
	 */

	public void showAppointments(HashMap<String,String> apps) {
		
	    showAppointsFrame=new JFrame();
	    showAppointsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    showAppointsFrame.setBounds(100, 100, 984, 689);
	    JPanel contentPane = new JPanel();
	    contentPane.setBackground(new Color(255, 192, 203));
	    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    showAppointsFrame.setContentPane(contentPane);
	    contentPane.setLayout(null);
	
     	JLabel lblNewLabel = new JLabel("My appointments:");
    	lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 32));
    	lblNewLabel.setBounds(15, 16, 522, 64);
    	contentPane.add(lblNewLabel);
	    
    	ArrayList<String> appointments=new ArrayList<String>();
    	for (Map.Entry<String,String> entry : apps.entrySet()) {
			appointments.add("Date: "+entry.getKey()+"  Service: "+entry.getValue());
		}
    	JList list = new JList();
    	list.setListData(appointments.toArray());
    	list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	list.setBorder(new LineBorder(new Color(0, 0, 0), 3));
    	list.setFont(new Font("Tahoma", Font.PLAIN, 22));
    	list.setBounds(53, 112, 572, 505);
    	contentPane.add(list);
	
    	JScrollBar scrollBar = new JScrollBar();
    	scrollBar.setBounds(599, 112, 26, 505);
    	contentPane.add(scrollBar);
    	
		JButton btnCancel = new JButton("cancel appointment");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnCancel.setBounds(665, 516, 226, 48);
		contentPane.add(btnCancel);
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String appointment=((String)list.getSelectedValue()).substring(6,22);
				String[] str=new String[2];
				str[0]=currentUser;
				str[1]=appointment;
				property.firePropertyChange(new PropertyChangeEvent(this,"Cancel appointment",null,str));
			}
		});
		
    	showAppointsFrame.setVisible(true);	
	}
	
/*
 * 
 * other View methods:
 *  
 */
	
    //response messages from controller for some actions on GUI.
	public void response(String action,boolean response) {
		
		switch (action) {
		
		case "New user":
			if(response) {
				   System.out.println("user created");
				   existFlag=false;			   
			}
				else {
					existFlag=true;
					JOptionPane.showMessageDialog(loginFrame.getContentPane(), "Sorry username already exist","",JOptionPane.ERROR_MESSAGE);						
				}
			break;
			
		case "User edit":
			if(response) {
				System.out.println("User edited sucsessfull");
				login();
			}
			else {
				System.out.println("User already exist");
				JOptionPane.showMessageDialog(loginFrame.getContentPane(), "Sorry there was error on user editing!","Error",JOptionPane.ERROR_MESSAGE);				
			}
			break;
		case "Add review":
			if(response)
				JOptionPane.showMessageDialog(loginFrame.getContentPane(), "Review saved successfully","",JOptionPane.DEFAULT_OPTION);
			else
				JOptionPane.showMessageDialog(loginFrame.getContentPane(), "Failed to change raiting","Error",JOptionPane.ERROR_MESSAGE);					
			break;
			
		case "Change rating":
			if(response)
				System.out.println("Rating changed");
			else
				JOptionPane.showMessageDialog(loginFrame.getContentPane(), "Failed to change raiting","Error",JOptionPane.ERROR_MESSAGE);				
			break;	
			
		case "Schedule appointment":
			if(response)
				JOptionPane.showMessageDialog(loginFrame.getContentPane(), "Appointment scheduled successfully","",JOptionPane.DEFAULT_OPTION);
			else
				JOptionPane.showMessageDialog(businessOwnerMainFrame.getContentPane(), "Failed to schedule appointment","Error",JOptionPane.ERROR_MESSAGE);							
			break;
			
		case "Cancel appointment":
			if(response) {
				JOptionPane.showMessageDialog(loginFrame.getContentPane(), "Apointment canceled succesfully","",JOptionPane.DEFAULT_OPTION);
				showAppointsFrame.dispose();
			}
			else
				JOptionPane.showMessageDialog(loginFrame.getContentPane(), "Sorry unable to cancel appointment","Error",JOptionPane.ERROR_MESSAGE);							
			break;
		case "Edit info":
			if(response)
				System.out.println("Information edited successful");
			else
				JOptionPane.showMessageDialog(editInfoFrame.getContentPane(), "Sorry unable to edit information","Error",JOptionPane.ERROR_MESSAGE);
			break;
		case "Edit services":
			if(response)
				System.out.println("Services edited successful");
			else
				JOptionPane.showMessageDialog(editInfoFrame.getContentPane(), "Sorry unable to edit services","Error",JOptionPane.ERROR_MESSAGE);
			break;	
			
		default:
			break;
		}
		
	}
	
    //show list of businesses that match sort user choose.	
	public void refreshList(ArrayList<User> new_list) {
		sortedUsersList=new_list;
		if(!new_list.isEmpty()) {			
			if(showListMatched.isEmpty()){
			    for (User element : new_list) 
			           showListMatched.addElement(element.getBusiness_name());
			}
			else {
			    for (User element : new_list) 
		           showListInterest.addElement(element.getBusiness_name());
			}
		}
	}
	
    /*
     *Check if sign in success
     */
	public void signInResponse(User user) {
		if(user==null)
			JOptionPane.showMessageDialog(loginFrame.getContentPane(), "Sorry invalid username or password!","invalid",JOptionPane.ERROR_MESSAGE);
		else {
		    System.out.println("User "+currentUser +" signed in!");
		    currentUser=user.getUsername();
		    currentUserData=user;
		    loginFrame.dispose();
		    if(user.getType().equals("Customer"))
		    	customerMainMenu(user);
		    else
		    	businessOwnerMain(user);
		}
		
	}
	
    /*
     * View appointments of customer/business in new frame.
     */
	public void viewAppointments(HashMap<String,String> apps) {
		if(apps!=null)
		showAppointments(apps);
		else
			JOptionPane.showMessageDialog(loginFrame.getContentPane(), "There is no appointments for you","",JOptionPane.DEFAULT_OPTION);
	}
	


}
