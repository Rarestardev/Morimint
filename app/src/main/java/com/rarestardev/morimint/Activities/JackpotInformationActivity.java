package com.rarestardev.morimint.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.rarestardev.morimint.Adapters.InformationJackpotAdapter;
import com.rarestardev.morimint.Constants.JackpotValues;
import com.rarestardev.morimint.OfflineModel.InformationJackpotModel;
import com.rarestardev.morimint.R;

import java.util.ArrayList;
import java.util.List;

public class JackpotInformationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<InformationJackpotModel> jackpotModels = new ArrayList<>();

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackpot_information);
        recyclerView = findViewById(R.id.recyclerView);


        String jackpot_icon_desc1 = "The prize received for this icon is equal to : 5M MoriBit";
        String jackpot_icon_desc2 = "The prize received for this icon is equal to : 1M MoriBit";
        String jackpot_icon_desc3 = "The prize received for this icon is equal to : 500K MoriBit";
        String jackpot_icon_desc4 = "The prize received for this icon is equal to : 100K MoriBit";
        String jackpot_icon_desc5 = "The prize received for this icon is equal to : 5K MoriBit";
        String jackpot_icon_desc6 = "The prize received for this icon is equal to : 1K MoriBit";


        jackpotModels.add(new InformationJackpotModel(jackpot_icon_desc1,JackpotValues.JACKPOT_ICON_ON[0]));
        jackpotModels.add(new InformationJackpotModel(jackpot_icon_desc2,JackpotValues.JACKPOT_ICON_ON[1]));
        jackpotModels.add(new InformationJackpotModel(jackpot_icon_desc3,JackpotValues.JACKPOT_ICON_ON[2]));
        jackpotModels.add(new InformationJackpotModel(jackpot_icon_desc4,JackpotValues.JACKPOT_ICON_ON[3]));
        jackpotModels.add(new InformationJackpotModel(jackpot_icon_desc5,JackpotValues.JACKPOT_ICON_ON[4]));
        jackpotModels.add(new InformationJackpotModel(jackpot_icon_desc6,JackpotValues.JACKPOT_ICON_ON[5]));

        InformationJackpotAdapter jackpotAdapter = new InformationJackpotAdapter(jackpotModels,this);




        recyclerView.setAdapter(jackpotAdapter);
        recyclerView.refreshDrawableState();
        recyclerView.bringToFront();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}