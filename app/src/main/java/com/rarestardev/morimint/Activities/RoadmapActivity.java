package com.rarestardev.morimint.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rarestardev.morimint.Adapters.RoadmapAdapter;
import com.rarestardev.morimint.Constants.UserConstants;
import com.rarestardev.morimint.OfflineModel.RoadmapModel;
import com.rarestardev.morimint.R;
import com.rarestardev.morimint.databinding.ActivityRoadmapBinding;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RoadmapActivity extends AppCompatActivity {

    ActivityRoadmapBinding binding;
    private static final String ADS_TAG = "StartApp";

    RoadmapAdapter adapter;

    List<RoadmapModel> roadmapModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_roadmap);

        LoadAd();

        binding.tvRoadmap.setText(readFromAssets());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SetRoadmapItems();

    }



    private void LoadAd(){
        StartAppSDK.init(this, UserConstants.startAppId, true);
        StartAppSDK.setTestAdsEnabled(UserConstants.startAppIsTested);
        Banner startAppBanner = findViewById(R.id.startapp_banner);
        Log.d(ADS_TAG,startAppBanner + "");
    }

    private String readFromAssets() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = getAssets().open("roadmap.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    private void SetRoadmapItems(){
        roadmapModels.add(new RoadmapModel(true,1,"Design and development of the Morimint application."));
        roadmapModels.add(new RoadmapModel(true,2,"Design and development of the MORI Telegram mini app."));
        roadmapModels.add(new RoadmapModel(true,3,"Distribution of 1 million token gift codes before launch."));
        roadmapModels.add(new RoadmapModel(true,4,"Verification of Instagram page and Twitter channel."));
        roadmapModels.add(new RoadmapModel(true,5,"Deployment of the token and smart contract."));
        roadmapModels.add(new RoadmapModel(true,6,"Minting Start: The program (application and Telegram mini app bot) begins on August 10, 2024."));
        roadmapModels.add(new RoadmapModel(false,7,"Wallet Integration with the Application."));
        roadmapModels.add(new RoadmapModel(false,8,"Start of Cash Prize Draws During Minting."));
        roadmapModels.add(new RoadmapModel(false,9,"Start of NFT and Token Distribution in Mini App and Application."));
        roadmapModels.add(new RoadmapModel(false,10,"Start of NFT Sales in the Market."));
        roadmapModels.add(new RoadmapModel(false,11,"Creation of MORIBIT Token Trading Pool."));
        roadmapModels.add(new RoadmapModel(false,12,"Token Listing on Exchange."));
        roadmapModels.add(new RoadmapModel(false,13,"End of Token and NFT Minting."));
        roadmapModels.add(new RoadmapModel(false,14,"Start of MORI AI Watch Production."));
        roadmapModels.add(new RoadmapModel(false,15,"Launch of Moriflix Social Network Application."));
        roadmapModels.add(new RoadmapModel(false,16,"Start of New Phase of Morimint Application."));
        roadmapModels.add(new RoadmapModel(false,17,"To Be Continued..."));

        adapter = new RoadmapAdapter(this,roadmapModels);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.refreshDrawableState();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}