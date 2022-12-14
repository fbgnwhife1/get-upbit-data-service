package upbit_candle.candle.WebSocket.GetDataInit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import upbit_candle.candle.Entity.MarketEntity;
import upbit_candle.candle.Repository.MarketRepository;
import upbit_candle.candle.WebSocket.RunSocket;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetDataInit {
    private final InitService initService;

    @PostConstruct
    public void init() throws Exception {
        initService.openSocket();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final RunSocket runSocket;
        private final MarketRepository marketRepository;

        public void openSocket() throws InterruptedException {
            List<MarketEntity> all = marketRepository.findAll();
//            List<MarketEntity> all = marketRepository.findByMarketContaining("KRW");

            List<String> marketList = new ArrayList<>();

            for (MarketEntity market : all) {
                marketList.add(market.getMarket());

                if(marketList.size() == 15){
                    runSocket.runSocket(marketList, 0L);
                    marketList.clear();
                    Thread.sleep(1000);
                }
            }
            if(!marketList.isEmpty()){
                runSocket.runSocket(marketList, 0L);
            }
        }
    }
}
