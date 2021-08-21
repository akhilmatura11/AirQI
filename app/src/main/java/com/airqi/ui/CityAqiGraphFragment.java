package com.airqi.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airqi.R;
import com.airqi.data.AqiModel;
import com.airqi.databinding.FragmentCityAqiGraphBinding;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityAqiGraphFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityAqiGraphFragment extends Fragment {

    private static final String ARG_CITY_NAME = "cityName";

    private String mCityName;
    private FragmentCityAqiGraphBinding binding;
    private CityAqiGraphViewModel mViewModel;
    private AqiModel mAqiModel;
    private List<AqiModel> mCityDataList;

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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_city_aqi_graph, container
        , false);
        setupViewModel();
        bindViews();
        return binding.getRoot();
    }

    private void setupViewModel() {
        mViewModel = new ViewModelProvider(this,
                new CityAqiGraphViewModelFactory(requireActivity().getApplication(), mCityName))
                .get(CityAqiGraphViewModel.class);
        mViewModel.getCurrentData().observe(getActivity(), current -> {
            if(current!=null){
                mAqiModel = current;
            }
        });
        mViewModel.getCityData().observe(getActivity(), cityData -> {
            if(cityData != null){
                mCityDataList = cityData;
                loadData();
            }
        });
    }

    private void bindViews() {
        binding.setLifecycleOwner(this);
        binding.setAqiModel(mAqiModel);

    }

    private void loadData(){
        Cartesian cartesian = AnyChart.line();

        cartesian.animation(true);

        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Trend of Sales of the Most Popular Products of ACME Corp.");

        cartesian.yAxis(0).title("Number of Bottles Sold (thousands)");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        for(AqiModel model:mCityDataList){
            seriesData.add(new ValueDataEntry(model.getTime(), Float.valueOf(model.getAqiValue())));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Brandy");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        binding.anyChartView.setChart(cartesian);
    }


}