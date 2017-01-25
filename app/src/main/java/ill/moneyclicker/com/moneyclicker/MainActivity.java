package ill.moneyclicker.com.moneyclicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.kosalgeek.android.caching.FileCacher;

import java.io.IOException;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    double thisMoney;
    int thisMoneyCounter;

    public double treeMoney;
    public double treeCost = 10.00;
    public int treeCounter;

    public double copierMoney;
    public double copierCost = 150.00;
    public int copierCounter;

    public double minerMoney;
    public double minerCost = 500.00;
    public int minerCounter;

    Button tMoney;
    Button cMoney;
    Button mMoney;
    Button makeMoney;
    Chronometer save;
    Chronometer tree;
    Chronometer copier;
    Chronometer miner;
    EditText thisMoneyText;
    TextView moneyTreePriceText;
    TextView moneyCopierPriceText;
    TextView moneyMinerPriceText;

    final FileCacher<Double> thisMoneyCache = new FileCacher<>(MainActivity.this, "Cache_01.cache");

    final FileCacher<Double> treeMoneyCache = new FileCacher<>(MainActivity.this, "Cache_03.cache");
    final FileCacher<Integer> treeCounterCache = new FileCacher<>(MainActivity.this, "Cache_04.cache");
    final FileCacher<Double> treePriceCache = new FileCacher<>(MainActivity.this, "Cache_05.cache");

    final FileCacher<Double> copierMoneyCache = new FileCacher<>(MainActivity.this, "Cache_06.cache");
    final FileCacher<Double> copierPriceCache = new FileCacher<>(MainActivity.this, "Cache_07.cache");
    final FileCacher<Integer> copierCounterCache = new FileCacher<>(MainActivity.this, "Cache_08.cache");

    final FileCacher<Double> minerMoneyCache = new FileCacher<>(MainActivity.this, "Cache_09.cache");
    final FileCacher<Double> minerPriceCache = new FileCacher<>(MainActivity.this, "Cache_10.cache");
    final FileCacher<Integer> minerCounterCache = new FileCacher<>(MainActivity.this, "Cache_11.cache");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tMoney = (Button) findViewById(R.id.moneyTree);
        cMoney = (Button) findViewById(R.id.moneyCopier);
        mMoney = (Button) findViewById(R.id.moneyMiner);
        makeMoney = (Button) findViewById(R.id.makeMoney);

        thisMoneyCounter = 1;
        tree = (Chronometer) findViewById(R.id.tree);
        save = (Chronometer) findViewById(R.id.chronometer);
        copier = (Chronometer) findViewById(R.id.copier);
        miner = (Chronometer) findViewById(R.id.miner);
        thisMoneyText = (EditText) findViewById(R.id.thisMoney);
        moneyTreePriceText = (TextView) findViewById(R.id.moneyTreePrice);
        moneyCopierPriceText = (TextView) findViewById(R.id.moneyCopierPrice);
        moneyMinerPriceText = (TextView) findViewById(R.id.moneyMinerPrice);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

        try {
            thisMoney = thisMoneyCache.readCache();
            treeMoney = treeMoneyCache.readCache();
            treeCounter = treeCounterCache.readCache();
            treeCost = treePriceCache.readCache();
            copierMoney = copierMoneyCache.readCache();
            copierCounter = copierCounterCache.readCache();
            copierCost = copierPriceCache.readCache();
            minerMoney = copierMoneyCache.readCache();
            minerCounter = copierCounterCache.readCache();
            minerCost = copierPriceCache.readCache();
        } catch (IOException e) {
            e.printStackTrace();
        }

        save.start();

        if (treeCounter > 0) {
            tree.start();
            tMoney.setText("Tree Level: " + treeCounter);
            moneyTreePriceText.setText(String.valueOf(currencyFormatter.format(treeCost)));
        }

        if (copierCounter > 0) {
            copier.start();
            cMoney.setText("Copier Level: " + copierCounter);
            moneyCopierPriceText.setText(String.valueOf(currencyFormatter.format(copierCost)));
        }

        if (minerCounter > 0) {
            miner.start();
            mMoney.setText("Miner Level: " + minerCounter);
            moneyMinerPriceText.setText(String.valueOf(currencyFormatter.format(minerCost)));
        }

        makeMoney.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                thisMoney += ((Math.PI * Math.random()) + ((thisMoney) / 4) * thisMoneyCounter) / 20d;

                if (treeCost <= thisMoney) {
                    tMoney.setEnabled(true);
                } else {
                    tMoney.setEnabled(false);
                }

                if (copierCost <= thisMoney) {
                    cMoney.setEnabled(true);
                } else {
                    cMoney.setEnabled(false);
                }

                if (minerCost <= thisMoney) {
                    mMoney.setEnabled(true);
                } else {
                    mMoney.setEnabled(false);
                }

                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
                thisMoneyText.setText(String.valueOf(currencyFormatter.format(thisMoney)));
                save.callOnClick();
            }
        });

        tMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (treeCost <= thisMoney) {
                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

                    thisMoney -= treeCost;

                    treeCost += (((Math.PI * Math.random()) * (treeCost * 2))) / 12d;
                    treeCost = treeCost;
                    moneyTreePriceText.setText(String.valueOf(currencyFormatter.format(treeCost)));

                    tree.start();

                    if (treeCost <= thisMoney) {
                        tMoney.setEnabled(true);
                    } else {
                        tMoney.setEnabled(false);
                    }

                    treeCounter++;

                    save.callOnClick();
                    tMoney.setText("Tree Level: " + treeCounter);

                    thisMoneyText.setText(String.valueOf(currencyFormatter.format(thisMoney)));
                }
            }
        });

        cMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (copierCost <= thisMoney) {
                    thisMoney -= copierCost;
                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

                    copierCost += (((Math.PI * Math.random()) * (copierCost * 2))) / 12d;
                    copierCost = copierCost;
                    moneyCopierPriceText.setText(String.valueOf(currencyFormatter.format(copierCost)));

                    copier.start();

                    if (copierCost <= thisMoney) {
                        cMoney.setEnabled(true);
                    } else {
                        cMoney.setEnabled(false);
                    }

                    copierCounter++;

                    save.callOnClick();
                    cMoney.setText("Copier Level: " + copierCounter);

                    thisMoneyText.setText(String.valueOf(currencyFormatter.format(thisMoney)));
                }
            }
        });

        mMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minerCost <= thisMoney) {
                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

                    thisMoney -= minerCost;

                    minerCost += (((Math.PI * Math.random()) * (minerCost * 4))) / 12d;
                    minerCost = minerCost;
                    moneyMinerPriceText.setText(String.valueOf(currencyFormatter.format(minerCost)));

                    miner.start();

                    if (minerCost <= thisMoney) {
                        mMoney.setEnabled(true);
                    } else {
                        mMoney.setEnabled(false);
                    }

                    minerCounter++;

                    save.callOnClick();
                    mMoney.setText("Miner Level: " + minerCounter);

                    thisMoneyText.setText(String.valueOf(currencyFormatter.format(thisMoney)));
                }
            }
        });

        save.getOnChronometerTickListener();
        save.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                if (treeCost <= thisMoney) {
                    tMoney.setEnabled(true);
                } else {
                    tMoney.setEnabled(false);
                }

                if (copierCost <= thisMoney) {
                    cMoney.setEnabled(true);
                } else {
                    cMoney.setEnabled(false);
                }

                if (minerCost <= thisMoney) {
                    mMoney.setEnabled(true);
                } else {
                    mMoney.setEnabled(false);
                }

                try {
                    thisMoneyCache.writeCache(thisMoney);
                    treeMoneyCache.writeCache(treeMoney);
                    treeCounterCache.writeCache(treeCounter);
                    treePriceCache.writeCache(treeCost);

                    copierPriceCache.writeCache(copierCost);
                    copierMoneyCache.writeCache(copierMoney);
                    copierCounterCache.writeCache(copierCounter);

                    minerPriceCache.writeCache(minerCost);
                    minerMoneyCache.writeCache(minerMoney);
                    minerCounterCache.writeCache(minerCounter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        tree.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                treeMoney += (((Math.PI * Math.random()) + ((treeMoney / 2) * treeCounter))) / 560d;

                thisMoney += treeMoney;

                if (treeCost <= thisMoney) {
                    tMoney.setEnabled(true);
                } else {
                    tMoney.setEnabled(false);
                }

                try {
                    thisMoneyCache.writeCache(thisMoney);
                    treeMoneyCache.writeCache(treeMoney);
                    treeCounterCache.writeCache(treeCounter);
                    treePriceCache.writeCache(treeCost);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
                thisMoneyText.setText(String.valueOf(currencyFormatter.format(thisMoney)));
            }
        });

        copier.getOnChronometerTickListener();
        copier.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                copierMoney += (((Math.PI * Math.random()) + ((copierMoney / 2) * copierCounter))) / 560d;

                thisMoney += copierMoney;

                if (copierCost <= thisMoney) {
                    cMoney.setEnabled(true);
                } else {
                    cMoney.setEnabled(false);
                }

                try {
                    thisMoneyCache.writeCache(thisMoney);
                    copierPriceCache.writeCache(copierCost);
                    copierMoneyCache.writeCache(copierMoney);
                    copierCounterCache.writeCache(copierCounter);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
                thisMoneyText.setText(String.valueOf(currencyFormatter.format(thisMoney)));
            }
        });

        miner.getOnChronometerTickListener();
        miner.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                minerMoney += (((Math.PI * Math.random()) + ((minerMoney / 2) * minerCounter))) / 560d;

                thisMoney += minerMoney;

                if (minerCost <= thisMoney) {
                    mMoney.setEnabled(true);
                } else {
                    mMoney.setEnabled(false);
                }

                try {
                    thisMoneyCache.writeCache(thisMoney);
                    minerPriceCache.writeCache(minerCost);
                    minerMoneyCache.writeCache(minerMoney);
                    minerCounterCache.writeCache(minerCounter);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
                thisMoneyText.setText(String.valueOf(currencyFormatter.format(thisMoney)));
            }
        });
    }
}