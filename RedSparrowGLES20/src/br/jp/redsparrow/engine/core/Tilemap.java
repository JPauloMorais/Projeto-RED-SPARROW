package br.jp.redsparrow.engine.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import br.jp.redsparrow.engine.core.util.TextFileReader;

import br.jp.redsparrow.R;

public class Tilemap implements Renderable {

	private Tile[][] mTiles;

	public Tilemap(final Context context, final int src){
		new Runnable() {
			public void run() {
				try {
					JSONObject jObj = new JSONObject(TextFileReader.readTextFromFile(context, src));
					JSONArray jArr = jObj.getJSONArray("layers");

					int widthInTiles = jArr.getInt(6);
					int heightInTiles = jArr.getInt(1);

					Tile auxTiles[][] = new Tile[widthInTiles][heightInTiles];
					int dataIndex = 0;
					int textureSrc = 0;
					//percorre o array de dados da camada
					for (int i = 0; i < widthInTiles; i++) {
						for (int j = 0; j < heightInTiles; j++) {
							//determina ql img do tileset usar com base no index do tileset
							switch (jArr.getJSONArray(0).getInt(dataIndex)) {
							case 0:

								textureSrc = R.drawable.tileset_test;

								break;

							case 1:

								break;

							default:
								break;
							}
							auxTiles[i][j] = new Tile(context, textureSrc);
							dataIndex++;
						}
					}
					mTiles = auxTiles;

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.run();
	}

	@Override
	public void render(VertexArray vertexArray, float[] projectionMatrix) {
		//TODO: render baseado em posicao do player
	}

}
