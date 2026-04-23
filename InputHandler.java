import java.util.Scanner;

public class InputHandler {
    private Scanner scanner = new Scanner(System.in);
    
    public String readMenuCode() {
        System.out.print("Masukkan Kode Menu (ketik 'DONE' jika selesai, 'CC' batal): ");
        return scanner.nextLine().trim();
    }
    
    public int readQuantity(MenuItem item) {
        while (true) {
            System.out.print("Kuantitas untuk [" + item.getNama() + "] (Maks " + item.getMaxQty() + "): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("CC")) return -2;
            if (input.equals("0") || input.equals("S")) return 0;
            int qty = 1;
            if (!input.isEmpty()) {
                try { qty = Integer.parseInt(input); } 
                catch (NumberFormatException e) { System.out.println("Error: Harap masukkan angka yang valid!"); continue; }
            }
            if (qty < 0 || qty > item.getMaxQty()) { System.out.println("Error: Kuantitas tidak valid!"); continue; }
            return qty;
        }
    }
    
    public PaymentChannel readPaymentChannel() {
        while (true) {
            System.out.print("Pilih Pembayaran (1. Tunai, 2. QRIS, 3. eMoney): ");
            String ch = scanner.next(); scanner.nextLine();
            if (ch.equals("1")) return new Tunai();
            if (ch.equals("2")) return new QRIS();
            if (ch.equals("3")) return new EMoney();
            System.out.println("Pilihan tidak valid!");
        }
    }
    
    public Currency readCurrency() {
        while (true) {
            System.out.print("Pilih Mata Uang (1. IDR, 2. USD, 3. JPY, 4. MYR, 5. EUR): ");
            String mu = scanner.next(); scanner.nextLine();
            if (mu.equals("1")) return new IDR();
            if (mu.equals("2")) return new USD();
            if (mu.equals("3")) return new JPY();
            if (mu.equals("4")) return new MYR();
            if (mu.equals("5")) return new EUR();
            System.out.println("Pilihan tidak valid!");
        }
    }
}