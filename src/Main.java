
import io.jsonwebtoken.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class Main {
	
	private static String createJWT(String id, String issuer, String subject, long ttlMillis) {
	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	 
	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	 
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("supersecretkey");
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	 
	    JwtBuilder builder = Jwts.builder().setId(id)
	                                .setIssuedAt(now)
	                                .setSubject(subject)
	                                .setIssuer(issuer)
	                                .signWith(signatureAlgorithm, signingKey);
	    							
	 
	    if (ttlMillis >= 0) {
	    long expMillis = nowMillis + ttlMillis;
	        Date exp = new Date(expMillis);
	        builder.setExpiration(exp);
	    }
	 
	    return builder.compact();
	}
	
	private static void parseJWT(String jwt) {
	    Claims claims = Jwts.parser()         
	       .setSigningKey(DatatypeConverter.parseBase64Binary("supersecretkey"))
	       .parseClaimsJws(jwt).getBody();
	    System.out.println("ID: " + claims.getId());
	    System.out.println("Subject: " + claims.getSubject());
	    System.out.println("Issuer: " + claims.getIssuer());
	    System.out.println("Expiration: " + claims.getExpiration());
	}

	public static void main(String[] args) {
		
		String token = createJWT("10", "kietnguyen", "createoken", 3600000);
		System.out.println(token);
		
		try {
			parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMCIsImlhdCI6MTU4NDE1ODM2NCwic3ViIjoiY3JlYXRlb2tlbiIsImlzcyI6ImtpZXRuZ3V5ZW4iLCJleHAiOjE1ODQxNjE5NjR9.cgI1Oppigcj0j-ZnR7L70dcz9WS5WraU85SOaUx_oBw");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	

	}

}
