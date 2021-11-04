package db;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class TestPasswords {
	
    @Test
    public void testSecurePassword() throws NoSuchAlgorithmException {
        // same salt should be passed
        byte[] salt = DatabaseUtil.getSalt();
        String password1 = DatabaseUtil.getSecurePassword("Password", salt);
        String password2 = DatabaseUtil.getSecurePassword("Password", salt);
        System.out.println(" Password 1 -> " + password1);
        System.out.println(" Password 2 -> " + password2);
        if (password1.equals(password2)) {
            System.out.println("passwords are equal");
        }
        
        // TODO: need to store both the hashed password AND salt in the database
        
}
}
