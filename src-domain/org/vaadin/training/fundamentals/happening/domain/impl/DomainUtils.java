package org.vaadin.training.fundamentals.happening.domain.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.UUID;

import org.vaadin.training.fundamentals.happening.domain.AppData;
import org.vaadin.training.fundamentals.happening.domain.entity.DomainUser;
import org.vaadin.training.fundamentals.happening.domain.entity.Happening;

public class DomainUtils {

    static String newRandomHexString() {
        return UUID.randomUUID().toString();
    }
    
    static String hash(String secret, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(secret.getBytes("UTF-8"));
        md.update(salt.getBytes("UTF-8"));
        byte[] hash = md.digest();
        return toHexString(hash);
    }
    
    static boolean isOwner(DomainUser user, Happening happening) {
        if (user != null && happening != null && happening.getOwner() != null) {
            return user.getId().equals(happening.getOwner().getId());
        }
        return false;
    }
    
    public static String toHexString(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "x", bi);
    }
    
    public static DomainUser createUser() {
        DomainUser user = new DomainUser();
        String salt = DomainUtils.newRandomHexString();
        String password = DomainUtils.newRandomHexString();
        user.setSalt(salt);
        try {
            user.setHash(hash(password, salt));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public static DomainUser auth(String hash) {
        return AppData.getDomain().find("SELECT u FROM DomainUser u WHERE u.hash = :hash", Collections.singletonMap("hash", (Object)hash));
    }
}
