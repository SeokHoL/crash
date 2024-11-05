package com.seokho.crash.config;


import com.seokho.crash.model.coinbase.PriceResponse;
import com.seokho.crash.model.crashsession.CrashSessionCategory;
import com.seokho.crash.model.crashsession.CrashSessionPostRequestBody;
import com.seokho.crash.model.exchange.ExchangeResponse;
import com.seokho.crash.model.sessionspeaker.SessionSpeaker;
import com.seokho.crash.model.sessionspeaker.SessionSpeakerPostRequestBody;
import com.seokho.crash.model.user.UserSignUpRequestBody;
import com.seokho.crash.service.CrashSessionService;
import com.seokho.crash.service.SessionSpeakerService;
import com.seokho.crash.service.SlackService;
import com.seokho.crash.service.UserService;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

@Configuration
public class ApplicationConfiguration {
    
    private static final RestClient restClient = RestClient.create();
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

    private static final Faker faker = new Faker();

    @Autowired private UserService userService;

    @Autowired private SessionSpeakerService sessionSpeakerService;

    @Autowired private CrashSessionService crashSessionService;

    @Autowired private SlackService slackService;

    @Bean
    public ApplicationRunner applicationRunner(){
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {



                createTestUsers();
                createTestSessionSpeakers(10);
//
//                //비트코인 USD 가격조회
//                var bitcoinUsdPrcie = getBitcoinUsdPrice();
//
//                //USD TO krw 환율조회
//                var usdTokrwExchangeRate = getUsdToKrwExchangeRate();
//
//                //비트코인 KRW 가격 계산
//                var koreanPremium = 1.1;
//                var bitcoinKrwPrice =bitcoinUsdPrcie * usdTokrwExchangeRate * koreanPremium;
//
//                logger.info(String.format("BTC KRW: %.2f", bitcoinKrwPrice));
            }
        };
    }

    private Double getBitcoinUsdPrice(){
        var response=
            restClient
                    .get()
                    .uri("https://api.coinbase.com/v2/prices/BTC-USD/buy")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
                        logger.error(new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8
                        ));
                    })//위의 요청을 실행해줘

                    .body(PriceResponse.class);    //응답을 어떻게 받을지 결정.

        assert response != null;
       return Double.parseDouble(response.data().amount());
    }

    private Double getUsdToKrwExchangeRate(){
        var response=
                restClient
                        .get()
                        .uri("https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=hJcQzBG8O11Bk8MorC09arb4a01FAxF0&searchdate=20180102&data=AP01")
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError,(req,res)->{
                            logger.error(new String(res.getBody().readAllBytes(), StandardCharsets.UTF_8
                            ));
                        })//위의 요청을 실행해줘

                        .body(ExchangeResponse[].class);    //응답을 어떻게 받을지 결정.

        assert response != null;
        logger.info(response.toString());

        var usdToKrwExchangeRate =
        Arrays.stream(response).filter(
                exchangeResponse -> exchangeResponse.cur_unit().equals("USD")
        ).findFirst().orElseThrow();

        return Double.parseDouble(usdToKrwExchangeRate.deal_bas_r().replace(",","")); //꼼마(,)제거
    }



    private void createTestUsers(){
        userService.signUp(new UserSignUpRequestBody("jayce","1234","Dev Jayce", "jayce@crash.com"));
        userService.signUp(new UserSignUpRequestBody("jay","1234","Dev Jay", "jay@crash.com"));
        userService.signUp(new UserSignUpRequestBody("rose","1234","Dev Rose", "rose@crash.com"));
        userService.signUp(new UserSignUpRequestBody("rosa","1234","Dev Rosa", "rosa@crash.com"));

    }

    private void createTestSessionSpeakers(int numberOfSpeakers){
        var sessionSpeakers =
        IntStream.range(0,numberOfSpeakers).mapToObj(i -> createTestSessionSpeaker()).toList();

        sessionSpeakers.forEach(
                sessionSpeaker -> {
                    int numberOfSessions = new Random().nextInt(4) + 1;
                    IntStream.range(0, numberOfSessions).forEach(i-> createTestCrashSession(sessionSpeaker));
                }
        );

    }

    private SessionSpeaker createTestSessionSpeaker() {
        var name = faker.name().fullName();
        var company = faker.company().name();
        var description = faker.shakespeare().romeoAndJulietQuote();

        return sessionSpeakerService.createSessionSpeaker(
                new SessionSpeakerPostRequestBody(company, name, description));
    }

    private void createTestCrashSession(SessionSpeaker sessionSpeaker){
        var title = faker.book().title();
        var body = faker.shakespeare().asYouLikeItQuote() +
                faker.shakespeare().hamletQuote() +
                faker.shakespeare().kingRichardIIIQuote() +
                faker.shakespeare().romeoAndJulietQuote();

        crashSessionService.createCrashSession(new CrashSessionPostRequestBody(
                title,
                body,
                getRandomCategory(),
                ZonedDateTime.now().plusDays(new Random().nextInt(2) + 1),
                sessionSpeaker.speakerId()
        ));
    }

    private CrashSessionCategory getRandomCategory(){
        var categories = CrashSessionCategory.values();
        int randomIndex = new Random().nextInt(categories.length);
        return categories[randomIndex];
    }

}
