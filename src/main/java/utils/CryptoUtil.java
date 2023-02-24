package utils;


import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
public class CryptoUtil {
    public static final String cryptoPassword="RocsogeiVocsokCsopi";
    public static StandardPBEStringEncryptor encryptor;
    private static CryptoUtil instance = null;
    protected CryptoUtil() {
        encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(cryptoPassword) ;

    }
    public static CryptoUtil getInstance() {
        if(instance == null) {
            instance = new CryptoUtil();
        }
        return instance;
    }

    public String encryptValue(String value)  {
        return encryptor.encrypt(value);
    }

    public String decryptValue(String encryptedValue)  {
        return encryptor.decrypt(encryptedValue);
    }
}


