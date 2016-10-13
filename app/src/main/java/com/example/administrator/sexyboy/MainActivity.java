package com.example.administrator.sexyboy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @BindView(R.id.list)
    ListView list;

    private List<ClassifyBean.TngouBean> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadDataFromServer();

    }

    private void loadDataFromServer() {
        String url = "http://apis.baidu.com/tngou/gallery/news";
        SexyBoyRequest<ClassifyBean> request = new SexyBoyRequest<>(url, ClassifyBean.class, mListener, mErrorListener);
        NetworkManager.sendRequest(request);
    }

    private Response.Listener<ClassifyBean> mListener = new Response.Listener<ClassifyBean>() {
        @Override
        public void onResponse(ClassifyBean response) {
            //  Log.d(TAG, "onResponse: " + response.getTngou().get(0).getTitle());
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
                convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(mDataList.get(position).getTitle());

            return convertView;
        }

    };

    public class ViewHolder {
        @BindView(R.id.title)
        TextView title;

        public ViewHolder(View root) {
            ButterKnife.bind(this, root);
        }
    }

}
