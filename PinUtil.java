import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class PinUtil {
    private static final SecureRandom RNG = new SecureRandom();

    public static String newSaltHex() {
        byte[] salt = new byte[16];
        RNG.nextBytes(salt);
        return toHex(salt);
    }

    public static String hashPin(String pin, String saltHex) {
        if (pin == null || !pin.matches("\\d{4,6}")) {
            throw new IllegalArgumentException("PIN must be 4–6 digits.");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(fromHex(saltHex));
            md.update(pin.getBytes(StandardCharsets.UTF_8));
            return toHex(md.digest());
        } catch (Exception e) {
            throw new RuntimeException("Hashing failed", e);
        }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private static byte[] fromHex(String hex) {
        int len = hex.length();
        byte[] out = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            out[i / 2] = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
        }
        return out;
    }
}
