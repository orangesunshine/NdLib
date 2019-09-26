package com.orange.utils.constant;

import android.os.Build;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.Manifest.permission;
import static android.Manifest.permission_group;

/**
 * @ProjectName: NdLib
 * @Package: com.orange.utils.constant
 * @ClassName: UtilsConst
 * @Description:
 * @Author: orange
 * @CreateDate: 2019/7/15 9:08
 * @UpdateUser:
 * @UpdateDate: 2019/7/15 9:08
 * @UpdateRemark:
 * @Version: 1.0
 */
public final class UtilsConst {
    // <editor-fold defaultstate="collapsed" desc="内存">
    //内存单位
    public static final int B = 0b1;
    public static final int KB = B << 10;
    public static final int MB = KB << 10;
    public static final int GB = MB << 10;

    @IntDef({B, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    @interface MemoryUnit {

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="权限">
    public static final String GROUP_PERMISSION_CALENDAR = permission_group.CALENDAR;
    public static final String GROUP_PERMISSION_CAMERA = permission_group.CAMERA;
    public static final String GROUP_PERMISSION_CONTACTS = permission_group.CONTACTS;
    public static final String GROUP_PERMISSION_LOCATION = permission_group.LOCATION;
    public static final String GROUP_PERMISSION_MICROPHONE = permission_group.MICROPHONE;
    public static final String GROUP_PERMISSION_PHONE = permission_group.PHONE;
    public static final String GROUP_PERMISSION_SENSORS = permission_group.SENSORS;
    public static final String GROUP_PERMISSION_SMS = permission_group.SMS;
    public static final String GROUP_PERMISSION_STORAGE = permission_group.STORAGE;

    private static final String[] GROUP_PERMISSION_CALENDAR_CONTENT = {permission.READ_CALENDAR, permission.WRITE_CALENDAR};
    private static final String[] GROUP_PERMISSION_CAMERA_CONTENT = {permission.CAMERA};
    private static final String[] GROUP_PERMISSION_CONTACTS_CONTENT = {permission.READ_CONTACTS, permission.WRITE_CONTACTS, permission.GET_ACCOUNTS};
    private static final String[] GROUP_PERMISSION_LOCATION_CONTENT = {permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION};
    private static final String[] GROUP_PERMISSION_MICROPHONE_CONTENT = {permission.RECORD_AUDIO};
    private static final String[] GROUP_PERMISSION_PHONE_CONTENT = {permission.READ_PHONE_STATE, permission.READ_PHONE_NUMBERS, permission.CALL_PHONE, permission.READ_CALL_LOG, permission.WRITE_CALL_LOG, permission.ADD_VOICEMAIL, permission.USE_SIP, permission.PROCESS_OUTGOING_CALLS, permission.ANSWER_PHONE_CALLS};
    private static final String[] GROUP_PERMISSION_PHONE_BELOW_O = {permission.READ_PHONE_STATE, permission.READ_PHONE_NUMBERS, permission.CALL_PHONE, permission.READ_CALL_LOG, permission.WRITE_CALL_LOG, permission.ADD_VOICEMAIL, permission.USE_SIP, permission.PROCESS_OUTGOING_CALLS};
    private static final String[] GROUP_PERMISSION_SENSORS_CONTENT = {permission.BODY_SENSORS};
    private static final String[] GROUP_PERMISSION_SMS_CONTENT = {permission.SEND_SMS, permission.RECEIVE_SMS, permission.READ_SMS, permission.RECEIVE_WAP_PUSH, permission.RECEIVE_MMS,};
    private static final String[] GROUP_PERMISSION_STORAGE_CONTENT = {permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 获取权限组下的所有权限
     *
     * @param group
     * @return
     */
    public static String[] group2Permisssions(String group) {
        switch (group) {
            case GROUP_PERMISSION_CALENDAR:
                return GROUP_PERMISSION_CALENDAR_CONTENT;
            case GROUP_PERMISSION_CAMERA:
                return GROUP_PERMISSION_CAMERA_CONTENT;
            case GROUP_PERMISSION_CONTACTS:
                return GROUP_PERMISSION_CONTACTS_CONTENT;
            case GROUP_PERMISSION_LOCATION:
                return GROUP_PERMISSION_LOCATION_CONTENT;
            case GROUP_PERMISSION_MICROPHONE:
                return GROUP_PERMISSION_MICROPHONE_CONTENT;
            case GROUP_PERMISSION_PHONE:
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    return GROUP_PERMISSION_PHONE_BELOW_O;
                }
                return GROUP_PERMISSION_PHONE_CONTENT;
            case GROUP_PERMISSION_SENSORS:
                return GROUP_PERMISSION_SENSORS_CONTENT;
            case GROUP_PERMISSION_SMS:
                return GROUP_PERMISSION_SMS_CONTENT;
            case GROUP_PERMISSION_STORAGE:
                return GROUP_PERMISSION_STORAGE_CONTENT;
        }
        return new String[]{group};
    }
// </editor-fold>
}
