package org.artoolkitx.arx.arxj.camera;

public interface CameraSurface {
  void surfaceCreated();
  
  void surfaceChanged();
  
  void closeCameraDevice();
  
  boolean isCamera2DeviceOpen();
  
  boolean isImageReaderCreated();
}


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\camera\CameraSurface.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */