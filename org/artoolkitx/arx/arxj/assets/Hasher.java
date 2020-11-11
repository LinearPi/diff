/*     */ package org.artoolkitx.arx.arxj.assets;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.zip.CRC32;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Hasher
/*     */ {
/*     */   private static final String HEX = "0123456789ABCDEF";
/*     */   
/*     */   public static String toHex(byte[] buf) {
/*  59 */     if (buf == null) return "";
/*     */     
/*  61 */     StringBuffer result = new StringBuffer(2 * buf.length);
/*     */     
/*  63 */     for (int i = 0; i < buf.length; i++) {
/*  64 */       result.append("0123456789ABCDEF".charAt(buf[i] >> 4 & 0xF)).append("0123456789ABCDEF".charAt(buf[i] & 0xF));
/*     */     }
/*     */     
/*  67 */     return result.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static long computeCRC(String filename) throws HashComputationException {
/*  72 */     InputStream in = null;
/*  73 */     byte[] buffer = new byte[16384];
/*  74 */     int bytesRead = -1;
/*  75 */     CRC32 crc = new CRC32();
/*     */     
/*     */     try {
/*  78 */       in = new FileInputStream(filename);
/*  79 */     } catch (FileNotFoundException fnfe) {
/*  80 */       throw new HashComputationException("File not found: " + filename, fnfe);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  86 */       for (; (bytesRead = in.read(buffer)) != -1; crc.update(buffer, 0, bytesRead));
/*  87 */       in.close();
/*  88 */       in = null;
/*  89 */     } catch (IOException ioe) {
/*  90 */       throw new HashComputationException("IOException while reading from file", ioe);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     return crc.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String computeHash(String filename) throws HashComputationException, IOException {
/* 105 */     InputStream in = null;
/* 106 */     MessageDigest digest = null;
/* 107 */     String algorithm = "SHA-1";
/* 108 */     byte[] buffer = new byte[16384];
/* 109 */     int bytesRead = -1;
/*     */ 
/*     */     
/*     */     try {
/* 113 */       in = new FileInputStream(filename);
/* 114 */     } catch (FileNotFoundException fnfe) {
/* 115 */       throw new HashComputationException("File not found: " + filename, fnfe);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 120 */       digest = MessageDigest.getInstance(algorithm);
/* 121 */     } catch (NoSuchAlgorithmException nsae) {
/*     */       try {
/* 123 */         in.close();
/* 124 */       } catch (IOException e) {
/* 125 */         throw e;
/*     */       } 
/* 127 */       in = null;
/* 128 */       throw new HashComputationException("No such algorithm: " + algorithm, nsae);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 134 */       for (; (bytesRead = in.read(buffer)) != -1; digest.update(buffer, 0, bytesRead));
/* 135 */       in.close();
/* 136 */       in = null;
/* 137 */     } catch (IOException ioe) {
/* 138 */       throw new HashComputationException("IOException while reading from file", ioe);
/*     */     } 
/*     */     
/* 141 */     byte[] digestResult = digest.digest();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     String hash = toHex(digestResult);
/*     */ 
/*     */     
/* 149 */     return hash;
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\assets\Hasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */