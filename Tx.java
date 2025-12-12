import java.time.Instant;

public record Tx(Instant timestamp, String type, long amountCents, long balanceAfterCents, String note) {
    @Override
    public String toString() {
        return timestamp + " | " + type +
                " | " + Money.format(amountCents) +
                " | bal=" + Money.format(balanceAfterCents) +
                " | " + note;
    }
}
