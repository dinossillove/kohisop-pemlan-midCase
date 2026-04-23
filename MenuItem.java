public abstract class MenuItem {
    protected String kode, nama, kategori;
    protected double harga;

    public MenuItem(String kode, String nama, double harga, String kategori) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public String getKategori() { return kategori; }

    public abstract double hitungPajak(int qty);
    public abstract int getMaxQty();
}