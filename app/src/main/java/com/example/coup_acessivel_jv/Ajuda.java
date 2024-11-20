package com.example.coup_acessivel_jv;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

//import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.MaterialDialog;
//import com.dgsm.accessibilitycoup.R;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import tardigrade.Tardigrade;
import tardigrade.deck.ICard;
import tardigrade.resources.impl.Deck;

public class Ajuda extends AppCompatActivity {

    private Button btAssassino, btCapitao, btCondessa, btDuque, btEmbaixador, btVoltar;

    private static final String TAG = "AjudaActivity";

    private static final String ARQUIVO_CARTAS = "ArquivoCartas";

    /*Usados para recuperar nome e descrição das cartas, através do CSV*/
    /*Usados para recuperar nome e descrição das cartas, através do CSV*/
    private String nameCard;
    private String descriptionCard;
    private String detailsCard;


    /*Tardigrade*/
    private Tardigrade game;
    private Deck deck = null;
    private ICard card;

    int[] mContador = new int[5];
    //NFC e TextToSpeech
    private NfcAdapter mNfcAdapter;


    /*Toast Customizavel*/
    Toast toast;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Coup","Abrindo Ajuda");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ajuda);

        mCasts();

        /*Checa se tem permissão para usar o NFC*/
        checkPermissionNFC();

        //Verifica o NFC
        verificaNFC();

        String titulo = String.valueOf(R.string.tela_ajudas);
        setTitle(Integer.parseInt(titulo));


        btAssassino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperaDescricaoDetalhadaCartaCSV("7");
            }
        });

        btCapitao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperaDescricaoDetalhadaCartaCSV("8");
            }
        });

        btCondessa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperaDescricaoDetalhadaCartaCSV("9");
            }
        });

        btDuque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperaDescricaoDetalhadaCartaCSV("10");
            }
        });

        btEmbaixador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recuperaDescricaoDetalhadaCartaCSV("11");
            }
        });

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Ajuda.this, Menu.class));
            }
        });

    }


    /*Recupera a descrição da carta do CSV*/
    private void recuperaDescricaoDetalhadaCartaCSV(String idCsv) {
        card = deck.getCard(idCsv);
        descriptionCard = card.getDescription();
        mToastsText(descriptionCard);
        Log.i(TAG, "recuperaDescricaoDetalhadaCartaCSV: leu a descrição da carta" + idCsv);
    }

    /*Cadastrar Cartas*/
    private void alertDialogCadastrar(final Tag tagText) {

        mToasts((R.string.mantenha_celular_proximo));

        String[] personagensCartas = {
                getString(R.string.assassino), getString(R.string.capitao),
                getString(R.string.condessa), getString(R.string.duque),
                getString(R.string.embaixador)};

        new MaterialDialog.Builder(this)
                .title((R.string.tela_cadastro_selecione_carta))
                .items(personagensCartas)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int myItem, CharSequence text) {

                        switch (myItem) {
                            case 0: {
                                switch (mContador[0]) {
                                    case 0: {
                                        Log.i(TAG, "Assassino 1");
                                        escreverTag(tagText, "As1");
                                        mContador[0]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Assassino 2");
                                        escreverTag(tagText, "As1");
                                        mContador[0]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Assassino 3");
                                        escreverTag(tagText, "As1");
                                        mContador[0]++;
                                        mToasts((R.string.todas_cartas_assassino));
                                        break;
                                    }
                                    default:
                                        mContador[0] = 0;
                                }
                                break;
                            }

                            case 1: {//Capitão
                                switch (mContador[1]) {
                                    case 0: {
                                        Log.i(TAG, "Capitão 1");
                                        escreverTag(tagText, "Cp1");
                                        mContador[1]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Capitão 2");
                                        escreverTag(tagText, "Cp1");
                                        mContador[1]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Capitão 3");
                                        escreverTag(tagText, "Cp1");
                                        mToasts((R.string.todas_carta_capitao));
                                        mContador[1]++;
                                        break;
                                    }
                                    default:
                                        mContador[1] = 0;
                                }
                                break;
                            }

                            case 2: {//Condessa
                                switch (mContador[2]) {
                                    case 0: {
                                        Log.i(TAG, "Condessa 1");
                                        escreverTag(tagText, "Cd1");
                                        mContador[2]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Condessa 2");
                                        escreverTag(tagText, "Cd1");
                                        mContador[2]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Condessa 3");
                                        escreverTag(tagText, "Cd1");
                                        mContador[2]++;
                                        mToasts((R.string.todas_cartas_condessa));
                                        break;
                                    }
                                    default:
                                        mContador[2] = 0;
                                }
                                break;
                            }

                            case 3: {
                                switch (mContador[3]) {
                                    case 0: {
                                        Log.i(TAG, "Duque 1");
                                        escreverTag(tagText, "Dq1");
                                        mContador[3]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Duque 2");
                                        escreverTag(tagText, "Dq1");
                                        mContador[3]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Duque 3");
                                        escreverTag(tagText, "Dq1");
                                        mContador[3]++;
                                        mToasts((R.string.todas_cartas_duque));
                                        break;
                                    }
                                    default:
                                        mContador[3] = 0;
                                }
                                break;
                            }

                            case 4: {//Embaixador
                                switch (mContador[4]) {
                                    case 0: {
                                        Log.i(TAG, "Embaixador 1");
                                        escreverTag(tagText, "Em1");
                                        mContador[4]++;
                                        break;
                                    }
                                    case 1: {
                                        Log.i(TAG, "Embaixador 2");
                                        escreverTag(tagText, "Em1");
                                        mContador[4]++;
                                        break;
                                    }
                                    case 2: {
                                        Log.i(TAG, "Embaixador 3");
                                        escreverTag(tagText, "Em1");
                                        mContador[4]++;
                                        mToasts((R.string.todas_cartas_embaixador));
                                        break;
                                    }
                                    default:
                                        mContador[4] = 0;
                                }
                                break;
                            }

                        }

                        return true;
                    }
                })
                .show();
    }

    private void escreverTag(Tag tag, String message) {
        NdefMessage ndefMessage = createNdefMessage(message);
        writeNdefMessage(tag, ndefMessage);
        Log.i(TAG, "escreverTag: Escrita na TAG com sucesso!" + tag);
    }

    /*Cast dos Botões*/
    private void mCasts() {

        game = Tardigrade.getInstance(this);
        deck = Deck.getInstance(this);
        toast = new Toast(this);
        inflater = getLayoutInflater();

        btAssassino = findViewById(R.id.btAssassino);
        btCapitao = findViewById(R.id.btCapitao);
        btCondessa = findViewById(R.id.btCondessa);
        btDuque = findViewById(R.id.btDuque);
        btEmbaixador = findViewById(R.id.btEmbaixador);
        btVoltar = findViewById(R.id.btVoltar);
    }

    /*Mostra Mensagens Toast*/
    public void mToasts(final int messageInt) {
        //String message = Integer.parseInt(messageInt);
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView myText = layout.findViewById(R.id.text);
        myText.setText(Integer.parseInt(String.valueOf(messageInt)));
        toast.setView(layout);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /*Mostra Mensagens Toast*/
    public void mToastsText(final String message) {
        //String message = Integer.parseInt(messageInt);
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_container));
        TextView myText = layout.findViewById(R.id.text);
        myText.setText(message);
        toast.setView(layout);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /*Recupera o nome da carta do CSV*/
    private void recuperaNomeCartaCSV(String idCsv) {
        card = deck.getCard(idCsv);
        nameCard = card.getName();
        mToastsText(nameCard);
        Log.i(TAG, "A carta lida foi: " + nameCard);
        startActivity(new Intent(this, ReadCard.class));
    }

    /*Nome das Cartas*/
    public void readNameCard(String tagContent) {

        //recuperaDadosParaVariaveis();

        if (tagContent.equals("As1")) {
            Log.i(TAG, "ReadNameCard: mAssassino: " + tagContent);
            recuperaNomeCartaCSV("1");
        } else if (tagContent.equals("Cp1")) {
            Log.i(TAG, "ReadNameCard: mCapitao: " + tagContent);
            recuperaNomeCartaCSV("2");
        } else if (tagContent.equals("Cd1")) {
            Log.i(TAG, "ReadNameCard: mCondessa: " + tagContent);
            recuperaNomeCartaCSV("3");
        } else if (tagContent.equals("Dq1")) {
            Log.i(TAG, "ReadNameCard: mDuque: " + tagContent);
            recuperaNomeCartaCSV("4");
        } else if (tagContent.equals("Em1")) {
            Log.i(TAG, "ReadNameCard: mEmbaixador: " + tagContent);
            recuperaNomeCartaCSV("5");
        }

        salvarDadosUltimaCartaLida(tagContent);

    }


    /************************************** SHARED PREFERENCES *********************************************/
    private void salvarDadosUltimaCartaLida(String idUltimaCarta) {

        limpaDadosUltimaCartaLida(idUltimaCarta);

        SharedPreferences preferences = getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("idUltimaCarta", idUltimaCarta);
        editor.commit();
        Log.i(TAG, "id da última carta SALVA foi: " + idUltimaCarta);
    }

    private void limpaDadosUltimaCartaLida(String idUltimaCarta) {
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_CARTAS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(idUltimaCarta);
        editor.commit();
    }

    /************************************* MÉTODOS DA TAG NFC ***********************************************/
    private void checkPermissionNFC() {

        Log.i(TAG, "Entrou nas permissões!");

     if (ActivityCompat.checkSelfPermission(this, NFC_SERVICE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.NFC)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.NFC}, 0);
            } else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.NFC}, 0);
        }
    }

    public void verificaNFC() {

        //Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            mToasts((R.string.seu_dispositivo_nao_possui_nfc));
            finish();
            return;
        }

        if ((!mNfcAdapter.isEnabled())) {
            mToasts((R.string.ative_o_nfc));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                startActivity(intent);
            } else {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
            Log.i(TAG, "Pede para ativar o NFC!");
        } else {
            Log.i(TAG, "O NFC já está ativado!");
            mToasts((R.string.aproxime_da_carta));
        }
    }

    /*Lê o ID da TAG*/
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "Carta lida");
        Tag tagText = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        /*Cadastrar texto na Tag*/
        verificaCadastro(intent, tagText);
    }

    public void verificaCadastro(Intent intent, Tag tagText) {

        /*Se tiver algum texto*/
        if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {

            Log.i(TAG, "verificaCadastro: Existe Texto Gravado na Tag");

            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (parcelables != null && parcelables.length > 0) {
                readTextFromMessage((NdefMessage) parcelables[0], tagText);
            } else {
                Log.i(TAG, "verificaCadastro: Nenhum texto encontrado na TAG");
                mToasts((R.string.carta_nao_cadastrada));
                alertDialogCadastrar(tagText);
            }
        }


    }

    private void readTextFromMessage(NdefMessage ndefMessage, Tag tagText) {

        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if (ndefRecords != null && ndefRecords.length > 0) {
            NdefRecord ndefRecord = ndefRecords[0];
            String tagContent = getTextFromNdefRecord(ndefRecord);

            if (tagContent.equals("As1") || tagContent.equals("Cp1") || tagContent.equals("Cd1") || tagContent.equals("Dq1") || tagContent.equals("Em1")) {
                readNameCard(tagContent);
                Log.i(TAG, "readTextFromMessage: Tag já cadastrada - " + tagContent);
            } else {
                alertDialogCadastrar(tagText);
                Log.i(TAG, "readTextFromMessage: Tag ainda não cadastrada ---> " + tagText);
            }
        } else {
            Log.i(TAG, "readTextFromMessage: Nenhuma mensagem encontrada ---> ");
        }

    }

    private void formatTag(Tag tag, NdefMessage ndefMessage) {
        try {
            NdefFormatable ndefFormatable = NdefFormatable.get(tag);

            if (ndefFormatable == null) {
                Toast.makeText(this, "Tag is not formatable", Toast.LENGTH_SHORT).show();
            }

            ndefFormatable.connect();
            ndefFormatable.format(ndefMessage);
            ndefFormatable.close();

        } catch (Exception e) {
            Log.i(TAG, "formatTag -----> Erro: " + e.getMessage());
        }
    }

    private void writeNdefMessage(Tag tag, NdefMessage ndefMessage) {
        try {

            if (tag == null) {
                Log.i(TAG, "writeNdefMessage: Objeto da TAG não pode ser nulo");
                return;
            }

            Ndef ndef = Ndef.get(tag);

            if (ndef == null) {
                formatTag(tag, ndefMessage);
            } else {
                ndef.connect();

                if (!ndef.isWritable()) {
                    Log.i(TAG, "writeNdefMessage: Não é possível gravar nesta TAG, use outra.");
                    mToasts((R.string.tag_protegida_gravacao));
                    ndef.close();
                    return;
                }

                ndef.writeNdefMessage(ndefMessage);
                ndef.close();
                mToasts((R.string.carta_cadastrada_sucesso));


            }

        } catch (Exception e) {
            Log.i(TAG, "writeNdefMessage: Erro" + e.getMessage());
        }
    }


    private NdefRecord createTextRecord(String content) {
        try {
            byte[] language;
            language = Locale.getDefault().getLanguage().getBytes("UTF-8");

            final byte[] text = content.getBytes("UTF-8");
            final int languageSize = language.length;
            final int textLenght = text.length;
            final ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + languageSize + textLenght);

            payload.write((byte) (languageSize & 0x1F));
            payload.write(language, 0, languageSize);
            payload.write(text, 0, textLenght);

            return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload.toByteArray());

        } catch (Exception e) {
            Log.i(TAG, "createTextRecord: " + e.getMessage());
        }

        return null;
    }


    private NdefMessage createNdefMessage(String content) {

        NdefRecord ndefRecord = createTextRecord(content);

        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{ndefRecord});

        return ndefMessage;

    }

    /*Pega o texto gravado na Tag*/
    public String getTextFromNdefRecord(NdefRecord ndefRecord) {

        String tagContent = null;
        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1, payload.length - languageSize - 1, textEncoding);
        } catch (Exception e) {
            Log.e(TAG, "getTextFromNdefRecord: " + e.getMessage());
        }

        return tagContent;
    }

    /*Converte o ID da TAG de Hexadecimal para Decimal*/
    private String ByteArrayToHexString(byte[] inarray) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String out = "";

        for (j = 0; j < inarray.length; ++j) {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    /*Códigos extras da TAG NFC*/
    private void enableForegroundDispatchSystem() {

        Intent intent = new Intent(this, Menu.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ? PendingIntent.FLAG_IMMUTABLE : 0
        );
        IntentFilter[] intentFilters = new IntentFilter[]{};

        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    private void disableForegroundDispatchSystem() {
        mNfcAdapter.disableForegroundDispatch(this);
    }

    /********************************* CICLO DE VIDA DA ACTIVITY ********************************************/
    @Override
    protected void onResume() {
        enableForegroundDispatchSystem();
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        disableForegroundDispatchSystem();
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }


    /******************************************** TARDIGRADE ***********************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String result = intent.getStringExtra("SCAN_RESULT");
                ICard card = deck.getCard(result);
                card.execute();
            }
        }
    }
}
