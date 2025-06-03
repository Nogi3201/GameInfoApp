import java.awt.*;
import java.util.List;
import javax.swing.*;

public class GameUI extends JFrame {
    public GameUI() {
        setTitle("Aplikasi Info Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GameDAO dao = new GameDAO();
        List<Game> games = dao.getPopularGames();

        JTextArea area = new JTextArea();
        area.setEditable(false); // Supaya tidak bisa diedit user

        // Cek jika games tidak null
        if (games != null) {
            for (Game g : games) {
                area.append(g.getName() + " - Rating: " + g.getRating() + "\n");
            }
        } else {
            area.setText("Tidak ada data game.");
        }

        add(new JScrollPane(area), BorderLayout.CENTER);
        setLocationRelativeTo(null); // Tampilkan di tengah layar
        setVisible(true);
    }
}