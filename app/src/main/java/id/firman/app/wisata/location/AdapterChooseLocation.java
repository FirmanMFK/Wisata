package id.firman.app.wisata.location;

/**
 * Created by Firman on 5/22/2017.
 */

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
import id.firman.app.wisata.databases.ParsingProvinsi;
import id.firman.app.wisata.databases.Url;


class AdapterChooseLocation extends RecyclerView.Adapter<AdapterChooseLocation.ViewHolder> {

    Context contexxt;
    public List<ParsingProvinsi.DataProvinsi> dataProvinsiss;

    public AdapterChooseLocation(FragmentActivity activity, List<ParsingProvinsi.DataProvinsi> dataProvinsis) {
        contexxt = activity;
        dataProvinsiss = dataProvinsis;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_provinsi, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textProvinsi.setText(dataProvinsiss.get(position).nama_provinsi);

        Glide.with(contexxt)
                .load(Url.URL_GAMBAR + dataProvinsiss.get(position).gambar_provinsi)
                .crossFade()
                .placeholder(R.drawable.ic_photo_size_select_actual_black_24dp)
                .into(holder.imageProvinsi);
        holder.cardProvinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(v.getContext(),KotaActivity.class);
                a.putExtra("id_provinsi",dataProvinsiss.get(position).id_provinsi);
                v.getContext().startActivity(a);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataProvinsiss.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView textProvinsi;
        ImageView imageProvinsi;
        CardView cardProvinsi;

        protected ViewHolder(View itemView) {
            super(itemView);
            cardProvinsi = (CardView) itemView.findViewById(R.id.card_provinsi);
            textProvinsi = (TextView) itemView.findViewById(R.id.nama_provinsi);
            imageProvinsi = (ImageView) itemView.findViewById(R.id.gambar_provinsi);

        }
    }
}
