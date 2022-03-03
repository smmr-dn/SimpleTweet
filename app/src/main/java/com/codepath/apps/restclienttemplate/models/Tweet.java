package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
public class Tweet {

    @PrimaryKey
    @ColumnInfo
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    public long userId;

    @Ignore
    public User user;

    @Ignore
    public ArrayList<String> photoURL = new ArrayList<>();

    //Empty constructor needed by the Parceler library
    public Tweet(){ }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");

        User user = User.fromJson(jsonObject.getJSONObject("user"));

        tweet.user = user;
        tweet.userId = user.id;

        try{
            JSONArray medias = jsonObject.getJSONObject("entities").getJSONArray("media");

            if(medias.length() == 0) tweet.photoURL = new ArrayList<>();
            else {
                for (int i = 0; i < medias.length(); ++i) {
                    JSONObject media = medias.getJSONObject(i);
                    if (media.getString("type").equals("photo")) {
                        tweet.photoURL.add(media.getString("media_url_https"));
                    }
                }
            }
        }
        catch (JSONException e){
            tweet.photoURL = new ArrayList<>();
        }

        return tweet;
    }
    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); ++i){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getFormattedTimestamp(){
        return TimeFormatter.getTimeDifference(createdAt);
    }
}
