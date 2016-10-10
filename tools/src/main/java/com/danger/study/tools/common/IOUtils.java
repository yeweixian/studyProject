package com.danger.study.tools.common;

import java.io.IOException;
import java.io.InputStream;

/**
 * io流操作工具类
 * Created by danger on 2016/6/15.
 */
public class IOUtils {

    public static String toStringByInputStream(InputStream inputStream) throws IOException {
        return toStringByInputStream(inputStream, "UTF-8");
    }

    public static String toStringByInputStream(InputStream inputStream, String charsetName) throws IOException {
        byte[] buffer = new byte[2048];
        int readBytes;
        StringBuilder stringBuilder = new StringBuilder();
        while ((readBytes = inputStream.read(buffer)) > 0) {
            stringBuilder.append(new String(buffer, 0, readBytes, charsetName));
        }
        return stringBuilder.toString();
    }
}
