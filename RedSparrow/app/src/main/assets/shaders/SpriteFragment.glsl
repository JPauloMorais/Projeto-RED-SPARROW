precision mediump float;

uniform vec3 u_LightPos;
uniform sampler2D u_Texture;

varying vec3 v_Position;
varying vec4 v_Color;
varying vec3 v_Normal;
varying vec2 v_TexCoordinate;

void main()
{
    vec3 lightVector = u_LightPos - v_Position;
    float distance = length(lightVector);
    lightVector = normalize(lightVector);
    float diffuse = max(dot(v_Normal, lightVector), 0.0);
    diffuse = diffuse * (1.0 / (1.0 + (0.10 * distance)));
//    if(u_LightPos.z < v_Position.z) diffuse = 0.0;
    diffuse = diffuse + 0.05f;
    vec4 texel = texture2D(u_Texture, v_TexCoordinate);
//    float r = texel.r * diffuse;
//    if(v_Color.a > 0.0)
//    {
//        r = r + v_Color.r * v_Color.a * texel.a;
//        g = g + v_Color.g * v_Color.a * texel.a;
//        r = r + v_Color. * v_Color.a * texel.a;
//    }
    vec3 finalColor = ((v_Color.rgb * v_Color.a) * texel.rgb) * diffuse * texel.a;
    gl_FragColor = vec4(finalColor.r, finalColor.g, finalColor.b, texel.a);
}