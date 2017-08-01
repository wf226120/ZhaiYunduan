package com.jlgproject.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.MainActivity;
import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.AddDebtResponse;
import com.jlgproject.model.AddDebtVo;
import com.jlgproject.model.Asset;
import com.jlgproject.model.DebtRelation1Vo;
import com.jlgproject.model.DebtRelation2Vo;
import com.jlgproject.model.DebtRelationVo;
import com.jlgproject.model.LoadImage;
import com.jlgproject.model.Login_zud;
import com.jlgproject.model.AssetRequest;
import com.jlgproject.model.Yzm;
import com.jlgproject.model.eventbusMode.AddDebtBean;
import com.jlgproject.model.eventbusMode.AssetEvent;
import com.jlgproject.model.eventbusMode.DebtBeiAn;
import com.jlgproject.model.eventbusMode.DebtPreson;
import com.jlgproject.model.eventbusMode.IntentDebtPresonBean;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.BitmapUtils;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.DialogUtils;
import com.jlgproject.util.JsonUtil;
import com.jlgproject.util.L;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.RequestBody;


/**
 * Created by sunbeibei on 2017/5/14.
 * 上传照片的activity
 */

public class UpdownPhotos extends BaseActivity implements View.OnClickListener, HttpMessageInfo.IMessage {

    private TextView mTv_Title_up;
    private ImageView mIv_Title_left_up;
    private LinearLayout mLl_ivParent_title_up;
    private GridView gridView;
    private Button btn;
    private static final int REQUEST_IMAGE = 2;
    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private ArrayList<String> path = null;
    private UpdownPhotoAdapter adapter;
    private ImageView iv_title_right;
    private android.app.AlertDialog alertDialog;
    private int addpic;
    //债事备案
    private DebtRelationVo debtRelationVo;
    private DebtRelation1Vo debtRelation1Vo;
    private DebtRelation2Vo debtRelation2Vo;
    //新增资产
    private AssetRequest dataAssets;
    //编辑资产
    private Asset assetsNew;
    //添加债事人 ||债事企业
    private AddDebtVo addDebtVob;
    private List<Bitmap> bitmapList;
    private ArrayList<String> pathList = null;
    private String isOwer = null;
    private int backPager;

    @Override
    public int loadWindowLayout() {
        return R.layout.updownphoto;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        addpic = getIntent().getIntExtra("addpic", 9);
        //动态设置标题
        mTv_Title_up = (TextView) findViewById(R.id.tv_title_name);
        mIv_Title_left_up = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_up.setVisibility(View.VISIBLE);
        mLl_ivParent_title_up = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_title_up.setOnClickListener(this);
        iv_title_right = (ImageView) findViewById(R.id.iv_title_right);
        iv_title_right.setImageResource(R.mipmap.return_home);
        iv_title_right.setVisibility(View.VISIBLE);
        iv_title_right.setOnClickListener(this);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

        //gridview
        gridView = (GridView) findViewById(R.id.grid);
        //适配器
        adapter = new UpdownPhotoAdapter(UpdownPhotos.this, addpic);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //判断最后一张点击增加照片
                if (position == parent.getCount() - 1) {
                    selectImage();
                    adapter.notifyDataSetChanged();
                }
            }
        });


        if (addpic == 0) {
            mTv_Title_up.setText(getResources().getText(R.string.date_xx));
        } else if (addpic == 1) {
            mTv_Title_up.setText("债事企业");
        } else if (addpic == 3) {
            mTv_Title_up.setText("债事人信息");
        } else if (addpic == 4) {
            mTv_Title_up.setText("编辑资产");
            editorAssets();
        } else if (addpic == 5) {
            mTv_Title_up.setText("新增资产");
        } else if (addpic == 6) {
            mTv_Title_up.setText("债事企业");
        }
    }

    private void editorAssets() {
        if (assetsNew.getPicUrl() != null && assetsNew.getPicUrl().size() > 0) {
            pathList = (ArrayList<String>) assetsNew.getPicUrl();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = handler.obtainMessage();
                    message.what = 123;
                    message.obj = BitmapUtils.getBitmap(pathList);
                    handler.sendMessage(message);
                }
            }).start();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 123) {
                bitmapList = (List<Bitmap>) msg.obj;
                path = BitmapUtils.saveBitmapToJpg(UpdownPhotos.this, bitmapList);
                adapter.setPath(path);
                gridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getDebePager1(DebtRelation1Vo debt1) {
        debtRelation1Vo = debt1;
        L.e("----debtRelation11111111Vo.getProof()----" + debtRelation1Vo.getRecommend() + "---" + debtRelation1Vo.getNature());
    }

    //债市备案 第二页数据
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getDebePager2(DebtRelation2Vo debt2) {
        debtRelation2Vo = debt2;
    }

    //新增债事人 债事企业
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getDebePerson(AddDebtVo add) {
        addDebtVob = add;
//        L.e("----addDebtVob----" + addDebtVob.getDebtCompany().getDebtCompanyName());
        isOwer = addDebtVob.getIsOwer();
    }

    //新增资产
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getAssets(AssetRequest info) {
        dataAssets = info;
    }

    //编辑资产
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void geteditAssets(Asset asset) {
        assetsNew = asset;
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void geteditAssets(IntentDebtPresonBean intentDebtPresonBean) {
        backPager = intentDebtPresonBean.getBackDebtPager();
    }


    //进入图库选择照片或者拍照
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
                    .count(9) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                    .multi() // 多选模式, 默认模式;
                    .origin(path) // 默认已选择图片. 只有在选择模式为多选时有效
                    .start(this, REQUEST_IMAGE);
        }
    }

    //访问相机和图库的权限
    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(UpdownPhotos.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    //访问回调结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //显示保存图片确定弹窗
    public void customDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.customdolig, null);
        int with = getWindowManager().getDefaultDisplay().getWidth();
        alertDialog = DialogUtils.getBuilder(view, this, with);
        Button button = (Button) view.findViewById(R.id.confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                L.e("-----onActivityR---" + path.get(0) + "-----" + path.size());
                adapter.setPath(path);
                gridView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        if (v == mLl_ivParent_title_up) {
            finish();
        } else if (v == iv_title_right) { //返回首页
            startActivity(new Intent(UpdownPhotos.this, MainActivity.class));
        } else if (v == btn) {//上传

            if (ShowDrawDialog(this)) {
                if (path != null) {
                    HttpMessageInfo<LoadImage> info = new HttpMessageInfo<LoadImage>(BaseUrl.LOAD_IMAGE, new LoadImage());
                    info.setiMessage(this);
                    AddHeaders headers = new AddHeaders();
                    headers.add("Content-Types", "image/png");
                    Login_zud zud = (Login_zud) SharedUtil.getSharedUtil().getObject(this, ConstUtils.IOGIN_INFO, null);
                    headers.add("Authorization", zud.getData().getToken());
                    OkHttpUtils.getInstance().upDownImage(OkHttpUtils.MUCH_UPLOAD, info, headers, path, 1);
                } else {
                    if (addpic == 0) {//债事备案
                        debtRelationVo = new DebtRelationVo(debtRelation1Vo, debtRelation2Vo, null);
                        addDebtInfo(debtRelationVo);
                    } else if (addpic == 1) {
                        customDialog();
                    } else if (addpic == 3) {//添加债事人
                        addDebtVob.setDebtCompany(null);
                        addDebtVob.getDebtPerson().setPicList(null);
                        addDebtPresonOrQy(addDebtVob);
//                    customDialog();
                    } else if (addpic == 4) {//编辑资产
                        addAsserts();
                    } else if (addpic == 5) {//新增资产
                        addAsserts();
                    } else if (addpic == 6) {//添加债事企业
                        addDebtVob.setDebtPerson(null);
                        addDebtVob.getDebtCompany().setPicList(null);
                        addDebtPresonOrQy(addDebtVob);
                    }
                }
            }
        }
    }

    //添加债事备案
    public void addDebtInfo(DebtRelationVo debtRelationVo) {
        if (ShowDrawDialog(this)) {
            String json = JsonUtil.toJson(debtRelationVo);
            Login_zud login = (Login_zud) SharedUtil.getSharedUtil().getObject(this, ConstUtils.IOGIN_INFO, null);
            HttpMessageInfo<AddDebtResponse> info = new HttpMessageInfo<AddDebtResponse>(BaseUrl.ADD_DEBT, new AddDebtResponse());
            info.setiMessage(this);
            L.e("------json--**" + json);
            info.setFormBody(RequestBody.create(ConstUtils.JSON, json));
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", login.getData().getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, headers, 1);
        }
    }

    //添加债市自然人
    public void addDebtPresonOrQy(AddDebtVo debtPerson) {
        if (ShowDrawDialog(this)) {
            String jsonR = JsonUtil.toJson(debtPerson);
            L.e("---新增债事人 & 企业---json--**" + jsonR);
            HttpMessageInfo<Yzm> info = new HttpMessageInfo<Yzm>(BaseUrl.ADD_DEBT_PRESON, new Yzm());
            info.setiMessage(this);
            info.setFormBody(RequestBody.create(ConstUtils.JSON, jsonR));
            Login_zud login = (Login_zud) SharedUtil.getSharedUtil().getObject(this, ConstUtils.IOGIN_INFO, null);
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", login.getData().getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, headers, 1);
        }
    }

    //资产
    public void addAsserts() {
        if (ShowDrawDialog(this)) {
            HttpMessageInfo<Yzm> info2 = null;
            String plist = null;
            if (addpic == 4) {//编辑资产
                plist = JsonUtil.toJson(assetsNew);
                info2 = new HttpMessageInfo<>(BaseUrl.EDITOR_ASSERTS, new Yzm());
            } else if (addpic == 5) { //新增资产
                plist = JsonUtil.toJson(dataAssets);
                info2 = new HttpMessageInfo<>(BaseUrl.ADD_ASSERTS, new Yzm());
            }
            info2.setiMessage(this);
            info2.setFormBody(RequestBody.create(ConstUtils.JSON, plist));
            Login_zud login = (Login_zud) SharedUtil.getSharedUtil().getObject(this, ConstUtils.IOGIN_INFO, null);
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", login.getData().getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info2, null, headers, 1);
        }
    }


    @Override
    public void getServiceData(Object o) {
        if (o instanceof LoadImage) {//上传图片返回
            LoadImage loadImage = (LoadImage) o;
            if (loadImage != null) {
                DismissDialog();
                if (loadImage.getState().equals("ok")) {
                    List<String> str = loadImage.getData();
                    if (addpic == 0) {//债事备案
                        debtRelationVo = new DebtRelationVo(debtRelation1Vo, debtRelation2Vo, str);
                        addDebtInfo(debtRelationVo);
                    } else if (addpic == 3) {//添加债事人
                        addDebtVob.setDebtCompany(null);
                        addDebtVob.getDebtPerson().setPicList(str);
                        addDebtPresonOrQy(addDebtVob);
                    } else if (addpic == 4) {//编辑资产
                        assetsNew.setPicUrl(str);
                        addAsserts();
                    } else if (addpic == 5) {//新增资产
                        dataAssets.setPicUrl(str);
                        addAsserts();
                    } else if (addpic == 6) {
                        L.e("---6-----债事企业------");
                        addDebtVob.setDebtPerson(null);
                        addDebtVob.getDebtCompany().setPicList(str);
                        addDebtPresonOrQy(addDebtVob);
                    }
                } else if (loadImage.getState().equals("warn")) {
                    ToastUtil.showShort(this, "上传失败，请稍候重试");
                }
            }


        } else if (o instanceof AddDebtResponse) {//新增债事备案
            final AddDebtResponse zh = (AddDebtResponse) o;
            if (zh != null) {
                DismissDialog();
                if (zh.getState().equals("warn")) {
                    if (zh.getMessage().equals("该条债务已存在")) {
                        ToastUtil.showShort(UpdownPhotos.this, zh.getMessage());
                    } else if (zh.getMessage().equals("您不是债事人，请添加您的证件信息")) {  //登录用户不是债事人
                        DialogUtils.showDialogZsr(this, true, zh.getMessage().toString(), new DialogUtils.OnButtonEventListener() {
                            @Override
                            public void onConfirm() {
                                //当IS_OWER 值为 1时 表示登录用户 不是债事人
                                ActivityCollector.startA(UpdownPhotos.this, New_Debt_Matter_Preson.class, ConstUtils.IS_OWER, 1);
                            }

                            @Override
                            public void onCancel() {
                            }
                        });
                    }
                } else if (zh.getState().equals("ok")) {
                    if (zh.getMessage().equals("债事备案成功,未支付")) {
                        DialogUtils.showConfirmDialog(this, zh.getMessage(), "确认", true, new DialogUtils.OnButtonEventListenerConfirm() {
                            @Override
                            public void onConfirm() {
                                String orderId = zh.getData().getRelation().getOrderId();
                                String money = zh.getData().getRelation().getQianshu();//支付金额用于页面显示
                                AddDebtBean addDebtBean = new AddDebtBean(orderId, money, 0);
                                EventBus.getDefault().postSticky(addDebtBean);
                                EventBus.getDefault().postSticky(new DebtBeiAn("1"));
                                ActivityCollector.startA(UpdownPhotos.this, ToPay.class);
                            }
                        });
                    }
                } else if (zh.getState().equals("error")) {
                    ToastUtil.showShort(this, zh.getMessage());
                }
            }

        } else if (o instanceof Yzm) {//添加债事人  & 债事企业
            final Yzm zst = (Yzm) o;
            if (zst != null) {
                DismissDialog();
                if (zst.getState().equals("ok")) {//添加成功
                    if (zst.getMessage().equals("新增资产成功！")) {
                        ToastUtil.showShort(this, zst.getMessage());
                        EventBus.getDefault().postSticky(new AssetEvent(1));
                    } else if (zst.getMessage().equals("会员添加债事人成功")) {//添加的是登录用户的
                        ToastUtil.showShort(this, zst.getMessage());
                        EventBus.getDefault().postSticky(new DebtPreson(1));
                        ActivityCollector.startA(this, New_Debt_Matter_Preson.class, "finish", 1);
                    } else if (zst.getMessage().equals("会员添加债事企业成功")) {//添加的是登录用户的
                        EventBus.getDefault().postSticky(new DebtPreson(1));
                        ActivityCollector.startA(this, New_Debt_Matter_Preson.class, "finish", 1);
                        ToastUtil.showShort(this, zst.getMessage());
                    } else if (zst.getMessage().equals("添加债事人成功")) {
                        EventBus.getDefault().postSticky(new DebtPreson(1));
                        ToastUtil.showShort(this, zst.getMessage());
                        if (backPager == 1) {
                            ActivityCollector.startA(UpdownPhotos.this, New_Debt_Matter.class);
                        } else {
                            L.e("------返回债事人页---债事人---");
                            ActivityCollector.startA(this, MainActivity.class, "currMenu", 2);
                        }
                    } else if (zst.getMessage().equals("添加债事企业成功")) {
                        EventBus.getDefault().postSticky(new DebtPreson(1));
                        ToastUtil.showShort(this, zst.getMessage());
                        if (backPager == 1) {// 债事备案没有该用户时 备完案返回 备案第一页
                            ActivityCollector.startA(UpdownPhotos.this, New_Debt_Matter.class);
                        } else { //普通用户添加债事人，添加完返回到债事人管理页
                            L.e("------返回债事人页---债事企业---");
                            ActivityCollector.startA(this, MainActivity.class, "currMenu", 2);
                        }
                    } else if (zst.getMessage().equals("编辑资产信息成功")) {
                        ToastUtil.showShort(this, zst.getMessage());
                    }
                    finish();
                } else if (zst.getState().equals("warn")) {//添加失败
                    ToastUtil.showShort(this, zst.getMessage());
                } else if (zst.getState().equals("error")) {
                    ToastUtil.showShort(this, zst.getMessage());
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        ToastUtil.showShort(this, "服务器繁忙，请稍后再试");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
