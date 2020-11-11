/*     */ package org.artoolkitx.arx.arxj;
/*     */ 
/*     */ import android.opengl.Matrix;
/*     */ import android.util.Log;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ARController
/*     */ {
/*     */   private static final String TAG = "ARController";
/*     */   private static boolean loadedNative = false;
/*     */   private static boolean initedNative = false;
/*  67 */   private static ARController instance = null;
/*     */   
/*     */   static {
/*  70 */     loadedNative = ARX_jni.loadNativeLibrary();
/*  71 */     if (!loadedNative) { Log.e("ARController", "Loading native library failed!"); }
/*  72 */     else { Log.i("ARController", "Loaded native library."); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ARController() {
/*  79 */     Log.i("ARController", "ARController(): ARController constructor");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ARController getInstance() {
/*  88 */     if (instance == null) instance = new ARController(); 
/*  89 */     return instance;
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
/*     */   public boolean initialiseNative(String resourcesDirectoryPath) {
/* 102 */     if (!loadedNative) return false; 
/* 103 */     ARX_jni.arwSetLogLevel(0);
/* 104 */     if (!ARX_jni.arwInitialiseAR()) {
/* 105 */       Log.e("ARController", "Error initialising native library!");
/* 106 */       return false;
/*     */     } 
/* 108 */     Log.i("ARController", "artoolkitX v" + ARX_jni.arwGetARToolKitVersion());
/* 109 */     if (!ARX_jni.arwChangeToResourcesDir(resourcesDirectoryPath)) {
/* 110 */       Log.i("ARController", "Error while attempting to change working directory to resources directory.");
/*     */     }
/* 112 */     initedNative = true;
/* 113 */     return true;
/*     */   }
/*     */   
/*     */   public String getARToolKitXVersion() {
/* 117 */     return ARX_jni.arwGetARToolKitVersion();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean startWithPushedVideo(int videoWidth, int videoHeight, String pixelFormat, String cameraParaPath, int cameraIndex, boolean cameraIsFrontFacing) {
/* 136 */     if (!initedNative) {
/* 137 */       Log.e("ARController", "startWithPushedVideo(): Cannot start because native interface not inited.");
/* 138 */       return false;
/*     */     } 
/*     */     
/* 141 */     if (!ARX_jni.arwStartRunning("", cameraParaPath)) {
/* 142 */       Log.e("ARController", "startWithPushedVideo(): Error starting");
/* 143 */       return false;
/*     */     } 
/* 145 */     if (ARX_jni.arwAndroidVideoPushInit(0, videoWidth, videoHeight, pixelFormat, cameraIndex, cameraIsFrontFacing ? 1 : 0) < 0) {
/*     */ 
/*     */       
/* 148 */       Log.e("ARController", "startWithPushedVideo(): Error initialising Android video");
/* 149 */       return false;
/*     */     } 
/*     */     
/* 152 */     return true;
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
/*     */   public boolean onlyPushVideo(int videoWidth, int videoHeight, String pixelFormat, int cameraIndex, boolean cameraIsFrontFacing) {
/* 167 */     if (isInited()) {
/* 168 */       return (ARX_jni.arwAndroidVideoPushInit(0, videoWidth, videoHeight, pixelFormat, cameraIndex, cameraIsFrontFacing ? 1 : 0) < 0);
/*     */     }
/*     */     
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getDebugMode() {
/* 180 */     if (!initedNative) return false; 
/* 181 */     return ARX_jni.arwGetTrackerOptionBool(8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDebugMode(boolean debug) {
/* 190 */     if (!initedNative)
/* 191 */       return;  ARX_jni.arwSetTrackerOptionBool(8, debug);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getThreshold() {
/* 201 */     if (!initedNative) return -1; 
/* 202 */     return ARX_jni.arwGetTrackerOptionInt(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThreshold(int threshold) {
/* 211 */     if (!initedNative)
/* 212 */       return;  ARX_jni.arwSetTrackerOptionInt(1, threshold);
/*     */   }
/*     */   
/*     */   public float getBorderSize() {
/* 216 */     if (!initedNative) return 0.0F; 
/* 217 */     return ARX_jni.arwGetTrackerOptionFloat(5);
/*     */   }
/*     */   
/*     */   public void setBorderSize(float size) {
/* 221 */     if (!initedNative)
/* 222 */       return;  ARX_jni.arwSetTrackerOptionFloat(5, size);
/*     */   }
/*     */   
/*     */   public int getPatternSize() {
/* 226 */     if (!initedNative) return 0; 
/* 227 */     return ARX_jni.arwGetTrackerOptionInt(9);
/*     */   }
/*     */   
/*     */   public void setPatternSize(int size) {
/* 231 */     if (!initedNative)
/* 232 */       return;  ARX_jni.arwSetTrackerOptionInt(9, size);
/*     */   }
/*     */   
/*     */   public int getPatternCountMax() {
/* 236 */     if (!initedNative) return 0; 
/* 237 */     return ARX_jni.arwGetTrackerOptionInt(10);
/*     */   }
/*     */   
/*     */   public void setPatternCountMax(int count) {
/* 241 */     if (!initedNative)
/* 242 */       return;  ARX_jni.arwSetTrackerOptionInt(10, count);
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
/*     */   public float[] getProjectionMatrix(float nearPlane, float farPlane) {
/* 254 */     if (!initedNative) return null; 
/* 255 */     return ARX_jni.arwGetProjectionMatrix(nearPlane, farPlane);
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
/*     */   public int addTrackable(String cfg) {
/* 270 */     if (!initedNative) return -1; 
/* 271 */     return ARX_jni.arwAddTrackable(cfg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean queryTrackableVisibilityAndTransformation(int trackableUID, float[] matrix) {
/* 281 */     if (!initedNative) return false; 
/* 282 */     return ARX_jni.arwQueryTrackableVisibilityAndTransformation(trackableUID, matrix);
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
/*     */   public boolean isInited() {
/* 294 */     if (!initedNative) {
/* 295 */       initedNative = ARX_jni.arwIsInited();
/*     */     }
/* 297 */     return initedNative;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRunning() {
/* 306 */     if (!initedNative) return false; 
/* 307 */     return ARX_jni.arwIsRunning();
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
/*     */   public boolean convertAndDetect1(byte[] frame, int frameSize) {
/* 319 */     if (!initedNative || frame == null) {
/* 320 */       return false;
/*     */     }
/*     */     
/* 323 */     if (ARX_jni.arwAndroidVideoPush1(0, frame, frameSize) < 0) {
/* 324 */       return false;
/*     */     }
/*     */     
/* 327 */     if (!ARX_jni.arwCapture()) {
/* 328 */       return false;
/*     */     }
/* 330 */     return ARX_jni.arwUpdateAR();
/*     */   }
/*     */   
/*     */   public boolean convert1(byte[] frame, int frameSize) {
/* 334 */     return (ARX_jni.arwAndroidVideoPush1(0, frame, frameSize) < 0);
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
/*     */   public boolean convertAndDetect2(ByteBuffer[] framePlanes, int[] framePlanePixelStrides, int[] framePlaneRowStrides) {
/* 346 */     if (!initedNative || framePlanes == null) {
/* 347 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 351 */     if (!convert(framePlanes, framePlanePixelStrides, framePlaneRowStrides)) return false;
/*     */ 
/*     */ 
/*     */     
/* 355 */     if (!ARX_jni.arwCapture()) {
/* 356 */       return false;
/*     */     }
/* 358 */     return ARX_jni.arwUpdateAR();
/*     */   }
/*     */   
/*     */   public boolean convert(ByteBuffer[] framePlanes, int[] framePlanePixelStrides, int[] framePlaneRowStrides) {
/* 362 */     int framePlaneCount = Math.min(framePlanes.length, 4);
/* 363 */     if (framePlaneCount == 1) {
/* 364 */       if (ARX_jni.arwAndroidVideoPush2(0, framePlanes[0], framePlanePixelStrides[0], framePlaneRowStrides[0], null, 0, 0, null, 0, 0, null, 0, 0) < 0)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 369 */         return false;
/*     */       }
/* 371 */     } else if (framePlaneCount == 2) {
/* 372 */       if (ARX_jni.arwAndroidVideoPush2(0, framePlanes[0], framePlanePixelStrides[0], framePlaneRowStrides[0], framePlanes[1], framePlanePixelStrides[1], framePlaneRowStrides[1], null, 0, 0, null, 0, 0) < 0)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 377 */         return false;
/*     */       }
/* 379 */     } else if (framePlaneCount == 3) {
/* 380 */       if (ARX_jni.arwAndroidVideoPush2(0, framePlanes[0], framePlanePixelStrides[0], framePlaneRowStrides[0], framePlanes[1], framePlanePixelStrides[1], framePlaneRowStrides[1], framePlanes[2], framePlanePixelStrides[2], framePlaneRowStrides[2], null, 0, 0) < 0)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 385 */         return false;
/*     */       }
/* 387 */     } else if (framePlaneCount == 4 && 
/* 388 */       ARX_jni.arwAndroidVideoPush2(0, framePlanes[0], framePlanePixelStrides[0], framePlaneRowStrides[0], framePlanes[1], framePlanePixelStrides[1], framePlaneRowStrides[1], framePlanes[2], framePlanePixelStrides[2], framePlaneRowStrides[2], framePlanes[3], framePlanePixelStrides[3], framePlaneRowStrides[3]) < 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 393 */       return false;
/*     */     } 
/*     */     
/* 396 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopAndFinal() {
/* 404 */     if (!initedNative)
/*     */       return; 
/* 406 */     ARX_jni.arwAndroidVideoPushFinal(0);
/* 407 */     ARX_jni.arwStopRunning();
/* 408 */     ARX_jni.arwShutdownAR();
/*     */     
/* 410 */     initedNative = false;
/*     */   }
/*     */   
/*     */   public void onlyFinal() {
/* 414 */     if (isInited()) {
/* 415 */       ARX_jni.arwAndroidVideoPushFinal(0);
/*     */     }
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
/*     */   public float[] calculateReferenceMatrix(int idMarkerBase, int idMarker2) {
/* 428 */     float[] referenceMarkerTranslationMatrix = new float[16];
/* 429 */     float[] secondMarkerTranslationMatrix = new float[16];
/* 430 */     if (queryTrackableVisibilityAndTransformation(idMarkerBase, referenceMarkerTranslationMatrix) && queryTrackableVisibilityAndTransformation(idMarker2, secondMarkerTranslationMatrix)) {
/* 431 */       float[] invertedMatrixOfReferenceMarker = new float[16];
/*     */       
/* 433 */       Matrix.invertM(invertedMatrixOfReferenceMarker, 0, referenceMarkerTranslationMatrix, 0);
/*     */       
/* 435 */       float[] transformationFromMarker1ToMarker2 = new float[16];
/* 436 */       Matrix.multiplyMM(transformationFromMarker1ToMarker2, 0, invertedMatrixOfReferenceMarker, 0, secondMarkerTranslationMatrix, 0);
/*     */       
/* 438 */       return transformationFromMarker1ToMarker2;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 443 */     Log.e("ARController", "calculateReferenceMatrix(): Currently there are no two markers visible at the same time");
/* 444 */     return null;
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
/*     */   public float distance(int referenceMarker, int markerId2) {
/* 457 */     float[] referenceMatrix = calculateReferenceMatrix(referenceMarker, markerId2);
/*     */     
/* 459 */     if (referenceMatrix != null) {
/* 460 */       float distanceX = referenceMatrix[12];
/* 461 */       float distanceY = referenceMatrix[13];
/* 462 */       float distanceZ = referenceMatrix[14];
/*     */       
/* 464 */       Log.d("ARController", "distance(): Marker distance: x: " + distanceX + " y: " + distanceY + " z: " + distanceZ);
/* 465 */       float length = Matrix.length(distanceX, distanceY, distanceZ);
/* 466 */       Log.d("ARController", "distance(): Absolute distance: " + length);
/*     */       
/* 468 */       return length;
/*     */     } 
/* 470 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] retrievePosition(int referenceMarkerId, int markerIdToGetThePositionFor) {
/* 481 */     float[] initialVector = { 1.0F, 1.0F, 1.0F, 1.0F };
/* 482 */     float[] positionVector = new float[4];
/*     */     
/* 484 */     float[] transformationMatrix = calculateReferenceMatrix(referenceMarkerId, markerIdToGetThePositionFor);
/* 485 */     if (transformationMatrix != null) {
/* 486 */       Matrix.multiplyMV(positionVector, 0, transformationMatrix, 0, initialVector, 0);
/* 487 */       return positionVector;
/*     */     } 
/* 489 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean drawVideoInit(int videoSourceIndex) {
/* 499 */     return ARX_jni.arwDrawVideoInit(videoSourceIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean drawVideoSettings(int videoSourceIndex, int width, int height, boolean rotate90, boolean flipH, boolean flipV, int hAlign, int vAlign, int scalingMode, int[] viewport) {
/* 509 */     return ARX_jni.arwDrawVideoSettings(videoSourceIndex, width, height, rotate90, flipH, flipV, hAlign, vAlign, scalingMode, viewport);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean drawVideoSettings(int videoSourceIndex) {
/* 520 */     return ARX_jni.arwDrawVideo(videoSourceIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean drawVideoFinal(int videoSourceIndex) {
/* 531 */     return ARX_jni.arwDrawVideoFinal(videoSourceIndex);
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\KYSHIYONG\arxjUnity.jar!\org\artoolkitx\arx\arxj\ARController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */