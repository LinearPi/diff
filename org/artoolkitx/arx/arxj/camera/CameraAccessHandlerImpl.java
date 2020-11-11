/*     */ package org.artoolkitx.arx.arxj.camera;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.os.Build;
/*     */ import android.util.Log;
/*     */ import android.widget.Toast;
/*     */ import androidx.core.content.ContextCompat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CameraAccessHandlerImpl
/*     */   implements CameraAccessHandler
/*     */ {
/*  58 */   private static final String TAG = CameraAccessHandlerImpl.class.getSimpleName();
/*     */   private final CameraSurfaceImpl mCameraSurface;
/*     */   private boolean mAskPermissionFirst = false;
/*     */   
/*     */   public CameraAccessHandlerImpl(Activity activity, CameraEventListener cameraEventListener) {
/*  63 */     Log.i(TAG, "CameraAccessHandlerImpl(): ctor called");
/*  64 */     Context mAppContext = activity.getApplicationContext();
/*  65 */     this.mCameraSurface = new CameraSurfaceImpl(cameraEventListener, mAppContext);
/*     */     
/*     */     try {
/*  68 */       if (Build.VERSION.SDK_INT >= 23 && 
/*  69 */         0 != ContextCompat.checkSelfPermission((Context)activity, "android.permission.CAMERA")) {
/*  70 */         this.mAskPermissionFirst = true;
/*  71 */         if (activity.shouldShowRequestPermissionRationale("android.permission.CAMERA"))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*  76 */           Toast.makeText(activity.getApplicationContext(), "App requires access to camera to be granted", 0)
/*     */             
/*  78 */             .show();
/*     */         }
/*     */ 
/*     */         
/*  82 */         Log.i(TAG, "CameraAccessHandler(): ask for camera access permission");
/*  83 */         activity.requestPermissions(new String[] { "android.permission.CAMERA" }, 0);
/*     */       }
/*     */     
/*  86 */     } catch (Exception ex) {
/*  87 */       Log.e(TAG, "CameraAccessHandler(): exception , " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetCameraAccessPermissionsFromUser() {
/*  93 */     this.mAskPermissionFirst = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getCameraAccessPermissions() {
/*  98 */     return this.mAskPermissionFirst;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeCamera() {
/* 103 */     getCameraSurfaceView().closeCameraDevice();
/*     */   }
/*     */ 
/*     */   
/*     */   public CameraSurface getCameraSurfaceView() {
/* 108 */     return this.mCameraSurface;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\camera\CameraAccessHandlerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */