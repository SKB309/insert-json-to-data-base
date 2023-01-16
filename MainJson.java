package com.said;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.xml.crypto.Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class MainJson {

	WebPage webPage;
	public static Connection connection;

	public static void mainMenue() {

		System.out.println("***********************************************************");
		System.out.println(" 1- connect To Data Base ");
		System.out.println(" 2- print json Api");
		System.out.println(" 3- create Json Table  ");
		System.out.println(" 4- insert Into Table Json Table");
		System.out.println(" 5- test creat by MARWA way");
		System.out.println(" 6- test insert by MARWA way ");
		System.out.println(" 7- git by id");
		System.out.println(" 8- update By Id");
		System.out.println(" 9- delete By Id");
		System.out.println(" 0- Exit ");
		System.out.println("***********************************************************");

	}

	static void connectToDataBase() throws Throwable, IllegalAccessException, ClassNotFoundException {

		Connection connection;

		try {

			// Create a connection to the database

			String serverName = "localhost";

			String url = "jdbc:sqlserver://localhost:1433;databaseName=JsonData;encrypt=true;trustServerCertificate=true";
			String user = "sa";
			String pass = "root";
			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);

			connection = DriverManager.getConnection(url, user, pass);

			System.out.println("Successfully Connected to the database!" + " JsonData ");

		} catch (SQLException e) {

			System.out.println("Could not connect to the database " + e.getMessage());
		}

	}

	public static void createJsonDataTable() throws Throwable, IllegalAccessException, ClassNotFoundException {

		String url = "jdbc:sqlserver://localhost:1433;databaseName=JsonData;encrypt=true;trustServerCertificate=true";
		String user = "sa";
		String pass = "root";

		try (Connection conn = DriverManager.getConnection(url, user, pass); Statement stmt = conn.createStatement();) {
			String sql = "CREATE TABLE JsonTable " + "( Id int PRIMARY KEY IDENTITY(1,1) ,"
					+ " web_pages VARCHAR(1000) ," + " state_province VARCHAR(1000),"
					+ " alpha_two_code VARCHAR(1000)  ," + " name VARCHAR(1000) ," + " country VARCHAR(1000), "
					+ " domains VARCHAR(1000),)";

			stmt.executeUpdate(sql);
			System.out.println("Created table in given database...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void jsonApi() throws Throwable, InterruptedException {

		HttpClient client = HttpClient.newHttpClient();

		System.out.println(" printing (API) information  ");

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://universities.hipolabs.com/search?country=United+States")).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		System.out.printf(response.body());

	}

	public static void insertIntoTableJsonData() throws Throwable, InterruptedException {

		String url = "jdbc:sqlserver://localhost:1433;databaseName=JsonData;encrypt=true;trustServerCertificate=true";
		String user = "sa";
		String pass = "root";

		Scanner scanner = new Scanner(System.in);

		HttpRequest request2 = HttpRequest.newBuilder()

				.uri(URI.create("http://universities.hipolabs.com/search?country=United+States")).build();

		HttpResponse<String> response2;

		HttpClient client = HttpClient.newHttpClient();

		System.out.println(" printing (API) information  ");

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://universities.hipolabs.com/search?country=United+States")).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
		System.out.println(response.body());
		Gson gson = new Gson();
		WebPage[] webPage = new Gson().fromJson(response.body(), WebPage[].class);
		System.out.println(webPage);

		for (WebPage web : webPage) {

			String sql = "Insert into JsonTable values( '" + web.getWeb_pages()[0] + "','" + web.getState_province()
					+ "','" + web.getAlpha_two_code() + "','" + web.getName() + "','" + web.getCountry() + "','"
					+ web.getDomains()[0] + "')";

			Connection con;

			try {

				Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
				// Registering drivers
				DriverManager.registerDriver(driver);

				// Reference to connection interface
				con = DriverManager.getConnection(url, user, pass);

				// Creating a statement
				Statement st = con.createStatement();

				// Executing query
				int m = st.executeUpdate(sql);
				if (m >= 1)
					System.out.println("inserted successfully : " + sql);
				else
					System.out.println("insertion failed");

				// Closing the connections
				con.close();
			}

//		 Catch block to handle exceptions
			catch (Exception ex) {
				// Display message when exceptions occurs
				System.err.println(ex);
			}
		}
	}

	public static void createJsonDataTableTestMarwaWay()
			throws Throwable, IllegalAccessException, ClassNotFoundException {

		String url = "jdbc:sqlserver://localhost:1433;databaseName=JsonData;encrypt=true;trustServerCertificate=true";
		String user = "sa";
		String pass = "root";

		try (Connection conn = DriverManager.getConnection(url, user, pass); Statement stmt = conn.createStatement();) {
			String sql = "CREATE TABLE SoloProject " + "( Id int PRIMARY KEY IDENTITY(1,1) ,"
					+ " web_pages VARCHAR(1000) ," + " state_province VARCHAR(1000),"
					+ " alpha_two_code VARCHAR(1000)  ," + " name VARCHAR(1000) ," + " country VARCHAR(1000), "
					+ " domains VARCHAR(1000),)";

			stmt.executeUpdate(sql);
			System.out.println("Created table in given database...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void testInsertMarwaWay() throws Throwable, InterruptedException {

		String url = "jdbc:sqlserver://localhost:1433;databaseName=JsonData;encrypt=true;trustServerCertificate=true";
		String user = "sa";
		String pass = "root";
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://universities.hipolabs.com/search?country=United+States")).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		String uglyJsonString = response.body();
//			System.out.println(uglyJsonString);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(uglyJsonString);
		String prettyJsonString = gson.toJson(je);
		System.out.println(prettyJsonString);
		WebPage[] M = gson.fromJson(prettyJsonString, WebPage[].class);

		for (WebPage x : M) {

			String webpage = x.getWeb_pages()[0];
			String name = x.getName();
			String domian = x.getDomains()[0];
			String state_province = x.getState_province();
			String alpha_two_code = x.getAlpha_two_code();
			String country = x.getCountry();
			String SQLqueryForInserting = "insert into SoloProject(web_pages,state_province, alpha_two_code,name, country,domains)"
					+ " values('" + webpage + "' ,'" + state_province + "', '" + alpha_two_code + "','" + name + "' ,' "
					+ domian + "','" + country + "')";

			System.out.println(SQLqueryForInserting);
			Connection con;
			try {
				Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
				DriverManager.registerDriver(driver);
				con = DriverManager.getConnection(url, user, pass);
				Statement st = con.createStatement();
				// Executing query
				int m = st.executeUpdate(SQLqueryForInserting);
				if (m >= 0)
					System.out.println("inserted successfully : " + SQLqueryForInserting);
				else
					System.out.println("insertion failed");
				// Closing the connections
				con.close();
			} catch (Exception ex) {
				// Display message when exceptions occurs
				System.err.println(ex);
			}
		}
	}

	public static void toExit() {

		System.out.println("***********");
		System.out.println("Good Bay");
		System.out.println("***********");

	}

	public static void byDefault() {

		System.out.println("plase Enter correct choise");

	}

	public static void getById() throws Throwable, IllegalAccessException, ClassNotFoundException {
//		take id input from the user
//		print on console
//		use SELECT query and ResultSets for showing

		Scanner sc = new Scanner(System.in);

		String serverName = "localhost";

		String url = "jdbc:sqlserver://localhost:1433;databaseName=JsonData;encrypt=true;trustServerCertificate=true";
		String user = "sa";
		String pass = "root";
		Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
		DriverManager.registerDriver(driver);

		connection = DriverManager.getConnection(url, user, pass);

		System.out.println(" Please Enter The ID Of The Row To Print");
		Scanner inputScanner = new Scanner(System.in);
		int userInput = inputScanner.nextInt();
		String sqlQueryToRead = "SELECT * FROM JsonTable WHERE id = " + userInput;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQueryToRead);

//                int count = 0; we can not use count here because it will limit my results

			while (resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String webPages = resultSet.getString("web_pages");
				String stateProvince = resultSet.getString("state_province");
				String alphaTwoCode = resultSet.getString("alpha_two_code");
				String name = resultSet.getString("name");
				String country = resultSet.getString("country");
				String domains = resultSet.getString("domains");

				System.out.println(id + " " + webPages + " " + stateProvince + " " + alphaTwoCode + " " + name + " "
						+ country + "" + domains);
			}
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	public static void updateById() throws Throwable {
//		take id input from the user
//		print on console
//		use Update query and ResultSets for showing
		Connection con;

		String url = "jdbc:sqlserver://localhost:1433;databaseName=JsonData;encrypt=true;trustServerCertificate=true";
		String user = "sa";
		String pass = "root";
		con = DriverManager.getConnection(url, user, pass);

		System.out.println(" Please Enter The ID To Update Its Data");
		Scanner inputScanner = new Scanner(System.in);
		int userInput = inputScanner.nextInt();
		System.out.println(" Please Enter The New web_pages ");
		String web_pages = inputScanner.next();
		System.out.println(" Please Enter The New name");
		String name = inputScanner.next();

		String sqlQueryToUpdate = "UPDATE JsonTable SET web_pages = " + "'" + web_pages + "',name=" + "'" + name + "'"
				+ "WHERE id =" + userInput;
		System.out.println(sqlQueryToUpdate);
		try {
			Statement statement = ((Connection) con).createStatement();
			int m = statement.executeUpdate(sqlQueryToUpdate);

		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	public static void deleteById() throws Throwable {
//		take id input from the user
//		use Delete query
		Connection con;

		String url = "jdbc:sqlserver://localhost:1433;databaseName=JsonData;encrypt=true;trustServerCertificate=true";
		String user = "sa";
		String pass = "root";
		con = DriverManager.getConnection(url, user, pass);

//		System.out.println(" Please Enter The ID To Delete The Row");
//		Scanner inputScanner = new Scanner(System.in);
//		int userInput = inputScanner.nextInt();
		String sqlQueryToRead = "DELETE JsonTable";
		
//		 WHERE Id = + userInput
		try {
			Statement statement = con.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQueryToRead);

			while (resultSet.next()) {
				Integer Id = resultSet.getInt("Id");
				String webPages = resultSet.getString("web_pages");
				String stateProvince = resultSet.getString("state_province");
				String alphaTwoCode = resultSet.getString("alpha_two_code");
				String name = resultSet.getString("name");
				String country = resultSet.getString("country");
				String domains = resultSet.getString("domains");

				System.out.println(Id + " " + webPages + " " + stateProvince + " " + alphaTwoCode + " " + name + " "
						+ country + " " + domains);
			}
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	public static void main(String[] args) throws Throwable, ClassNotFoundException, Throwable {

		Scanner sc = new Scanner(System.in);

		boolean exit = true;

		do {

			mainMenue();

			int option = sc.nextInt();

			switch (option) {

			case 1:
				connectToDataBase();
				break;

			case 2:
				jsonApi();
				break;

			case 3:
				createJsonDataTable();

				break;

			case 4:
				insertIntoTableJsonData();

				break;

			case 5:

				createJsonDataTableTestMarwaWay();

				break;

			case 6:

				testInsertMarwaWay();

				break;

			case 7:

				getById();

				break;

			case 8:

				updateById();
				break;

			case 9:

				deleteById();

				break;

			case 0:
				toExit();
				exit = false;

				break;

			default:

				byDefault();
			}

		} while (exit);
	}
}
