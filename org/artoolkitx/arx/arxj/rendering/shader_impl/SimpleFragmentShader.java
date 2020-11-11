/*    */ package org.artoolkitx.arx.arxj.rendering.shader_impl;
/*    */ 
/*    */ import android.opengl.GLES20;
/*    */ import org.artoolkitx.arx.arxj.rendering.OpenGLShader;
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
/*    */ public class SimpleFragmentShader
/*    */   implements OpenGLShader
/*    */ {
/* 57 */   private String fragmentShader = "precision mediump float;       \nvarying vec4 v_Color;          \nvoid main()                    \n{                              \n   gl_FragColor = v_Color;     \n}                              \n";
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
/*    */   public int configureShader() {
/* 69 */     int fragmentShaderHandle = GLES20.glCreateShader(35632);
/* 70 */     String fragmentShaderErrorLog = "";
/*    */     
/* 72 */     if (fragmentShaderHandle != 0) {
/*    */ 
/*    */       
/* 75 */       GLES20.glShaderSource(fragmentShaderHandle, this.fragmentShader);
/*    */ 
/*    */       
/* 78 */       GLES20.glCompileShader(fragmentShaderHandle);
/*    */ 
/*    */       
/* 81 */       int[] compileStatus = new int[1];
/* 82 */       GLES20.glGetShaderiv(fragmentShaderHandle, 35713, compileStatus, 0);
/*    */ 
/*    */       
/* 85 */       if (compileStatus[0] == 0) {
/* 86 */         fragmentShaderErrorLog = GLES20.glGetShaderInfoLog(fragmentShaderHandle);
/* 87 */         GLES20.glDeleteShader(fragmentShaderHandle);
/* 88 */         fragmentShaderHandle = 0;
/*    */       } 
/*    */     } 
/* 91 */     if (fragmentShaderHandle == 0) {
/* 92 */       throw new RuntimeException("Error creating fragment shader.\\n" + fragmentShaderErrorLog);
/*    */     }
/* 94 */     return fragmentShaderHandle;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setShaderSource(String source) {
/* 99 */     this.fragmentShader = source;
/*    */   }
/*    */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\KYSHIYONG\arxjUnity.jar!\org\artoolkitx\arx\arxj\rendering\shader_impl\SimpleFragmentShader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */