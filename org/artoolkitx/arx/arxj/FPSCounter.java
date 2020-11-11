/*     */ package org.artoolkitx.arx.arxj;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FPSCounter
/*     */ {
/*     */   private int frameCount;
/*     */   private long periodStart;
/*     */   private float currentFPS;
/*     */   
/*     */   public FPSCounter() {
/*  63 */     reset();
/*     */   }
/*     */   
/*     */   public void reset() {
/*  67 */     this.frameCount = 0;
/*  68 */     this.periodStart = 0L;
/*  69 */     this.currentFPS = 0.0F;
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
/*     */   public boolean frame() {
/*  82 */     this.frameCount++;
/*     */     
/*  84 */     long time = System.currentTimeMillis();
/*  85 */     if (this.periodStart <= 0L) this.periodStart = time;
/*     */     
/*  87 */     long elapsed = time - this.periodStart;
/*     */     
/*  89 */     if (elapsed >= 1000L) {
/*     */       
/*  91 */       this.currentFPS = (1000 * this.frameCount) / (float)elapsed;
/*  92 */       this.frameCount = 0;
/*     */       
/*  94 */       this.periodStart = time;
/*     */       
/*  96 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFPS() {
/* 110 */     return this.currentFPS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 120 */     return "FPS: " + this.currentFPS;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\FPSCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */