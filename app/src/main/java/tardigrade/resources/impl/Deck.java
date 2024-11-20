package tardigrade.resources.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import tardigrade.Tardigrade;
import tardigrade.comunication.IPack;
import tardigrade.deck.ICard;
import tardigrade.deck.IDeck;
import tardigrade.resources.ReadFile;
import tardigrade.utils.Flag;
import tardigrade.utils.ICallback;

public class Deck extends SQLiteOpenHelper implements IDeck {
    private static int VERSION;

    public static String DATABASE_FILE = "Tardigrade.db";
    private static String TABLE_NAME = "Deck";
    private static String CARD_ID = "id";
    private static String CARD_NAME = "name";
    private static String CARD_DESCRIPTION = "description";

    private static String[] CARD_ATTR = {Card.ID,CARD_NAME,CARD_DESCRIPTION};

    private SQLiteDatabase db;

    private List<ICard> stack;

    private ICallback onLoadDeck = null;
    private ICallback onUseCard = null;

    public static Context mContext;

    private static Deck ourInstance = null;
    public static Deck getInstance(Context context) {
        if(ourInstance == null){
            if(context != null){
                VERSION = (int) (System.currentTimeMillis()/100000);

                ourInstance = new Deck(context);
                mContext = context;

                ourInstance.init();
            }
        }
        return ourInstance;
    }

    private Deck(Context context) {
        super(context, DATABASE_FILE, null, VERSION);
        stack = new ArrayList<>();
        onLoadDeck = new NullCallback();
        onUseCard = new NullCallback();
    }


    /**
     *  Métodos do Banco de dados
     *
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                CARD_ID + " integer primary key," +
                CARD_NAME + " text," +
                CARD_DESCRIPTION + " text";

        if(Tardigrade.CARD_ATTRIBUTES != null) {
            for (int i = 0; i < Tardigrade.CARD_ATTRIBUTES.length; i++) {
                sql += ", " + Tardigrade.CARD_ATTRIBUTES[i] + " text";
            }
        }

        sql += ")";

        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    private boolean addCard(ICard card){
        if(getCard(card.getId()) != null){ return false; }

        ContentValues tuple = new ContentValues();
        db = this.getWritableDatabase();

        tuple.put(CARD_ID, card.getId());
        tuple.put(CARD_NAME, card.getName());
        tuple.put(CARD_DESCRIPTION, card.getDescription());

        if(Tardigrade.CARD_ATTRIBUTES != null) {
            for (int i = 0; i < Tardigrade.CARD_ATTRIBUTES.length; i++) {
                tuple.put(
                        Tardigrade.CARD_ATTRIBUTES[i],
                        String.valueOf(card.getAttributeByName(Tardigrade.CARD_ATTRIBUTES[i]))
                );
            }
        }

        long result = db.insert(TABLE_NAME, "", tuple);
        db.close();

        if(result == -1){
            return false;
        }
        return true;
    }

    @SuppressLint("NewApi")
    @Override
    public ICard getCard(String id) {
        ICard card = null;
        if(!id.isEmpty() || id != null) {
            String where = CARD_ID + "=" + id;

            ArrayList<String> fields = new ArrayList<>();
            fields.add(CARD_ID);
            fields.add(CARD_NAME);
            fields.add(CARD_DESCRIPTION);

            if(Tardigrade.CARD_ATTRIBUTES != null) {
                for (int i = 0; i < Tardigrade.CARD_ATTRIBUTES.length; i++) {
                    fields.add(Tardigrade.CARD_ATTRIBUTES[i]);
                }
            }

            String[] f = new String[fields.size()];
            f = fields.toArray(f);

            db = this.getReadableDatabase();
            Cursor cursor;
            cursor = db.query(TABLE_NAME, f, where, null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CARD_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(CARD_DESCRIPTION));

                String[] attr = null;

                if(Tardigrade.CARD_ATTRIBUTES != null) {
                    attr = new String[Tardigrade.CARD_ATTRIBUTES.length];
                    for (int i = 0; i < Tardigrade.CARD_ATTRIBUTES.length; i++) {
                        attr[i] = cursor.getString(cursor.getColumnIndexOrThrow(Tardigrade.CARD_ATTRIBUTES[i]));
                    }
                }

                card = Card.Create(id, name, description, attr);
            }

            db.close();
        }
        return card;
    }
    @Override
    public List<ICard> getAllCards() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add(CARD_ID);
        fields.add(CARD_NAME);
        fields.add(CARD_DESCRIPTION);

        if(Tardigrade.CARD_ATTRIBUTES != null) {
            for (int i = 0; i < Tardigrade.CARD_ATTRIBUTES.length; i++) {
                fields.add(Tardigrade.CARD_ATTRIBUTES[i]);
            }
        }

        String[] f = new String[fields.size()];
        f = fields.toArray(f);

        db = this.getReadableDatabase();
        Cursor cursor;
        cursor = db.query(TABLE_NAME, f, null, null, null, null, null, null);

        List<ICard> cards = new ArrayList<ICard>();
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            boolean open = true;
            while(open){
                String id = cursor.getString(cursor.getColumnIndexOrThrow(CARD_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CARD_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(CARD_DESCRIPTION));

                String[] attr = null;
                if(Tardigrade.CARD_ATTRIBUTES != null) {
                    attr = new String[Tardigrade.CARD_ATTRIBUTES.length];
                    for (int i = 0; i < Tardigrade.CARD_ATTRIBUTES.length; i++) {
                        attr[i] = cursor.getString(cursor.getColumnIndexOrThrow(Tardigrade.CARD_ATTRIBUTES[i]));
                    }
                }

                ICard card = Card.Create(id, name, description, attr);
                cards.add(card);

                if(cursor.isLast())
                    open = false;
                else
                    cursor.moveToNext();
            }
        }

        db.close();

        return cards;
    }


    /**
     *  Métodos de Carregamento
     *
     */

    @Override
    public void init(){
        loadDeck();
    }

    private String cardFile = "cardbase.csv";


    public void loadDeckParameter(String id){

        /*boolean isEnglish = Locale.getDefault().getLanguage().equals("en");
        boolean isPortuguese = Locale.getDefault().getLanguage().equals("pt-br");
        boolean isEspanhol = Locale.getDefault().getLanguage().equals("es");
        boolean isFranchise = Locale.getDefault().getLanguage().equals("fr");*/

        if(Locale.getDefault().getLanguage().equals("pt-br")){
            cardFile = "cardbase.csv";
        }

        if(Locale.getDefault().getLanguage().equals("es")){
            cardFile = "cardbase-espanhol.csv";
        }

        if(Locale.getDefault().getLanguage().equals("en")){
            cardFile = "cardbase-english.csv";
        }


        //loadDeck();
    }

    @Override
    public void loadDeck(){

        //Identifica o idioma do dispositivo
        if(Locale.getDefault().getLanguage().equals("pt-br")){
            cardFile = "cardbase.csv";
        }else if(Locale.getDefault().getLanguage().equals("es")){
            cardFile = "cardbase-espanhol.csv";
        }else if(Locale.getDefault().getLanguage().equals("en")){
            cardFile = "cardbase-english.csv";
        }

        ReadFile read = new ReadFile(cardFile);
        read.setOnStartListener(new ICallback() {
            @Override
            public void doit(IPack result) {

            }
        });
        read.setOnReadingListener(new ICallback() {
            @Override
            public void doit(IPack result) {
                String[] row = (String[]) result.getValue();
                String[] attr = null;

                String id = row[0];
                String name = row[1];
                String description = row[2];

                if(Tardigrade.CARD_ATTRIBUTES != null) {
                    attr = new String[Tardigrade.CARD_ATTRIBUTES.length];

                    for (int i = 3; i < 4 + Tardigrade.CARD_ATTRIBUTES.length ; i++){
                        attr[i-3] = row[i];
                    }
                }

                if(getCard(id) == null) {
                    ICard card = Card.Create(id, name, description, attr);
                    addCard(card);
                }
            }
        });
        read.setOnFinishListener(new ICallback() {
            @Override
            public void doit(IPack result) {
                List<ICard> cards = getAllCards();
                IPack pack = Pack.create(Flag.NOTIFY, cards);
                onLoadDeck.doit(pack);
            }
        });
        read.setOnFailListener(new ICallback() {
            @Override
            public void doit(IPack result) {
                onUseCard.doit(result);
            }
        });
        read.readAsCSV();
    }


    /**
     *  Métodos de deck
     *
     */

    @Override
    public void shuffleDeck() {
        long seed = System.nanoTime();
        Collections.shuffle(stack, new Random(seed));
    }
    @Override
    public void putCard(ICard card) {
        stack.add(getCard(card.getId()));
    }
    @Override
    public void useCard(ICard card){
        onUseCard.doit(Pack.create(Flag.RECOGNIZED_IN_GAME, card));
    }

    @Override
    public void setOnUseCard(ICallback callback){
        onUseCard = callback;

    }



    @Override
    public void setOnLoadDeck(ICallback callback) {
        onLoadDeck = callback;
    }

}