uniform mat4 g_WorldViewProjectionMatrix;
uniform mat4 g_WorldMatrix;

attribute vec3 inPosition;

in vec2 inTexCoord;
out vec2 texCoord;

void main() {
    texCoord = inTexCoord;

    // Calculate real position
    gl_Position = g_WorldViewProjectionMatrix * vec4(inPosition, 1.0); // Position for fragment shader
}
