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
/*     */ import android.util.Log;
/*     */ import android.view.Menu;
/*     */ import android.view.MenuInflater;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.FrameLayout;
/*     */ import android.widget.ImageButton;
/*     */ import android.widget.Toast;
/*     */ import androidx.annotation.NonNull;
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
/* 120 */     return this.mContext;
/*     */   }
/*     */ 
/*     */   
/*     */   @TargetApi(14)
/*     */   protected void onCreate(Bundle savedInstanceState) {
/* 126 */     super.onCreate(savedInstanceState);
/*     */     
/* 128 */     this.mContext = getApplicationContext();
/*     */ 
/*     */ 
/*     */     
/* 132 */     PreferenceManager.setDefaultValues((Context)this, R.xml.preferences, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     setRequestedOrientation(0);
/* 139 */     getWindow().addFlags(128);
/*     */     
/* 141 */     AndroidUtils.reportDisplayInformation(this);
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
/* 161 */     super.onStart();
/*     */     
/* 163 */     Log.i("ARXJ::ARActivity", "onStart(): called");
/*     */ 
/*     */     
/* 166 */     if (!ARController.getInstance().initialiseNative(getCacheDir().getAbsolutePath())) {
/* 167 */       notifyFinish("The native ARX library could not be loaded.");
/*     */       
/*     */       return;
/*     */     } 
/* 171 */     this.mainLayout = supplyFrameLayout();
/* 172 */     if (this.mainLayout == null) {
/* 173 */       Log.e("ARXJ::ARActivity", "onStart(): Error: supplyFrameLayout did not return a layout.");
/*     */       
/*     */       return;
/*     */     } 
/* 177 */     this.renderer = supplyRenderer();
/* 178 */     if (this.renderer == null) {
/* 179 */       Log.e("ARXJ::ARActivity", "onStart(): Error: supplyRenderer did not return a renderer.");
/* 180 */       notifyFinish("You need to supply a renderer. Create your own renderer class (MyArRenderer) and derive it from ARRenderer.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResume() {
/* 186 */     Log.i("ARXJ::ARActivity", "onResume(): called");
/* 187 */     super.onResume();
/*     */ 
/*     */     
/* 190 */     this.mGlView = new GLSurfaceView((Context)this);
/*     */     
/* 192 */     FrameListenerImpl frameListenerImpl = new FrameListenerImpl(this.renderer, this, this.mGlView);
/* 193 */     CameraEventListenerImpl cameraEventListenerImpl = new CameraEventListenerImpl(this, (FrameListener)frameListenerImpl);
/* 194 */     this.mCameraAccessHandler = AndroidUtils.createCameraAccessHandler(this, (CameraEventListener)cameraEventListenerImpl);
/*     */ 
/*     */     
/* 197 */     this.mGlView.setEGLContextClientVersion(2);
/*     */     
/* 199 */     if (this.renderer != null) {
/* 200 */       this.mGlView.setRenderer((GLSurfaceView.Renderer)this.renderer);
/*     */     }
/*     */     
/* 203 */     this.mGlView.setRenderMode(0);
/* 204 */     this.mGlView.addOnLayoutChangeListener(new LayoutChangeListenerImpl(this, this.mCameraAccessHandler));
/*     */     
/* 206 */     Log.i("ARXJ::ARActivity", "onResume(): GLSurfaceView created");
/*     */ 
/*     */     
/* 209 */     this.mainLayout.addView((View)this.mGlView, new ViewGroup.LayoutParams(-1, -1));
/* 210 */     Log.i("ARXJ::ARActivity", "onResume(): Views added to main layout.");
/* 211 */     this.mGlView.onResume();
/*     */     
/* 213 */     if (this.mCameraAccessHandler.getCameraAccessPermissions()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 219 */     View settingsButtonLayout = getLayoutInflater().inflate(R.layout.settings, (ViewGroup)this.mainLayout, false);
/* 220 */     this.mConfigButton = (ImageButton)settingsButtonLayout.findViewById(R.id.button_config);
/* 221 */     this.mainLayout.addView(settingsButtonLayout);
/* 222 */     this.mConfigButton.setOnClickListener(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPause() {
/* 227 */     Log.i("ARXJ::ARActivity", "onPause(): called");
/*     */     
/* 229 */     this.mCameraAccessHandler.closeCamera();
/*     */     
/* 231 */     if (this.mGlView != null) {
/* 232 */       this.mGlView.onPause();
/* 233 */       this.mainLayout.removeView((View)this.mGlView);
/*     */     } 
/*     */     
/* 236 */     super.onPause();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStop() {
/* 241 */     Log.i("ARXJ::ARActivity", "onStop(): Activity stopping.");
/* 242 */     super.onStop();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCreateOptionsMenu(Menu menu) {
/* 247 */     MenuInflater inflater = getMenuInflater();
/* 248 */     inflater.inflate(R.menu.options, menu);
/* 249 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onOptionsItemSelected(MenuItem item) {
/* 254 */     if (item.getItemId() == R.id.settings) {
/* 255 */       startActivity(new Intent((Context)this, CameraPreferencesActivity.class));
/* 256 */       return true;
/*     */     } 
/* 258 */     return super.onOptionsItemSelected(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void showInfo() {
/* 265 */     AlertDialog.Builder dialogBuilder = new AlertDialog.Builder((Context)this);
/*     */     
/* 267 */     dialogBuilder.setMessage("artoolkitX v" + ARX_jni.arwGetARToolKitVersion());
/*     */     
/* 269 */     dialogBuilder.setCancelable(false);
/* 270 */     dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
/*     */           public void onClick(DialogInterface dialog, int id) {
/* 272 */             dialog.cancel();
/*     */           }
/*     */         });
/*     */     
/* 276 */     AlertDialog alert = dialogBuilder.create();
/* 277 */     alert.setTitle("artoolkitX");
/* 278 */     alert.show();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onWindowFocusChanged(boolean hasFocus) {
/* 283 */     super.onWindowFocusChanged(hasFocus);
/* 284 */     View decorView = getWindow().getDecorView();
/* 285 */     if (hasFocus)
/*     */     {
/* 287 */       decorView.setSystemUiVisibility(5894);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClick(View v) {
/* 293 */     if (v.equals(this.mConfigButton)) {
/* 294 */       v.getContext().startActivity(new Intent(v.getContext(), CameraPreferencesActivity.class));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
/* 302 */     Log.i("ARXJ::ARActivity", "onRequestPermissionsResult(): called");
/*     */     
/* 304 */     if (requestCode == 0) {
/* 305 */       if (grantResults[0] != 0) {
/* 306 */         notifyFinish("Application will not run with camera access denied");
/* 307 */       } else if (1 <= permissions.length) {
/* 308 */         Toast.makeText(getApplicationContext(), 
/* 309 */             String.format("Camera access permission \"%s\" allowed", new Object[] { permissions[0] }), 0)
/* 310 */           .show();
/*     */       } 
/* 312 */       Log.i("ARXJ::ARActivity", "onRequestPermissionsResult(): reset ask for cam access perm");
/* 313 */       this.mCameraAccessHandler.resetCameraAccessPermissionsFromUser();
/*     */     } else {
/* 315 */       super.onRequestPermissionsResult(requestCode, permissions, grantResults);
/*     */     } 
/* 317 */     onStart();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GLSurfaceView getGLView() {
/* 327 */     return this.mGlView;
/*     */   }
/*     */ 
/*     */   
/*     */   public ARRenderer getRenderer() {
/* 332 */     return this.renderer;
/*     */   }
/*     */   
/*     */   private void notifyFinish(String errorMessage) {
/* 336 */     (new AlertDialog.Builder((Context)this))
/* 337 */       .setMessage(errorMessage)
/* 338 */       .setTitle("Error")
/* 339 */       .setCancelable(true)
/* 340 */       .setNeutralButton(17039370, new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int whichButton) {
/* 343 */             ARActivity.this.finish();
/*     */           }
/* 346 */         }).show();
/*     */   }
/*     */ }


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\ARActivity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */