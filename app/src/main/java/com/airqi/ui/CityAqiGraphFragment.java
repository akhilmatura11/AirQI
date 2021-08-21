package com.airqi.ui;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airqi.R;
import com.airqi.data.AqiModel;
import com.airqi.databinding.FragmentCityAqiGraphBinding;
import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityAqiGraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityAqiGraphFragment extends BottomSheetDialogFragment {

    private static final String ARG_CITY_NAME = "cityName";

    private String mCityName;
    private FragmentCityAqiGraphBinding binding;
    private CityAqiGraphViewModel mViewModel;
    private List<AqiModel> mCityDataList;
    private BottomSheetBehavior<View> bottomSheetBehavior;

    public CityAqiGraphFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cityName Parameter 1.
     * @return A new instance of fragment CityAqiGraphFragment.
     */
    public static CityAqiGraphFragment newInstance(String cityName) {
        CityAqiGraphFragment fragment = new CityAqiGraphFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY_NAME, cityName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCityName = getArguments().getString(ARG_CITY_NAME);
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //inflating layout
        View view = View.inflate(getContext(), R.layout.fragment_city_aqi_graph, null);

        binding = DataBindingUtil.bind(view);
        //setting layout with bottom sheet
        bottomSheet.setContentView(view);

        bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));


        //setting Peek at the 16:9 ratio keyline of its parent.
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_EXPANDED);


        //setting max height of bottom sheet
        view.setMinimumHeight((Resources.getSystem().getDisplayMetrics().heightPixels));

        setupViewModel();
        bindViews();
        return bottomSheet;
    }

    @Override
    public void onStart() {
        super.onStart();
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void setupViewModel() {
        mViewModel = new ViewModelProvider(this,
                new CityAqiGraphViewModelFactory(requireActivity().getApplication(), mCityName))
                .get(CityAqiGraphViewModel.class);
        mViewModel.getCurrentData().observe(requireActivity(), current -> {
            if (current != null) {
                binding.executePendingBindings();
            }
        });
        mViewModel.getCityData().observe(requireActivity(), cityData -> {
            if (cityData != null) {
                mCityDataList = cityData;
                loadData();
            }
        });
    }

    private void bindViews() {
        binding.setLifecycleOwner(this);
        binding.setViewModel(mViewModel);
        binding.anyChartView.setLayoutParams
                (new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        (int) (getResources().getDisplayMetrics().heightPixels * 0.8)));
    }

    private void loadData() {
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Air Quality Index for " + mCityDataList.get(0).getCityName());

        cartesian.yAxis(0).title("Air Quality Index");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        for (AqiModel model : mCityDataList) {
            seriesData.add(new ValueDataEntry(model.getTime(), Float.valueOf(model.getAqiValue())));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name(mCityDataList.get(0).getCityName());
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(0.5d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(1d)
                .offsetY(1d);

        binding.anyChartView.setChart(cartesian);
    }
}