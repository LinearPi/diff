/*     */ package org.artoolkitx.arx.arxj.camera;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.SharedPreferences;
/*     */ import android.content.pm.PackageManager;
/*     */ import android.hardware.Camera;
/*     */ import android.os.Bundle;
/*     */ import android.preference.ListPreference;
/*     */ import android.preference.PreferenceActivity;
/*     */ import android.util.Log;
/*     */ import java.util.List;
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
/*     */ public class CameraPreferencesActivity
/*     */   extends PreferenceActivity
/*     */   implements SharedPreferences.OnSharedPreferenceChangeListener
/*     */ {
/*     */   private static final String TAG = "CameraPreferences";
/*     */   private ListPreference cameraIndexPreference;
/*     */   private ListPreference cameraResolutionPreference;
/*     */   
/*     */   @TargetApi(9)
/*     */   public void onCreate(Bundle savedInstanceState) {
/* 119 */     super.onCreate(savedInstanceState);
/*     */ 
/*     */     
/* 122 */     PackageManager pm = getPackageManager();
/* 123 */     if (!pm.hasSystemFeature("android.hardware.camera")) {
/* 124 */       finish();
/*     */       
/*     */       return;
/*     */     } 
/* 128 */     int prefIdent = getResources().getIdentifier("preferences", "xml", getPackageName());
/* 129 */     addPreferencesFromResource(prefIdent);
/* 130 */     this.cameraIndexPreference = (ListPreference)findPreference("pref_cameraIndex");
/* 131 */     this.cameraResolutionPreference = (ListPreference)findPreference("pref_cameraResolution");
/*     */ 
/*     */ 
/*     */     
/* 135 */     int cameraCount = Camera.getNumberOfCameras();
/* 136 */     Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
/* 137 */     int cameraCountFront = 0;
/* 138 */     int cameraCountRear = 0;
/* 139 */     CharSequence[] entries = new CharSequence[cameraCount];
/* 140 */     CharSequence[] entryValues = new CharSequence[cameraCount];
/* 141 */     for (int camIndex = 0; camIndex < cameraCount; camIndex++) {
/* 142 */       Camera.getCameraInfo(camIndex, cameraInfo);
/* 143 */       if (cameraInfo.facing == 1) {
/* 144 */         cameraCountFront++;
/* 145 */         entries[camIndex] = "Front camera";
/* 146 */         if (cameraCountFront > 1)
/* 147 */           entries[camIndex] = entries[camIndex] + " " + cameraCountFront; 
/*     */       } else {
/* 149 */         cameraCountRear++;
/* 150 */         entries[camIndex] = "Rear camera";
/* 151 */         if (cameraCountRear > 1)
/* 152 */           entries[camIndex] = entries[camIndex] + " " + cameraCountRear; 
/*     */       } 
/* 154 */       entryValues[camIndex] = Integer.toString(camIndex);
/*     */     } 
/* 156 */     this.cameraIndexPreference.setEnabled(true);
/* 157 */     this.cameraIndexPreference.setEntries(entries);
/* 158 */     this.cameraIndexPreference.setEntryValues(entryValues);
/*     */     
/* 160 */     buildResolutionListForCameraIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   private void buildResolutionListForCameraIndex() {
/* 165 */     int camIndex = Integer.parseInt(this.cameraIndexPreference.getValue());
/*     */ 
/*     */     
/*     */     try {
/* 169 */       Camera cam = Camera.open(camIndex);
/*     */       
/* 171 */       Camera.Parameters params = cam.getParameters();
/* 172 */       List<Camera.Size> previewSizes = params.getSupportedPreviewSizes();
/* 173 */       cam.release();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 180 */       String camResolution = this.cameraResolutionPreference.getValue();
/* 181 */       boolean foundCurrentResolution = false;
/* 182 */       CharSequence[] entries = new CharSequence[previewSizes.size()];
/* 183 */       CharSequence[] entryValues = new CharSequence[previewSizes.size()];
/* 184 */       for (int i = 0; i < previewSizes.size(); i++) {
/* 185 */         int w = ((Camera.Size)previewSizes.get(i)).width;
/* 186 */         int h = ((Camera.Size)previewSizes.get(i)).height;
/* 187 */         entries[i] = w + "x" + h + "   (" + findAspectRatioName(w, h) + ")";
/*     */         
/* 189 */         entryValues[i] = w + "x" + h;
/* 190 */         if (entryValues[i].equals(camResolution))
/* 191 */           foundCurrentResolution = true; 
/*     */       } 
/* 193 */       this.cameraResolutionPreference.setEntries(entries);
/* 194 */       this.cameraResolutionPreference.setEntryValues(entryValues);
/*     */       
/* 196 */       if (!foundCurrentResolution) {
/* 197 */         this.cameraResolutionPreference.setValue(entryValues[0].toString());
/* 198 */         this.cameraResolutionPreference
/* 199 */           .setSummary(this.cameraResolutionPreference.getEntry());
/*     */       }
/*     */     
/* 202 */     } catch (RuntimeException e) {
/* 203 */       Log.e("CameraPreferences", "buildResolutionListForCameraIndex(): Camera failed to open: " + e.getLocalizedMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onResume() {
/* 210 */     super.onResume();
/*     */     
/* 212 */     this.cameraIndexPreference.setSummary(this.cameraIndexPreference.getEntry());
/* 213 */     this.cameraResolutionPreference.setSummary(this.cameraResolutionPreference
/* 214 */         .getEntry());
/*     */ 
/*     */     
/* 217 */     getPreferenceManager().getSharedPreferences()
/* 218 */       .registerOnSharedPreferenceChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
/* 224 */     if (key.equals("pref_cameraIndex")) {
/* 225 */       this.cameraIndexPreference.setSummary(this.cameraIndexPreference.getEntry());
/* 226 */       buildResolutionListForCameraIndex();
/* 227 */     } else if (key.equals("pref_cameraResolution")) {
/* 228 */       this.cameraResolutionPreference.setSummary(this.cameraResolutionPreference
/* 229 */           .getEntry());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onPause() {
/* 236 */     super.onPause();
/*     */ 
/*     */     
/* 239 */     getPreferenceManager().getSharedPreferences()
/* 240 */       .unregisterOnSharedPreferenceChangeListener(this);
/*     */   }
/*     */   
/*     */   public enum ASPECT_RATIO {
/* 244 */     _1_1,
/* 245 */     _11_9,
/* 246 */     _5_4,
/* 247 */     _4_3,
/* 248 */     _SQRROOT2_1,
/* 249 */     _3_2,
/* 250 */     _14_9,
/* 251 */     _8_5,
/* 252 */     _5_3,
/* 253 */     _16_9,
/* 254 */     _9_5,
/* 255 */     _17_9,
/* 256 */     _UNIQUE;
/*     */   }
/*     */   
/*     */   private static final class PixelSizeToAspectRatio {
/*     */     final int width;
/*     */     final int height;
/*     */     final CameraPreferencesActivity.ASPECT_RATIO aspectRatio;
/*     */     final String name;
/*     */     
/*     */     PixelSizeToAspectRatio(int w, int h, CameraPreferencesActivity.ASPECT_RATIO ar, String name) {
/* 266 */       this.width = w;
/* 267 */       this.height = h;
/* 268 */       this.aspectRatio = ar;
/* 269 */       this.name = name;
/*     */     }
/*     */   }
/*     */   
/* 273 */   private static final PixelSizeToAspectRatio[] aspectRatios = new PixelSizeToAspectRatio[] { new PixelSizeToAspectRatio(1, 1, ASPECT_RATIO._1_1, "1:1"), new PixelSizeToAspectRatio(11, 9, ASPECT_RATIO._11_9, "11:9"), new PixelSizeToAspectRatio(5, 4, ASPECT_RATIO._5_4, "5:4"), new PixelSizeToAspectRatio(4, 3, ASPECT_RATIO._4_3, "4:3"), new PixelSizeToAspectRatio(3, 2, ASPECT_RATIO._3_2, "3:2"), new PixelSizeToAspectRatio(14, 9, ASPECT_RATIO._14_9, "14:9"), new PixelSizeToAspectRatio(8, 5, ASPECT_RATIO._8_5, "8:5"), new PixelSizeToAspectRatio(5, 3, ASPECT_RATIO._5_3, "5:3"), new PixelSizeToAspectRatio(16, 9, ASPECT_RATIO._16_9, "16:9"), new PixelSizeToAspectRatio(9, 5, ASPECT_RATIO._9_5, "9:5"), new PixelSizeToAspectRatio(17, 9, ASPECT_RATIO._17_9, "17:9"), new PixelSizeToAspectRatio(683, 384, ASPECT_RATIO._16_9, "16:9"), new PixelSizeToAspectRatio(85, 48, ASPECT_RATIO._16_9, "16:9"), new PixelSizeToAspectRatio(256, 135, ASPECT_RATIO._17_9, "17:9"), new PixelSizeToAspectRatio(512, 307, ASPECT_RATIO._5_3, "5:3"), new PixelSizeToAspectRatio(30, 23, ASPECT_RATIO._4_3, "4:3"), new PixelSizeToAspectRatio(128, 69, ASPECT_RATIO._17_9, "17:9"), new PixelSizeToAspectRatio(30, 23, ASPECT_RATIO._11_9, "11:9"), new PixelSizeToAspectRatio(53, 30, ASPECT_RATIO._16_9, "16:9"), new PixelSizeToAspectRatio(37, 30, ASPECT_RATIO._11_9, "11:9"), new PixelSizeToAspectRatio(192, 145, ASPECT_RATIO._4_3, "4:3"), new PixelSizeToAspectRatio(640, 427, ASPECT_RATIO._3_2, "3:2"), new PixelSizeToAspectRatio(427, 240, ASPECT_RATIO._16_9, "16:9") };
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
/*     */   public ASPECT_RATIO findAspectRatio(int w, int h) {
/* 317 */     int[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97 };
/*     */     
/* 319 */     int w_lcd = w, h_lcd = h;
/* 320 */     for (int i : primes) {
/* 321 */       while (w_lcd >= i && h_lcd >= i && w_lcd % i == 0 && h_lcd % i == 0) {
/* 322 */         w_lcd /= i;
/* 323 */         h_lcd /= i;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 328 */     for (PixelSizeToAspectRatio aspectRatio : aspectRatios) {
/* 329 */       if (w_lcd == aspectRatio.width && h_lcd == aspectRatio.height)
/* 330 */         return aspectRatio.aspectRatio; 
/*     */     } 
/* 332 */     return ASPECT_RATIO._UNIQUE;
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
/*     */   
/*     */   public String findAspectRatioName(int w, int h) {
/* 348 */     int[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97 };
/*     */     
/* 350 */     int w_lcd = w, h_lcd = h;
/* 351 */     for (int i : primes) {
/* 352 */       while (w_lcd >= i && h_lcd >= i && w_lcd % i == 0 && h_lcd % i == 0) {
/* 353 */         w_lcd /= i;
/* 354 */         h_lcd /= i;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 359 */     for (PixelSizeToAspectRatio aspectRatio : aspectRatios) {
/* 360 */       if (w_lcd == aspectRatio.width && h_lcd == aspectRatio.height)
/* 361 */         return aspectRatio.name; 
/*     */     } 
/* 363 */     return w + ":" + h;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\KYSHIYONG\arxjUnity.jar!\org\artoolkitx\arx\arxj\camera\CameraPreferencesActivity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */