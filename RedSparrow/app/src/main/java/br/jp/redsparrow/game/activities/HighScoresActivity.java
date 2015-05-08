package br.jp.redsparrow.game.activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.jp.redsparrow.R;

public class HighScoresActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        LinearLayout scoresLayout = (LinearLayout) findViewById(R.id.scoresLayout);

        TextView label = (TextView) findViewById(R.id.highScoresLabel);
        label.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DisposableDroidBB.otf"));

        try {

            String dirName = "RedSparrow";
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

            File dirFile = new File(dir, dirName);
            dirFile.mkdirs();

            String fileName = ".scores";
            File scoresFile = new File(dirFile, fileName);

            if(scoresFile.createNewFile()) {

                FileWriter writer = new FileWriter(scoresFile);
                BufferedWriter buffWriter = new BufferedWriter(writer);

                buffWriter.write("p Zamooni Slayer");
                buffWriter.newLine();
                buffWriter.write("s 10001019");
                buffWriter.newLine();
                buffWriter.write("s 9399323");

                buffWriter.close();
            }
//
//            BufferedReader reader = new BufferedReader(new FileReader(scoresFile));
//            String line = null;
//            String curPlayer = null;
//            Map<String, List<Long>> scores = new HashMap<String, List<Long>>();
//
//            while ((line = reader.readLine())!=null){
//                String[] lineParts = line.split("[ ]+");
//
//                StringBuilder builder;
//                if (lineParts[0].equals("p")) {
//                    builder = new StringBuilder();
//                    for (int i = 1; i < lineParts.length; i++) {
//                        builder.append(lineParts[i]);
//                    }
//                    curPlayer = builder.toString();
//                    scores.put(curPlayer, new ArrayList<Long>());
//                } else if (lineParts[0].equals("s")) {
//                    scores.get(curPlayer).add(Long.parseLong(lineParts[1]));
//                }
//            }
//

            Log.i("HiScoresActivity", scoresFile.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
