package com.example.myapplication.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.facebook.ads.MediaView;

public class AdsViewHolder extends RecyclerView.ViewHolder {

    public MediaView nativeAdIcon;
    public TextView nativeAdTitle;
    public MediaView nativeAdMedia;
    public TextView nativeAdSocialContext;
    public TextView nativeAdBody;
    public TextView sponsoredLabel;
    public Button nativeAdCallToAction;

    public AdsViewHolder(@NonNull View itemView) {
        super(itemView);

        nativeAdIcon = itemView.findViewById(R.id.native_ad_icon);
        nativeAdTitle = itemView.findViewById(R.id.native_ad_title);
        nativeAdMedia = itemView.findViewById(R.id.native_ad_media);
        nativeAdSocialContext = itemView.findViewById(R.id.native_ad_social_context);
        nativeAdBody = itemView.findViewById(R.id.native_ad_body);
        sponsoredLabel = itemView.findViewById(R.id.native_ad_sponsored_label);
        nativeAdCallToAction = itemView.findViewById(R.id.native_ad_call_to_action);

    }
}
