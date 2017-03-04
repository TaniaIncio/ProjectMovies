package com.tincio.popularmovies.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.services.response.ResultTrailer;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdapterRecyclerDetailMovie extends RecyclerView.Adapter<AdapterRecyclerDetailMovie.ViewHolderItem> {

    List<ResultTrailer> listTrailer;


    public AdapterRecyclerDetailMovie(List listTrailer){
        this.listTrailer = listTrailer;
    }
    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_detallemovie, parent, false);
        ViewHolderItem viewHolder = new ViewHolderItem(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, int position) {
        String id = listTrailer.get(position).getId().toString();
        holder.txtNombreTrailer.setText("Trailer "+(position+1));
        holder.txtTituloTrailer.setText(listTrailer.get(position).getName().toString());
        holder.txtSitioTrailer.setText(listTrailer.get(position).getSite().toString());
        holder.txtTamanioTrailer.setText(listTrailer.get(position).getSize().toString()+" p");
    }

    @Override
    public int getItemCount() {
        return listTrailer==null?0:listTrailer.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {

        @BindView(R.id.rowrecyclerdetalle_linear_nrotrailer)
        LinearLayout linearLayout;
        @BindView(R.id.rowrecyclerdetalle_txt_nombretrailer)
        TextView txtNombreTrailer;
        @BindView(R.id.rowrecyclerdetalle_txt_title)
        TextView txtTituloTrailer;
        @BindView(R.id.rowrecyclerdetalle_txt_site)
        TextView txtSitioTrailer;
        @BindView(R.id.rowrecyclerdetalle_txt_size)
        TextView txtTamanioTrailer;

        public ViewHolderItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.rowrecyclerdetalle_linear_nrotrailer)
        void onClickLinearTrailer(){
            try{
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.setOnItemClickListener(listTrailer.get(getAdapterPosition()));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



    OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        public void setOnItemClickListener(ResultTrailer trailer);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener){
        this.mOnItemClickListener = mItemClickListener;
    }
}
