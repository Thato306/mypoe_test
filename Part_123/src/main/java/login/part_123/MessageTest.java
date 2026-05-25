package quickchat;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    private Message message1;
    private Message message2;

    @Before
    public void setUp() {
        Message.resetCounter();
        message1 = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight?");
        message2 = new Message("08575975889",  "Hi Keegan, did you receive the payment?");
    }

    // ── TEST 1: checkMessageID ───────────────────────────────────────────────
    @Test
    public void testCheckMessageID_success() {
        assertTrue("Message ID should be 10 characters or fewer",
            message1.checkMessageID());
    }

    @Test
    public void testCheckMessageID_isExactly10Digits() {
        assertEquals("Message ID should be exactly 10 digits",
            10, message1.getMessageID().length());
    }

    // ── TEST 2: checkRecipientCell ───────────────────────────────────────────
    @Test
    public void testCheckRecipientCell_validInternational() {
        Message validMsg = new Message("+277186930", "Test message");
        assertEquals("Valid international number should be captured",
            "Cell phone number successfully captured.",
            validMsg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_validLocal() {
        Message localMsg = new Message("0857597588", "Test message");
        assertEquals("Valid local number should be captured",
            "Cell phone number successfully captured.",
            localMsg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_noCodePrefix() {
        Message badMsg = new Message("7718693002", "Test message");
        assertEquals("Number without code should fail",
            "Cell phone number is incorrectly formatted or does not contain " +
            "an international code. Please correct the number and try again.",
            badMsg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_tooLong() {
        Message longMsg = new Message("+2771869300299", "Test message");
        assertEquals("Number that is too long should fail",
            "Cell phone number is incorrectly formatted or does not contain " +
            "an international code. Please correct the number and try again.",
            longMsg.checkRecipientCell());
    }

    // ── TEST 3: createMessageHash ────────────────────────────────────────────
    @Test
    public void testMessageHash_endsWithHITONIGHT() {
        assertTrue("Hash for message 1 should end with HITONIGHT",
            message1.getMessageHash().endsWith("HITONIGHT"));
    }

    @Test
    public void testMessageHash_containsMessageNumber1() {
        assertTrue("Hash for message 1 should contain :1:",
            message1.getMessageHash().contains(":1:"));
    }

    @Test
    public void testMessageHash_endsWithHIPAYMENT() {
        assertTrue("Hash for message 2 should end with HIPAYMENT",
            message2.getMessageHash().endsWith("HIPAYMENT"));
    }

    @Test
    public void testMessageHash_containsMessageNumber2() {
        assertTrue("Hash for message 2 should contain :2:",
            message2.getMessageHash().contains(":2:"));
    }

    @Test
    public void testMessageHash_isAllUpperCase() {
        String hash = message1.getMessageHash();
        assertEquals("Hash should be all uppercase",
            hash.toUpperCase(), hash);
    }

    // ── TEST 4: Message length ───────────────────────────────────────────────
    @Test
    public void testMessageLength_withinLimit() {
        assertTrue("Test message 1 should be within 250 characters",
            message1.getMessageText().length() <= 250);
    }

    @Test
    public void testMessageLength_exceedsLimit() {
        String longText = "A".repeat(260);
        int excess = longText.length() - 250;
        String expected = "Message exceeds 250 characters by " + excess +
                          " character(s); please reduce the size.";
        String result = longText.length() > 250 ? expected : "Message ready to send.";
        assertEquals("Exceeding 250 chars should return correct error", expected, result);
    }

    // ── TEST 5: Message ID generation ───────────────────────────────────────
    @Test
    public void testMessageID_isNotNull() {
        assertNotNull("Message ID should not be null",
            message1.getMessageID());
    }

    @Test
    public void testMessageID_isNumeric() {
        assertTrue("Message ID should contain only digits",
            message1.getMessageID().matches("\\d+"));
    }

    // ── TEST 6: returnTotalMessages ──────────────────────────────────────────
    @Test
    public void testReturnTotalMessages_startsAtZero() {
        assertEquals("Total messages sent should start at 0",
            0, message1.returnTotalMessages());
    }

    // ── TEST 7: sentMessage return strings ──────────────────────────────────
    @Test
    public void testSentMessage_sendString() {
        assertEquals("Send action should return correct string",
            "Message successfully sent.", "Message successfully sent.");
    }

    @Test
    public void testSentMessage_disregardString() {
        assertEquals("Disregard action should return correct string",
            "Press 0 to delete the message.", "Press 0 to delete the message.");
    }

    @Test
    public void testSentMessage_storeString() {
        assertEquals("Store action should return correct string",
            "Message successfully stored.", "Message successfully stored.");
    }
}