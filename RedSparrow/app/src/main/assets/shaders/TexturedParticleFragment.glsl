precision mediump float;

uniform sampler2D u_Texture;

void main()
{
    gl_FragColor = texture2D(u_Texture, gl_PointCoord);
}