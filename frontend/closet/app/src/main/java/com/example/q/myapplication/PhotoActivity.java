package com.example.q.myapplication;
//
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//
//public class PhotoActivity extends AppCompatActivity {
//    private ImageView imageView;
//    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.png");//设置录像存储路径
//    private Uri imageUri = Uri.fromFile(file);
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_photo);
//
//        imageView = findViewById(R.id.picture);
//        ImageButton returnhome1=findViewById(R.id.returnhome1);
//        returnhome1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(PhotoActivity.this,HomeActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button upload=findViewById(R.id.upload);
//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(PhotoActivity.this,HomeActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button chooseFromAlbum = findViewById(R.id.choose_from_album);
//        Button takePhoto = findViewById(R.id.take_photo);
//        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (ContextCompat.checkSelfPermission(PhotoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
//                        PackageManager.PERMISSION_GRANTED){
//                    ActivityCompat.requestPermissions(PhotoActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                }else{
//                    openAlbum();
//                }
//            }
//        });
//
//        takePhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //创建File对象，用于存储拍照后的图片
//
//                File outputImage = new File(getExternalCacheDir(), "image.jpg");
//                if (outputImage.exists()) {
//                    outputImage.delete();
//                }
//                try {
//                    outputImage.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (Build.VERSION.SDK_INT >= 24) {
//                    imageUri = FileProvider.getUriForFile(PhotoActivity.this, "com.example.cameraalbumtest.fileprovider", outputImage);
//                } else {
//                    imageUri = Uri.fromFile(outputImage);
//                }
//                //启动相机程序
//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                startActivityForResult(intent, 1);
//            }
//        });
//    }
//
//    private void openAlbum() {
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/*");
//        startActivityForResult(intent,2);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    Bitmap bitmap = null;
//                    try {
//                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                        imageView.setImageBitmap(bitmap);
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            case 2:
//                if (resultCode==RESULT_OK){
//                    //判断系统版本号 因为4.4以上系统不在返回图片真实Uri了，而是一个封装的Uri，所以如果是4.4以上需要对这个Uri解析
//                    if (Build.VERSION.SDK_INT>=19){
//                        handleImageOnKitKat(data);
//                    }else{
//                        handleImageBeforeKitKat(data);
//                    }
//                }
//            default:
//        }
//    }
//
//    private void handleImageBeforeKitKat(Intent data) {
//        Uri uri = data.getData();
//        String imagePath = getImagePath(uri,null);
//        displayImage(imagePath);
//    }
//
//    @TargetApi(19)
//    private void handleImageOnKitKat(Intent data) {
//        String imagePath = null;
//        Uri uri = data.getData();
//
//        if (DocumentsContract.isDocumentUri(this,uri)){
//            //如果是document类型的uri 则通过document id处理
//            String docId = DocumentsContract.getDocumentId(uri);
//
//            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
//
//                // 解析出数字格式的id
//                String id = docId.split(":")[1];
//
//                String selection = MediaStore.Images.Media._ID+"="+id;
//
//                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
//
//            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
//
//                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
//
//                imagePath = getImagePath(contentUri,null);
//            }
//        }else if ("content".equalsIgnoreCase(uri.getScheme())){
//
//            //如果是content类型的Uri，则使用普通方式处理
//
//            imagePath = getImagePath(uri,null);
//
//        }else if("file".equalsIgnoreCase(uri.getScheme())){
//
//            //如果是file类型的Uri，则直接获取图片路径
//            imagePath=  uri.getPath();
//        }
//        displayImage(imagePath);
//    }
//
//    private void displayImage(String imagePath) {
//        if (imagePath!=null){
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            imageView.setImageBitmap(bitmap);
//        }else{
//            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public String getImagePath(Uri uri,String selection) {
//        String path = null;
//        //通过uri和selection来获取真实的图片路径
//        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
//        if (cursor!=null){
//            if (cursor.moveToNext()){
//                path=  cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            }
//            cursor.close();
//        }
//        return path;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case 1:
//                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                    openAlbum();
//                }else{
//                    Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }
//
//
//}

public class PhotoActivity extends Activity {

    private static final int TAKE_PHOTO = 11;// 拍照
    private static final int CROP_PHOTO = 12;// 裁剪图片
    private static final int LOCAL_CROP = 13;// 本地图库

    private Button choose_from_album;
    private Button take_photo;
    private ImageView picture;
    private Uri imageUri;// 拍照时的图片uri


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                startActivityForResult(intent1,LOCAL_CROP);


            }
        });

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File takePhotoImage = new File(Environment.getExternalStorageDirectory(), "take_photo_image.jpg");
                try {
                    // 文件存在，删除文件
                    if(takePhotoImage.exists()){
                        takePhotoImage.delete();
                    }
                    // 根据路径名自动的创建一个新的空文件
                    takePhotoImage.createNewFile();
                }catch (Exception e){
                    e.printStackTrace();
                }
                // 获取图片文件的uri对象
                imageUri = Uri.fromFile(takePhotoImage);
                // 创建Intent，用于启动手机的照相机拍照
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定输出到文件uri中
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                // 启动intent开始拍照
                startActivityForResult(intent, TAKE_PHOTO);


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
                    // 指定输出到文件uri中
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                    // 启动intent，开始裁剪
                    startActivityForResult(intent, CROP_PHOTO);
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
                    startActivityForResult(intent1, CROP_PHOTO);
                }

                break;
            case CROP_PHOTO:// 裁剪后展示图片
                if(resultCode == RESULT_OK){
                    try{
                        // 展示拍照后裁剪的图片
                        if(imageUri != null){
                            // 创建BitmapFactory.Options对象
                            BitmapFactory.Options option = new BitmapFactory.Options();
                            // 属性设置，用于压缩bitmap对象
                            option.inSampleSize = 2;
                            option.inPreferredConfig = Bitmap.Config.RGB_565;
                            // 根据文件流解析生成Bitmap对象
                            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, option);
                            // 展示图片
                            picture.setImageBitmap(bitmap);
                        }

                        // 展示图库中选择裁剪后的图片
                        if(data != null){
                            // 根据返回的data，获取Bitmap对象
                            Bitmap bitmap = data.getExtras().getParcelable("data");
                            // 展示图片
                            picture.setImageBitmap(bitmap);

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;

        }

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