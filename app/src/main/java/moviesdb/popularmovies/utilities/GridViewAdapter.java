package moviesdb.popularmovies.utilities;

import android.content.*;
import android.view.*;
import android.widget.*;

import com.squareup.picasso.*;

import android.app.*;
import android.text.*;

import java.util.ArrayList;

import moviesdb.popularmovies.R;

public class GridViewAdapter extends ArrayAdapter<GridItem> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();

    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<GridItem> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
        this.mGridData = mGridData;
    }

    public void setGridData(ArrayList<GridItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final ViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
            holder.progressBar = (ProgressBar) row.findViewById(R.id.loader);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        GridItem item = mGridData.get(position);
        Picasso.with(mContext).load(item.getImage()).placeholder(R.mipmap.images).into(holder.imageView,new Callback() {
            @Override
            public void onSuccess() {
              holder.progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {
                // TODO Auto-generated method stub

            }
        });
        return row;
    }

    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }
}