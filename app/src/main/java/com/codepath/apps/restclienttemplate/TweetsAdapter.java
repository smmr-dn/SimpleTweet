package com.codepath.apps.restclienttemplate;

import static android.text.TextUtils.isEmpty;

import android.content.Context;
import android.media.Image;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONException;

import java.util.List;

public class TweetsAdapter extends  RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    Context context;
    List<Tweet> tweets;
    String TAG = "TweetsAdapter";

    //Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //For each row, inflate a layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    //Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get the data at position
        Tweet tweet = tweets.get(position);

        //Bind the tweet at the view holder
        holder.bind(tweet);
    }


    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // Clean all elements of the recycler
    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> tweetList){
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    //Define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTime;
        TextView tvUserName;
        ImageView ivImageMedia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ivImageMedia = itemView.findViewById(R.id.ivImageMedia);
        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvTime.setText(tweet.getFormattedTimestamp());
            tvUserName.setText(tweet.user.name);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);

            try {
                Glide.with(context).load(tweet.photoURL.get(0)).transform(new RoundedCorners(50)).into(ivImageMedia);
            }catch(Exception e){}
        }


    }
}
