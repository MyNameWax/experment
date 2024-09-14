package cn.rzpt.netty;

import cn.rzpt.netty.ws.handler.NettyWebSocketServerHandler;
import cn.rzpt.netty.ws.handler.encoder.MessageProtocolDecoder;
import cn.rzpt.netty.ws.handler.encoder.MessageProtocolEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@Configuration
@AllArgsConstructor
public class NettySocketServer {
    private final RedisTemplate redisTemplate;

    // WebSocket端口
    public static final Integer WEB_SOCKET_PORT = 8090;

    // 创建Netty线程池执行器
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(4);


    /**
     * 启动Netty
     */
    @PostConstruct
    public void start() {
        run();
    }

    /**
     * 销毁Netty
     */
    @PreDestroy
    public void destroy() {
        Future<?> future = bossGroup.shutdownGracefully();
        Future<?> future1 = workerGroup.shutdownGracefully();
        future.syncUninterruptibly();
        future1.syncUninterruptibly();
        log.info("WebSocket Netty 停止成功");
    }


    /**
     * 启动Netty
     */
    @SneakyThrows
    public void run() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                // NioServerSocketChannel 非阻塞模式支持非阻塞模式操作，这意味着它可以异步地处理 I/O 操作，从而提高系统的并发性能
                // NioServerSocketChannel可以与Selector结合使用,实现对多个管道的高效管理（多路复用）
                // 灵活的数据传输
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_KEEPALIVE, true)
                // 添加log处理器
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        // 30秒客户端没有向服务器发送心跳则关闭连接
                        pipeline.addLast(new IdleStateHandler(30, 0, 0));
                        // 因为使用http协议,所以需要使用http的编码器,解码器
                        pipeline.addLast(new HttpServerCodec());
                        // 以块方式写,添加ChunkedWriter处理器,支持大数据流
                        pipeline.addLast(new ChunkedWriteHandler());
                        // 对Http消息做聚合操作,产生两个对象,FullHttpRequest、FullHttpResponse
                        pipeline.addLast(new HttpObjectAggregator(1024 * 60));
                        // WebSocket处理器
                        pipeline.addLast(new WebSocketServerProtocolHandler("/"));
                        // WebSocket自定义编解码处理器
                        pipeline.addLast("encode", new MessageProtocolEncoder());
                        pipeline.addLast("decode", new MessageProtocolDecoder());
                        // 自定义Handler 处理业务逻辑
                        pipeline.addLast(new NettyWebSocketServerHandler(redisTemplate));
                    }
                });
        //启动服务器
        serverBootstrap.bind(WEB_SOCKET_PORT).sync();
        log.info("WebSocket Netty 启动成功");
    }
}
