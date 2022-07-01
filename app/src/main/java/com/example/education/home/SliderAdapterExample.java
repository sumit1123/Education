package com.example.education.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.education.R;
import com.example.education.response.SubjectResponse;
import com.example.education.utils.Constant;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Response;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Activity context;
    String cate_id;
    String type;
    Response<List<SubjectResponse>> response;

    public SliderAdapterExample(Activity context, Response<List<SubjectResponse>> response) {
        this.context = context;
        this.response = response;
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_adapter, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        Picasso.get().load(Constant.IMAGE_URL + response.body().get(position).image).placeholder(R.drawable.video_placeholder).into(viewHolder.iv_auto_image_slider);

    }

    @Override
    public int getCount() {
        return response.body().size();
    }



    class SliderAdapterVH extends ViewHolder {

        View itemView;
        ImageView imageViewBackground ,iv_auto_image_slider;
        FrameLayout fl_shadow_container;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            iv_auto_image_slider = itemView.findViewById(R.id.iv_auto_image_slider);
        }
    }

}