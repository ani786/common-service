package com.mycommonservref.api.common.commonservice.capi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycommonservref.api.common.commonservice.capi.model.ApiResultPages;
import com.mycommonservref.api.common.commonservice.capi.util.XmlParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


class XmlParserTest {

    @InjectMocks private XmlParser underTest;

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
    void formatTitleSearchResult() throws Exception {
        String testingData = """
        <webapi><status>0</status><charge>0</charge><badfield>0</badfield><data><getmore>0</getmore><length>1297</length><response>S|ST|DP|1/23424|DP|1/23424|60|1||2|             NEW SOUTH WALES LAND REGISTRY SERVICES - TITLE SEARCH|3|             -----------------------------------------------------|4|                      WARNING: ***** TEST DATA ONLY *****|5||6||7|    FOLIO: 1/23424|8|    ------|9||10|               SEARCH DATE       TIME              EDITION NO    DATE|11|               -----------       ----              ----------    ----|12|               18/10/2021       1:11 PM                EDITION 2     18/12/2004|13||14||15|    LAND|16|    ----|17|    LOT 1 IN DEPOSITED PLAN 23424|18|       LOCAL GOVERNMENT AREA WAGGA WAGGA|19|       PARISH OF SOUTH WAGGA WAGGA   COUNTY OF WYNYARD|20|       TITLE DIAGRAM DP23424|21||22|    FIRST SCHEDULE|23|    --------------|24|    CRAIG STEPHEN KATSOOLIS                                 (T AB168412)|25||26|    SECOND SCHEDULE (2 NOTIFICATIONS)|27|    ---------------|28|        1   RESERVATIONS AND CONDITIONS IN THE CROWN GRANT(S)|29|MW      2   AB168413  MORTGAGE TO NECDL EASTPAC|30||31|    NOTATIONS|32|    ---------|33||34|    UNREGISTERED DEALINGS: NIL|35||36|            ***  END OF SEARCH  ***|37||38||39||40||41||42||43||44||45||46||47||48||49||50||51||52||53||54||55||56||57||58||59||60|    lrs:DRD-aelali                           PRINTED ON 18/10/2021|E|</response></data></webapi> 
        """;
        ApiResultPages apiResultPages = underTest.parse(testingData);
        System.out.println("apiResultPages ===\n" + apiResultPages);
        assertEquals(1, apiResultPages.getPageCount());
    }


    @Test
    void formatTitleSearchResult_WasFailing_on_ampersand() throws Exception {
        String testingData = """
        <webapi><status>0</status><charge>0</charge><badfield>0</badfield><data><getmore>0</getmore><length>4568</length><response>S|ST|DP|45/270215|DP|45/270215|120|1||2|             NEW SOUTH WALES LAND REGISTRY SERVICES - TITLE SEARCH|3|             -----------------------------------------------------|4|                      WARNING: ***** TEST DATA ONLY *****|5||6||7|    FOLIO: 45/270215|8|    ------|9||10|               SEARCH DATE       TIME              EDITION NO    DATE|11|               -----------       ----              ----------    ----|12|               18/10/2021       10:17 AM               EDITION 1       3/8/2004|13||14||15|    LAND|16|    ----|17|    LOT 45 IN COMMUNITY PLAN DP270215|18|       AT PYRMONT|19|       LOCAL GOVERNMENT AREA SYDNEY|20|       PARISH OF ST ANDREW   COUNTY OF CUMBERLAND|21|       TITLE DIAGRAM DP270215|22||23|    FIRST SCHEDULE|24|    --------------|25|    JACKSONS LANDING DEVELOPMENT PTY LIMITED|26||27|    SECOND SCHEDULE (18 NOTIFICATIONS)|28|    ---------------|29|IR      1   INTERESTS RECORDED ON REGISTER FOLIO 1/270215|30|CM      2   ATTENTION IS DIRECTED TO THE MANAGEMENT STATEMENT OF THE|31|            COMMUNITY SCHEME FILED WITH THE COMMUNITY PLAN|32|ESU2    3   DP1011425 EASEMENT FOR SUPPORT 4.97 METRE(S) WIDE (K3)|33|                      APPURTENANT TO THE LAND ABOVE DESCRIBED|34|RA3     4   DP1008189 RIGHT OF ACCESS 4, 4.8 METRE(S) WIDE AND VARIABLE|35|                      APPURTENANT TO THE LAND ABOVE DESCRIBED|36|EA29    5   DP270215  EASEMENT TO ACCESS & USE SWITCHBOARD 1.8 WIDE|37|                      APPURTENANT TO THE LAND ABOVE DESCRIBED (DOC.1)|38|EC2     6   DP270215  EASEMENT FOR SECURITY CONDUITS OVER EXISTING|39|                      LINE OF CONDUITS APPURTENANT TO THE LAND ABOVE|40|                      DESCRIBED (DOC.1)|41|EDW9    7   DP270215  EASEMENT FOR DRAINAGE OF WATER 14 & 2 METRE(S)|42|                      WIDE AND VARIABLE (A7) APPURTENANT TO THE LAND|43|                      ABOVE DESCRIBED (DOC.4)|44|EA5     8   DP270215  EASEMENT FOR LIGHTING PURPOSES 0.2 WIDE AND|45|                      VARIABLE (C7) APPURTENANT TO THE LAND ABOVE|46|                      DESCRIBED (DOC.4)|47|ESU2    9   DP270215  EASEMENT FOR SUPPORT AND SHELTER (F7)|48|                      APPURTENANT TO THE LAND ABOVE DESCRIBED (DOC.4)|49|EEP3    10  DP270215  EASEMENT FOR ELECTRICITY PURPOSES & ACCESS (MA)|50|                      APPURTENANT TO THE LAND ABOVE DESCRIBED (DOC.5)|51|EA9     11  DP270215  EASEMENT FOR IRRIGATION PURPOSES & ACCESS (MB)|52|                      APPURTENANT TO THE LAND ABOVE DESCRIBED (DOC.5)|53|EA11    12  DP270215  EASEMENT FOR FIRE SERVICES (MC) APPURTENANT TO|54|                      THE LAND ABOVE DESCRIBED (DOC.5)|55|EDW18   13  DP270215  EASEMENT FOR DRAINAGE OF WATER (MD) APPURTENANT|56|                      TO THE LAND ABOVE DESCRIBED (DOC.5)|57||58|                                             END OF PAGE 1 - CONTINUED OVER|59||60|    lrs:null-ewang                           PRINTED ON 18/10/2021|P|61||62|             NEW SOUTH WALES LAND REGISTRY SERVICES - TITLE SEARCH|63|             -----------------------------------------------------|64|                      WARNING: ***** TEST DATA ONLY *****|65||66||67|    FOLIO: 45/270215                                           PAGE   2|68|    ------|69||70|    SECOND SCHEDULE (18 NOTIFICATIONS) (CONTINUED)|71|    ---------------|72|EA13    14  DP270215  EASEMENT FOR ENCROACHMENT AND SUPPORT OF STABLE|73|                      TRUSS (MH) APPURTENANT TO THE LAND ABOVE DESCRIBED|74|                      (DOC.5)|75|EA17    15  DP270215  RIGHT OF VEHICULAR AND PEDESTRIAN ACCESS (MN)|76|                      APPURTENANT TO THE LAND ABOVE DESCRIBED (DOC.5)|77|ESU10   16  DP270215  EASEMENT FOR SUPPORT & SHELTER (MW) APPURTENANT|78|                      TO THE LAND ABOVE DESCRIBED (DOC.5)|79|EDW22   17  DP270215  EASEMENT FOR DRAINAGE OF WATER 1, 2 METRE(S)|80|                      WIDE AND VARIABLE (ER) APPURTENANT TO THE LAND|81|                      ABOVE DESCRIBED (DOC.7)|82|EDW25   18  DP270215  EASEMENT FOR DRAINAGE OF WATER 1 METRE(S) WIDE|83|                      (DG) APPURTENANT TO THE LAND ABOVE DESCRIBED|84|                      (DOC.7)|85||86|    NOTATIONS|87|    ---------|88||89|    UNREGISTERED DEALINGS:    CE AB675896    R  AC933842    R  AC933853|90|      R  AC933875    R  AC933876    R  AC933877    R  AC933878|91|      DP270215.|92||93|            ***  END OF SEARCH  ***|94||95||96||97||98||99||100||101||102||103||104||105||106||107||108||109||110||111||112||113||114||115||116||117||118||119||120|    lrs:null-ewang                           PRINTED ON 18/10/2021|E|</response></data></webapi>""";
        ApiResultPages apiResultPages = underTest.parse(testingData);
        assertEquals(2, apiResultPages.getPageCount());
    }


    @Test
    void formatTitleSearchResult_WasFailing_onNonPrintableCharsIncludingAmpersand() throws Exception {
        String testingData = """
        <webapi><status>0</status><charge>0</charge><badfield>0</badfield><data><getmore>0</getmore><length>1408</length><response>S|RD|SP|1/SP62511|SP|1/SP62511|60|1||2|              NEW SOUTH WALES LAND REGISTRY SERVICES - CT INQUIRY|3|              ---------------------------------------------------|4|Following the abolishment of paper certificates of title in NSW|5|on 11 October 2021, Real Property title reference information retrieved through|6|the �Certificate of Title Inquiry� and �Certificate Authentication Code� searches|7|should not be relied upon without conducting further checks. Information provided|8|about Water Access Licences in these searches is unaffected and remains current.|9||10|                      WARNING: ***** TEST DATA ONLY *****|11||12||13|    FOLIO: 1/SP62511|14|    ------|15||16|            Status: AFFECTED BY LODGMENT|17|        Edition No: 4    Date Issued: 16/9/2006    Status Date: 19/4/2016|18| Controlling Party: 20S    AUSTRALIA & NEW ZEALAND BANKING GROUP LTD.|19|   Returning Party: 20S    AUSTRALIA & NEW ZEALAND BANKING GROUP LTD.|20| Last Delivered To: 36B    LANGES MORTGAGE SERVICE|21|    Date Delivered: 16/9/2006      Issued By: M  AC604841|22| Title Affected By: AD AD288869    AE AD288870.|23|           CT With: AD288869|24||25|                    *** END OF SEARCH ***|26||27||28||29||30||31||32||33||34||35||36||37||38||39||40||41||42||43||44||45||46||47||48||49||50||51||52||53||54||55||56||57||58||59||60|    lrs:null-ewang                           PRINTED ON 18/10/2021|E|</response></data></webapi>""";
        ApiResultPages apiResultPages = underTest.parse(testingData);
        assertEquals(1, apiResultPages.getPageCount());
    }

    @Test
    void formatTitleSearchResult_WasFailing_on_Q_Response_FirstChar_ApiRequest() throws Exception {
        String testingData = """
           <webapi><status>0</status><charge>0</charge><badfield>0</badfield><data><getmore>0</getmore><length>29</length><response>Q|1||0||4|A3|253|201101111223</response></data></webapi> 
           """;
        ApiResultPages apiResultPages = underTest.parse(testingData);
        assertEquals(1, apiResultPages.getPageCount());
    }
    @Test
    void formatTitleSearchResult_WasFailing_on_R_Response_FirstChar_ApiRequest() throws Exception {
        String testingData = """
                             <webapi><status>0</status><charge>1015</charge><badfield>0</badfield><data><getmore>0</getmore><length>9</length><response>R|R080331</response></data></webapi>
                    """;
        ApiResultPages apiResultPages = underTest.parse(testingData);
        System.out.println("data \n" + apiResultPages);
        assertEquals(1, apiResultPages.getPageCount());
    }



    @Test
    void formatTitleSearchResult_WasFailing_on_M_Response_FirstChar_ApiRequest() throws Exception {
        String testingData = """
                              <webapi><status>22</status><charge>0</charge><badfield>0</badfield><data><getmore>0</getmore><length>77</length><response>M|1|1|    SUB FOLIOS EXIST - FOR DETAILS SEE SUB FOLIO INQUIRY FOR 1/SP244|E|</response></data></webapi>
                    """;
        ApiResultPages apiResultPages = underTest.parse(testingData);
        System.out.println("data \n" + apiResultPages);
        assertEquals(1, apiResultPages.getPageCount());
    }

}
