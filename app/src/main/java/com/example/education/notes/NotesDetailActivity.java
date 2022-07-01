package com.example.education.notes;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.WindowManager;
import androidx.lifecycle.ViewModelProvider;
import com.example.education.base.BaseActivity;

import com.example.education.databinding.ActivityNotesDetailBinding;
import com.example.education.response.NotesResponse;
import com.example.education.utils.Constant;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Response;

public class NotesDetailActivity extends BaseActivity implements NotesInterface {

    ActivityNotesDetailBinding activityNotesDetailBinding;
    private NotesViewModel notesViewModel;
    String pdffile;
    NotesAdapter notesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityNotesDetailBinding = ActivityNotesDetailBinding.inflate(getLayoutInflater());
        setContentView(activityNotesDetailBinding.getRoot());
        notesViewModel = new ViewModelProvider(this, new NotesViewModelFactory(this)).get(NotesViewModel.class);
        pdffile = getIntent().getStringExtra("pdffile");
        new RetrivePDFfromUrl().execute(Constant.IMAGE_URL + pdffile);

    }


    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setNotes(Response<List<NotesResponse>> response) {
        notesAdapter.setData(response.body());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            activityNotesDetailBinding.idPDFView.fromStream(inputStream).load();
        }
    }
}