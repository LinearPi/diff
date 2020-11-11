/*     */ package org.artoolkitx.arx.arxj.rendering;
/*     */ 
/*     */ import android.opengl.GLES20;
/*     */ import android.opengl.GLSurfaceView;
/*     */ import android.util.Log;
/*     */ import javax.microedition.khronos.egl.EGLConfig;
/*     */ import javax.microedition.khronos.opengles.GL10;
/*     */ import org.artoolkitx.arx.arxj.ARController;
/*     */ import org.artoolkitx.arx.arxj.rendering.shader_impl.SimpleFragmentShader;
/*     */ import org.artoolkitx.arx.arxj.rendering.shader_impl.SimpleShaderProgram;
/*     */ import org.artoolkitx.arx.arxj.rendering.shader_impl.SimpleVertexShader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ARRenderer
/*     */   implements GLSurfaceView.Renderer
/*     */ {
/*     */   private SimpleShaderProgram simpleShaderProgram;
/*     */   private int width;
/*     */   private int height;
/*     */   private int cameraIndex;
/*  71 */   private int[] viewport = new int[4];
/*     */   
/*     */   private boolean firstRun = true;
/*  74 */   private static final String TAG = ARRenderer.class.getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean configureARScene() {
/*  82 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSurfaceCreated(GL10 unused, EGLConfig config) {
/*  88 */     GLES20.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/*  89 */     this.simpleShaderProgram = new SimpleShaderProgram((OpenGLShader)new SimpleVertexShader(), (OpenGLShader)new SimpleFragmentShader());
/*  90 */     GLES20.glUseProgram(this.simpleShaderProgram.getShaderProgramHandle());
/*     */   }
/*     */   
/*     */   public void onSurfaceChanged(GL10 unused, int w, int h) {
/*  94 */     this.width = w;
/*  95 */     this.height = h;
/*  96 */     if (ARController.getInstance().isRunning())
/*     */     {
/*  98 */       ARController.getInstance().drawVideoSettings(this.cameraIndex, w, h, false, false, false, 1, 1, 0, this.viewport);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onDrawFrame(GL10 unused) {
/* 103 */     if (ARController.getInstance().isRunning()) {
/*     */       
/* 105 */       if (this.firstRun) {
/* 106 */         boolean isDisplayFrameInited = ARController.getInstance().drawVideoInit(this.cameraIndex);
/* 107 */         if (!isDisplayFrameInited) {
/* 108 */           Log.e(TAG, "Display Frame not inited");
/*     */         }
/*     */         
/* 111 */         if (!ARController.getInstance().drawVideoSettings(this.cameraIndex, this.width, this.height, false, false, false, 1, 1, 0, this.viewport)) {
/*     */ 
/*     */           
/* 114 */           Log.e(TAG, "Error during call of displayFrameSettings.");
/*     */         } else {
/* 116 */           Log.i(TAG, "Viewport {" + this.viewport[0] + ", " + this.viewport[1] + ", " + this.viewport[2] + ", " + this.viewport[3] + "}.");
/*     */         } 
/*     */         
/* 119 */         this.firstRun = false;
/*     */       } 
/* 121 */       GLES20.glClear(16640);
/* 122 */       if (!ARController.getInstance().drawVideoSettings(this.cameraIndex)) {
/* 123 */         Log.e(TAG, "Error during call of displayFrame.");
/*     */       }
/* 125 */       draw();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw() {
/* 133 */     GLES20.glViewport(this.viewport[0], this.viewport[1], this.viewport[2], this.viewport[3]);
/*     */ 
/*     */     
/* 136 */     this.simpleShaderProgram.setProjectionMatrix(ARController.getInstance().getProjectionMatrix(10.0F, 10000.0F));
/* 137 */     float[] camPosition = { 1.0F, 1.0F, 1.0F };
/* 138 */     this.simpleShaderProgram.render(camPosition);
/*     */   }
/*     */ 
/*     */   
/*     */   public ShaderProgram getSimpleShaderProgram() {
/* 143 */     return (ShaderProgram)this.simpleShaderProgram;
/*     */   }
/*     */   
/*     */   public void setCameraIndex(int cameraIndex) {
/* 147 */     this.cameraIndex = cameraIndex;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\rendering\ARRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */