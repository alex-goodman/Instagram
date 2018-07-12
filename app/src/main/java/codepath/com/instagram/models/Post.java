package codepath.com.instagram.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@ParseClassName("Post")
public class Post extends ParseObject {
    private static final String KEY_CAPTION = "caption";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USER = "user";

    public String getCaption() {
        return getString(KEY_CAPTION);
    }

    public void setCaption(String caption) {
        put(KEY_CAPTION, caption);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getDate() {
        return getRelativeTimeAgo(getCreatedAt());
    }

    public static class Query extends ParseQuery<Post> {
        public Query() {
            super(Post.class);
        }

        public Query getTop() {
            setLimit(20);
            return this;
        }

        public Query withUser() {
            include("user");
            return this;
        }
    }

    // from Jerome Jaglale at http://jeromejaglale.com/doc/java/twitter
    public static String getRelativeTimeAgo(Date createdAt) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);
        long dateMillis;


        dateMillis = createdAt.getTime();
        Date today = new Date();
        long duration = today.getTime() - dateMillis;
        int second = 1000;
        int minute = 60 * second;
        int hour = minute * 60;
        int day = hour * 24;

        if (duration < second * 7) return "now";
        else if (duration < minute) {
            int n = (int) Math.floor(duration / second);
            return n + "s";
        }
        else if (duration < 2 * minute) return "1m";
        else if (duration < hour) {
            int n = (int) Math.floor(duration / minute);
            return n + "m";
        }
        else if (duration < hour * 2) {
            return "1h";
        }
        else if (duration < day) {
            int n = (int) Math.floor(duration / hour);
            return n + "h";
        }
        else if (duration > day && duration < 2 * day) {
            return "yesterday";
        }
        else {
            int n = (int) Math.floor(duration / day);
            return n + "d";
        }
    }

}
