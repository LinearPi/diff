/*    */ package org.artoolkitx.arx.arxj.rendering;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
/*    */ import java.nio.FloatBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import java.nio.ShortBuffer;
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
/*    */ public class RenderUtils
/*    */ {
/*    */   public static FloatBuffer buildFloatBuffer(float[] arr) {
/* 50 */     ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
/* 51 */     bb.order(ByteOrder.nativeOrder());
/* 52 */     FloatBuffer fb = bb.asFloatBuffer();
/* 53 */     fb.put(arr);
/* 54 */     fb.position(0);
/* 55 */     return fb;
/*    */   }
/*    */   
/*    */   public static IntBuffer buildIntBuffer(int[] arr) {
/* 59 */     ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
/* 60 */     bb.order(ByteOrder.nativeOrder());
/* 61 */     IntBuffer ib = bb.asIntBuffer();
/* 62 */     ib.put(arr);
/* 63 */     ib.position(0);
/* 64 */     return ib;
/*    */   }
/*    */   
/*    */   public static ShortBuffer buildShortBuffer(short[] arr) {
/* 68 */     ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 2);
/* 69 */     bb.order(ByteOrder.nativeOrder());
/* 70 */     ShortBuffer sb = bb.asShortBuffer();
/* 71 */     sb.put(arr);
/* 72 */     sb.position(0);
/* 73 */     return sb;
/*    */   }
/*    */   
/*    */   public static ByteBuffer buildByteBuffer(byte[] arr) {
/* 77 */     ByteBuffer bb = ByteBuffer.allocateDirect(arr.length);
/* 78 */     bb.put(arr);
/* 79 */     bb.position(0);
/* 80 */     return bb;
/*    */   }
/*    */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\rendering\RenderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */