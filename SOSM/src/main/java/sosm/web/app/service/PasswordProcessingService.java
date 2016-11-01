package sosm.web.app.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service
public class PasswordProcessingService {
	
	private static final String SALT_STRING = "s11adc1949qa31a0be56e557f20f983yb87u67bf19da565w191e42f76670ragv";

	public String getHashedPassword(String password, String username) throws NoSuchAlgorithmException {
		String salt = SALT_STRING + username;
		
		String hashedPassword = null;
		String hashedSalt = null;
		String hashedSaltedPassword = null;
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		md.update(password.getBytes());
		byte[] passwordBytes = md.digest();
		            
		StringBuilder passwordStringBuilder = new StringBuilder();
		for(int i=0; i<passwordBytes.length; i++)
		{
			passwordStringBuilder.append(Integer.toString((passwordBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		hashedPassword = passwordStringBuilder.toString();
		
		md.update(salt.getBytes());
		byte[] saltBytes = md.digest();
		
		StringBuilder saltStringBuilder = new StringBuilder();
		for(int i=0; i<saltBytes.length; i++)
		{
			saltStringBuilder.append(Integer.toString((saltBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		hashedSalt = saltStringBuilder.toString();
		
		md.update((hashedPassword+hashedSalt).getBytes());
		byte[] saltedPasswordBytes = md.digest();
		
		StringBuilder saltedPasswordStringBuilder = new StringBuilder();
		for(int i=0; i<saltedPasswordBytes.length; i++)
		{
			saltedPasswordStringBuilder.append(Integer.toString((saltedPasswordBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		hashedSaltedPassword = saltedPasswordStringBuilder.toString();
		
		return hashedSaltedPassword;
	}
	
}
