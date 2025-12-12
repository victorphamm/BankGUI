import java.util.LinkedHashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new LinkedHashMap<>();
    private int nextId = 1000;

    public Account createAccount(String owner, String type, String pin) {
        if (owner == null || owner.isBlank()) throw new IllegalArgumentException("Owner cannot be empty.");
        String id = String.valueOf(nextId++);
        Account acct;

        switch (type) {
            case "checking" -> acct = new CheckingAccount(id, owner, pin);
            case "savings"  -> acct = new SavingsAccount(id, owner, pin);
            default -> throw new IllegalArgumentException("Unknown type. Use checking or savings.");
        }

        accounts.put(id, acct);
        return acct;
    }

    public Account getAccount(String id) {
        Account acct = accounts.get(id);
        if (acct == null) throw new IllegalArgumentException("Account not found: " + id);
        return acct;
    }

    public Account login(String id, String pin) {
        Account acct = getAccount(id);
        if (!acct.verifyPin(pin)) throw new IllegalArgumentException("Invalid PIN.");
        return acct;
    }

    public void transfer(String fromId, String toId, long cents) {
        if (fromId.equals(toId)) throw new IllegalArgumentException("Cannot transfer to the same account.");
        Money.requirePositive(cents);

        Account from = getAccount(fromId);
        Account to = getAccount(toId);

        from.withdraw(cents);
        to.deposit(cents);

        from.log("TRANSFER_OUT", cents, "To " + toId);
        to.log("TRANSFER_IN", cents, "From " + fromId);
    }

    public String listAccounts() {
        if (accounts.isEmpty()) return "(no accounts yet)";
        StringBuilder sb = new StringBuilder();
        for (Account a : accounts.values()) sb.append(a).append("\n");
        return sb.toString();
    }
}
