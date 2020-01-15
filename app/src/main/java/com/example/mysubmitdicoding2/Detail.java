package com.example.mysubmitdicoding2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mysubmitdicoding2.db.FavoriteHelper;
import com.example.mysubmitdicoding2.entity.FavoriteDB;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    private FavoriteHelper favoriteHelper;
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
        btnFavorite=findViewById(R.id.btn_favorite);
        btnRemoveFavorite = findViewById(R.id.btn_remove);
        final Movie movie = getIntent().getParcelableExtra(EXTRA_FILM);
        final String tipe = getIntent().getStringExtra(EXTRA_TIPE);
        tvtitle.setText(movie.getName());
        tvdescription.setText(movie.getDescription());
        String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();

        favoriteHelper = FavoriteHelper.getInstance(getApplicationContext());
        favoriteHelper.open();
        int a = favoriteHelper.queryById(movie.getName()).getCount();
        if (favoriteHelper.queryById(movie.getName()).getCount()>0) {
            //udah ada di db
            btnFavorite.setEnabled(false);
            btnRemoveFavorite.setEnabled(true);
        }
        else
        {//belum ada di db
            btnFavorite.setEnabled(true);
            btnRemoveFavorite.setEnabled(false);
        }

        favoritedb = getIntent().getParcelableExtra(EXTRA_NOTE);
        if (favoritedb != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
        } else {
            favoritedb = new FavoriteDB();

        }
        Glide.with(Detail.this)
                .load(url_image)
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(imgposter);
        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tambahkan ke sqllite
                favoritedb.setTitle(movie.getName());
                favoritedb.setDescription(movie.getDescription());
                favoritedb.setPhoto(movie.getPhoto());
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String strDate = formatter.format(date);
                favoritedb.setDate(strDate);

                favoritedb.setTipe(tipe);
                if (favoriteHelper.queryById(movie.getName()).getCount()<=0) {
                    favoriteHelper.insert(movie, tipe, strDate);
                    btnRemoveFavorite.setEnabled(true);
                    btnFavorite.setEnabled(false);
                }
                else{
                    //sudah pernah ditambahkan
                }
               // favoriteHelper.close();

            }
        });

        btnRemoveFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tambahkan ke sqllite
                favoritedb.setTitle(movie.getName());
                favoritedb.setDescription(movie.getDescription());
                favoritedb.setPhoto(movie.getPhoto());
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String strDate = formatter.format(date);
                favoritedb.setDate(strDate);

                favoritedb.setTipe(tipe);
                if (favoriteHelper.queryById(movie.getName()).getCount()>0) {
                    favoriteHelper.deleteById(movie.getName());
                    btnRemoveFavorite.setEnabled(false);
                    btnFavorite.setEnabled(true);
                }
                else{
                    //sudah pernah ditambahkan
                }
                //favoriteHelper.close();

            }
        });
    }
    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
