package blossom.project.rpc.netty.chatroom.server.handler;

import blossom.project.rpc.netty.chatroom.message.GroupJoinResponseMessage;
import blossom.project.rpc.netty.chatroom.server.session.Group;
import blossom.project.rpc.netty.chatroom.server.session.GroupSessionFactory;
import blossom.project.rpc.netty.chatroom.message.GroupQuitRequestMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {
        Group group = GroupSessionFactory.getGroupSession().removeMember(msg.getGroupName(), msg.getUsername());
        if (group != null) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, "已退出群" + msg.getGroupName()));
        } else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, msg.getGroupName() + "群不存在"));
        }
    }
}
