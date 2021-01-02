package advanceJava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class RB {

	public static void main(String[] args) throws Exception {
		ResourceBundle rb = ResourceBundle.getBundle("advanceJava.app");
		Class.forName(rb.getString("Driver"));
		Connection conn = DriverManager.getConnection(rb.getString("url"),rb.getString("username"), rb.getString("password"));
		int id = 2;

		PreparedStatement ps = conn.prepareStatement("select * from employee where id=?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString(1));
			System.out.println(rs.getString(2));
			System.out.println(rs.getString(3));
			System.out.println(rs.getString(4));

		}
		conn.close();
		ps.close();
		
		

		}
	}


