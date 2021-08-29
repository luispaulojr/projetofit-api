package br.com.senacrio.projetofitapi.config.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class GeneratedSecuredPassword {
        public static String hash(String password) {
            return BCrypt.hashpw(password, BCrypt.gensalt(12));
        }
}
