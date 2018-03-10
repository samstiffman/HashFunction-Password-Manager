import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	private final String address;

	public Database(String address) {
		this.address = address;
	}

	private Connection connect() {
		Connection conn = null;
		try {
			// db parameters
			String url = this.address;
			// create a connection to the database
			conn = DriverManager.getConnection(url);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return conn;
	}

	/**
	 * Inserts hashed data into the database
	 * 
	 * @param userName used to make sure data inserted is correct
	 * @param hashValue Used to make sure the passwords have no collisions
	 * @param hash hashed password
	 */
	public void insert(String userName, long hashValue, String hash) {
		String sql = "INSERT INTO hashedPasswords(userName,seed,hash) VALUES(?,?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userName);
			pstmt.setLong(2, hashValue);
			pstmt.setString(3, hash);
			pstmt.executeUpdate();
			System.out.println("Data successfully inserted");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Checks to see if a username already exists
	 * 
	 * @param userName to compare with database
	 * @return true if username exists false otherwise
	 */
	public boolean checkUser(String userName) {
		String sql = "SELECT userName " + "FROM hashedPasswords WHERE userName = ?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userName);

			ResultSet rs = pstmt.executeQuery();

			// get value from result set checks to make sure it exists
			while (rs.next()) {
				return userName.equals(rs.getString("userName"));
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}

	public String getHash(String userName) {
		String sql = "SELECT hash " + "FROM hashedPasswords WHERE userName = ?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userName);

			ResultSet rs = pstmt.executeQuery();
			String hash = null;

			// get value from result set
			hash = rs.getString("hash");

			return hash;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * retrieves the correct seed that corresponds to a username
	 * 
	 * @param userName
	 * @return
	 */
	public long getSeed(String userName) {
		String sql = "SELECT seed " + "FROM hashedPasswords WHERE userName = ?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, userName);

			long seed = 0;
			ResultSet rs = pstmt.executeQuery();

			// get value from result set
			seed = rs.getLong("seed");

			return seed;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Seed was not retrieved");
		return 0;
	}
}
