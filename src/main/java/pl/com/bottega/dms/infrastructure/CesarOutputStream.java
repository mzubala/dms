package pl.com.bottega.dms.infrastructure;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CesarOutputStream extends OutputStream {

    private final OutputStream decorated;
    private final int key;

    public CesarOutputStream(OutputStream decorated, int key) {
        this.decorated = decorated;
        this.key = key;
    }

    @Override
    public void write(int b) throws IOException {
        int cipheredB = (b +  key) % 0xff;
        this.decorated.write(cipheredB);
    }

}


