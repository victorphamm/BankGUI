public class Money {

    public static void requirePositive(long cents) {
        if (cents <= 0) throw new IllegalArgumentException("Amount must be > 0.");
    }

    public static String format(long cents) {
        long abs = Math.abs(cents);
        long dollars = abs / 100;
        long rem = abs % 100;
        String sign = cents < 0 ? "-" : "";
        return sign + "$" + dollars + "." + (rem < 10 ? "0" : "") + rem;
    }

    public static long parseToCents(String s) {
        if (s == null) throw new IllegalArgumentException("Amount required.");
        s = s.trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Amount required.");
        if (s.startsWith("$")) s = s.substring(1);

        if (!s.matches("\\d+(\\.\\d{1,2})?")) {
            throw new IllegalArgumentException("Invalid money format: " + s);
        }

        String[] parts = s.split("\\.");
        long dollars = Long.parseLong(parts[0]);
        long cents = dollars * 100;

        if (parts.length == 2) {
            String frac = parts[1];
            if (frac.length() == 1) frac = frac + "0";
            cents += Long.parseLong(frac);
        }
        return cents;
    }
}
