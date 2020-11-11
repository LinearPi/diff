/*     */ package org.artoolkitx.arx.arxj.camera;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import android.hardware.camera2.CameraAccessException;
/*     */ import android.hardware.camera2.CameraCaptureSession;
/*     */ import android.hardware.camera2.CameraDevice;
/*     */ import android.hardware.camera2.CameraManager;
/*     */ import android.hardware.camera2.CaptureRequest;
/*     */ import android.media.Image;
/*     */ import android.media.ImageReader;
/*     */ import android.preference.PreferenceManager;
/*     */ import android.support.annotation.NonNull;
/*     */ import android.support.v4.content.ContextCompat;
/*     */ import android.util.DisplayMetrics;
/*     */ import android.util.Log;
/*     */ import android.util.Size;
/*     */ import android.view.Surface;
/*     */ import android.widget.Toast;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
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
/*     */ class CameraSurfaceImpl
/*     */   implements CameraSurface
/*     */ {
/*  70 */   private static final String TAG = CameraSurfaceImpl.class.getSimpleName();
/*     */   private CameraDevice mCameraDevice;
/*     */   private ImageReader mImageReader;
/*     */   private Size mImageReaderVideoSize;
/*     */   private final Context mAppContext;
/*     */   
/*  76 */   private final CameraDevice.StateCallback mCamera2DeviceStateCallback = new CameraDevice.StateCallback()
/*     */     {
/*     */       public void onOpened(@NonNull CameraDevice camera2DeviceInstance) {
/*  79 */         CameraSurfaceImpl.this.mCameraDevice = camera2DeviceInstance;
/*  80 */         CameraSurfaceImpl.this.startCaptureAndForwardFramesSession();
/*     */       }
/*     */ 
/*     */       
/*     */       public void onDisconnected(@NonNull CameraDevice camera2DeviceInstance) {
/*  85 */         camera2DeviceInstance.close();
/*  86 */         CameraSurfaceImpl.this.mCameraDevice = null;
/*     */       }
/*     */ 
/*     */       
/*     */       public void onError(@NonNull CameraDevice camera2DeviceInstance, int error) {
/*  91 */         camera2DeviceInstance.close();
/*  92 */         CameraSurfaceImpl.this.mCameraDevice = null;
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private final CameraEventListener mCameraEventListener;
/*     */   
/*     */   private boolean mImageReaderCreated;
/*     */   
/*     */   private final ImageReader.OnImageAvailableListener mImageAvailableAndProcessHandler;
/*     */   
/*     */   private int mCamera2DeviceID;
/*     */   
/*     */   private CaptureRequest.Builder mCaptureRequestBuilder;
/*     */   
/*     */   private CameraCaptureSession mYUV_CaptureAndSendSession;
/*     */ 
/*     */   
/*     */   public CameraSurfaceImpl(CameraEventListener cameraEventListener, Context appContext) {
/* 111 */     this.mImageAvailableAndProcessHandler = new ImageReader.OnImageAvailableListener()
/*     */       {
/*     */         public void onImageAvailable(ImageReader reader)
/*     */         {
/* 115 */           Image imageInstance = reader.acquireLatestImage();
/* 116 */           if (imageInstance == null) {
/*     */             
/* 118 */             Log.v(CameraSurfaceImpl.TAG, "onImageAvailable(): unable to acquire new image");
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 123 */           Image.Plane[] imagePlanes = imageInstance.getPlanes();
/* 124 */           int imagePlaneCount = Math.min(4, imagePlanes.length);
/* 125 */           ByteBuffer[] imageBuffers = new ByteBuffer[imagePlaneCount];
/* 126 */           int[] imageBufferPixelStrides = new int[imagePlaneCount];
/* 127 */           int[] imageBufferRowStrides = new int[imagePlaneCount];
/* 128 */           for (int i = 0; i < imagePlaneCount; i++) {
/* 129 */             imageBuffers[i] = imagePlanes[i].getBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 135 */             imageBufferPixelStrides[i] = imagePlanes[i].getPixelStride();
/* 136 */             imageBufferRowStrides[i] = imagePlanes[i].getRowStride();
/*     */           } 
/*     */           
/* 139 */           if (CameraSurfaceImpl.this.mCameraEventListener != null) {
/* 140 */             CameraSurfaceImpl.this.mCameraEventListener.cameraStreamFrame(imageBuffers, imageBufferPixelStrides, imageBufferRowStrides);
/*     */           }
/*     */           
/* 143 */           imageInstance.close();
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     this.mCamera2DeviceID = -1; this.mCameraEventListener = cameraEventListener; this.mAppContext = appContext;
/*     */   } public void surfaceCreated() { Log.i(TAG, "surfaceCreated(): called"); SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.mAppContext); int defaultCameraIndexId = this.mAppContext.getResources().getIdentifier("pref_defaultValue_cameraIndex", "string", this.mAppContext.getPackageName()); this.mCamera2DeviceID = Integer.parseInt(prefs.getString("pref_cameraIndex", this.mAppContext.getResources().getString(defaultCameraIndexId))); Log.i(TAG, "surfaceCreated(): will attempt to open camera \"" + this.mCamera2DeviceID + "\", set orientation, set preview surface"); int defaultCameraValueId = this.mAppContext.getResources().getIdentifier("pref_defaultValue_cameraResolution", "string", this.mAppContext.getPackageName()); String camResolution = prefs.getString("pref_cameraResolution", "auto"); String[] dims = camResolution.split("x", 2); this.mImageReaderVideoSize = new Size(Integer.parseInt(dims[0]), Integer.parseInt(dims[1]));
/*     */     DisplayMetrics dm = this.mAppContext.getResources().getDisplayMetrics();
/*     */     Toast.makeText(this.mAppContext, "w = " + dm.widthPixels + " h = " + dm.heightPixels, 0).show();
/*     */     this.mImageReader = ImageReader.newInstance(dm.widthPixels, dm.heightPixels, 35, 2);
/*     */     this.mImageReader.setOnImageAvailableListener(this.mImageAvailableAndProcessHandler, null);
/* 233 */     this.mImageReaderCreated = true; } private void startCaptureAndForwardFramesSession() { if (null == this.mCameraDevice || !this.mImageReaderCreated) {
/*     */       return;
/*     */     }
/*     */     
/* 237 */     closeYUV_CaptureAndForwardSession();
/*     */     
/*     */     try {
/* 240 */       this.mCaptureRequestBuilder = this.mCameraDevice.createCaptureRequest(1);
/* 241 */       List<Surface> surfaces = new ArrayList<>();
/*     */ 
/*     */       
/* 244 */       Surface surfaceInstance = this.mImageReader.getSurface();
/* 245 */       surfaces.add(surfaceInstance);
/* 246 */       this.mCaptureRequestBuilder.addTarget(surfaceInstance);
/*     */       
/* 248 */       this.mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback()
/*     */           {
/*     */             
/*     */             public void onConfigured(@NonNull CameraCaptureSession session)
/*     */             {
/*     */               try {
/* 254 */                 if (CameraSurfaceImpl.this.mCameraEventListener != null) {
/* 255 */                   CameraSurfaceImpl.this.mCameraEventListener.cameraStreamStarted(CameraSurfaceImpl.this.mImageReaderVideoSize.getWidth(), CameraSurfaceImpl.this.mImageReaderVideoSize.getHeight(), "YUV_420_888", CameraSurfaceImpl.this.mCamera2DeviceID, false);
/*     */                 }
/* 257 */                 CameraSurfaceImpl.this.mYUV_CaptureAndSendSession = session;
/*     */                 
/* 259 */                 CameraSurfaceImpl.this.mYUV_CaptureAndSendSession.setRepeatingRequest(CameraSurfaceImpl.this.mCaptureRequestBuilder.build(), null, null);
/* 260 */               } catch (CameraAccessException e) {
/* 261 */                 e.printStackTrace();
/*     */               } 
/*     */             }
/*     */ 
/*     */             
/*     */             public void onConfigureFailed(@NonNull CameraCaptureSession session) {
/* 267 */               Toast.makeText(CameraSurfaceImpl.this.mAppContext, "Unable to setup camera sensor capture session", 0).show();
/*     */             }
/*     */           }null);
/*     */     }
/* 271 */     catch (CameraAccessException ex) {
/* 272 */       ex.printStackTrace();
/*     */     }  }
/*     */   public void surfaceChanged() { Log.i(TAG, "surfaceChanged(): called"); if (!this.mImageReaderCreated)
/*     */       surfaceCreated();  if (!isCamera2DeviceOpen())
/*     */       openCamera2(this.mCamera2DeviceID); 
/*     */     if (isCamera2DeviceOpen() && null == this.mYUV_CaptureAndSendSession)
/* 278 */       startCaptureAndForwardFramesSession();  } public void closeCameraDevice() { closeYUV_CaptureAndForwardSession();
/* 279 */     if (null != this.mCameraDevice) {
/* 280 */       this.mCameraDevice.close();
/* 281 */       this.mCameraDevice = null;
/*     */     } 
/* 283 */     if (null != this.mImageReader) {
/* 284 */       this.mImageReader.close();
/* 285 */       this.mImageReader = null;
/*     */     } 
/* 287 */     if (this.mCameraEventListener != null) {
/* 288 */       this.mCameraEventListener.cameraStreamStopped();
/*     */     }
/* 290 */     this.mImageReaderCreated = false; }
/*     */   private void openCamera2(int camera2DeviceID) { Log.i(TAG, "openCamera2(): called"); CameraManager camera2DeviceMgr = (CameraManager)this.mAppContext.getSystemService("camera"); try { if (0 == ContextCompat.checkSelfPermission(this.mAppContext, "android.permission.CAMERA")) { camera2DeviceMgr.openCamera(Integer.toString(camera2DeviceID), this.mCamera2DeviceStateCallback, null); return; }  } catch (CameraAccessException ex) { Log.e(TAG, "openCamera2(): CameraAccessException caught, " + ex.getMessage()); }
/*     */     catch (Exception ex) { Log.e(TAG, "openCamera2(): exception caught, " + ex.getMessage()); }
/*     */      if (null == camera2DeviceMgr)
/* 294 */       Log.e(TAG, "openCamera2(): Camera2 DeviceMgr not set");  Log.e(TAG, "openCamera2(): abnormal exit"); } private void closeYUV_CaptureAndForwardSession() { if (this.mYUV_CaptureAndSendSession != null) {
/* 295 */       this.mYUV_CaptureAndSendSession.close();
/* 296 */       this.mYUV_CaptureAndSendSession = null;
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCamera2DeviceOpen() {
/* 305 */     return (null != this.mCameraDevice);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isImageReaderCreated() {
/* 310 */     return this.mImageReaderCreated;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\KYSHIYONG\arxjUnity.jar!\org\artoolkitx\arx\arxj\camera\CameraSurfaceImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */