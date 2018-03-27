package com.host.gp50.app.ui.activity.user;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.host.gp50.app.R;
import com.host.gp50.app.ui.activity.BaseActivity;
import com.host.gp50.app.ui.view.CircleImageView;
import com.host.gp50.app.utils.DensityUtil;
import com.host.gp50.app.utils.StatusbarUtils;
import com.host.gp50.app.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * com.host.gp50.app.ui.activity.user
 * <p>
 * 用户中心
 *
 * @author Administrator
 * @date 2017/12/01
 */

public class UserCenterActivity extends BaseActivity implements View.OnClickListener {
    /**
     * sd路径
     */
    private static String path = Environment.getExternalStorageDirectory().getPath() + "/UserHead/";
    private final String url = "/DemoHead/";
    private static final int TAKE_PICTURE = 1;
    private static final int CHOOSE_PICTURE = 2;
    private static final int CROP_SMALL_PICTURE = 3;

    @BindView(R.id.tittleBack)
    LinearLayout tittleBack;
    @BindView(R.id.tittleText)
    TextView tittleText;
    @BindView(R.id.tittleView)
    RelativeLayout tittleView;
    @BindView(R.id.civ_user)
    CircleImageView civUser;
    @BindView(R.id.rl_user_show)
    RelativeLayout rlUserShow;
    @BindView(R.id.tv_user_alias)
    TextView tvUserAlias;
    @BindView(R.id.ll_user_alias)
    LinearLayout llUserAlias;
    @BindView(R.id.tv_qr)
    ImageView tvQr;
    @BindView(R.id.ll_user_qr)
    LinearLayout llUserQr;
    @BindView(R.id.user_id)
    TextView userId;
    @BindView(R.id.ll_user_id)
    LinearLayout llUserId;
    @BindView(R.id.user_phone)
    TextView userPhone;
    @BindView(R.id.ll_user_phone)
    LinearLayout llUserPhone;
    @BindView(R.id.user_email)
    TextView userEmail;
    @BindView(R.id.ll_user_email)
    LinearLayout llUserEmail;
    @BindView(R.id.ll_modify_pw)
    LinearLayout llModifyPw;
    @BindView(R.id.ll_user_change)
    LinearLayout llUserChange;
    @BindView(R.id.ll_user_logout)
    LinearLayout llUserLogout;
    private Dialog dialog;
    private Bitmap head;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        ButterKnife.bind(this);
        StatusbarUtils.initAfterSetContentView(this, tittleView);

        initView();
    }

    private void initView() {
        DensityUtil.setViewLayoutParams(rlUserShow, (int) (screenWidth * 0.6), LinearLayout.LayoutParams.MATCH_PARENT);
        DensityUtil.setViewLayoutParams(civUser, (int) (screenWidth * 0.25), (int) (screenWidth * 0.25));
    }

    @OnClick({R.id.tittleBack, R.id.ll_user_alias, R.id.ll_user_qr, R.id.ll_user_id,
            R.id.ll_user_phone, R.id.ll_user_email, R.id.ll_modify_pw, R.id.ll_user_change,
            R.id.ll_user_logout, R.id.civ_user})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tittleBack:
                //返回
                finish();
                break;
            case R.id.ll_user_alias:
                //用户别名
                break;
            case R.id.ll_user_qr:
                //用户二维码
                intent = new Intent(this, UserQrCodeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.ll_user_id:
                //用户ID
                break;
            case R.id.ll_user_phone:
                //用户电话号码
                break;
            case R.id.ll_user_email:
                //用户邮箱
                break;
            case R.id.ll_modify_pw:
                //修改密码
                break;
            case R.id.ll_user_change:
                //切换用户
                intent = new Intent(this, UserLoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.ll_user_logout:
                //注销
                break;
            case R.id.civ_user:
                //头像
                showMenu();
                break;
            default:
                break;
        }
    }

    private void intiData() {
        //从Sd中找头像，转换成Bitmap
        Bitmap bt = BitmapFactory.decodeFile(path + "jadehead.jpg");
        if (bt != null) {
            //如果本地有头像图片的话
            civUser.setImageBitmap(bt);
//            Bitmap bitmap = FastBlurUtil.toBlur(bt, 5);
//            rlUserShow.setBackground(new BitmapDrawable(bitmap));
        } else {
            //如果本地没有头像图片则从服务器取头像，然后保存在SD卡中，本Demo的网络请求头像部分忽略
            Glide.with(this)
                    .load(url)
                    .into(civUser);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_take_photo:
                dialog.dismiss();
                try {
                    //开启相机应用程序获取并返回图片（capture：俘获）
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //指明存储图片或视频的地址URI
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                            "jadehead.jpg")));
                    //采用ForResult打开
                    startActivityForResult(intent2, CHOOSE_PICTURE);
                } catch (Exception e) {
                    ToastUtil.showToast(this, "相机权限未打开");
                }
                break;
            case R.id.tv_choose:
                dialog.dismiss();
                //返回被选中项的URI
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //得到所有图片的URI
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, TAKE_PICTURE);
                break;
            case R.id.cancel:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 如果返回码是可以用的
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //从相册里面取相片的返回结果
                case TAKE_PICTURE:
                    //裁剪图片
                    cropPhoto(data.getData());
                    break;
                //相机拍照后的返回结果
                case CHOOSE_PICTURE:
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/jadehead.jpg");
                    //裁剪图片
                    cropPhoto(Uri.fromFile(temp));
                    break;
                //调用系统裁剪图片后
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        head = extras.getParcelable("data");
                        if (head != null) {
                            /*
                             * 上传服务器代码
                             */
                            //保存在SD卡中
                            setPicToView(head);
                            //用ImageView显示出来
                            civUser.setImageBitmap(head);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        //找到指定URI对应的资源图片
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        //进入系统裁剪图片的界面
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        // 检测sd卡是否可用
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        // 创建以此File对象为名（path）的文件夹
        file.mkdirs();
        //图片名字
        String fileName = path + "jadehead.jpg";
        try {
            b = new FileOutputStream(fileName);
            // 把数据写入文件（compress：压缩）
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 显示dialog
     */
    private void showMenu() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_modify_portrait, null);
        TextView tvTakePhoto = (TextView) inflate.findViewById(R.id.tv_take_photo);
        TextView tvChoose = (TextView) inflate.findViewById(R.id.tv_choose);
        TextView cancel = (TextView) inflate.findViewById(R.id.cancel);

        //初始化控件
        tvTakePhoto.setOnClickListener(this);
        tvChoose.setOnClickListener(this);
        cancel.setOnClickListener(this);

        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出

        //获得窗体的属性
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        //设置Dialog距离底部的距离
        params.gravity = Gravity.BOTTOM | Gravity.CENTER;
        params.y = 20;
        //将属性设置给窗体
        dialogWindow.setAttributes(params);
        dialog.show();//显示对话框
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_back_in, R.anim.anim_back_out);
    }
}
