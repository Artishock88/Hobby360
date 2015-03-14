package de.artmedia.artyom.hobby360;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Artyom on 14.03.2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];
    private int mIcons[];

    private String title;
    private int image;
    private String teaser;


public static class ViewHolder extends RecyclerView.ViewHolder {
    private int Holderid;

    TextView textView;
    ImageView imageView;
    ImageView image;
    TextView title;
    TextView teaser;


    public ViewHolder(View itemView, int ViewType) {
        super(itemView);

        if (ViewType == TYPE_ITEM) {
            textView = (TextView) itemView.findViewById(R.id.rowText);
            imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
            Holderid = 1;
        } else {
            title = (TextView) itemView.findViewById(R.id.appname);
            teaser = (TextView) itemView.findViewById(R.id.appname_teaser);
            image = (ImageView) itemView.findViewById(R.id.circleView);
            Holderid = 0;
        }
    }
}

    public MyAdapter(String TITLES[], int ICONS[], String TITLE, String TEASER, int IMAGE){
        mNavTitles = TITLES;
        mIcons = ICONS;
        title = TITLE;
        teaser = TEASER;
        image = IMAGE;

    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
            ViewHolder vhItem = new ViewHolder(v,viewType);
            return vhItem;
        } else if (viewType == TYPE_HEADER){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType);
            return vhHeader;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        if(holder.Holderid == 1){
            holder.textView.setText(mNavTitles[position - 1]);
            holder.imageView.setImageResource(mIcons[position - 1]);
        }
        else {
            holder.image.setImageResource(image);
            holder.title.setText(title);
            holder.teaser.setText(teaser);
        }
    }


    @Override
    public int getItemCount() {
        return mNavTitles.length+1;
    }

    @Override
    public int getItemViewType(int position){
        if(isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}


