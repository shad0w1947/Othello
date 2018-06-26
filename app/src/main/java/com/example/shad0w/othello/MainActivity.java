package com.example.shad0w.othello;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout root;
    obutton board[][];
    int Rsize = 10;
    int Csize = 10;
    int black = -1;
    int white = 1;
    int blank = 0;
    int score=0;
    int whiteScore=0;
    int BlackScore=0;
    TextView turn;
    TextView WhiteView;
    TextView BlackView;
    String currentPlayer="White";
    int intialPlayer = white;
    boolean click =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        turn=findViewById(R.id.playerturn);
        turn.setText(currentPlayer+" Move");
        WhiteView=findViewById(R.id.white);
        BlackView=findViewById(R.id.black);
        root = findViewById(R.id.root);
        setupboard();

    }

    int[] index_x = {-1, -1, -1, 0, 1, 1, 1, 0};
    int[] index_y = {-1, 0, 1, 1, 1, 0, -1, -1};

    private void setupboard() {
        currentPlayer="White";
        intialPlayer=white;
        WhiteView.setText("WhiteScore\n"+whiteScore);
        BlackView.setText("BlackScore\n"+BlackScore);
        turn.setText(currentPlayer+" Move");
        if (root != null)
            root.removeAllViews();
        board = new obutton[Rsize][Csize];
        //ans = new int[Rsize][Csize];
        for (int i = 0; i < Rsize; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            linearLayout.setLayoutParams(layoutParams);
            root.addView(linearLayout);
            for (int j = 0; j < Csize; j++) {
                obutton button = new obutton(this);
                ViewGroup.LayoutParams layoutbutton = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                button.setLayoutParams(layoutbutton);
                button.setOnClickListener(MainActivity.this);
                button.r = i;
                button.c = j;
                if ((i == 4 && j == 4) || (j == 5 && i == 5))
                    button.setValue(black);
                else if ((i == 4 && j == 5) || (j == 4 && i == 5))
                    button.setValue(white);
                else
                    button.setValue(blank);
                //  button.setBackgroundDrawable(getResources().getDrawable(R.drawable.blank, null));
                linearLayout.addView(button);
                board[i][j] = button;
                Log.i("value", "this" + button.r + " " + button.c);
                // board[i][j].setvalues(5);
                //Toast.makeText(this, "game over", Toast.LENGTH_LONG).show();
            }
        }
        traverse(0, 0, intialPlayer);

    }

    private void traverse(int x, int y, int player) {
        click=false;
        for (int i = 0; i < Rsize; i++) {
            for (int j = 0; j < Csize; j++) {
                board[i][j].click = false;
                board[i][j].setValue(board[i][j].getValue());
                if(board[i][j].getValue()==-1)
                    BlackScore++;
                if(board[i][j].getValue()==1)
                    whiteScore++;
                if(board[i][j].getValue()!=0)
                    continue;
                for (int k = 0; k < index_x.length; k++) {
                    int x1 = i + index_x[k];
                    int y1 = j + index_y[k];
                    if (x1 < 0 || y1 < 0 || x1 >= Csize || y1 >= Rsize||board[x1][y1].getValue() == 0)
                        continue;
                    if (player + board[x1][y1].getValue() == 0) {
                        if (direction(x1, y1, index_x[k], index_y[k], player)) {
                            board[i][j].click = true;
                            board[i][j].setValue(0);
                            score++;
                            break;
                        }

                    }
                }
            }
        }
    }

    private boolean direction(int i, int j, int x1, int y1, int player) {
        if (i < 0 || j < 0 || i >= Csize || j >= Rsize)
            return false;
        if (board[i][j].getValue() == 0)
            return false;
        if (board[i][j].getValue() == player)
            return true;
        boolean check=direction(i + x1, j + y1, x1, y1, player);
        if(check&&click){
            board[i][j].setValue(player);
           // score++;
        }

        return check;

    }

    public void flipvalue(int i, int j, int player) {
        click=true;
        for (int k = 0; k < index_x.length; k++) {
            int x1 = i + index_x[k];
            int y1 = j + index_y[k];
            if (x1 < 0 || y1 < 0 || x1 >= Csize || y1 >= Rsize || board[x1][y1].getValue() == 0)
                continue;
            if (player + board[x1][y1].getValue() == 0) {
                if (direction(x1, y1, index_x[k], index_y[k], player)) {
                    board[x1][y1].setValue(player);
                   // score++;
                }

            }
        }
      board[i][j].setValue(player);
       // score++;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reset_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        whiteScore=2;
        BlackScore=2;
        setupboard();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        obutton button = (obutton) view;
        if(button.getValue()!=0||!button.click)
            return;
        score=0;
        flipvalue(button.r, button.c, intialPlayer);
        if (intialPlayer == black){
            intialPlayer = white;
            currentPlayer="white";
           // BlackScore+=score;
            //whiteScore-=score+1;
        }

        else if (intialPlayer == white){
            intialPlayer = black;
            //whiteScore+=score;
            //BlackScore-=score+1;
            currentPlayer="Black";
        }
        whiteScore=0;
        score=0;
        BlackScore=0;
        traverse(0,0,intialPlayer);
        WhiteView.setText("WhiteScore\n"+whiteScore);
        BlackView.setText("BlackScore\n"+BlackScore);
        turn.setText(currentPlayer+" Move");
        if(score==0)
        {
            if(whiteScore>BlackScore)
                Toast.makeText(this,"White Wins",Toast.LENGTH_SHORT).show();
          else if(whiteScore<BlackScore)
                Toast.makeText(this,"Black Wins",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"Draw",Toast.LENGTH_SHORT).show();
        }

    }
}
