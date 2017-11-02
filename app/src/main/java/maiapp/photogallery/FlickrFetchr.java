package maiapp.photogallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amai on 10/26/2017.
 */

public class FlickrFetchr {

    private static final String TAG = "FlickrFetchr";

    private static final String API_KEY = "49eaf32ab709779b453dc2281df43807";

    public byte[] getURLBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); //server is sending us data

            InputStream inputStream = httpURLConnection.getInputStream(); //getting input stream from out connection

            int bytesRead = 0;                                      //determines size of array
            byte[] buffer = new byte[1024];                         //reading the data from the buffer we made, reading 1024 bytes at a time.

            while ((bytesRead = inputStream.read(buffer)) > 0) {      //>0 so if we still have data to read.
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();                    //once we run out of data, return the data that we read

        } finally {                                                 //finally block runs everytime
            httpURLConnection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getURLBytes(urlSpec));
    }

    public List<GalleryItem> fetchItems() {

        List<GalleryItem> items = new ArrayList<>();

        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Recieved JSON " + jsonString);

            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }






    private void parseItems(List<GalleryItem>items, JSONObject jsonBody)
        throws  IOException, JSONException {

        JSONObject photoJsonObject = jsonBody.getJSONObject("photos");          //navigates JSONObject hierarchy
        JSONArray photoJsonArray = photoJsonObject.getJSONArray("photo");

        for (int i = 0; i < photoJsonArray.length(); i++){
            photoJsonObject = photoJsonArray.getJSONObject(i);

            GalleryItem item = new GalleryItem();
            item.setID(photoJsonObject.getString("id"));
            item.setCaption(photoJsonObject.getString("title"));

            if (!photoJsonObject.has("url_s")){
                continue;
            }

            item.setUrl(photoJsonObject.getString("url_s"));
            items.add(item);

        }
    }
}
