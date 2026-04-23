public class Minuman extends MenuItem {
    public Minuman(String kode, String nama, double harga, String kategori) {
        super(kode, nama, harga, kategori);
    }
    @Override
    public double hitungPajak(int qty) {
        double sub = harga * qty;
        if (harga < 50) return 0;
        else if (harga >= 50 && harga <= 55) return sub * 0.08;
        else return sub * 0.11;
    }
    @Override
    public int getMaxQty() { return 3; }
}