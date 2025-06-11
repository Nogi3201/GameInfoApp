import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    private Connection conn;

    public GameDAO() {
        conn = DBConnection.getConnection(); // pastikan DBConnection punya method ini
    }

    // Ambil daftar game populer (10 game berdasarkan popularity tertinggi)
    public List<Game> getPopularGames() {
        List<Game> list = new ArrayList<>();
        String sql = "SELECT * FROM games ORDER BY popularity DESC LIMIT 10";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Game g = mapResultSetToGame(rs);
                list.add(g);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔍 Tambahkan fitur search game
    public List<Game> searchGames(String keyword) {
        List<Game> list = new ArrayList<>();
        String sql = "SELECT * FROM games WHERE name LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Game g = mapResultSetToGame(rs);
                    list.add(g);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // ➕ Tambahkan fitur menambah game
    public boolean addGame(Game g) {
        String sql = "INSERT INTO games (name, genre, rating, release_date, popularity, description, image) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, g.getName());
            stmt.setString(2, g.getGenre());
            stmt.setFloat(3, g.getRating());
            stmt.setString(4, g.getReleaseDate());
            stmt.setInt(5, g.getPopularity());
            stmt.setString(6, g.getDescription());
            stmt.setBytes(7, g.getImage());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Konversi ResultSet ke objek Game
    private Game mapResultSetToGame(ResultSet rs) throws SQLException {
        Game g = new Game();
        g.setId(rs.getInt("id"));
        g.setName(rs.getString("name"));
        g.setGenre(rs.getString("genre"));
        g.setRating(rs.getFloat("rating"));
        g.setReleaseDate(rs.getString("release_date"));
        g.setPopularity(rs.getInt("popularity"));
        g.setDescription(rs.getString("description"));

        Blob imageBlob = rs.getBlob("image");
        if (imageBlob != null) {
            g.setImage(imageBlob.getBytes(1, (int) imageBlob.length()));
        } else {
            g.setImage(null);
        }

        return g;
    }
}
