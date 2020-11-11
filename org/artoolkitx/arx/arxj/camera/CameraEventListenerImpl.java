/*     */ package org.artoolkitx.arx.arxj.camera;
/*     */ 
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.util.Log;
/*     */ import android.widget.Toast;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.artoolkitx.arx.arxj.ARController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CameraEventListenerImpl
/*     */   implements CameraEventListener
/*     */ {
/*     */   private final Activity arActivity;
/*  52 */   private static final String TAG = CameraEventListenerImpl.class.getName();
/*     */   private final FrameListener frameListener;
/*     */   private boolean firstUpdate;
/*     */   private int cameraIndex;
/*     */   
/*     */   public CameraEventListenerImpl(Activity arActivity, FrameListener frameCallbackListener) {
/*  58 */     this.arActivity = arActivity;
/*  59 */     this.frameListener = frameCallbackListener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cameraStreamStarted(int width, int height, String pixelFormat, int cameraIndex, boolean cameraIsFrontFacing) {
/*  64 */     this.cameraIndex = cameraIndex;
/*  65 */     if (ARController.getInstance().startWithPushedVideo(width, height, pixelFormat, null, cameraIndex, cameraIsFrontFacing)) {
/*     */       
/*  67 */       Log.i(TAG, "Initialised AR.");
/*     */     } else {
/*     */       
/*  70 */       Log.e(TAG, "Error initialising AR. Cannot continue.");
/*  71 */       this.arActivity.finish();
/*     */     } 
/*     */     
/*  74 */     Toast.makeText((Context)this.arActivity, "Camera settings: " + width + "x" + height, 0).show();
/*  75 */     this.firstUpdate = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cameraStreamFrame(byte[] frame, int frameSize) {
/*  80 */     if (this.firstUpdate) {
/*  81 */       this.frameListener.firstFrame(this.cameraIndex);
/*  82 */       this.firstUpdate = false;
/*     */     } 
/*     */     
/*  85 */     if (ARController.getInstance().convertAndDetect1(frame, frameSize)) {
/*  86 */       this.frameListener.onFrameProcessed();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void cameraStreamFrame(ByteBuffer[] framePlanes, int[] framePlanePixelStrides, int[] framePlaneRowStrides) {
/*  92 */     if (this.firstUpdate) {
/*  93 */       this.frameListener.firstFrame(this.cameraIndex);
/*  94 */       this.firstUpdate = false;
/*     */     } 
/*     */     
/*  97 */     if (ARController.getInstance().convertAndDetect2(framePlanes, framePlanePixelStrides, framePlaneRowStrides)) {
/*  98 */       this.frameListener.onFrameProcessed();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void cameraStreamStopped() {
/* 104 */     ARController.getInstance().stopAndFinal();
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\KYSHIYONG\arxjUnity.jar!\org\artoolkitx\arx\arxj\camera\CameraEventListenerImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */