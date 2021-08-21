package com.airqi.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airqi.R;
import com.airqi.data.AqiModel;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        bindViews();
    }

    private void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mViewModel.getList().observe(this, aqiModel -> {
            if (aqiModel != null) {
                mViewModel.setDataInAdapter(aqiModel);
            }
        });
        mViewModel.getOnClickCity().observe(this, onClick -> {
            if (onClick != null) {
                sendToCityFragment(onClick);
            }
        });
    }

    private void bindViews() {
        com.airqi.databinding.ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setAqiViewModel(mViewModel);
        binding.citiesList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void sendToCityFragment(AqiModel aqiModel) {
        Bundle args = new Bundle();
        args.putString("cityName", aqiModel.getCityName());

        CityAqiGraphFragment fragment = CityAqiGraphFragment.newInstance(aqiModel.getCityName());
        fragment.show(getSupportFragmentManager(), "CityDetail");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.startSocket();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.stopSocket();
    }

}