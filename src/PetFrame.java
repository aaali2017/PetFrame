import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Scanner;

@SuppressWarnings("serial")
public class PetFrame extends JFrame {
	private Object[] colNames = { "Pet Name", "Breed" };
	private Object[][] data = { { "", "" } };
	private String ownerCode, first, last;
	JTextField fName;
	JTextField lName;
	JTable table = null;
	
	public PetFrame() {
		super( "Pet Database Query" );
		setLayout( new BorderLayout() );
		
		// Add table to a scroll pane
		JScrollPane scrollPane = new JScrollPane( table );
		
		// Add table to content pane
		add( scrollPane, BorderLayout.CENTER );
		
		fName = new JTextField();
		lName = new JTextField();
		
		JPanel northPanel = new JPanel( new GridLayout( 1,2 ) );
		northPanel.add( fName );
		northPanel.add( lName );
		
		JButton button = new JButton("Submit");
		add(  button, BorderLayout.SOUTH);
		button.addActionListener( new ButtonHandler() );
	}
	
	private class ButtonHandler implements ActionListener {
		public void actionPerformed( ActionEvent event ) {
			first = fName.getText();
			last = lName.getText();
			query();
			
			table = new JTable( data, colNames );
			JScrollPane scrollPane = new JScrollPane( table );
			add (scrollPane, BorderLayout.CENTER);
			
			validate();
			
		}
	}
	
	private void query() {
		Scanner in = new Scanner(System.in);
		System.out.println( "Testing Oracle JDBC Connection" );
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver" );
		} 
		
		catch (ClassNotFoundException e) {
			System.out.println( "Oracle JDBC Not Found" );
			e.printStackTrace();
			return;
		}
		
		System.out.println( "Oracle JDBC Driver Registered" );
		
		Connection conn = null;
		try {
			System.out.print("User: ");
			String user = in.next();
			System.out.print( "Password: " );
			String password = in.next();
			conn = DriverManager.getConnection( 
					"jdbc:oracle:thin:@blackhawk:1521/codb", user, password );
		}
		
		catch ( SQLException e ) {
			System.out.println( "Connection Failed" );
			e.printStackTrace();
			return;
		}
		
		System.out.printf( "\n\n\n\n\n\n\n\n\n\n\n\n\n" );
		System.out.println( "Connection Successful" );
		
		// Perform Queries
		String statement = "select oid from owner where fname = ? and lname = ? ";
		ResultSet r = null;
		
		try {
			PreparedStatement p = conn.prepareStatement( statement );
			p.clearParameters();
			p.setString(1, first);
			p.setString(2, last);
			r = p.executeQuery();
			while (r.next()) {
				ownerCode = r.getString(1);
			}
		}
		
		catch (SQLException e) {
			System.out.println("SQL statement exception");
		}
		
		String statement2 = "select pname, breed from pet where ownerid = ?";
		ResultSet r2 = null;
		try {
			PreparedStatement p2 = conn.prepareStatement( statement2 );
			p2.clearParameters();
			p2.setString( 1, ownerCode );
			
			r2 = p2.executeQuery();
		}
		
		catch (SQLException e) {
			System.out.println( "SQL statement exception2" );
		}
		
		colNames = new Object[2];
		data = new Object[20][2];
		
		colNames[0] = "Pet Name";
		colNames[1] = "Breed";
		
		try {
			int row = 0;
			
			while ( r2.next() ) {
				data[row][0] = r2.getString(1);
				data[row][1] = r2.getString(2);
				row++;
			}
		}
		
		catch ( SQLException e ) {
			System.out.println( "ResultSet exception" );
		}
		
	}
}
