package br.jp.redsparrow.engine.core;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import br.jp.redsparrow.engine.core.game.Game;
import br.jp.redsparrow.engine.core.game.GameSystem;

/**
 * Created by JoaoPaulo on 07/05/2015.
 */
public class SpaceBackground extends GameSystem{

    private FloatBuffer starPositionsBuffer;

    public SpaceBackground(Game game, int starNumber){
        super(game);

        Random r = new Random();
        float[] starPos = new float[starNumber * 3];
        int i = 0;
        for (int j = 0; j < starNumber; j++) {
            starPos[i++] = r.nextFloat() * r.nextInt(100);
            starPos[i++] = r.nextFloat() * r.nextInt(100);
            starPos[i++] = r.nextFloat() * r.nextInt(100);
        }

        starPositionsBuffer = ByteBuffer
                .allocateDirect(starPos.length * Consts.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(starPos);
        starPositionsBuffer.position(0);

    }

    @Override
    public void loop(Game game, float[] projectionMatrix) {

    }
}
