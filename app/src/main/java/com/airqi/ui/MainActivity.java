package com.airqi.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.airqi.R;
import com.airqi.data.AqiModel;
import com.airqi.databinding.ActivityMainBinding;
import com.airqi.utils.AppUtility;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

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
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setAqiViewModel(mViewModel);
        binding.citiesList.setLayoutManager(new LinearLayoutManager(this));
        binding.infoBtn.setOnClickListener(view ->
                new MaterialAlertDialogBuilder(MainActivity.this).setView(R.layout.aqi_info).show());
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
        if(!AppUtility.isInternetAvailable(this)) {
            Snackbar.make(findViewById(R.id.citiesList), getString(R.string.no_connection),
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, view -> {
                    }).show();
        }
        else {
            mViewModel.startSocket();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewModel.stopSocket();
    }
}