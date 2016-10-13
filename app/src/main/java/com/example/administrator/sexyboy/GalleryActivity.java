package com.example.administrator.sexyboy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Administrator on 2016/10/13.
 */

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    private List<GalleryBean.TngouBean> mDataList;
    @BindView(R.id.grllery_list)
    ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        int id = getIntent().getIntExtra("id", 0);
        Log.d(TAG, id + "");
        String url = "http://apis.baidu.com/tngou/gallery/list?id=" + id + "&page=1&rows=20";

        SexyBoyRequest<GalleryBean> request = new SexyBoyRequest<>(url, GalleryBean.class, mListener, mErrorListener);
        NetworkManager.sendRequest(request);
    }

    private Response.Listener<GalleryBean> mListener = new Response.Listener<GalleryBean>() {
        @Override
        public void onResponse(GalleryBean response) {
            mDataList = response.getTngou();
            list.setAdapter(mBaseAdapter);
        }

    };
    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };
    private BaseAdapter mBaseAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return mDataList.size() == 0 ? 0 : mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            if (mDataList != null) {
                return mDataList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(GalleryActivity.this).inflate(R.layout.gallery_list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(mDataList.get(position).getTitle());
            String url = "http://tnfs.tngou.net/image" + mDataList.get(position).getImg();
            Glide.with(GalleryActivity.this).load(url).bitmapTransform(new BlurTransformation(GalleryActivity.this)).into(holder.image);
            return convertView;
        }
    };

    public class ViewHolder {
        @BindView(R.id.gallery_list_item_image)
        ImageView image;
        @BindView(R.id.gallery_list_item_title)
        TextView title;

        public ViewHolder(View root) {
            ButterKnife.bind(this, root);
        }

    }
}
