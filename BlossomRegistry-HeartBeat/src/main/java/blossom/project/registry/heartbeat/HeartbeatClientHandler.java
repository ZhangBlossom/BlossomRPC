package blossom.project.registry.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class HeartbeatClientHandler extends ChannelInboundHandlerAdapter {
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleStateEvent.WRITER_IDLE_STATE_EVENT.state()) {
                System.out.println("写空闲，发送心跳");
                ctx.writeAndFlush("Heartbeat\n");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
