package com.example.baselibrary.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baselibrary.BaseViewModel;
import com.example.baselibrary.utils.PUtils;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public abstract class BaseActivity<ViewModel extends BaseViewModel> extends AppCompatActivity {

    private ArrayList<Disposable> disposables = new ArrayList<>();
    public ViewModel viewModel;

    public void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    public void deleteDisposable(Disposable disposable) {
        if (!disposable.isDisposed()) {
            disposable.isDisposed();
        }
        if (disposables.contains(disposable)) {
            disposables.remove(disposable);
        }
    }

    public void clearDisposable() {
        for (Disposable disposable : disposables) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        disposables.clear();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = PUtils.create(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearDisposable();
    }
}
