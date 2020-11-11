/*     */ package org.artoolkitx.arx.arxj.assets;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.pm.PackageManager;
/*     */ import android.content.res.AssetManager;
/*     */ import android.util.Log;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssetHelper
/*     */ {
/*     */   private static final String TAG = "AssetHelper";
/*     */   private final AssetManager manager;
/*     */   
/*     */   public AssetHelper(AssetManager am) {
/*  72 */     this.manager = am;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<AssetFileTransfer> copyAssetFolder(String assetBasePath, String targetDirPath) {
/*  78 */     Set<String> filenames = getAssetFilenames(assetBasePath);
/*  79 */     List<AssetFileTransfer> transfers = new ArrayList<>();
/*     */     
/*  81 */     for (String f : filenames) {
/*  82 */       AssetFileTransfer aft = new AssetFileTransfer();
/*  83 */       transfers.add(aft);
/*     */       try {
/*  85 */         aft.copyAssetToTargetDir(this.manager, f, targetDirPath);
/*  86 */       } catch (AssetFileTransferException afte) {
/*  87 */         afte.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     return transfers;
/*     */   }
/*     */   
/*     */   private void deleteRecursive(File fileOrDirectory) {
/*  95 */     if (fileOrDirectory.isDirectory())
/*  96 */       for (File child : fileOrDirectory.listFiles()) {
/*  97 */         deleteRecursive(child);
/*     */       } 
/*  99 */     fileOrDirectory.delete();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cacheAssetFolder(Context ctx, String assetBasePath) {
/*     */     int versionCode;
/* 108 */     boolean reCache = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 115 */       versionCode = (ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0)).versionCode;
/* 116 */     } catch (android.content.pm.PackageManager.NameNotFoundException nnfe) {
/* 117 */       nnfe.printStackTrace();
/*     */       return;
/*     */     } 
/* 120 */     File cacheFolder = new File(ctx.getCacheDir().getAbsolutePath() + "/" + assetBasePath);
/* 121 */     File cacheIndexFile = new File(cacheFolder, "cacheIndex-" + versionCode + ".txt");
/*     */     
/* 123 */     BufferedReader inBuf = null;
/*     */     try {
/* 125 */       inBuf = new BufferedReader(new FileReader(cacheIndexFile));
/* 126 */       if (inBuf != null) {
/*     */         String line;
/*     */ 
/*     */         
/* 130 */         while ((line = inBuf.readLine()) != null) {
/* 131 */           File cachedFile = new File(line);
/* 132 */           if (!cachedFile.exists()) {
/* 133 */             Log.i("AssetHelper", "cacheAssetFolder(): Cache for folder '" + assetBasePath + "' incomplete. Re-caching.");
/* 134 */             reCache = true;
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 139 */     } catch (FileNotFoundException fnfe) {
/*     */       
/* 141 */       Log.i("AssetHelper", "cacheAssetFolder(): Cache index not found for folder '" + assetBasePath + "'. Re-caching.");
/* 142 */       reCache = true;
/* 143 */     } catch (IOException ioe) {
/* 144 */       ioe.printStackTrace();
/*     */       return;
/*     */     } finally {
/* 147 */       if (inBuf != null) {
/*     */         try {
/* 149 */           inBuf.close();
/* 150 */           inBuf = null;
/* 151 */         } catch (IOException ioe) {
/* 152 */           ioe.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 158 */     if (reCache) {
/* 159 */       deleteRecursive(cacheFolder);
/* 160 */       List<AssetFileTransfer> transfers = copyAssetFolder(assetBasePath, ctx.getCacheDir().getAbsolutePath());
/*     */ 
/*     */       
/* 163 */       BufferedWriter outBuf = null;
/*     */       try {
/* 165 */         outBuf = new BufferedWriter(new FileWriter(cacheIndexFile));
/* 166 */         for (AssetFileTransfer aft : transfers) {
/* 167 */           outBuf.write(aft.targetFile.getAbsolutePath());
/* 168 */           outBuf.newLine();
/*     */         } 
/* 170 */       } catch (FileNotFoundException fnfe) {
/* 171 */         fnfe.printStackTrace();
/* 172 */       } catch (IOException ioe) {
/* 173 */         ioe.printStackTrace();
/*     */       } finally {
/* 175 */         if (outBuf != null) {
/*     */           try {
/* 177 */             outBuf.flush();
/* 178 */             outBuf.close();
/* 179 */             outBuf = null;
/* 180 */           } catch (IOException ioe) {
/* 181 */             ioe.printStackTrace();
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } else {
/* 186 */       Log.i("AssetHelper", "cacheAssetFolder(): Using cached folder '" + assetBasePath + "'.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getAssetFilenames(String path) {
/* 192 */     Set<String> files = new HashSet<>();
/* 193 */     getAssetFilenames(path, files);
/* 194 */     return files;
/*     */   }
/*     */ 
/*     */   
/*     */   private void getAssetFilenames(String path, Set<String> files) {
/*     */     try {
/* 200 */       String[] filenames = this.manager.list(path);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 206 */       if (filenames.length == 0) {
/* 207 */         files.add(path);
/* 208 */         Log.i("AssetHelper", "getAssetFilenames(): Found asset '" + path + "'");
/*     */       } else {
/* 210 */         for (String f : filenames) {
/*     */           
/* 212 */           File file = new File(path, f);
/* 213 */           String fileName = file.getPath();
/* 214 */           getAssetFilenames(fileName, files);
/*     */         } 
/*     */       } 
/* 217 */     } catch (IOException ioe) {
/* 218 */       ioe.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\KYSHIYONG\arxjUnity.jar!\org\artoolkitx\arx\arxj\assets\AssetHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */