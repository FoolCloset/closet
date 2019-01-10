package com.example.q.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rickey
 *
 */

public class ListviewAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList = new ArrayList<>();
    public ListviewAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
            viewHolder.imageButton = (ImageButton) view.findViewById(R.id.sort_enter);
            viewHolder.mButton = (Button) view.findViewById(R.id.sort_button);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mButton.setText(mList.get(i));
        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(i);
            }
        });
        return view;
    }
    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }
    private onItemDeleteListener mOnItemDeleteListener;
    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }
    class ViewHolder {
        ImageButton imageButton;
        Button mButton;
    }
}