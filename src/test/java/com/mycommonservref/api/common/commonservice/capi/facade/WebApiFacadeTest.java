package com.nswlrs.api.common.commonservice.capi.facade;

import com.nswlrs.api.common.commonservice.capi.model.ApiResultPages;
import com.nswlrs.api.common.commonservice.capi.util.XmlParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class WebApiFacadeTest {

    @Mock private LinuxWebApiExecutor linuxWebApiExecutor;
    @Mock private WindowsWebApiExecutor windowsWebApiExecutor;
    @Mock private XmlParser xmlParser;
    @InjectMocks private WebApiFacade underTest;
    @Mock private WebApiRequest apiRequest;

    private AutoCloseable closeable;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @Test
    void runProcess_test_S() {
        WebApiRequest webApiRequest = new WebApiRequest("fsc", "22302","lpi","RT|depopd|1863/1000001|N");
                    //STEP 5 C-API Request
        ApiResultPages data = underTest.runProcess(webApiRequest);
        System.out.println("data =====" + data);

    }

}
