/*    */ package org.artoolkitx.arx.arxj;
/*    */ 
/*    */ import android.util.Log;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ARX_jni
/*    */ {
/*    */   private static final String TAG = "ARX_jni";
/*    */   private static final String LIBRARY_NAME = "ARX";
/*    */   public static final int AR_LOG_LEVEL_DEBUG = 0;
/*    */   public static final int AR_LOG_LEVEL_INFO = 1;
/*    */   public static final int AR_LOG_LEVEL_WARN = 2;
/*    */   public static final int AR_LOG_LEVEL_ERROR = 3;
/*    */   public static final int AR_LOG_LEVEL_REL_INFO = 4;
/*    */   public static final int ARW_H_ALIGN_LEFT = 0;
/*    */   public static final int ARW_H_ALIGN_CENTRE = 1;
/*    */   public static final int ARW_H_ALIGN_RIGHT = 2;
/*    */   public static final int ARW_V_ALIGN_TOP = 0;
/*    */   public static final int ARW_V_ALIGN_CENTRE = 1;
/*    */   public static final int ARW_V_ALIGN_BOTTOM = 2;
/*    */   public static final int ARW_SCALE_MODE_FIT = 0;
/*    */   public static final int ARW_SCALE_MODE_FILL = 1;
/*    */   public static final int ARW_SCALE_MODE_STRETCH = 2;
/*    */   public static final int ARW_SCALE_MODE_1_TO_1 = 3;
/*    */   public static final int ARW_TRACKER_OPTION_NFT_MULTIMODE = 0;
/*    */   public static final int ARW_TRACKER_OPTION_SQUARE_THRESHOLD = 1;
/*    */   public static final int ARW_TRACKER_OPTION_SQUARE_THRESHOLD_MODE = 2;
/*    */   public static final int ARW_TRACKER_OPTION_SQUARE_LABELING_MODE = 3;
/*    */   public static final int ARW_TRACKER_OPTION_SQUARE_PATTERN_DETECTION_MODE = 4;
/*    */   public static final int ARW_TRACKER_OPTION_SQUARE_BORDER_SIZE = 5;
/*    */   public static final int ARW_TRACKER_OPTION_SQUARE_MATRIX_CODE_TYPE = 6;
/*    */   public static final int ARW_TRACKER_OPTION_SQUARE_IMAGE_PROC_MODE = 7;
/*    */   public static final int ARW_TRACKER_OPTION_SQUARE_DEBUG_MODE = 8;
/*    */   public static final int ARW_TRACKER_OPTION_SQUARE_PATTERN_SIZE = 9;
/*    */   public static final int ARW_TRACKER_OPTION_SQUARE_PATTERN_COUNT_MAX = 10;
/*    */   public static final int AR_LABELING_THRESH_MODE_MANUAL = 0;
/*    */   public static final int AR_LABELING_THRESH_MODE_AUTO_MEDIAN = 1;
/*    */   public static final int AR_LABELING_THRESH_MODE_AUTO_OTSU = 2;
/*    */   public static final int AR_LABELING_THRESH_MODE_AUTO_ADAPTIVE = 3;
/*    */   public static final int AR_LABELING_THRESH_MODE_AUTO_BRACKETING = 4;
/*    */   public static final int AR_LABELING_WHITE_REGION = 0;
/*    */   public static final int AR_LABELING_BLACK_REGION = 1;
/*    */   public static final int AR_TEMPLATE_MATCHING_COLOR = 0;
/*    */   public static final int AR_TEMPLATE_MATCHING_MONO = 1;
/*    */   public static final int AR_MATRIX_CODE_DETECTION = 2;
/*    */   public static final int AR_TEMPLATE_MATCHING_COLOR_AND_MATRIX = 3;
/*    */   
/*    */   static boolean loadNativeLibrary() {
/*    */     try {
/* 71 */       Log.i("ARX_jni", "Attempting to load library ARX.");
/*    */       
/* 73 */       System.loadLibrary("c++_shared");
/* 74 */       System.loadLibrary("ARX");
/*    */     }
/* 76 */     catch (UnsatisfiedLinkError e) {
/* 77 */       Log.e("ARX_jni", "Cannot load native library ARX.");
/* 78 */       return false;
/* 79 */     } catch (Exception e) {
/* 80 */       Log.e("ARX_jni", "Error loading native library ARX:" + e.toString());
/* 81 */       return false;
/*    */     } 
/*    */     
/* 84 */     return true;
/*    */   }
/*    */   
/*    */   public static final int AR_TEMPLATE_MATCHING_MONO_AND_MATRIX = 4;
/*    */   public static final int AR_MATRIX_CODE_3x3 = 3;
/*    */   public static final int AR_MATRIX_CODE_3x3_PARITY65 = 259;
/*    */   public static final int AR_MATRIX_CODE_3x3_HAMMING63 = 515;
/*    */   public static final int AR_MATRIX_CODE_4x4 = 4;
/*    */   public static final int AR_MATRIX_CODE_4x4_BCH_13_9_3 = 772;
/*    */   public static final int AR_MATRIX_CODE_4x4_BCH_13_5_5 = 1028;
/*    */   public static final int AR_MATRIX_CODE_5x5_BCH_22_12_5 = 1029;
/*    */   public static final int AR_MATRIX_CODE_5x5_BCH_22_7_7 = 1285;
/*    */   public static final int AR_MATRIX_CODE_5x5 = 5;
/*    */   public static final int AR_MATRIX_CODE_6x6 = 6;
/*    */   public static final int AR_MATRIX_CODE_GLOBAL_ID = 2830;
/*    */   public static final int AR_IMAGE_PROC_FRAME_IMAGE = 0;
/*    */   public static final int AR_IMAGE_PROC_FIELD_IMAGE = 1;
/*    */   public static final int ARW_TRACKABLE_OPTION_FILTERED = 1;
/*    */   public static final int ARW_TRACKABLE_OPTION_FILTER_SAMPLE_RATE = 2;
/*    */   public static final int ARW_TRACKABLE_OPTION_FILTER_CUTOFF_FREQ = 3;
/*    */   public static final int ARW_TRACKABLE_OPTION_SQUARE_USE_CONT_POSE_ESTIMATION = 4;
/*    */   public static final int ARW_TRACKABLE_OPTION_SQUARE_CONFIDENCE = 5;
/*    */   public static final int ARW_TRACKABLE_OPTION_SQUARE_CONFIDENCE_CUTOFF = 6;
/*    */   public static final int ARW_TRACKABLE_OPTION_NFT_SCALE = 7;
/*    */   public static final int ARW_TRACKABLE_OPTION_MULTI_MIN_SUBMARKERS = 8;
/*    */   public static final int ARW_TRACKABLE_OPTION_MULTI_MIN_CONF_MATRIX = 9;
/*    */   public static final int ARW_TRACKABLE_OPTION_MULTI_MIN_CONF_PATTERN = 10;
/*    */   public static final int AR_PIXEL_FORMAT_INVALID = -1;
/*    */   public static final int AR_PIXEL_FORMAT_RGB = 0;
/*    */   public static final int AR_PIXEL_FORMAT_BGR = 1;
/*    */   public static final int AR_PIXEL_FORMAT_RGBA = 2;
/*    */   public static final int AR_PIXEL_FORMAT_BGRA = 3;
/*    */   public static final int AR_PIXEL_FORMAT_ABGR = 4;
/*    */   public static final int AR_PIXEL_FORMAT_MONO = 5;
/*    */   public static final int AR_PIXEL_FORMAT_ARGB = 6;
/*    */   public static final int AR_PIXEL_FORMAT_2vuy = 7;
/*    */   public static final int AR_PIXEL_FORMAT_yuvs = 8;
/*    */   public static final int AR_PIXEL_FORMAT_RGB_565 = 9;
/*    */   public static final int AR_PIXEL_FORMAT_RGBA_5551 = 10;
/*    */   public static final int AR_PIXEL_FORMAT_RGBA_4444 = 11;
/*    */   public static final int AR_PIXEL_FORMAT_420v = 12;
/*    */   public static final int AR_PIXEL_FORMAT_420f = 13;
/*    */   public static final int AR_PIXEL_FORMAT_NV21 = 14;
/*    */   
/*    */   public static native String arwGetARToolKitVersion();
/*    */   
/*    */   public static native void arwSetLogLevel(int paramInt);
/*    */   
/*    */   public static native boolean arwInitialiseAR();
/*    */   
/*    */   public static native boolean arwChangeToResourcesDir(String paramString);
/*    */   
/*    */   public static native boolean arwStartRunning(String paramString1, String paramString2);
/*    */   
/*    */   public static native boolean arwStartRunningStereo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
/*    */   
/*    */   public static native boolean arwIsInited();
/*    */   
/*    */   public static native boolean arwIsRunning();
/*    */   
/*    */   public static native boolean arwStopRunning();
/*    */   
/*    */   public static native boolean arwShutdownAR();
/*    */   
/*    */   public static native float[] arwGetProjectionMatrix(float paramFloat1, float paramFloat2);
/*    */   
/*    */   public static native boolean arwGetProjectionMatrixStereo(float paramFloat1, float paramFloat2, float[] paramArrayOffloat1, float[] paramArrayOffloat2);
/*    */   
/*    */   public static native boolean arwGetVideoParams(int[] paramArrayOfint1, int[] paramArrayOfint2, int[] paramArrayOfint3, String[] paramArrayOfString);
/*    */   
/*    */   public static native boolean arwGetVideoParamsStereo(int[] paramArrayOfint1, int[] paramArrayOfint2, int[] paramArrayOfint3, String[] paramArrayOfString1, int[] paramArrayOfint4, int[] paramArrayOfint5, int[] paramArrayOfint6, String[] paramArrayOfString2);
/*    */   
/*    */   public static native boolean arwCapture();
/*    */   
/*    */   public static native boolean arwUpdateAR();
/*    */   
/*    */   public static native boolean arwUpdateTexture32(byte[] paramArrayOfbyte);
/*    */   
/*    */   public static native boolean arwUpdateTextureStereo32(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2);
/*    */   
/*    */   public static native boolean arwDrawVideoInit(int paramInt);
/*    */   
/*    */   public static native boolean arwDrawVideoSettings(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfint);
/*    */   
/*    */   public static native boolean arwDrawVideo(int paramInt);
/*    */   
/*    */   public static native boolean arwDrawVideoFinal(int paramInt);
/*    */   
/*    */   public static native int arwAddTrackable(String paramString);
/*    */   
/*    */   public static native boolean arwRemoveTrackable(int paramInt);
/*    */   
/*    */   public static native int arwRemoveAllTrackables();
/*    */   
/*    */   public static native boolean arwQueryTrackableVisibilityAndTransformation(int paramInt, float[] paramArrayOffloat);
/*    */   
/*    */   public static native boolean arwQueryTrackableVisibilityAndTransformationStereo(int paramInt, float[] paramArrayOffloat1, float[] paramArrayOffloat2);
/*    */   
/*    */   public static native void arwSetTrackerOptionBool(int paramInt, boolean paramBoolean);
/*    */   
/*    */   public static native void arwSetTrackerOptionInt(int paramInt1, int paramInt2);
/*    */   
/*    */   public static native void arwSetTrackerOptionFloat(int paramInt, float paramFloat);
/*    */   
/*    */   public static native boolean arwGetTrackerOptionBool(int paramInt);
/*    */   
/*    */   public static native int arwGetTrackerOptionInt(int paramInt);
/*    */   
/*    */   public static native float arwGetTrackerOptionFloat(int paramInt);
/*    */   
/*    */   public static native void arwSetTrackableOptionBool(int paramInt1, int paramInt2, boolean paramBoolean);
/*    */   
/*    */   public static native void arwSetTrackableOptionInt(int paramInt1, int paramInt2, int paramInt3);
/*    */   
/*    */   public static native void arwSetTrackableOptionFloat(int paramInt1, int paramInt2, float paramFloat);
/*    */   
/*    */   public static native boolean arwGetTrackableOptionBool(int paramInt1, int paramInt2);
/*    */   
/*    */   public static native int arwGetTrackableOptionInt(int paramInt1, int paramInt2);
/*    */   
/*    */   public static native float arwGetTrackableOptionFloat(int paramInt1, int paramInt2);
/*    */   
/*    */   public static native int arwAndroidVideoPushInit(int paramInt1, int paramInt2, int paramInt3, String paramString, int paramInt4, int paramInt5);
/*    */   
/*    */   public static native int arwAndroidVideoPush1(int paramInt1, byte[] paramArrayOfbyte, int paramInt2);
/*    */   
/*    */   public static native int arwAndroidVideoPush2(int paramInt1, ByteBuffer paramByteBuffer1, int paramInt2, int paramInt3, ByteBuffer paramByteBuffer2, int paramInt4, int paramInt5, ByteBuffer paramByteBuffer3, int paramInt6, int paramInt7, ByteBuffer paramByteBuffer4, int paramInt8, int paramInt9);
/*    */   
/*    */   public static native int arwAndroidVideoPushFinal(int paramInt);
/*    */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\KYSHIYONG\arxjUnity.jar!\org\artoolkitx\arx\arxj\ARX_jni.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */