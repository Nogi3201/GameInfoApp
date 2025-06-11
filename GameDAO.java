import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO {

    private Connection conn;

    public GameDAO() {
        conn = DBConnection.connect(); // FIXED: gunakan .connect() sesuai yang didefinisikan
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
