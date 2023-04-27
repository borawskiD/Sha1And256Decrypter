package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Task {
    public void run() {
        exerciseOne();
        exerciseThree();
    }

    public void exerciseThree() {
        try {
            ArrayList<String> hashes = getInput("hashes");
            ArrayList<String> names = getInput("names");
            while (!hashes.isEmpty()) {
                for (String name : names) {
                    for (int i = 1900; i <= 2000; i++) {
                        if (hashes.contains(encodeSHA256(name + "" + i))) {
                            System.out.println(encodeSHA256(name + "" + i) + " : " + name + "" + i);
                            hashes.remove(encodeSHA256(name + "" + i));
                        }
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void analyze(int maxValue, ArrayList<String> output) {
        String value = "" + maxValue;
        for (int i = 0; i <= maxValue; i++) {
            String code = String.valueOf(i);
            if (code.length() < value.length()) {
                StringBuilder sb = new StringBuilder();
                while (sb.length() < value.length() - code.length()) {
                    sb.append('0');
                }
                sb.append(code);
                code = sb.toString();
            }
            if (output.contains(encodeSha(code))) {
                System.out.println(output.get(output.indexOf(encodeSha(code))) + ":" + code);
                output.remove(encodeSha(code));
            }
        }
    }

    public void exerciseOne() {
        ArrayList<String> output = getInput("pins");
        analyze(9999, output);
        analyze(99999, output);
        analyze(999999, output);
    }

    public String encodeSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public String encodeSha(String value) {
        String sha1 = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(value.getBytes("utf8"));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sha1;
    }

    public ArrayList<String> getInput(String fileName) {
        BufferedReader reader;
        ArrayList<String> input = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileName + ".txt"));
            String line = reader.readLine();
            while (line != null) {
                input.add(line);
                // read next lined
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }
}
