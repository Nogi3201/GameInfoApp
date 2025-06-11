import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;

public class TambahGameForm extends JFrame {
    private JTextField nameField, genreField, ratingField, releaseDateField, popularityField;
    private JTextArea descriptionArea;
    private JLabel imageLabel;
    private byte[] imageBytes;

    public TambahGameForm() {
        setTitle("Tambah Game Baru");
        setSize(400, 600);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        genreField = new JTextField();
        ratingField = new JTextField();
        releaseDateField = new JTextField();
        popularityField = new JTextField();
        descriptionArea = new JTextArea(3, 20);
        imageLabel = new JLabel("Belum ada gambar", SwingConstants.CENTER);

        JButton uploadButton = new JButton("Pilih Gambar");
        uploadButton.addActionListener(e -> pilihGambar());

        JButton simpanButton = new JButton("Simpan");
        simpanButton.addActionListener(e -> simpanGame());

        formPanel.add(new JLabel("Nama:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Genre:"));
        formPanel.add(genreField);
        formPanel.add(new JLabel("Rating:"));
        formPanel.add(ratingField);
        formPanel.add(new JLabel("Tanggal Rilis (YYYY-MM-DD):"));
        formPanel.add(releaseDateField);
        formPanel.add(new JLabel("Popularitas:"));
        formPanel.add(popularityField);
        formPanel.add(new JLabel("Deskripsi:"));
        formPanel.add(new JScrollPane(descriptionArea));
        formPanel.add(new JLabel("Gambar:"));
        formPanel.add(imageLabel);
        formPanel.add(uploadButton);
        formPanel.add(simpanButton);

        add(formPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void pilihGambar() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(file)) {
                imageBytes = fis.readAllBytes();
                imageLabel.setText("Gambar dipilih: " + file.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Gagal membaca gambar");
                e.printStackTrace();
            }
        }
    }

    private void simpanGame() {
        String name = nameField.getText();
        String genre = genreField.getText();
        float rating;
        int popularity;
        try {
            rating = Float.parseFloat(ratingField.getText());
            popularity = Integer.parseInt(popularityField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Rating atau popularitas tidak valid.");
            return;
        }

        String releaseDate = releaseDateField.getText();
        String description = descriptionArea.getText();

        try (Connection conn = DBConnection.connect()) {
            String sql = "INSERT INTO games (name, genre, rating, release_date, popularity, description, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, name);
            stmt.setString(2, genre);
            stmt.setFloat(3, rating);
            stmt.setString(4, releaseDate);
            stmt.setInt(5, popularity);
            stmt.setString(6, description);
            if (imageBytes != null) {
                stmt.setBytes(7, imageBytes);
            } else {
                stmt.setNull(7, Types.BLOB);
            }

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Game berhasil ditambahkan!");
            dispose();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan game ke database.");
            e.printStackTrace();
        }
    }
}
