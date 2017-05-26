package id.firman.app.wisata.popular;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
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
import id.firman.app.wisata.databases.ParsingDataPopular;
import id.firman.app.wisata.location.DetailWisata;

/**
 * Created by Firman on 5/22/2017.
 */

public class AdapterPopular extends RecyclerView.Adapter<AdapterPopular.ViewHolder> {

    private List<ParsingDataPopular.DataPopular> listPopulars;
    private Context context;

    public AdapterPopular(FragmentActivity listPopulars, List<ParsingDataPopular.DataPopular> context) {
        this.listPopulars = context;
        this.context = listPopulars;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_popular, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvNamaWisata.setText(listPopulars.get(position).judul_wisata2);
        holder.tvJenisWisata.setText(listPopulars.get(position).nama_kategori_wisata);
        holder.tvLokasi.setText(listPopulars.get(position).nama_kota_kabupaten);
        Glide.with(context)
                .load(listPopulars.get(position).url_gambar2)
                .crossFade()
                .placeholder(R.drawable.ic_photo_size_select_actual_black_24dp)
                .into(holder.imageViewPopular);
        holder.card_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(v.getContext(),DetailWisata.class);
                a.putExtra("id_wisata", listPopulars.get(position).id_wisata2);
                v.getContext().startActivity(a);
            }
        });



    }

    @Override
    public int getItemCount() {
        return listPopulars.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvNamaWisata;
        public TextView tvLokasi;
        public TextView tvJenisWisata;
        public ImageView imageViewPopular;
        CardView card_popular;

        protected ViewHolder(View itemView) {
            super(itemView);

            tvNamaWisata = (TextView)itemView.findViewById(R.id.tvNamaWisata);
            tvLokasi = (TextView)itemView.findViewById(R.id.lokasi);
            tvJenisWisata = (TextView)itemView.findViewById(R.id.jenis_wisata);
            imageViewPopular = (ImageView)itemView.findViewById(R.id.image_wisata);
            card_popular = (CardView)itemView.findViewById(R.id.card_wisata);
        }
    }
}
