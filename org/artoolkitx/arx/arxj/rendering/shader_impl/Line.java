/*     */ package org.artoolkitx.arx.arxj.rendering.shader_impl;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import javax.microedition.khronos.opengles.GL10;
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
/*     */ public class Line
/*     */   implements ARDrawable
/*     */ {
/*  52 */   private final int vertexLength = 3;
/*  53 */   private float[] start = new float[3];
/*  54 */   private float[] end = new float[3];
/*     */   private float width;
/*  56 */   private float[] color = new float[] { 1.0F, 0.0F, 0.0F, 1.0F };
/*     */ 
/*     */   
/*     */   private FloatBuffer mVertexBuffer;
/*     */ 
/*     */   
/*     */   private FloatBuffer mColorBuffer;
/*     */   
/*     */   private ShaderProgram shaderProgram;
/*     */ 
/*     */   
/*     */   public Line(float width) {
/*  68 */     this.shaderProgram = null;
/*  69 */     setWidth(width);
/*     */   }
/*     */   
/*     */   public Line(float width, ShaderProgram shaderProgram) {
/*  73 */     this(width);
/*  74 */     this.shaderProgram = shaderProgram;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setArrays() {
/*  79 */     float[] vertices = new float[6];
/*     */     
/*  81 */     for (int i = 0; i < 3; i++) {
/*  82 */       vertices[i] = this.start[i];
/*  83 */       vertices[i + 3] = this.end[i];
/*     */     } 
/*     */     
/*  86 */     this.mVertexBuffer = RenderUtils.buildFloatBuffer(vertices);
/*  87 */     this.mColorBuffer = RenderUtils.buildFloatBuffer(this.color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(GL10 gl) {
/*  92 */     gl.glVertexPointer(3, 5126, 0, this.mVertexBuffer);
/*     */     
/*  94 */     gl.glEnableClientState(32884);
/*  95 */     gl.glColor4f(1.0F, 0.0F, 0.0F, 1.0F);
/*  96 */     gl.glLineWidth(this.width);
/*  97 */     gl.glDrawArrays(1, 0, 2);
/*  98 */     gl.glDisableClientState(32884);
/*     */   }
/*     */   
/*     */   public float getWidth() {
/* 102 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWidth(float width) {
/* 107 */     this.width = width;
/*     */   }
/*     */ 
/*     */   
/*     */   public FloatBuffer getMVertexBuffer() {
/* 112 */     return this.mVertexBuffer;
/*     */   }
/*     */   
/*     */   public float[] getStart() {
/* 116 */     return this.start;
/*     */   }
/*     */   
/*     */   public void setStart(float[] start) {
/* 120 */     if (start.length > 3) {
/* 121 */       this.start[0] = start[0];
/* 122 */       this.start[1] = start[1];
/* 123 */       this.start[2] = start[2];
/*     */     } else {
/* 125 */       this.start = start;
/*     */     } 
/*     */   }
/*     */   
/*     */   public float[] getEnd() {
/* 130 */     return this.end;
/*     */   }
/*     */   
/*     */   public void setEnd(float[] end) {
/* 134 */     if (end.length > 3) {
/* 135 */       this.end[0] = end[0];
/* 136 */       this.end[1] = end[1];
/* 137 */       this.end[2] = end[2];
/*     */     } else {
/* 139 */       this.end = end;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getColor() {
/* 145 */     return this.color;
/*     */   }
/*     */   
/*     */   public void setColor(float[] color) {
/* 149 */     this.color = color;
/*     */   }
/*     */ 
/*     */   
/*     */   public FloatBuffer getColorBuffer() {
/* 154 */     return this.mColorBuffer;
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
/*     */   public void draw(float[] projectionMatrix, float[] modelViewMatrix) {
/* 166 */     this.shaderProgram.setProjectionMatrix(projectionMatrix);
/* 167 */     this.shaderProgram.setModelViewMatrix(modelViewMatrix);
/*     */     
/* 169 */     setArrays();
/* 170 */     this.shaderProgram.render(getMVertexBuffer(), getColorBuffer(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShaderProgram(ShaderProgram program) {
/* 179 */     this.shaderProgram = program;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\rendering\shader_impl\Line.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */