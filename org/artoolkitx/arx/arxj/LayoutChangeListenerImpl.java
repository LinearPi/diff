/*    */ package org.artoolkitx.arx.arxj;
/*    */ 
/*    */ import android.app.Activity;
/*    */ import android.util.Log;
/*    */ import android.view.View;
/*    */ import org.artoolkitx.arx.arxj.camera.CameraAccessHandler;
/*    */ import org.artoolkitx.arx.arxj.camera.CameraSurface;
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
/*    */ class LayoutChangeListenerImpl
/*    */   implements View.OnLayoutChangeListener
/*    */ {
/* 49 */   private static final String TAG = LayoutChangeListenerImpl.class.getSimpleName();
/*    */   private final Activity activity;
/*    */   private final CameraAccessHandler cameraAccessHandler;
/*    */   
/*    */   public LayoutChangeListenerImpl(Activity activity, CameraAccessHandler cameraAccessHandler) {
/* 54 */     this.activity = activity;
/* 55 */     this.cameraAccessHandler = cameraAccessHandler;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
/* 60 */     View decorView = this.activity.getWindow().getDecorView();
/* 61 */     CameraSurface cameraSurface = this.cameraAccessHandler.getCameraSurfaceView();
/* 62 */     if (5894 == decorView.getSystemUiVisibility()) {
/* 63 */       if (!this.cameraAccessHandler.getCameraAccessPermissions()) {
/*    */         
/* 65 */         if (!cameraSurface.isImageReaderCreated()) {
/* 66 */           cameraSurface.surfaceCreated();
/*    */         }
/* 68 */         if (!cameraSurface.isCamera2DeviceOpen())
/* 69 */           cameraSurface.surfaceChanged(); 
/*    */       } 
/*    */     } else {
/* 72 */       Log.v(TAG, "Not in fullscreen.");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\LayoutChangeListenerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */