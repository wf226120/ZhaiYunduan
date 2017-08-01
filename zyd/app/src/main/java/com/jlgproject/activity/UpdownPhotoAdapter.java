package com.jlgproject.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.jlgproject.R;
import com.jlgproject.util.L;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sunbeibei on 2017/5/12.
 * <p>gridview图片的适配器
 */

public class UpdownPhotoAdapter extends BaseAdapter {
    private ArrayList<String> path = new ArrayList<>();
    private Context context;

    //    设置最大上传张数
    private int maxImages = 10;
    private int count;
    private int addpic;

    private String s;
    private File file;


    public UpdownPhotoAdapter(Context context, int addpic) {
        this.context = context;
        this.addpic = addpic;
    }

    public void setPath(ArrayList<String> path) {
        this.path = path;
    }

    @Override
    public int getCount() {

        count = path == null ? 1 : path.size() + 1;
        if (count >= maxImages) {
            return path.size();
        } else {
            return count;
        }
    }


    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.griditem, parent, false);
            vh = new ViewHolder();
            vh.imageView = (ImageView) convertView.findViewById(R.id.image);
            vh.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            vh.rl_griditem = (RelativeLayout) convertView.findViewById(R.id.rl_griditem);
            vh.deleteImage = (ImageView) convertView.findViewById(R.id.delete_markView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        if (path != null && position < path.size()) {
            vh.imageView.setVisibility(View.VISIBLE);
            s = path.get(position);
            file = new File(s);
            L.e("-----file-----", file + "");
            Glide.with(context).load(file).into(vh.imageView);
            vh.deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击删除按钮进行删除图片
                    path.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else {
            if (addpic == 1) {
                Glide.with(context).load(R.mipmap.photo_company).priority(Priority.HIGH).into(vh.imageView);
            } else if (addpic == 0 || addpic == 4) {
                Glide.with(context).load(R.mipmap.photo).priority(Priority.HIGH).into(vh.imageView);
                notifyDataSetChanged();
            } else if (addpic == 3) {
                Glide.with(context).load(R.mipmap.photo_icard).priority(Priority.HIGH).into(vh.imageView);
            }
            vh.deleteImage.setVisibility(View.GONE);


        }
        return convertView;
    }


    public class ViewHolder {
        ImageView imageView, deleteImage;
        RelativeLayout rl_griditem;
    }
}
