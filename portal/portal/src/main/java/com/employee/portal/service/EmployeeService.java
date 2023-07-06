package com.employee.portal.service;

import com.employee.portal.model.*;
import com.employee.portal.repository.EmployeeRepository;
import com.employee.portal.repository.entity.TEmployee;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@Service
//@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private static final String AES_ALGORITHM = "AES";

    @Transactional
    public EmployeeSignUpResponse signUpService(EmployeeData model) throws ParseException {

        LOGGER.info("Entering signUpService");
        EmployeeSignUpResponse response = new EmployeeSignUpResponse();
        LOGGER.info("Password from ui :" + model.getPassword());
        LOGGER.info("Decrypted password :" + decryptPassword(model.getPassword()));
        model.setPassword(decryptPassword(model.getPassword()));

        ValidationMsg msg = validatePassword(model);
        if (msg.isValid()) {
            msg = validateUserName(model);
            if (msg.isValid()) {

                TEmployee employee = new TEmployee();
                employee.setFirstName(model.getFirstName());
                employee.setLastName(model.getLastName());
                employee.setUserName(model.getUserName());
                employee.setEmail(model.getEmail());
                employee.setPassword(encodePassword(model.getPassword()));
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                Date date = sdf.parse(model.getDob());
                employee.setDob(date);
                employeeRepository.saveAndFlush(employee);
                response.setMessage("Employee added successfully!");
            } else {
                response.setErrorMessage(msg);
                return response;
            }
        } else {
            response.setErrorMessage(msg);
            return response;
        }
        return response;
    }

    ValidationMsg validatePassword(EmployeeData model) {
        LOGGER.info("Validating the password");
        ValidationMsg msg = new ValidationMsg();
        if (model.getPassword().length() < 8 || model.getPassword().length() > 20) {
            msg.setMessage("Password length should be between 8 and 20 characters");
            msg.setValid(false);
            return msg;
        }
        if (!model.getPassword().matches(".*[A-Z].*")
                || !model.getPassword().matches(".*[a-z].*")
                || !model.getPassword().matches(".*\\d.*")
                || !model.getPassword().matches(".*[!@#$%^&*()].*")) {
            msg.setMessage("Password should contain an uppercase,  a lowercase, a number, and a symbol");
            msg.setValid(false);
            return msg;
        }

        if (model.getPassword().matches(".*\\d{4,}.*")) {
            msg.setMessage("Password should not contain repeated numbers for more than 3");
            msg.setValid(false);
            return msg;
        }

        if (model.getPassword().contains(model.getUserName())) {
            msg.setMessage("Password should not contain the username");
            msg.setValid(false);
            return msg;
        }

        if (model.getPassword().matches(".*\\d{3,}.*")) {
            msg.setMessage("Password should not contain sequence of numbers");
            msg.setValid(false);
            return msg;
        }

        String fullName = model.getFirstName() + model.getLastName();
        if (model.getPassword().contains(fullName) || model.getPassword()
                .contains(model.getLastName() + model.getFirstName())) {
            msg.setMessage("Password should not be a combination of first name and last name");
            msg.setValid(false);
            return msg;
        }
        msg.setValid(true);
        return msg;
    }

    private ValidationMsg validateUserName(EmployeeData model) {
        LOGGER.info("Validating the userName");
        ValidationMsg msg = new ValidationMsg();
        TEmployee tEmployee = employeeRepository.findByUserName(model.getUserName());
        if (null != tEmployee) {
            msg.setMessage("An Employee with the same name already exists!");
            msg.setValid(false);
            return msg;
        }

        if (model.getUserName().equals(model.getPassword())) {
            msg.setMessage("The username and email cannot be same");
            msg.setValid(false);
            return msg;
        }

        msg.setValid(true);
        return msg;
    }

    public EmployeeLoginResponse login(EmployeeSignInInfo model) {
        LOGGER.info("Entering login service");
        EmployeeLoginResponse response = new EmployeeLoginResponse();
        TEmployee employee = employeeRepository.findByUserName(model.getUserName());
        if (null != employee) {
            if (verifyPassword(decryptPassword(model.getPassword()), employee.getPassword())) {
                response.setMessage("Logged In");
                response.setValid(true);
            } else {
                response.setMessage("Incorrect Password");
                response.setValid(false);
            }
            return response;
        }
        response.setMessage("User not found");
        response.setValid(false);
        return response;
    }

    // Method to encode a password using bcrypt
    public static String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    // Method to verify if a provided password matches a hashed password
    public static boolean verifyPassword(String password, String hashedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hashedPassword);
    }

    // Method to decrypt an encrypted password using AES
    public static String decryptPassword(String encryptedPassword) {
        try {
            SecretKeySpec secretKey = getSecretKeySpec();
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to generate a secret key spec for AES encryption/decryption
    private static SecretKeySpec getSecretKeySpec() throws Exception {
        // Provide your own secret key here (must be a valid length: 16, 24, or 32 bytes)

        // Provide your own secret key here (must be a valid length: 16, 24, or 32 bytes)
        String secretKeyString = "mySecretKey123456789";
        byte[] secretKeyBytes = secretKeyString.getBytes(StandardCharsets.UTF_8);

        // Use a hashing algorithm to generate a valid key length
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        secretKeyBytes = sha.digest(secretKeyBytes);
        secretKeyBytes = Arrays.copyOf(secretKeyBytes, 16); // Use only the first 16 bytes for AES-128

        return new SecretKeySpec(secretKeyBytes, AES_ALGORITHM);
    }

}
