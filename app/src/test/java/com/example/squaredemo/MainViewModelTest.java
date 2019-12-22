package com.example.squaredemo;

import androidx.annotation.NonNull;

import com.example.squaredemo.models.Employee;
import com.example.squaredemo.models.ListData;
import com.example.squaredemo.network.MainApi;
import com.example.squaredemo.network.MainNetworkClient;
import com.example.squaredemo.network.RequestContent;
import com.example.squaredemo.ui.MainActivity;
import com.example.squaredemo.ui.MainViewModel;
import com.example.squaredemo.ui.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JUnit4.class)
public class MainViewModelTest {

    private MainViewModel viewModel;

    private Scheduler testScheduler;

    @InjectMocks
    MainActivity mockActivity;

    @InjectMocks
    MainNetworkClient mockMainNetworkClient;

    @InjectMocks
    RequestContent mockRequestContent;

    @Mock
    MainApi mockMainApi;

    @Mock
    Employee mockEmployee;

    @Mock
    List<Employee> mockEmployeeList;

    @Mock
    ListData listData;

    @Mock
    Resource resource;

    @BeforeClass
    public static void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit);
            }

            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run, true);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }

    @Before
    public void setUp(){
        initMocks(this);
        mockMainNetworkClient = new MainNetworkClient(mockMainApi);
        mockRequestContent = new RequestContent(mockMainNetworkClient);
        viewModel = new MainViewModel(mockRequestContent);
        testScheduler = new TestScheduler();
        mockEmployee = new Employee("1", "John", "test", "Android", "Full");
        mockEmployeeList = new ArrayList<>(Arrays.asList(mockEmployee));
        listData = new ListData(mockEmployeeList);
    }

    @Test
    public void verifyGetEmployees() {
        doReturn(Single.just(Resource.success(listData))).when(mockMainApi).getEmployees();

        viewModel.getEmployees();
        verify(resource);
    }

    @Test
    public void verifyModel() {
        assertEquals(listData.getEmployees().get(0).getUuid(), "1");
        assertEquals(listData.getEmployees().get(0).getFullName(), "John");
        assertEquals(listData.getEmployees().get(0).getEmailAddress(), "test");
        assertEquals(listData.getEmployees().get(0).getTeam(), "Android");
        assertEquals(listData.getEmployees().get(0).getEmployeeType(), "Full");
    }

}