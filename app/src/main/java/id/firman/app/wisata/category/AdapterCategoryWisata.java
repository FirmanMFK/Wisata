package id.firman.app.wisata.category;

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
import id.firman.app.wisata.databases.ParsingKategori;
import id.firman.app.wisata.databases.Url;
import id.firman.app.wisata.location.WisataActivity;

/**
 * Created by Firman on 5/22/2017.
 */

class AdapterCategoryWisata extends RecyclerView.Adapter<AdapterCategoryWisata.ViewHolder> {
    Context context;
    public List<ParsingKategori.KumpulanKategoriWisata> kategoriWisatas;
    String lemoar;

    public AdapterCategoryWisata(Category listener, List<ParsingKategori.KumpulanKategoriWisata> kategoriWisataList, String lempar) {
        context = listener;
        kategoriWisatas = kategoriWisataList;
        lemoar = lempar;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_kategori, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textNamaKategori.setText(kategoriWisatas.get(position).nama_kategori_wisata);
        Glide.with(context)
                .load(Url.URL_GAMBAR + kategoriWisatas.get(position).gambar_kategori_wisata)
                .crossFade()
                .placeholder(R.drawable.ic_photo_size_select_actual_black_24dp)
                .into(holder.imageKategori);

        holder.cardKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(v.getContext(), WisataActivity.class);
                //      Intent b = new Intent()
                //     String aaa = a.getStringExtra("id_kota_kabupatens");
                //      a.putExtra("id_kota_kabupaten_1",aaa);
                a.putExtra("id_kota_kabupaten_1",lemoar);
                a.putExtra("id_kategori_wisata", kategoriWisatas.get(position).id_kategori_wisata);
                v.getContext().startActivity(a);
            }
        });


    }

    @Override
    public int getItemCount() {
        return kategoriWisatas.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        TextView textNamaKategori;
        ImageView imageKategori;
        CardView cardKategori;

        protected ViewHolder(View itemView) {
            super(itemView);
            cardKategori = (CardView) itemView.findViewById(R.id.card_kategori);
            imageKategori = (ImageView) itemView.findViewById(R.id.gambar_kategori);
            textNamaKategori = (TextView) itemView.findViewById(R.id.nama_kategori);
        }
    }
}
