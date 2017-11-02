package maiapp.photogallery;

/**
 * Created by Andy on 10/31/2017.
 */

public class GalleryItem {

    private String mCaption;
    private String mID;
    private String mUrl;

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getID() {
        return mID;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setID(String ID) {
        mID = ID;

    }


    @Override
    public String toString(){
        return mCaption;
    }
}
