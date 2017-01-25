package ill.moneyclicker.com.moneyclicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.kosalgeek.android.caching.FileCacher;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    double thisMoney;
    double totalMoney;
    int thisMoneyCounter;

    public double treeMoney;
    public double treeCost = 10.00;
    public int treeCounter;

    public double copierMoney;
    public double copierCost = 150.00;
    public int copierCounter;

    Button tMoney;
    Button cMoney;
    Button makeMoney;
    Chronometer save;
    Chronometer tree;
    Chronometer copier;
    EditText thisMoneyText;
    EditText totalMoneyText;
    TextView moneyTreePriceText;
    TextView moneyCopierPriceText;

    final FileCacher<Double> thisMoneyCache = new FileCacher<>(MainActivity.this, "Cache_01.cache");
    final FileCacher<Double> totalMoneyCache = new FileCacher<>(MainActivity.this, "Cache_02.cache");

    final FileCacher<Double> treeMoneyCache = new FileCacher<>(MainActivity.this, "Cache_03.cache");
    final FileCacher<Integer> treeCounterCache = new FileCacher<>(MainActivity.this, "Cache_04.cache");
    final FileCacher<Double> treePriceCache = new FileCacher<>(MainActivity.this, "Cache_05.cache");

    final FileCacher<Double> copierMoneyCache = new FileCacher<>(MainActivity.this, "Cache_06.cache");
    final FileCacher<Double> copierPriceCache = new FileCacher<>(MainActivity.this, "Cache_07.cache");
    final FileCacher<Integer> copierCounterCache = new FileCacher<>(MainActivity.this, "Cache_08.cache");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tMoney = (Button) findViewById(R.id.moneyTree);
        cMoney = (Button) findViewById(R.id.moneyCopier);
        makeMoney = (Button) findViewById(R.id.makeMoney);

        thisMoneyCounter = 1;
        tree = (Chronometer) findViewById(R.id.tree);
        save = (Chronometer) findViewById(R.id.chronometer);
        copier = (Chronometer) findViewById(R.id.copier);
        thisMoneyText = (EditText) findViewById(R.id.thisMoney);
        totalMoneyText = (EditText) findViewById(R.id.totalMoney);
        moneyTreePriceText = (TextView) findViewById(R.id.moneyTreePrice);
        moneyCopierPriceText = (TextView) findViewById(R.id.moneyCopierPrice);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        thisMoneyText.setText(String.valueOf(currencyFormatter.format(totalMoney)));

        try {
            thisMoney = thisMoneyCache.readCache();
            totalMoney = totalMoneyCache.readCache();
            treeMoney = treeMoneyCache.readCache();
            treeCounter = treeCounterCache.readCache();
            treeCost = treePriceCache.readCache();
            copierMoney = copierMoneyCache.readCache();
            copierCounter = copierCounterCache.readCache();
            copierCost = copierPriceCache.readCache();
        } catch (IOException e) {
            e.printStackTrace();
        }

        save.start();

        if (treeCounter > 0) {
            tree.start();
            tMoney.setText("Money Tree    Level: " + treeCounter);
            moneyTreePriceText.setText(String.valueOf(currencyFormatter.format(treeCost)));
        }

        if (copierCounter > 0) {
            copier.start();
            cMoney.setText("Money Copier    Level: " + copierCounter);
            moneyCopierPriceText.setText(String.valueOf(currencyFormatter.format(copierCost)));
        }

        makeMoney.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                thisMoney += ((Math.PI * Math.random()) + ((thisMoney) / 17) * thisMoneyCounter) / 20d;

                totalMoney += thisMoney;
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
                thisMoneyText.setText(String.valueOf(currencyFormatter.format(thisMoney)));
            }
        });

        tMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (treeCost <= thisMoney) {
                    thisMoney -= treeCost;
                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
                    thisMoneyText.setText(String.valueOf(currencyFormatter.format(thisMoney)));

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

                    tMoney.setText("Money Tree    Level: " + treeCounter);
                }
            }
        });

        cMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (copierCost <= thisMoney) {
                    thisMoney -= copierCost;
                    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
                    thisMoneyText.setText(String.valueOf(currencyFormatter.format(thisMoney)));

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

                    cMoney.setText("Money Copier    Level: " + copierCounter);
                }
            }
        });

        save.getOnChronometerTickListener();
        save.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                try {
                    thisMoneyCache.writeCache(thisMoney);
                    totalMoneyCache.writeCache(totalMoney);
                    treeMoneyCache.writeCache(treeMoney);
                    treeCounterCache.writeCache(treeCounter);
                    treePriceCache.writeCache(treeCost);
                    copierPriceCache.writeCache(copierCost);
                    copierMoneyCache.writeCache(copierMoney);
                    copierCounterCache.writeCache(copierCounter);
                } catch (IOException e) {
                    e.printStackTrace();
                }

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

                totalMoney = thisMoney;
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
                totalMoneyText.setText(String.valueOf(currencyFormatter.format(totalMoney)));
            }
        });

        tree.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (treeCost <= thisMoney) {
                    tMoney.setEnabled(true);
                } else {
                    tMoney.setEnabled(false);
                }

                treeMoney += (((Math.PI * Math.random()) * treeCounter)) / 560d;

                thisMoney += treeMoney;

                try {
                    thisMoneyCache.writeCache(thisMoney);
                    totalMoneyCache.writeCache(totalMoney);
                    treeMoneyCache.writeCache(treeMoney);
                    treeCounterCache.writeCache(treeCounter);
                    treePriceCache.writeCache(treeCost);
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

        copier.getOnChronometerTickListener();
        copier.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                try {
                    thisMoneyCache.writeCache(thisMoney);
                    totalMoneyCache.writeCache(totalMoney);
                    treeMoneyCache.writeCache(treeMoney);
                    treeCounterCache.writeCache(treeCounter);
                    treePriceCache.writeCache(treeCost);
                    copierPriceCache.writeCache(copierCost);
                    copierMoneyCache.writeCache(copierMoney);
                    copierCounterCache.writeCache(copierCounter);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (copierCost <= thisMoney) {
                    cMoney.setEnabled(true);
                } else {
                    cMoney.setEnabled(false);
                }

                totalMoney = thisMoney;
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
                totalMoneyText.setText(String.valueOf(currencyFormatter.format(totalMoney)));
            }
        });
    }
}