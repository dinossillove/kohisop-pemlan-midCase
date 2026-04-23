
```markdown
# KohiSop - Coffee Shop Management System

KohiSop adalah aplikasi kasir berbasis Command Line Interface (CLI) yang dirancang untuk mengelola pesanan, perhitungan pajak otomatis, hingga transaksi pembayaran multi-mata uang di sebuah kedai kopi. Proyek ini dibuat untuk memenuhi tugas mata kuliah Pemrograman Lanjut dengan menerapkan prinsip-prinsip Pemrograman Berorientasi Objek (OOP) secara mendalam.

## 🚀 Fitur Utama
- **Manajemen Menu**: Pemisahan kategori makanan dan minuman dengan logika pajak yang berbeda.
- **Sistem Pemesanan**: Validasi kuantitas maksimal dan penanganan pembatalan pesanan secara dinamis.
- **Multi-Metode Pembayaran**: Mendukung Tunai, QRIS (diskon 5%), dan EMoney (diskon 7% + biaya admin).
- **Multi-Mata Uang**: Konversi otomatis dari IDR ke USD, MYR, JPY, dan EUR.
- **Robustness**: Dilengkapi dengan *exception handling* untuk mencegah program berhenti saat terjadi kesalahan input.

## 🏗️ Struktur Proyek (Tier System)

Aplikasi ini dirancang menggunakan arsitektur berlapis (tiered) untuk memastikan kode yang bersih dan mudah dikelola:

### TIER 1: Menu (Struktur Data Dasar)
Mengimplementasikan *Inheritance* dan *Abstraction* untuk model menu.
- **MenuItem**: Class abstract sebagai cetak biru semua menu.
- **Minuman**: Subclass dengan logika pajak berbasis harga (8% - 11%).
- **Makanan**: Subclass dengan logika pajak tetap.

### TIER 2: Pemesanan
Mengelola logika keranjang belanja dan penyimpanan data pesanan.
- **Order**: Merepresentasikan satu baris item yang dipesan beserta jumlahnya.
- **OrderDisplay**: Menampung daftar pesanan dan memisahkannya berdasarkan kategori.

### TIER 3: Pembayaran
Menggunakan *Interface* untuk fleksibilitas metode pembayaran.
- **PaymentChannel**: Interface utama untuk aturan pembayaran.
- **Tunai, QRIS, Emoney**: Implementasi konkret dengan aturan diskon dan biaya admin masing-masing.

### TIER 4: Currency
Mengangani konversi nilai tukar mata uang secara *polymorphic*.
- **Currency**: Interface untuk standar konversi mata uang.
- **IDR, USD, MYR, JPY, EUR**: Implementasi nilai tukar terhadap mata uang dasar (IDR).

### TIER 5: Display & Logic
Menangani interaksi pengguna dan kalkulasi akhir.
- **MenuDisplay**: Menampilkan daftar menu yang tersedia.
- **InputHandler**: Mengelola input user dan validasi (Error Handling).
- **PaymentCalculator**: Menghitung total harga, pajak, diskon, dan konversi.
- **ReceiptPrinter**: Mencetak struk belanja secara rapi dan terstruktur.

### MAIN ENTRY
- **Main.java**: Titik awal eksekusi program dan pengatur alur kerja aplikasi secara keseluruhan.

## 🛠️ Cara Menjalankan (Local Setup)

### Prerequisites
Pastikan Anda sudah menginstal **Java Development Kit (JDK)** versi 8 atau yang lebih baru di komputer Anda.

### 1. Clone Repository
Buka terminal atau command prompt, lalu jalankan perintah berikut:
```bash
git clone [https://github.com/dinossillove/kohisop-pemlan-midCase.git](https://github.com/dinossillove/kohisop-pemlan-midCase.git)
cd kohisop-pemlan-midCase
```

### 2. Compile Program
Lakukan kompilasi pada semua file Java yang ada di dalam folder:
```bash
javac *.java
```

### 3. Run Program
Jalankan kelas utama untuk memulai aplikasi:
```bash
java Main
```

## 📝 Konsep OOP yang Diterapkan
- **Encapsulation**: Melindungi data menggunakan access modifier `private` dan `protected`.
- **Inheritance**: Menurunkan sifat dari `MenuItem` ke `Makanan` dan `Minuman`.
- **Polymorphism**: Penggunaan method `hitungPajak()` dan `konversi()` yang berbeda perilaku tergantung objeknya.
- **Abstraction**: Penggunaan *Abstract Class* dan *Interface* untuk standarisasi kontrak antar class.

---
**Tim Pengembang:**
- Aisha Berlianti (Cia)

*© 2026 KohiSop Project - Pemrograman Lanjut*
