package com.seya330.ranchat.user.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.seya330.ranchat.user.vo.RegUserVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component("JwtUtil")
public class JwtUtil {
	public String generateToken(Map<String, Object> claims, String subject, String audience) {
		Date createdDate = dateWithoutTime();
        Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("seya330.ranchat.com")
                .setSubject(subject)
                .setAudience(audience)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, "SEYA330R@NCH@TKEY")
                .compact();
	}
	
	public String getJwtDataFromKey(String token, String key) {
        Claims claims = getAllClaimsFromToken(token);

        if (claims != null) {
        	return claims.get(key, String.class);
        }

        return "";
	}
	
	
	public String userBeanToJwtToken(RegUserVO userBean) {
		HashMap<String, Object> claims = new HashMap<String, Object>();
		claims.put("uniqId", userBean.getUniqId());
		claims.put("userId", userBean.getUserId());
		return this.generateToken(claims, userBean.getUniqId(), "WEB");
	}
	
    private Date dateWithoutTime() {
        return removeTimeFromDate(Calendar.getInstance().getTime());
    }

    private Date removeTimeFromDate(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
    
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + 604800 * 1000);
    }
    
    private Claims getAllClaimsFromToken(String token) {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey("SEYA330R@NCH@TKEY")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }

        return claims;
    }
}
