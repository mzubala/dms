package pl.com.bottega.dms.infrastructure;

import java.io.IOException;
import java.io.InputStream;

public class CesarInputStream extends InputStream {

    private final InputStream decorated;
    private final int key;

    public CesarInputStream(InputStream decorated, int key) {
        this.decorated = decorated;
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int b = decorated.read();
        if(b == -1)
            return -1;
        return (b - key) % 0xff;
    }
}
