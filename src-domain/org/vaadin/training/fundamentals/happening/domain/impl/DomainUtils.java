/* 
 * Copyright 2012 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.vaadin.training.fundamentals.happening.domain.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.UUID;

import org.vaadin.training.fundamentals.happening.domain.entity.DomainUser;
import org.vaadin.training.fundamentals.happening.domain.entity.Happening;
import org.vaadin.training.fundamentals.happening.ui.AppData;

public class DomainUtils {

    static String newRandomUUIDString() {
        return UUID.randomUUID().toString();
    }

    /**
     * Hashes secret and salt
     * 
     * @param secret
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    static String hash(String secret, String salt)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
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

    /**
     * Returns an byte array as lowercase hex string
     * 
     * @param bytes
     * @return
     */
    public static String toHexString(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "x", bi);
    }

    /**
     * Creates a new DomainUser with random password and salt.
     * 
     * @return
     */
    public static DomainUser createUser() {
        DomainUser user = new DomainUser();
        String salt = DomainUtils.newRandomUUIDString();
        String password = DomainUtils.newRandomUUIDString();
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
        return AppData.getDomain().find(
                "SELECT u FROM DomainUser u WHERE u.hash = :hash",
                Collections.singletonMap("hash", (Object) hash));
    }
}
