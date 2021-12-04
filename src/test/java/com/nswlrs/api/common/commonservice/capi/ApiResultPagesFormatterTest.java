package com.nswlrs.api.common.commonservice.capi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nswlrs.api.common.commonservice.capi.model.ApiResultPages;
import com.nswlrs.api.common.commonservice.capi.util.ApiResultPagesFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test cases for LandTitleFormatter")
class ApiResultPagesFormatterTest {

    @Test
    void formatSearchResult() throws Exception {
        String testingData = "S|RT|DP|1863/1000001|DP|1863/1000001|120|1||2|             NEW SOUTH WALES LAND REGISTRY SERVICES - TITLE SEARCH|3|             -----------------------------------------------------|4|                      WARNING: ***** TEST DATA ONLY *****|5||6||7|    FOLIO: 1863/1000001|8|    ------|9||10|               SEARCH DATE       TIME              EDITION NO    DATE|11|               -----------       ----              ----------    ----|12|               12/10/2021       10:23 AM               8       20/9/2016|13||14||15|    LAND|16|    ----|17|    LOT 1863 IN DEPOSITED PLAN 1000001|18|       AT QUEEN'S SQUARE|19|       LOCAL GOVERNMENT AREA SYDNEY|20|       PARISH OF ST JAMES   COUNTY OF CUMBERLAND|21|       TITLE DIAGRAM DP1000001|22||23|    FIRST SCHEDULE|24|    --------------|25|    THE STATE OF NEW SOUTH WALES                            (T AD288743)|26||27|    SECOND SCHEDULE (19 NOTIFICATIONS)|28|    ---------------|29|    1   DP1000001 THIS IS THE FIRST TITLE ISSUED IN THE INTEGRATED|30|                  TITLING SYSTEM AND REPRESENTS THE LAND UPON WHICH THE|31|                  LAND TITLES OFFICE IN SYDNEY N.S.W. AUSTRALIA IS|32|                  ERECTED|33|    2   7436472   THIS PRIORITY NOTICE TO PROTECT THE INTERESTS OF|34|                  ALLAN MURRAY JAPALJARRI EXPIRES 1-AUG-2014|35|  * 3   AD284154  CAVEAT BY ABC PTY LIMITED|36|  * 4   AD283531  CAVEAT BY ABC PTY LIMITED|37|    5   X234      LEASE TO ABC PTY LIMITED EXPIRES: SEE DEALING.|38|    6   AD284797  LEASE TO AB CPL EXPIRES: SEE DEALING.|39|    7   AD284798  LEASE TO XYZ PTY LIMITED EXPIRES: SEE DEALING.|40|    8   AD284799  LEASE TO 123 PTY LIMITED EXPIRES: SEE DEALING.|41|    9   AD284800  LEASE TO 646 AM PTY LIMITED EXPIRES: SEE DEALING.|42|  * 10  AD284801  CAVEAT BY 666 PTY LIMITED|43|    11  AD284802  LEASE TO ABC PTY LIMITED EXPIRES: SEE DEALING.|44|    12  AD284803  LEASE TO XYZ PTY LIMITED EXPIRES: SEE DEALING.|45|    13  AD284804  LEASE TO 123 PTY LIMITED EXPIRES: SEE DEALING.|46|    14  AD284805  LEASE TO 789 PTY LIMITED EXPIRES: SEE DEALING.|47|    15  AD284806  LEASE TO SDFBSK PTY LIMITED EXPIRES: SEE DEALING.|48|    16  AD284807  LEASE TO BLAH BLAH PTY LIMITED EXPIRES: SEE DEALING.|49|    17  AFFECTED BY BOUNDARY ADJUSTMENT. SEE GOVERNMENT GAZETTE DATED|50|        XXXXXXXXX|51|  * 18  AK998770  BIODIVERSITY CERTIFICATION AGREEMENT (PART 8|52|                  DIVISION 5 BIODIVERSITY CONSERVATION ACT 2016)|53|  * 19  AK999064  CAVEAT BY KEVIN PORTLOCK|54||55||56||57||58|                                             END OF PAGE 1 - CONTINUED OVER|59||60|    depopd                                   PRINTED ON 12/10/2021|P|61||62|             NEW SOUTH WALES LAND REGISTRY SERVICES - TITLE SEARCH|63|             -----------------------------------------------------|64|                      WARNING: ***** TEST DATA ONLY *****|65||66||67|    FOLIO: 1863/1000001                                        PAGE   2|68|    ------|69||70|    NOTATIONS|71|    ---------|72|    NOTE: MD TEST|73|    DP1206393 NOTE: STUFF|74||75|    UNREGISTERED DEALINGS:    C  AD283535    L  AD283536    L  AD283537|76|      X  AD283579    X  AD283580    T  AD283893    X  AD284402|77|      X  AD284582    R  AD284651    R  AD284676    R  AD284678|78|      R  AD284679    R  AD284684    R  AD284712    R  AD284713|79|      R  AD284714    R  AD284715    R  AD284716    R  AD284717|80|      R  AD284718    R  AD284719    R  AD284720    R  AD284721|81|      R  AD284722    R  AD284726    R  AD284727    R  AD284728|82|      R  AD284729    R  AD284730    R  AD284731    R  AD284732|83|      R  AD284733    R  AD284734    L  AD284784    L  AD284785|84|      L  AD284786    L  AD284787    L  AD284788    L  AD284789|85|      X  AD284790    X  AD284791    R  AD284792    R  AD284793|86|      R  AD284794    R  AD284795    R  AD284796    X  AD284953|87|      X  AD285056    T  AD285218    X  AD285797    X  AD285798|88|      DM AD286035    DM AD286079    DM AD286100    T  AD286101|89|      M  AD286102    X  AD286130    T  AD286141    R  AD286192|90|      R  AD286193    R  AD286194    DM AD286197    T  AD286198|91|      X  AD286250    X  AD286306    MA AD287220    VP AD287229|92|      X  AD287686    R  AD287721    LM AD288498    X  AD288752|93|      X  AD288754    X  AD288755    X  AD288756    X  AD288757|94|      X  AD288758    X  AD288759    X  AD288760    DD AD289022|95|      X  AD289038    L  AK998339    L  AK998340    L  AK998341|96|      L  AK998342    L  AK998343    L  AK998344    L  AK998345|97|      L  AK998346    X  AK998347    X  AK998348    DD AK999355|98|      PE DP270639  PP DP1112445    PP DP1112448    PP DP1123595|99|      PP DP1123598    DP1123848     DP1124409     DP1124506|100|      DP1124528     DP1124531     DP1124566     DP1124581     DP1125164|101|      DP1126552     DP1126684     PP DP1185178    DP1185197|102|      DP1185293     DP1185296     DP1185297     DP1185298     DP1185303|103|      DP1185351     DP1185352     DP1185353     DP1185364     PE DP1185385|104|      DP1185390     DP1185403     PP DP1185455    PP DP1185456|105|      PP DP1185524    PP DP1185810    PP DP1185850    PP DP1186011|106|      PP DP1186012    PP DP1186013    PE DP1186197  PP DP1186198|107|      DP1186615     DP1186616     DP1186617     DP1186635     PP SP79814|108|      SP86568.|109||110|            ***  END OF SEARCH  ***|111||112||113||114||115||116||117||118||119||120|    depopd                                   PRINTED ON 12/10/2021|E|";
        ApiResultPages apiResultPages = ApiResultPagesFormatter.formatSearchResult(testingData);
        assertEquals(2, apiResultPages.getPageCount());
    }

    @Test
    void formatSearchHistoricalResult() throws Exception {
        String testingData = "S|RH|DP|1/23424|DP|1/23424|60|1||2|           NEW SOUTH WALES LAND REGISTRY SERVICES - HISTORICAL SEARCH|3|           ----------------------------------------------------------|4|                      WARNING: ***** TEST DATA ONLY *****|5||6|                                              SEARCH DATE|7|                                              -----------|8|                                              15/10/2021 10:57AM|9||10|  FOLIO: 1/23424|11|  ------|12||13|         First Title(s): SEE PRIOR TITLE(S)|14|         Prior Title(s): VOL 14328 FOL 200|15||16|  Recorded    Number     Type of Instrument              C.T. Issue|17|  --------    ------     ------------------              ----------|18|  21/8/1988              TITLE AUTOMATION PROJECT        LOT RECORDED|19|                                                         FOLIO NOT CREATED|20||21|  2/12/1988              CONVERTED TO COMPUTER FOLIO     FOLIO CREATED|22|                                                         CT NOT ISSUED|23||24|  15/6/2004   AA719520   TRANSMISSION APPLICATION        EDITION 1|25||26| 18/12/2004   AB168412   TRANSFER|27| 18/12/2004   AB168413   MORTGAGE                        EDITION 2|28||29|   1/9/2018   AN678862   DEPARTMENTAL DEALING|30||31||32|                    ***  END OF SEARCH  ***|33||34||35||36||37||38||39||40||41||42||43||44||45||46||47||48||49||50||51||52||53||54||55||56||57||58||59||60|    lrs:DRD-aelali                           PRINTED ON 15/10/2021|E|";
        ApiResultPages apiResultPages = ApiResultPagesFormatter.formatSearchResult(testingData);
        assertEquals(1, apiResultPages.getPageCount());
    }

    @Test
    void formatTitleSearchResult() throws Exception {
        String testingData = "S|ST|DP|1/23424|DP|1/23424|60|1||2|             NEW SOUTH WALES LAND REGISTRY SERVICES - TITLE SEARCH|3|             -----------------------------------------------------|4|                      WARNING: ***** TEST DATA ONLY *****|5||6||7|    FOLIO: 1/23424|8|    ------|9||10|               SEARCH DATE       TIME              EDITION NO    DATE|11|               -----------       ----              ----------    ----|12|               15/10/2021       10:56 AM               EDITION 2     18/12/2004|13||14||15|    LAND|16|    ----|17|    LOT 1 IN DEPOSITED PLAN 23424|18|       LOCAL GOVERNMENT AREA WAGGA WAGGA|19|       PARISH OF SOUTH WAGGA WAGGA   COUNTY OF WYNYARD|20|       TITLE DIAGRAM DP23424|21||22|    FIRST SCHEDULE|23|    --------------|24|    CRAIG STEPHEN KATSOOLIS                                 (T AB168412)|25||26|    SECOND SCHEDULE (2 NOTIFICATIONS)|27|    ---------------|28|        1   RESERVATIONS AND CONDITIONS IN THE CROWN GRANT(S)|29|MW      2   AB168413  MORTGAGE TO NECDL EASTPAC|30||31|    NOTATIONS|32|    ---------|33||34|    UNREGISTERED DEALINGS: NIL|35||36|            ***  END OF SEARCH  ***|37||38||39||40||41||42||43||44||45||46||47||48||49||50||51||52||53||54||55||56||57||58||59||60|    lrs:DRD-aelali                           PRINTED ON 15/10/2021|E|";
        ApiResultPages apiResultPages = ApiResultPagesFormatter.formatSearchResult(testingData);
        assertEquals(1, apiResultPages.getPageCount());
    }

    @Test
    void formatCtilnquirySearchResult() throws Exception {
        String testingData = "S|RD|DP|1/23424|DP|1/23424|60|1||2|              NEW SOUTH WALES LAND REGISTRY SERVICES - CT INQUIRY|3|              ---------------------------------------------------|4|Following the abolishment of paper certificates of title in NSW|5|on 11 October 2021, Real Property title reference information retrieved through|6|the �Certificate of Title Inquiry� and �Certificate Authentication Code� searches|7|should not be relied upon without conducting further checks. Information provided|8|about Water Access Licences in these searches is unaffected and remains current.|9||10|                      WARNING: ***** TEST DATA ONLY *****|11||12||13|    FOLIO: 1/23424|14|    ------|15||16|            Status: NOT IN NSWLRS - DELIVERED|17|        Edition No: 2    Date Issued: 18/12/2004   Status Date: 18/12/2004|18| Last Delivered To: 208X   ST GEORGE BANK LIMITED|19|    Date Delivered: 18/12/2004     Issued By: M  AB168413|20||21|                    *** END OF SEARCH ***|22||23||24||25||26||27||28||29||30||31||32||33||34||35||36||37||38||39||40||41||42||43||44||45||46||47||48||49||50||51||52||53||54||55||56||57||58||59||60|    lrs:DRD-aelali                           PRINTED ON 15/10/2021|E|";
        ApiResultPages apiResultPages = ApiResultPagesFormatter.formatSearchResult(testingData);
        assertEquals(1, apiResultPages.getPageCount());
    }
}
