package com.amazon.helpers;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static java.lang.String.format;

public final class FileHelper {

    public static File getFile(String fileName) {
        if (exists(fileName)) {
            return new File(fileName);
        }
        throw new IllegalArgumentException(format("Cannot find file [%s]", fileName));
    }

    public static Properties getProperties(String fileName) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("secret");
        Properties properties = new EncryptableProperties(encryptor);
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public static boolean exists(String pathName) {
        File file = new File(pathName);
        return file.exists();
    }
}
