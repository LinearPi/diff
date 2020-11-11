/*     */ package org.artoolkitx.arx.arxj.rendering.shader_impl;
/*     */ 
/*     */ import android.opengl.GLES20;
/*     */ import org.artoolkitx.arx.arxj.rendering.OpenGLShader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleVertexShader
/*     */   implements OpenGLShader
/*     */ {
/*     */   static final String colorVectorString = "a_Color";
/*  57 */   private String vertexShader = "uniform mat4 u_MVPMatrix;        \nuniform mat4 u_projection; \nuniform mat4 u_modelView; \nattribute vec4 a_Position; \nattribute vec4 a_Color; \nvarying vec4 v_Color;          \nvoid main()                    \n{                              \n   v_Color = a_Color; \n   vec4 p = u_modelView * a_Position; \n    gl_Position = u_projection \n                     * p;              \n}                              \n";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int configureShader() {
/*  80 */     int vertexShaderHandle = GLES20.glCreateShader(35633);
/*  81 */     String vertexShaderErrorLog = "";
/*     */     
/*  83 */     if (vertexShaderHandle != 0) {
/*     */       
/*  85 */       GLES20.glShaderSource(vertexShaderHandle, this.vertexShader);
/*     */ 
/*     */       
/*  88 */       GLES20.glCompileShader(vertexShaderHandle);
/*     */ 
/*     */       
/*  91 */       int[] compileStatus = new int[1];
/*  92 */       GLES20.glGetShaderiv(vertexShaderHandle, 35713, compileStatus, 0);
/*     */ 
/*     */       
/*  95 */       if (compileStatus[0] == 0) {
/*  96 */         vertexShaderErrorLog = GLES20.glGetShaderInfoLog(vertexShaderHandle);
/*  97 */         GLES20.glDeleteShader(vertexShaderHandle);
/*  98 */         vertexShaderHandle = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     if (vertexShaderHandle == 0) {
/* 103 */       throw new RuntimeException("Error creating vertex shader.\n" + vertexShaderErrorLog);
/*     */     }
/*     */     
/* 106 */     return vertexShaderHandle;
/*     */   }
/*     */   
/*     */   public void setShaderSource(String vertexShaderSource) {
/* 110 */     this.vertexShader = vertexShaderSource;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\rendering\shader_impl\SimpleVertexShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */