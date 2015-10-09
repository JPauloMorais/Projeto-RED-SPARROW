package br.jp.redsparrow_zsdemo.engine.shaders;

import android.content.Context;
import android.opengl.GLES20;

import br.jp.redsparrow_zsdemo.R;

/**
 * Created by JoaoPaulo on 16/04/2015.
 */
public class DiffuseColorShaderProg extends ShaderProg {

    protected static final String U_MVPMATRIX = "u_MVPMatrix";
    protected static final String U_MVMATRIX = "u_MVMatrix";
    protected static final String U_LIGHT_POSITION = "u_LightPos";
    protected static final String U_AMBIENT_LIGHT = "u_AmbientLight";
    protected static final String A_NORMAL = "a_Normal";

    private final int U_MVPMATRIX_LOCATION;
    private final int U_MVMATRIX_LOCATION;
    private final int U_LIGHT_POSITION_LOCATION;
    private final int U_AMBIENT_LIGHT_LOCATION;

    private final int A_POSITION_LOCATION;
    private final int A_COLOR_LOCATION;
    private final int A_NORMAL_LOCATION;

    public DiffuseColorShaderProg(Context context) {
        super(context, R.raw.diffuse_color_vertex,
                R.raw.diffuse_color_fragment);

        U_MVPMATRIX_LOCATION = GLES20.glGetUniformLocation(program, U_MVPMATRIX);
        U_MVMATRIX_LOCATION = GLES20.glGetUniformLocation(program, U_MVMATRIX);
        U_LIGHT_POSITION_LOCATION = GLES20.glGetUniformLocation(program, U_LIGHT_POSITION);
        U_AMBIENT_LIGHT_LOCATION = GLES20.glGetUniformLocation(program, U_AMBIENT_LIGHT);

        A_POSITION_LOCATION = GLES20.glGetAttribLocation(program, A_POSITION);
        A_COLOR_LOCATION = GLES20.glGetAttribLocation(program, A_COLOR);
        A_NORMAL_LOCATION = GLES20.glGetAttribLocation(program, A_NORMAL);

    }

    public void setUniforms(float[] MVPMatrix, float[] MVMatrix, float ambientLight) {

        GLES20.glUniformMatrix4fv(U_MVPMATRIX_LOCATION, 1, false, MVPMatrix, 0);
        GLES20.glUniformMatrix4fv(U_MVMATRIX_LOCATION, 1, false, MVMatrix, 0);
        GLES20.glUniform1f(U_AMBIENT_LIGHT_LOCATION, ambientLight);

    }

    public int getU_MVPMATRIX_LOCATION() {
        return U_MVPMATRIX_LOCATION;
    }

    public int getU_MVMATRIX_LOCATION() {
        return U_MVMATRIX_LOCATION;
    }

    public int getU_LIGHT_POSITION_LOCATION() {
        return U_LIGHT_POSITION_LOCATION;
    }

    public int getU_AMBIENT_LIGHT_LOCATION() {
        return U_AMBIENT_LIGHT_LOCATION;
    }

    public int getA_POSITION_LOCATION() {
        return A_POSITION_LOCATION;
    }

    public int getA_COLOR_LOCATION() {
        return A_COLOR_LOCATION;
    }

    public int getA_NORMAL_LOCATION() {
        return A_NORMAL_LOCATION;
    }
}
