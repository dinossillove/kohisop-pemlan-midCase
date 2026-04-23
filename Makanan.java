public class Makanan extends MenuItem {
    public Makanan(String kode, String nama, double harga, String kategori) {
        super(kode, nama, harga, kategori);
    }
    @Override
    public double hitungPajak(int qty) {
        double sub = harga * qty;
        if (harga < 50) return sub * 0.11;
        else return sub * 0.08;
    }
    @Override
    public int getMaxQty() { return 2; }
}