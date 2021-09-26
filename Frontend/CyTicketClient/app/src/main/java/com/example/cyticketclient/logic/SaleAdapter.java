package com.example.cyticketclient.logic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cyticketclient.R;
import com.example.cyticketclient.data.PostObject;

import java.util.List;

/**
 * Class for displaying Sales.
 * @author Logan Kroeze
 */
public class SaleAdapter extends BaseAdapter {

    private LayoutInflater saleInflater;
    private List<PostObject> sales;

    /**
     * SaleAdapter constructor.
     * @param c Sale Context
     * @param sales List of all sales.
     */
    public SaleAdapter(Context c, List<PostObject> sales)
    {
        this.sales = sales;
        saleInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Returns number of sales.
     * @return Number of sales.
     */
    @Override
    public int getCount() {
        return sales.size();
    }

    /**
     * Returns specific sale.
     * @param i Sale index.
     * @return Specific sale.
     */
    @Override
    public Object getItem(int i) {
        return sales.get(i);
    }

    /**
     * Returns sale ID.
     * @param i Sale index.
     * @return Specific sale ID.
     */
    @Override
    public long getItemId(int i) {
        return sales.get(i).getPostId();
    }

    /**
     * Returns Sale View.
     * @param i Sale index.
     * @param view Sale View.
     * @param viewGroup Sale ViewGroup.
     * @return Sale View.
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = saleInflater.inflate(R.layout.sale_layout, null);

        TextView description = (TextView) v.findViewById(R.id.description);
        TextView price = (TextView) v.findViewById(R.id.price);
        TextView title = (TextView) v.findViewById(R.id.postTitle);

        // Limiting the number of lines of the description to two
        if(sales.get(i).getBody().length() > 66 ) {
            description.setText(sales.get(i).getBody().substring(0, 66));
        }else
        {
            description.setText(sales.get(i).getBody());
        }

        // todo add prices
        price.setText(sales.get(i).getPrice());
        title.setText(sales.get(i).getTitle());

        return v;
    }

}
