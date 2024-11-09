import java.sql.*;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class AuthenticationService {
    private Connection conn;
    public AuthenticationService(Connection conn) {
        this.conn = conn;
    }

    public boolean registerUser(String username, String password) throws Exception {
        String salt = generateSalt();
        String passwordHash = hashPassword(password, salt);

        String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, passwordHash);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateUser(String username, String password) throws Exception {
        String sql = "SELECT password_hash FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                return verifyPassword(password, storedHash);
            }
        }
        return false;
    }

    private String hashPassword(String password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] hashedBytes = md.digest(password.getBytes());
        return salt + ":" + bytesToHex(hashedBytes);
    }

    private boolean verifyPassword(String password, String storedHash) throws Exception {
        String[] parts = storedHash.split(":");
        String salt = parts[0];
        String hash = hashPassword(password, salt);
        return hash.equals(storedHash);
    }

    private String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return bytesToHex(salt);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
