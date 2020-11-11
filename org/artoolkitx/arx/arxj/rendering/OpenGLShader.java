package org.artoolkitx.arx.arxj.rendering;

public interface OpenGLShader {
  public static final String projectionMatrixString = "u_projection";
  
  public static final String modelViewMatrixString = "u_modelView";
  
  public static final String positionVectorString = "a_Position";
  
  int configureShader();
  
  void setShaderSource(String paramString);
}


/* Location:              C:\Users\ms\Desktop\新建文件夹\120\arxjUnity.jar!\org\artoolkitx\arx\arxj\rendering\OpenGLShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */