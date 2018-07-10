package codepath.com.instagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import codepath.com.instagram.models.Post;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // register Post model
        ParseObject.registerSubclass(Post.class);

        // debug logging
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

        // Use for monitoring Parse OkHttp traffic
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // initialize Parse SDK
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.appId))
                .clientKey(null)
                .clientBuilder(builder)
                .server(getString(R.string.serverURL)).build());
    }
}
