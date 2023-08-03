package com.ing.custommaterials;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class FragmentBir extends Fragment implements SongChangeListener {

    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    private String permission;

    private List<MusicList> musicLists = new ArrayList<>();
    private RecyclerView musicRV;
    private MediaPlayer mediaPlayer;
    private TextView startTime, endTime;
    private boolean isPlaying= false;
    private SeekBar playerSeekBar;
    private ImageView playPauseImg;

    // Gerekli boş yapıcı metot
    public FragmentBir() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment'in layout dosyasını şişiriyoruz
        return inflater.inflate(R.layout.fragment_bir, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Fragment içindeki UI bileşenlerini burada bulabilir ve işlemleri yapabiliriz
        LinearLayout searchBtn = view.findViewById(R.id.search_btn);
        LinearLayout menuBtn = view.findViewById(R.id.menu_btn);
        musicRV = view.findViewById(R.id.music_rv);
        CardView playPauseCard = view.findViewById(R.id.play_pause_card);
        playPauseImg = view.findViewById(R.id.play_pause_img);
        ImageView nextBtn = view.findViewById(R.id.next_btn);
        ImageView prevBtn = view.findViewById(R.id.prev_btn);
        playerSeekBar = view.findViewById(R.id.player_seekbar);

        startTime = view.findViewById(R.id.start_time);
        endTime = view.findViewById(R.id.end_time);

        musicRV.setHasFixedSize(true);
        musicRV.setLayoutManager(new LinearLayoutManager(getContext()));

        mediaPlayer = new MediaPlayer();

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
//            if (!Environment.isExternalStorageManager()){
//                try {
//                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                    intent.addCategory("android.intent.category.DEFAULT");
//                    intent.setData(Uri.parse(String.format("package:%s", requireContext().getPackageName())));
//                    startActivity(intent);
//                }catch (Exception e) {
//                    Intent intent = new Intent();
//                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                    startActivity(intent);
//                }
//            }
//        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            getMusicFiles();
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
            }else{
                getMusicFiles();
            }
        }

    }
    @SuppressLint("Range")
    private void getMusicFiles(){
        ContentResolver contentResolver = getContext().getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null,MediaStore.Audio.Media.DATA + " LIKE?", new String[]{"%.mp3%"},null);

        if (cursor == null){
            Toast.makeText(requireContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show();
        } else if(!cursor.moveToNext()){
            Toast.makeText(requireContext(), "No Music Found", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                @SuppressLint("Range") final String getMusicFileName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                @SuppressLint("Range") final String getArtistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                @SuppressLint("Range") long cursorId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));

                Uri musicFileUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursorId);
                String getDuration = "00:00";

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    getDuration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION));
                }
                final MusicList musicList = new MusicList(getMusicFileName, getArtistName, getDuration, false, musicFileUri);
                musicLists.add(musicList);
            }
            musicRV.setAdapter(new MusicAdapter(musicLists,requireContext()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            getMusicFiles();
        } else {
            Toast.makeText(requireContext(), "Permission Decline by User", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChanged(int position) {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            mediaPlayer.reset();
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer.setDataSource(requireContext(), musicLists.get(position).getMusicFile());
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                final int getTotalDuration = mp.getDuration();

                String generateDuration = String.format(Locale.getDefault(), "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(getTotalDuration),
                        TimeUnit.MILLISECONDS.toSeconds(getTotalDuration),
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getTotalDuration))
                );

                endTime.setText(generateDuration);
                isPlaying =true;
                mp.start();

                playerSeekBar.setMax(getTotalDuration);
                playPauseImg.setImageResource(R.drawable.pause_icon);
            }
        });
    }

//    @Override
//    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
//        if(getView().hasFocus()){
//            View decodeView = getWindow().getDecorWindow();
//            int options = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//            decodeView.setSystemUiVisibility(options);
//        }
//    }
}