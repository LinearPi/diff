/*     */ package org.artoolkitx.arx.arxj;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.os.Build;
/*     */ import android.os.Environment;
/*     */ import android.os.StatFs;
/*     */ import android.support.annotation.NonNull;
/*     */ import android.util.DisplayMetrics;
/*     */ import android.util.Log;
/*     */ import java.io.File;
/*     */ import java.text.DecimalFormat;
/*     */ import org.artoolkitx.arx.arxj.camera.CameraAccessHandler;
/*     */ import org.artoolkitx.arx.arxj.camera.CameraAccessHandlerImpl;
/*     */ import org.artoolkitx.arx.arxj.camera.CameraEventListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AndroidUtils
/*     */ {
/*     */   private static final String TAG = "AndroidUtils";
/*     */   public static final int VIEW_VISIBILITY = 5894;
/*     */   
/*     */   public static String androidBuildVersion() {
/*  81 */     String buf = "Version\n *Release: " + Build.VERSION.RELEASE + "\n Incremental: " + Build.VERSION.INCREMENTAL + "\n Codename: " + Build.VERSION.CODENAME + "\n SDK: " + Build.VERSION.SDK_INT + "\n\n*Model: " + Build.MODEL + "\nManufacturer: " + Build.MANUFACTURER + "\nBoard: " + Build.BOARD + "\nBrand: " + Build.BRAND + "\nDevice: " + Build.DEVICE + "\nProduct: " + Build.PRODUCT + "\nHardware: " + Build.HARDWARE + "\nCPU ABI: " + Build.CPU_ABI + "\nCPU second ABI: " + Build.CPU_ABI2 + "\n\n*Displayed ID: " + Build.DISPLAY + "\nHost: " + Build.HOST + "\nUser: " + Build.USER + "\nID: " + Build.ID + "\nType: " + Build.TYPE + "\nTags: " + Build.TAGS + "\n\nFingerprint: " + Build.FINGERPRINT + "\n\nItems with * are intended for display to the end user.";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSDCardMounted() {
/* 112 */     return Environment.getExternalStorageState().equals("mounted");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getAvailableExternalMemorySize() {
/* 122 */     if (isSDCardMounted()) {
/* 123 */       File path = Environment.getExternalStorageDirectory();
/* 124 */       StatFs stat = new StatFs(path.getPath());
/* 125 */       long blockSize = stat.getBlockSize();
/* 126 */       long availableBlocks = stat.getAvailableBlocks();
/* 127 */       return availableBlocks * blockSize;
/*     */     } 
/* 129 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getTotalExternalMemorySize() {
/* 139 */     if (isSDCardMounted()) {
/* 140 */       File path = Environment.getExternalStorageDirectory();
/* 141 */       StatFs stat = new StatFs(path.getPath());
/* 142 */       long blockSize = stat.getBlockSize();
/* 143 */       long totalBlocks = stat.getBlockCount();
/* 144 */       return totalBlocks * blockSize;
/*     */     } 
/* 146 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatBytes(long bytes) {
/* 161 */     double val = 0.0D;
/* 162 */     String units = "";
/*     */     
/* 164 */     if (bytes < 1024L) {
/* 165 */       val = bytes;
/* 166 */       units = "bytes";
/* 167 */     } else if (bytes < 1048576L) {
/* 168 */       val = ((float)bytes / 1024.0F);
/* 169 */       units = "KB";
/* 170 */     } else if (bytes < 1073741824L) {
/* 171 */       val = ((float)bytes / 1048576.0F);
/* 172 */       units = "MB";
/*     */     } else {
/* 174 */       val = ((float)bytes / 1.07374182E9F);
/* 175 */       units = "GB";
/*     */     } 
/*     */     
/* 178 */     DecimalFormat df = new DecimalFormat("###.##");
/* 179 */     return df.format(val) + " " + units;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reportDisplayInformation(Activity activity) {
/* 191 */     DisplayMetrics metrics = new DisplayMetrics();
/* 192 */     activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
/*     */     
/* 194 */     int displayWidth = metrics.widthPixels;
/* 195 */     int displayHeight = metrics.heightPixels;
/*     */     
/* 197 */     String density = "unknown";
/* 198 */     switch (metrics.densityDpi) {
/*     */       case 120:
/* 200 */         density = "Low";
/*     */         break;
/*     */       case 160:
/* 203 */         density = "Medium";
/*     */         break;
/*     */       case 240:
/* 206 */         density = "High";
/*     */         break;
/*     */     } 
/*     */     
/* 210 */     Log.i("AndroidUtils", "reportDisplayInformation(): Display is " + displayWidth + "x" + displayHeight + ", Density: " + density);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public static CameraAccessHandler createCameraAccessHandler(Activity activity, CameraEventListener cameraEventListener) {
/* 219 */     CameraAccessHandlerImpl cameraAccessHandlerImpl = new CameraAccessHandlerImpl(activity, cameraEventListener) {  }
/*     */       ;
/* 221 */     Log.i("AndroidUtils", "onResume(): Cam2CaptureSurface constructed");
/*     */     
/* 223 */     return (CameraAccessHandler)cameraAccessHandlerImpl;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\KYSHIYONG\arxjUnity.jar!\org\artoolkitx\arx\arxj\AndroidUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */