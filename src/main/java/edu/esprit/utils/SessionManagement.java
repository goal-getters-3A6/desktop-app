package edu.esprit.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;
public class SessionManagement {

    private static final String path = System.getProperty("user.home") + "/.go-fit.json";

    public static boolean checkFile() {
        File f = new File(path);
        if (f.exists() && f.isFile()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(path));
                StringBuilder sb = new StringBuilder();
                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                br.close();
                JSONObject json = new JSONObject(sb.toString());

                return json.has("email") && json.has("password");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static String getEmail() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            br.close();
            JSONObject json = new JSONObject(sb.toString());

            return json.get("email").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getPassword() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            br.close();
            JSONObject json = new JSONObject(sb.toString());

            return json.get("password").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveSession(String email, String password) {
        String json = new JSONObject()
                .put("email", email)
                .put("password", password)
                .toString();
        try {
            FileWriter fw = new FileWriter(path, false);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveSession(String email) {
        String json = new JSONObject()
                .put("email", email)
                .put("password", getPassword())
                .toString();
        try {
            FileWriter fw = new FileWriter(path, false);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
