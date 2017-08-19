package moviesdb.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.*;


import static android.content.Context.MODE_PRIVATE;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.VideoInfoHolder> {


    String[] VideoID, Names, Category, Trailer, Clip, Teaser, Featurette;
    int count_trailer, count_clip, count_teaser, count_featurette;
    int temp_trailer, temp_clip, temp_teaser, temp_featurette;
    Context ctx;
    String Title;
    RecyclerView.RecyclerListener Listener;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    List<String> list;
    Object[] listall;

    public TrailerAdapter(Context context, String[] names, String[] ids, String[] cat, String title) {
        this.ctx = context;
        this.Names = names;
        this.VideoID = ids;
        this.Category = cat;
        this.Title = title;
        count_trailer = count_clip = count_teaser = count_featurette = 0;
        for (int i = 0; i < Category.length; i++) {
            String type = Category[i];
            switch (type) {
                case "Trailer":
                    count_trailer++;
                    break;
                case "Teaser":
                    count_teaser++;
                    break;
                case "Clip":
                    count_clip++;
                    break;
                case "Featurette":
                    count_featurette++;
                    break;
                default:
                    break;
            }
        }
        Trailer = new String[count_trailer];
        Clip = new String[count_clip];
        Teaser = new String[count_teaser];
        Featurette = new String[count_featurette];

        for (int i = 0; i < Category.length; i++) {
            String type = Category[i];
            switch (type) {
                case "Trailer":
                    Trailer[temp_trailer] = Names[i];
                    temp_trailer++;
                    break;
                case "Teaser":
                    Teaser[temp_teaser] = Names[i];
                    temp_teaser++;
                    break;
                case "Clip":
                    Clip[temp_clip] = Names[i];
                    temp_clip++;
                    break;
                case "Featurette":
                    Featurette[temp_featurette] = Names[i];
                    temp_featurette++;
                    break;
                default:
                    break;
            }
        }

        list = new ArrayList();
        list.addAll(Arrays.asList(Trailer));
        list.addAll(Arrays.asList(Teaser));
        list.addAll(Arrays.asList(Clip));
        list.addAll(Arrays.asList(Featurette));
        listall = list.toArray();
    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        parent.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
        parent.setBackground(parent.getResources().getDrawable(R.color.light_bluegrey));
        View view = new TextView(parent.getContext());
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setPadding(0, 7, 0, 7);
        VideoInfoHolder viewHolder = new VideoInfoHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {

        holder.textView.setText(Title + ":" + listall[position]);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = ctx.getSharedPreferences("pref", MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("video_key", VideoID[holder.getAdapterPosition()]);
                editor.commit();
                Intent i = new Intent(ctx, VideoPlayer.class);
                ctx.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listall.length;
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setTextSize(20);
            textView.setTextColor(Color.WHITE);
            textView.setClickable(true);
            textView.setFocusable(true);
            textView.setBackground(ctx.getResources().getDrawable(R.drawable.black));
        }
    }
}

