package com.example.mysubmitdicoding2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ListViewHolder> {
private ArrayList<Movie> listMovie=new ArrayList<>();
    private OnClickCallback onClickCallback;

    public ListMovieAdapter() {

    }

    public void setData(ArrayList<Movie> items) {
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
    }
    public void setItemArrayList(ArrayList<Movie> itemArrayList) {
        //this.listFilm = itemArrayList;
        listMovie.clear();
        listMovie.addAll(itemArrayList);
        notifyDataSetChanged();
    }
    public interface OnClickCallback {
        void onClick(Movie movie);
    }

    public void setOnClickCallback(OnClickCallback onClickCallback) {
        this.onClickCallback = onClickCallback;
    }
    public ListMovieAdapter(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    @NonNull
    @Override
    public ListMovieAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMovieAdapter.ListViewHolder holder, int position) {
//        final Movie Movie = listMovie.get(position);
//        /*Glide.with(holder.itemView.getContext())
//                .load(Film.getPhoto())
//               .apply(new RequestOptions().override(55, 55))
//                .into(holder.imgPhoto);*/
//        holder.imgPhoto.setImageResource(Movie.getPhoto());
//        holder.tvName.setText(Movie.getName());
//        holder.tvdescription.setText(Movie.getDescription());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (onClickCallback != null) {
//                    onClickCallback.onClick(Movie);
//                }
//            }
//        });
        holder.bind(listMovie.get(position));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgPhoto;

        TextView tvName, tvdescription;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            tvName = itemView.findViewById(R.id.txt_name);
            tvdescription = itemView.findViewById(R.id.txt_description);
        }
        public void bind(final Movie movie) {
            String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();


            tvName.setText(movie.getName());
            tvdescription.setText(movie.getDescription());
            String url = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();


            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPhoto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickCallback != null) {
                        onClickCallback.onClick(movie);
                    }
                }
            });
        }
    }


}
