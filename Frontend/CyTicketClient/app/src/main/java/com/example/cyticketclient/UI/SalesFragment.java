package com.example.cyticketclient.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.android.volley.VolleyError;
import com.example.cyticketclient.R;
import com.example.cyticketclient.app.AppController;
import com.example.cyticketclient.data.DatabaseAdapter;
import com.example.cyticketclient.data.PostObject;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.logic.PostAdapter;
import com.example.cyticketclient.logic.SaleAdapter;
import com.example.cyticketclient.net_utils.Const;
import com.example.cyticketclient.networking.ServerCall;
import com.example.cyticketclient.interfaces.IDatabaseListener;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for displaying the Sales Fragment.
 * @author Logan Kroeze
 */
public class SalesFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, IDatabaseListener {

    private View view;
    private ListView saleList;
    private List<PostObject> sales;
    private DatabaseAdapter data;
    private ProgressDialog pDialog;
    private String UUID;

    /**
     * SalesFragment constructor.
     */
    public SalesFragment() {}

    /**
     * Load Sales Fragment.
     * @param savedInstanceState Sales Bundle.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Displays Sales.
     * @param inflater Sales LayoutInflater
     * @param container Sales ViewGroup
     * @param savedInstanceState Sales Bundle.
     * @return Sales View.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sales, container, false);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        saleList = (ListView) view.findViewById(R.id.saleList);
        Button refreshPostBtn = view.findViewById(R.id.refreshPostBtn);
        data = new DatabaseAdapter(getActivity());

        refreshPostBtn.setOnClickListener(this);

        // Remove all forum posts not created by this user.
        sales = data.getStoredPosts();
        List<PostObject> temp = new ArrayList<PostObject>();
        UUID = AppController.getInstance().getUser().getUUID();
        int index = 0;
        while(index < sales.size())
        {
            if(sales.get(index).getAuthorId().toString().equals(UUID))
            {
                temp.add(sales.get(index));
            }
            index++;
        }
        sales = temp;

        // todo getActivity might be null
        SaleAdapter saleAdapter = new SaleAdapter(getActivity(), sales);
        saleList.setAdapter(saleAdapter);

        saleList.setOnItemClickListener(this);

        return view;
    }

    /**
     * Refreshes Sales Fragment.
     * @param view Sales View.
     */
    @Override
    public void onClick(View view) {
        pDialog.show();
        data.clear();
        data.refreshDatabase(this);
    }

    /**
     * Displays a Specific Sale when clicked.
     * @param adapterView Sales AdapterView
     * @param view Sales View
     * @param i Sales index.
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent showSpecificPost = new Intent(getActivity(), SpecificPost.class);
        showSpecificPost.putExtra("com.example.cyticketclient.AuthorID", sales.get(i).getAuthorId());
        showSpecificPost.putExtra("com.example.cyticketclient.PostBody", sales.get(i).getBody());
        showSpecificPost.putExtra("com.example.cyticketclient.PostTitle", sales.get(i).getTitle());
        showSpecificPost.putExtra("com.example.cyticketclient.PostID", sales.get(i).getPostId());
        startActivity(showSpecificPost);
    }

    @Override
    public void onPostDataSuccess(JSONArray response) {
        sales = inverseList(data.getStoredPosts());
        SaleAdapter saleAdapter = new SaleAdapter(getActivity(),sales);
        saleList.setAdapter(saleAdapter);
        pDialog.hide();
    }

    @Override
    public void onRatingDataSuccess(JSONArray response) {
    }

    @Override
    public void onDataFail(VolleyError error) {
        pDialog.hide();
    }

    private List<PostObject> inverseList(List<PostObject> list){
        List<PostObject> returnList = new ArrayList<PostObject>();
        int listSize = list.size()-1;
        if(listSize < 0)
        {
            return list;
        }
        for(int i= listSize ;i >= 0;i--)
        {
            returnList.add(list.get(i));
        }
        return returnList;
    }
}