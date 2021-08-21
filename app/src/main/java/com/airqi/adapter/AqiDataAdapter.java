package com.airqi.adapter;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.airqi.R;
import com.airqi.data.AqiModel;
import com.airqi.databinding.ItemCityBinding;
import com.airqi.ui.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class AqiDataAdapter extends RecyclerView.Adapter<AqiDataAdapter.AqiDataViewHolder> {

    private final MainActivityViewModel viewModel;
    List<AqiModel> aqiList = new ArrayList<>();

    public AqiDataAdapter(MainActivityViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public AqiDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_city, parent, false);
        return new AqiDataViewHolder(binding, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull AqiDataViewHolder holder, int position) {
        AqiModel aqiModel = aqiList.get(position);
        holder.setRowDetails(aqiModel);
    }

    @Override
    public int getItemCount() {
        return aqiList.size();
    }

    public void setData(List<AqiModel> aqiModel) {
        aqiList.clear();
        aqiList.addAll(aqiModel);
        notifyDataSetChanged();
    }

    public static class AqiDataViewHolder extends RecyclerView.ViewHolder {
        private final MainActivityViewModel viewModel;
        ItemCityBinding mItemViewBinding;
        public AqiDataViewHolder(ItemCityBinding itemViewBinding, MainActivityViewModel viewModel) {
            super(itemViewBinding.getRoot());
            mItemViewBinding = itemViewBinding;
            this.viewModel = viewModel;
        }

        public void setRowDetails(AqiModel aqiModel) {
            mItemViewBinding.setAqiDataModel(aqiModel);
            mItemViewBinding.setViewModel(viewModel);
        }
    }
}
