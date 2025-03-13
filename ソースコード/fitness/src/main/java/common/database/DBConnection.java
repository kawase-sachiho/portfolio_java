package common.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements AutoCloseable{
	private Connection connection;

	public DBConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.h2.Driver");

		this.connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/fitness", "sa",
				"sa");
	}

	public Connection getInstance() throws SQLException, ClassNotFoundException {
		return this.connection;
	}


	public void close() {
		try {
			this.connection.close();
		} catch (Exception e) {
		}
	}

}
