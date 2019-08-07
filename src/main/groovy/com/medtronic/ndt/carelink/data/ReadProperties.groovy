
package com.medtronic.ndt.carelink.data

import com.google.common.io.ByteSource
import com.google.common.io.Resources

/**
 * Reads a properties file and print all the key value pairs to the console.
 * Note: Writing to console is just for convenience here.
 */
public final class ReadProperties {
    private static  Properties instance
    private static void appReader() {
        final URL url = Resources.getResource("application_en.properties")
        final ByteSource byteSource = Resources.asByteSource(url)
        final Properties properties = new Properties()
        InputStream inputStream = null
        try {
            inputStream = byteSource.openBufferedStream()
            properties.load(inputStream)
        } catch (final IOException ioException) {
            ioException.printStackTrace()
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (final IOException ioException) {
                    ioException.printStackTrace()
                }
            }
        }
 instance = properties
    }
    public static Properties getProperties(){
        if (instance == null) {
            appReader()
        }
        return instance
    }
}




