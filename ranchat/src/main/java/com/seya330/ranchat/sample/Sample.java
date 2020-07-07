package com.seya330.ranchat.sample;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seya330.ranchat.user.bean.RegUserBean;
import com.seya330.ranchat.user.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Sample {

	public static void main(String[] args) throws JsonProcessingException {
		String aaa = "SEYA330R@NCH@TKEY";
		// TODO Auto-generated method stub
		Date createdDate = new Date();
        Date expirationDate = new Date(createdDate.getTime() + 604800 * 1000);

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userId", "seya330");
        claims.put("uniqId", "12345");
        String token = Jwts.builder()
            .setClaims(claims)
            .setIssuer("seya330.ranchat.com")
            .setSubject("12345")
            .setAudience("WEB")
            .setIssuedAt(createdDate)
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, aaa.getBytes())
            .compact();
        
        System.out.println(token);
        
        
        Claims claim = Jwts.parser().setSigningKey(aaa.getBytes()).parseClaimsJws(token).getBody();
        System.out.println(claim.get("userId", String.class));
        
        
	}

}
