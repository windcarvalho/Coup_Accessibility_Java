package com.example.coup_acessivel_jv;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import tardigrade.Tardigrade;
import tardigrade.deck.ICard;
import tardigrade.resources.impl.Deck;

public class Utilitarios extends AppCompatActivity {

    static final String TAG = "Utilitários";
    private Tardigrade game;
    private Deck deck = null;

    Toast toast;
    LayoutInflater inflater;

    //NFC e TextToSpeech
    private NfcAdapter mNfcAdapter;

    /*Variável do Arquivo gerado do SharedPreferences*/
    private static final String ARQUIVO_CARTAS = "ArquivoCartas";

    private ICard card;

    /*Usados para recuperar nome e descrição das cartas, através do CSV*/
    private String nameCard;
    private String descriptionCard;

    /*Usados para recuperar os dados do SharedPreferences*/
    private String[] mDuque      = new String[3];
    private String[] mAssassino  = new String[3];
    private String[] mCondessa   = new String[3];
    private String[] mCapitao    = new String[3];
    private String[] mEmbaixador = new String[3];
    private String[] mInquisidor = new String[3];

    CharSequence[] values = {String.valueOf(R.string.assassino), String.valueOf(R.string.capitao), String.valueOf(R.string.condessa), String.valueOf(R.string.duque), String.valueOf(R.string.embaixador)};

    int[] mContador = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void fullScreen(Context context){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
