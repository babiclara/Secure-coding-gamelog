package hr.algebra.gamelog.service;

import hr.algebra.gamelog.security.WhitelistObjectInputStream;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class SecureDeserializationService {

    private static final byte[] JAVA_MAGIC_BYTES = {(byte) 0xAC, (byte) 0xED, (byte) 0x00, (byte) 0x05};

    public void serialize(Object obj, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(obj);
        }
    }

    public Object deserialize(String filePath) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] header = new byte[4];
            if (fis.read(header) != 4) {
                throw new IOException("File too small to be a valid serialized object");
            }
            for (int i = 0; i < 4; i++) {
                if (header[i] != JAVA_MAGIC_BYTES[i]) {
                    throw new IOException("Invalid magic bytes - file is not a valid Java serialized object");
                }
            }
        }

        try (WhitelistObjectInputStream wois = new WhitelistObjectInputStream(new FileInputStream(filePath))) {
            return wois.readObject();
        }
    }
}