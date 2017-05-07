import java.sql.*;
import java.util.ArrayList;

public class Database {

	private Connection conn;

	public Database() {
		conn = null;

	}

	/**
	 * Open connection
	 */
	public boolean openConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/krusty", "root", "password");
		}

		catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Close connection
	 */
	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn = null;
	}

	/**
	 * Returns an array with all products
	 * @return an array with all products
	 */
	public ArrayList<String> getProducts() {
		ArrayList<String> al = new ArrayList<String>();
		try {

			Statement myStmt = conn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select ProductName from recepies");
			while (myRs.next()) {
				al.add(myRs.getString("ProductName"));
			}
			return al;
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns an array with all orders registered
	 * @return an array with all orders registered
	 */
	public ArrayList<String> getOrderNbrs() {
		ArrayList<String> al = new ArrayList<String>();
		try {

			Statement myStmt = conn.createStatement();
			ResultSet myRs = myStmt.executeQuery("select OrderNbr from Orders");
			while (myRs.next()) {
				al.add(myRs.getString("OrderNbr"));
			}
			return al;
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}

	}

	/**
	 * Returns the name of the customers connected ordernbr
	 * @param ordernbr selected ordernumber
	 * @return Customer name
	 */
	public String getCustomerName(String ordernbr) {
		try {

			StringBuilder sb = new StringBuilder();
			PreparedStatement stmt = conn.prepareStatement("select Name from orders where ordernbr = ?");
			stmt.setString(1, ordernbr);

			ResultSet myRs = stmt.executeQuery();

			while (myRs.next()) {
				sb.append(myRs.getString("Name"));
			}
			return sb.toString();
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}

	}

	/**
	 * Returns nbr of pallets in stock for the given product
	 * @param product selected product
	 * @return the number in stock
	 */
	public int getPalletsInStock(String product) {
		try {
			int palletsInStock = 0;
			PreparedStatement myStmt = conn.prepareStatement(
					"select count(*) as amount from products where productname = ? and orderNbr IS NULL and Blocked = 'N'");
			myStmt.setString(1, product);

			ResultSet myRs = myStmt.executeQuery();
			while (myRs.next()) {
				palletsInStock = Integer.parseInt(myRs.getString("amount"));
			}
			return palletsInStock;
		} catch (Exception exc) {
			exc.printStackTrace();
			return 0;
		}

	}

	/**
	 * Returns nbr of pallets in stock for the given product
	 * @param product selected product
	 * @param date date of production
	 * @param time time of production
	 * @return true if its possible to produce a pallet
	 */
	public boolean producePallet(String product, String date, String time) {
		try {
			PreparedStatement myStmt = conn.prepareStatement(
					"insert into products (PalletNbr, OrderNbr, ProdDate, ProdTime, Blocked, ProductName) values (?, ?, ?, ?, ?, ?);");
			myStmt.setString(1, null);
			myStmt.setString(2, null);
			myStmt.setString(3, date);
			myStmt.setString(4, time);
			myStmt.setString(5, "N");
			myStmt.setString(6, product);

			myStmt.executeUpdate();
			return updateStock(product);

		} catch (Exception exc) {
			exc.printStackTrace();
			return false;

		}

	}

	/**
	 * Reserves pallets for a given ordernbr
	 * @param product selected product
	 * @param orderNbr order number
	 * @param nbrOfPallets number of pallets in order
	 */
	public void reservPallets(String orderNbr, String product, String nbrOfPallets) {
		try {
			PreparedStatement myStmt = conn.prepareStatement(
					"update products set ordernbr = ? where productname = ? and ordernbr IS NULL and Blocked = 'N' limit ?;");
			myStmt.setString(1, orderNbr);
			myStmt.setString(2, product);
			myStmt.setInt(3, Integer.parseInt(nbrOfPallets));

			myStmt.executeUpdate();

		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}

	/**
	 * Returns an array with data from a specific cell in table: customers
	 * @param cell the cell in the database
	 * @return an array with data from a specific cell in table: customers
	 */
	public ArrayList<String> getCustomers(String cell) {
		ArrayList<String> al = new ArrayList<String>();
		try {

			PreparedStatement myStmt = conn.prepareStatement("select * from customers");
			ResultSet myRs = myStmt.executeQuery();

			while (myRs.next()) {
				al.add(myRs.getString(cell));
			}

			return al;
		}

		catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}

	}
	
	/**
	 * Returns an array with data from a specific cell in table: orders join products
	 * @param cell the cell in the database
	 * @return an array with data from a specific cell in table: orders join products
	 */
	public ArrayList<String> getTrack(String cell) {
		ArrayList<String> al = new ArrayList<String>();
		try {

			PreparedStatement myStmt = conn.prepareStatement("select * from orders right join products on orders.ordernbr = products.ordernbr order by outdelivdate DESC");
			ResultSet myRs = myStmt.executeQuery();

			while (myRs.next()) {
				al.add(myRs.getString(cell));
			}

			return al;
		}

		catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}

	}

	/**
	 * Returns an array with data from a specific cell in table: storage
	 * @param cell the cell in the database
	 * @return an array with data from a specific cell in table: storage
	 */
	public ArrayList<String> getStorage(String cell) {
		ArrayList<String> al = new ArrayList<String>();
		try {

			PreparedStatement myStmt = conn.prepareStatement("select * from rawmaterials");

			ResultSet myRs = myStmt.executeQuery();

			while (myRs.next()) {
				al.add(myRs.getString(cell));
			}

			return al;
		}

		catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}

	}

	/**
	 * Returns an array with data from a specific cell in table: orders
	 * @param cell the cell in the database
	 * @return an array with data from a specific cell in table: orders
	 */
	public ArrayList<String> getOrders(String cell) {
		ArrayList<String> al = new ArrayList<String>();
		try {

			PreparedStatement myStmt = conn.prepareStatement("select * from orders");
			ResultSet myRs = myStmt.executeQuery();
			while (myRs.next()) {
				al.add(myRs.getString(cell));
			}

			return al;
		}

		catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}

	}

	/**
	 * Returns an array with data from a specific cell in table: products
	 * @param cell the cell in the database
	 * @return an array with data from a specific cell in table: products
	 */
	public ArrayList<String> getPallets(String cell) {
		ArrayList<String> al = new ArrayList<String>();
		try {
			PreparedStatement myStmt = conn.prepareStatement("select * from products where products.orderNbr IS NULL or products.ordernbr IN (select ordernbr from orders where outdelivdate IS NULL);");
			//PreparedStatement myStmt = conn.prepareStatement("select * from products order by orderNbr DESC");
			ResultSet myRs = myStmt.executeQuery();
			while (myRs.next()) {
				al.add(myRs.getString(cell));
			}

			return al;
		}

		catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
// select * from products where products.orderNbr IS NULL and products.ordernbr NOT IN (select ordernbr from orders where outdelivdate IS NULL);
	}

	/**
	 * updates table rawmaterials (stock)
	 * @param product the chosen product
	 * @return true if it was possible to update depending on enough rawmaterials in stock
	 */
	private boolean updateStock(String product) {
		try {
			PreparedStatement myStmt = conn.prepareStatement(
					"create or replace view updatestock as select rawmat, units, amountinstock from recepies natural join contains natural join rawmaterials where productname = ?");
			myStmt.setString(1, product);

			myStmt.execute();
			return updateAmountInStock();

		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}

	}
	/**
	 * checks if its possible to update stock
	 * @return true if it is possible to update depending on enough rawmaterials in stock
	 */
	private boolean updateAmountInStock() {
		try {
			if (checkAmountInStock()) {
				PreparedStatement myStmt = conn
						.prepareStatement("update updatestock set amountinstock = amountinstock - units;");
				myStmt.executeUpdate();
				return true;

			} else {
				return false;
			}

		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}
	}
	
	/**
	 * checks updates the values in stock
	 * @return true if it was possible to update stock
	 */
	private boolean checkAmountInStock() {
		try {
			String total = "total";
			PreparedStatement myStmt = conn
					.prepareStatement("select count(*) as ? from updatestock where amountinstock < units");
			myStmt.setString(1, total);
			ResultSet myRs = myStmt.executeQuery();
			while (myRs.next()) {
				if (myRs.getString(total).equals("0")) {
					return true;
				}
			}
			return false;

		} catch (Exception exc) {
			exc.printStackTrace();
			return false;
		}
	}

}
