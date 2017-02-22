package com.example.jirka.silvestr2016;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int penize = 0;
    int jidlo = 0;
    int alkohol = 0;
    int lampy = 0;
    int kapacitaObjektu = 12;

    int ucastnikuBozich = 0;
    int ucastnikuDocela = 0;
    int ucastnikuOzrat = 0;

    private int pocetUcastniku() {
        return ucastnikuBozich + ucastnikuDocela + ucastnikuOzrat;
    }
    private double zajemBozi() {
        return (5.0 + 0.5*ucastnikuBozich + 0.25*ucastnikuDocela + 0.1*ucastnikuOzrat) * Math.pow(2,lampy) * (1 - pocetUcastniku()/40) - ucastnikuBozich;
    }
    private double zajemDocela() {
        return (5.0 + 0.25*ucastnikuBozich + 0.34*jidlo + 0.1*alkohol) * Math.pow(2,lampy) * (1 - pocetUcastniku()/70) - ucastnikuDocela;
    }
    private double zajemOzrat() {
        return (5.0 + 0.1*ucastnikuBozich + 0.34*jidlo + 0.5*alkohol) * Math.pow(2,lampy) * (1 - pocetUcastniku()/120) - ucastnikuOzrat;
    }

    private double bodu() {
        return 1.5*ucastnikuBozich + 1.2*ucastnikuDocela + 1*ucastnikuOzrat;
    }

    private static final int UCASTNICKY_POPLATEK = 100;
    private static final int CENA_JIDLO = 50;
    private static final int CENA_ALKOHOL = 50;
    private static final int CENA_LAMPA = 800;
    private static final int CENA_KAPACITA = 800;
    private static final int KAPACITA_SKOK = 12;

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        penize = savedInstanceState.getInt("penize");
        jidlo = savedInstanceState.getInt("jidlo");
        alkohol = savedInstanceState.getInt("alkohol");
        lampy = savedInstanceState.getInt("lampy");
        kapacitaObjektu = savedInstanceState.getInt("kapacitaObjektu");

        ucastnikuBozich = savedInstanceState.getInt("ucastnikuBozich");
        ucastnikuDocela = savedInstanceState.getInt("ucastnikuDocela");
        ucastnikuOzrat = savedInstanceState.getInt("ucastnikuOzrat");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("penize", penize);
        outState.putInt("jidlo", jidlo);
        outState.putInt("alkohol", alkohol);
        outState.putInt("lampy", lampy);
        outState.putInt("kapacitaObjektu", kapacitaObjektu);

        outState.putInt("ucastnikuBozich", ucastnikuBozich);
        outState.putInt("ucastnikuDocela", ucastnikuDocela);
        outState.putInt("ucastnikuOzrat", ucastnikuOzrat);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button buttonJidlo = (Button)findViewById(R.id.plusJidlo);
        Button buttonAlkohol = (Button)findViewById(R.id.plusAlkohol);
        Button buttonLampy = (Button)findViewById(R.id.plusLampy);

        Button buttonUcastniciBozi = (Button)findViewById(R.id.plusUcastniciBozi);
        Button buttonUcastniciDocela = (Button)findViewById(R.id.plusUcastniciDocela);
        Button buttonUcastniciOzrat = (Button)findViewById(R.id.plusUcastniciOzrat);

        Button buttonKapacita = (Button)findViewById(R.id.plusKapacita);

        buttonJidlo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { pridejJidlo(); }
        });
        buttonJidlo.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) { odeberJidlo(); return true; }
        });

        buttonAlkohol.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { pridejAlkohol(); }
        });
        buttonAlkohol.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) { odeberAlkohol(); return true; }
        });

        buttonLampy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { pridejLampy(); }
        });
        buttonLampy.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) { odeberLampy(); return true; }
        });

        ////

        buttonUcastniciBozi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { pridejUcastnikaBozi(); }
        });
        buttonUcastniciBozi.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) { odeberUcastnikaBozi(); return true; }
        });

        buttonUcastniciDocela.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { pridejUcastnikaDocela(); }
        });
        buttonUcastniciDocela.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) { odeberUcastnikaDocela(); return true; }
        });

        buttonUcastniciOzrat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { pridejUcastnikaOzrat(); }
        });
        buttonUcastniciOzrat.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) { odeberUcastnikaOzrat(); return true; }
        });

        buttonKapacita.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { pridejKapacita(); }
        });
        buttonKapacita.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) { odeberKapacita(); return true; }
        });

        updateCounters();
    }

    public void updateCounters() {
        ((TextView)findViewById(R.id.statusPenize)).setText(Integer.toString(penize));
        ((TextView)findViewById(R.id.statusJidlo)).setText(Integer.toString(jidlo));
        ((TextView)findViewById(R.id.statusAlkohol)).setText(Integer.toString(alkohol));
        ((TextView)findViewById(R.id.statusLampy)).setText(Integer.toString(lampy));

        ((TextView)findViewById(R.id.ucastnikuBozich)).setText(Integer.toString(ucastnikuBozich));
        ((TextView)findViewById(R.id.ucastnikuBozichDostupno)).setText(Double.toString(Math.round(zajemBozi() * 10) / 10.0));
        ((TextView)findViewById(R.id.ucastnikuDocela)).setText(Integer.toString(ucastnikuDocela));
        ((TextView)findViewById(R.id.ucastnikuDocelaDostupno)).setText(Double.toString(Math.round(zajemDocela() * 10) / 10.0));
        ((TextView)findViewById(R.id.ucastnikuOzrat)).setText(Integer.toString(ucastnikuOzrat));
        ((TextView)findViewById(R.id.ucastnikuOzratDostupno)).setText(Double.toString(Math.round(zajemOzrat() * 10) / 10.0));

        ((TextView)findViewById(R.id.statusKapacita)).setText(Integer.toString(pocetUcastniku())+"/"+Integer.toString(kapacitaObjektu));
        ((TextView)findViewById(R.id.statusBodu)).setText(Double.toString(bodu()));
    }

    public void pridejJidlo() {
        if (penize >= CENA_JIDLO) {
            jidlo++;
            penize -= CENA_JIDLO;
            updateCounters();
        }
    }
    public void odeberJidlo() {
        if (jidlo > 0) {
            jidlo--;
            penize += CENA_JIDLO;
            updateCounters();
        }
    }

    public void pridejAlkohol() {
        if (penize >= CENA_ALKOHOL) {
            alkohol++;
            penize -= CENA_ALKOHOL;
            updateCounters();
        }
    }
    public void odeberAlkohol() {
        if (alkohol > 0) {
            alkohol--;
            penize += CENA_ALKOHOL;
            updateCounters();
        }
    }

    public void pridejLampy() {
        if (penize >= CENA_LAMPA) {
            lampy++;
            penize -= CENA_LAMPA;
            updateCounters();
        }
    }
    public void odeberLampy() {
        if (lampy > 0) {
            lampy--;
            penize += CENA_LAMPA;
            updateCounters();
        }
    }

    public void pridejUcastnikaBozi() {
        if (zajemBozi() >= 1 && pocetUcastniku() < kapacitaObjektu) {
            ucastnikuBozich++;
            penize += UCASTNICKY_POPLATEK;
            updateCounters();
        }
    }
    public void odeberUcastnikaBozi() {
        if (ucastnikuBozich > 0 && penize >= UCASTNICKY_POPLATEK) {
            ucastnikuBozich--;
            penize -= UCASTNICKY_POPLATEK;
            updateCounters();
        }
    }

    public void pridejUcastnikaDocela() {
        if (zajemDocela() >= 1 && pocetUcastniku() < kapacitaObjektu) {
            ucastnikuDocela++;
            penize += UCASTNICKY_POPLATEK;
            updateCounters();
        }
    }
    public void odeberUcastnikaDocela() {
        if (ucastnikuDocela > 0 && penize >= UCASTNICKY_POPLATEK) {
            ucastnikuDocela--;
            penize -= UCASTNICKY_POPLATEK;
            updateCounters();
        }
    }

    public void pridejUcastnikaOzrat() {
        if (zajemOzrat() >= 1 && pocetUcastniku() < kapacitaObjektu) {
            ucastnikuOzrat++;
            penize += UCASTNICKY_POPLATEK;
            updateCounters();
        }
    }
    public void odeberUcastnikaOzrat() {
        if (ucastnikuOzrat > 0 && penize >= UCASTNICKY_POPLATEK) {
            ucastnikuOzrat--;
            penize -= UCASTNICKY_POPLATEK;
            updateCounters();
        }
    }

    public void pridejKapacita() {
        if (penize >= CENA_KAPACITA) {
            kapacitaObjektu += KAPACITA_SKOK;
            penize -= CENA_KAPACITA;
            updateCounters();
        }
    }
    public void odeberKapacita() {
        if (pocetUcastniku() <= kapacitaObjektu - KAPACITA_SKOK && kapacitaObjektu > KAPACITA_SKOK) {
            kapacitaObjektu -= KAPACITA_SKOK;
            penize -= CENA_KAPACITA;
            updateCounters();
        }
    }
}
