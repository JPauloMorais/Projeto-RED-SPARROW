uniform mat4 u_MVPMatrix;

void main()
{
    gl_Position = u_MVPMatrix * vec4(0.0, 0.0, 0.0, 1.0);
    gl_PointSize = 5.0;
}