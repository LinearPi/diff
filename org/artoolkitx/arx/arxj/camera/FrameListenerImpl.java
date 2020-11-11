/*    */ package org.artoolkitx.arx.arxj.camera;
/*    */ 
/*    */ import android.app.Activity;
/*    */ import android.opengl.GLSurfaceView;
/*    */ import android.util.Log;
/*    */ import org.artoolkitx.arx.arxj.rendering.ARRenderer;
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
/*    */ public final class FrameListenerImpl
/*    */   implements FrameListener
/*    */ {
/* 48 */   private static final String TAG = FrameListenerImpl.class.getSimpleName();
/*    */   private final ARRenderer renderer;
/*    */   private final Activity activity;
/*    */   private final GLSurfaceView glSurfaceView;
/*    */   
/*    */   public FrameListenerImpl(ARRenderer renderer, Activity activity, GLSurfaceView glSurfaceView) {
/* 54 */     this.renderer = renderer;
/* 55 */     this.activity = activity;
/* 56 */     this.glSurfaceView = glSurfaceView;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void firstFrame(int cameraIndex) {
/* 62 */     if (this.renderer != null) {
/* 63 */       this.renderer.setCameraIndex(cameraIndex);
/* 64 */       if (this.renderer.configureARScene()) {
/* 65 */         Log.i(TAG, "firstFrame(): Scene configured successfully");
/*    */       } else {
/*    */         
/* 68 */         Log.e(TAG, "firstFrame(): Error configuring scene. Cannot continue.");
/* 69 */         this.activity.finish();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onFrameProcessed() {
/* 77 */     if (this.glSurfaceView != null)
/* 78 */       this.glSurfaceView.requestRender(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\camera\FrameListenerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */