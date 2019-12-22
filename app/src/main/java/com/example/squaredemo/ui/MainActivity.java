package com.example.squaredemo.ui;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.squaredemo.R;
import com.example.squaredemo.models.ListData;
import com.example.squaredemo.util.VerticalSpacingItemDecoration;
import com.example.squaredemo.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends DaggerAppCompatActivity {

    private static final String TAG = "MainActivity";

    private MainViewModel viewModel;
    Unbinder unbinder;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    MainRecyclerAdapter adapter;

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(MainViewModel.class);

        iniRecyclerView();
        subscribeObservers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        disposables.clear();
    }

    private void iniRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(0);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
    }

    private void subscribeObservers() {
        Log.d(TAG, "subscribeObservers: starting");
        disposables.add(viewModel.getEmployees().subscribe(
                employeeResource -> observePosts(employeeResource),
                throwable -> Log.e(TAG, "subscribeObservers: onError", throwable)
        ));
    }

    private void observePosts(Resource<ListData> listDataResource) {
        Log.d(TAG, "observePosts: starting");

        if(listDataResource != null) {
            switch (listDataResource.status) {
                case SUCCESS:{
                    Log.d(TAG, "observePosts: got employees!");
                    adapter.setEmployeeList(listDataResource.data.getEmployees());
                    break;
                }
                case ERROR:{
                    Log.e(TAG, "observePosts: ERROR! " + listDataResource.message);
                    Toast.makeText(this, listDataResource.message, Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
    }
}
