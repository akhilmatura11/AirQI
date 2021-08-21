package com.airqi.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airqi.R;
import com.airqi.data.AqiModel;
import com.airqi.databinding.FragmentHomeBinding;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private MainActivityViewModel mViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        setupViewModel();
        bindViews();
        return binding.getRoot();
    }

    private void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        mViewModel.getList().observe(requireActivity(), aqiModel -> {
            if(aqiModel!=null)
            {
                mViewModel.setDataInAdapter(aqiModel);
            }
        });
        mViewModel.getOnClickCity().observe(requireActivity(), onClick -> {
            if(onClick!=null){
                sendToCityFragment(onClick);
            }
        });
    }

    private void bindViews() {
        binding.setLifecycleOwner(this);
        binding.setAqiViewModel(mViewModel);
        binding.citiesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void sendToCityFragment(AqiModel aqiModel) {
        Bundle args = new Bundle();
        args.putString("cityName", aqiModel.getCityName());
        Navigation.findNavController(requireActivity()
                ,(R.id.fragment)).navigate(R.id.cityAqiGraphFragment, args);
       // Navigation.findNavController(binding.citiesList).navigate(R.id.cityAqiGraphFragment);
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.startSocket();
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewModel.stopSocket();
    }

}