package com.example.maihuy.leaf_color.framework.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by maihuy on 4/27/2015.
 */
public class Vertices {
    final GLGraphics glGraphics;
    final boolean hasColor;
    final boolean hasTexCoords;
    final int vertexSize;
    final FloatBuffer vertices;
    final ShortBuffer indices;

    public Vertices(GLGraphics glGraphics, int maxVertices, int maxIndicas, boolean hasColor, boolean hasTexCoords) {
        this.glGraphics = glGraphics;
        this.hasColor = hasColor;
        this.hasTexCoords = hasTexCoords;
        this.vertexSize = (2+(hasColor?4:0) +(hasTexCoords?2:0))*4;
        ByteBuffer buffer= ByteBuffer.allocateDirect(maxVertices * vertexSize);
        buffer.order(ByteOrder.nativeOrder());
        vertices=buffer.asFloatBuffer();

        if (maxIndicas>0){
            buffer= ByteBuffer.allocateDirect(maxIndicas * Short.SIZE / 8);
            buffer.order(ByteOrder.nativeOrder());
            indices=buffer.asShortBuffer();
        }else {
            indices=null;
        }
    }

    public void setVertices(float[] vertices, int offset, int length){
        this.vertices.clear();
        this.vertices.put(vertices,offset,length);
        this.vertices.flip();
    }

    public void setIndices(short[] indices, int offset, int length){
        this.indices.clear();
        this.indices.put(indices, offset, length);
        this.indices.flip();
    }

    /*public void/* draw(int primitiveType, int offset, int numVertices){
        GL10 gl=glGraphics.getGl();

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        vertices.position(0);
        gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize, vertices);

        if (hasColor){
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            vertices.position(2);
            gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
        }

        if (hasTexCoords){
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            vertices.position(hasColor?6:2);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
        }

        if (indices!=null){
            indices.position(offset);
            gl.glDrawElements(primitiveType, numVertices, GL10.GL_UNSIGNED_SHORT, indices);
        }else {
            gl.glDrawArrays(primitiveType, offset, numVertices);
        }

        if (hasTexCoords)
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        if (hasColor)
                gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }*/

    public void bind(){
        GL10 gl=glGraphics.getGl();

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        vertices.position(0);
        gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize, vertices);

        if (hasColor){
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            vertices.position(2);
            gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
        }
        if (hasTexCoords){
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            vertices.position(hasColor?6:2);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
        }
    }

    public void draw(int primitiveType, int offset, int numVertices){
            GL10 gl=glGraphics.getGl();

        if (indices!=null){
            indices.position(offset);
            gl.glDrawElements(primitiveType, numVertices, GL10.GL_UNSIGNED_SHORT, indices);
        }else {
            gl.glDrawArrays(primitiveType, offset, numVertices);
        }
    }

    public void unbind(){
        GL10 gl=glGraphics.getGl();
        if (hasTexCoords) gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        if (hasColor) gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }

}