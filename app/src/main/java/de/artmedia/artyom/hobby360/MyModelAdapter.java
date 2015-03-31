package de.artmedia.artyom.hobby360;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Artyom on 27.03.2015.
 */
public class MyModelAdapter extends RecyclerView.Adapter<MyModelAdapter.ViewHolder> {

    private final Context context;

    private int mThumbs[];
    public static int mPano[];
    private String  mModels[];
    private String mInfos[];

    private int thumb;
    private String model;
    private String info;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private int Holderid;

        ImageView imageView;
        TextView Modell;
        TextView Infotext;
        Context contxt;

        public ViewHolder(View itemView, int ViewType, Context c) {
            super(itemView);
            contxt = c;
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            Modell = (TextView) itemView.findViewById(R.id.modell_list_title);
            Infotext = (TextView) itemView.findViewById(R.id.modell_list_info);
            imageView = (ImageView) itemView.findViewById(R.id.modell_list_thumbnail);
        }

        @Override
        public void onClick(View v) {

            int usedPos = getPosition();

            Intent i = new Intent(contxt, Panoviewer.class);
            Bundle bundle = new Bundle();
            bundle.putInt("pano",mPano[usedPos]);
            i.putExtras(bundle);
            contxt.startActivity(i);


        }
    }

    public MyModelAdapter(int THUMB[], int[] PANO, String MODEL[], String[] mINFO, Context passedContext)
    {
        mThumbs = THUMB;
        mPano = PANO;
        mModels = MODEL;
        mInfos = mINFO;
        this.context = passedContext;
    }

    @Override
    public MyModelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,parent,false);
        ViewHolder vhItem = new ViewHolder(v,viewType,context);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

           holder.Modell.setText(mModels[position]);
           holder.Infotext.setText(mInfos[position]);
           holder.imageView.setImageResource(mThumbs[position]);

    }

    @Override
    public int getItemCount() {
        return mModels.length;
    }


}
