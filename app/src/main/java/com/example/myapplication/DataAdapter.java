package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.viewholder.AdsViewHolder;
import com.example.myapplication.viewholder.DataViewHolder;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdsManager;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Object> dataModelList;
    private NativeAdsManager nativeAdsManager;
    private List<NativeAd> nativeAdList = new ArrayList<>();
    private static int TYPE_AD = 1;
    private static int TYPE_ITEM = 0;
    private int FIRST_ADS_ITEM_POSITION = 5;
    private int ADS_FREQUENCY = 6;

    public DataAdapter(Context context, List<Object> dataModelList, int ListSize) {
        this.context = context;
        this.dataModelList = dataModelList;
        int no_of_ad_request = ListSize / (ADS_FREQUENCY - 1);
        nativeAdsManager = new NativeAdsManager(context, context.getString(R.string.fb_native), no_of_ad_request);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_AD) {
            return new AdsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.native_ads_layout, parent, false));
        } else {
            return new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_item, parent, false));
        }
    }

    public void initNativeAds() {
        nativeAdsManager.setListener(new NativeAdsManager.Listener() {
            @Override
            public void onAdsLoaded() {
                int count = nativeAdsManager.getUniqueNativeAdCount();
                for (int i = 0; i < count; i++) {
                    NativeAd ad = nativeAdsManager.nextNativeAd();
                    addNativeAds(i, ad);
                }
            }

            @Override
            public void onAdError(AdError adError) {
                Toast.makeText(context, "Ads Load Failed : Error Code - " + adError.getErrorCode() + "-" + adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        nativeAdsManager.loadAds();
    }

    public void addNativeAds(int i, NativeAd ad) {

        if (ad == null) {
            return;
        }
        if (this.nativeAdList.size() > i && this.nativeAdList.get(i) != null) {
            this.nativeAdList.get(i).unregisterView();
            this.dataModelList.remove(FIRST_ADS_ITEM_POSITION + (i * ADS_FREQUENCY));
            this.nativeAdList = null;
            this.notifyDataSetChanged();
        }
        this.nativeAdList.add(i, ad);
        if (dataModelList.size() > FIRST_ADS_ITEM_POSITION + (i * ADS_FREQUENCY)) {
            dataModelList.add(FIRST_ADS_ITEM_POSITION + (i * ADS_FREQUENCY), ad);
            notifyItemInserted(FIRST_ADS_ITEM_POSITION + (i * ADS_FREQUENCY));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof DataViewHolder) {
            DataModel dataModel = (DataModel) dataModelList.get(position);
            DataViewHolder dataViewHolder = (DataViewHolder) holder;
            dataViewHolder.title.setText(dataModel.getTitle());
            dataViewHolder.description.setText(dataModel.getDescription());
        } else {
            NativeAd nativeAd = (NativeAd) dataModelList.get(position);
            AdsViewHolder adsViewHolder = (AdsViewHolder) holder;
            // Set the Text.
            adsViewHolder.nativeAdTitle.setText(nativeAd.getAdvertiserName());
            adsViewHolder.nativeAdBody.setText(nativeAd.getAdBodyText());
            adsViewHolder.nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
            adsViewHolder.nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
            adsViewHolder.nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
            adsViewHolder.sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

            List<View> clickableViews = new ArrayList<>();
            clickableViews.add(adsViewHolder.nativeAdTitle);
            clickableViews.add(adsViewHolder.nativeAdCallToAction);

            // Register the Title and CTA button to listen for clicks.
            nativeAd.registerViewForInteraction(
                    adsViewHolder.itemView, adsViewHolder.nativeAdMedia, adsViewHolder.nativeAdIcon, clickableViews);

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (dataModelList.get(position) instanceof NativeAd) {
            return TYPE_AD;
        } else
            return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }
}
