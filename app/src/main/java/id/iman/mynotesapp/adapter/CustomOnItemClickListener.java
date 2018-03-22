package id.iman.mynotesapp.adapter;

import android.view.View;

/**
 * Created by Iman on 22/03/2018.
 */

public class CustomOnItemClickListener implements View.OnClickListener {
    private int position;
    public OnItemClickCallback onItemClickCallback;

    public CustomOnItemClickListener(int position, OnItemClickCallback onItemClickCallback) {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(v, position);

    }

    public interface OnItemClickCallback {
        void onItemClicked(View view, int position);
    }
}
