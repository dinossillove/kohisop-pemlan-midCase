package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Membership {
    private static final String CODE_CHARS = "ABCDEF0123456789";
    private static final int CODE_LENGTH = 6;
    private static final Random RANDOM = new Random();
    private static final List<Membership> DATABASE = new ArrayList<>();

    private String kodeMember;
    private String namaMember;
    private int poin;

    public Membership(String namaMember, int poin) {
        this.kodeMember = generateUniqueCode();
        this.namaMember = namaMember;
        this.poin = poin;
    }

    public static Membership getOrCreateMember(String namaMember) {
        String normalizedNama = namaMember.trim();
        Membership member = findByNama(normalizedNama);
        if (member == null) {
            member = new Membership(normalizedNama, 0);
            DATABASE.add(member);
        }
        return member;
    }

    public static Membership registerPurchase(String namaMember, double totalIDR) {
        Membership member = getOrCreateMember(namaMember);
        member.addPointsFromTransaction(totalIDR);
        return member;
    }

    public static List<Membership> getDatabase() {
        return DATABASE;
    }

    private static Membership findByNama(String namaMember) {
        for (Membership member : DATABASE) {
            if (member.namaMember.equalsIgnoreCase(namaMember)) {
                return member;
            }
        }
        return null;
    }

    private static Membership findByKode(String kodeMember) {
        for (Membership member : DATABASE) {
            if (member.kodeMember.equalsIgnoreCase(kodeMember)) {
                return member;
            }
        }
        return null;
    }

    private static int calculatePoints(double totalIDR) {
        long roundedTotal = Math.round(totalIDR);
        return (roundedTotal % 10 == 0) ? 1 : 0;
    }

    public int redeemPoints(double amountIDR) {
        if (poin <= 0 || amountIDR <= 0) {
            return 0;
        }
        int maxUsablePoints = (int) Math.ceil(amountIDR / 2.0);
        int pointsToUse = Math.min(poin, maxUsablePoints);
        poin -= pointsToUse;
        return pointsToUse;
    }

    public void addPointsFromTransaction(double totalIDR) {
        int earnedPoints = calculatePoints(totalIDR);
        if (earnedPoints > 0) {
            poin += earnedPoints;
        }
    }

    public int getAvailablePoints() {
        return poin;
    }

    public double getAvailablePointsValueIDR() {
        return poin * 2.0;
    }

    public int getEarnedPointsForTransaction(double totalIDR) {
        return calculatePoints(totalIDR);
    }

    private static String generateUniqueCode() {
        String code;
        do {
            code = generateCode();
        } while (findByKode(code) != null);
        return code;
    }

    private static String generateCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = RANDOM.nextInt(CODE_CHARS.length());
            sb.append(CODE_CHARS.charAt(index));
        }
        return sb.toString();
    }

    public String getKodeMember() {
        return kodeMember;
    }

    public String getNamaMember() {
        return namaMember;
    }

    public int getPoin() {
        return poin;
    }
}
