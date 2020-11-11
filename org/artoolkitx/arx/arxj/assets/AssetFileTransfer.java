/*     */ package org.artoolkitx.arx.arxj.assets;
/*     */ 
/*     */ import android.content.res.AssetManager;
/*     */ import android.os.Environment;
/*     */ import android.util.Log;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AssetFileTransfer
/*     */ {
/*     */   private static final String TAG = "AssetFileTransfer";
/*     */   private File assetFile;
/*     */   private boolean assetAvailable;
/*     */   public File targetFile;
/*     */   private File targetDirectory;
/*     */   private boolean targetFileAlreadyExists;
/*     */   private long targetFileCRC;
/*     */   private File tempFile;
/*     */   private long tempFileCRC;
/*     */   private boolean assetCopied;
/*     */   
/*     */   private void copyContents(InputStream in, OutputStream out) throws IOException {
/*  73 */     int bufferSize = 16384;
/*  74 */     byte[] buffer = new byte[16384];
/*     */     
/*     */     int bytesRead;
/*  77 */     while ((bytesRead = in.read(buffer)) != -1) {
/*  78 */       out.write(buffer, 0, bytesRead);
/*     */     }
/*     */     
/*  81 */     out.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyAssetToTargetDir(AssetManager manager, String assetFilePath, String targetDirPath) throws AssetFileTransferException {
/*     */     InputStream in;
/*  87 */     this.assetFile = new File(assetFilePath);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  93 */       in = manager.open(assetFilePath);
/*  94 */       this.assetAvailable = true;
/*  95 */     } catch (IOException e) {
/*  96 */       this.assetAvailable = false;
/*  97 */       throw new AssetFileTransferException("Unable to open the asset file: " + assetFilePath, e);
/*     */     } 
/*     */     
/* 100 */     this.targetFile = new File(targetDirPath, assetFilePath);
/* 101 */     this.targetFileAlreadyExists = this.targetFile.exists();
/*     */     
/* 103 */     Log.i("AssetFileTransfer", "copyAssetToTargetDir(): [" + assetFilePath + "] -> [" + this.targetFile.getPath() + "]");
/*     */     
/* 105 */     if (this.targetFileAlreadyExists) {
/*     */       OutputStream out;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 111 */         this.tempFile = File.createTempFile("unpacker", null, Environment.getExternalStorageDirectory());
/*     */       }
/* 113 */       catch (IOException ioe) {
/* 114 */         throw new AssetFileTransferException("Error creating temp file: " + this.tempFile.getPath(), ioe);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 119 */         out = new FileOutputStream(this.tempFile);
/* 120 */       } catch (FileNotFoundException fnfe) {
/* 121 */         throw new AssetFileTransferException("Error creating temp file: " + this.tempFile.getPath(), fnfe);
/*     */       } 
/*     */       try {
/* 124 */         copyContents(in, out);
/* 125 */         in.close();
/* 126 */         in = null;
/* 127 */         out.close();
/* 128 */         out = null;
/* 129 */       } catch (IOException ioe) {
/* 130 */         throw new AssetFileTransferException("Error copying asset to temp file: " + this.tempFile.getPath(), ioe);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 137 */         this.tempFileCRC = Hasher.computeCRC(this.tempFile.getPath());
/*     */ 
/*     */         
/* 140 */         this.targetFileCRC = Hasher.computeCRC(this.targetFile.getPath());
/*     */       }
/* 142 */       catch (HashComputationException hce) {
/* 143 */         throw new AssetFileTransferException("Error hashing files", hce);
/*     */       } 
/*     */       
/* 146 */       if (this.tempFileCRC == this.targetFileCRC) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 151 */         this.tempFile.delete();
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 156 */         this.targetFile.delete();
/*     */         
/* 158 */         this.tempFile.renameTo(this.targetFile);
/*     */         
/* 160 */         this.assetCopied = true;
/*     */       } 
/*     */     } else {
/*     */       OutputStream out;
/*     */       
/* 165 */       Log.i("AssetFileTransfer", "copyAssetToTargetDir(): Target file does not exist. Creating directory structure.");
/*     */ 
/*     */       
/* 168 */       this.targetDirectory = this.targetFile.getParentFile();
/* 169 */       this.targetDirectory.mkdirs();
/*     */ 
/*     */       
/*     */       try {
/* 173 */         out = new FileOutputStream(this.targetFile);
/* 174 */       } catch (FileNotFoundException fnfe) {
/* 175 */         throw new AssetFileTransferException("Error creating target file: " + this.targetFile.getPath(), fnfe);
/*     */       } 
/*     */       try {
/* 178 */         copyContents(in, new FileOutputStream(this.targetFile));
/*     */ 
/*     */         
/* 181 */         in.close();
/* 182 */         in = null;
/* 183 */         out.close();
/* 184 */         out = null;
/* 185 */       } catch (IOException ioe) {
/* 186 */         throw new AssetFileTransferException("Error copying asset to target file: " + this.targetFile.getPath(), ioe);
/*     */       } 
/* 188 */       this.assetCopied = true;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\assets\AssetFileTransfer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */