package com.example.q.myapplication;
//
import com.example.q.myapplication.HttpUtils.imageResponse;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.q.myapplication.HttpUtils.PostRequest_Interface;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PhotoActivity extends Activity {

    private static final int TAKE_PHOTO = 11;// 拍照
    private static final int CROP_PHOTO_CAMERA = 8;// 裁剪拍好的图片
    private static final int CROP_PHOTO_ABLUM = 9;  //裁剪相册中的
    private static final int LOCAL_CROP = 13;// 本地图库

    private Button choose_from_album;
    private Button take_photo;
    private ImageView picture;
    private Uri imageUri;// 拍照时的图片uri
    public Handler handler;

    private File tempFile;
    public File pngfile;//最后转成的png文件
    public Bitmap bitmap;
    public final  int SUCCESS=1;
    public final  int FAIL=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case SUCCESS:
                        picture.setImageBitmap(bitmap);
                        Toast.makeText(PhotoActivity.this,"获取成功",Toast.LENGTH_SHORT).show();
                        break;
                    case FAIL:
                        Toast.makeText(PhotoActivity.this,"获取失败",Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        setViews();// 初始化控件
        setListeners();// 设置监听
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        ImageButton back_search =(ImageButton) findViewById(R.id.returnhome1);
        back_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PhotoActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置监听
     */
    private void setListeners() {

        // 展示图片按钮点击事件
        choose_from_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 创建Intent，用于打开手机本地图库选择图片
                Intent intent1 = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // 启动intent打开本地图库
                startActivityForResult(intent1, LOCAL_CROP);
            }
        });

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取系統版本
                imageUri=null;
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                        "yyyy_MM_dd_HH_mm_ss");
                String filename = timeStampFormat.format(new Date());
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        filename + ".jpg");
                // 创建Intent，用于启动手机的照相机拍照
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

//                File takePhotoImage = new File(Environment.getExternalStorageDirectory(), "take_photo_image.jpg");
//                try {
//                    // 文件存在，删除文件
//                    if(takePhotoImage.exists()){
//                        takePhotoImage.delete();
//                    }
//                    // 根据路径名自动的创建一个新的空文件
//                    takePhotoImage.createNewFile();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
                //有存储卡的权限
                if (hasSdcard()) {

                    // 从文件中创建uri
                    Uri uri = Uri.fromFile(tempFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                    } else {
//                        //兼容android7.0 使用共享文件的形式
//                        ContentValues contentValues = new ContentValues(1);
//                        contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
//                        Uri uri = getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                    }

                    // 获取图片文件的uri对象
                    imageUri = Uri.fromFile(tempFile);

                    // 指定输出到文件uri中
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    // 启动intent开始拍照
                    startActivityForResult(intent, TAKE_PHOTO);
                }
            }

            /*
//     * 判断sdcard是否被挂载
//     */
            private boolean hasSdcard() {
                //判断ＳＤ卡手否是安装好的media_mounted
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    /**
     *
     */
    /**
     * 调用startActivityForResult方法启动一个intent后，
     * 可以在该方法中拿到返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){

            case TAKE_PHOTO:// 拍照

                if(resultCode == RESULT_OK){
                    // 创建intent用于裁剪图片
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    // 设置数据为文件uri，类型为图片格式
                    intent.setDataAndType(imageUri,"image/*");
                    // 允许裁剪
                    intent.putExtra("scale",true);
                    // 裁剪后返回数据
                    intent.putExtra("return-data", true);
//                    // 指定输出到文件uri中
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    // 启动intent，开始裁剪
                    startActivityForResult(intent, CROP_PHOTO_CAMERA);
                }
                break;
            case LOCAL_CROP:// 系统图库

                if(resultCode == RESULT_OK){
                    // 创建intent用于裁剪图片
                    Intent intent1 = new Intent("com.android.camera.action.CROP");
                    // 获取图库所选图片的uri
                    Uri uri = data.getData();
                    intent1.setDataAndType(uri,"image/*");
                    //  设置裁剪图片的宽高
                    intent1.putExtra("outputX", 300);
                    intent1.putExtra("outputY", 300);
                    // 裁剪后返回数据
                    intent1.putExtra("return-data", true);
                    // 启动intent，开始裁剪
                    startActivityForResult(intent1, CROP_PHOTO_ABLUM);
                }

                break;
            case CROP_PHOTO_ABLUM:
                if(resultCode==RESULT_OK){
                    try{
                        bitmap = data.getExtras().getParcelable("data");
                        pngfile=saveBitmapFile(bitmap);
                        new Thread(){
                            public void run(){
                                request();
                            }
                        }.start();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case CROP_PHOTO_CAMERA:// 裁剪后展示图片
                if(resultCode == RESULT_OK){
                    try{
                        if(imageUri != null){
                            // 创建BitmapFactory.Options对象
                            BitmapFactory.Options option = new BitmapFactory.Options();
                            // 属性设置，用于压缩bitmap对象
                            option.inSampleSize = 2;
                            option.inPreferredConfig = Bitmap.Config.RGB_565;
                            // 根据文件流解析生成Bitmap对象
                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, option);
//                            bitmap = data.getParcelableExtra("data");
                            pngfile=saveBitmapFile(bitmap);
                            new Thread(){
                                public void run(){
                                    request();
                                }
                            }.start();
                        // 展示图库中选择裁剪后的图片
//                        if(data != null&&imageUri==null){
//                            // 根据返回的data，获取Bitmap对象
//                            bitmap = data.getExtras().getParcelable("data");
//                            pngfile=saveBitmapFile(bitmap);
//                            new Thread(){
//                                public void run(){
//                                    request();
//                                }
//                            }.start();
//                            // 展示图片
//                            picture.setImageBitmap(bitmap);

//                        }

                        // 展示拍照后裁剪的图片

//                            // 展示图片
//                            picture.setImageBitmap(bitmap);
                        }



                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;

        }

    }
    public void request(){
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.31.234:8000/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        final PostRequest_Interface request = retrofit.create(PostRequest_Interface.class);
//        String username="xue";
//        String password="xue123456";
//        String base=username+":"+password;
//        String authorization="Basic "+Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        MediaType textType = MediaType.parse("text/plain");
        RequestBody username = RequestBody.create(textType, "xue");
        RequestBody password = RequestBody.create(textType, "xue123456");
//        RequestBody name = RequestBody.create("text","11");
//        RequestBody age = RequestBody.create(MediaType.parse("text"), "24");
        RequestBody files = RequestBody.create(MediaType.parse("multipart/form-data"),pngfile);
//        RequestBody files = RequestBody.create(MediaType.parse("application/octet-stream"),pngfile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("FILES", "file", files);
        //对 发送请求 进行封装
        Call<imageResponse> call = request.getImageResponseCall(username,password,filePart);

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<imageResponse>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<imageResponse> call, Response<imageResponse> response) {

                response.body();
                Message msg=new Message();

                msg.obj=response.body();
                msg.what=SUCCESS;
                handler.sendMessage(msg);

                // 步骤7：处理返回的数据结果
                System.out.print("helloworld");
                //response.body就是我声明的类
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<imageResponse> call, Throwable throwable) {
                Message msg=new Message();

                msg.obj=null;
                msg.what=FAIL;
                handler.sendMessage(msg);
                System.out.println("连接失败");
            }
        });
    }

    /**
     //     * 把bitmap 转file
     //     * @param bitmap
     //
     //     */
    public static File saveBitmapFile(Bitmap bitmap){
        File file=new File(Environment.getExternalStorageDirectory(),
                "IMG_20190104_162811" + ".png");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    /**
     * 控件初始化
     */
    private void setViews() {
        choose_from_album = (Button)findViewById(R.id.choose_from_album);
        take_photo=(Button)findViewById(R.id.take_photo);
        picture = (ImageView)findViewById(R.id.picture);
    }
}
