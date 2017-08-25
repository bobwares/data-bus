package com.bobwares.core.util.stream;

import java.io.IOException;
import java.io.OutputStream;

public interface StreamingOutput {
    void write(OutputStream os) throws IOException;
}
