package com.jlgproject.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlgproject.MainActivity;
import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Login_zud;
import com.jlgproject.model.MyPagerResponse;
import com.jlgproject.model.UpdataUserImage;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.NetUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/5/19.
 * 个人资料
 */

public class My_Personal_Data extends BaseActivity implements View.OnClickListener, HttpMessageInfo.IMessage {

    private ImageView head;
    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private File file;
    private ArrayList<String> path;
    private ImageView title_left;
    private TextView title_name;
    private TextView tellphone;
    private MyPagerResponse myPagerResponse;
    private TextView userName,shengfengz;
    private ImageView head_portrait;
    private LinearLayout line_photo;
    private LinearLayout line_phone;
    private LinearLayout prgress;

    @Override
    public int loadWindowLayout() {
        return R.layout.my_personal_data;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        EventBus.getDefault().register(this);
    }

    @Override
    public void initViews() {
        super.initViews();
        prgress = (LinearLayout) findViewById(R.id.prgress_photo);
        line_photo = (LinearLayout) findViewById(R.id.line_touxiang);
        line_photo.setOnClickListener(this);
        line_phone = (LinearLayout) findViewById(R.id.line3_phone);
        line_phone.setOnClickListener(this);
        head = (ImageView) findViewById(R.id.head_portrait);
        title_left = (ImageView) findViewById(R.id.iv_title_left);
        title_left.setImageResource(R.mipmap.back);
        title_left.setVisibility(View.VISIBLE);
        title_left.setOnClickListener(this);
        title_name = (TextView) findViewById(R.id.tv_title_name);
        title_name.setText("个人资料");
        userName = (TextView) findViewById(R.id.username);
        tellphone = (TextView) findViewById(R.id.tellphone);
        head_portrait = (ImageView) findViewById(R.id.head_portrait);
        shengfengz= (TextView) findViewById(R.id.shengfengz);
        String str = SharedUtil.getSharedUtil().getString(this, ConstUtils.ESC);
        if (str != null) {
            tellphone.setText("");
            userName.setText("");
            head_portrait.setImageResource(R.mipmap.my_userimage);
            shengfengz.setText("");
        } else {
            if (myPagerResponse != null) {
                tellphone.setText(myPagerResponse.getData().getPhone().toString());
                userName.setText(myPagerResponse.getData().getUsername().toString());
                if(!TextUtils.isEmpty(myPagerResponse.getData().getCardNumber().toString())){
                    shengfengz.setText(myPagerResponse.getData().getCardNumber().toString());
                }
                Glide.with(this).load(myPagerResponse.getData().getImage().toString()).placeholder(R.mipmap.my_userimage)//加载时图片
                        .bitmapTransform(new CropCircleTransformation(this))
                        .crossFade(1000)
                        .error(R.mipmap.my_userimage)//错误
                        .into(head);
                loadUserImage();


            }
        }


    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getMyInfo(MyPagerResponse myPager) {
        myPagerResponse = myPager;
        L.e("----myPagerResponse----" + myPagerResponse.getData().getPhone().toString() + "--------" + myPagerResponse.getData().getUsername().toString());

    }

    private void selectImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            MultiImageSelector.create(this)
                    .showCamera(true) // 是否显示相机. 默认为显示
                    .single()// 单选模式
                    .start(this, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(My_Personal_Data.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (path != null) {
                    String imagePath = path.get(0);
                    file = new File(imagePath);
                    Glide.with(this).load(file).bitmapTransform(new CropCircleTransformation(this)).crossFade(1000).into(head);
                    prgress.setVisibility(View.VISIBLE);
                    loadUserImage();
                } else if (path == null) {
                    Glide.with(this).load(R.mipmap.my_userimage).into(head);
                }
            }

        }
    }

    //上传用户头像
    private void loadUserImage() {

        if (NetUtils.isConnected(this)) {
            Login_zud zyd = (Login_zud) SharedUtil.getSharedUtil().getObject(this, ConstUtils.IOGIN_INFO, null);
            HttpMessageInfo<UpdataUserImage> info = new HttpMessageInfo<>(BaseUrl.UPDETA_IMAGE, new UpdataUserImage());
            info.setiMessage(this);
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization",zyd.getData().getToken());
            headers.add("Content-Types", "image/png");
            OkHttpUtils.getInstance().upDownImage(OkHttpUtils.SINGLE_UPLOAD, info, headers, path, 1);
        } else {
            NetUtils.openSetting(this);
        }
    }


    @Override
    public void getServiceData(Object o) {
        if (o instanceof UpdataUserImage) {
            UpdataUserImage loadImage = (UpdataUserImage) o;
            if (loadImage.getState().equals("ok")) {
                prgress.setVisibility(View.GONE);
                ToastUtil.showShort(this, loadImage.getMessage());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ActivityCollector.startA(My_Personal_Data.this, MainActivity.class, "currMenu", 0);
                    }
                }, 1000);
            } else if (loadImage.getState().equals("warn")) {
                prgress.setVisibility(View.GONE);
                ToastUtil.showShort(this, loadImage.getMessage());
            } else if (loadImage.getState().equals("error")) {
                prgress.setVisibility(View.GONE);
                ToastUtil.showShort(this, loadImage.getMessage());
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_touxiang:

                    selectImage();
                break;
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.line3_phone:
                ActivityCollector.startA(this, ChangePhone.class);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
