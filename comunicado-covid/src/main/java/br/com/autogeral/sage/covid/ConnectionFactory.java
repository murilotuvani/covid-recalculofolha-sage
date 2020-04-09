package br.com.autogeral.sage.covid;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	
	public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/folha";
        //String url = "jdbc:postgresql://ad01:5432/folha";
        Properties prop = new Properties();
        prop.put("user", "postgres");
        prop.put("password", "postgres");
        Connection conn = DriverManager.getConnection(url, prop);
        return conn;
    }

}
