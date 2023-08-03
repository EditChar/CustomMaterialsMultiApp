package com.ing.custommaterials;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private List<MusicList> list;
    private Context context;
    private int playingPosition = 0;
   // private final SongChangeListener songChangeListener;

    public MusicAdapter(List<MusicList> list, Context context) {
        this.list = list;
        this.context = context;
       // this.songChangeListener = ((SongChangeListener)context);
    }

    @NonNull
    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.music_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        MusicList list2= list.get(position);

        if (list2.isPlaying()){
            playingPosition = position;// sadece positionu getItemView içine koyarsak multi select gösterir.
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_blue_10);
        }else {
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_10);
        }

        String generateDuration = String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(list2.getDuration())),
                TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(list2.getDuration())),
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(list2.getDuration())))
        );
        holder.title.setText(list2.getTitle());
        holder.artist.setText(list2.getArtist());
        holder.musicDuration.setText(generateDuration);

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(playingPosition).setPlaying(false);
                list2.setPlaying(true);

                //songChangeListener.onChanged(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rootLayout;
        private TextView title, artist, musicDuration;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rootLayout = itemView.findViewById(R.id.root_layout);
            title = itemView.findViewById(R.id.music_title);
            artist = itemView.findViewById(R.id.music_artist);
            musicDuration = itemView.findViewById(R.id.music_duration);

        }
    }
}
