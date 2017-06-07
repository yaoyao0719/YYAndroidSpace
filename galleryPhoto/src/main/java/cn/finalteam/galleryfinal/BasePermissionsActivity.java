package cn.finalteam.galleryfinal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by yaoyao on 2016-05-18.
 */
public class BasePermissionsActivity extends Activity {

    private Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
   }

    private int permissionRequestCode = 88;
    private PermissionCallback permissionRunnable ;
    /**
     * 权限申请对应的提示语
     */
    private static final String CAMERA_TIP="请在设置-应用-天天投-权限中开启相机权限，以正常使用拍照功能";
    private static final String RECORD_TIP="请在设置-应用-天天投-权限中开启麦克风权限，以正常使用语音功能";
    private static final String LOCATION_TIP="请在设置-应用-天天投-权限中开启位置信息权限，以正常使用位置功能";
    private static final String STORAGE_TIP="请在设置-应用-天天投-权限中开启存储空间权限，以正常使用天天投功能";
    private static final String CONTACTS_TIP="请在设置-应用-天天投-权限中开启通讯录权限，以正常使用人脉功能";
    private static final String PHONE_TIP="天天投使用电话权限确定本机设备ID，以保证账号登录的安全性。天天投不会拨打其他号码或者终止通话。\n" +
            "请在设置-应用-天天投-权限中开启电话权限，以正常登录天天投";
    public interface PermissionCallback{
        void hasPermission();
        void noPermission();
    }

    /**
     * Android M运行时权限请求封装
     * @param runnable 请求权限回调
     * @param permissions 请求的权限（数组类型），直接从Manifest中读取相应的值，eg：android.permission.WRITE_CONTACTS
     */
    public void performCodeWithPermission(PermissionCallback runnable,@NonNull String... permissions){
        if (permissions == null || permissions.length == 0) {
            return;
        }

        this.permissionRunnable = runnable;
        /**
         * 如果Android SDK低于23或者已经获得权限，则直接执行对应方法
         */
        if((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || checkPermissionGranted(permissions)){
            if(permissionRunnable!=null){
                permissionRunnable.hasPermission();
                permissionRunnable = null;
            }
        }else{
            requestPermission(permissionRequestCode,permissions);
        }

    }
    private boolean checkPermissionGranted(String[] permissions){
        boolean flag = true;
        for(String p:permissions){
            if(ActivityCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED){
                flag = false;
                break;
            }
        }
        return flag;
    }
    private void requestPermission(final int requestCode,final String[] permissions){
        ActivityCompat.requestPermissions(BasePermissionsActivity.this, permissions, requestCode);
    }

    /**
     * 申请权限后的回调，即处理结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == permissionRequestCode){
            if(verifyPermissions(grantResults)){
                if(permissionRunnable!=null) {
                    permissionRunnable.hasPermission();
                    permissionRunnable = null;
                }
            } else {
                /**
                 * 一般一次只请求一个权限，所以只取permissions中的第一个
                 */
                String tip = "";
                if (permissions != null && permissions.length > 0) {
                    if (permissions[0].equals("android.permission.READ_EXTERNAL_STORAGE")||permissions[0].equals("android.permission.WRITE_EXTERNAL_STORAGE\n")) {
                        tip = STORAGE_TIP;
                    }
                    if (permissions[0].equals("android.permission.CAMERA")) {
                        tip = CAMERA_TIP;
                    }
                    if (permissions[0].equals("android.permission.READ_CONTACTS")) {
                        tip = CONTACTS_TIP;
                    }
                    if (permissions[0].equals("android.permission.READ_PHONE_STATE")) {
                        tip = PHONE_TIP;
                    }
                    if (permissions[0].equals("android.permission.ACCESS_FINE_LOCATION")||permissions[0].equals("android.permission.ACCESS_COARSE_LOCATION")) {
                        tip = LOCATION_TIP;
                    }
                    if (permissions[0].equals("android.permission.RECORD_AUDIO")) {
                        tip = RECORD_TIP;
                    }
                }
                new AlertDialog.Builder(mContext)
                        .setTitle("权限申请")
                        .setMessage(tip)
                        .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(Settings.ACTION_APPLICATION_SETTINGS);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消",null)
                        .show();
                if(permissionRunnable!=null) {
                    permissionRunnable.noPermission();
                    permissionRunnable = null;
                }
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }
    public boolean verifyPermissions(int[] grantResults) {
        if(grantResults.length < 1){
            return false;
        }

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    /**
     * 判断是否获得权限
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions){
        boolean flag = false;
        for(String p:permissions){
            if (ActivityCompat.shouldShowRequestPermissionRationale(BasePermissionsActivity.this,p)){
                flag = true;
                break;
            }
        }
        return flag;
    }

}
