package com.mjm.todomicroservice.utilities;


import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {

    //Attributes from jasypt library
    private BasicTextEncryptor textEncryptor;

    //Constructor
    public EncryptionUtils(){
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("mySecretEncryptionKeyBlaBla1234");
    }

    public String encrypt(String data){
        return textEncryptor.encrypt(data);
    }

    public String decrypt(String encryptedData){
        return textEncryptor.decrypt(encryptedData);
    }
}
