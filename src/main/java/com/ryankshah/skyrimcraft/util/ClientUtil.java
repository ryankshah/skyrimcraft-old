package com.ryankshah.skyrimcraft.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientUtil
{
    public static World getClientWorld() {
        return Minecraft.getInstance().level;
    }

    public static AbstractClientPlayerEntity getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    public static Set<Vector3d> circle(Vector3d origin, Vector3d normal, double radius, double amount){
        normal.normalize();
        //First tangent vector is derived from positive y, giving "up" from the player's view.
        //Subtracting off the normal vector removes the part of the vector that is not in the plane
        //It's unlikely the player will be looking straight up, but if they are this will fail
        ///since it will give the zero vector. You should add some additional logic to detect
        //if the player is "looking up" and use x or z instead.
        Vector3d tangent1 = new Vector3d(0, 1, 0).subtract(normal).normalize();
        //Second tangent is simply orthogonal to both normal and tangent vectors.
        //This is obtained by a cross product. There is no need to normalize again
        //since the cross product of orthonormal vectors produces a unit vector
        Vector3d tangent2 = normal.cross(tangent1);

        Set<Vector3d> circlePoints = new HashSet<>();
        for(double angle = 0.0D; angle < 2 * Math.PI; angle += amount / 180D * (2 * Math.PI)) {
            //Iterate over angles and use the tangent vectors as your coordinates
            //Think of these vectors as hands on a clock rotating around to produce the circle.
            Vector3d x = tangent1.scale(Math.cos(angle) * radius); //Think of this as x, but projected in the plane
            Vector3d y = tangent2.scale(Math.sin(angle) * radius); //Think of this as y, but projected in the plane
            //Add these contributions to the origin, making a circle around the point defined by origin
            circlePoints.add(origin.add(x).add(y));
        }
        return circlePoints;
    }

    public static void drawCubeAtPos(RenderWorldLastEvent event, BlockPos pos, Vector3d size, int color)  {
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        IVertexBuilder builder = buffer.getBuffer(RenderType.lightning());

        //move where the line is in the world. otherwise it is drawn around origin 0,0,0 I think?
        Vector3d view = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        MatrixStack matrixStack = event.getMatrixStack();
        float minX = pos.getX() - (float)(size.x / 2), minY = pos.getY() - (float)(size.y / 2), minZ = pos.getZ() - (float)(size.z / 2),
                maxX = pos.getX() + (float)(size.x / 2), maxY = pos.getY() + (float)(size.y / 2), maxZ = pos.getZ() + (float)(size.z / 2);
        int[] colors = getRGBAArrayFromHexColor(color);
        //RenderSystem.lineWidth(3);

        matrixStack.pushPose();
        matrixStack.translate(-view.x - minX, -view.y, -view.z - minZ); //+ size.y + 5
        Matrix4f matrix = matrixStack.last().pose();

        // up
        builder.vertex(matrix, minX, minY, minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, maxX, minY, minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, maxX, minY, maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, minX, minY, maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();

        //down
        builder.vertex(matrix, minX, maxY, minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, minX, maxY, maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, maxX, maxY, maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, maxX, maxY, minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();

        //north
        builder.vertex(matrix, minX, minY, minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, minX, maxY, minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, maxX, maxY, minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, maxX, minY, minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();

        //south
        builder.vertex(matrix, minX, minY, maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, maxX, minY, maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, maxX, maxY, maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, minX, maxY, maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();

        //east
        builder.vertex(matrix, maxX, minY, minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, maxX, maxY, minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, maxX, maxY, maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, maxX, minY, maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();

        //west
        builder.vertex(matrix, minX, minY, minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, minX, minY, maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, minX, maxY, maxZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();
        builder.vertex(matrix, minX, maxY, minZ).color(colors[0], colors[1], colors[2], colors[3]).endVertex();

        matrixStack.popPose();
        RenderSystem.disableDepthTest();
        buffer.endBatch(RenderType.lightning());

    }

    public static int[] getRGBAArrayFromHexColor(int color)  {
        int[] ints = new int[4];
        ints[0] = (color >> 24 & 255);
        ints[1] = (color >> 16 & 255);
        ints[2] = (color >>  8 & 255);
        ints[3] = (color       & 255);
        return ints;
    }

    public static boolean canPlayerBeSeen() {
        PlayerEntity clientPlayer = getClientPlayer();
        double maxSeenBound = 20D;
        List<LivingEntity> entityList = clientPlayer.level.getEntities(clientPlayer, new AxisAlignedBB(clientPlayer.getX() - (double)maxSeenBound, clientPlayer.getY() - (double)maxSeenBound, clientPlayer.getZ() - (double)maxSeenBound, clientPlayer.getX() + (double)maxSeenBound, clientPlayer.getY() + (double)maxSeenBound, clientPlayer.getZ() + (double)maxSeenBound)).stream().filter(entity -> entity instanceof LivingEntity).map(LivingEntity.class::cast).collect(Collectors.toList());

        return entityList.stream().anyMatch(e -> canEntitySee(e, clientPlayer));
    }

    public static boolean canEntitySee(LivingEntity viewer, LivingEntity beingViewed) {
        double dx = beingViewed.getX() - viewer.getX();
        double dz;
        for (dz = beingViewed.getX() - viewer.getZ(); dx * dx + dz * dz < 1.0E-4D; dz = (Math.random() - Math.random()) * 0.01D) {
            dx = (Math.random() - Math.random()) * 0.01D;
        }
        while (viewer.yRot > 360) {
            viewer.yRot -= 360;
        }
        while (viewer.yRot < -360) {
            viewer.yRot += 360;
        }
        float yaw = (float) (Math.atan2(dz, dx) * 180.0D / Math.PI) - viewer.yRot;
        yaw = yaw - 90;
        while (yaw < -180) {
            yaw += 360;
        }
        while (yaw >= 180) {
            yaw -= 360;
        }

        return yaw < 60 && yaw > -60 && viewer.canSee(beingViewed);
    }
}