package cn.smark.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChatChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LoggingHandler());
        // 因为是基于http的 所以使用http的编码和解析器
        pipeline.addLast(new HttpServerCodec());
        // 是以块方式写，添加ChunkWriteHandler() 处理器
        pipeline.addLast(new ChunkedWriteHandler());
        // http在传输过程中是分段的，这就是为什么当浏览器发送大量数据的时候，会发出多次http请求
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new WebSocketServerProtocolHandler("/chat"));
        pipeline.addLast(new ChatHandler());
    }
}
