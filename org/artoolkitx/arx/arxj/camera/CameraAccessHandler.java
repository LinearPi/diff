package org.artoolkitx.arx.arxj.camera;

public interface CameraAccessHandler {
  void resetCameraAccessPermissionsFromUser();
  
  boolean getCameraAccessPermissions();
  
  void closeCamera();
  
  CameraSurface getCameraSurfaceView();
}


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\camera\CameraAccessHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */