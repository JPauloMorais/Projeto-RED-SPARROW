package br.jp.redsparrow_zsdemo.engine;

/**
 * Created by JoaoPaulo on 02/04/2015.
 */
public class Vector3f {

    public float x;
    public float y;
    public float z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f copy(){
        return new Vector3f(x, y, z);
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3f v) {
        this.x = v.x;
        this.x = v.x;
        this.z = v.z;
    }

    public float dot(Vector3f v){
        return x * v.x + y * v.y + z * v.z;
    }

    public float length(){
        return (float) Math.sqrt( (x*x) + (y*y) + (z*z));
    }

    public void normalize (){
        this.div(length());
    }

    public void setLength (float length) {
        normalize();
        mult(length);
    }

    public void add(Vector3f v){
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public void add(float n){
        x += n;
        y += n;
        z += n;
    }

    public void sub(Vector3f v){
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public void sub(float n){
        x -= n;
        y -= n;
        z -= n;
    }

    public void mult(Vector3f v){
        x *= v.x;
        y *= v.y;
        z *= v.z;
    }

    public void mult(float n){
        x *= n;
        y *= n;
        z *= n;
    }

    public void div(Vector3f v){
        x /= v.x;
        y /= v.y;
        z /= v.z;
    }

    public void div(float n){
        x /= n;
        y /= n;
        z /= n;
    }

    public String toString(){
        return "(" + x + "," + y + "," + z + ")";
    }


}
