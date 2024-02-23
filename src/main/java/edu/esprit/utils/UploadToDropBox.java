package edu.esprit.utils;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.DbxUserUsersRequests;
import com.dropbox.core.v2.users.FullAccount;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UploadToDropBox {

    private static final String ACCESS_TOKEN = "sl.BwJ39pqUF6AnarBdgvtrD4u6IatvTOqlYj9AtZQks9J4lgO5Yu1IrP8jNmELGfURq7CJmP8ba5vEnYsqqyd4nycIhH_L1gKRVRRD9v8EiP3gSeEfDzkYNFmrOoV19zzmZpnf6WGmixjQY1w";

    public static String uploadPhoto(String path, String name) {
        try {
            DbxRequestConfig config;
            config = new DbxRequestConfig("dropbox/go-fit-pro");
            DbxClientV2 client;
            client = new DbxClientV2(config, ACCESS_TOKEN);
            DbxUserUsersRequests r1 = client.users();
            // Get files and folder metadata from Dropbox root directory
            ListFolderResult result = client.files().listFolder("");
            while (true) {
                for (Metadata metadata : result.getEntries()) {
                    System.out.println(metadata.getPathLower());
                }

                if (!result.getHasMore()) {
                    break;
                }

                result = client.files().listFolderContinue(result.getCursor());
            }

        } catch (DbxException ex1) {
            ex1.printStackTrace();
        }

        DbxRequestConfig config;
        config = new DbxRequestConfig("dropbox/go-fit-pro");
        DbxClientV2 client;
        client = new DbxClientV2(config, ACCESS_TOKEN);
        try (
                InputStream in = new FileInputStream(path)) {

            FileMetadata metadata = client.files().uploadBuilder(name)
                    .uploadAndFinish(in);
        // get url of the uploaded file
            String url = client.files().getTemporaryLink(name).getLink();
            return url;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (UploadErrorException e) {
            e.printStackTrace();
            return null;
        } catch (DbxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
