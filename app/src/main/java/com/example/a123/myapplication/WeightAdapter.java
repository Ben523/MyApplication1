package com.example.a123.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WeightAdapter extends ArrayAdapter<Weight>  {
    List<Weight> weights = new ArrayList<Weight>();
    Context context;

    public WeightAdapter(Context context, int resouce, List<Weight> objects){
        super(context, resouce, objects);
        this.weights = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {

        View _weightItem = LayoutInflater.from(context).inflate(
                R.layout.fragment_custom_weight_list,
                parent,
                false);

        TextView _date = _weightItem.findViewById(R.id.custom_date);
        TextView _weight = _weightItem.findViewById(R.id.custom_weight);
        TextView _status = _weightItem.findViewById(R.id.custom_status);

        Weight _row = weights.get(position);
        _date.setText(_row.getDate());
        _weight.setText(String.valueOf( _row.getWeight() )+" Kg");
        _status.setText(_row.getStatus());

        return _weightItem;
    }
}
