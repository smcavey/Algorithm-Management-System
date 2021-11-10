package db;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class TestPasswords {
	
    @Test
    public void testSecurePassword() throws NoSuchAlgorithmException {
    	UsersDAO dao = new UsersDAO();
    	
        // same salt should be passed
        byte[] salt = dao.getSalt();
        String password1 = dao.getSecurePassword("Password", salt);
        String password2 = dao.getSecurePassword("Password", salt);
        System.out.println(" Password 1 -> " + password1);
        System.out.println(" Password 2 -> " + password2);
        if (password1.equals(password2)) {
            System.out.println("passwords are equal");
        }
    }
}
