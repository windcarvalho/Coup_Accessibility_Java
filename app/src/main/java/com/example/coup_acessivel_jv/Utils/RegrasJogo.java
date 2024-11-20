package com.example.coup_acessivel_jv.Utils;

//import static com.blankj.utilcode.util.ActivityUtils.startActivity;



//import static androidx.core.content.ContextCompat.startActivity;

//import static android.support.v4.content.ContextCompat.startActivity;
import static androidx.core.content.ContextCompat.startActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
//import android.support.v7.app.AppCompatActivity;

public class RegrasJogo {

    static final String TAG = "Regras do Jogo";

    public String mRegras(){
        String regras = "Cada jogador começa com duas moedas e duas cartas.\n" +
                "\n\nAÇÕES LIVRES!\n\n" +
                "\n\nRenda:\n Permite Pegar uma moeda do Tesouro Central.\n" +
                "\n\nAjuda Externa:\n Pegue duas moedas do Tesouro Central. (Esta ação pode ser bloqueada pelo Duque)\n" +
                "\n\nGolpe de Estado:\n Pague sete moedas para o Tesouro Central e aplique um Golpe contra outro jogador!! forçando-o a perder uma carta. (Se você possuir dez moedas, você deve realizar esta ação)\n" +
                "\n\n\n\nSe você disser que tem um determinado personagem, e algum oponente lhe contestar, e você estiver mentindo, você perde uma carta, virando um de seus personagens para cima.\n" +
                "\n\n\nPersonagens virados para cima não podem ser usados!! se os seus dois personagens forem virados para cima, você está fora do jogo.\n" +
                "\n\nSe você tiver o personagem que disse ter você deve revelá-lo, e o seu oponente perde uma carta, após isso descarte o seu personagem revelado no baralho central e pegue uma nova carta\n" +
                "\nO último jogador a possuir uma carta ainda virada para baixo — vence o jogo!";
        return regras;
    }

    NfcAdapter mNfcAdapter;
    public void verificaNfc(Context context){
        //Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(context);

        if (mNfcAdapter == null){
            //Toast.makeText(this, "O dispositivo não possui NFC!", Toast.LENGTH_SHORT).show();
        //    finish();
            return;
        }

        if((!mNfcAdapter.isEnabled())){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                startActivity(context.getApplicationContext(), intent, null);
            } else {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(context.getApplicationContext(), intent, null);
            }
            Log.i(TAG,"Pede para ativar o NFC!");
        }

        else
            Log.i(TAG,"O NFC já está ativado!");
    }

}
