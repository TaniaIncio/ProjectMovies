package com.tincio.popularmovies.presentation.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tincio.popularmovies.R;
import com.tincio.popularmovies.data.model.MovieRealm;
import com.tincio.popularmovies.data.services.response.ResponseTrailersMovie;
import com.tincio.popularmovies.data.services.response.Result;
import com.tincio.popularmovies.data.services.response.ResultTrailer;
import com.tincio.popularmovies.presentation.adapter.AdapterRecyclerDetailMovie;
import com.tincio.popularmovies.presentation.presenter.MovieTrailerPresenter;
import com.tincio.popularmovies.presentation.util.Constants;
import com.tincio.popularmovies.presentation.util.Utils;
import com.tincio.popularmovies.presentation.view.MovieTrailerView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleMovieFragment extends Fragment implements MovieTrailerView {

    AdapterRecyclerDetailMovie adapterRecyclerDetailMovie;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.contentscrolling_recycler_trailers)
    RecyclerView recTrailers;
    @BindView(R.id.fragmentdetallemovie_fab_addfavorite)
    FloatingActionButton fabAddFavorito;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.fragmentdetallemovie_img)
    ImageView imgMovie;
    @BindView(R.id.contentscrolling_txt_descriptionmovie)
    TextView txtDescripcionMovie;
    @BindView(R.id.contentscrolling_txt_languagemovie)
    TextView txtLenguageMovie;
    @BindView(R.id.contentscrolling_txt_popularitymovie)
    TextView txtPopularidadMovie;
    @BindView(R.id.contentscrolling_txt_countvotesmovie)
    TextView txtCantidadVotosMovie;
    @BindView(R.id.appCompatRatingBar2)
    AppCompatRatingBar txtValoracionMovie;
    @BindView(R.id.contentscrolling_txt_averagevotesmovie)
    TextView txtPromedioVotosMovie;
    @BindView(R.id.contentscrolling_txt_datemovie)
    TextView dateMovie;
    @BindView(R.id.titleTrailer)
    TextView txtTituloTrailer;
    ProgressDialog progress;

    MovieTrailerPresenter presenter;
    String favoritoOff= "ic_favorite_border_white_24dp";
    String favoritoOn= "ic_favorite_white_24dp";
    Result detailMovieSelection;

    public static String TAG = DetalleMovieFragment.class.getSimpleName();
    public DetalleMovieFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle_movie, container, false);
        ButterKnife.bind(this, view);
        presenter = new MovieTrailerPresenter();
        presenter.setView(this);
        return view;
    }

    public static Fragment newInstance(Bundle bundle){
        Fragment fragment = new DetalleMovieFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        //get movie detail
        Result mResult=null;
        if(getArguments()!=null)
             mResult = (Result) getArguments().getSerializable(getResources().getString(R.string.serializable_detailmovie));
        if(mResult!=null)
            setDetailMovie(mResult);
    }

    public void setDetailMovie(Result detailMovie){
        if(detailMovie!=null ){
            Float v_valoracion = new Float("0.0");
            if(detailMovie.getVoteAverage()!=null){
                 v_valoracion = Float.valueOf(detailMovie.getVoteAverage().toString());
                if(v_valoracion > 0){
                    v_valoracion = v_valoracion /2;
                }else{
                    v_valoracion = Float.valueOf(0);
                }
            }


            detailMovieSelection = detailMovie;
            collapsingToolbarLayout.setTitle(detailMovie.getTitle());
            Picasso.with(getActivity()).load(Constants.serviceNames.GET_IMAGE_MOVIES+detailMovie.getBackdropPath()).into(imgMovie);
            txtDescripcionMovie.setText(detailMovie.getOverview());
            txtLenguageMovie.setText(detailMovie.getOriginalLanguage());
            txtPopularidadMovie.setText(String.valueOf(detailMovie.getPopularity()));
            txtCantidadVotosMovie.setText(String.valueOf(detailMovie.getVoteCount()));
            txtValoracionMovie.setRating(v_valoracion);
            txtPromedioVotosMovie.setText(String.valueOf(v_valoracion));
            dateMovie.setText(detailMovie.getReleaseDate());
            fabAddFavorito.setImageDrawable(Utils.getDrawableByName(getContext(),detailMovie.getFavorito()?favoritoOn:favoritoOff));
            fabAddFavorito.setTag(detailMovie.getFavorito()?favoritoOn:favoritoOff);
            presenter.getTrailerByMovie(detailMovie.getId());
        }
    }

    @Override
    public void showLoading() {
        progress = Utils.showProgressDialog(getActivity());
    }

    @Override
    public void closeLoading() {
        if(progress!=null)
            progress.dismiss();
    }

    @Override
    public void showMovieTrailer(ResponseTrailersMovie detailMovie, String responseError) {
        if(detailMovie == null){
            txtTituloTrailer.setText("");
        }else if(detailMovie.getResults().size()>1){
            txtTituloTrailer.setText("Trailers");
        }else if (detailMovie.getResults().size() == 1){
            txtTituloTrailer.setText("Trailer");
        }else {
            txtTituloTrailer.setText("");
        }
        adapterRecyclerDetailMovie = new AdapterRecyclerDetailMovie(detailMovie==null?null:detailMovie.getResults());
        recTrailers.setAdapter(adapterRecyclerDetailMovie);
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recTrailers.setLayoutManager(linearLayoutManager);
        adapterRecyclerDetailMovie.setOnItemClickListener(new AdapterRecyclerDetailMovie.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(ResultTrailer trailer) {
                getIntentWatchTrailer(trailer.getKey());
            }
        });
    }

    @Override
    public void showResultFavorite(String response) {
        try {
            if(response.equals(getString(R.string.response_succesfull))){
                if (fabAddFavorito.getTag().equals(favoritoOn)) {
                    fabAddFavorito.setImageDrawable(getResources().getDrawable(R.mipmap.ic_favorite_border_white_24dp));
                    fabAddFavorito.setTag(favoritoOff);
                    detailMovieSelection.setFavorito(false);
                } else {
                    fabAddFavorito.setImageDrawable(getResources().getDrawable(R.mipmap.ic_favorite_white_24dp));
                    fabAddFavorito.setTag(favoritoOn);
                    detailMovieSelection.setFavorito(true);
                }
            }
            changeItemOfBackFragment();
        } catch(Exception e){
           e.printStackTrace();
        }
    }

    void getIntentWatchTrailer(String key){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+key));
            startActivity(intent);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.fragmentdetallemovie_fab_addfavorite)
    void onChangeStateFavorite(){
        try{
            presenter.saveFavoriteMovie(detailMovieSelection.getId(), detailMovieSelection.getPosterPath(), detailMovieSelection.getTitle());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void changeItemOfBackFragment(){
        try{
            ListMoviesFragment fragment = (ListMoviesFragment)getFragmentManager().findFragmentByTag(ListMoviesFragment.TAG);
            fragment.updateItemOfRecycler(detailMovieSelection);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
