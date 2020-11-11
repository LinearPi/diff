package org.artoolkitx.arx.arxj.camera;

import java.nio.ByteBuffer;

public interface CameraEventListener {
  void cameraStreamStarted(int paramInt1, int paramInt2, String paramString, int paramInt3, boolean paramBoolean);
  
  void cameraStreamFrame(byte[] paramArrayOfbyte, int paramInt);
  
  void cameraStreamFrame(ByteBuffer[] paramArrayOfByteBuffer, int[] paramArrayOfint1, int[] paramArrayOfint2);
  
  void cameraStreamStopped();
}


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\camera\CameraEventListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */