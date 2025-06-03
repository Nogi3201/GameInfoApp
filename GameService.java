import java.sql.*;
import java.util.*;

public class GameService {
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM games");

            while (rs.next()) {
                Game game = new Game();
                game.setId(rs.getInt("id"));
                game.setName(rs.getString("name")); // sebelumnya "title"
                game.setGenre(rs.getString("genre"));
                game.setRating((float) rs.getDouble("rating")); // CAST ke float
                game.setReleaseDate(rs.getString("release_date"));
                game.setPopularity(rs.getInt("popularity"));
                game.setDescription(rs.getString("description"));
                games.add(game);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return games;
    }
}
