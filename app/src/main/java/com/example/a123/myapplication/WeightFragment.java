package com.example.a123.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class WeightFragment extends Fragment {
    private ArrayList<Weight> weights = new ArrayList<>();
    private DBHelper mHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHelper = new DBHelper(getActivity());
        SharedPreferences sp = getContext().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        String user_id = sp.getString("userId", "");
        weights = mHelper.getWeightList(user_id);
        Collections.reverse(weights);
        ListView _weightList = getView().findViewById(R.id.weight_list);
        WeightAdapter weightAdapter = new WeightAdapter(
                getActivity(),
                R.layout.fragment_custom_weight_list,
                weights
        );
        _weightList.setAdapter(weightAdapter);
        addWeightBtn();
        backBtn();

    }
    private void addWeightBtn()
    {
        Button addWeight = getView().findViewById(R.id.weight_add);
        addWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view,new WeightFormFragment())
                        .commit();
            }
        });
    }

    private void backBtn()
    {
        Button back = getView().findViewById(R.id.weight_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view,new Menufragment())
                        .commit();
            }
        });
    }

}
