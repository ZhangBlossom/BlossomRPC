package blossom.project.rpc.netty.chatroom.server.handler;

import blossom.project.rpc.netty.chatroom.message.GroupMembersRequestMessage;
import blossom.project.rpc.netty.chatroom.server.session.GroupSessionFactory;
import blossom.project.rpc.netty.chatroom.message.GroupMembersResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Set;

@ChannelHandler.Sharable
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage msg) throws Exception {
        Set<String> members = GroupSessionFactory.getGroupSession()
                .getMembers(msg.getGroupName());
        ctx.writeAndFlush(new GroupMembersResponseMessage(members));
    }
}
