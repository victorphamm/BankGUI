import java.util.Scanner;

public class Main {

    private static void requireLogin(Account current) {
        if (current == null) throw new IllegalStateException("Please login first.");
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner sc = new Scanner(System.in);
        Account current = null;

        while (true) {
            System.out.println("\n=== BANK MENU ===");
            System.out.println("1) Create account");
            System.out.println("7) Login");
            System.out.println("8) Logout");
            System.out.println("2) Deposit (logged-in)");
            System.out.println("3) Withdraw (logged-in)");
            System.out.println("4) Transfer (from logged-in)");
            System.out.println("5) View account (logged-in)");
            System.out.println("9) Change PIN (logged-in)");
            System.out.println("6) List accounts");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> {
                        System.out.print("Owner name: ");
                        String owner = sc.nextLine().trim();
                        System.out.print("Type (checking/savings): ");
                        String type = sc.nextLine().trim().toLowerCase();
                        System.out.print("Set PIN (4–6 digits): ");
                        String pin = sc.nextLine().trim();

                        Account acct = bank.createAccount(owner, type, pin);
                        System.out.println("Created: " + acct + " | Login with ID=" + acct.getId());
                    }

                    case "7" -> {
                        System.out.print("Account ID: ");
                        String id = sc.nextLine().trim();
                        System.out.print("PIN: ");
                        String pin = sc.nextLine().trim();
                        current = bank.login(id, pin);
                        System.out.println("Logged in as " + current.getOwner() + " (ID " + current.getId() + ")");
                    }

                    case "8" -> {
                        current = null;
                        System.out.println("Logged out.");
                    }

                    case "2" -> {
                        requireLogin(current);
                        System.out.print("Amount: ");
                        long cents = Money.parseToCents(sc.nextLine());
                        current.deposit(cents);
                        System.out.println("Deposited.");
                    }

                    case "3" -> {
                        requireLogin(current);
                        System.out.print("Amount: ");
                        long cents = Money.parseToCents(sc.nextLine());
                        current.withdraw(cents);
                        System.out.println("Withdrew.");
                    }

                    case "4" -> {
                        requireLogin(current);
                        System.out.print("To Account ID: ");
                        String to = sc.nextLine().trim();
                        System.out.print("Amount: ");
                        long cents = Money.parseToCents(sc.nextLine());
                        bank.transfer(current.getId(), to, cents);
                        System.out.println("Transferred.");
                    }

                    case "5" -> {
                        requireLogin(current);
                        System.out.println(current.detailedView());
                    }

                    case "9" -> {
                        requireLogin(current);
                        System.out.print("Old PIN: ");
                        String oldPin = sc.nextLine().trim();
                        System.out.print("New PIN (4–6 digits): ");
                        String newPin = sc.nextLine().trim();
                        current.changePin(oldPin, newPin);
                        System.out.println("PIN updated.");
                    }

                    case "6" -> System.out.println(bank.listAccounts());

                    case "0" -> {
                        System.out.println("Bye!");
                        return;
                    }

                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
