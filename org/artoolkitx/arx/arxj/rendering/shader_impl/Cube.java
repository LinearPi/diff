/*     */ package org.artoolkitx.arx.arxj.rendering.shader_impl;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import org.artoolkitx.arx.arxj.rendering.ARDrawable;
/*     */ import org.artoolkitx.arx.arxj.rendering.RenderUtils;
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
/*     */ public final class Cube
/*     */   implements ARDrawable
/*     */ {
/*     */   private FloatBuffer mVertexBuffer;
/*     */   private FloatBuffer mColorBuffer;
/*     */   private ByteBuffer mIndexBuffer;
/*     */   private ShaderProgram shaderProgram;
/*     */   
/*     */   public Cube() {
/*  58 */     this(1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public Cube(ShaderProgram shaderProgram) {
/*  63 */     this.shaderProgram = shaderProgram;
/*     */   }
/*     */ 
/*     */   
/*     */   public Cube(float size) {
/*  68 */     this(size, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public Cube(float size, float x, float y, float z) {
/*  72 */     setArrays(size, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public FloatBuffer getmVertexBuffer() {
/*  77 */     return this.mVertexBuffer;
/*     */   }
/*     */   
/*     */   public FloatBuffer getmColorBuffer() {
/*  81 */     return this.mColorBuffer;
/*     */   }
/*     */   
/*     */   public ByteBuffer getmIndexBuffer() {
/*  85 */     return this.mIndexBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setArrays(float size, float x, float y, float z) {
/*  90 */     float hs = size / 2.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     float[] vertices = { x - hs, y - hs, z - hs, x + hs, y - hs, z - hs, x + hs, y + hs, z - hs, x - hs, y + hs, z - hs, x - hs, y - hs, z + hs, x + hs, y - hs, z + hs, x + hs, y + hs, z + hs, x - hs, y + hs, z + hs };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     float c = 1.0F;
/* 108 */     float[] colors = { 0.0F, 0.0F, 0.0F, c, c, 0.0F, 0.0F, c, c, c, 0.0F, c, 0.0F, c, 0.0F, c, 0.0F, 0.0F, c, c, c, 0.0F, c, c, c, c, c, c, 0.0F, c, c, c };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     byte[] indices = { 1, 0, 2, 2, 0, 3, 1, 2, 5, 5, 2, 6, 4, 5, 7, 7, 5, 6, 0, 4, 3, 3, 4, 7, 7, 6, 3, 6, 2, 3, 0, 1, 4, 4, 1, 5 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     this.mVertexBuffer = RenderUtils.buildFloatBuffer(vertices);
/* 141 */     this.mColorBuffer = RenderUtils.buildFloatBuffer(colors);
/* 142 */     this.mIndexBuffer = RenderUtils.buildByteBuffer(indices);
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
/*     */   public void draw(float[] projectionMatrix, float[] modelViewMatrix) {
/* 156 */     this.shaderProgram.setProjectionMatrix(projectionMatrix);
/* 157 */     this.shaderProgram.setModelViewMatrix(modelViewMatrix);
/*     */     
/* 159 */     this.shaderProgram.render(getmVertexBuffer(), getmColorBuffer(), getmIndexBuffer());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShaderProgram(ShaderProgram shaderProgram) {
/* 168 */     this.shaderProgram = shaderProgram;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\rendering\shader_impl\Cube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */