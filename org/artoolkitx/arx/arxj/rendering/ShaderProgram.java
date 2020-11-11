/*     */ package org.artoolkitx.arx.arxj.rendering;
/*     */ 
/*     */ import android.opengl.GLES20;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ShaderProgram
/*     */ {
/*  69 */   protected final int positionDataSize = 3;
/*     */ 
/*     */   
/*  72 */   protected final int colorDataSize = 4;
/*     */   
/*  74 */   private final int mBytesPerFloat = 4;
/*     */ 
/*     */ 
/*     */   
/*  78 */   protected final int positionStrideBytes = 12;
/*     */ 
/*     */   
/*  81 */   protected final int colorStrideBytes = 16;
/*     */   
/*     */   protected final int shaderProgramHandle;
/*     */   
/*     */   private float[] projectionMatrix;
/*     */   private float[] modelViewMatrix;
/*     */   
/*     */   public ShaderProgram(OpenGLShader vertexShader, OpenGLShader fragmentShader) {
/*  89 */     this.shaderProgramHandle = createProgram(vertexShader.configureShader(), fragmentShader.configureShader());
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract int getProjectionMatrixHandle();
/*     */ 
/*     */   
/*     */   public abstract int getModelViewMatrixHandle();
/*     */ 
/*     */   
/*     */   protected abstract void bindAttributes();
/*     */   
/*     */   private int createProgram(int vertexShaderHandle, int fragmentShaderHandle) {
/* 102 */     int programHandle = GLES20.glCreateProgram();
/* 103 */     String programErrorLog = "";
/*     */     
/* 105 */     if (programHandle != 0) {
/*     */       
/* 107 */       GLES20.glAttachShader(programHandle, vertexShaderHandle);
/*     */ 
/*     */       
/* 110 */       GLES20.glAttachShader(programHandle, fragmentShaderHandle);
/*     */ 
/*     */       
/* 113 */       GLES20.glLinkProgram(programHandle);
/*     */ 
/*     */       
/* 116 */       int[] linkStatus = new int[1];
/* 117 */       GLES20.glGetProgramiv(programHandle, 35714, linkStatus, 0);
/*     */ 
/*     */       
/* 120 */       if (linkStatus[0] == 0) {
/* 121 */         programErrorLog = GLES20.glGetProgramInfoLog(programHandle);
/* 122 */         GLES20.glDeleteProgram(programHandle);
/* 123 */         programHandle = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 127 */     if (programHandle == 0) {
/* 128 */       throw new RuntimeException("Error creating program.\\n " + programErrorLog);
/*     */     }
/* 130 */     return programHandle;
/*     */   }
/*     */   
/*     */   public int getShaderProgramHandle() {
/* 134 */     return this.shaderProgramHandle;
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
/*     */   public void render(FloatBuffer vertexBuffer, FloatBuffer colorBuffer, ByteBuffer indexBuffer) {
/* 146 */     throw new RuntimeException("Please override at least this method.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(FloatBuffer vertexBuffer, ByteBuffer indexBuffer) {
/* 151 */     render(vertexBuffer, null, indexBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(float[] position) {
/* 162 */     render(RenderUtils.buildFloatBuffer(position), null);
/*     */   }
/*     */   
/*     */   public void setProjectionMatrix(float[] projectionMatrix) {
/* 166 */     this.projectionMatrix = projectionMatrix;
/*     */   }
/*     */   
/*     */   public void setModelViewMatrix(float[] modelViewMatrix) {
/* 170 */     this.modelViewMatrix = modelViewMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setupShaderUsage() {
/* 180 */     GLES20.glUseProgram(this.shaderProgramHandle);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     if (this.projectionMatrix != null) {
/* 195 */       GLES20.glUniformMatrix4fv(getProjectionMatrixHandle(), 1, false, this.projectionMatrix, 0);
/*     */     } else {
/* 197 */       throw new RuntimeException("You need to set the projection matrix.");
/*     */     } 
/* 199 */     if (this.modelViewMatrix != null)
/* 200 */       GLES20.glUniformMatrix4fv(getModelViewMatrixHandle(), 1, false, this.modelViewMatrix, 0); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\KYSHIYONG\arxjUnity.jar!\org\artoolkitx\arx\arxj\rendering\ShaderProgram.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */