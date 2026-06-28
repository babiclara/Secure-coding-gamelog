package hr.algebra.gamelog.security;

import java.io.*;
import java.util.Set;

public class WhitelistObjectInputStream extends ObjectInputStream {

    private static final Set<String> ALLOWED_CLASSES = Set.of(
            "hr.algebra.gamelog.dto.GameSnapshot",
            "java.lang.String",
            "java.lang.Integer",
            "java.lang.Long",
            "java.lang.Number"
    );

    public WhitelistObjectInputStream(InputStream in) throws IOException {
        super(in);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        if (!ALLOWED_CLASSES.contains(desc.getName())) {
            throw new InvalidClassException("Unauthorized deserialization attempt: " + desc.getName());
        }
        return super.resolveClass(desc);
    }
}