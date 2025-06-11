import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Game {
    private int id;
    private String name;
    private String genre;
    private float rating;
    private String releaseDate;
    private int popularity;
    private String description;
    private byte[] image; // Menyimpan gambar sebagai byte array (dari database)

    public Game() {}

    public Game(int id, String name, String genre, float rating, String releaseDate, int popularity, String description, byte[] image) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.description = description;
        this.image = image;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public int getPopularity() { return popularity; }
    public void setPopularity(int popularity) { this.popularity = popularity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    // Method utilitas untuk konversi byte[] ke ImageIcon (untuk ditampilkan di Swing)
    public ImageIcon getImageIcon(int width, int height) {
        if (image != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
                Image scaledImage = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", genre='" + genre + "'" +
                ", rating=" + rating +
                ", releaseDate='" + releaseDate + "'" +
                ", popularity=" + popularity +
                ", description='" + description + "'" +
                ", image=" + (image != null ? image.length + " bytes" : "null") +
                '}';
    }
}
