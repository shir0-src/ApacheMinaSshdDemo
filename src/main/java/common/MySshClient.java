package common;

import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.PropertyResolverUtils;
import org.apache.sshd.core.CoreModuleProperties;

public class MySshClient {
    // SSHサーバに接続してインタラクティブなシェルを起動するデモ
    public static void start(String host, String username, String password, int port, long defaultTimeout)
            throws Exception {
        SshClient client = SshClient.setUpDefaultClient();
        PropertyResolverUtils.updateProperty(client, CoreModuleProperties.SEND_IMMEDIATE_IDENTIFICATION.getName(),
                false);
        client.start();
        try (ClientSession session = client.connect(username, host, port).verify(defaultTimeout, TimeUnit.SECONDS)
                .getSession(); ClientChannel channel = session.createShellChannel();) {
            // パスワード認証
            session.addPasswordIdentity(password);
            session.auth().verify();
            // 標準入力、標準出力、標準エラー出力の設定
            channel.setIn(System.in);
            channel.setOut(System.out);
            channel.setErr(System.err);
            // チャンネルのオープン
            channel.open().verify(defaultTimeout);
            channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 0L);
        } finally {
            client.close();
        }
    }
}
