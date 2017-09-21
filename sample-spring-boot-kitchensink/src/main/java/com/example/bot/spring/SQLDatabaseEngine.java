package com.example.bot.spring;

import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.*;
import java.net.URISyntaxException;
import java.net.URI;

@Slf4j
public class SQLDatabaseEngine extends DatabaseEngine {
	@Override
	String search(String text) throws Exception {
		//Write your code here
		Connection connection = DriverManager.getConnection("postgres://nfchtqwaxpkada:a683b7e36275c3111d58e6e9142f2515030677e9da13db0c6df4aff9f08b1c4e@ec2-54-225-88-191.compute-1.amazonaws.com:5432/df7tdl564epu2l");
		PreparedStatement stmt = connection.prepareStatement("SELECT respond FROM chatbot where name=" + text + ";");
		ResultSet rs = stmt.executeQuery();
		if(rs.next())
			return rs.getString(0);
		else
			return null;
	}
	
	
	private Connection getConnection() throws URISyntaxException, SQLException {
		Connection connection;
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() +  "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

		log.info("Username: {} Password: {}", username, password);
		log.info ("dbUrl: {}", dbUrl);
		
		connection = DriverManager.getConnection(dbUrl, username, password);

		return connection;
	}

}
