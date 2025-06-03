import java.sql.*;
import java.util.*;

public class GameDAO {
    private Connection conn;

    public GameDAO() {
        conn = DBConnection.connect();
    }

    public List<Game> getPopularGames() {
        List<Game> list = new ArrayList<>();
        String sql = "SELECT * FROM games ORDER BY popularity DESC LIMIT 10";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Game g = mapResultSetToGame(rs);
                list.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Game> searchGame(String keyword) {
        List<Game> list = new ArrayList<>();
        String sql = "SELECT * FROM games WHERE name LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Game g = mapResultSetToGame(rs);
                list.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Game getGameDetail(int id) {
        String sql = "SELECT * FROM games WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToGame(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Game> filterAndSortGames(String genre, String sortBy) {
        List<Game> list = new ArrayList<>();
        // Validasi sortBy agar tidak SQL Injection
        List<String> allowedSort = Arrays.asList("popularity", "rating", "release_date");
        if (!allowedSort.contains(sortBy)) {
            sortBy = "popularity";
        }
        String sql = "SELECT * FROM games WHERE genre = ? ORDER BY " + sortBy;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Game g = mapResultSetToGame(rs);
                list.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Game mapResultSetToGame(ResultSet rs) throws SQLException {
        Game g = new Game();
        g.setId(rs.getInt("id"));
        g.setName(rs.getString("name"));
        g.setGenre(rs.getString("genre"));
        g.setRating(rs.getFloat("rating"));
        g.setReleaseDate(rs.getString("release_date"));
        g.setPopularity(rs.getInt("popularity"));
        g.setDescription(rs.getString("description"));
        return g;
    }
}