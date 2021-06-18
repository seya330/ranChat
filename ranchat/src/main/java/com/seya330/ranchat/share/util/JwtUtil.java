package com.seya330.ranchat.share.util;

import com.seya330.ranchat.core.user.vo.RegUserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

@Component("JwtUtil")
public class JwtUtil {
  public String generateToken(Map<String, Object> claims, String subject, String audience) {
    Date createdDate = new Date();
    Date expirationDate = calculateExpirationDate(createdDate);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuer("amyutok.io")
        .setSubject(subject)
        .setAudience(audience)
        .setIssuedAt(createdDate)
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS256, "SEYA330R@NCH@TKEY".getBytes())
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
    return this.generateToken(claims, "userAuth", "WEB");
  }

  public RegUserVO getUserByToken(String token) {
    RegUserVO vo = convertMapToUser(verifyToken(token));
    return vo;
  }

  public Map<String, Object> verifyToken(String token) {
    Map<String, Object> claimMap = null;
    Claims claims = Jwts.parser()
        .setSigningKey("SEYA330R@NCH@TKEY".getBytes()) // Set Key
        .parseClaimsJws(token) // 파싱 및 검증, 실패 시 에러
        .getBody();

    claimMap = claims;
    return claimMap;
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
    Date date = new Date(createdDate.getTime() + 1800 * 1000);
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
    System.out.println(format.format(date));
    return date;
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

  private RegUserVO convertMapToUser(Map<String, Object> map) {
    RegUserVO vo = new RegUserVO();
    String keyAttribute = null;
    String setMethodString = "set";
    String methodString = null;
    Iterator itr = map.keySet().iterator();

    while (itr.hasNext()) {
      keyAttribute = (String) itr.next();
      methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
      Method[] methods = vo.getClass().getDeclaredMethods();
      for (int i = 0; i < methods.length; i++) {
        if (methodString.equals(methods[i].getName())) {
          try {
            methods[i].invoke(vo, map.get(keyAttribute));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }

    return vo;
  }

}
