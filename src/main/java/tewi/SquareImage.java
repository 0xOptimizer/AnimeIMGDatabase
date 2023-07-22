package tewi;

public class SquareImage {
    private String image; // base64 encoded image data
    private int squareId; // the id of the square the image belongs to

    // No-argument constructor
    public SquareImage() {
    }

    // Constructor that initializes both fields
    public SquareImage(String image, int squareId) {
        this.image = image;
        this.squareId = squareId;
    }

    // Getter for the image field
    public String getImage() {
        return image;
    }

    // Setter for the image field
    public void setImage(String image) {
        this.image = image;
    }

    // Getter for the squareId field
    public int getSquareId() {
        return squareId;
    }

    // Setter for the squareId field
    public void setSquareId(int squareId) {
        this.squareId = squareId;
    }
}