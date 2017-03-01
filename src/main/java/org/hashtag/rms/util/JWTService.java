//package org.hashtag.rms.util;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//
///**
// * Created by Buddhi on 3/2/2017.
// */
//public class JWTService {
//
//    public static String createJWToken() {
//        try {
//            String jwt = Jwts.builder()
//                    .setSubject("users/TzMUocMF4p")
//                    .setExpiration(new Date(1300819380))
//                    .claim("name", "Robert Token Man")
//                    .claim("scope", "self groups/admins")
//                    .signWith(
//                            SignatureAlgorithm.HS256, "secret".getBytes("UTF-8")
//                    )
//                    .compact();
//
//            return jwt;
//        } catch (UnsupportedEncodingException e) {
//            return null;
//        }
//    }
//
//}
