package com.hrm.bookstore.service.impl;

import com.hrm.bookstore.service.DigestService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class DigestServiceImpl implements DigestService {
    @Override
    public String hash(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(password.getBytes());
            byte[] bytes = messageDigest.digest();
            BigInteger bigInteger = new BigInteger(1, bytes);
            return bigInteger.toString(16).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
