package hr.algebra.gamelog.security;

import org.springframework.stereotype.Service;
import java.net.InetAddress;
import java.net.URL;

@Service
public class SsrfProtectionService {

    public boolean isSafeUrl(String urlString) {
        try {
            URL url = new URL(urlString);
            InetAddress address = InetAddress.getByName(url.getHost());
            String ip = address.getHostAddress();

            if (ip.startsWith("127.") ||
                    ip.startsWith("10.") ||
                    ip.startsWith("192.168.") ||
                    ip.startsWith("169.254.") ||
                    ip.equals("0.0.0.0") ||
                    ip.startsWith("172.16.")) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}