/*     */ package org.artoolkitx.arx.arxj;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.os.Build;
/*     */ import android.os.Environment;
/*     */ import android.os.StatFs;
/*     */ import android.util.DisplayMetrics;
/*     */ import android.util.Log;
/*     */ import androidx.annotation.NonNull;
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
/*     */ 
/*     */ public class AndroidUtils
/*     */ {
/*     */   private static final String TAG = "AndroidUtils";
/*     */   public static final int VIEW_VISIBILITY = 5894;
/*     */   
/*     */   public static String androidBuildVersion() {
/*  82 */     String buf = "Version\n *Release: " + Build.VERSION.RELEASE + "\n Incremental: " + Build.VERSION.INCREMENTAL + "\n Codename: " + Build.VERSION.CODENAME + "\n SDK: " + Build.VERSION.SDK_INT + "\n\n*Model: " + Build.MODEL + "\nManufacturer: " + Build.MANUFACTURER + "\nBoard: " + Build.BOARD + "\nBrand: " + Build.BRAND + "\nDevice: " + Build.DEVICE + "\nProduct: " + Build.PRODUCT + "\nHardware: " + Build.HARDWARE + "\nCPU ABI: " + Build.CPU_ABI + "\nCPU second ABI: " + Build.CPU_ABI2 + "\n\n*Displayed ID: " + Build.DISPLAY + "\nHost: " + Build.HOST + "\nUser: " + Build.USER + "\nID: " + Build.ID + "\nType: " + Build.TYPE + "\nTags: " + Build.TAGS + "\n\nFingerprint: " + Build.FINGERPRINT + "\n\nItems with * are intended for display to the end user.";
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
/* 104 */     return buf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSDCardMounted() {
/* 113 */     return Environment.getExternalStorageState().equals("mounted");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getAvailableExternalMemorySize() {
/* 123 */     if (isSDCardMounted()) {
/* 124 */       File path = Environment.getExternalStorageDirectory();
/* 125 */       StatFs stat = new StatFs(path.getPath());
/* 126 */       long blockSize = stat.getBlockSize();
/* 127 */       long availableBlocks = stat.getAvailableBlocks();
/* 128 */       return availableBlocks * blockSize;
/*     */     } 
/* 130 */     return -1L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getTotalExternalMemorySize() {
/* 140 */     if (isSDCardMounted()) {
/* 141 */       File path = Environment.getExternalStorageDirectory();
/* 142 */       StatFs stat = new StatFs(path.getPath());
/* 143 */       long blockSize = stat.getBlockSize();
/* 144 */       long totalBlocks = stat.getBlockCount();
/* 145 */       return totalBlocks * blockSize;
/*     */     } 
/* 147 */     return -1L;
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
/* 162 */     double val = 0.0D;
/* 163 */     String units = "";
/*     */     
/* 165 */     if (bytes < 1024L) {
/* 166 */       val = bytes;
/* 167 */       units = "bytes";
/* 168 */     } else if (bytes < 1048576L) {
/* 169 */       val = ((float)bytes / 1024.0F);
/* 170 */       units = "KB";
/* 171 */     } else if (bytes < 1073741824L) {
/* 172 */       val = ((float)bytes / 1048576.0F);
/* 173 */       units = "MB";
/*     */     } else {
/* 175 */       val = ((float)bytes / 1.07374182E9F);
/* 176 */       units = "GB";
/*     */     } 
/*     */     
/* 179 */     DecimalFormat df = new DecimalFormat("###.##");
/* 180 */     return df.format(val) + " " + units;
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
/* 192 */     DisplayMetrics metrics = new DisplayMetrics();
/* 193 */     activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
/*     */     
/* 195 */     int displayWidth = metrics.widthPixels;
/* 196 */     int displayHeight = metrics.heightPixels;
/*     */     
/* 198 */     String density = "unknown";
/* 199 */     switch (metrics.densityDpi) {
/*     */       case 120:
/* 201 */         density = "Low";
/*     */         break;
/*     */       case 160:
/* 204 */         density = "Medium";
/*     */         break;
/*     */       case 240:
/* 207 */         density = "High";
/*     */         break;
/*     */     } 
/*     */     
/* 211 */     Log.i("AndroidUtils", "reportDisplayInformation(): Display is " + displayWidth + "x" + displayHeight + ", Density: " + density);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public static CameraAccessHandler createCameraAccessHandler(Activity activity, CameraEventListener cameraEventListener) {
/* 220 */     CameraAccessHandlerImpl cameraAccessHandlerImpl = new CameraAccessHandlerImpl(activity, cameraEventListener) {  }
/*     */       ;
/* 222 */     Log.i("AndroidUtils", "onResume(): Cam2CaptureSurface constructed");
/*     */     
/* 224 */     return (CameraAccessHandler)cameraAccessHandlerImpl;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\AndroidUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */