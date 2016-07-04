package com.example.rodri.usingcardview.activity;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.rodri.usingcardview.GridSpacingItemDecoration;
import com.example.rodri.usingcardview.R;
import com.example.rodri.usingcardview.adapter.AlbumsAdapter;
import com.example.rodri.usingcardview.model.Album;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlbumsAdapter albumsAdapter;
    private List<Album> albums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albums = new ArrayList<>();
        albumsAdapter = new AlbumsAdapter(this, albums);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(albumsAdapter);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.imgBackdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will initialized the collapsing toolbar and show/hide the toolbar tittle on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // showing and hiding the title when toolbar is expanded and collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void prepareAlbums() {
        int[] covers = new int[] {
                R.drawable.coldplay_album,
                R.drawable.disturbed_album,
                R.drawable.foo_fighters_album,
                R.drawable.green_day_album,
                R.drawable.jack_johnson_album,
                R.drawable.linkin_park_album
        };

        Album album = new Album("A Sky full of Stars", 10, covers[0]);
        albums.add(album);

        album = new Album("The Vengeful One", 8, covers[1]);
        albums.add(album);

        album = new Album("Foo Fighters - Greatest Hits", 10, covers[2]);
        albums.add(album);

        album = new Album("American Idiot", 12, covers[3]);
        albums.add(album);

        album = new Album("In Between Dreams", 8, covers[4]);
        albums.add(album);

        album = new Album("Meteora", 10, covers[5]);
        albums.add(album);

        albumsAdapter.notifyDataSetChanged();
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
