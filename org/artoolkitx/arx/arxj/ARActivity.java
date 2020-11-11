/*     */ package org.artoolkitx.arx.arxj;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.app.Activity;
/*     */ import android.app.AlertDialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.content.Intent;
/*     */ import android.opengl.GLSurfaceView;
/*     */ import android.os.Bundle;
/*     */ import android.preference.PreferenceManager;
/*     */ import android.support.annotation.NonNull;
/*     */ import android.util.Log;
/*     */ import android.view.Menu;
/*     */ import android.view.MenuInflater;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.FrameLayout;
/*     */ import android.widget.ImageButton;
/*     */ import android.widget.Toast;
/*     */ import com.ksxkq.mylibrary.R;
/*     */ import org.artoolkitx.arx.arxj.camera.CameraAccessHandler;
/*     */ import org.artoolkitx.arx.arxj.camera.CameraEventListener;
/*     */ import org.artoolkitx.arx.arxj.camera.CameraEventListenerImpl;
/*     */ import org.artoolkitx.arx.arxj.camera.CameraPreferencesActivity;
/*     */ import org.artoolkitx.arx.arxj.camera.FrameListener;
/*     */ import org.artoolkitx.arx.arxj.camera.FrameListenerImpl;
/*     */ import org.artoolkitx.arx.arxj.rendering.ARRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ARActivity
/*     */   extends Activity
/*     */   implements View.OnClickListener
/*     */ {
/*     */   public static final int REQUEST_CAMERA_PERMISSION_RESULT = 0;
/*     */   private static final String TAG = "ARXJ::ARActivity";
/*     */   private ARRenderer renderer;
/*     */   private FrameLayout mainLayout;
/*     */   private Context mContext;
/*     */   private CameraAccessHandler mCameraAccessHandler;
/*     */   private ImageButton mConfigButton;
/*     */   private GLSurfaceView mGlView;
/*     */   
/*     */   public Context getAppContext() {
/* 118 */     return this.mContext;
/*     */   }
/*     */ 
/*     */   
/*     */   @TargetApi(14)
/*     */   protected void onCreate(Bundle savedInstanceState) {
/* 124 */     super.onCreate(savedInstanceState);
/*     */     
/* 126 */     this.mContext = getApplicationContext();
/*     */ 
/*     */ 
/*     */     
/* 130 */     PreferenceManager.setDefaultValues((Context)this, R.xml.preferences, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     setRequestedOrientation(0);
/* 137 */     getWindow().addFlags(128);
/*     */     
/* 139 */     AndroidUtils.reportDisplayInformation(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ARRenderer supplyRenderer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract FrameLayout supplyFrameLayout();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onStart() {
/* 159 */     super.onStart();
/*     */     
/* 161 */     Log.i("ARXJ::ARActivity", "onStart(): called");
/*     */ 
/*     */     
/* 164 */     if (!ARController.getInstance().initialiseNative(getCacheDir().getAbsolutePath())) {
/* 165 */       notifyFinish("The native ARX library could not be loaded.");
/*     */       
/*     */       return;
/*     */     } 
/* 169 */     this.mainLayout = supplyFrameLayout();
/* 170 */     if (this.mainLayout == null) {
/* 171 */       Log.e("ARXJ::ARActivity", "onStart(): Error: supplyFrameLayout did not return a layout.");
/*     */       
/*     */       return;
/*     */     } 
/* 175 */     this.renderer = supplyRenderer();
/* 176 */     if (this.renderer == null) {
/* 177 */       Log.e("ARXJ::ARActivity", "onStart(): Error: supplyRenderer did not return a renderer.");
/* 178 */       notifyFinish("You need to supply a renderer. Create your own renderer class (MyArRenderer) and derive it from ARRenderer.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResume() {
/* 184 */     Log.i("ARXJ::ARActivity", "onResume(): called");
/* 185 */     super.onResume();
/*     */ 
/*     */     
/* 188 */     this.mGlView = new GLSurfaceView((Context)this);
/*     */     
/* 190 */     FrameListenerImpl frameListenerImpl = new FrameListenerImpl(this.renderer, this, this.mGlView);
/* 191 */     CameraEventListenerImpl cameraEventListenerImpl = new CameraEventListenerImpl(this, (FrameListener)frameListenerImpl);
/* 192 */     this.mCameraAccessHandler = AndroidUtils.createCameraAccessHandler(this, (CameraEventListener)cameraEventListenerImpl);
/*     */ 
/*     */     
/* 195 */     this.mGlView.setEGLContextClientVersion(2);
/*     */     
/* 197 */     if (this.renderer != null) {
/* 198 */       this.mGlView.setRenderer((GLSurfaceView.Renderer)this.renderer);
/*     */     }
/*     */     
/* 201 */     this.mGlView.setRenderMode(0);
/* 202 */     this.mGlView.addOnLayoutChangeListener(new LayoutChangeListenerImpl(this, this.mCameraAccessHandler));
/*     */     
/* 204 */     Log.i("ARXJ::ARActivity", "onResume(): GLSurfaceView created");
/*     */ 
/*     */     
/* 207 */     this.mainLayout.addView((View)this.mGlView, new ViewGroup.LayoutParams(-1, -1));
/* 208 */     Log.i("ARXJ::ARActivity", "onResume(): Views added to main layout.");
/* 209 */     this.mGlView.onResume();
/*     */     
/* 211 */     if (this.mCameraAccessHandler.getCameraAccessPermissions()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 217 */     View settingsButtonLayout = getLayoutInflater().inflate(R.layout.settings, (ViewGroup)this.mainLayout, false);
/* 218 */     this.mConfigButton = (ImageButton)settingsButtonLayout.findViewById(R.id.button_config);
/* 219 */     this.mainLayout.addView(settingsButtonLayout);
/* 220 */     this.mConfigButton.setOnClickListener(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPause() {
/* 225 */     Log.i("ARXJ::ARActivity", "onPause(): called");
/*     */     
/* 227 */     this.mCameraAccessHandler.closeCamera();
/*     */     
/* 229 */     if (this.mGlView != null) {
/* 230 */       this.mGlView.onPause();
/* 231 */       this.mainLayout.removeView((View)this.mGlView);
/*     */     } 
/*     */     
/* 234 */     super.onPause();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStop() {
/* 239 */     Log.i("ARXJ::ARActivity", "onStop(): Activity stopping.");
/* 240 */     super.onStop();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCreateOptionsMenu(Menu menu) {
/* 245 */     MenuInflater inflater = getMenuInflater();
/* 246 */     inflater.inflate(R.menu.options, menu);
/* 247 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onOptionsItemSelected(MenuItem item) {
/* 252 */     if (item.getItemId() == R.id.settings) {
/* 253 */       startActivity(new Intent((Context)this, CameraPreferencesActivity.class));
/* 254 */       return true;
/*     */     } 
/* 256 */     return super.onOptionsItemSelected(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void showInfo() {
/* 263 */     AlertDialog.Builder dialogBuilder = new AlertDialog.Builder((Context)this);
/*     */     
/* 265 */     dialogBuilder.setMessage("artoolkitX v" + ARX_jni.arwGetARToolKitVersion());
/*     */     
/* 267 */     dialogBuilder.setCancelable(false);
/* 268 */     dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
/*     */           public void onClick(DialogInterface dialog, int id) {
/* 270 */             dialog.cancel();
/*     */           }
/*     */         });
/*     */     
/* 274 */     AlertDialog alert = dialogBuilder.create();
/* 275 */     alert.setTitle("artoolkitX");
/* 276 */     alert.show();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onWindowFocusChanged(boolean hasFocus) {
/* 281 */     super.onWindowFocusChanged(hasFocus);
/* 282 */     View decorView = getWindow().getDecorView();
/* 283 */     if (hasFocus)
/*     */     {
/* 285 */       decorView.setSystemUiVisibility(5894);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClick(View v) {
/* 291 */     if (v.equals(this.mConfigButton)) {
/* 292 */       v.getContext().startActivity(new Intent(v.getContext(), CameraPreferencesActivity.class));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
/* 300 */     Log.i("ARXJ::ARActivity", "onRequestPermissionsResult(): called");
/*     */     
/* 302 */     if (requestCode == 0) {
/* 303 */       if (grantResults[0] != 0) {
/* 304 */         notifyFinish("Application will not run with camera access denied");
/* 305 */       } else if (1 <= permissions.length) {
/* 306 */         Toast.makeText(getApplicationContext(), 
/* 307 */             String.format("Camera access permission \"%s\" allowed", new Object[] { permissions[0] }), 0)
/* 308 */           .show();
/*     */       } 
/* 310 */       Log.i("ARXJ::ARActivity", "onRequestPermissionsResult(): reset ask for cam access perm");
/* 311 */       this.mCameraAccessHandler.resetCameraAccessPermissionsFromUser();
/*     */     } else {
/* 313 */       super.onRequestPermissionsResult(requestCode, permissions, grantResults);
/*     */     } 
/* 315 */     onStart();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GLSurfaceView getGLView() {
/* 325 */     return this.mGlView;
/*     */   }
/*     */ 
/*     */   
/*     */   public ARRenderer getRenderer() {
/* 330 */     return this.renderer;
/*     */   }
/*     */   
/*     */   private void notifyFinish(String errorMessage) {
/* 334 */     (new AlertDialog.Builder((Context)this))
/* 335 */       .setMessage(errorMessage)
/* 336 */       .setTitle("Error")
/* 337 */       .setCancelable(true)
/* 338 */       .setNeutralButton(17039370, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int whichButton) {
/* 341 */             ARActivity.this.finish();
/*     */           }
/* 344 */         }).show();
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\KYSHIYONG\arxjUnity.jar!\org\artoolkitx\arx\arxj\ARActivity.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */