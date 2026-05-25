package quickchat;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Message {

    // ── Fields ───────────────────────────────────────────────────────────────
    private String messageID;
    private int messageNumber;
    private String recipientCell;
    private String messageText;
    private String messageHash;

    private static ArrayList<String> sentMessages = new ArrayList<>();
    private static int totalMessagesSent = 0;
    private static int messageCounter = 0;

    // ── Constructor ──────────────────────────────────────────────────────────
    public Message(String recipientCell, String messageText) {
        messageCounter++;
        this.messageNumber = messageCounter;
        this.messageID     = generateMessageID();
        this.recipientCell = recipientCell;
        this.messageText   = messageText;
        this.messageHash   = createMessageHash();
    }

    // ── Generate random 10-digit ID ──────────────────────────────────────────
    private String generateMessageID() {
        Random random = new Random();
        long id = (long)(random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    // ════════════════════════════════════════════════════════════════════════
    // METHOD 1 — checkMessageID()
    // Ensures the message ID is not more than 10 characters
    // ════════════════════════════════════════════════════════════════════════
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // ════════════════════════════════════════════════════════════════════════
    // METHOD 2 — checkRecipientCell()
    // Validates cell number: max 10 chars, starts with + or 0
    // ════════════════════════════════════════════════════════════════════════
    public String checkRecipientCell() {
        boolean correctLength = recipientCell.length() <= 10;
        boolean correctFormat = recipientCell.startsWith("+")
                             || recipientCell.startsWith("0");
        if (correctLength && correctFormat) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain " +
                   "an international code. Please correct the number and try again.";
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // METHOD 3 — createMessageHash()
    // Format: first2charsOfID:messageNumber:FIRSTWORDlastword (ALL CAPS)
    // Example: 00:1:HITONIGHT
    // ════════════════════════════════════════════════════════════════════════
    public String createMessageHash() {
        String idPart    = messageID.substring(0, 2);
        String[] words   = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord  = words[words.length - 1].replaceAll("[^a-zA-Z0-9]", "");
        String hash = (idPart + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        this.messageHash = hash;
        return hash;
    }

    // ════════════════════════════════════════════════════════════════════════
    // METHOD 4 — sentMessage()
    // Checks 250-char limit then lets user Send / Disregard / Store
    // ════════════════════════════════════════════════════════════════════════
    public String sentMessage(Scanner scanner) {
        if (messageText.length() > 250) {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess +
                   " character(s); please reduce the size.";
        }
        System.out.println("\nWhat would you like to do with this message?");
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");
        System.out.print("Enter choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                totalMessagesSent++;
                sentMessages.add(printMessages());
                return "Message successfully sent.";
            case "2":
                return "Press 0 to delete the message.";
            case "3":
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option. Please enter 1, 2, or 3.";
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // METHOD 5 — printMessages()
    // Returns formatted details: ID, Hash, Recipient, Message
    // ════════════════════════════════════════════════════════════════════════
    public String printMessages() {
        return "\n--- Message Details ---"         +
               "\nMessage ID   : " + messageID     +
               "\nMessage Hash : " + messageHash   +
               "\nRecipient    : " + recipientCell +
               "\nMessage      : " + messageText   +
               "\n-----------------------";
    }

    // ════════════════════════════════════════════════════════════════════════
    // METHOD 6 — returnTotalMessages()
    // Returns total messages sent this session
    // ════════════════════════════════════════════════════════════════════════
    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    // ════════════════════════════════════════════════════════════════════════
    // METHOD 7 — storeMessage()
    // Stores message in JSON format (research method)
    // ════════════════════════════════════════════════════════════════════════
    public void storeMessage() {
        String json = "{\n" +
                      "  \"messageID\"     : \"" + messageID     + "\",\n" +
                      "  \"messageHash\"   : \"" + messageHash   + "\",\n" +
                      "  \"messageNumber\" : "   + messageNumber + ",\n"   +
                      "  \"recipient\"     : \"" + recipientCell + "\",\n" +
                      "  \"message\"       : \"" + messageText   + "\"\n"  +
                      "}";
        System.out.println("Message stored as JSON:\n" + json);
    }

    // ── Print all sent messages ──────────────────────────────────────────────
    public static void printAllSentMessages() {
        if (sentMessages.isEmpty()) {
            System.out.println("No messages have been sent yet.");
            return;
        }
        System.out.println("\n===== All Sent Messages =====");
        for (String msg : sentMessages) {
            System.out.println(msg);
        }
        System.out.println("Total messages sent: " + totalMessagesSent);
    }

    // ── Getters ──────────────────────────────────────────────────────────────
    public String getMessageID()             { return messageID;         }
    public String getRecipientCell()         { return recipientCell;     }
    public String getMessageText()           { return messageText;       }
    public String getMessageHash()           { return messageHash;       }
    public int    getMessageNumber()         { return messageNumber;     }
    public static int getTotalMessagesSent() { return totalMessagesSent; }
    public static ArrayList<String> getSentMessages() { return sentMessages; }

    // ── Setters / Reset ──────────────────────────────────────────────────────
    public void setMessageText(String text) { this.messageText = text; }
    public static void resetCounter() {
        totalMessagesSent = 0;
        sentMessages.clear();
        messageCounter = 0;
    }
}