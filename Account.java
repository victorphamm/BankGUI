import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected final String id;
    protected final String owner;

    protected long balanceCents;

    protected final String pinSaltHex;
    protected String pinHashHex;

    protected final List<Tx> history = new ArrayList<>();

    protected Account(String id, String owner, String pin) {
        this.id = id;
        this.owner = owner;
        this.balanceCents = 0;

        this.pinSaltHex = PinUtil.newSaltHex();
        this.pinHashHex = PinUtil.hashPin(pin, pinSaltHex);

        log("OPEN", 0, "Account opened");
    }

    public String getId() { return id; }
    public String getOwner() { return owner; }
    public long getBalanceCents() { return balanceCents; }

    public boolean verifyPin(String pin) {
        String attempt = PinUtil.hashPin(pin, pinSaltHex);
        return attempt.equals(pinHashHex);
    }

    public void changePin(String oldPin, String newPin) {
        if (!verifyPin(oldPin)) throw new IllegalArgumentException("Old PIN incorrect.");
        this.pinHashHex = PinUtil.hashPin(newPin, pinSaltHex);
        log("PIN_CHANGE", 0, "PIN changed");
    }

    public void deposit(long cents) {
        Money.requirePositive(cents);
        balanceCents += cents;
        log("DEPOSIT", cents, "Deposit");
    }

    public void withdraw(long cents) {
        Money.requirePositive(cents);
        if (balanceCents < cents) throw new IllegalArgumentException("Insufficient funds.");
        balanceCents -= cents;
        log("WITHDRAW", cents, "Withdraw");
    }

    public void log(String type, long cents, String note) {
        history.add(new Tx(Instant.now(), type, cents, balanceCents, note));
    }

    public String detailedView() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString()).append("\n");
        sb.append("Balance: ").append(Money.format(balanceCents)).append("\n");
        sb.append("History:\n");
        for (Tx t : history) sb.append("  ").append(t).append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{id=" + id + ", owner=" + owner + "}";
    }
}
