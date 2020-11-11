/*     */ package org.artoolkitx.arx.arxj.camera;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.os.Build;
/*     */ import android.support.v4.content.ContextCompat;
/*     */ import android.util.Log;
/*     */ import android.widget.Toast;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  56 */   private static final String TAG = CameraAccessHandlerImpl.class.getSimpleName();
/*     */   private final CameraSurfaceImpl mCameraSurface;
/*     */   private boolean mAskPermissionFirst = false;
/*     */   
/*     */   public CameraAccessHandlerImpl(Activity activity, CameraEventListener cameraEventListener) {
/*  61 */     Log.i(TAG, "CameraAccessHandlerImpl(): ctor called");
/*  62 */     Context mAppContext = activity.getApplicationContext();
/*  63 */     this.mCameraSurface = new CameraSurfaceImpl(cameraEventListener, mAppContext);
/*     */     
/*     */     try {
/*  66 */       if (Build.VERSION.SDK_INT >= 23 && 
/*  67 */         0 != ContextCompat.checkSelfPermission((Context)activity, "android.permission.CAMERA")) {
/*  68 */         this.mAskPermissionFirst = true;
/*  69 */         if (activity.shouldShowRequestPermissionRationale("android.permission.CAMERA"))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/*  74 */           Toast.makeText(activity.getApplicationContext(), "App requires access to camera to be granted", 0)
/*     */             
/*  76 */             .show();
/*     */         }
/*     */ 
/*     */         
/*  80 */         Log.i(TAG, "CameraAccessHandler(): ask for camera access permission");
/*  81 */         activity.requestPermissions(new String[] { "android.permission.CAMERA" }, 0);
/*     */       }
/*     */     
/*  84 */     } catch (Exception ex) {
/*  85 */       Log.e(TAG, "CameraAccessHandler(): exception , " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetCameraAccessPermissionsFromUser() {
/*  91 */     this.mAskPermissionFirst = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getCameraAccessPermissions() {
/*  96 */     return this.mAskPermissionFirst;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeCamera() {
/* 101 */     getCameraSurfaceView().closeCameraDevice();
/*     */   }
/*     */ 
/*     */   
/*     */   public CameraSurface getCameraSurfaceView() {
/* 106 */     return this.mCameraSurface;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\KYSHIYONG\arxjUnity.jar!\org\artoolkitx\arx\arxj\camera\CameraAccessHandlerImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */