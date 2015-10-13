uniform mat4 u_MVPMatrix;

attribute vec2 a_Position;

void main()
{
    gl_Position = u_MVPMatrix * vec4(a_Position.xy, 0.0, 1.0);
    gl_PointSize = 10.0;
}