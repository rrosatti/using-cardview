package com.example.rodri.usingcardview.adapter;

import android.app.Activity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rodri.usingcardview.R;
import com.example.rodri.usingcardview.model.Album;

import java.util.List;

/**
 * Created by rodri on 7/4/2016.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Activity activity;
    private List<Album> albums;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView displayTitle, displayCount;
        public ImageView displayThumbnail, displayOverflow;

        public MyViewHolder(View view) {
            super(view);

            displayTitle = (TextView) view.findViewById(R.id.txtTitle);
            displayCount = (TextView) view.findViewById(R.id.txtCount);
            displayThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);
            displayOverflow = (ImageView) view.findViewById(R.id.imgOverflow);
        }
    }

    public AlbumsAdapter(Activity activity, List<Album> albums) {
        this.activity = activity;
        this.albums = albums;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album album = albums.get(position);

        holder.displayTitle.setText(album.getName());
        holder.displayCount.setText(album.getNumOfSongs() + " songs");

        // Loading album cover using Glide library
        Glide.with(activity).load(album.getThumbnail()).into(holder.displayThumbnail);

        holder.displayOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder.displayOverflow);
            }
        });
    }

    /**
     *
     * This method will show the popup menu when user click in the '3 dots' icon
     *
     * @param view
     */
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(activity, view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_album, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popupMenu.show();
    }

    /**
     *
     * This is a Listener class for Popup menu items
     *
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {}

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favorite:
                    Toast.makeText(activity, "Add to favorite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(activity, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
}
