/*     */ package org.artoolkitx.arx.arxj.rendering.shader_impl;
/*     */ 
/*     */ import android.opengl.GLES20;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import org.artoolkitx.arx.arxj.rendering.OpenGLShader;
/*     */ import org.artoolkitx.arx.arxj.rendering.ShaderProgram;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleShaderProgram
/*     */   extends ShaderProgram
/*     */ {
/*     */   public SimpleShaderProgram(OpenGLShader vertexShader, OpenGLShader fragmentShader) {
/*  58 */     super(vertexShader, fragmentShader);
/*  59 */     bindAttributes();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void bindAttributes() {
/*  64 */     GLES20.glBindAttribLocation(this.shaderProgramHandle, 0, "a_Position");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getProjectionMatrixHandle() {
/*  69 */     return GLES20.glGetUniformLocation(this.shaderProgramHandle, "u_projection");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getModelViewMatrixHandle() {
/*  74 */     return GLES20.glGetUniformLocation(this.shaderProgramHandle, "u_modelView");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPositionHandle() {
/*  79 */     return GLES20.glGetAttribLocation(this.shaderProgramHandle, "a_Position");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getColorHandle() {
/*  87 */     return GLES20.glGetAttribLocation(this.shaderProgramHandle, "a_Color");
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
/*     */   public void render(FloatBuffer vertexBuffer, FloatBuffer colorBuffer, ByteBuffer indexBuffer) {
/* 101 */     setupShaderUsage();
/*     */     
/* 103 */     vertexBuffer.position(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     GLES20.glVertexAttribPointer(getPositionHandle(), 3, 5126, false, 12, vertexBuffer);
/*     */     
/* 121 */     GLES20.glEnableVertexAttribArray(getPositionHandle());
/*     */     
/* 123 */     if (colorBuffer != null) {
/*     */       
/* 125 */       colorBuffer.position(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 131 */       GLES20.glVertexAttribPointer(getColorHandle(), 4, 5126, false, 16, colorBuffer);
/*     */ 
/*     */       
/* 134 */       GLES20.glEnableVertexAttribArray(getColorHandle());
/*     */     } 
/*     */     
/* 137 */     if (indexBuffer != null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 142 */       GLES20.glDrawElements(4, indexBuffer.limit(), 5121, indexBuffer);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\rendering\shader_impl\SimpleShaderProgram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */