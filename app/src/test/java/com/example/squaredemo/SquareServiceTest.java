package com.example.squaredemo;

import com.example.squaredemo.network.MainApi;
import com.example.squaredemo.network.MainNetworkClient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JUnit4.class)
public class SquareServiceTest {

    private MainNetworkClient mainNetworkClient;

    @Mock
    MainApi mockMainApi;

    @Before
    public void setUp(){
        initMocks(this);

        mainNetworkClient = new MainNetworkClient(mockMainApi);
    }

    @Test
    public void getEmployees_performsNetworkRequest() {
        mainNetworkClient.requestEmployees();

        verify(mockMainApi).getEmployees();
    }
}
