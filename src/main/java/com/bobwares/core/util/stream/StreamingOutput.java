package com.bobwares.core.util.stream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by morrisc6 on 6/5/2014.
 */
public interface StreamingOutput {
    void write(OutputStream os) throws IOException;
}
