package id.firman.app.wisata.location;

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
import id.firman.app.wisata.databases.ParsingDaftarWisata;

/**
 * Created by Firman on 5/22/2017.
 */

class AdapterWisata extends RecyclerView.Adapter<AdapterWisata.ViewHolder> {

    Context context;
    public List<ParsingDaftarWisata.DaftarWisata> daftarWisatass;

    public AdapterWisata(WisataActivity wisataActivity, List<ParsingDaftarWisata.DaftarWisata> daftarWisatas) {
        context = wisataActivity;
        daftarWisatass = daftarWisatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_popular, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.nama_wisata.setText(daftarWisatass.get(position).judul_wisata);
        holder.lokasi_wisata.setText(daftarWisatass.get(position).id_kota_kabupaten);
        holder.jenis_wisata.setText(daftarWisatass.get(position).id_kategori_wisata);
        Glide.with(context)
                .load(daftarWisatass.get(position).url_gambar)
                .crossFade()
                .placeholder(R.drawable.ic_photo_size_select_actual_black_24dp)
                .into(holder.gambar_wisata);
        holder.cardWisata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(v.getContext(),DetailWisata.class);
                a.putExtra("id_wisata", daftarWisatass.get(position).id_wisata);
                v.getContext().startActivity(a);
            }
        });

    }

    @Override
    public int getItemCount() {
        return daftarWisatass.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama_wisata, lokasi_wisata, jenis_wisata;
        ImageView gambar_wisata;
        CardView cardWisata;

        protected ViewHolder(View itemView) {
            super(itemView);

            nama_wisata = (TextView)itemView.findViewById(R.id.tvNamaWisata);
            lokasi_wisata = (TextView)itemView.findViewById(R.id.lokasi);
            jenis_wisata = (TextView)itemView.findViewById(R.id.jenis_wisata);
            gambar_wisata = (ImageView)itemView.findViewById(R.id.image_wisata);
            cardWisata = (CardView)itemView.findViewById(R.id.card_wisata);

        }
    }
}
