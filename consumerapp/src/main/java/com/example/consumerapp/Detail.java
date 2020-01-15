package com.example.consumerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Detail extends AppCompatActivity {
    public static final String EXTRA_FILM = "extra_film";
    public static final String EXTRA_TIPE = "extra_tipe";
    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";

    TextView tvtitle;
    TextView tvdescription;
    ImageView imgposter;
    Button btnFavorite,btnRemoveFavorite;

    private FavoriteDB favoritedb;
    private int position;
    private boolean isEdit = false;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        progressBar = this.findViewById(R.id.pbar);
        showLoading(true);

        tvtitle = findViewById(R.id.tvtitle);
        tvdescription = findViewById(R.id.tvdescription);
        imgposter = findViewById(R.id.img_film);

        final Movie movie = getIntent().getParcelableExtra(EXTRA_FILM);
        //final String tipe = getIntent().getStringExtra(EXTRA_TIPE);
        tvtitle.setText(movie.getName());
        tvdescription.setText(movie.getDescription());
        String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();

        Glide.with(Detail.this)
                .load(url_image)
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(imgposter);
    }
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
