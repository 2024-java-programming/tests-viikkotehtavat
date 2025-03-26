package exercises;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.*;

@DisplayName("Notification System")
public class NotificationTest {
    private static MethodHandle emailSendHandle;
    private static MethodHandle smsSendHandle;
    private static MethodHandle emailFormatHandle;
    private static MethodHandle smsFormatHandle;

    @BeforeEach
    public void setUp() throws Exception {
        assertTrue(Modifier.isInterface(Sendable.class.getModifiers()), "Sendable must be an interface");
        assertEquals("exercises.Notification", EmailNotification.class.getSuperclass().getName(), "EmailNotification must extend Notification");
        assertEquals("exercises.Notification", SMSNotification.class.getSuperclass().getName(), "SMSNotification must extend Notification");

        try {
            Class<?> emailClass = Class.forName("exercises.EmailNotification");
            emailFormatHandle = MethodHandles.lookup().findVirtual(emailClass, "formatMessage", MethodType.methodType(String.class));
            emailSendHandle = MethodHandles.lookup().findVirtual(emailClass, "send", MethodType.methodType(String.class));

            Class<?> smsClass = Class.forName("exercises.SMSNotification");
            smsFormatHandle = MethodHandles.lookup().findVirtual(smsClass, "formatMessage", MethodType.methodType(String.class));
            smsSendHandle = MethodHandles.lookup().findVirtual(smsClass, "send", MethodType.methodType(String.class));
        } catch (Exception e) {
            fail("Notification classes or methods are missing: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test sending email")
    public void testSendEmail() throws Throwable {
        EmailNotification email = new EmailNotification("my@email.com", "Hello!");
        assertEquals("my@email.com: Hello!", emailFormatHandle.invoke(email), "Email formatMessage() is incorrect");
        
        email = new EmailNotification("hello@gmail.com", "Good bye!");
        assertEquals("Sent email hello@gmail.com: Good bye!", emailSendHandle.invoke(email), "Email send() is incorrect");
    }

    @Test
    @DisplayName("Test sending SMS")
    public void testSendSMS() throws Throwable {
        SMSNotification sms = new SMSNotification("0123456789", "Hello!");
        assertEquals("+358-123456789: Hello!", smsFormatHandle.invoke(sms), "SMS formatMessage() phone number formatting is incorrect");

        sms = new SMSNotification("0987654321", "Good bye!");
        assertEquals("Sent SMS +358-987654321: Good bye!", smsSendHandle.invoke(sms), "SMS send() is incorrect");
    }

    @Test
    @DisplayName("Test empty notification inputs")
    public void testEmptyInputs() throws Throwable {
        EmailNotification email = new EmailNotification("", "");
        assertEquals(": ", emailFormatHandle.invoke(email), "Empty email should format correctly");

        SMSNotification sms = new SMSNotification("01234", "");
        assertEquals("+358-1234: ", smsFormatHandle.invoke(sms), "Empty SMS message should format correctly");
    }
}
