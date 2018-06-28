package com.wardabbass.hereassignment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wardabbass.hereassignment.adapters.taxietas.TaxiEtasAdapter;
import com.wardabbass.hereassignment.models.TaxiEta;
import com.wardabbass.hereassignment.viewmodels.MainActivityViewModel;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    public static final int UPDATE_INTERVAL_MS = 5000;

    MainActivityViewModel mainActivityViewModel; // to hold data surviving orientation change :]

    TaxiEtasAdapter taxiEtasAdapter;

    RecyclerView recyclerView;

    CompositeDisposable compositeDisposable;

    private final static Handler handler = new Handler();

    private final Runnable updateEtasRunnable = new Runnable() {
        @Override
        public void run() {
            updateEtas();
            handler.postDelayed(updateEtasRunnable, UPDATE_INTERVAL_MS);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        initRecyclerView();

        compositeDisposable = new CompositeDisposable();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (taxiEtasAdapter.getItemCount() == 0) {
            handler.post(updateEtasRunnable);

        } else {
            handler.postDelayed(updateEtasRunnable, UPDATE_INTERVAL_MS);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.clear();
        handler.removeCallbacks(updateEtasRunnable);
    }

    private void initRecyclerView() {
        initAdapter();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(taxiEtasAdapter);
    }

    private void initAdapter() {
        taxiEtasAdapter = new TaxiEtasAdapter();
        taxiEtasAdapter.setItems(mainActivityViewModel.getTaxiEtas());
    }

    private void updateEtas() {

        Disposable disposable = Observable.fromCallable(new Callable<List<TaxiEta>>() {
            @Override
            public List<TaxiEta> call() throws Exception {
                return getOrCreateEtas();
            }
        })
                .flatMap(new Function<List<TaxiEta>, Observable<TaxiEta>>() {
                    @Override
                    public Observable<TaxiEta> apply(List<TaxiEta> taxiEtas) throws Exception {
                        return updateEtasObservable();

                    }
                }).toSortedList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TaxiEta>>() {
                    @Override
                    public void accept(List<TaxiEta> taxiEtas) throws Exception {
                        mainActivityViewModel.setTaxiEtas(taxiEtas);
                        taxiEtasAdapter.setItems(taxiEtas);
                    }
                });

        compositeDisposable.add(disposable);
    }

    private List<TaxiEta> getOrCreateEtas() {
        if (mainActivityViewModel.getTaxiEtas().isEmpty()) {
            mainActivityViewModel.generateTaxiEtas();
        }
        return mainActivityViewModel.getTaxiEtas();
    }

    private Observable<TaxiEta> updateEtasObservable() {
        return Observable.fromIterable(mainActivityViewModel.getTaxiEtas())
                .map(new Function<TaxiEta, TaxiEta>() {
                    @Override
                    public TaxiEta apply(TaxiEta taxiEta) throws Exception {
                        taxiEta.setEta(MainActivityViewModel.generateEta());
                        return taxiEta;
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
