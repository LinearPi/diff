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
/*     */ import android.util.Log;
/*     */ import android.util.Size;
/*     */ import android.view.Surface;
/*     */ import android.widget.Toast;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.core.content.ContextCompat;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class CameraSurfaceImpl
/*     */   implements CameraSurface
/*     */ {
/*  72 */   private static final String TAG = CameraSurfaceImpl.class.getSimpleName();
/*     */   private CameraDevice mCameraDevice;
/*     */   private ImageReader mImageReader;
/*     */   private Size mImageReaderVideoSize;
/*     */   private final Context mAppContext;
/*     */   
/*  78 */   private final CameraDevice.StateCallback mCamera2DeviceStateCallback = new CameraDevice.StateCallback()
/*     */     {
/*     */       public void onOpened(@NonNull CameraDevice camera2DeviceInstance) {
/*  81 */         CameraSurfaceImpl.this.mCameraDevice = camera2DeviceInstance;
/*  82 */         CameraSurfaceImpl.this.startCaptureAndForwardFramesSession();
/*     */       }
/*     */ 
/*     */       
/*     */       public void onDisconnected(@NonNull CameraDevice camera2DeviceInstance) {
/*  87 */         camera2DeviceInstance.close();
/*  88 */         CameraSurfaceImpl.this.mCameraDevice = null;
/*     */       }
/*     */ 
/*     */       
/*     */       public void onError(@NonNull CameraDevice camera2DeviceInstance, int error) {
/*  93 */         camera2DeviceInstance.close();
/*  94 */         CameraSurfaceImpl.this.mCameraDevice = null;
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
/* 113 */     this.mImageAvailableAndProcessHandler = new ImageReader.OnImageAvailableListener()
/*     */       {
/*     */         public void onImageAvailable(ImageReader reader)
/*     */         {
/* 117 */           Image imageInstance = reader.acquireLatestImage();
/* 118 */           if (imageInstance == null) {
/*     */             
/* 120 */             Log.v(CameraSurfaceImpl.TAG, "onImageAvailable(): unable to acquire new image");
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 125 */           Image.Plane[] imagePlanes = imageInstance.getPlanes();
/* 126 */           int imagePlaneCount = Math.min(4, imagePlanes.length);
/* 127 */           ByteBuffer[] imageBuffers = new ByteBuffer[imagePlaneCount];
/* 128 */           int[] imageBufferPixelStrides = new int[imagePlaneCount];
/* 129 */           int[] imageBufferRowStrides = new int[imagePlaneCount];
/* 130 */           for (int i = 0; i < imagePlaneCount; i++) {
/* 131 */             imageBuffers[i] = imagePlanes[i].getBuffer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 137 */             imageBufferPixelStrides[i] = imagePlanes[i].getPixelStride();
/* 138 */             imageBufferRowStrides[i] = imagePlanes[i].getRowStride();
/*     */           } 
/*     */           
/* 141 */           if (CameraSurfaceImpl.this.mCameraEventListener != null) {
/* 142 */             CameraSurfaceImpl.this.mCameraEventListener.cameraStreamFrame(imageBuffers, imageBufferPixelStrides, imageBufferRowStrides);
/*     */           }
/*     */           
/* 145 */           imageInstance.close();
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
/* 226 */     this.mCamera2DeviceID = -1; this.mCameraEventListener = cameraEventListener; this.mAppContext = appContext;
/*     */   } public void surfaceCreated() { Log.i(TAG, "surfaceCreated(): called"); SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.mAppContext); int defaultCameraIndexId = this.mAppContext.getResources().getIdentifier("pref_defaultValue_cameraIndex", "string", this.mAppContext.getPackageName()); this.mCamera2DeviceID = Integer.parseInt(prefs.getString("pref_cameraIndex", this.mAppContext.getResources().getString(defaultCameraIndexId))); Log.i(TAG, "surfaceCreated(): will attempt to open camera \"" + this.mCamera2DeviceID + "\", set orientation, set preview surface"); int defaultCameraValueId = this.mAppContext.getResources().getIdentifier("pref_defaultValue_cameraResolution", "string", this.mAppContext.getPackageName()); String camResolution = prefs.getString("pref_cameraResolution", this.mAppContext.getResources().getString(defaultCameraValueId));
/*     */     String[] dims = camResolution.split("x", 2);
/*     */     this.mImageReaderVideoSize = new Size(Integer.parseInt(dims[0]), Integer.parseInt(dims[1]));
/*     */     this.mImageReader = ImageReader.newInstance(this.mImageReaderVideoSize.getWidth(), this.mImageReaderVideoSize.getHeight(), 35, 2);
/*     */     this.mImageReader.setOnImageAvailableListener(this.mImageAvailableAndProcessHandler, null);
/* 232 */     this.mImageReaderCreated = true; } private void startCaptureAndForwardFramesSession() { if (null == this.mCameraDevice || !this.mImageReaderCreated) {
/*     */       return;
/*     */     }
/*     */     
/* 236 */     closeYUV_CaptureAndForwardSession();
/*     */     
/*     */     try {
/* 239 */       this.mCaptureRequestBuilder = this.mCameraDevice.createCaptureRequest(1);
/* 240 */       List<Surface> surfaces = new ArrayList<>();
/*     */ 
/*     */       
/* 243 */       Surface surfaceInstance = this.mImageReader.getSurface();
/* 244 */       surfaces.add(surfaceInstance);
/* 245 */       this.mCaptureRequestBuilder.addTarget(surfaceInstance);
/*     */       
/* 247 */       this.mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback()
/*     */           {
/*     */             
/*     */             public void onConfigured(@NonNull CameraCaptureSession session)
/*     */             {
/*     */               try {
/* 253 */                 if (CameraSurfaceImpl.this.mCameraEventListener != null) {
/* 254 */                   CameraSurfaceImpl.this.mCameraEventListener.cameraStreamStarted(CameraSurfaceImpl.this.mImageReaderVideoSize.getWidth(), CameraSurfaceImpl.this.mImageReaderVideoSize.getHeight(), "YUV_420_888", CameraSurfaceImpl.this.mCamera2DeviceID, false);
/*     */                 }
/* 256 */                 CameraSurfaceImpl.this.mYUV_CaptureAndSendSession = session;
/*     */                 
/* 258 */                 CameraSurfaceImpl.this.mYUV_CaptureAndSendSession.setRepeatingRequest(CameraSurfaceImpl.this.mCaptureRequestBuilder.build(), null, null);
/* 259 */               } catch (CameraAccessException e) {
/* 260 */                 e.printStackTrace();
/*     */               } 
/*     */             }
/*     */ 
/*     */             
/*     */             public void onConfigureFailed(@NonNull CameraCaptureSession session) {
/* 266 */               Toast.makeText(CameraSurfaceImpl.this.mAppContext, "Unable to setup camera sensor capture session", 0).show();
/*     */             }
/*     */           }null);
/*     */     }
/* 270 */     catch (CameraAccessException ex) {
/* 271 */       ex.printStackTrace();
/*     */     }  }
/*     */   public void surfaceChanged() { Log.i(TAG, "surfaceChanged(): called"); if (!this.mImageReaderCreated)
/*     */       surfaceCreated();  if (!isCamera2DeviceOpen())
/*     */       openCamera2(this.mCamera2DeviceID); 
/*     */     if (isCamera2DeviceOpen() && null == this.mYUV_CaptureAndSendSession)
/* 277 */       startCaptureAndForwardFramesSession();  } public void closeCameraDevice() { closeYUV_CaptureAndForwardSession();
/* 278 */     if (null != this.mCameraDevice) {
/* 279 */       this.mCameraDevice.close();
/* 280 */       this.mCameraDevice = null;
/*     */     } 
/* 282 */     if (null != this.mImageReader) {
/* 283 */       this.mImageReader.close();
/* 284 */       this.mImageReader = null;
/*     */     } 
/* 286 */     if (this.mCameraEventListener != null) {
/* 287 */       this.mCameraEventListener.cameraStreamStopped();
/*     */     }
/* 289 */     this.mImageReaderCreated = false; }
/*     */   private void openCamera2(int camera2DeviceID) { Log.i(TAG, "openCamera2(): called"); CameraManager camera2DeviceMgr = (CameraManager)this.mAppContext.getSystemService("camera"); try { if (0 == ContextCompat.checkSelfPermission(this.mAppContext, "android.permission.CAMERA")) { camera2DeviceMgr.openCamera(Integer.toString(camera2DeviceID), this.mCamera2DeviceStateCallback, null); return; }  } catch (CameraAccessException ex) { Log.e(TAG, "openCamera2(): CameraAccessException caught, " + ex.getMessage()); }
/*     */     catch (Exception ex) { Log.e(TAG, "openCamera2(): exception caught, " + ex.getMessage()); }
/*     */      if (null == camera2DeviceMgr)
/* 293 */       Log.e(TAG, "openCamera2(): Camera2 DeviceMgr not set");  Log.e(TAG, "openCamera2(): abnormal exit"); } private void closeYUV_CaptureAndForwardSession() { if (this.mYUV_CaptureAndSendSession != null) {
/* 294 */       this.mYUV_CaptureAndSendSession.close();
/* 295 */       this.mYUV_CaptureAndSendSession = null;
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCamera2DeviceOpen() {
/* 304 */     return (null != this.mCameraDevice);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isImageReaderCreated() {
/* 309 */     return this.mImageReaderCreated;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\camera\CameraSurfaceImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */