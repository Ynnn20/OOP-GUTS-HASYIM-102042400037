public class Album {
    private String albumName;
    private String artist;
    private int total;
    private int available;
    private int rented;

    public Album(String albumName, String artist, int total, int available) {
        this.albumName = albumName;
        this.artist = artist;
        this.total = total;
        this.available = available;
        this.rented = total - available;
    }

    public String getAlbumName() { return albumName; }
    public void setAlbumName(String albumName) { this.albumName = albumName; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public int getTotal() { return total; }
    public void setTotal(int total) {
        this.total = total;
        updateRented();
    }

    public int getAvailable() { return available; }
    public void setAvailable(int available) {
        this.available = available;
        updateRented();
    }

    public int getRented() { return rented; }

    private void updateRented() {
        this.rented = this.total - this.available;
    }
}