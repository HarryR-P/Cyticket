package com.kk08.robbieexperiments.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kk08.robbieexperiments.ForumActivity;
import com.kk08.robbieexperiments.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuyTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyTab extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView dataTextView;
    private View view;

    private String mParam1;
    private String mParam2;

    public BuyTab() {

    }

    public BuyTab(View view) {
        this.view = view;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuyTab.
     */
    // TODO: Rename and change types and number of parameters
    public static BuyTab newInstance(String param1, String param2) {
        BuyTab fragment = new BuyTab();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    // Happens every time you switch to the tab
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_tab, container, false);

        dataTextView = (TextView) view.findViewById(R.id.textViewData);

        int num = 5;

        String data = ForumActivity.viewData(view);

        dataTextView.setText(data);

        return view;
    }
}