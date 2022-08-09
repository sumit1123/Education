package com.example.education.examnames;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.education.R;
import com.example.education.databinding.AdapterTrackResultBinding;
import com.example.education.notes.NotesDetailActivity;
import com.example.education.response.SetResponse;
import com.example.education.utils.Constant;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrackResultAdapter extends RecyclerView.Adapter<TrackResultAdapter.ViewHolder> {

    private Activity mContext;
    List<SetResponse> setResponses = new ArrayList<>();

    public void setData(List<SetResponse> setResponses) {
        this.setResponses = setResponses;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AdapterTrackResultBinding adapterTrackResultBinding;

        public ViewHolder(AdapterTrackResultBinding itemView) {
            super(itemView.getRoot());
            this.adapterTrackResultBinding = itemView;
        }
    }

    public TrackResultAdapter(Activity context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterTrackResultBinding adapterTrackResultBinding = AdapterTrackResultBinding.inflate(LayoutInflater.from(mContext), parent ,false);
        ViewHolder vh = new ViewHolder(adapterTrackResultBinding);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.adapterTrackResultBinding.tvCorrectAnswerValue.setText(setResponses.get(holder.getAdapterPosition()).correct_ans);
        holder.adapterTrackResultBinding.tvIncorrectAnsValue.setText(setResponses.get(holder.getAdapterPosition()).incorrect_ans);
        holder.adapterTrackResultBinding.tvSkipQuesValue.setText(setResponses.get(holder.getAdapterPosition()).skip_que);
        holder.adapterTrackResultBinding.tvResultValue.setText(setResponses.get(holder.getAdapterPosition()).result);
        holder.adapterTrackResultBinding.btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RetrivePDFfromUrl().execute(Constant.IMAGE_URL + setResponses.get(holder.getAdapterPosition()).supportFile);
            }
        });
    }

    @Override
    public int getItemCount() {
        return setResponses.size();
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
            Toast.makeText(mContext, "Downlaoded" ,Toast.LENGTH_SHORT).show();
        }
    }
}