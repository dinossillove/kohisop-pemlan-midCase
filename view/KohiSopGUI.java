package view;

import controller.KitchenProcessor;
import controller.PaymentCalculator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import model.Currency;
import model.EMoney;
import model.EUR;
import model.IDR;
import model.JPY;
import model.MYR;
import model.Makanan;
import model.Membership;
import model.MenuItem;
import model.Minuman;
import model.Order;
import model.PaymentChannel;
import model.QRIS;
import model.Tunai;
import model.USD;

public class KohiSopGUI extends JFrame {

    private static final Color C_BG          = new Color(0x1C1917);
    private static final Color C_PANEL       = new Color(0x292524);
    private static final Color C_CARD        = new Color(0x3B3330);
    private static final Color C_ACCENT      = new Color(0xC8854A);
    private static final Color C_ACCENT2     = new Color(0x9BB580);
    private static final Color C_TEXT        = new Color(0xF5F0EB);
    private static final Color C_TEXT_DIM    = new Color(0xA8998E);
    private static final Color C_BORDER      = new Color(0x4A4340);
    private static final Color C_TABLE_HEAD  = new Color(0xC8854A);
    private static final Color C_TABLE_ALT   = new Color(0x322E2C);
    private static final Color C_ERR         = new Color(0xE07070);
    private static final Color C_SUCCESS     = new Color(0x9BB580);

    private static final Font  F_TITLE       = new Font("SansSerif", Font.BOLD,  22);
    private static final Font  F_SECTION     = new Font("SansSerif", Font.BOLD,  13);
    private static final Font  F_LABEL       = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font  F_MONO        = new Font("Monospaced", Font.PLAIN, 12);
    private static final Font  F_SMALL       = new Font("SansSerif", Font.PLAIN, 11);

    private static final List<MenuItem> MENU_ITEMS = Arrays.asList(
        new Minuman("A1","Caffe Latte",46,"Coffee"),
        new Minuman("A2","Cappuccino",46,"Coffee"),
        new Minuman("E1","Caffe Americano",37,"Coffee"),
        new Minuman("E2","Caffe Mocha",55,"Coffee"),
        new Minuman("E3","Caramel Macchiato",59,"Coffee"),
        new Minuman("E4","Asian Dolce Latte",55,"Coffee"),
        new Minuman("E5","Double Shots Iced Shaken Espresso",50,"Coffee"),
        new Minuman("B1","Freshly Brewed Coffee",23,"Brew"),
        new Minuman("B2","Vanilla Sweet Cream Cold Brew",50,"Brew"),
        new Minuman("B3","Cold Brew",44,"Brew"),
        new Makanan("M1","Petemania Pizza",112,"Food"),
        new Makanan("M2","Mie Rebus Super Mario",35,"Food"),
        new Makanan("M3","Ayam Bakar Goreng Rebus Spesial",72,"Food"),
        new Makanan("M4","Soto Kambing Iga Guling",124,"Food"),
        new Makanan("S1","Singkong Bakar A La Carte",37,"Snack"),
        new Makanan("S2","Ubi Cilembu Bakar Arang",58,"Snack"),
        new Makanan("S3","Tempe Mendoan",18,"Snack"),
        new Makanan("S4","Tahu Bakso Extra Telur",28,"Snack")
    );

    private final OrderDisplay orderDisplay = new OrderDisplay();
    private final KitchenProcessor kitchen  = new KitchenProcessor();
    private Membership currentMember        = null;

    private JTable menuDrinkTable, menuFoodTable, cartTable;
    private DefaultTableModel cartModel;
    private JTextField txtCode, txtQty, txtCustomerName, txtMemberCode,
                       txtMemberName, txtMemberPoints;
    private JComboBox<String> cmbPayment, cmbCurrency;
    private JTextArea receiptArea, kitchenArea;
    private JLabel lblStatus, lblCartDrinkCount, lblCartFoodCount;
    private JButton btnAdd, btnCancelOrder, btnCheckout;

    public KohiSopGUI() {
        setTitle("KohiSop II – Sistem Pembayaran");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1340, 820));
        getContentPane().setBackground(C_BG);
        buildUI();
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1100, 700));
    }

    private void buildUI() {
        setLayout(new BorderLayout(0, 0));
        add(buildTopBar(),    BorderLayout.NORTH);
        add(buildMainArea(),  BorderLayout.CENTER);
        add(buildStatusBar(), BorderLayout.SOUTH);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout(16, 0));
        bar.setBackground(C_PANEL);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, C_ACCENT));
        bar.setPreferredSize(new Dimension(0, 60));

        JLabel logo = new JLabel("☕  KohiSop II");
        logo.setFont(F_TITLE);
        logo.setForeground(C_ACCENT);
        logo.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        JLabel sub = new JLabel("Data Structure-based POS System");
        sub.setFont(F_SMALL);
        sub.setForeground(C_TEXT_DIM);
        sub.setHorizontalAlignment(SwingConstants.RIGHT);
        sub.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        bar.add(logo, BorderLayout.WEST);
        bar.add(sub,  BorderLayout.EAST);
        return bar;
    }

    private JComponent buildMainArea() {
        JPanel leftCol = buildMenuPanel();
        leftCol.setPreferredSize(new Dimension(340, 0));

        JPanel centreCol = buildCentrePanel();
        centreCol.setPreferredSize(new Dimension(440, 0));

        JPanel rightCol = buildRightPanel();

        JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftCol, centreCol);
        split1.setDividerSize(4);
        split1.setDividerLocation(345);
        split1.setBackground(C_BG);

        JSplitPane split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split1, rightCol);
        split2.setDividerSize(4);
        split2.setDividerLocation(795);
        split2.setBackground(C_BG);

        return split2;
    }

    private JPanel buildMenuPanel() {
        JPanel panel = panel(new BorderLayout(0, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 6));

        JLabel title = sectionLabel("DAFTAR MENU");
        panel.add(title, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        styleTab(tabs);
        tabs.addTab("☕ Minuman", buildMenuTable(false));
        tabs.addTab("🍽 Makanan", buildMenuTable(true));
        panel.add(tabs, BorderLayout.CENTER);

        return panel;
    }

    private JScrollPane buildMenuTable(boolean food) {
        String[] cols = {"Kode", "Nama", "Harga (Rp)"};
        DefaultTableModel mdl = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        List<MenuItem> filtered = new ArrayList<>();
        for (MenuItem mi : MENU_ITEMS) {
            if (food == (mi instanceof Makanan)) filtered.add(mi);
        }
        filtered.sort(Comparator.comparing(MenuItem::getKode));

        for (MenuItem mi : filtered) {
            mdl.addRow(new Object[]{mi.getKode(), mi.getNama(),
                String.format("Rp %.0f", mi.getHarga())});
        }

        JTable tbl = styledTable(mdl);
        tbl.getColumnModel().getColumn(0).setPreferredWidth(45);
        tbl.getColumnModel().getColumn(1).setPreferredWidth(180);
        tbl.getColumnModel().getColumn(2).setPreferredWidth(80);

        if (!food) menuDrinkTable = tbl; else menuFoodTable = tbl;

        tbl.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tbl.getSelectedRow();
                    if (row >= 0) {
                        txtCode.setText(tbl.getValueAt(row, 0).toString());
                        txtCode.requestFocus();
                        setStatus("Kode dipilih: " + txtCode.getText()
                            + " – Atur kuantitas lalu klik Tambah.", C_ACCENT);
                    }
                }
            }
        });

        return scrollPane(tbl);
    }

    private JPanel buildCentrePanel() {
        JPanel panel = panel(new BorderLayout(0, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 6));

        JPanel top = panel(new BorderLayout(0, 8));
        top.add(buildOrderInputPanel(), BorderLayout.NORTH);
        top.add(buildCartPanel(),       BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, top, buildBottomCentrePanel());
        split.setResizeWeight(0.55);
        split.setDividerSize(4);
        split.setBackground(C_BG);

        panel.add(split, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildOrderInputPanel() {
        JPanel card = card();
        card.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill   = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; g.gridwidth = 4;
        card.add(sectionLabel("INPUT PESANAN"), g);

        g.gridwidth = 1; g.gridy = 1;

        g.gridx = 0; g.weightx = 0;
        card.add(label("Kode Item:"), g);
        g.gridx = 1; g.weightx = 1;
        txtCode = styledField();
        txtCode.setToolTipText("Double-klik menu untuk isi otomatis, atau ketik kode lalu Enter");
        card.add(txtCode, g);

        g.gridx = 2; g.weightx = 0;
        card.add(label("Qty:"), g);
        g.gridx = 3; g.weightx = 0.4;
        txtQty = styledField();
        txtQty.setColumns(4);
        txtQty.setText("1");
        txtQty.setToolTipText("0 / S = skip item | Minuman maks 3 | Makanan maks 2");
        card.add(txtQty, g);

        g.gridy = 2; g.gridx = 0; g.gridwidth = 2; g.weightx = 1;
        btnAdd = accentButton("+ Tambah ke Pesanan");
        card.add(btnAdd, g);

        g.gridx = 2; g.gridwidth = 2; g.weightx = 1;
        btnCancelOrder = dangerButton("✕  Batal (CC)");
        card.add(btnCancelOrder, g);

        txtCode.addActionListener(e -> txtQty.requestFocus());
        txtQty.addActionListener(e -> handleAddItem());
        btnAdd.addActionListener(e -> handleAddItem());
        btnCancelOrder.addActionListener(e -> handleCancelOrder());

        return card;
    }

    private JPanel buildCartPanel() {
        JPanel card = card();
        card.setLayout(new BorderLayout(0, 6));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(sectionLabel("PESANAN AKTIF"), BorderLayout.WEST);

        JPanel counts = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        counts.setOpaque(false);
        lblCartDrinkCount = badge("Minuman: 0/5");
        lblCartFoodCount  = badge("Makanan: 0/5");
        counts.add(lblCartDrinkCount);
        counts.add(lblCartFoodCount);
        header.add(counts, BorderLayout.EAST);
        card.add(header, BorderLayout.NORTH);

        String[] cols = {"Kode", "Nama", "Harga", "Qty", "Subtotal"};
        cartModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        cartTable = styledTable(cartModel);
        cartTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        cartTable.getColumnModel().getColumn(1).setPreferredWidth(170);
        cartTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        cartTable.getColumnModel().getColumn(3).setPreferredWidth(35);
        cartTable.getColumnModel().getColumn(4).setPreferredWidth(65);

        card.add(scrollPane(cartTable), BorderLayout.CENTER);

        JButton btnRemove = subtleButton("Hapus Item Terpilih");
        btnRemove.addActionListener(e -> handleRemoveCartItem());
        card.add(btnRemove, BorderLayout.SOUTH);

        return card;
    }

    private JPanel buildBottomCentrePanel() {
        JPanel panel = panel(new BorderLayout(0, 8));
        panel.add(buildMembershipPanel(), BorderLayout.NORTH);
        panel.add(buildPaymentPanel(),    BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildMembershipPanel() {
        JPanel card = card();
        card.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 6, 3, 6);
        g.fill   = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; g.gridwidth = 4;
        card.add(sectionLabel("MEMBERSHIP"), g);

        g.gridwidth = 1; g.gridy = 1;

        g.gridx = 0; g.weightx = 0; card.add(label("Nama:"), g);
        g.gridx = 1; g.weightx = 1;
        txtCustomerName = styledField();
        txtCustomerName.setToolTipText("Nama pelanggan – member dibuat/diambil otomatis");
        card.add(txtCustomerName, g);

        g.gridx = 2; g.weightx = 0; card.add(label("Kode:"), g);
        g.gridx = 3; g.weightx = 0.6;
        txtMemberCode = styledField();
        txtMemberCode.setEditable(false);
        txtMemberCode.setForeground(C_ACCENT);
        card.add(txtMemberCode, g);

        g.gridy = 2;
        g.gridx = 0; g.weightx = 0; card.add(label("Poin:"), g);
        g.gridx = 1; g.weightx = 1;
        txtMemberPoints = styledField();
        txtMemberPoints.setEditable(false);
        txtMemberPoints.setForeground(C_ACCENT2);
        card.add(txtMemberPoints, g);

        g.gridx = 2; g.weightx = 0; card.add(label("Status:"), g);
        g.gridx = 3;
        txtMemberName = styledField();
        txtMemberName.setEditable(false);
        card.add(txtMemberName, g);

        g.gridy = 3; g.gridx = 0; g.gridwidth = 4;
        JButton btnLookup = subtleButton("🔍  Cek / Daftarkan Member");
        btnLookup.addActionListener(e -> handleMemberLookup());
        card.add(btnLookup, g);

        txtCustomerName.addActionListener(e -> handleMemberLookup());

        return card;
    }

    private JPanel buildPaymentPanel() {
        JPanel card = card();
        card.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6);
        g.fill   = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; g.gridwidth = 4;
        card.add(sectionLabel("PEMBAYARAN & MATA UANG"), g);

        g.gridwidth = 1; g.gridy = 1;
        g.gridx = 0; g.weightx = 0; card.add(label("Channel:"), g);
        g.gridx = 1; g.weightx = 1;
        cmbPayment = styledCombo(new String[]{
            "1 – Tunai (no diskon)",
            "2 – QRIS (diskon 5%)",
            "3 – eMoney (diskon 7% + admin Rp20)"
        });
        card.add(cmbPayment, g);

        g.gridx = 2; g.weightx = 0; card.add(label("Mata Uang:"), g);
        g.gridx = 3; g.weightx = 0.8;
        cmbCurrency = styledCombo(new String[]{"IDR", "USD", "JPY", "MYR", "EUR"});
        card.add(cmbCurrency, g);

        g.gridy = 2; g.gridx = 0; g.gridwidth = 4; g.weightx = 1;
        JLabel info = new JLabel("Tip: 1 poin = 2 IDR. Poin hanya berlaku saat bayar IDR.");
        info.setFont(F_SMALL);
        info.setForeground(C_TEXT_DIM);
        card.add(info, g);

        g.gridy = 3;
        btnCheckout = accentButton("⬛  CHECKOUT & CETAK KUITANSI");
        btnCheckout.setFont(new Font("SansSerif", Font.BOLD, 13));
        card.add(btnCheckout, g);

        btnCheckout.addActionListener(e -> handleCheckout());

        return card;
    }

    private JPanel buildRightPanel() {
        JPanel panel = panel(new BorderLayout(0, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 6, 10, 10));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            buildReceiptPanel(), buildKitchenPanel());
        split.setResizeWeight(0.65);
        split.setDividerSize(4);
        split.setBackground(C_BG);

        panel.add(split, BorderLayout.CENTER);

        JButton btnClear = subtleButton("Bersihkan Layar");
        btnClear.addActionListener(e -> {
            receiptArea.setText("");
            kitchenArea.setText("");
        });
        panel.add(btnClear, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildReceiptPanel() {
        JPanel card = card();
        card.setLayout(new BorderLayout(0, 6));
        card.add(sectionLabel("KUITANSI"), BorderLayout.NORTH);

        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(F_MONO);
        receiptArea.setBackground(new Color(0x14120F));
        receiptArea.setForeground(C_TEXT);
        receiptArea.setCaretColor(C_ACCENT);
        receiptArea.setLineWrap(true);
        receiptArea.setWrapStyleWord(true);
        receiptArea.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JScrollPane sp = new JScrollPane(receiptArea);
        sp.setBorder(BorderFactory.createLineBorder(C_BORDER));
        sp.getViewport().setBackground(new Color(0x14120F));
        card.add(sp, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildKitchenPanel() {
        JPanel card = card();
        card.setLayout(new BorderLayout(0, 6));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(sectionLabel("ANTRIAN DAPUR"), BorderLayout.WEST);

        JLabel info = new JLabel("Diproses setelah 3 pelanggan");
        info.setFont(F_SMALL);
        info.setForeground(C_TEXT_DIM);
        info.setHorizontalAlignment(SwingConstants.RIGHT);
        header.add(info, BorderLayout.EAST);
        card.add(header, BorderLayout.NORTH);

        kitchenArea = new JTextArea();
        kitchenArea.setEditable(false);
        kitchenArea.setFont(F_MONO);
        kitchenArea.setBackground(new Color(0x14120F));
        kitchenArea.setForeground(C_ACCENT2);
        kitchenArea.setLineWrap(true);
        kitchenArea.setWrapStyleWord(true);
        kitchenArea.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        JScrollPane sp = new JScrollPane(kitchenArea);
        sp.setBorder(BorderFactory.createLineBorder(C_BORDER));
        sp.getViewport().setBackground(new Color(0x14120F));
        card.add(sp, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new BorderLayout(10, 0));
        bar.setBackground(C_PANEL);
        bar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, C_BORDER),
            BorderFactory.createEmptyBorder(5, 14, 5, 14)));
        bar.setPreferredSize(new Dimension(0, 32));

        lblStatus = new JLabel("Selamat datang di KohiSop! Double-klik menu untuk memilih item.");
        lblStatus.setFont(F_SMALL);
        lblStatus.setForeground(C_TEXT_DIM);

        JLabel brand = new JLabel("KohiSop II  v1.0");
        brand.setFont(F_SMALL);
        brand.setForeground(C_BORDER);

        bar.add(lblStatus, BorderLayout.WEST);
        bar.add(brand,     BorderLayout.EAST);
        return bar;
    }

    // ─── Business Logic Handlers ──────────────────────────────────────────────

    private void handleAddItem() {
        String code = txtCode.getText().trim().toUpperCase();
        if (code.isEmpty()) { setStatus("Masukkan kode item terlebih dahulu.", C_ERR); return; }

        if (code.equals("CC")) { handleCancelOrder(); return; }

        MenuItem item = findItemByCode(code);
        if (item == null) {
            setStatus("Error: Kode '" + code + "' tidak ditemukan di menu.", C_ERR);
            txtCode.selectAll();
            return;
        }

        int drinkCount = orderDisplay.getDrinks().size();
        int foodCount  = orderDisplay.getFoods().size();

        if (item instanceof Minuman && drinkCount >= 5) {
            setStatus("Maksimal 5 jenis minuman per pesanan sudah tercapai.", C_ERR); return;
        }
        if (item instanceof Makanan && foodCount >= 5) {
            setStatus("Maksimal 5 jenis makanan per pesanan sudah tercapai.", C_ERR); return;
        }

        for (Order o : orderDisplay.getDrinks()) {
            if (o.getMenuItem().getKode().equalsIgnoreCase(code)) {
                setStatus("Item '" + item.getNama() + "' sudah ada dalam pesanan.", C_ERR); return;
            }
        }
        for (Order o : orderDisplay.getFoods()) {
            if (o.getMenuItem().getKode().equalsIgnoreCase(code)) {
                setStatus("Item '" + item.getNama() + "' sudah ada dalam pesanan.", C_ERR); return;
            }
        }

        String qtyStr = txtQty.getText().trim().toUpperCase();
        if (qtyStr.equals("S") || qtyStr.equals("0")) {
            setStatus("Item dilewati (skip).", C_TEXT_DIM);
            txtCode.setText(""); txtQty.setText("1"); txtCode.requestFocus(); return;
        }

        int qty = 1;
        if (!qtyStr.isEmpty()) {
            try {
                qty = Integer.parseInt(qtyStr);
            } catch (NumberFormatException ex) {
                setStatus("Error: Kuantitas harus berupa angka.", C_ERR); return;
            }
        }
        if (qty < 0 || qty > item.getMaxQty()) {
            setStatus("Error: Kuantitas tidak valid. Maks " + item.getMaxQty()
                + " untuk " + item.getNama() + ".", C_ERR); return;
        }
        if (qty == 0) {
            setStatus("Item dilewati (qty 0).", C_TEXT_DIM);
            txtCode.setText(""); txtQty.setText("1"); txtCode.requestFocus(); return;
        }

        orderDisplay.addItem(new Order(item, qty));
        refreshCartTable();
        txtCode.setText(""); txtQty.setText("1"); txtCode.requestFocus();
        setStatus("Ditambahkan: " + item.getNama() + " x" + qty, C_SUCCESS);
    }

    private void handleRemoveCartItem() {
        int row = cartTable.getSelectedRow();
        if (row < 0) { setStatus("Pilih item di tabel pesanan terlebih dahulu.", C_TEXT_DIM); return; }

        String code = cartModel.getValueAt(row, 0).toString();
        orderDisplay.getDrinks().removeIf(o -> o.getMenuItem().getKode().equals(code));
        orderDisplay.getFoods().removeIf(o -> o.getMenuItem().getKode().equals(code));
        refreshCartTable();
        setStatus("Item " + code + " dihapus dari pesanan.", C_ACCENT);
    }

    private void handleCancelOrder() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Batalkan seluruh pesanan saat ini?", "Konfirmasi Batal",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            orderDisplay.clear();
            refreshCartTable();
            currentMember = null;
            txtCustomerName.setText(""); txtMemberCode.setText("");
            txtMemberName.setText(""); txtMemberPoints.setText("");
            setStatus("Pesanan dibatalkan (CC).", C_ERR);
        }
    }

    private void handleMemberLookup() {
        String name = txtCustomerName.getText().trim();
        if (name.isEmpty()) { setStatus("Masukkan nama pelanggan terlebih dahulu.", C_ERR); return; }
        currentMember = Membership.getOrCreateMember(name);
        txtMemberCode.setText(currentMember.getKodeMember());
        txtMemberName.setText(currentMember.getNamaMember());
        txtMemberPoints.setText(String.valueOf(currentMember.getPoin()));

        boolean hasA = currentMember.getKodeMember().contains("A");
        String msg = "Member: " + currentMember.getNamaMember()
            + "  [" + currentMember.getKodeMember() + "]"
            + "  Poin: " + currentMember.getPoin()
            + (hasA ? "  ★ Bebas Pajak + Poin Ganda!" : "");
        setStatus(msg, C_SUCCESS);
    }

    private void handleCheckout() {
        if (orderDisplay.getDrinks().isEmpty() && orderDisplay.getFoods().isEmpty()) {
            setStatus("Pesanan kosong. Tambah item sebelum checkout.", C_ERR); return;
        }
        String name = txtCustomerName.getText().trim();
        if (name.isEmpty()) {
            setStatus("Masukkan nama pelanggan untuk membership.", C_ERR);
            txtCustomerName.requestFocus(); return;
        }

        if (currentMember == null) handleMemberLookup();

        PaymentChannel channel = resolveChannel(cmbPayment.getSelectedIndex());
        Currency currency      = resolveCurrency(cmbCurrency.getSelectedItem().toString());

        boolean taxExempt = currentMember != null
            && currentMember.getKodeMember().contains("A");

        OrderDisplay calcOrder = buildTaxAdjustedOrder(taxExempt);
        PaymentCalculator calc = new PaymentCalculator(
            calcOrder, channel, currency, currentMember);

        // --- PERBAIKAN: simpan poin SEBELUM transaksi ---
        int pointsBefore = currentMember.getPoin();

        // Redeem poin (hanya untuk IDR)
        int pointsUsed = 0;
        boolean usePoints = currentMember != null
            && currentMember.getPoin() > 0
            && currency.getCode().equals("IDR");
        if (usePoints) {
            pointsUsed = calc.getPointsRedeemed();
        }

        double finalIDR = calc.getFinalTotalIDRBeforePoints();

        // --- PERBAIKAN: getEarnedPointsForTransaction sudah include penggandaan "A",
        //     jangan kalikan lagi di sini ---
        int pointsEarned = currentMember.getEarnedPointsForTransaction(finalIDR);
        boolean doubled  = currentMember.getKodeMember().contains("A");

        // Tambahkan poin ke member (sudah otomatis ganda jika kode ada "A")
        currentMember.addPointsFromTransaction(finalIDR);

        String receipt = buildReceipt(calc, calcOrder, taxExempt,
            pointsBefore, pointsUsed, pointsEarned, doubled, currency, channel);
        receiptArea.setText(receipt);
        receiptArea.setCaretPosition(0);

        List<Order> drinksCopy = new ArrayList<>(orderDisplay.getDrinks());
        List<Order> foodsCopy  = new ArrayList<>(orderDisplay.getFoods());
        kitchen.submitOrder(drinksCopy, foodsCopy);

        txtMemberPoints.setText(String.valueOf(currentMember.getPoin()));

        String kitchenOutput = buildKitchenOutput();
        kitchenArea.setText(kitchenOutput);

        orderDisplay.clear();
        refreshCartTable();
        currentMember = null;
        txtCustomerName.setText(""); txtMemberCode.setText("");
        txtMemberName.setText(""); txtMemberPoints.setText("");

        setStatus("Checkout berhasil! Pelanggan ke-" + kitchen.getCustomerCount()
            + "/3 dilayani.", C_SUCCESS);
    }

    // ─── Receipt Builder ─────────────────────────────────────────────────────
    // PERBAIKAN: parameter pointsBefore ditambahkan, tidak lagi dihitung dari getPoin()-newPoints
    private String buildReceipt(PaymentCalculator calc, OrderDisplay od,
            boolean taxExempt, int pointsBefore, int pointsUsed, int pointsEarned,
            boolean doubled, Currency currency, PaymentChannel channel) {

        StringBuilder sb = new StringBuilder();
        String line = "=".repeat(52);
        String dash = "-".repeat(52);

        sb.append(line).append("\n");
        sb.append("          ☕  NOTA PEMBAYARAN KOHISOP II\n");
        sb.append(line).append("\n");
        sb.append(String.format("Tanggal : %s\n", new java.util.Date()));
        if (currentMember != null) {
            sb.append(String.format("Member  : %s  [%s]\n",
                currentMember.getNamaMember(), currentMember.getKodeMember()));
        }
        if (taxExempt) sb.append("⚡ BEBAS PAJAK (Kode member mengandung 'A')\n");
        sb.append(dash).append("\n");

        // Makanan dulu
        if (!od.getFoods().isEmpty()) {
            sb.append(String.format("%-5s %-26s %6s %4s %10s  %8s\n",
                "Kode","Makanan","Harga","Qty","Subtotal","Pajak"));
            sb.append(dash).append("\n");
            for (Order o : od.getFoods()) {
                double tax = taxExempt ? 0 : o.getTotalTax();
                sb.append(String.format("%-5s %-26s %6.0f %4d %10.2f  %8.2f\n",
                    o.getMenuItem().getKode(),
                    truncate(o.getMenuItem().getNama(), 26),
                    o.getMenuItem().getHarga(),
                    o.getQuantity(),
                    currency.convertFromIDR(o.getSubtotal()),
                    currency.convertFromIDR(tax)));
            }
            sb.append("\n");
        }

        // Minuman
        if (!od.getDrinks().isEmpty()) {
            sb.append(String.format("%-5s %-26s %6s %4s %10s  %8s\n",
                "Kode","Minuman","Harga","Qty","Subtotal","Pajak"));
            sb.append(dash).append("\n");
            for (Order o : od.getDrinks()) {
                double tax = taxExempt ? 0 : o.getTotalTax();
                sb.append(String.format("%-5s %-26s %6.0f %4d %10.2f  %8.2f\n",
                    o.getMenuItem().getKode(),
                    truncate(o.getMenuItem().getNama(), 26),
                    o.getMenuItem().getHarga(),
                    o.getQuantity(),
                    currency.convertFromIDR(o.getSubtotal()),
                    currency.convertFromIDR(tax)));
            }
            sb.append("\n");
        }

        sb.append(dash).append("\n");

        double rawIDR       = calc.getRawTotalIDR();
        double taxIDR       = taxExempt ? 0 : calc.getTaxIDR();
        double totalWithTax = rawIDR + taxIDR;
        double discAmt      = totalWithTax * channel.getDiscount();
        double adminFee     = channel.getAdminFee();
        double beforePoints = totalWithTax - discAmt + adminFee;
        double pointsVal    = pointsUsed * 2.0;
        double finalIDR     = Math.max(0, beforePoints - pointsVal);

        sb.append(String.format("%-36s %13.2f %s\n",
            "Total (sebelum pajak):", currency.convertFromIDR(rawIDR), currency.getCode()));
        sb.append(String.format("%-36s %13.2f %s\n",
            "Total Pajak" + (taxExempt ? " (DIBEBASKAN):" : ":"),
            currency.convertFromIDR(taxIDR), currency.getCode()));
        sb.append(String.format("%-36s %13.2f %s\n",
            "Total + Pajak:", currency.convertFromIDR(totalWithTax), currency.getCode()));
        sb.append(String.format("%-36s %13.2f %s\n",
            "Diskon " + channel.getName()
                + String.format(" (%.0f%%):", channel.getDiscount() * 100),
            -currency.convertFromIDR(discAmt), currency.getCode()));

        if (adminFee > 0) {
            sb.append(String.format("%-36s %13.2f %s\n",
                "Biaya Admin " + channel.getName() + ":",
                currency.convertFromIDR(adminFee), currency.getCode()));
        }

        sb.append(String.format("%-36s %13.2f %s\n",
            "Total tagihan (sebelum poin):", currency.convertFromIDR(beforePoints), currency.getCode()));

        // Membership & poin — pakai pointsBefore yang disimpan sebelum transaksi
        if (currentMember != null) {
            sb.append(dash).append("\n");
            sb.append(String.format("%-36s %13d poin\n",
                "Poin sebelum transaksi:", pointsBefore));
            if (pointsUsed > 0) {
                sb.append(String.format("%-36s %13d poin = %.0f IDR\n",
                    "Poin digunakan:", pointsUsed, pointsVal));
            }
            sb.append(String.format("%-36s %13d poin%s\n",
                "Poin diperoleh:", pointsEarned, doubled ? " (x2 bonus kode A!)" : ""));
            sb.append(String.format("%-36s %13d poin\n",
                "Poin sesudah transaksi:", currentMember.getPoin()));
        }

        sb.append(line).append("\n");
        sb.append(String.format("%-36s %13.2f %s\n",
            "TOTAL TAGIHAN AKHIR:", currency.convertFromIDR(finalIDR), currency.getCode()));
        sb.append(line).append("\n");
        sb.append("     Terima kasih dan silakan datang kembali!\n");
        sb.append(line).append("\n");
        return sb.toString();
    }

    private String buildKitchenOutput() {
        StringBuilder sb = new StringBuilder();
        sb.append("═".repeat(44)).append("\n");
        sb.append("    ⚙️  ANTRIAN TIM DAPUR – KohiSop II\n");
        sb.append("═".repeat(44)).append("\n");
        sb.append(String.format("  Pelanggan dilayani: %d / 3\n", kitchen.getCustomerCount()));
        sb.append("\n");

        if (kitchen.isReadyToProcess()) {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream old = System.out;
            System.setOut(new java.io.PrintStream(baos));
            kitchen.processOrders();
            System.setOut(old);
            sb.append(baos.toString());
            sb.append("\n").append("═".repeat(44)).append("\n");
            sb.append("  ✅ Batch selesai diproses. Siap untuk pelanggan berikutnya.\n");
        } else {
            sb.append("  ⏳ Menunggu batch 3 pelanggan penuh.\n");
            sb.append("  Pesanan akan diproses setelah pelanggan ke-3.\n\n");
            sb.append("  🍽  MAKANAN: diproses sesuai prioritas harga\n");
            sb.append("       (harga tertinggi → prioritas tertinggi)\n\n");
            sb.append("  ☕  MINUMAN: diproses LOFS\n");
            sb.append("       (pesanan terakhir → diproses pertama / Stack)\n");
            sb.append("═".repeat(44)).append("\n");
        }
        return sb.toString();
    }

    private OrderDisplay buildTaxAdjustedOrder(boolean taxExempt) {
        if (!taxExempt) return orderDisplay;
        OrderDisplay od = new OrderDisplay();
        for (Order o : orderDisplay.getDrinks()) {
            od.addItem(new Order(wrapTaxFree(o.getMenuItem()), o.getQuantity()));
        }
        for (Order o : orderDisplay.getFoods()) {
            od.addItem(new Order(wrapTaxFree(o.getMenuItem()), o.getQuantity()));
        }
        return od;
    }

    private MenuItem wrapTaxFree(MenuItem original) {
        if (original instanceof Minuman) {
            return new Minuman(original.getKode(), original.getNama(),
                original.getHarga(), original.getKategori()) {
                @Override public double hitungPajak(int qty) { return 0; }
            };
        } else {
            return new Makanan(original.getKode(), original.getNama(),
                original.getHarga(), original.getKategori()) {
                @Override public double hitungPajak(int qty) { return 0; }
            };
        }
    }

    private void refreshCartTable() {
        cartModel.setRowCount(0);
        // Makanan dulu, diurutkan harga (sudah di-sort di OrderDisplay)
        for (Order o : orderDisplay.getFoods()) {
            cartModel.addRow(new Object[]{
                o.getMenuItem().getKode(),
                o.getMenuItem().getNama(),
                String.format("Rp %.0f", o.getMenuItem().getHarga()),
                o.getQuantity(),
                String.format("Rp %.0f", o.getSubtotal())
            });
        }
        // Lalu minuman
        for (Order o : orderDisplay.getDrinks()) {
            cartModel.addRow(new Object[]{
                o.getMenuItem().getKode(),
                o.getMenuItem().getNama(),
                String.format("Rp %.0f", o.getMenuItem().getHarga()),
                o.getQuantity(),
                String.format("Rp %.0f", o.getSubtotal())
            });
        }
        lblCartDrinkCount.setText("Minuman: " + orderDisplay.getDrinks().size() + "/5");
        lblCartFoodCount.setText("Makanan: " + orderDisplay.getFoods().size() + "/5");
    }

    private MenuItem findItemByCode(String code) {
        for (MenuItem mi : MENU_ITEMS) {
            if (mi.getKode().equalsIgnoreCase(code)) return mi;
        }
        return null;
    }

    private PaymentChannel resolveChannel(int idx) {
        return switch (idx) {
            case 1  -> new QRIS();
            case 2  -> new EMoney();
            default -> new Tunai();
        };
    }

    private Currency resolveCurrency(String code) {
        return switch (code) {
            case "USD" -> new USD();
            case "JPY" -> new JPY();
            case "MYR" -> new MYR();
            case "EUR" -> new EUR();
            default    -> new IDR();
        };
    }

    private void setStatus(String msg, Color color) {
        lblStatus.setText(msg);
        lblStatus.setForeground(color);
    }

    private static String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }

    // ─── Widget Factory Helpers ───────────────────────────────────────────────
    private JPanel panel(LayoutManager lm) {
        JPanel p = new JPanel(lm);
        p.setBackground(C_BG);
        return p;
    }

    private JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(C_CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER, 1),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        return p;
    }

    private JLabel sectionLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(F_SECTION);
        lbl.setForeground(C_ACCENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        return lbl;
    }

    private JLabel label(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(F_LABEL);
        lbl.setForeground(C_TEXT);
        return lbl;
    }

    private JLabel badge(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(F_SMALL);
        lbl.setForeground(C_TEXT_DIM);
        lbl.setOpaque(true);
        lbl.setBackground(C_PANEL);
        lbl.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER),
            BorderFactory.createEmptyBorder(1, 6, 1, 6)));
        return lbl;
    }

    private JTextField styledField() {
        JTextField tf = new JTextField();
        tf.setBackground(new Color(0x1C1917));
        tf.setForeground(C_TEXT);
        tf.setCaretColor(C_ACCENT);
        tf.setFont(F_LABEL);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER),
            BorderFactory.createEmptyBorder(4, 6, 4, 6)));
        return tf;
    }

    private JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setBackground(C_CARD);
        cb.setForeground(C_TEXT);
        cb.setFont(F_LABEL);
        cb.setBorder(BorderFactory.createLineBorder(C_BORDER));
        return cb;
    }

    private JButton accentButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(C_ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(C_ACCENT.brighter()); }
            @Override public void mouseExited(MouseEvent e)  { btn.setBackground(C_ACCENT); }
        });
        return btn;
    }

    private JButton dangerButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(0x8B3232));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton subtleButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(C_PANEL);
        btn.setForeground(C_TEXT_DIM);
        btn.setFont(F_SMALL);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JTable styledTable(DefaultTableModel model) {
        JTable tbl = new JTable(model) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(new Color(0xC8854A, false));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? C_CARD : C_TABLE_ALT);
                    c.setForeground(C_TEXT);
                }
                return c;
            }
        };
        tbl.setBackground(C_CARD);
        tbl.setForeground(C_TEXT);
        tbl.setFont(F_LABEL);
        tbl.setRowHeight(24);
        tbl.setGridColor(C_BORDER);
        tbl.setShowHorizontalLines(true);
        tbl.setShowVerticalLines(false);
        tbl.setSelectionBackground(C_ACCENT);
        tbl.setSelectionForeground(Color.WHITE);

        JTableHeader hdr = tbl.getTableHeader();
        hdr.setBackground(C_TABLE_HEAD);
        hdr.setForeground(Color.WHITE);
        hdr.setFont(new Font("SansSerif", Font.BOLD, 12));
        hdr.setReorderingAllowed(false);
        hdr.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, C_BORDER));

        return tbl;
    }

    private JScrollPane scrollPane(JComponent comp) {
        JScrollPane sp = new JScrollPane(comp);
        sp.setBorder(BorderFactory.createLineBorder(C_BORDER));
        sp.getViewport().setBackground(C_CARD);
        sp.getVerticalScrollBar().setBackground(C_PANEL);
        return sp;
    }

    private void styleTab(JTabbedPane tabs) {
        tabs.setBackground(C_BG);
        tabs.setForeground(C_TEXT);
        tabs.setFont(F_SECTION);
        tabs.setBorder(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {}

            UIManager.put("TabbedPane.background",          C_PANEL);
            UIManager.put("TabbedPane.foreground",          C_TEXT);
            UIManager.put("TabbedPane.selected",            C_CARD);
            UIManager.put("TabbedPane.contentBorderInsets", new Insets(4, 0, 0, 0));
            UIManager.put("SplitPane.background",           C_BG);
            UIManager.put("SplitPaneDivider.background",    C_BORDER);
            UIManager.put("OptionPane.background",          C_PANEL);
            UIManager.put("OptionPane.messageForeground",   C_TEXT);
            UIManager.put("Panel.background",               C_PANEL);

            new KohiSopGUI().setVisible(true);
        });
    }
}
