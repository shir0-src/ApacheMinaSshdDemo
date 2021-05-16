package common;

import java.io.IOException;

import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.shell.InteractiveProcessShellFactory;

public class MySshServer {

    // パスワード認証
    public static void start(final int port) throws IOException {
        SshServer sshd = SshServer.setUpDefaultServer();
        // インタラクティブなシェルを提供する
        sshd.setShellFactory(new InteractiveProcessShellFactory());
        // パスワード認証を設定
        sshd.setPasswordAuthenticator(new MyPasswordAuthenticator());
        sshd.setPort(port);
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
        // サーバ起動
        sshd.start();
        System.out.println("[+] SSH server started on port " + sshd.getPort());
    }
}
