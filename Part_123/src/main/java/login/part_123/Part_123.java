package quickchat;

import java.util.Scanner;

public class QuickChat {

    private static final String REGISTERED_USERNAME = "kyl_1";
    private static final String REGISTERED_PASSWORD = "Ch&&sec@ke99!";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("        Welcome to QuickChat             ");
        System.out.println("==========================================");

        // ── STEP 1: Login ────────────────────────────────────────────────────
        boolean loggedIn = false;
        int attempts = 0;

        while (!loggedIn && attempts < 3) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine().trim();
            System.out.print("Enter password: ");
            String password = scanner.nextLine().trim();

            if (username.equals(REGISTERED_USERNAME) && password.equals(REGISTERED_PASSWORD)) {
                loggedIn = true;
                System.out.println("\nWelcome to QuickChat.");
            } else {
                attempts++;
                System.out.println("Incorrect credentials. " + (3 - attempts) + " attempt(s) remaining.");
            }
        }

        if (!loggedIn) {
            System.out.println("Too many failed attempts. Exiting.");
            return;
        }

        // ── STEP 2: How many messages? ───────────────────────────────────────
        System.out.print("\nHow many messages would you like to send? ");
        int numMessages = 1;
        try {
            numMessages = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Defaulting to 1.");
        }

        // ── STEP 3: Main menu loop ───────────────────────────────────────────
        boolean running = true;
        int messagesSentSession = 0;

        while (running) {
            System.out.println("\n==========================================");
            System.out.println("               Main Menu                 ");
            System.out.println("==========================================");
            System.out.println("1) Send Messages");
            System.out.println("2) Show Recently Sent Messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    if (messagesSentSession >= numMessages) {
                        System.out.println("You have reached your limit of " + numMessages + " message(s).");
                        break;
                    }

                    System.out.print("\nEnter recipient cell number: ");
                    String recipient = scanner.nextLine().trim();

                    System.out.print("Enter your message (max 250 characters): ");
                    String text = scanner.nextLine().trim();

                    Message msg = new Message(recipient, text);

                    if (!msg.checkMessageID()) {
                        System.out.println("Error: Message ID exceeds 10 characters.");
                        break;
                    }

                    System.out.println(msg.checkRecipientCell());
                    System.out.println(msg.printMessages());

                    String result = msg.sentMessage(scanner);
                    System.out.println(result);

                    if (result.equals("Message successfully sent.")) {
                        messagesSentSession++;
                    }

                    if (messagesSentSession >= numMessages) {
                        System.out.println("\nAll " + numMessages + " message(s) processed.");
                        System.out.println("Total messages sent: " + msg.returnTotalMessages());
                        Message.printAllSentMessages();
                    }
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    System.out.println("Thank you for using QuickChat. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
        scanner.close();
    }
}