package DBMS;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
@SuppressWarnings("serial")
public class InsertTables extends Frame implements ActionListener {
	MenuBar mb;
	MenuItem m0,m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12;
	Menu Personal_details,Exchange,details,upi,View;
	TextField nameText,ph_noText,mail_idText,languageText;
	TextField R_PText,FROM_TOText,AmountText,ReasonText,DayText,ModeofpaymentText;
	TextField banknameText,acc_noText,card_noText,cvvText;
	TextField app_nameText,upi_idText ;

	TextArea errorText;
	Connection connection;
	Statement statement;
	
	Button updateButton;
	Button insertButton;
	List Personal_detailsList,ExchangeList,detailsList,upiList,Future_paymentsList,ViewList;
	ResultSet rs;
	
	
	//For delete
	Button deleteRowButton;
	public InsertTables()
	{
		try 
		{
			Class.forName ("oracle.jdbc.driver.OracleDriver");
		} 
		catch (Exception e) 
		{
			System.err.println("Unable to find and load driver");
			System.exit(1);
		}
		connectToDB ();
	}

	public void connectToDB() 
    {
		try 
		{
		  connection=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","akshitha","akshitha");
		  statement = connection.createStatement();

		} 
		catch (SQLException connectException) 
		{
		  System.out.println(connectException.getMessage());
		  System.out.println(connectException.getSQLState());
		  System.out.println(connectException.getErrorCode());
		  System.exit(1);
		}
    }
	public void buildFrame()
	{
		//Basic Frame Properties
		setTitle("Money Management System");
		setSize(500, 600);
		setVisible(true);
		
		//menubar
		mb = new MenuBar();
		setMenuBar(mb);  
        setSize(550,500);  
        setLayout(null);  
        setVisible(true);
        
        //
        Personal_details=new Menu("Personal Details");
        m0=new MenuItem("View Personal_details");
        Personal_details.add(m0);
        mb.add(Personal_details);
        
        Exchange=new Menu("Exchange");  
        
         m1=new MenuItem("Insert Exchange");  
         m2=new MenuItem("Update Exchange");  
         m3=new MenuItem("Delete Exchange");  
         m4=new MenuItem("View Exchange");
           
        Exchange.add(m1);  
        Exchange.add(m2);  
        Exchange.add(m3); 
        Exchange.add(m4);
        
        mb.add( Exchange);
    
        details=new Menu("Bank_details"); 
        m5=new MenuItem("Insert details");
        m6=new MenuItem("Update details");
        m7=new MenuItem("Delete details");
        m8=new MenuItem("View details");
        
        details.add(m5);
        details.add(m6);
        details.add(m7);
        details.add(m8);
        
        mb.add(details);
        
        
        upi=new Menu("UPI APPS");  
        m9=new MenuItem("Insert upi");
        m10=new MenuItem("Update upi");
        m11=new MenuItem("Delete upi");
        m12=new MenuItem("View upi");
       
        upi.add(m9);
        upi.add(m10);
        upi.add(m11);
        upi.add(m12);
        
        mb.add(upi);
        
  
        
       
        
      
       //Registering action Listeners
        m0.addActionListener(this);
        m1.addActionListener(this);
        m2.addActionListener(this);
        m3.addActionListener(this);
        m4.addActionListener(this);
        m5.addActionListener(this);
        m6.addActionListener(this);
        m7.addActionListener(this);
        m8.addActionListener(this);
        m9.addActionListener(this);
        m10.addActionListener(this);
        m11.addActionListener(this);
        m12.addActionListener(this);
       
        
      
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		String arg = ae.getActionCommand();
		if(arg.equals("View Personal_details"))
			this.viewpdGUI();
		if(arg.equals("Insert Exchange"))
			this.buildExchangeGUI();
		if(arg.equals("Update Exchange"))
			this.updateExchangeGUI();
		if(arg.equals("Delete Exchange"))
			this.deleteGUIExchange();
		if(arg.equals("View Exchange"))
			this.viewExchangeGUI();	
		if(arg.equals("Insert details"))
			this.buildGUIdetails();
		if(arg.equals("Update details"))
			this.updatedetailsGUI();
		if(arg.equals("Delete details"))
			this.deleteGUIdetails();
		if(arg.equals("View details"))
			this.viewdetailsGUI();
		if(arg.equals("Insert upi"))
			this.buildGUIupi();
		if(arg.equals("Update upi"))
			this.updateupiGUI();
		if(arg.equals("Delete upi"))
			this.deleteGUIupi();
		if(arg.equals("View upi"))
			this.viewupiGUI();	
		
	}
	private void loadpd() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT * FROM personal_details");
		  while (rs.next()) 
		  {
			  Personal_detailsList.add(rs.getString("name"));
		  }
		} 
		catch (SQLException e) 
		{ 
		  displaySQLErrors(e);
		}
	}
	public void viewpdGUI() 
	{	
		removeAll();
		Personal_detailsList = new List(6);
		loadpd();
		add(Personal_detailsList);
		
		//When a list item is selected populate the text fields
		Personal_detailsList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM personal_details");
					while (rs.next()) 
					{
						if (rs.getString("name").equals(Personal_detailsList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						nameText.setText(rs.getString("name"));
						ph_noText.setText(rs.getString("ph_no"));
						mail_idText.setText(rs.getString("mail_id"));
						languageText.setText(rs.getString("language"));
						
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});	    
		//Handle Update  Button
		updateButton = new Button("Update Personal_details");
		updateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE Personal_details "
					+ "SET ph_no=" + ph_noText.getText()  
					+ " WHERE name= '" + Personal_detailsList.getSelectedItem() + "'");
					errorText.append("\nUpdated " + i + " rows successfully");
					ExchangeList.removeAll();
					loadpd();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		nameText = new TextField(15);
	    nameText.setEditable(false);
		ph_noText = new TextField(15);
		ph_noText.setEditable(false);
		mail_idText = new TextField(15);
		mail_idText.setEditable(false);
		languageText = new TextField(15);
		languageText.setEditable(false);
		

		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("NAME:"));
		first.add(nameText);
		first.add(new Label("PHONE NUMBER:"));
		first.add(ph_noText);
		first.add(new Label("Mail Id:"));
		first.add(mail_idText);
		first.add(new Label("Language:"));
		first.add(languageText);
		
		
	
		
		Panel second = new Panel(new GridLayout(4, 1));
		//second.add(updateButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		
		add(second);
		add(third);
	    
		//setTitle("Update ....");
		//setSize(500, 600);
		setLayout(new FlowLayout());
		setVisible(true);
		
	}
	public void buildGUIupi() 
	{	 	
		removeAll();
		//Handle Insert Account Button
		insertButton = new Button("Insert upi");
		insertButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{		  
				  String query= "INSERT INTO upi VALUES('" + app_nameText.getText() + "', " + "'" + upi_idText.getText() +  "')";
				  int i = statement.executeUpdate(query);
				  errorText.append("\nInserted " + i + " rows successfully");
				} 
				catch (SQLException insertException) 
				{
				  displaySQLErrors(insertException);
				}
			}
		});	
		app_nameText = new TextField(15);
		upi_idText = new TextField(15);
	
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("App Name:"));
		first.add(app_nameText);
		first.add(new Label("UPI ID:"));
		first.add(upi_idText);
		
		first.setBounds(125,90,200,100);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(insertButton);
        		second.setBounds(125,220,150,100);         
		
		Panel third = new Panel();
		third.add(errorText);
		third.setBounds(125,320,300,200);
		
		//setLayout(null);

		add(first);
		add(second);
		add(third);
		
		setLayout(new FlowLayout());
		setVisible(true);
	    
	
	}
	private void loadupi() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT * FROM upi");
		  while (rs.next()) 
		  {
			  upiList.add(rs.getString("app_name"));
		  }
		} 
		catch (SQLException e) 
		{ 
		  displaySQLErrors(e);
		}
	}
	public void updateupiGUI() 
	{	
		removeAll();
		upiList = new List(6);
		loadupi();
		add(upiList);
		
		//When a list item is selected populate the text fields
		upiList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM upi");
					while (rs.next()) 
					{
						if (rs.getString("app_name").equals(upiList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						app_nameText.setText(rs.getString("app_name"));
						upi_idText.setText(rs.getString("upi_id"));
						
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});	    
		//Handle Update upi Button
		updateButton = new Button("Update upi");
		updateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE upi "
					+ "SET app_name=" + app_nameText.getText()  
					+ " WHERE upi_id = '" + upiList.getSelectedItem() + "'");
					errorText.append("\nUpdated " + i + " rows successfully");
					ExchangeList.removeAll();
					loadupi();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		app_nameText = new TextField(15);
	    app_nameText.setEditable(false);
		upi_idText = new TextField(15);
		upi_idText.setEditable(false);
		

		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		
		first.add(new Label("APP NAME"));
		first.add(app_nameText);
		first.add(new Label("UPI ID:"));
		first.add(upi_idText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(updateButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		
		add(second);
		add(third);
	    
		//setTitle("Update ....");
		//setSize(500, 600);
		setLayout(new FlowLayout());
		setVisible(true);
		
	}
	
	public void deleteGUIupi() 
	{	
		removeAll();
	    upiList = new List(10);
		loadupi();
		add(upiList);
		
		//When a list item is selected populate the text fields
		upiList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM upi");
					while (rs.next()) 
					{
						if (rs.getString("app_name").equals(upiList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						app_nameText.setText(rs.getString("app_name"));
						upi_idText.setText(rs.getString("upi_id"));
						
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});
	    
		//Handle Delete  Button
		deleteRowButton = new Button("Delete Row");
		deleteRowButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("DELETE FROM upi WHERE app_name = '" + upiList.getSelectedItem()+"'");
					errorText.append("\nDeleted " + i + " rows successfully");
					
					app_nameText.setText(null);
					upi_idText.setText(null);
					
					upiList.removeAll();
					loadupi();
				} 
				catch (SQLException deleteException) 
				{
					displaySQLErrors(deleteException);
				}
			}
		});
		
		
		app_nameText = new TextField(15);
		upi_idText = new TextField(15);
		
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);
		
		app_nameText.setEditable(false);
		upi_idText.setEditable(false);
		
	

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
	
		first.add(new Label("APP name:"));
		first.add(app_nameText);
		first.add(new Label("upi_id:"));
		first.add(upi_idText);
		
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(deleteRowButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    

		setLayout(new FlowLayout());
		setVisible(true);
		
	}

	public void viewupiGUI() 
	{	
		removeAll();
		upiList = new List(6);
		loadupi();
		add(upiList);
		
		//When a list item is selected populate the text fields
		upiList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM upi");
					while (rs.next()) 
					{
						if (rs.getString("app_name").equals(upiList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						app_nameText.setText(rs.getString("app_name"));
						upi_idText.setText(rs.getString("upi_id"));
						
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});	    
		//Handle Update Menu Button
		updateButton = new Button("Update upi");
		updateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE upi "
					+ "SET upi_id=" + upi_idText.getText()  
					+ " WHERE app_name= '" + upiList.getSelectedItem() + "'");
					errorText.append("\nUpdated " + i + " rows successfully");
					ExchangeList.removeAll();
					loadupi();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		app_nameText = new TextField(15);
	    app_nameText.setEditable(false);
		upi_idText = new TextField(15);
		upi_idText.setEditable(false);
		

		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("APP NAME:"));
		first.add(app_nameText);
		first.add(new Label("UPI ID:"));
		first.add(upi_idText);
	
		
		Panel second = new Panel(new GridLayout(4, 1));
		//second.add(updateButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		
		add(second);
		add(third);
	    
		//setTitle("Update ....");
		//setSize(500, 600);
		setLayout(new FlowLayout());
		setVisible(true);
		
	}
	public void viewdetailsGUI() 
	{	
		removeAll();
		detailsList = new List(6);
		loaddetails();
		add(detailsList);
		
		//When a list item is selected populate the text fields
		detailsList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM bank");
					while (rs.next()) 
					{
						if (rs.getString("bankname").equals(detailsList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						banknameText.setText(rs.getString("bankname"));
						acc_noText.setText(rs.getString("acc_no"));
						card_noText.setText(rs.getString("card_no"));
						cvvText.setText(rs.getString("cvv"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});	    
		//Handle Update Menu Button
		updateButton = new Button("Update details");
		updateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE bank"
					+ "SET acc_no=" + acc_noText.getText()  
					+ " WHERE bankname = '" + detailsList.getSelectedItem() + "'");
					errorText.append("\nUpdated " + i + " rows successfully");
					detailsList.removeAll();
					loaddetails();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		banknameText = new TextField(15);
		banknameText.setEditable(false);
		acc_noText = new TextField(15);
		acc_noText.setEditable(false);
		card_noText = new TextField(15);
		card_noText = new TextField(15);
		cvvText.setEditable(false);

		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Bank Name:"));
		first.add(banknameText);
		first.add(new Label("Account Number:"));
		first.add(acc_noText);
		first.add(new Label("Card Number:"));
		first.add(card_noText);
		first.add(new Label("CVV:"));
		first.add(cvvText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		//second.add(updateButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		
		//setSize(500, 600);
		setLayout(new FlowLayout());
		setVisible(true);
		
	}
	private void loaddetails() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT * FROM bank");
		  while (rs.next()) 
		  {
			  detailsList.add(rs.getString("bankname"));
		  }
		} 
		catch (SQLException e) 
		{ 
		  displaySQLErrors(e);
		}
	}
	public void updatedetailsGUI() 
	{	
		removeAll();
		detailsList = new List(6);
		loaddetails();
		add(detailsList);
		
		//When a list item is selected populate the text fields
		detailsList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM bank");
					while (rs.next()) 
					{
						if (rs.getString("bankname").equals(detailsList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						banknameText.setText(rs.getString("bankname"));
						acc_noText.setText(rs.getString("acc_no"));
						card_noText.setText(rs.getString("card_no"));
						cvvText.setText(rs.getString("cvv"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});	    
		//Handle Update Menu Button
		updateButton = new Button("Update details");
		updateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE bank "
					+ "SET acc_no=" + acc_noText.getText()  
					+ " WHERE bankname = '" + detailsList.getSelectedItem() + "'");
					errorText.append("\nUpdated " + i + " rows successfully");
					detailsList.removeAll();
					loaddetails();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		banknameText = new TextField(15);
		banknameText.setEditable(false);
		acc_noText = new TextField(15);
		acc_noText.setEditable(false);
		card_noText = new TextField(15);
		cvvText = new TextField(15);
		cvvText.setEditable(false);

		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Bank Name:"));
		first.add(banknameText);
		first.add(new Label("Account Number:"));
		first.add(acc_noText);
		first.add(new Label("Card Number:"));
		first.add(card_noText);
		first.add(new Label("CVV:"));
		first.add(cvvText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(updateButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    
		
		//setSize(500, 600);
		setLayout(new FlowLayout());
		setVisible(true);
		
	}
	
	public void deleteGUIdetails()
	{
		removeAll();
	    detailsList = new List(10);
		loaddetails();
		add(detailsList);
		
		//When a list item is selected populate the text fields
		detailsList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM bank");
					while (rs.next()) 
					{
						if (rs.getString("bankname").equals(detailsList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						banknameText.setText(rs.getString("bankname"));
						acc_noText.setText(rs.getString("acc_no"));
						card_noText.setText(rs.getString("card_no"));
						cvvText.setText(rs.getString("cvv"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});
	    
		//Handle Delete restaurant Button
		deleteRowButton = new Button("Delete Row");
		deleteRowButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("DELETE FROM bank WHERE bankname = '" + detailsList.getSelectedItem()+"'");
					errorText.append("\nDeleted " + i + " rows successfully");
					banknameText.setText(null);
					acc_noText.setText(null);
					card_noText.setText(null);
					cvvText.setText(null);
					detailsList.removeAll();
					loaddetails();
				} 
				catch (SQLException deleteException) 
				{
					displaySQLErrors(deleteException);
				}
			}
		});
		
		banknameText = new TextField(15);
		acc_noText = new TextField(15);
		card_noText = new TextField(15);
		cvvText = new TextField(15);
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);
		
		banknameText.setEditable(false);
		acc_noText.setEditable(false);
		card_noText.setEditable(false);
		cvvText.setEditable(false);
	

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Bank name:"));
		first.add(banknameText);
		first.add(new Label("Acc no:"));
		first.add(acc_noText);
		first.add(new Label("Card Number:"));
		first.add(card_noText);
		first.add(new Label("CVV:"));
		first.add(cvvText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(deleteRowButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    

		setLayout(new FlowLayout());
		setVisible(true);
		
	}


	public void buildGUIdetails() 
	{	 	
		removeAll();
		//Handle Insert Account Button
		insertButton = new Button("Insert details");
		insertButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{		  
				  String query= "INSERT INTO bank VALUES('" + banknameText.getText() + "', " + "'" + acc_noText.getText() + "'," + card_noText.getText() + ",'" + cvvText.getText() + "')";
				  int i = statement.executeUpdate(query);
				  errorText.append("\nInserted " + i + " rows successfully");
				} 
				catch (SQLException insertException) 
				{
				  displaySQLErrors(insertException);
				}
			}
		});	
		banknameText = new TextField(15);
		acc_noText = new TextField(15);
		card_noText = new TextField(15);
		cvvText= new TextField(15);

		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Bank Name:"));
		first.add(banknameText);
		first.add(new Label("Account Number:"));
		first.add(acc_noText);
		first.add(new Label("Card number:"));
		first.add(card_noText);
		first.add(new Label("CVV:"));
		first.add(cvvText);
		first.setBounds(125,90,200,100);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(insertButton);
        		second.setBounds(125,220,150,100);         
		
		Panel third = new Panel();
		third.add(errorText);
		third.setBounds(125,320,300,200);
		
		//setLayout(null);

		add(first);
		add(second);
		add(third);
		
		setLayout(new FlowLayout());
		setVisible(true);
	    
	
	}
	public void buildExchangeGUI() 
	{	
		removeAll();
		//Handle Insert Account Button
		insertButton = new Button("Insert Exchange");
		insertButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{		  
				  String query= "INSERT INTO exchange VALUES('" + R_PText.getText() + "',' " + FROM_TOText.getText() + "','" + ReasonText.getText() + "','"   + AmountText.getText() + "','"+ ModeofpaymentText.getText() + "')";
				  int i = statement.executeUpdate(query);
				  errorText.append("\nInserted " + i + " rows successfully");
				} 
				catch (SQLException insertException) 
				{
				  displaySQLErrors(insertException);
				}
			}
		});	
		R_PText = new TextField(15);
		FROM_TOText = new TextField(15);
		ReasonText = new TextField(15);
		AmountText= new TextField(15);
		ModeofpaymentText= new TextField(15);

		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("RECEIVED/PAID(R/P):"));
		first.add(R_PText);
		first.add(new Label("FROM/TO:"));
		first.add(FROM_TOText);
		first.add(new Label("Reason:"));
		first.add(ReasonText);
		first.add(new Label("Amount:"));
		first.add(AmountText);
		
		first.add(new Label("MODE OF PAYMENT:"));
		first.add(ModeofpaymentText);
		first.setBounds(700,500,800,600);
		
		Panel second = new Panel(new GridLayout(5, 3));
		second.add(insertButton);
        		second.setBounds(800,420,600,300);         
		
		Panel third = new Panel();
		third.add(errorText);
		third.setBounds(400,600,500,700);
		
		//setLayout(null);

		add(first);
		add(second);
		add(third);
		
		setLayout(new FlowLayout());
		setVisible(true);
	    
	}
	private void loadExchange() 
	{	   
		try 
		{
		  rs = statement.executeQuery("SELECT * FROM exchange");
		  while (rs.next()) 
		  {
			  ExchangeList.add(rs.getString("FROM_TO"));
		  }
		} 
		catch (SQLException e) 
		{ 
		  displaySQLErrors(e);
		}
	}
	public void updateExchangeGUI() 
	{	
		removeAll();
		ExchangeList = new List(6);
		loadExchange();
		add(ExchangeList);
		
		//When a list item is selected populate the text fields
		ExchangeList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM exchange");
					while (rs.next()) 
					{
						if (rs.getString("FROM_TO").equals(ExchangeList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						R_PText.setText(rs.getString("R_P"));
						FROM_TOText.setText(rs.getString("FROM_TO"));
						AmountText.setText(rs.getString("Amount"));
						ReasonText.setText(rs.getString("Reason"));
						ModeofpaymentText.setText(rs.getString("modeofpayment"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});	    
		//Handle Update  Button
		updateButton = new Button("Update Exchange");
		updateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE Exchange "
					+ "SET amount=" + AmountText.getText()  
					+ " WHERE FROM_TO = '" + ExchangeList.getSelectedItem() + "'");
					errorText.append("\nUpdated " + i + " rows successfully");
					ExchangeList.removeAll();
					loadExchange();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		ReasonText = new TextField(15);
	    ReasonText.setEditable(false);
		FROM_TOText = new TextField(15);
		FROM_TOText.setEditable(false);
		R_PText = new TextField(15);
		AmountText = new TextField(15);
		AmountText.setEditable(false);
		ModeofpaymentText = new TextField(15);
		ModeofpaymentText.setEditable(false);

		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		
		first.add(new Label("Amount:"));
		first.add(AmountText);
		first.add(new Label("REceieved/paid(r/p):"));
		first.add(R_PText);
		first.add(new Label("FROM/TO:"));
		first.add(FROM_TOText);
		first.add(new Label("Reason:"));
		first.add(ReasonText);
		first.add(new Label("Modeofpayment:"));
		first.add(ModeofpaymentText);
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(updateButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		
		add(second);
		add(third);
	    
		//setTitle("Update ....");
		//setSize(500, 600);
		setLayout(new FlowLayout());
		setVisible(true);
		
	}
	
	public void deleteGUIExchange() 
	{	
		removeAll();
	    ExchangeList = new List(10);
		loadExchange();
		add(ExchangeList);
		
		//When a list item is selected populate the text fields
		ExchangeList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM exchange");
					while (rs.next()) 
					{
						if (rs.getString("FROM_TO").equals(ExchangeList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						R_PText.setText(rs.getString("R_P"));
						AmountText.setText(rs.getString("amount"));
						FROM_TOText.setText(rs.getString("FROM_TO"));
						ReasonText.setText(rs.getString("Reason"));
						ModeofpaymentText.setText(rs.getString("modeofpayment"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});
	    
		//Handle Delete  Button
		deleteRowButton = new Button("Delete Row");
		deleteRowButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("DELETE FROM exchange WHERE FROM_TO = '" + ExchangeList.getSelectedItem()+"'");
					errorText.append("\nDeleted " + i + " rows successfully");
					
					AmountText.setText(null);
					FROM_TOText.setText(null);
					R_PText.setText(null);
					ReasonText.setText(null);
					ModeofpaymentText.setText(null);
					ExchangeList.removeAll();
					loadExchange();
				} 
				catch (SQLException deleteException) 
				{
					displaySQLErrors(deleteException);
				}
			}
		});
		
		
		AmountText = new TextField(15);
		ReasonText = new TextField(15);
		R_PText = new TextField(15);
		FROM_TOText = new TextField(15);
		ModeofpaymentText = new TextField(15);
		
		errorText = new TextArea(10, 40);
		errorText.setEditable(false);
		
		R_PText.setEditable(false);
		AmountText.setEditable(false);
		FROM_TOText.setEditable(false);
		ReasonText.setEditable(false);
		ModeofpaymentText.setEditable(false);
	

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("Receieved/Paid(r/p):"));
		first.add(R_PText);
		first.add(new Label("FROM_TO:"));
		first.add(FROM_TOText);
		first.add(new Label("Amount:"));
		first.add(AmountText);
		first.add(new Label("Reason:"));
		first.add(ReasonText);
		first.add(new Label("Modeofpayment:"));
		first.add(ModeofpaymentText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		second.add(deleteRowButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		add(second);
		add(third);
	    

		setLayout(new FlowLayout());
		setVisible(true);
		
	}

	public void viewExchangeGUI() 
	{	
		removeAll();
		ExchangeList = new List(6);
		loadExchange();
		add(ExchangeList);
		
		//When a list item is selected populate the text fields
		ExchangeList.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e) 
			{
				try 
				{
					rs = statement.executeQuery("SELECT * FROM exchange");
					while (rs.next()) 
					{
						if (rs.getString("FROM_TO").equals(ExchangeList.getSelectedItem()))
						break;
					}
					if (!rs.isAfterLast()) 
					{
						FROM_TOText.setText(rs.getString("FROM_TO"));
						AmountText.setText(rs.getString("Amount"));
						ReasonText.setText(rs.getString("Reason"));
						ModeofpaymentText.setText(rs.getString("Modeofpayment"));
					}
				} 
				catch (SQLException selectException) 
				{
					displaySQLErrors(selectException);
				}
			}
		});	    
		//Handle Update Menu Button
		updateButton = new Button("Update Exchange");
		updateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					Statement statement = connection.createStatement();
					int i = statement.executeUpdate("UPDATE Exchange "
					+ "SET Amount=" + AmountText.getText()  
					+ " WHERE FROM_TO = '" + ExchangeList.getSelectedItem() + "'");
					errorText.append("\nUpdated " + i + " rows successfully");
					ExchangeList.removeAll();
					loadExchange();
				} 
				catch (SQLException insertException) 
				{
					displaySQLErrors(insertException);
				}
			}
		});
		
		FROM_TOText = new TextField(15);
	    FROM_TOText.setEditable(false);
		AmountText = new TextField(15);
		AmountText.setEditable(false);
		ReasonText = new TextField(15);
		ReasonText.setEditable(false);
		ModeofpaymentText = new TextField(15);
		ModeofpaymentText.setEditable(false);

		errorText = new TextArea(10, 40);
		errorText.setEditable(false);

		Panel first = new Panel();
		first.setLayout(new GridLayout(4, 2));
		first.add(new Label("FROM_TO:"));
		first.add(FROM_TOText);
		first.add(new Label("Amount:"));
		first.add(AmountText);
		first.add(new Label("Reason:"));
		first.add(ReasonText);
		first.add(new Label("Modeofpayment:"));
		first.add(ModeofpaymentText);
		
		Panel second = new Panel(new GridLayout(4, 1));
		//second.add(updateButton);
		
		Panel third = new Panel();
		third.add(errorText);
		
		add(first);
		
		add(second);
		add(third);
	    
		//setTitle("Update ....");
		//setSize(500, 600);
		setLayout(new FlowLayout());
		setVisible(true);
		
	}
	public void displaySQLErrors(SQLException e) 
	{
		errorText.append("\nSQLException: " + e.getMessage() + "\n");
		errorText.append("SQLState:     " + e.getSQLState() + "\n");
		errorText.append("VendorError:  " + e.getErrorCode() + "\n");
	}

	public static void main(String[] args) 
	{
		InsertTables it = new InsertTables();
		it.addWindowListener(new WindowAdapter(){
		  public void windowClosing(WindowEvent e) 
		  {
			System.exit(0);
		  }
		});
		it.buildFrame();
	}
}

