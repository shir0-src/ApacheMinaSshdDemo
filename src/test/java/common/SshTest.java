package common;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SshTest {
    private final static int PORT = 2222;

    @BeforeAll
    public static void startSshServer() {
        try {
            MySshServer.start(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sshConnectionTest() {
        try {
            MySshClient.start("127.0.0.1", "user", "password", PORT, 10000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
