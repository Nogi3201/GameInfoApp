import java.awt.*;
import java.util.List;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;

public class GameUI extends JFrame {
    public GameUI() {
        setTitle("Aplikasi Info Game");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        GameDAO dao = new GameDAO();
        List<Game> games = dao.getPopularGames();

        if (games != null && !games.isEmpty()) {
            for (Game g : games) {
                JPanel gamePanel = new JPanel();
                gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
                gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                gamePanel.setBackground(Color.WHITE);

                // Gambar dari BLOB
                JLabel imageLabel = new JLabel();
                imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                try {
                    if (g.getImage() != null) {
                        ByteArrayInputStream bis = new ByteArrayInputStream(g.getImage());
                        BufferedImage bImage = ImageIO.read(bis);
                        Image scaledImage = bImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(scaledImage));
                    } else {
                        imageLabel.setText("Gambar tidak tersedia");
                    }
                } catch (Exception e) {
                    imageLabel.setText("Gagal memuat gambar");
                }

                // Info game
                JLabel titleLabel = new JLabel("<html><div style='text-align:center;'><b>" + g.getName() + "</b><br>" +
                        "Rating: " + g.getRating() + "<br>" +
                        "Genre: " + g.getGenre() + "<br>" +
                        "Rilis: " + g.getReleaseDate() + "<br>" +
                        "Telah dibeli: " + g.getPopularity() + " kali</div></html>");
                titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

                JTextArea descriptionArea = new JTextArea(g.getDescription());
                descriptionArea.setWrapStyleWord(true);
                descriptionArea.setLineWrap(true);
                descriptionArea.setEditable(false);
                descriptionArea.setOpaque(false);
                descriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT);

                gamePanel.add(imageLabel);
                gamePanel.add(titleLabel);
                gamePanel.add(descriptionArea);

                mainPanel.add(gamePanel);
                mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            }
        } else {
            JLabel kosong = new JLabel("Tidak ada data game.");
            kosong.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(kosong);
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
