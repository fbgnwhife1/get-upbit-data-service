package upbit_candle.candle.WebSocket;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.stereotype.Service;
import upbit_candle.candle.Entity.MarketEntity;
import upbit_candle.candle.Entity.Result.Conclusion;
import upbit_candle.candle.Repository.MarketRepository;
import upbit_candle.candle.Service.forTest.ForTest;
import upbit_candle.candle.WebSocket.WsListener;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RunSocketService {

    private final WsListener webSocketListener;

    /*
    체결 내역
     */
    public void runSocket(List<String> marketList, Long pivot) throws InterruptedException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("wss://api.upbit.com/websocket/v1")
                .build();

        webSocketListener.setParameter(Conclusion.trade, marketList, pivot);
        client.newWebSocket(request, webSocketListener);
        client.dispatcher().executorService().shutdown();
    }

//    public void runOrderBook() throws InterruptedException{
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url("wss://api.upbit.com/websocket/v1")
//                .build();
//
//        webSocketListener.setParameter(Conclusion.trade);
//        client.newWebSocket(request, webSocketListener);
//        client.dispatcher().executorService().shutdown();
//    }
}
