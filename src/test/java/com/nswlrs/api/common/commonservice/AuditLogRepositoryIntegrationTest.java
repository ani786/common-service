package com.nswlrs.api.common.commonservice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.sql.DataSource;

import com.nswlrs.api.common.commonservice.config.IntegrationTest;
import com.nswlrs.api.common.commonservice.model.UpdtTrnlog;
import com.nswlrs.api.common.commonservice.model.UserAuditLog;
import com.nswlrs.api.common.commonservice.repository.UpdtTrnlogRepository;
import com.nswlrs.api.common.commonservice.repository.UserAuditLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * The type User audit log repository integration test.
 * This is used only for Dev purpose to test the insert so whenever you need it in your local remove @Disabled and run
 */
@Disabled
@IntegrationTest
class AuditLogRepositoryIntegrationTest {

    private UserAuditLogRepository userAuditLogRepository;
    private UpdtTrnlogRepository updtTrnlogRepository;

    private JdbcTemplate jdbcTemplate;

    /**
     * Init use case.
     */
    @BeforeEach
    void initUseCase() {
        setDataSource(getDataSource());
        userAuditLogRepository = new UserAuditLogRepository(jdbcTemplate);
        updtTrnlogRepository = UpdtTrnlogRepository.builder().jdbcTemplate(jdbcTemplate).build();
    }

    /**
     * Sets data source.
     *
     * @param dataSource the data source
     */
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Insert.
     */
    @Test
    public void userAuditLog() {

        final String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"));
        int returnVal = userAuditLogRepository.insert(
                    UserAuditLog.builder().actionDate(date).actionedBy("loggedInUsser")
                                .userCode("jdoe").processName("test-from-common-service").dbTable("NONE")
                                .dbActionSig("I")
                                .activityDetails("WATER,SCAN").build());
        System.out.println("insert returnVal {}, " + returnVal);
        assert (returnVal >= 0);
    }


    @Test
    public void updtTrnlog() {

        final String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"));
        int returnVal = updtTrnlogRepository.insert(
                    UpdtTrnlog.builder().transnDate(date).transnType("CXS").userCode("TEST-USR").progName("test-microservice_name")
                                .inputData("test-API_name").recordKey("test-API_name").dbTble("test-DB_TBL_NAME").dbActionSig("I")
                                .aftrImage("Activity details shall be recorded here").build());
        System.out.println("insert returnVal {}, " + returnVal);
        assert (returnVal >= 0);
    }



    /**
     * Returns a DataSource object for connection to the database.
     *
     * @return a DataSource.
     */
    private static DataSource getDataSource() {
        // Creates a new instance of DriverManagerDataSource and sets
        // the required parameters such as the Jdbc Driver class,
        // Jdbc URL, database user name and password.
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl(
                    "jdbc:oracle:thin:@service-non-prod-itsdev.c7hrloe7gir6.ap-southeast-2.rds.amazonaws" +
                                ".com:1525/itsdev");
        dataSource.setUsername("ITSDQM");
        dataSource.setPassword("2C5YV8BC");
        return dataSource;
    }

}
