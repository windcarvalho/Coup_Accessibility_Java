package com.example.coup_acessivel_jv.Utils;


import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
//import static com.blankj.utilcode.util.ActivityUtils.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.coup_acessivel_jv.R;
import tardigrade.Tardigrade;
import tardigrade.deck.ICard;
import tardigrade.resources.impl.Deck;

public class Utils {

    private final static String TAG = "Utils ";
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

    CharSequence[] values = {" Duque ", " Assassino ", " Condessa ",
            " Capitão ", " Embaixador "};

    int[] mContador = new int[5];

    Activity act;
    
    /**/
    public void mInstancias(final Activity context){
        act = new Activity();
        game = Tardigrade.getInstance(context);
        deck = Deck.getInstance(context);
        toast = new Toast(context);
        inflater = act.getLayoutInflater();
    }
    

    /*Confirma se o usuário realmente deseja apagar todos os dados*/
    public void alertDeletaDados(final Context context){
        new MaterialDialog.Builder(context)
                .title("TELA DE CONFIRMAÇÃO!\n\nVocê realmente deseja apagar todas as suas cartas?")
                .positiveText("SIM")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        Toast.makeText(context, "Foi clicado", Toast.LENGTH_SHORT).show();
                        //limpaTodosDadosSharedPreferences();
                    }
                })
                .negativeText("NÃO")
                .show();
    }

    /*Cadastrar Cartas*/
    public void alertDialogCadastrar(final String id,final Context context){
        new MaterialDialog.Builder(context)
                .title("Tela de Cadastro!\n\nSelecione a carta que deseja atribuir a TAG!")
                .items(values)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int myItem, CharSequence text) {

                        switch (myItem) {
                            case 0: {
                                switch (mContador[0]) {
                                    case 0: {
                                        Log.i(TAG, "Duque 1");
                                        salvarDados("Duque","duque1", id,context);
                                        mContador[0]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Duque 2");
                                        salvarDados("Duque","duque2", id,context);
                                        mContador[0]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Duque 3");
                                        salvarDados("Duque","duque3", id,context);
                                        mContador[0]++;
                                        break;
                                    }
                                    default: mContador[0] = 0;
                                }
                                break;
                            }

                            case 1:{
                                switch (mContador[1]) {
                                    case 0: {
                                        Log.i(TAG, "Assassino 1");
                                        salvarDados("Assassino","assassino1", id,context);
                                        mContador[1]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Assassino 2");
                                        salvarDados("Assassino","assassino2", id,context);
                                        mContador[1]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Assassino 3");
                                        salvarDados("Assassino","assassino3", id,context);
                                        mContador[1]++;
                                        break;
                                    }
                                    default: mContador[1] = 0;
                                }
                                break;
                            }

                            case 2:{//Condessa
                                switch (mContador[2]) {
                                    case 0: {
                                        Log.i(TAG, "Condessa 1");
                                        salvarDados("Condessa","condessa1", id,context);
                                        mContador[2]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Condessa 2");
                                        salvarDados("Condessa","condessa2", id,context);
                                        mContador[2]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Condessa 3");
                                        salvarDados("Condessa","condessa3", id,context);
                                        mContador[2]++;
                                        break;
                                    }
                                    default: mContador[2] = 0;
                                }
                                break;
                            }

                            case 3:{//Capitão
                                switch (mContador[3]) {
                                    case 0: {
                                        Log.i(TAG, "Capitão 1");
                                        salvarDados("Capitão","capitao1", id,context);
                                        mContador[3]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Capitão 2");
                                        salvarDados("Capitão","capitao2", id,context);
                                        mContador[3]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Capitão 3");
                                        salvarDados("Capitão","capitao3", id,context);
                                        mContador[3]++;
                                        break;
                                    }
                                    default: mContador[3] = 0;
                                }
                                break;
                            }

                            case 4:{//Embaixador
                                switch (mContador[4]) {
                                    case 0: {
                                        Log.i(TAG, "Embaixador 1");
                                        salvarDados("Embaixador","embaixador1", id,context);
                                        mContador[4]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Embaixador 2");
                                        salvarDados("Embaixador","embaixador2", id,context);
                                        mContador[4]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Embaixador 3");
                                        salvarDados("Embaixador","embaixador3", id,context);
                                        mContador[4]++;
                                        break;
                                    }
                                    default: mContador[4] = 0;
                                }
                                break;
                            }
                        }

                        return true;
                    }
                })
                //.positiveText("Cadastrar")
                .show();
    }


    /*Mostra Mensagens Toast*/
    public void mToasts(final String message, final Context context){
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) act.findViewById(R.id.custom_toast_container));
        TextView myText = layout.findViewById(R.id.text);
        myText.setText(message);
        toast.setView(layout);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER,0,100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    /*Recupera as informações do SharedPreferences para as Variáveis*/
    public void recuperaDadosParaVariaveis(final Context context) {
        mDuque[0] = recuperarDados("duque1",context);
        mDuque[1] = recuperarDados("duque2",context);
        mDuque[2] = recuperarDados("duque3",context);

        mAssassino[0] = recuperarDados("assassino1",context);
        mAssassino[1] = recuperarDados("assassino2",context);
        mAssassino[2] = recuperarDados("assassino3",context);

        mCondessa[0] = recuperarDados("condessa1",context);
        mCondessa[1] = recuperarDados("condessa2",context);
        mCondessa[2] = recuperarDados("condessa3",context);

        mCapitao[0] = recuperarDados("capitao1",context);
        mCapitao[1] = recuperarDados("capitao2",context);
        mCapitao[2] = recuperarDados("capitao3",context);

        mEmbaixador[0] = recuperarDados("embaixador1",context);
        mEmbaixador[1] = recuperarDados("embaixador2",context);
        mEmbaixador[2] = recuperarDados("embaixador3",context);
    }

    /*Recupera o nome da carta do CSV*/
    public void recuperaNomeCartaCSV(String idCsv, final Context context){
        card = deck.getCard(idCsv);
        nameCard = card.getName();
        mToasts(nameCard,context);
        Log.i(TAG,"A carta lida foi: "+nameCard);
       // startActivity(new Intent(context, com.example.coup_acessivel_jv.ReadCard.class));
    }

    /*Nome das Cartas*/
    public void readNameCard(String id, final Context context){

        recuperaDadosParaVariaveis(context);

        if (mDuque[0].equals(id) || mDuque[1].equals(id) || mDuque[2].equals(id)){
            Log.i(TAG,"mDuque: "+id);
            recuperaNomeCartaCSV("1",context);
            salvarDadosUltimaCartaLida(id,context);
        }

        else if (mAssassino[0].equals(id) || mAssassino[1].equals(id) || mAssassino[2].equals(id)){
            Log.i(TAG,"mAssassino: "+id);
            recuperaNomeCartaCSV("2",context);
            salvarDadosUltimaCartaLida(id,context);
        }

        else if (mCondessa[0].equals(id) || mCondessa[1].equals(id) || mCondessa[2].equals(id)){
            Log.i(TAG,"mCondessa: "+id);
            recuperaNomeCartaCSV("3",context);
            salvarDadosUltimaCartaLida(id,context);
        }


        else if (mCapitao[0].equals(id) || mCapitao[1].equals(id) || mCapitao[2].equals(id)){
            Log.i(TAG,"mCapitao: "+id);
            recuperaNomeCartaCSV("4",context);
            salvarDadosUltimaCartaLida(id,context);
        }


        else if (mEmbaixador[0].equals(id) || mEmbaixador[1].equals(id) || mEmbaixador[2].equals(id)){
            Log.i(TAG,"mEmbaixador: "+id);
            recuperaNomeCartaCSV("5",context);
            salvarDadosUltimaCartaLida(id,context);
        }

        else{
            alertDialogCadastrar(id,context);
        }

    }

    /************************************** SHARED PREFERENCES *********************************************/
    public void salvarDados(String nome,String nomeCarta, String tagCarta,final Context context){

        limpaDadoCartaEspecifica(tagCarta,context);

        SharedPreferences preferences = act.getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(nomeCarta, tagCarta);
        editor.commit();
        mToasts("Carta "+nome+" cadastrada com sucesso!",context);
        Log.i(TAG,"Nome Carta: "+nomeCarta+"\tTAG ID: "+tagCarta);
    }

    public void salvarDadosUltimaCartaLida(String idUltimaCarta,final Context context){

        limpaDadosUltimaCartaLida(idUltimaCarta,context);

        SharedPreferences preferences = act.getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("idUltimaCarta", idUltimaCarta);
        editor.commit();
        Log.i(TAG,"id da última carta SALVA foi: "+idUltimaCarta);
    }

    public String recuperarDados(String nameCard,final Context context){
        SharedPreferences preferences = act.getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        String cardID = preferences.getString(nameCard,"Carta não cadastrada!");
        return cardID;
    }

    public void limpaDadoCartaEspecifica(String removerCarta,final Context context){
        SharedPreferences preferences = act.getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(removerCarta);
        editor.commit();
    }

    public void limpaDadosUltimaCartaLida(String idUltimaCarta,final Context context){
        SharedPreferences preferences = act.getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(idUltimaCarta);
        editor.commit();
    }

    public void limpaTodosDadosSharedPreferences(final Context context){
        SharedPreferences preferences = act.getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        mToasts("Dados apagados com sucesso!!!",context);
        //startActivity(new Intent(this, ReadCard.class)); //modificar depois
    }


    /******************************************** TARDIGRADE ***********************************************/
    //@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0){
            if (resultCode == RESULT_OK){
                String result = intent.getStringExtra("SCAN_RESULT");
                ICard card = deck.getCard(result);
                card.execute();
            }
        }
    }


}
