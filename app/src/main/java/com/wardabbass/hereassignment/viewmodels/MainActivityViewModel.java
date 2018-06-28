package com.wardabbass.hereassignment.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.wardabbass.hereassignment.models.TaxiEta;

import org.fluttercode.datafactory.impl.DataFactory;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivityViewModel extends ViewModel {
    public static final int STUB_TAXIES_COUNT = 10;

    private List<TaxiEta> taxiEtas = new ArrayList<>();

    private final static DataFactory DATA_FACTORY = new DataFactory();

    public void generateTaxiEtas() {

        for (int i = 0; i < STUB_TAXIES_COUNT; i++) {
            TaxiEta taxiEta = new TaxiEta(DATA_FACTORY.getBusinessName(), "", new DateTime());
            taxiEtas.add(taxiEta);
        }
    }


    public void updateEtas() {
        Date nowDate = DateTime.now().toDate();
        Date endDate = DateTime.now().plusDays(1).withTimeAtStartOfDay().toDate(); // mid night of tommorow 00:00
        DateTime randDate = new DateTime(DATA_FACTORY.getDateBetween(nowDate, endDate));

        for (int i = 0; i < 50; i++) {
            taxiEtas.get(i).setEta(randDate);
        }

    }

    public void setTaxiEtas(List<TaxiEta> taxiEtas) {
        this.taxiEtas = taxiEtas;
    }

    public List<TaxiEta> getTaxiEtas() {
        return taxiEtas;
    }



    public static DateTime generateEta(){
        Date nowDate = DateTime.now().toDate();
        Date endDate = DateTime.now().plusDays(1).withTimeAtStartOfDay().toDate(); // mid night of tommorow 00:00
        DateTime randDate = new DateTime(DATA_FACTORY.getDateBetween(nowDate, endDate));
        return randDate;
    }
}
