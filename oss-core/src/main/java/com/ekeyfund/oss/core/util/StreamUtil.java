package com.ekeyfund.oss.core.util;

import java.io.*;

/**
 * IO流操作工具
 * Created by zhusheng on 2018/4/4 0004.
 */
public class StreamUtil {
    public static final int ioBufferSize = 1024;

    public static final String defaultEncoding = "utf-8";

    public static byte[] readBytes(Reader input, String encoding)throws IOException{
        ByteArrayOutputStream bytesOS = new ByteArrayOutputStream();
        copy(input, bytesOS, encoding);
        return bytesOS.toByteArray();
    }

    public static void copy(Reader input, OutputStream output, String encoding) throws IOException {
        Writer out = new OutputStreamWriter(output, encoding);
        copy(input, out);
        out.flush();
    }

    public static int copy(Reader input, Writer output) throws IOException {
        char[] buffer = new char[ioBufferSize];

        int count;
        int read;
        for(count = 0; (read = input.read(buffer, 0, ioBufferSize)) >= 0; count += read) {
            output.write(buffer, 0, read);
        }

        output.flush();
        return count;
    }
}
