package com.hangnadi.recyclerview.snap;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("unused")
    private static final String TAG = "hangnadi";

    private RecyclerView recyclerView;
    private TextView textView;

    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        textView = (TextView) findViewById(R.id.textview);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
            }
        });

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        Observable.interval(3000L, TimeUnit.MILLISECONDS)
                .timeInterval()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Timed<Long>>() {
                    @Override
                    public void accept(@NonNull Timed<Long> longTimed) throws Exception {
                        if (currentPosition == recyclerView.getAdapter().getItemCount() - 1) {
                            currentPosition = -1;
                        }
                        recyclerView.smoothScrollToPosition(currentPosition + 1);
                        textView.setText(String.format(Locale.US, "%d", currentPosition + 1));
                    }
                });


    }

    private class RecyclerViewAdapter
            extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

        class RecyclerViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;

            Context context;

            RecyclerViewHolder(View itemView) {
                super(itemView);
                this.imageView = (ImageView) itemView.findViewById(R.id.imageview);
                this.context = itemView.getContext();
            }

        }
        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_viewholder, parent, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            float scale = getResources().getDisplayMetrics().density;
            int padding_8dp = (int) (8 * scale + 0.5f);

            if (position == getItemCount() - 1) {
                holder.itemView.setPadding(padding_8dp, padding_8dp, padding_8dp, padding_8dp);
            } else {
                holder.itemView.setPadding(padding_8dp, padding_8dp, 0, padding_8dp);
            }

            Glide.with(holder.context)
                    .load("http://diskonaja.okezone.com/wp-content/uploads/sites/7/2017/03/promo-tokopedia.jpg")
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return 5;
        }
    }
}
