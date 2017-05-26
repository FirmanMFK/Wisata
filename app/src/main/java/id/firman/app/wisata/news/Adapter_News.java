package id.firman.app.wisata.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import id.firman.app.wisata.R;
import id.firman.app.wisata.databases.GSONnews;


/**
 * Created by Firman on 5/22/2017.
 */

class Adapter_News extends RecyclerView.Adapter <Adapter_News.ViewHolder> {
    Context context;
    List<GSONnews.KumpulanBerita>kumpulanBeritas;


    public Adapter_News(News news, List<GSONnews.KumpulanBerita> kumpulanBeritas) {
        context = news;
        this.kumpulanBeritas = kumpulanBeritas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_news, parent , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.text_judul.setText(kumpulanBeritas.get(position).judul_news);
        Glide.with(context)
                .load(kumpulanBeritas.get(position).url_gambar)
                .crossFade()
                .placeholder(R.drawable.ic_photo_size_select_actual_black_24dp)
                .into(holder.image_news);
        holder.cardViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(v.getContext(),Detail_News.class);
                a.putExtra("id_news",kumpulanBeritas.get(position).id_news);
                v.getContext().startActivity(a);
            }
        });

    }

    @Override
    public int getItemCount() {
        return kumpulanBeritas.size();
    }
    protected class ViewHolder extends RecyclerView.ViewHolder{


        TextView text_judul;
        ImageView image_news;
        CardView cardViewNews;
        public ViewHolder(View itemView) {
            super(itemView);
            text_judul= (TextView)itemView.findViewById(R.id.judul_news);
            image_news = (ImageView) itemView.findViewById(R.id.gambar_news);
            cardViewNews = (CardView)itemView.findViewById(R.id.card_news);
        }
    }


}
