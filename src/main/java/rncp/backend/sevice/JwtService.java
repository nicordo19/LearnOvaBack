package rncp.backend.sevice;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rncp.backend.entity.User;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {

    @Value("${jwt.secret}")
private  String SECRET_KEY ;


private Key getSecretKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        //transformer la clée en objet Key
};

public String generateToken(String email) {
// creation de token pour un utilisateur
    return Jwts.builder()
            .setSubject(email) // mettre l'email dans le token
            .setIssuedAt(new Date()) // date de creation du token
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // temps de vie d'u token
            .signWith(getSecretKey())// signer le token pour verifier qu'il n'est pas deja utilisé
            .compact() ; // creation du token final
}


public String extractEmail(String token){
    return Jwts.parserBuilder()

            .setSigningKey(getSecretKey()) // clé secrete a verifier
            .build()// l'objet a builder
            .parseClaimsJws(token)// analise les donné/contenu (claims) du token
            .getBody()// recuper les contenue dans le token
            .getSubject();// recuper l'email dans le token (l'utilisateur)
            //je prent le token
            // je verifie avec la clée
            // je l'ouvre
            // je recuper l'email (l'utilisateur)
}

    public boolean isTokenValid(String token, User user) {
        // on lit le token et on extrait l'email dedans
        final String extractedEmail = extractEmail(token);
        // on retourne l'email et le token valide
        return (extractedEmail.equals(user.getEmail()) && !isTokenExpired(token));

    }
// verification que le token soit expiré ou non
    public boolean isTokenExpired(String token){
    Date expiration = Jwts.parserBuilder()// la date d'expiation
            .setSigningKey(getSecretKey()) // on verifie que le token est bien assigner avec la key
            .build() // on lit les information de l'utilisateur
            .parseClaimsJws(token)// on verifi les donnée de l'utilisateur par les claimes
            .getBody() // on regarde les donné dans les claims
            .getExpiration(); // on regarde si il est expiré

        return expiration.before(new Date()); // si expiré on retourne une nouvelle date  
    }

}
