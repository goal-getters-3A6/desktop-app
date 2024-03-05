package edu.esprit.utils;

import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;


import java.io.*;


public class UploadToDropBox {

    public static String uploadPhoto(String path, String name) throws FileNotFoundException {
        DbxRequestConfig config;
        config = new DbxRequestConfig("dropbox/go-fit-pro");
        DbxClientV2 client;
        DbxAuthInfo authInfo = null;
        try {
            authInfo = DbxAuthInfo.Reader.readFromFile("src/main/java/edu/esprit/utils/dropbox_auth.json");
        } catch (JsonReader.FileLoadException ex) {
            System.err.println("Error loading <auth-file>: " + ex.getMessage());
        }
        client = new DbxClientV2(config, authInfo.getAccessToken(), authInfo.getHost());
        InputStream in = new FileInputStream(path);
        try {
            FileMetadata metadata = client.files().uploadBuilder(name)
                    .uploadAndFinish(in);
            // get url of the uploaded file
            String url = client.files().getTemporaryLink(name).getLink();
            return url;

        } catch (IOException | DbxException e) {
            throw new RuntimeException(e);
        }
    }
}